package com.adee.test;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class Test {
	public static void main(String[] args) {
		//demo1();
		demo2();
	}
	
	/**
	 * jedis类方式连接
	 */
	public static void demo1() {
		Jedis jedis = new Jedis("192.168.1.110", 6379);
		jedis.set("name", "adee");
		System.out.println(jedis.get("name"));
		System.out.println(jedis.get("foo"));
		jedis.close();
	}
	
	/**
	 * 使用连接池连接
	 */
	public static void demo2() {
		JedisPoolConfig config = new JedisPoolConfig();
		//设置最大连接数
		config.setMaxTotal(30);
		//设置空闲连接数
		config.setMaxIdle(10);
		//获得连接池
		JedisPool pool = new JedisPool("192.168.1.110", 6379);
		//获得核心对象
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			jedis.set("name", "world");
			System.out.println(jedis.get("name"));
		}catch(Exception ex) {
			ex.printStackTrace();
		}finally {
			if(jedis != null) {
				jedis.close();
			}
			if(pool != null) {
				pool.close();
			}
		}
	}
	
	/**
	 * redis的五种数据类型：key最好不要超过1024字节，key最好有统一的命名规范
	 * 	字符串String：
	 * 		value最大为512M。常用命令：赋值、取值、删除、扩展命令
	 * 		赋值：set key value，空则返货null，
	 * 		取值：get key
	 * 		获取并设置：getset key value，返回old value
	 * 		删除：del key
	 * 		数值递增：incr key，将指定key的value递增1，如果value不存在，那就先设置为0，在增1。如果不能转为整型，那么操作失败并返回相应的错误信息。
	 * 		数值递减：decr key
	 * 		数值加几：incrby key 数字，如incrby num 5
	 * 		数值减几：decrby key 数字，如decrby num 5
	 * 		拼凑字符串：append key value，若key存在则追加，不存在则创建一个空字符串并追加。
	 * 	字符串列表list
	 * 		分两种：ArrayList和LinkedList
	 * 		两端添加：lpush mylist a b c --lpush左侧插入，返回list的长度
	 * 							rpush mylist 1 2 3 --rpush右侧插入，返回list的长度
	 * 		查看列表：lrange mylist 0 5 --0和5表示起始index和结束index，第0个到第5个
	 * 							lrange mylist 0 -1 --第0个到最后一个
	 * 							lrange mylist 0 -2 --第0个到倒数第2个
	 * 		两端弹出：lpop mylist --从左端弹出一个元素
	 * 							rpop mylist --从右端弹出一个元素
	 * 							llen mylist --获取元素个数
	 * 		获取列表元素个数：
	 * 		扩展命令：
	 * 					lrem mylist 5 a --从左到右遍历mylist，删除5个a
	 * 	字符串集合set
	 * 	有序字符串集合sorted set
	 * 	哈希hash：redis中的hash类型可以看成具有String类型的key和String类型的value的map容器，常用命令如下：
	 * 		赋值：hset myhash username jack
	 * 					hmset myhash2 username rose age 21
	 * 		取值：hget myhash username
	 * 					hmget myhash1 username age
	 * 					hgetall myhash1 --得到某个hash的所有字段和值
	 * 		删除：hdel myhash username --hdel是删除hash的某个或某些字段
	 * 					hdel myhash1 username age sex
	 * 					del myhash1 --del是删除整个hash
	 * 		增加数字：hincrby myhash1 age 5
	 * 		自学命令：
	 * 			判断hash中的某个属性是否存在：hexists myhash1 username，存在返回1，否则返回0
	 * 			获取hash的字段的个数：hlen myhash1
	 * 			获取hash的所有字段的名称：hkeys myhash1
	 * 			获取hash的所有字段的值：hvals myhash1 
	 */
	public static void demo3() {
		
	}
}
