package com.ssafy.ssadang.domain.notification.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class NotificationRequestDto {
    @NotBlank
    private String content;
}
