package com.samirdev.encurtador_url.repository;

import com.samirdev.encurtador_url.model.Url;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

public interface UrlRepository extends JpaRepository<Url, Long> {
    Optional<Url> findByHash(String hash);

    @Query("SELECT u.originalUrl FROM Url u WHERE u.hash = :hash AND (u.expiresAt > :dateTime OR u.expiresAt IS NULL)")
    Optional<String> findByHashAndExpiresAtAfter(@Param("hash") String hash, @Param("dateTime") LocalDateTime dateTime);

    @Transactional
    @Modifying
    @Query("UPDATE Url u SET u.accessCount = u.accessCount + 1 WHERE u.hash = :hash")
    int incrementAccessCount(@Param("hash") String hash);
}
