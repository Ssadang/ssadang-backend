package com.ssafy.ssadang.domain.notification.controller;

import com.ssafy.ssadang.domain.notification.dto.NotificationRequestDto;
import com.ssafy.ssadang.domain.notification.entity.Notification;
import com.ssafy.ssadang.domain.notification.service.NotificationService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@RestController
@RequestMapping("/api/v1/notifications")
public class NotificationController {

    private final NotificationService notificationService;
    private final ConcurrentMap<String, SseEmitter> emitters = new ConcurrentHashMap<>();

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    // 알림 구독(프론트 sse 구현을 위한)
    @GetMapping("/subscribe")
    public SseEmitter subscribe(@RequestParam String userId) {
        SseEmitter emitter = new SseEmitter(60 * 1000L);
        emitters.put(userId, emitter);

        emitter.onCompletion(() -> emitters.remove(userId));
        emitter.onTimeout(() -> emitters.remove(userId));

        return emitter;
    }

    // 읽지 않은 알림 조회
    @GetMapping("/unread")
    public ResponseEntity<List<Notification>> getUnreadNotifications(@RequestParam String userId) {
        List<Notification> unreadNotifications = notificationService.getUnreadNotifications(userId);
        return ResponseEntity.ok(unreadNotifications);
    }

    // 특정 알림 읽음 처리
    @PutMapping("/{notificationId}/read")
    public ResponseEntity<Void> markAsRead(@PathVariable Long notificationId) {
        notificationService.markAsRead(notificationId);
        return ResponseEntity.noContent().build();
    }

    // 알림 전송
    @PostMapping("/send")
    public ResponseEntity<?> sendNotification(@RequestParam String userId, @Valid @RequestBody NotificationRequestDto requestDto) {
        notificationService.createNotification(userId, requestDto.getContent());

        SseEmitter emitter = emitters.get(userId);
        if (emitter != null) {
            try {
                emitter.send(SseEmitter.event().name("notification").data(requestDto.getContent()));
            } catch (IOException e) {
                emitters.remove(userId);
            }
        }
        return ResponseEntity.ok().build();
    }


}