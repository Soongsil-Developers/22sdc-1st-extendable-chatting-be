package com.extendablechattingbe.extendablechattingbe.repository;


import com.extendablechattingbe.extendablechattingbe.domain.Message;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MessageRepository extends JpaRepository<Message,Long> {




}
