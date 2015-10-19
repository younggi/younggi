package com.sds.janus.sample.manager;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sds.janus.oauth2.authserver.manager.OAuthTokenManager;


@Component
public class OAuthRedisTokenManager implements OAuthTokenManager {
	
	@Autowired
	private RedisTemplate redisTemplate;
	
	private ObjectMapper mapper = new ObjectMapper();

	@Override
	public void setOAuthtokenInfo(String accessToken,
			Map<String, String> tokenInfo, long timeout) {
		redisTemplate.opsForHash().putAll(accessToken, tokenInfo);
		redisTemplate.expire(accessToken, timeout, TimeUnit.SECONDS);
	}

	@Override
	public Map<String, String> getOAuthtokenInfo(String accessToken) {
		return redisTemplate.opsForHash().entries(accessToken);
	}

	@Override
	public boolean checkToken(String accessToken) {
		return !getOAuthtokenInfo(accessToken).isEmpty();
	}
}
