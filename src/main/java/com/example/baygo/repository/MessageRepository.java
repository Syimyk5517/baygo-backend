
package com.example.baygo.repository;

import com.example.baygo.db.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
}