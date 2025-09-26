package com.samirdev.encurtador_url.dto.Url;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UrlRequestDTO {
    @NotNull(message = "Original URL is required")
    @NotBlank(message = "Original URL can not be empty")
    @Size(max = 2048)
    private String originalUrl;

    @Pattern(
        regexp = "^\\d{4}-\\d{2}-\\d{2}([T\\d:]{0,9})?$",
        message = "expiresAt must be in format yyyy-MM-dd or yyyy-MM-ddTHH:mm:ss"
    )
    private String expiresAt;
}
