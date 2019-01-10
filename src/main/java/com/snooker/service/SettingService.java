package com.snooker.service;

import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * Description:
 *
 * @author Junyu Wang
 */
@Service
public class SettingService {
    @CachePut(value = "setting:liveScoreUrl", key = "'setting:liveScoreUrl'")
    public String putLiveScoreUrl(String url) {
        return url;
    }

    @Cacheable(value = "setting:liveScoreUrl", key = "'setting:liveScoreUrl'")
    public String getLiveScoreUrl() {
        return "";
    }
}
