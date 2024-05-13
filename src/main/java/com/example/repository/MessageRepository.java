package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.entity.Message;

public interface MessageRepository  extends JpaRepository<Message, Integer>{

	@Query("SELECT m.message FROM Message m WHERE m.room.id = :roomId")
	public List<String> findMessageContentsByRoomId(@Param("roomId") int roomId);
}
