package com.ssafy.ssadang.domain.notification.service;

import com.ssafy.ssadang.domain.notification.entity.Notification;

import java.util.List;

public interface NotificationService {
    void createNotification(String userId, String content);
    //레디스 사용할지 고민 중..
    List<Object> getCachedNotifications(String userId);
    void markAsRead(Long notificationId);
    //일단 mysql로 구현
    List<Notification> getUnreadNotifications(String userId);
}
