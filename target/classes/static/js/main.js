'use strict';

var usernamePage = document.querySelector('#username-page');
var userlist = document.querySelector('#userList');
var chatPage = document.querySelector('#chat-page');
var usernameForm = document.querySelector('#usernameForm');
var messageForm = document.querySelector('#messageForm');
var messageInput = document.querySelector('#message');
var messageArea = document.querySelector('#messageArea');
var connectingElement = document.querySelector('.connecting');

var stompClient = null;
var username = null;

function connect(event) {
	username = document.querySelector('#name').value.trim();

	if (username) {
		usernamePage.classList.add('hidden');
		chatPage.classList.remove('hidden');

		var socket = new SockJS('/webChat');
		stompClient = Stomp.over(socket);

		stompClient.connect({}, onConnected, onError);

	}
	event.preventDefault();
}

function onError(error) {
	connectingElement.textContent = 'Could not connect to WebSocket server. Please refresh this page to try again!';
	connectingElement.style.color = 'red';
}

function onConnected() {

	stompClient.subscribe('/topic/public', onMessageReceived);
	stompClient.subscribe('/topic/users', onUsersReceived);
	setInterval(refreshUsers, 5000);
	stompClient.send("/app/chat.register", {}, JSON.stringify({
		user : username,
		type : 'JOIN'
	}))

	connectingElement.classList.add('hidden');
}

function refreshUsers() {
	stompClient.send('/app/users', {}, '');
}

function onUsersReceived(payload) {

	var users = JSON.parse(payload.body);
	var ul = document.getElementById("userList");
	var ll = ul.getElementsByTagName("li");

	if (ll != null) {
		while (ll.length > 0) {
			ul.removeChild(ll[0]);
		}
	}
	for (var i = 0, il = users.length; i < il; i++) {
		var userElement = document.createElement('li');
		var textElement = document.createElement('p');
		var usernameText = document.createTextNode(users[i]);
		textElement.appendChild(usernameText);
		userElement.appendChild(textElement);
		userlist.appendChild(userElement);
	}
	
}


function send(event) {

		var messageContent = messageInput.value.trim();

		if (messageContent && stompClient) {
			var chatMessage = {
				user : username,
				content : messageInput.value,
				type : 'CHAT'
			};

			stompClient.send("/app/chat.send", {}, JSON.stringify(chatMessage));
			messageInput.value = '';
		}
		event.preventDefault();
	
}

function onMessageReceived(payload) {

		var message = JSON.parse(payload.body);

		var messageElement = document.createElement('li');

		if (message.type === 'JOIN') {
			messageElement.classList.add('event-message');
			message.content = message.user + ' joined!';
		} else if (message.type === 'LEAVE') {
			messageElement.classList.add('event-message');
			message.content = message.user + ' left!';
		} else {
			messageElement.classList.add('chat-message');

			var avatarElement = document.createElement('i');
			var avatarText = document.createTextNode(message.user[0]);
			avatarElement.appendChild(avatarText);
			avatarElement.style['background-color'] = '#a6ded1';

			messageElement.appendChild(avatarElement);

			var usernameElement = document.createElement('span');
			var usernameText = document.createTextNode(message.user);
			usernameElement.appendChild(usernameText);
			messageElement.appendChild(usernameElement);
		}

		var textElement = document.createElement('p');
		var messageText = document.createTextNode(message.content);
		textElement.appendChild(messageText);

		messageElement.appendChild(textElement);

		messageArea.appendChild(messageElement);
		messageArea.scrollTop = messageArea.scrollHeight;
	
}

function onDisconect() {
	stompClient.send("/app/chat.leave", {}, JSON.stringify({
		user : username,
		type : 'LEAVE'
	}));
	document.location.reload(true);
}

usernameForm.addEventListener('submit', connect, true)
messageForm.addEventListener('submit', send, true)
