package com.example.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.service.MessageService;

@Controller
public class MessageController {

	@Autowired
	private MessageService messageService;

	private List<String> messageList = new ArrayList<>();

	@MessageMapping("/chat/{roomId}")
	@SendTo("/topic/messages/{roomId}")
	public String handleChatMessage(@DestinationVariable String roomId, String message) {
		System.out.println("Received message: " + message);
		messageService.saveMessage(message, Integer.parseInt(roomId));
		return message;
	}

	@GetMapping("/getMessages/{roomId}")
	@ResponseBody
	public ResponseEntity<List<String>> getMessages(@PathVariable int roomId) {
		messageList = null;
		messageList = messageService.getMessageList(roomId);
		if (!messageList.isEmpty()) {
			return ResponseEntity.ok(messageList);
		} else {
			return ResponseEntity.noContent().build();
		}
	}

}