package com.extendablechattingbe.extendablechattingbe.controller;

import com.extendablechattingbe.extendablechattingbe.domain.Member;
import com.extendablechattingbe.extendablechattingbe.domain.RoomMember;
import com.extendablechattingbe.extendablechattingbe.dto.request.MemberRequest;
import com.extendablechattingbe.extendablechattingbe.dto.response.MemberResponse;
import com.extendablechattingbe.extendablechattingbe.dto.response.RoomMemberResponse;
import com.extendablechattingbe.extendablechattingbe.dto.response.RoomResponse;
import com.extendablechattingbe.extendablechattingbe.service.MemberService;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 멤버가 방에 입장, 퇴장 할 수 있는 API
 */


@Tag(name = "members", description = "채팅 사용자 API")
@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/members")
    public ResponseEntity<Member> signUp(@RequestBody MemberRequest request) {
        Member member = memberService.signUp(request);
        return ResponseEntity.created(URI.create("/members/" + member.getId())).body(member);
    }

    @GetMapping("/members/{memberId}")
    public ResponseEntity<MemberResponse> getMember(@PathVariable Long memberId) {
        MemberResponse response = memberService.getMemberOne(memberId);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/members/{memberId}")
    public ResponseEntity deleteMember(@PathVariable Long memberId) {
        memberService.deleteMember(memberId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/members/{memberId}/rooms/{roomId}")
    public ResponseEntity<RoomMember> joinRoom(@PathVariable("memberId") Long memberId,
        @PathVariable("roomId") Long roomId) {
        RoomMember roomMember = memberService.joinTheRoom(memberId, roomId);
        return ResponseEntity.created(URI.create("/members/" + memberId + "/rooms/" + roomId))
            .body(roomMember);
    }

// 별 의미 없어서 삭제(멤버가 속한 방들을 찾는것으로 대체 가능)
//    @GetMapping("/members/{memberId}/rooms/{roomId}")
//    public ResponseEntity<RoomMemberResponse> findRoomMember(
//        @PathVariable("memberId") Long memberId, @PathVariable("roomId") Long roomId) {
//        RoomMemberResponse response = memberService.findRoomMember(memberId, roomId);
//        return ResponseEntity.ok().body(response);
//
//    }

    //멤버가 속한 방 찾기
    @GetMapping("/members/{memberId}/rooms")
    public ResponseEntity<List<RoomResponse>> findRoomFromMember(
        @PathVariable("memberId") Long memberId) {
        List<RoomResponse> roomFromMember = memberService.findRoomFromMember(memberId);
        return ResponseEntity.ok().body(roomFromMember);

    }

    @DeleteMapping("/members/{memberId}/rooms/{roomId}")
    public ResponseEntity leaveRoom(@PathVariable("memberId") Long memberId,
        @PathVariable("roomId") Long roomId) {
        memberService.leaveTheRoom(memberId, roomId);
        return ResponseEntity.ok().build();
    }
}
