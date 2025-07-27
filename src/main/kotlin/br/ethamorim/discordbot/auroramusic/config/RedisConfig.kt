package br.ethamorim.discordbot.auroramusic.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.cache.RedisCacheConfiguration
import org.springframework.data.redis.cache.RedisCacheManager
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.connection.RedisStandaloneConfiguration
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories
import java.time.Duration

@Configuration
@EnableRedisRepositories
class RedisConfig(
    @Value("\${spring.data.redis.cluster.cache-name.otp.ttl}")
    private val cacheTTL: Long,
    @Value("\${spring.data.redis.host}")
    private val host: String
) {
    @Bean
    fun redisConnectionFactory(): RedisConnectionFactory {
        return LettuceConnectionFactory(RedisStandaloneConfiguration(host))
    }

    @Bean
    fun redisTemplate(redisConnectionFactory: RedisConnectionFactory): RedisTemplate<Array<Byte>, Array<Byte>> {
        val template = RedisTemplate<Array<Byte>, Array<Byte>>()
        template.connectionFactory = redisConnectionFactory

        return template
    }

    @Bean
    fun cacheManager(redisConnectionFactory: RedisConnectionFactory): RedisCacheManager {
        return RedisCacheManager.builder(redisConnectionFactory)
            .cacheDefaults(RedisCacheConfiguration
                .defaultCacheConfig()
                .entryTtl(Duration.ofHours(cacheTTL)))
            .build()
    }
}