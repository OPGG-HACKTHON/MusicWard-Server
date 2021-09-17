package io.github.opgg.music_ward_server.config;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import redis.embedded.RedisServer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestRedisConfig {

	private RedisServer redisServer;

	public TestRedisConfig(@Value("${spring.redis.port}") int redisPort) {
		redisServer = new RedisServer(redisPort);
	}

	@PostConstruct
	public void startRedis() {
		redisServer.start();
	}

	@PreDestroy
	public void stopRedis() {
		redisServer.stop();
	}

}
