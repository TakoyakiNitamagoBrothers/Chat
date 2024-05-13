package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.entity.Room;
import com.example.service.RoomService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpSession;

@Controller
public class RoomController {

	@Autowired
	private RoomService roomService;

	@PostMapping("/createRoom")
	public String CreateRoom(@RequestParam("newRoomName") String newRoomName, HttpSession session) {
		boolean isNotExistingRoom = roomService.authenticate(newRoomName);
		if (isNotExistingRoom) {
			// insert data into the database
			roomService.createRoom(newRoomName);
			// Retrieve the room ID based on the room
			int roomId = roomService.getIdByRoomName(newRoomName);
			// create room object to set the name and id of the created room
			Room room = new Room(newRoomName, roomId);
			// store current room info in the session
			session.setAttribute("currentRoom", room);
			return "redirect:/room";
		} else
			return "redirect:/rooms";
	}

	@PostMapping("/enterRoom")
	public String enterRoom(@RequestParam("roomData") String room, HttpSession session)
			throws JsonMappingException, JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		Room roomData = objectMapper.readValue(room, Room.class);
		session.setAttribute("currentRoom", roomData);
		return "redirect:/room";
	}

	@GetMapping("/room")
	public String room() {
		return "room";
	}

	@GetMapping("/rooms")
	public String rooms() {
		return "rooms";
	}

	@GetMapping("/getExistingRooms")
	public ResponseEntity<List<Room>> getExistingRooms() {
		List<Room> existingRooms = roomService.findAllExistingRooms();
		return ResponseEntity.ok(existingRooms);
	}

}