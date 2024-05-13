document.addEventListener('DOMContentLoaded', function() {
	const roomsTableBody = document.getElementById('roomsTableBody');

	fetch('/getExistingRooms')
		.then(response => response.json())
		.then(rooms => {
			rooms.forEach(room => {
				const row = document.createElement('tr');

				const nameCell = document.createElement('td');
				nameCell.textContent = room.name;
				row.appendChild(nameCell);

				const actionsCell = document.createElement('td');
				const enterForm = createEnterRoomForm(room);
				actionsCell.appendChild(enterForm);
				row.appendChild(actionsCell);

				roomsTableBody.appendChild(row);
			});
		})
		.catch(error => {
			console.error('Error fetching rooms:', error);
		});
});

function createEnterRoomForm(room) {
	const form = document.createElement('form');
	form.setAttribute('method', 'post');
	form.setAttribute('action', '/enterRoom');
	form.setAttribute('enctype', 'application/json');
	
	const roomDataInput = document.createElement('input');
	roomDataInput.setAttribute('type', 'hidden');
	roomDataInput.setAttribute('name', 'roomData');
	roomDataInput.setAttribute('value', JSON.stringify(room));
	form.appendChild(roomDataInput);

	const enterButton = document.createElement('button');
	enterButton.setAttribute('type', 'submit');
	enterButton.textContent = 'Enter';
	form.appendChild(enterButton);

	return form;
}