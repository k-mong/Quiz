package example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@EnableRedisRepositories
public class RedisConfig {
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory){
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        StringRedisSerializer serializer = new StringRedisSerializer();

        redisTemplate.setConnectionFactory(redisConnectionFactory);

        redisTemplate.setKeySerializer(serializer);
        redisTemplate.setValueSerializer(serializer);

        redisTemplate.setHashKeySerializer(serializer);
        redisTemplate.setHashValueSerializer(serializer);

        return redisTemplate;
    }


    /**
     * @Bean: 스프링 컨테이너에(DI) 의해 관리되는 빈을 생성하는 것 , 생성되는 빈은 애플리케이션 전반에서 주입되어 사용됨
     * RedisTemplate<String,Object>: 레디스에서 데이터를 저장하고 조회하는데 사용되는 템플릿
     * RedisConnectionFactory: 레디스 연결을 추상화한 인터페이스. 레디스 서버와의 연결을 관리.
     *                      파라미터를 통해 스프링 부트가 자동으로 구성한 레디스 연결 팩토리를 주입받습니다.
     * StringRedisSerializer: 직렬화 도구, 키와 값 모두 문자열로 저장되기 때문에 직렬화 도구의 사용이 필요
     * setConnectionFactory(): RedisTemplate에 RedisConnectionFactory를 설정합니다. 이는 레디스 서버와의 연결을 가능
     */
}
