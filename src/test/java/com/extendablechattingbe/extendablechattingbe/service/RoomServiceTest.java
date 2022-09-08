package com.extendablechattingbe.extendablechattingbe.service;

import com.extendablechattingbe.extendablechattingbe.domain.Room;
import com.extendablechattingbe.extendablechattingbe.dto.request.RoomRequest;
import com.extendablechattingbe.extendablechattingbe.dto.response.RoomResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.AggregateWith;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.aggregator.ArgumentsAggregationException;
import org.junit.jupiter.params.aggregator.ArgumentsAggregator;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@ExtendWith(MockitoExtension.class)
class RoomServiceTest {
    @Mock
    private RoomService roomService;
    @ParameterizedTest(name="{index} {displayName} message={0}")
    @DisplayName("채팅방 입장")
    @CsvSource( {"Room1"})
    //@NullSource
    void register(@AggregateWith(RoomRequestAggregator.class)RoomRequest request) {
        //Room room=new Room("Room1");
        //when(roomService.register(any())).thenReturn(room);
        //assertSame(roomService.register(request),room);

    }
    static class RoomRequestAggregator implements ArgumentsAggregator{
        @Override
        public RoomRequest aggregateArguments(ArgumentsAccessor accessor, ParameterContext context) throws ArgumentsAggregationException {
            return RoomRequest.builder().roomName(accessor.getString(0)).build();
        }
    }

    @ParameterizedTest
    @DisplayName("하나의 채팅방")
    @ValueSource(longs={1L})
    void getOne(Long id) {
        RoomResponse response=RoomResponse.builder().id(id).roomName("임의의이름1").build();
        when(roomService.getOne(id)).thenReturn(response);

        assertEquals(roomService.getOne(id).getRoomName(),"임의의이름1");
    }
}