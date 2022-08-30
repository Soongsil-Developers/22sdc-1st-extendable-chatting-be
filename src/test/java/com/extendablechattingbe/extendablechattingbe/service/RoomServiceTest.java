package com.extendablechattingbe.extendablechattingbe.service;

import com.extendablechattingbe.extendablechattingbe.dto.request.RoomRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;
class RoomServiceTest {


    @Test
    @DisplayName("채팅방 입장")
    @NullSource
    @CsvSource( {"","스터디모임1"})
    void register(RoomRequest request) {

    }

    @Test
    void getList() {
    }

    @Test
    void getOne() {
    }

    @Test
    void delete() {
    }

    @Test
    void entityToResponse() {
    }
}