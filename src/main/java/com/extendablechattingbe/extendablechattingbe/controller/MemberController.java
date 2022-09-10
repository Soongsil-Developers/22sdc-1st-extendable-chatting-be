package com.extendablechattingbe.extendablechattingbe.controller;

import com.extendablechattingbe.extendablechattingbe.domain.Member;
import com.extendablechattingbe.extendablechattingbe.domain.RoomMember;
import com.extendablechattingbe.extendablechattingbe.dto.request.MemberRequest;
import com.extendablechattingbe.extendablechattingbe.dto.response.MemberResponse;
import com.extendablechattingbe.extendablechattingbe.dto.response.RoomMemberResponse;
import com.extendablechattingbe.extendablechattingbe.service.MemberService;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.net.URI;
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


@Tag(name="members",description="채팅 사용자 API")
@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/members/signup")
    public ResponseEntity<Member> signUp(@RequestBody MemberRequest request){
        Member member = memberService.signUp(request);
        return ResponseEntity.created(URI.create("/members/"+member.getId())).body(member);
    }

    @GetMapping("/members/{memberId}")
    public ResponseEntity<MemberResponse> getMember(@PathVariable Long memberId){
        MemberResponse response = memberService.getMemberOne(memberId);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/members/{memberId}/rooms/{roomId}")
    public ResponseEntity<RoomMember> JoinRoom(@PathVariable("memberId")Long memberId, @PathVariable("roomId")Long roomId){
        RoomMember roomMember = memberService.JoinTheRoom(memberId, roomId);
        return ResponseEntity.created(URI.create("/members/"+memberId+"/rooms/"+roomId)).body(roomMember);
    }

    @GetMapping("/members/{memberId}/rooms/{roomId}")
    public ResponseEntity<RoomMemberResponse> findRoomMember(@PathVariable("memberId")Long memberId, @PathVariable("roomId")Long roomId){
        RoomMemberResponse response = memberService.findRoomMember(memberId, roomId);
        return ResponseEntity.ok().body(response);

    }

    @DeleteMapping("/members/{memberId}/rooms/{roomId}")
    public ResponseEntity<Object> LeaveRoom(@PathVariable("memberId")Long memberId, @PathVariable("roomId")Long roomId){
        memberService.LeaveTheRoom(memberId, roomId);
        return ResponseEntity.ok().build();
    }
}
