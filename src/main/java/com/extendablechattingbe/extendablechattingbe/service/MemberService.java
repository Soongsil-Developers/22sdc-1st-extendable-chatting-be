package com.extendablechattingbe.extendablechattingbe.service;

import static com.extendablechattingbe.extendablechattingbe.common.ResponseMessages.MEMBER_NOT_FOUND_ERROR;
import static com.extendablechattingbe.extendablechattingbe.common.ResponseMessages.ROOM_MEMBER_NOT_FOUND_ERROR;
import static com.extendablechattingbe.extendablechattingbe.common.ResponseMessages.ROOM_NOT_FOUND_ERROR;

import com.extendablechattingbe.extendablechattingbe.common.exception.CustomException;
import com.extendablechattingbe.extendablechattingbe.domain.Member;
import com.extendablechattingbe.extendablechattingbe.domain.Room;
import com.extendablechattingbe.extendablechattingbe.domain.RoomMember;
import com.extendablechattingbe.extendablechattingbe.dto.request.MemberRequest;
import com.extendablechattingbe.extendablechattingbe.dto.response.MemberResponse;
import com.extendablechattingbe.extendablechattingbe.dto.response.RoomMemberResponse;
import com.extendablechattingbe.extendablechattingbe.dto.response.RoomResponse;
import com.extendablechattingbe.extendablechattingbe.repository.MemberRepository;
import com.extendablechattingbe.extendablechattingbe.repository.RoomMemberRepository;
import com.extendablechattingbe.extendablechattingbe.repository.RoomRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final RoomMemberRepository roomMemberRepository;
    private final MemberRepository memberRepository;
    private final RoomRepository roomRepository;


    @Transactional
    public Member signUp(MemberRequest request) {
        Member member = new Member(request.getNickname());
        memberRepository.save(member);
        return member;
    }

    public MemberResponse getMemberOne(Long memberId) {
        Member member = memberRepository.findById(memberId)
            .orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND_ERROR));

        MemberResponse response = new MemberResponse(member.getId(), member.getNickname());
        return response;
    }

    @Transactional
    public void deleteMember(Long memberId) {
        Member member = memberRepository.findById(memberId)
            .orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND_ERROR));

        memberRepository.delete(member);
    }


    @Transactional
    public RoomMember joinTheRoom(Long memberId, Long roomId) {

        Member member = getMemberFromId(memberId);

        Room room = getRoomFromId(roomId);

        RoomMember roomMember = new RoomMember(member, room);

        roomMemberRepository.save(roomMember);
        return roomMember;
    }


    @Transactional
    public void leaveTheRoom(Long memberId, Long roomId) {

        Member member = getMemberFromId(memberId);

        Room room = getRoomFromId(roomId);

        RoomMember findRoomMember = roomMemberRepository.findByRoomAndMember(room, member)
            .orElseThrow(() -> new CustomException(ROOM_MEMBER_NOT_FOUND_ERROR));

        roomMemberRepository.delete(findRoomMember);

        return;
    }

    public List<RoomResponse> findRoomFromMember(Long memberId) {
        List<RoomResponse> roomList = new ArrayList<>();
        Member member = getMemberFromId(memberId);
        List<RoomMember> RoomFromMember = roomMemberRepository.findByMember(member);
        RoomFromMember.forEach(roomMember -> {
            roomList.add(
                new RoomResponse(roomMember.getRoom().getId(), roomMember.getRoom().getRoomName()));
        });
        return roomList;
    }

//별 의미 없어서 삭제(멤버가 속한 방들을 찾는것으로 대체 가능)
//    public RoomMemberResponse findRoomMember(Long memberId, Long roomId) {
//        Member member = getMemberFromId(memberId);
//
//        Room room = getRoomFromId(roomId);
//
//        RoomMember roomMember = roomMemberRepository.findByRoomAndMember(room, member)
//            .orElseThrow(() -> new CustomException(ROOM_MEMBER_NOT_FOUND_ERROR));
//
//        return new RoomMemberResponse(roomMember.getId(), memberId, roomId);
//    }


    private Member getMemberFromId(Long memberId) {
        Member member = memberRepository.findById(memberId)
            .orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND_ERROR));
        return member;
    }

    private Room getRoomFromId(Long roomId) {
        Room room = roomRepository.findById(roomId)
            .orElseThrow(() -> new CustomException(ROOM_NOT_FOUND_ERROR));
        return room;
    }


}
