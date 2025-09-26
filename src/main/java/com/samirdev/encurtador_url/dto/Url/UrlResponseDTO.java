package com.samirdev.encurtador_url.dto.Url;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UrlResponseDTO {
    private String hash;
    private String originalUrl;
    private Long accessCount;
    private LocalDateTime expiresAt;
    private LocalDateTime createdAt;
}
