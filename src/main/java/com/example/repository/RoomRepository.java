package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.entity.Room;

@Repository
public interface RoomRepository extends JpaRepository<Room, Integer> {

	public Room findByName(String name);

	@Query("SELECT r.roomId FROM Room r WHERE r.name = :roomName")
	public int findRoomIdByName(@Param("roomName") String roomName);
	
	@Query("SELECT new Room(r.name, r.roomId) FROM Room r")
    List<Room> findAllExistingRooms();
}
