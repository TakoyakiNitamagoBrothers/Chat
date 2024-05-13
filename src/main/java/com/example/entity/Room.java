package com.example.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "rooms")
public class Room {

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	@Column(name = "room_id")
	private int roomId;

	@Column(name = "name")
	private String name;

	public Room() {
	}

	public Room(String name) {
		this.name = name;
	}
	public Room(int roomId) {
		this.roomId = roomId;
	}

	
	public Room(String name, int roomId) {
		this.name = name;
		this.roomId = roomId;
	}

	public int getRoomId() {
		return roomId;
	}

	public String getName() {
		return name;
	}

	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@OneToMany(mappedBy = "room")
	private List<Message> messages;



}
