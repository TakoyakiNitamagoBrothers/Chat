package com.example.controller;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.example.entity.Room;
import com.example.service.RoomService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(RoomController.class)
public class RoomControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RoomService roomService;

    @Test
    public void testCreateRoom() throws Exception {
        String newRoomName = "Test Room";
        int roomId = 1;
        given(roomService.authenticate(newRoomName)).willReturn(true);
        given(roomService.getIdByRoomName(newRoomName)).willReturn(roomId);

        mockMvc.perform(post("/createRoom")
            .param("newRoomName", newRoomName))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/room"))
            .andExpect(request().sessionAttribute("currentRoom", new Room(newRoomName, roomId)));

        verify(roomService).createRoom(newRoomName);
    }

    @Test
    public void testCreateRoomWithExistingName() throws Exception {
        String existingRoomName = "Existing Room";
        given(roomService.authenticate(existingRoomName)).willReturn(false);

        mockMvc.perform(post("/createRoom")
            .param("newRoomName", existingRoomName))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/rooms"));

        verify(roomService, never()).createRoom(anyString());
        verify(roomService, never()).getIdByRoomName(anyString());
    }

    @Test
    public void testEnterRoom() throws Exception {
        Room room = new Room("Test Room", 1);
        String roomJson = new ObjectMapper().writeValueAsString(room);

        mockMvc.perform(post("/enterRoom")
            .param("roomData", roomJson))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/room"))
            .andExpect(request().sessionAttribute("currentRoom", room));
    }

    @Test
    public void testEnterRoomWithInvalidJson() throws Exception {
        String invalidJson = "{\"name\": \"Invalid Room\", \"id\": \"abc\"}";

        mockMvc.perform(post("/enterRoom")
            .param("roomData", invalidJson))
            .andExpect(status().isBadRequest());
    }

    @Test
    public void testRoom() throws Exception {
        mockMvc.perform(get("/room"))
            .andExpect(status().isOk())
            .andExpect(view().name("room"));
    }

    @Test
    public void testRoomWithoutCurrentRoomAttribute() throws Exception {
        mockMvc.perform(get("/room"))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/rooms"));
    }

    @Test
    public void testRooms() throws Exception {
        mockMvc.perform(get("/rooms"))
            .andExpect(status().isOk())
            .andExpect(view().name("rooms"));
    }

    @Test
    public void testRoomsWithCurrentRoomAttribute() throws Exception {
        Room room = new Room("Test Room", 1);
        mockMvc.perform(get("/rooms").sessionAttr("currentRoom", room))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/room"));
    }

    @Test
    public void testGetExistingRooms() throws Exception {
        List<Room> existingRooms = Arrays.asList(
            new Room("Room 1", 1),
            new Room("Room 2", 2)
        );
        given(roomService.findAllExistingRooms()).willReturn(existingRooms);

        mockMvc.perform(get("/getExistingRooms"))
            .andExpect(status().isOk())
            .andExpect(content().json(new ObjectMapper().writeValueAsString(existingRooms)));
    }

    @Test
    public void testGetExistingRoomsWhenNoRoomsExist() throws Exception {
        given(roomService.findAllExistingRooms()).willReturn(Collections.emptyList());

        mockMvc.perform(get("/getExistingRooms"))
            .andExpect(status().isOk())
            .andExpect(content().json("[]"));
    }
}