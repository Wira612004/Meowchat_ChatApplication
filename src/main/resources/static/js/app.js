var stompClient = null;
var username = null;
var selectedContact = "defaultContact"; 

function setUsername() {
    username = document.getElementById('usernameInput').value.trim();
    if (username) {
        document.getElementById('usernameInputContainer').style.display = 'none';
        document.getElementById('messages').style.display = 'block';
        document.getElementById('messageInput').style.display = 'block';
        document.querySelector('button[onclick="sendMessage()"]').style.display = 'block';
        connect();
    } else {
        alert('Please enter a valid name.');
    }
}

function connect() {
    var socket = new SockJS('/ws');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/messages', function (messageOutput) {
            showMessageOutput(JSON.parse(messageOutput.body));
        });
    }, function (error) {
        console.error('STOMP error: ' + error);
        setTimeout(connect, 10000); 
    });
}

function sendMessage() {
    var messageContent = document.getElementById('messageInput').value.trim();
    if (messageContent && stompClient && stompClient.connected && selectedContact) {
        var chatMessage = {
            content: messageContent,
            sender: username,
            recipient: selectedContact,
            timestamp: new Date().toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' })
        };
        stompClient.send("/app/sendMessage", {}, JSON.stringify(chatMessage));
        document.getElementById('messageInput').value = '';
    } else {
        console.error('Cannot send message: Message content or WebSocket connection not available.');
    }
}

function showMessageOutput(messageOutput) {
    var response = document.getElementById('messages');
    var messageElement = document.createElement('li');
    messageElement.className = 'message';
    messageElement.classList.add(messageOutput.sender === username ? 'me' : 'other');

    var senderElement = document.createElement('p');
    senderElement.className = 'sender';
    senderElement.innerText = messageOutput.sender + ":";
    messageElement.appendChild(senderElement);

    var content = document.createElement('p');
    content.className = 'text';
    content.innerText = messageOutput.content;
    messageElement.appendChild(content);

    var timestamp = document.createElement('span');
    timestamp.className = 'timestamp';
    timestamp.innerText = messageOutput.timestamp || new Date().toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' });
    messageElement.appendChild(timestamp);

    response.appendChild(messageElement);
    response.scrollTop = response.scrollHeight;
}

function selectContact(contact) {
    selectedContact = contact;
}

document.addEventListener('DOMContentLoaded', function () {
    document.getElementById('usernameInputContainer').style.display = 'block';
});
