package com.extendablechattingbe.extendablechattingbe.service;

import com.extendablechattingbe.extendablechattingbe.common.exception.CustomException;
import com.extendablechattingbe.extendablechattingbe.domain.Room;
import com.extendablechattingbe.extendablechattingbe.repository.MemberRepository;
import com.extendablechattingbe.extendablechattingbe.repository.MessageRepository;
import com.extendablechattingbe.extendablechattingbe.repository.RoomRepository;
import com.extendablechattingbe.extendablechattingbe.dto.request.PageRequestDTO;
import com.extendablechattingbe.extendablechattingbe.dto.request.RoomRequest;
import com.extendablechattingbe.extendablechattingbe.dto.response.PageResponse;
import com.extendablechattingbe.extendablechattingbe.dto.response.RoomResponse;

import java.util.function.Function;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.extendablechattingbe.extendablechattingbe.common.ResponseMessages.ROOM_NOT_FOUND_ERROR;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;

    @Transactional
    public Room register(RoomRequest request) {
        Room room = new Room(request.getRoomName());
        return roomRepository.save(room);

    }

    public PageResponse getList(PageRequestDTO request) {

        Pageable pageable = PageRequest.of(request.getPage() - 1, request.getSize(),
            Sort.by("id").descending());
        Page<Room> result = roomRepository.findAll(pageable);
        Function<Room, RoomResponse> fn = (entity -> RoomResponse.from(entity));
        return new PageResponse(result, fn);

    }

    public RoomResponse getOne(Long id) {
        Room findRoom = roomRepository.findById(id)
            .orElseThrow(() -> new CustomException(ROOM_NOT_FOUND_ERROR));
        RoomResponse response = RoomResponse.builder()
            .id(findRoom.getId())
            .roomName(findRoom.getRoomName())
            .build();

        return response;
    }

    @Transactional
    public void delete(Long id) {
        Room deleteRoom = roomRepository.findById(id)
            .orElseThrow(() -> new CustomException(ROOM_NOT_FOUND_ERROR));
        roomRepository.delete(deleteRoom);
    }

    public Room validateAndFindRoomById(Long roomId) {
        return roomRepository.findById(roomId)
                .orElseThrow(() -> new CustomException(ROOM_NOT_FOUND_ERROR));
    }
}
