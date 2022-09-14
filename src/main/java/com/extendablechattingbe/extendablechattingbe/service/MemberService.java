package com.extendablechattingbe.extendablechattingbe.service;

import com.extendablechattingbe.extendablechattingbe.common.exception.CustomException;
import com.extendablechattingbe.extendablechattingbe.domain.Member;
import com.extendablechattingbe.extendablechattingbe.domain.Room;
import com.extendablechattingbe.extendablechattingbe.domain.RoomMember;
import com.extendablechattingbe.extendablechattingbe.dto.request.MemberRequest;
import com.extendablechattingbe.extendablechattingbe.dto.response.MemberResponse;
import com.extendablechattingbe.extendablechattingbe.dto.response.RoomResponse;
import com.extendablechattingbe.extendablechattingbe.repository.MemberRepository;
import com.extendablechattingbe.extendablechattingbe.repository.RoomMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.extendablechattingbe.extendablechattingbe.common.ResponseMessages.MEMBER_NOT_FOUND_ERROR;
import static com.extendablechattingbe.extendablechattingbe.common.ResponseMessages.ROOM_MEMBER_NOT_FOUND_ERROR;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final RoomMemberRepository roomMemberRepository;
    private final MemberRepository memberRepository;
    private final RoomService roomService;


    @Transactional
    public Member signUp(MemberRequest request) {
        Member member = new Member(request.getNickname());
        memberRepository.save(member);
        return member;
    }

    public MemberResponse getMemberOne(Long memberId) {
        Member member = validateAndFindMemberById(memberId);

        MemberResponse response = new MemberResponse(member.getId(), member.getNickname());
        return response;
    }

    @Transactional
    public void deleteMember(Long memberId) {
        Member member = validateAndFindMemberById(memberId);

        memberRepository.delete(member);
    }


    @Transactional
    public RoomMember joinTheRoom(Long memberId, Long roomId) {

        Member member = validateAndFindMemberById(memberId);

        Room room = roomService.validateAndFindRoomById(roomId);

        RoomMember roomMember = new RoomMember(member, room);

        roomMemberRepository.save(roomMember);
        return roomMember;
    }


    @Transactional
    public void leaveTheRoom(Long memberId, Long roomId) {

        Member member = validateAndFindMemberById(memberId);

        Room room = roomService.validateAndFindRoomById(roomId);

        RoomMember findRoomMember = roomMemberRepository.findByRoomAndMember(room, member)
            .orElseThrow(() -> new CustomException(ROOM_MEMBER_NOT_FOUND_ERROR));

        roomMemberRepository.delete(findRoomMember);

    }

    public List<RoomResponse> findRoomFromMember(Long memberId) {
        List<RoomResponse> roomList = new ArrayList<>();
        Member member = validateAndFindMemberById(memberId);
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

    public Member validateAndFindMemberById(Long memberId) {
        return memberRepository.findById(memberId)
            .orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND_ERROR));
    }
}
