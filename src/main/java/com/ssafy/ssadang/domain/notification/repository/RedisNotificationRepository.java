package com.ssafy.ssadang.domain.notification.repository;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RedisNotificationRepository {
    private final RedisTemplate<String, Object> redisTemplate;

    public RedisNotificationRepository(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void saveNotification(String userId, String message) {
        String key = "notification:" + userId;
        redisTemplate.opsForList().rightPush(key, message);
    }

    public List<Object> findNotifications(String userId) {
        String key = "notification:" + userId;
        return redisTemplate.opsForList().range(key, 0, -1);
    }
}
