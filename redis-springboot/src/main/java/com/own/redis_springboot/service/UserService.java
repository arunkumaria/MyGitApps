package com.own.redis_springboot.service;



import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.own.redis_springboot.model.User;

@Service
public class UserService {

    private final Map<Long, User> db = new ConcurrentHashMap<>();

    public UserService() {
        db.put(1L, new User(1L, "Arun"));
    }

    // Cache read
    @Cacheable(cacheNames = "users", key = "#id")
    public User getUser(Long id) {
        System.out.println("[DB] Loading user " + id);
        simulateSlowCall();
        return db.get(id);
    }

    // Cache update
    @CachePut(cacheNames = "users", key = "#result.id")
    public User save(User user) {
        db.put(user.getId(), user);
        return user;
    }

    // Cache eviction
    @CacheEvict(cacheNames = "users", key = "#id")
    public void delete(Long id) {
        db.remove(id);
    }

    private void simulateSlowCall() {
        try { Thread.sleep(1000); } catch (InterruptedException ignored) {}
    }
}
