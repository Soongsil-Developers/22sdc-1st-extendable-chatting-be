package com.extendablechattingbe.extendablechattingbe.service;

import static com.extendablechattingbe.extendablechattingbe.common.ResponseMessage.*;

import com.extendablechattingbe.extendablechattingbe.common.error.exception.NotFoundException;
import com.extendablechattingbe.extendablechattingbe.domain.Member;
import com.extendablechattingbe.extendablechattingbe.domain.Message;
import com.extendablechattingbe.extendablechattingbe.domain.Room;
import com.extendablechattingbe.extendablechattingbe.dto.response.MessageResponseDTO;
import com.extendablechattingbe.extendablechattingbe.repository.MemberRepository;
import com.extendablechattingbe.extendablechattingbe.repository.MessageRepository;
import com.extendablechattingbe.extendablechattingbe.repository.RoomRepository;
import com.extendablechattingbe.extendablechattingbe.dto.request.PageRequestDTO;
import com.extendablechattingbe.extendablechattingbe.dto.request.RoomRequest;
import com.extendablechattingbe.extendablechattingbe.dto.response.PageResponse;
import com.extendablechattingbe.extendablechattingbe.dto.response.RoomResponse;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

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

    private final RoomRepository roomRepository;
    private final MessageRepository messageRepository;
    private final MemberRepository memberRepository;


    @Transactional(readOnly = false)
    public Long register(RoomRequest request) {
        Room room = Room.builder()
            .roomName(request.getRoomName())
            .build();

        roomRepository.save(room);
        return room.getId();

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
            .orElseThrow(() -> new IllegalArgumentException("없는 방입니다."));
        RoomResponse response = RoomResponse.builder()
            .id(findRoom.getId())
            .roomName(findRoom.getRoomName())
            .build();

        return response;
    }

    @Transactional
    public void delete(Long id) {
        Room deleteRoom = roomRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("없는 방입니다."));
        roomRepository.delete(deleteRoom);
    }


    /**
     * Use RoomResponse.from(Room) for changing Room to RoomResponse
     * **/
    @Deprecated
    public RoomResponse entityToResponse(Room entity) {
        RoomResponse response = RoomResponse.builder()
            .id(entity.getId())
            .roomName(entity.getRoomName())
            .build();

        return response;
    }

    public List<MessageResponseDTO> getMessageHistory(Long roomId, Long memberId, PageRequestDTO pageRequest) {
        Member member = getMember(memberId);
        Room room = getRoom(roomId);
        Pageable pageable = PageRequest.of(pageRequest.getPage() - 1, pageRequest.getSize(), Sort.by("id").descending());
        Optional<LocalDateTime> optionalEnterDate = memberRepository.findEnterDate(room, member);

        if (optionalEnterDate.isPresent()) {  // == 이전 방문 이력이 있는 사용자
            LocalDateTime enterDate = optionalEnterDate.get();
            List<Message> messages = messageRepository.findAllByEnterDate(room, enterDate, pageable);
            return messages.stream()
                    .map(MessageResponseDTO::from)
                    .collect(Collectors.toList());
        }
        return null;  // == 처음 방문한 사용자
    }

    private Member getMember(Long id) {
        return memberRepository.findById(id)
            .orElseThrow(() -> new NotFoundException(MEMBER_NOT_FOUND_ERROR));
    }

    private Room getRoom(Long id) {
        return roomRepository.findById(id)
            .orElseThrow(() -> new NotFoundException(ROOM_NOT_FOUND_ERROR));
    }
}
