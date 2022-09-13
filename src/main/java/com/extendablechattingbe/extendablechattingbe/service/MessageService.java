package com.extendablechattingbe.extendablechattingbe.service;

import com.extendablechattingbe.extendablechattingbe.common.exception.CustomException;
import com.extendablechattingbe.extendablechattingbe.domain.Member;
import com.extendablechattingbe.extendablechattingbe.domain.Message;
import com.extendablechattingbe.extendablechattingbe.domain.Room;
import com.extendablechattingbe.extendablechattingbe.dto.request.MessageRequestDTO;
import com.extendablechattingbe.extendablechattingbe.dto.request.PageRequestDTO;
import com.extendablechattingbe.extendablechattingbe.dto.response.MessageResponseDTO;
import com.extendablechattingbe.extendablechattingbe.dto.response.PageResponse;
import com.extendablechattingbe.extendablechattingbe.repository.MemberRepository;
import com.extendablechattingbe.extendablechattingbe.repository.MessageRepository;
import com.extendablechattingbe.extendablechattingbe.repository.RoomRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;


import java.io.IOException;
import java.util.function.Function;

import static com.extendablechattingbe.extendablechattingbe.common.ResponseMessages.*;
@Log4j2
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MessageService {

    private final MemberRepository memberRepository;
    private final RoomRepository roomRepository;
    private final MessageRepository messageRepository;

    private final ObjectMapper objectMapper;

    @Transactional
    public void saveMessage(MessageRequestDTO messageRequestDTO) {
        Member member = memberRepository.findById(messageRequestDTO.getMemberId())
            .orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND_ERROR));

        Room room = roomRepository.findById(messageRequestDTO.getRoomId())
            .orElseThrow(() -> new CustomException(ROOM_NOT_FOUND_ERROR));

        Message message = Message.builder()
            .message(messageRequestDTO.getMessage())
            .room(room)
            .member(member)
            .type(messageRequestDTO.getType())
            .build();

        messageRepository.save(message);
    }


    public PageResponse getMessagelist(Long roomId, PageRequestDTO pageRequestDTO) {

        PageRequest request = PageRequest.of(pageRequestDTO.getPage() - 1,
            pageRequestDTO.getSize());
        Room room = roomRepository.findById(roomId)
            .orElseThrow(() -> new CustomException(ROOM_NOT_FOUND_ERROR));
        Page<Message> result = messageRepository.findByRoom(room, request);

        Function<Message, MessageResponseDTO> fn = (entity -> MessageResponseDTO.from(entity));
        return new PageResponse(result, fn);

    }

    @Transactional
    public void deleteMessage(Long messageId) {
        Message message = messageRepository.findById(messageId)
            .orElseThrow(() -> new CustomException(MESSAGE_NOT_FOUND_ERROR));
        messageRepository.delete(message);
    }

    public MessageResponseDTO getOne(Long chatId) {
        Message message = messageRepository.findById(chatId)
            .orElseThrow(() -> new CustomException(MESSAGE_NOT_FOUND_ERROR));

        return MessageResponseDTO.from(message);
    }

    public<T> void sendMessage(WebSocketSession session, T message) {
        try{
            session.sendMessage(new TextMessage(objectMapper.writeValueAsString(message)));
        }catch (IOException e){
            e.printStackTrace();
            log.error(e.getMessage(),e);
        }
    }
}
