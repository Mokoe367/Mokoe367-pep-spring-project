package com.example.repository;

import com.example.entity.Message;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Repository
public interface MessageRepository extends CrudRepository<Message, Integer> {
    @Modifying
    @Transactional
    @Query("UPDATE Message m SET m.postedBy = :postedBy, m.messageText = :messageText, m.timePostedEpoch = :timePostedEpoch" +
            " WHERE m.messageId = :messageId")
    int updateMessage(@Param("postedBy") Integer postedBy, @Param("messageText") String messageText,
                      @Param("timePostedEpoch") Long timePostedEpoch, @Param("messageId") Integer messageId);

    @Modifying
    @Transactional
    @Query("DELETE Message m WHERE m.messageId = :messageId")
    int deleteMessage(@Param("messageId") Integer messageId);

    @Query("SELECT m from Message m WHERE m.postedBy = :postedBy")
    Collection<Message> findMessagesPostedByUser(@Param("postedBy") Integer postedBy);
}
