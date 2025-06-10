package com.dehimik.art.Repositories;

import com.dehimik.art.Entities.ChatGroup;
import com.dehimik.art.Entities.ChatMember;
import com.dehimik.art.Entities.ChatMemberId;
import com.dehimik.art.Entities.User;

import java.util.List;
import java.util.Optional;

public interface ChatMemberRepository extends BaseRepository<ChatMember, ChatMemberId>{
    Optional<ChatMember> findById(ChatMemberId memberId);
    List<ChatMember> findByUser(User user);
    List<ChatMember> findByGroup(ChatGroup group);
    boolean existsByGroupAndUser(ChatGroup group, User user);
}
