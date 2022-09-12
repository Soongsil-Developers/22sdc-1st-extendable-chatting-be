package com.extendablechattingbe.extendablechattingbe.service;

import static com.extendablechattingbe.extendablechattingbe.common.ResponseMessages.MEMBER_NOT_FOUND_ERROR;
import static com.extendablechattingbe.extendablechattingbe.common.ResponseMessages.ROOM_NOT_FOUND_ERROR;

import com.extendablechattingbe.extendablechattingbe.common.exception.CustomException;
import com.extendablechattingbe.extendablechattingbe.domain.Member;
import com.extendablechattingbe.extendablechattingbe.domain.Message;
import com.extendablechattingbe.extendablechattingbe.domain.Room;
import com.extendablechattingbe.extendablechattingbe.dto.request.MessageRequestDto;
import com.extendablechattingbe.extendablechattingbe.repository.MemberRepository;
import com.extendablechattingbe.extendablechattingbe.repository.MessageRepository;
import com.extendablechattingbe.extendablechattingbe.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MemberRepository memberRepository;
    private final RoomRepository roomRepository;
    private final MessageRepository messageRepository;

    @Transactional
    public void saveMessage(MessageRequestDto messageRequestDto) {
        Member member = memberRepository.findByNickname(messageRequestDto.getNickname())
            .orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND_ERROR));

        Room room = roomRepository.findById(messageRequestDto.getRoomId())
            .orElseThrow(() -> new CustomException(ROOM_NOT_FOUND_ERROR));

        Message message = Message.builder()
            .message(messageRequestDto.getMessage())
            .room(room)
            .member(member)
            .type(messageRequestDto.getType())
            .build();

        messageRepository.save(message);
    }

}
