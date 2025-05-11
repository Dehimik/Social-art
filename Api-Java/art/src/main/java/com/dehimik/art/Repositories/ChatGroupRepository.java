package com.dehimik.art.Repositories;

import com.dehimik.art.Entities.ChatGroup;
import com.dehimik.art.Entities.User;

import java.util.List;

public interface ChatGroupRepository extends BaseRepository<ChatGroup, Long>{
    List<ChatGroup> findByCreatedBy(User user);
}
