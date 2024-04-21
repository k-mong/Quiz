package example.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PathVariable;

@Service
@RequiredArgsConstructor
@Slf4j
public class RedisClient {
    private final RedisTemplate<String, Object> redisTemplate;

    private static final ObjectMapper mapper = new ObjectMapper();

    public <T> T get(Long key, Class<T> classType) {
        return get(key.toString(), classType);
    }

    public <T> T get(String key, Class<T> classType) {
        String redisValue = (String) redisTemplate.opsForValue().get(key);
        if(ObjectUtils.isEmpty(redisValue)) {
            return null;
        } else {
            try {
                return mapper.readValue(redisValue, classType);
            } catch (JsonProcessingException e) {
                log.error("parsing error");
                return null;
            }
        }
    }

    public void put(Long key, String token) {
        put(key.toString(), token);
    }

    public void put(String key, String token) {
        try {
            redisTemplate.opsForValue().set(key, mapper.writeValueAsString(token));
        } catch (JsonProcessingException e) {
            throw new RuntimeException("fail to save token");
        }
    }

}
