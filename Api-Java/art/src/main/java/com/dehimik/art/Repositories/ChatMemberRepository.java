package com.dehimik.art.Repositories;

import com.dehimik.art.Entities.ChatGroup;
import com.dehimik.art.Entities.ChatMember;
import com.dehimik.art.Entities.User;

import java.util.List;

public interface ChatMemberRepository extends BaseRepository<ChatMember, Long>{
    List<ChatMember> findByUser(User user);
    List<ChatMember> findByGroup(ChatGroup group);
    boolean existsByGroupAndUser(ChatGroup group, User user);
}
