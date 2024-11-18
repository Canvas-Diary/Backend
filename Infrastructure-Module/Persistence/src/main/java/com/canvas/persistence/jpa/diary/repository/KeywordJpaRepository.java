package com.canvas.persistence.jpa.diary.repository;

import com.canvas.persistence.jpa.diary.entity.KeywordEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface KeywordJpaRepository extends JpaRepository<KeywordEntity, UUID> {

    @Query("""
        select k
        from KeywordEntity k
        where k.name in (:names)
    """)
    List<KeywordEntity> findByKeywords(List<String> names);

    @Query("""
        select k
        from KeywordEntity k
        where k.id in (:keywordIds)
    """)
    List<KeywordEntity> findByKeywordIds(List<UUID> keywordIds);

}
