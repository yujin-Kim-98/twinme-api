package com.api.twinme.common.config.database

import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.springframework.boot.autoconfigure.data.redis.RedisProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.cache.annotation.CachingConfigurerSupport
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.cache.RedisCacheManager
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.connection.RedisStandaloneConfiguration
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.StringRedisSerializer

@EnableCaching
@Configuration
@EnableConfigurationProperties(RedisProperties::class)
class RedisConfig(
    private val redisProperties: RedisProperties
): CachingConfigurerSupport() {

    @Bean
    fun redisConnectionFactory(): LettuceConnectionFactory {
        val configuration = RedisStandaloneConfiguration()
        configuration.hostName = redisProperties.host
        configuration.port = redisProperties.port
        return LettuceConnectionFactory(configuration)
    }

    @Bean
    fun redisTemplate(): RedisTemplate<String, Any> {
        val redisTemplate: RedisTemplate<String, Any> = RedisTemplate()
        redisTemplate.setConnectionFactory(redisConnectionFactory())
        redisTemplate.keySerializer = StringRedisSerializer()
        redisTemplate.valueSerializer = GenericJackson2JsonRedisSerializer()
        redisTemplate.hashKeySerializer = StringRedisSerializer()
        redisTemplate.hashValueSerializer = GenericJackson2JsonRedisSerializer()
        return redisTemplate
    }

    /**
     * @Cacheable에서 사용
     */
//    @Bean
//    override fun cacheManager(): CacheManager? {
//        val builder = RedisCacheManager
//            .RedisCacheManagerBuilder
//            .fromConnectionFactory(
//                redisConnectionFactory()
//            )
//        val defaultConfig = RedisCacheConfiguration
//            .defaultCacheConfig()
//            .serializeValuesWith(
//                RedisSerializationContext
//                    .SerializationPair
//                    .fromSerializer(GenericJackson2JsonRedisSerializer())
//            )
//            .entryTtl(Duration.ofSeconds(30L))
//        builder.cacheDefaults(defaultConfig)
//        return builder.build()
//    }

    private fun redisCacheManager(redisConnectionFactory: RedisConnectionFactory): RedisCacheManager.RedisCacheManagerBuilder {
        return RedisCacheManager.RedisCacheManagerBuilder
            .fromConnectionFactory(redisConnectionFactory)
    }

    private fun defaultObjectMapper(): ObjectMapper {
        return ObjectMapper()
            .activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY)
            .registerModule(KotlinModule())
            .registerModule(JavaTimeModule())
    }

}