package com.extendablechattingbe.extendablechattingbe.controller;

import com.extendablechattingbe.extendablechattingbe.common.response.SimpleResponseDTO;
import com.extendablechattingbe.extendablechattingbe.domain.Room;
import com.extendablechattingbe.extendablechattingbe.dto.request.PageRequestDTO;
import com.extendablechattingbe.extendablechattingbe.dto.request.RoomRequest;
import com.extendablechattingbe.extendablechattingbe.dto.response.MessageResponse;
import com.extendablechattingbe.extendablechattingbe.dto.response.PageResponse;
import com.extendablechattingbe.extendablechattingbe.dto.response.RoomResponse;
import com.extendablechattingbe.extendablechattingbe.service.RoomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

@Tag(name = "rooms", description = "채팅방 API")
@RestController
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;

    @Operation(summary = "방 생성", description = "방을 생성할 수 있습니다.")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "성공적으로 방 생성")
    })
    @PostMapping("/rooms")
    public ResponseEntity<Room> register(@RequestBody @Valid RoomRequest request) {
        Room room = roomService.register(request);
        return ResponseEntity.created(URI.create("/rooms/" + room.getId())).body(room);
    }

    @Operation(summary = "페이징 처리된 방 목록 획득", description = "페이지,사이즈를 입력하면 방 목록을 페이징 처리 하여 얻을 수 있습니다.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "방목록을 성공적으로 획득", content = @Content(schema = @Schema(implementation = PageResponse.class)))
    })
    @GetMapping("/rooms")
    public ResponseEntity<PageResponse> getList(PageRequestDTO request) {
        return ResponseEntity.ok().body(roomService.getList(request));
    }


    @Operation(summary = "특정 방 정보 획득", description = "방의 아이디를 파라미터로 넣으면, 특정 방에 대한 정보를 얻을 수 있습니다.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "특정 방에 대한 정보를 성공적으로 획득", content = @Content(schema = @Schema(implementation = RoomResponse.class))),
        @ApiResponse(responseCode = "404", description = "입력한 방의 아이디가 데이터 베이스에 저장되어있지 않습니다.",content = @Content(schema = @Schema(implementation = SimpleResponseDTO.class)))
    })
    @GetMapping("/rooms/{roomId}")
    private ResponseEntity<RoomResponse> getOne(
        @Parameter(name = "roomId", description = "방 의 아이디", in = ParameterIn.PATH) @PathVariable Long roomId) {
        return ResponseEntity.ok().body(roomService.getOne(roomId));
    }

    @Operation(summary = "특정 방 삭제", description = "방의 아이디를 파라미터로 넣으면, 특정 방을 삭제 할 수 있습니다.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "특정 방을 성공적으로 삭제"),
        @ApiResponse(responseCode = "404", description = "입력한 방에 대한 정보가 데이터 베이스에 저장되어있지 않습니다.",content = @Content(schema = @Schema(implementation = SimpleResponseDTO.class)))
    })
    @DeleteMapping("/rooms/{roomId}")
    private ResponseEntity delete(
        @Parameter(name = "roomId", description = "방 의 아이디", in = ParameterIn.PATH) @PathVariable Long roomId) {
        roomService.delete(roomId);
        return ResponseEntity.noContent().build();
    }


}
