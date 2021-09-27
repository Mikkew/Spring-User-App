package mx.com.mms.users.config;

//import java.util.*;

//import org.redisson.Redisson;
//import org.redisson.api.RedissonClient;
//import org.redisson.config.Config;
//import org.redisson.spring.cache.RedissonSpringCacheManager;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.*;

@EnableCaching
@Configuration
public class CacheConfig {

	@Bean
	public CacheManager getManager() {
		return new ConcurrentMapCacheManager("users");
	}
	
//	@Bean
//	public CacheManager getCache(RedissonClient reddisonClient) {
//		Map<String, CacheConfig> config = new HashMap<>();
//		config.put("testMap", new CacheConfig());
//		return new RedissonSpringCacheManager(reddisonClient);
//	}
	
//	@Bean(destroyMethod = "shutdown")
//	public RedissonClient redisson() {
//		Config config = new Config();
//		config.useSingleServer().setAddress("redis://127.0.0.1:6379");
//		return Redisson.create(config);
//	}
}
