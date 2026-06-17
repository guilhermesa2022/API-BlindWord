package com.hotTable.API_BlindWord.repository;

import com.hotTable.API_BlindWord.entity.FiveCharWords;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface WordRepository extends JpaRepository<FiveCharWords, Long> {

    @Query(value = "SELECT word FROM five_char_words WHERE deleted = false ORDER BY RANDOM() LIMIT 1", nativeQuery = true)
    Optional<String> findRandomWord();
}