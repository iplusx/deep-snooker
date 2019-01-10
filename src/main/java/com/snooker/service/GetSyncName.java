package com.snooker.service;

import com.snooker.domain.player.FriPlayerRanking;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Authored by WangJunyu on 2017/4/7
 */
@Service
public class GetSyncName {
    @Cacheable(value = "player:name", key = "'player:name'")
    public Map<String, FriPlayerRanking> getCache() {
        System.out.println("nothing?");
        return null;
    }
}
