package com.extendablechattingbe.extendablechattingbe.service;

import com.extendablechattingbe.extendablechattingbe.domain.Room;
import com.extendablechattingbe.extendablechattingbe.repository.RoomRepository;
import com.extendablechattingbe.extendablechattingbe.request.PageRequestDTO;
import com.extendablechattingbe.extendablechattingbe.request.RoomRequest;
import com.extendablechattingbe.extendablechattingbe.response.PageResponse;
import com.extendablechattingbe.extendablechattingbe.response.RoomResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository repository;

    @Transactional(readOnly = false)
    public Long register(RoomRequest request){
        Room room = Room.builder()
                .roomName(request.getRoomName())
                .build();

        repository.save(room);
        return room.getId();

    }

    public PageResponse getList(PageRequestDTO request){

       Pageable pageable =PageRequest.of(request.getPage()-1,request.getSize(), Sort.by("room_id").descending());
        Page<Room> all = repository.findAll(pageable);
        return PageResponse.<Room>builder()
                .dtoList(all.getContent())
                .page(all.getNumber())
                .size(all.getSize())
                .totalSize(all.getTotalPages())
                .build();


    }

    public RoomResponse getOne(Long id){
        Room findRoom = repository.findById(id).orElseThrow(() -> new IllegalArgumentException("없는 방입니다."));
        RoomResponse response = RoomResponse.builder()
                .id(findRoom.getId())
                .roomName(findRoom.getRoomName())
                .build();

        return  response;
    }

    public void delete(Long id){
        Room deleteRoom = repository.findById(id).orElseThrow(() -> new IllegalArgumentException("없는 방입니다."));
        repository.delete(deleteRoom);
    }



}
