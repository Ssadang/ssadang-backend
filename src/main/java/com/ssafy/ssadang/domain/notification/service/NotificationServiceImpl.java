package com.ssafy.ssadang.domain.notification.service;

import com.ssafy.ssadang.domain.notification.entity.Notification;
import com.ssafy.ssadang.domain.notification.repository.NotificationRepository;
import com.ssafy.ssadang.domain.notification.repository.RedisNotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {
    private final NotificationRepository notificationRepository;
    private final RedisNotificationRepository redisNotificationRepository;

    @Override
    public void createNotification(String userId, String content) {

        redisNotificationRepository.saveNotification(userId, content);

        Notification notification = Notification.builder()
                .userId(userId)
                .content(content)
                .build();
        notificationRepository.save(notification);
    }

    @Override
    public List<Object> getCachedNotifications(String userId) {
        return redisNotificationRepository.findNotifications(userId);
    }

    @Override
    public void markAsRead(Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 알림입니다."));
        notification.markAsRead();
        notificationRepository.save(notification);
    }

    @Override
    public List<Notification> getUnreadNotifications(String userId) {
        return notificationRepository.findByUserIdAndIsRead(userId, false);
    }
}
