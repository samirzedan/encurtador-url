package com.samirdev.encurtador_url.controller;

import com.samirdev.encurtador_url.dto.Url.UrlRequestDTO;
import com.samirdev.encurtador_url.dto.Url.UrlResponseDTO;
import com.samirdev.encurtador_url.service.UrlService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/urls")
public class UrlController {

    private final UrlService urlService;

    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }

    @PostMapping
    public ResponseEntity<UrlResponseDTO> store(@RequestBody @Valid UrlRequestDTO request) {
        return ResponseEntity.ok(urlService.store(request));
    }

    @GetMapping("/{hash}")
    public ResponseEntity<UrlResponseDTO> show(@PathVariable String hash) {
        return ResponseEntity.ok(urlService.searchByHash(hash));
    }

    @GetMapping
    public ResponseEntity<Page<UrlResponseDTO>> index(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(urlService.list(page, size));
    }

    @DeleteMapping("/{hash}")
    public ResponseEntity<Void> delete(@PathVariable String hash) {
        urlService.delete(hash);
        return ResponseEntity.noContent().build();
    }
}
