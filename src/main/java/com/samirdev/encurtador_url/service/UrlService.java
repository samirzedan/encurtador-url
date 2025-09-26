package com.samirdev.encurtador_url.service;

import com.samirdev.encurtador_url.dto.Url.UrlRequestDTO;
import com.samirdev.encurtador_url.dto.Url.UrlResponseDTO;
import com.samirdev.encurtador_url.model.Url;
import com.samirdev.encurtador_url.repository.UrlRepository;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class UrlService {
    private final UrlRepository urlRepository;

    public UrlService(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    public UrlResponseDTO store(UrlRequestDTO request) {
        Url url = new Url();
        byte hashLength = 6;
        url.setHash(this.generateUniqueHash(hashLength));
        url.setOriginalUrl(request.getOriginalUrl());
        if (request.getExpiresAt() != null) {
            url.setExpiresAt(LocalDateTime.parse(request.getExpiresAt()));
        }
        Url urlSaved = urlRepository.save(url);
        return toResponse(urlSaved);
    }

    public UrlResponseDTO searchByHash(String hash) {
        Url url = urlRepository.findByHash(hash)
                .orElseThrow(() -> new RuntimeException("URL not found!"));
        return toResponse(url);
    }

    public List<UrlResponseDTO> list() {
        return urlRepository.findAll().stream().map(this::toResponse).toList();
    }

    public void delete(String hash) {
        Url url = urlRepository.findByHash(hash)
                .orElseThrow(() -> new RuntimeException("URL not found!"));
        urlRepository.delete(url);
    }

    private UrlResponseDTO toResponse(Url url) {
        UrlResponseDTO response = new UrlResponseDTO();
        response.setHash(url.getHash());
        response.setOriginalUrl(url.getOriginalUrl());
        response.setAccessCount(url.getAccessCount());
        response.setExpiresAt(url.getExpiresAt());
        response.setCreatedAt(url.getCreatedAt());
        return response;
    }

    private String generateHash(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            sb.append(characters.charAt(random.nextInt(characters.length())));
        }

        return sb.toString();
    }

    private String generateUniqueHash(int length) {
        String hash;
        do {
            hash = generateHash(length);
        } while (urlRepository.findByHash(hash).isPresent());
        return hash;
    }
}
