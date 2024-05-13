document.addEventListener('DOMContentLoaded', function() {

	const chatContainer = document.querySelector('.chat-container');
	chatContainer.scrollTop = chatContainer.scrollHeight;

	var roomDataElement = document.getElementById('room-data');
	var roomId = roomDataElement.getAttribute('data-room-id');
	console.log(roomId);

	const messageList = document.getElementById('messageList');
	fetch('/getMessages/' + roomId)
		.then(response => {
			if (response.ok) {
				return response.json();
			} else {
				throw new Error('Failed to fetch message history');
			}
		})
		.then(messages => {
			messages.forEach(message => {
				const listItem = document.createElement('li');
				listItem.textContent = message;
				messageList.appendChild(listItem);
			});
			console.log(messages);
		})
		.catch(error => {
			console.error('Error fetching message history:', error);
		});

	const socket = new SockJS('/ws');
	const stompClient = Stomp.over(socket);

	stompClient.connect({}, function(frame) {

		console.log('Connected: ' + frame);

		stompClient.subscribe('/topic/messages/' + roomId, function(message) {
			const list = document.getElementById('messageList');
			const listItem = document.createElement('li');
			listItem.textContent = message.body;
			list.appendChild(listItem);
			chatContainer.scrollTop = chatContainer.scrollHeight;
		});

		document.getElementById('chat-form').addEventListener('submit', function(e) {
			e.preventDefault();
			const messageInput = document.getElementById('messageInput');
			const message = messageInput.value.trim();
			if (message !== '') {
				stompClient.send("/app/chat/" + roomId, {}, message);
				messageInput.value = '';
			}
		});
	});

});