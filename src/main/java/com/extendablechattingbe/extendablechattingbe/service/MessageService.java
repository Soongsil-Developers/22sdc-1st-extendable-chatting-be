package com.extendablechattingbe.extendablechattingbe.service;

import static com.extendablechattingbe.extendablechattingbe.common.ResponseMessages.MEMBER_NOT_FOUND_ERROR;
import static com.extendablechattingbe.extendablechattingbe.common.ResponseMessages.MESSAGE_NOT_FOUND_ERROR;
import static com.extendablechattingbe.extendablechattingbe.common.ResponseMessages.ROOM_NOT_FOUND_ERROR;

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
    private final MemberRepository memberRepository;
    private final RoomRepository roomRepository;


    @Transactional
    public Message registerMessage(Long roomId, MessageRequestDTO messageRequestDTO) {

        Member member = memberRepository.findById(messageRequestDTO.getMemberId())
            .orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND_ERROR));

        Room room = roomRepository.findById(roomId)
            .orElseThrow(() -> new CustomException(ROOM_NOT_FOUND_ERROR));

        Message message = Message.builder()
            .message(messageRequestDTO.getMessage())
            .room(room)
            .member(member)
            .type(messageRequestDTO.getType())
            .build();
        messageRepository.save(message);
        return message;
    }

    public PageResponse getMessageList(Long roomId, PageRequestDTO pageRequestDTO) {

        PageRequest request = PageRequest.of(pageRequestDTO.getPage() - 1,
            pageRequestDTO.getSize());
        Room room = roomRepository.findById(roomId)
            .orElseThrow(() -> new CustomException(ROOM_NOT_FOUND_ERROR));
        Page<Message> result = messageRepository.findByRoom(room, request);

        Function<Message, MessageResponseDTO> fn = (entity -> MessageResponseDTO.from(entity));
        return new PageResponse(result, fn);

    }

    @Transactional
    public void DeleteMessage(Long messageId) {
        Message message = messageRepository.findById(messageId)
            .orElseThrow(() -> new CustomException(MESSAGE_NOT_FOUND_ERROR));
        messageRepository.delete(message);
        return;
    }

    public MessageResponseDTO getOne(Long chatId) {
        Message message = messageRepository.findById(chatId)
            .orElseThrow(() -> new CustomException(MESSAGE_NOT_FOUND_ERROR));

        return MessageResponseDTO.from(message);
    }

}
