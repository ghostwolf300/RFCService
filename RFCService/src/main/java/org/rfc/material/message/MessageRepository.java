package org.rfc.material.message;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository  extends JpaRepository<Message, Integer> {

}
