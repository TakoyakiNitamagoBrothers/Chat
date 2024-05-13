package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Room;
import com.example.repository.RoomRepository;

import jakarta.transaction.Transactional;

@Service
public class RoomService {

	@Autowired
	private RoomRepository roomRepository;

	public boolean authenticate(String newRoomName) {
		Room roomName = roomRepository.findByName(newRoomName);
		return roomName == null;
	}

	@Transactional
	public void createRoom(String newRoomName) {
		Room newRoom = new Room(newRoomName);
		roomRepository.save(newRoom);
	}

	@Transactional
	public int getIdByRoomName(String roomName) {
		return roomRepository.findRoomIdByName(roomName);
	}

	public List<Room> findAllExistingRooms() {
		return roomRepository.findAllExistingRooms();
	}

}
