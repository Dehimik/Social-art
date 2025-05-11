package com.dehimik.art.Repositories;

import com.dehimik.art.Entities.ChatGroup;
import com.dehimik.art.Entities.ChatMessage;

import java.util.List;

public interface ChatMessageRepository extends BaseRepository<ChatMessage, Long>{
    List<ChatMessage> findByGroupOrderByCreatedAtAsc(ChatGroup group);
}
