package com.snooker.service;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * Authored by WangJunyu on 2017/4/8
 */
@Service
public class MatchPosterService {
    @CachePut(value = "match:poster", key = "'match:poster:'+#matchId")
    public String putPoster(String matchId, String posterUrl) {
        return posterUrl;
    }

    @CacheEvict(value = "match:list", key = "'match:list:'+#season")
    public void emptyListCache(String season) {
        //
    }

    @Cacheable(value = "match:poster", key = "'match:poster:'+#matchId")
    public String getPoster(String matchId) {
        return "";
    }
}
