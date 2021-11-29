package com.risk.riskmanage.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Component
public class RedisManager {
	// 0-表示永远不过期
	private int expire = 0;

	@Autowired
	private JedisPool jedisPool;

	public RedisManager() {}

	/**
	 * @Title: init
	 * @Description: 初始化方法，用来初始化
	 * @param     设定文件
	 * @return void    返回类型
	 * @throws
	 */
	public void init() {
	}

	/**
	 * @Title: get
	 * @Description: 根据key来获得一条特定的缓存数据
	 * @param @param key string类型的key
	 * @param @return    设定文件
	 * @return String    返回类型
	 * @throws
	 */
	public String get(String key) {
		String value = null;
		Jedis jedis = jedisPool.getResource();
		try {
			value = jedis.get(key);
		} catch (Exception e) {
			//释放redis对象
			jedisPool.returnBrokenResource(jedis);
			e.printStackTrace();
		} finally {
			//返还到连接池
			if (jedis != null) {
				jedisPool.returnResource(jedis);
			}
		}
		return value;
	}

	/**
	 * @Title: set
	 * @Description: 向redis数据库中缓存数据，key，value都为字符串的类型
	 * @param @param key
	 * @param @param value
	 * @param @param expire 0为永不过期，其他时间则会设置对应的过期时间
	 * @param @return 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	public String set(String key, String value, int expire) {
		Jedis jedis = jedisPool.getResource();
		try {
			jedis.set(key, value);
			if (expire != 0) {
				jedis.expire(key, expire);
			}
		} catch (Exception e) {
			//释放redis对象
			jedisPool.returnBrokenResource(jedis);
			e.printStackTrace();
		} finally {
			//返还到连接池
			if (jedis != null) {
				jedisPool.returnResource(jedis);
			}
		}
		return value;
	}

	/**
	 * @Title: del
	 * @Description: 根据特定的string类型的key来删除redis数据库中的缓存数据
	 * @param @param key 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	public void del(String key) {
		Jedis jedis = jedisPool.getResource();
		try {
			jedis.del(key);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	/**
	 * 获取key的剩余过期时间，单位秒
	 * @param key
	 * @return
	 */
	public Long ttl(String key) {
		Long value = null;
		Jedis jedis = jedisPool.getResource();
		try {
			value = jedis.ttl(key);
		} catch (Exception e) {
			//释放redis对象
			jedisPool.returnBrokenResource(jedis);
			e.printStackTrace();
		} finally {
			//返还到连接池
			if (jedis != null) {
				jedisPool.returnResource(jedis);
			}
		}
		return value;
	}

}
