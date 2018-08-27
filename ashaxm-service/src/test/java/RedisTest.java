import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;


public class RedisTest {

	@Autowired
	private RedisTemplate<String, String> redisTemplate;
	
	@Test
	public void test1(){
		redisTemplate.opsForValue().set("abc", "123");
		System.out.println("设置成功");
	}
}
