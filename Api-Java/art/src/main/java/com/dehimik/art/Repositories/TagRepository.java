package com.dehimik.art.Repositories;

import com.dehimik.art.Entities.*;
import java.util.Optional;

public interface TagRepository extends BaseRepository<Tag, Long>{
    Optional<Tag> findByName(String name);
}
