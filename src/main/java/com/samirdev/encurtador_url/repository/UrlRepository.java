package com.samirdev.encurtador_url.repository;

import com.samirdev.encurtador_url.model.Url;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UrlRepository extends JpaRepository<Url, Long> {
    Optional<Url> findByHash(String hash);
}
