package com.extendablechattingbe.extendablechattingbe.service;

import static com.extendablechattingbe.extendablechattingbe.common.ResponseMessages.MEMBER_NOT_FOUND_ERROR;
import static com.extendablechattingbe.extendablechattingbe.common.ResponseMessages.MESSAGE_NOT_FOUND_ERROR;
import static com.extendablechattingbe.extendablechattingbe.common.ResponseMessages.ROOM_NOT_FOUND_ERROR;

import com.extendablechattingbe.extendablechattingbe.common.exception.CustomException;
import com.extendablechattingbe.extendablechattingbe.domain.Member;
import com.extendablechattingbe.extendablechattingbe.domain.Message;
import com.extendablechattingbe.extendablechattingbe.domain.Room;
import com.extendablechattingbe.extendablechattingbe.dto.request.MessageRequestDto;
import com.extendablechattingbe.extendablechattingbe.dto.request.PageRequestDTO;
import com.extendablechattingbe.extendablechattingbe.dto.response.MessageResponse;
import com.extendablechattingbe.extendablechattingbe.dto.response.PageResponse;
import com.extendablechattingbe.extendablechattingbe.repository.MemberRepository;
import com.extendablechattingbe.extendablechattingbe.repository.MessageRepository;
import com.extendablechattingbe.extendablechattingbe.repository.RoomRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.function.Function;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MessageService {

    private final MessageRepository messageRepository;
    private final RoomRepository roomRepository;
    private final ObjectMapper objectMapper;

// 웹 소켓과 중복
//    @Transactional
//    public Message registermessage(Long roomId, MessageRequestDto MessageRequestDto) {
//
//        Member member = memberRepository.findByNickname(MessageRequestDto.getNickname())
//            .orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND_ERROR));
//
//        Room room = roomRepository.findById(roomId)
//            .orElseThrow(() -> new CustomException(ROOM_NOT_FOUND_ERROR));
//
//        Message message = Message.builder()
//            .message(MessageRequestDto.getMessage())
//            .room(room)
//            .member(member)
//            .type(MessageRequestDto.getType())
//            .build();
//        messageRepository.save(message);
//        return message;
//    }

    public PageResponse getMessagelist(Long roomId, PageRequestDTO pageRequestDTO) {

        PageRequest request = PageRequest.of(pageRequestDTO.getPage() - 1,
            pageRequestDTO.getSize());
        Room room = roomRepository.findById(roomId)
            .orElseThrow(() -> new CustomException(ROOM_NOT_FOUND_ERROR));
        Page<Message> result = messageRepository.findByRoom(room, request);

        Function<Message, MessageResponse> fn = (entity -> MessageResponse.from(entity));
        return new PageResponse(result, fn);

    }

    @Transactional
    public void deleteMessage(Long messageId) {
        Message message = messageRepository.findById(messageId)
            .orElseThrow(() -> new CustomException(MESSAGE_NOT_FOUND_ERROR));
        messageRepository.delete(message);
        return;
    }

    public MessageResponse getOne(Long chatId) {
        Message message = messageRepository.findById(chatId)
            .orElseThrow(() -> new CustomException(MESSAGE_NOT_FOUND_ERROR));

        return MessageResponse.builder()
            .id(message.getId())
            .message(message.getMessage())
            .roomId(message.getRoom().getId())
            .memberId(message.getMember().getId())
            .type(message.getType())
            .build();
    }

//  웹 소켓과 중복    
//    public void sendMessage(Long roomId, Message message) {
//        Room room = roomRepository.findById(roomId)
//            .orElseThrow(() -> new CustomException(ROOM_NOT_FOUND_ERROR));
//        room.sendMessage(message,objectMapper);
//    }
}
