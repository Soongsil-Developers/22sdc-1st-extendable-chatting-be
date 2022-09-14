package com.extendablechattingbe.extendablechattingbe.repository;


import com.extendablechattingbe.extendablechattingbe.domain.Message;
import com.extendablechattingbe.extendablechattingbe.domain.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;


public interface MessageRepository extends JpaRepository<Message, Long> {

    @EntityGraph(attributePaths = {"room", "member"}, type = EntityGraphType.LOAD)
    Page<Message> findByRoom(Room room, Pageable pageable);
    // 채팅방의 모든 메시지를 불러옴
    @Query(value = "select distinct m from Message m where m.room = :room order by m.createdDate DESC")
    List<Message> findAllByRoomId(@Param("room")Room room, Pageable pageable);

    // 마지막 읽은 메시지부터 이어서 보기
    @Query(value = "select distinct m from Message m where m.room = :room and m.createdDate >= :lastReadDate order by m.createdDate DESC")
    List<Message> findAllByLastReadDate(@Param("room")Room room, @Param("lastReadDate") LocalDateTime lastReadDate, Pageable pageable);

    // 방 입장부터 메시지 이어서 보기
    @Query(value = "select distinct m from Message m where m.room = :room and m.createdDate >= :enterDate order by m.createdDate DESC")
    Page<Message> findAllByEnterDate(@Param("room")Room room, @Param("enterDate") LocalDateTime enterDate, Pageable pageable);

}