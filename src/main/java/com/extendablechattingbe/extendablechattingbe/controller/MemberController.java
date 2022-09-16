package com.extendablechattingbe.extendablechattingbe.controller;

import com.extendablechattingbe.extendablechattingbe.common.response.SimpleResponseDTO;
import com.extendablechattingbe.extendablechattingbe.domain.Member;
import com.extendablechattingbe.extendablechattingbe.domain.RoomMember;
import com.extendablechattingbe.extendablechattingbe.dto.request.MemberRequest;
import com.extendablechattingbe.extendablechattingbe.dto.response.MemberResponse;
import com.extendablechattingbe.extendablechattingbe.dto.response.MessageResponse;
import com.extendablechattingbe.extendablechattingbe.dto.response.PageResponse;
import com.extendablechattingbe.extendablechattingbe.dto.response.RoomMemberResponse;
import com.extendablechattingbe.extendablechattingbe.dto.response.RoomResponse;
import com.extendablechattingbe.extendablechattingbe.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "회원 가입", description = "닉네임을 파리미터로 입력하면, 회원가입을 할 수 있습니다.")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "회원을 성공적으로 등록"),
    })
    @PostMapping("/members")
    public ResponseEntity<MemberResponse> signUp(@RequestBody MemberRequest request) {
        Member member = memberService.signUp(request);
        return ResponseEntity.created(URI.create("/members/" + member.getId()))
            .body(new MemberResponse(member.getId(), member.getNickname()));
    }

    @Operation(summary = "특정 멤버 정보 흭득", description = "특정 멤버에 대한 아이디를 파라미터로 입력하면, 특정 멤버에 대한 정보를 가져올 수 있습니다.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "특정 메세지를 성공적으로 획득", content = @Content(schema = @Schema(implementation = MemberResponse.class))),
        @ApiResponse(responseCode = "404", description = "입력한 정보가 데이터 베이스에 저장되어있지 않습니다.", content = @Content(schema = @Schema(implementation = SimpleResponseDTO.class)))
    })
    @GetMapping("/members/{memberId}")
    public ResponseEntity<MemberResponse> getMember(
        @Parameter(name = "memberId", description = "멤버의 아이디", in = ParameterIn.PATH) @PathVariable Long memberId) {
        MemberResponse response = memberService.getMemberOne(memberId);
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "특정 멤버 삭제", description = "특정 멤버에 대한 아이디를 파라미터로 입력하면, 특정 멤버를 삭제할 수 있습니다.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "특정 메세지를 성공적으로 삭제"),
        @ApiResponse(responseCode = "404", description = "입력한 정보가 데이터 베이스에 저장되어있지 않습니다.", content = @Content(schema = @Schema(implementation = SimpleResponseDTO.class)))
    })
    @DeleteMapping("/members/{memberId}")
    public ResponseEntity deleteMember(
        @Parameter(name = "memberId", description = "멤버의 아이디", in = ParameterIn.PATH) @PathVariable Long memberId) {
        memberService.deleteMember(memberId);
        return ResponseEntity.noContent().build();
    }


    @Operation(summary = "멤버가 방에 입장", description = "특정 멤버 및 특정 방에 대한 아이디를 파라미터로 입력하면, 특정 멤버가 특정 방에 입장 합니다.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "특정 메세지를 성공적으로 획득"),
        @ApiResponse(responseCode = "404", description = "입력한 정보가 데이터 베이스에 저장되어있지 않습니다.", content = @Content(schema = @Schema(implementation = SimpleResponseDTO.class)))
    })
    @PostMapping("/members/{memberId}/rooms/{roomId}")
    public ResponseEntity<RoomMemberResponse> joinRoom(
        @Parameter(name = "memberId", description = "멤버의 아이디", in = ParameterIn.PATH) @PathVariable("memberId") Long memberId,
        @Parameter(name = "roomId", description = "방의 아이디", in = ParameterIn.PATH) @PathVariable("roomId") Long roomId) {
        RoomMember roomMember = memberService.joinTheRoom(memberId, roomId);
        return ResponseEntity.created(URI.create("/members/" + memberId + "/rooms/" + roomId))
            .body(new RoomMemberResponse(roomMember.getId(), roomMember.getMember().getId(),
                roomMember.getRoom().getId()));
    }

// 별 의미 없어서 삭제(멤버가 속한 방들을 찾는것으로 대체 가능)
//    @GetMapping("/members/{memberId}/rooms/{roomId}")
//    public ResponseEntity<RoomMemberResponse> findRoomMember(
//        @PathVariable("memberId") Long memberId, @PathVariable("roomId") Long roomId) {
//        RoomMemberResponse response = memberService.findRoomMember(memberId, roomId);
//        return ResponseEntity.ok().body(response);
//
//    }


    @Operation(summary = "특정 멤버가 속한 방 목록 획득", description = "특정 멤버에 대한 아이디를 파리미터로 입력하면, 특정 멤버가 속한 방 목록을 얻을 수 있습니다.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "특정 멤버가 속한 방 목록 획득 성공", content = @Content(schema = @Schema(implementation = PageResponse.class))),
        @ApiResponse(responseCode = "404", description = "입력한 정보가 데이터 베이스에 저장되어있지 않습니다.", content = @Content(schema = @Schema(implementation = SimpleResponseDTO.class)))
    })
    //멤버가 속한 방 찾기
    @GetMapping("/members/{memberId}/rooms")
    public ResponseEntity<List<RoomResponse>> findRoomFromMember(
        @Parameter(name = "memberId", description = "멤버의 아이디", in = ParameterIn.PATH) @PathVariable("memberId") Long memberId) {
        List<RoomResponse> roomFromMember = memberService.findRoomFromMember(memberId);
        return ResponseEntity.ok().body(roomFromMember);

    }


    @Operation(summary = "특정 멤버가 방을 나감", description = "특정 멤버 및 특정 방에 대한 아이디를 파라미터로 입력하면, 특정 멤버가 특정 방을 나갈 수 있습니다.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "특정 멤버가 성공적으로 방을 나갔습니다."),
        @ApiResponse(responseCode = "404", description = "입력한 정보가 데이터 베이스에 저장되어있지 않습니다.", content = @Content(schema = @Schema(implementation = SimpleResponseDTO.class)))
    })
    @DeleteMapping("/members/{memberId}/rooms/{roomId}")
    public ResponseEntity leaveRoom(
        @Parameter(name = "memberid", description = "멤버의 아이디", in = ParameterIn.PATH) @PathVariable("memberId") Long memberId,
        @Parameter(name = "roomid", description = "방의 아이디", in = ParameterIn.PATH) @PathVariable("roomId") Long roomId) {
        memberService.leaveTheRoom(memberId, roomId);
        return ResponseEntity.noContent().build();
    }
}
