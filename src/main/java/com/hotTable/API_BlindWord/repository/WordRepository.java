package com.hotTable.API_BlindWord.repository;

import com.hotTable.API_BlindWord.entity.FiveCharWords;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface WordRepository extends JpaRepository<FiveCharWords, Long> {

    @Query("select max(word.id) from FiveCharWords word where word.deleted = false")
    Optional<Long> findMaxId();

    @Query(value = """
            select word
            from five_char_words
            where deleted = false
              and id >= :randomId
            order by id
            limit 1
            """, nativeQuery = true)
    Optional<String> findRandomWordFromId(@Param("randomId") Long randomId);
}
