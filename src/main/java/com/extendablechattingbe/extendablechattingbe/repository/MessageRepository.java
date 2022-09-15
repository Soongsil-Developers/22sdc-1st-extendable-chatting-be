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

    @Query(value = "select distinct m from Message m where m.room = :room order by m.createdDate DESC")
    Page<Message> findByRoom(Room room, Pageable pageable);

}