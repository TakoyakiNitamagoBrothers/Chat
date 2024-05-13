package com.example.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Message;
import com.example.entity.Room;
import com.example.repository.MessageRepository;
import com.example.repository.RoomRepository;

import jakarta.transaction.Transactional;

@Service
public class MessageService {

	@Autowired
	private MessageRepository messageRepository;

	@Autowired
	private RoomRepository roomRepository;

	@Transactional
	public void saveMessage(String messageContent, int roomId) {
		// Room エンティティを取得
        Room room = roomRepository.findById(roomId)
                  .orElseThrow(() -> new IllegalArgumentException("Invalid Room ID: " + roomId));

        // Message エンティティを作成
        Message message = new Message();
        message.setMessage(messageContent);
        message.setRoom(room);
        message.setTimestamp(LocalDateTime.now()); // 現在の時刻をセット

        // メッセージをデータベースに保存
        messageRepository.save(message);
	}

//	public List<Message> getMessagesByRoomId(int roomId) {
//		return messageRepository.findByMessageRoomId(int roomId);
//	}
	
	@Transactional
	public int getRoomId(String roomName) {

		return roomRepository.findRoomIdByName(roomName);
	}
	
	@Transactional
	public List<String> getMessageList(int roomId) {
		return messageRepository.findMessageContentsByRoomId(roomId);
	}

}
