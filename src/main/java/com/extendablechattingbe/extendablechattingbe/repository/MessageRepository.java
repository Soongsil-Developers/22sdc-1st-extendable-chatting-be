package com.extendablechattingbe.extendablechattingbe.repository;


import com.extendablechattingbe.extendablechattingbe.domain.Message;
import com.extendablechattingbe.extendablechattingbe.domain.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MessageRepository extends JpaRepository<Message, Long> {


    @EntityGraph(attributePaths = {"room", "member"}, type = EntityGraphType.LOAD)
    Page<Message> findByRoom(Room room, Pageable pageable);

}
