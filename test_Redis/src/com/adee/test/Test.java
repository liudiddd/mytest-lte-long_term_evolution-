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
	 * jedis�෽ʽ����
	 */
	public static void demo1() {
		Jedis jedis = new Jedis("192.168.1.110", 6379);
		jedis.set("name", "adee");
		System.out.println(jedis.get("name"));
		System.out.println(jedis.get("foo"));
		jedis.close();
	}
	
	/**
	 * ʹ�����ӳ�����
	 */
	public static void demo2() {
		JedisPoolConfig config = new JedisPoolConfig();
		//�������������
		config.setMaxTotal(30);
		//���ÿ���������
		config.setMaxIdle(10);
		//������ӳ�
		JedisPool pool = new JedisPool("192.168.1.110", 6379);
		//��ú��Ķ���
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
	 * redis�������������ͣ�key��ò�Ҫ����1024�ֽڣ�key�����ͳһ�������淶
	 * 	�ַ���String��
	 * 		value���Ϊ512M�����������ֵ��ȡֵ��ɾ������չ����
	 * 		��ֵ��set key value�����򷵻�null��
	 * 		ȡֵ��get key
	 * 		��ȡ�����ã�getset key value������old value
	 * 		ɾ����del key
	 * 		��ֵ������incr key����ָ��key��value����1�����value�����ڣ��Ǿ�������Ϊ0������1���������תΪ���ͣ���ô����ʧ�ܲ�������Ӧ�Ĵ�����Ϣ��
	 * 		��ֵ�ݼ���decr key
	 * 		��ֵ�Ӽ���incrby key ���֣���incrby num 5
	 * 		��ֵ������decrby key ���֣���decrby num 5
	 * 		ƴ���ַ�����append key value����key������׷�ӣ��������򴴽�һ�����ַ�����׷�ӡ�
	 * 	�ַ����б�list
	 * 		�����֣�ArrayList��LinkedList
	 * 		������ӣ�lpush mylist a b c --lpush�����룬����list�ĳ���
	 * 							rpush mylist 1 2 3 --rpush�Ҳ���룬����list�ĳ���
	 * 		�鿴�б�lrange mylist 0 5 --0��5��ʾ��ʼindex�ͽ���index����0������5��
	 * 							lrange mylist 0 -1 --��0�������һ��
	 * 							lrange mylist 0 -2 --��0����������2��
	 * 		���˵�����lpop mylist --����˵���һ��Ԫ��
	 * 							rpop mylist --���Ҷ˵���һ��Ԫ��
	 * 							llen mylist --��ȡԪ�ظ���
	 * 		��ȡ�б�Ԫ�ظ�����
	 * 		��չ���
	 * 					lrem mylist 5 a --�����ұ���mylist��ɾ��5��a
	 * 	�ַ�������set
	 * 	�����ַ�������sorted set
	 * 	��ϣhash��redis�е�hash���Ϳ��Կ��ɾ���String���͵�key��String���͵�value��map�����������������£�
	 * 		��ֵ��hset myhash username jack
	 * 					hmset myhash2 username rose age 21
	 * 		ȡֵ��hget myhash username
	 * 					hmget myhash1 username age
	 * 					hgetall myhash1 --�õ�ĳ��hash�������ֶκ�ֵ
	 * 		ɾ����hdel myhash username --hdel��ɾ��hash��ĳ����ĳЩ�ֶ�
	 * 					hdel myhash1 username age sex
	 * 					del myhash1 --del��ɾ������hash
	 * 		�������֣�hincrby myhash1 age 5
	 * 		��ѧ���
	 * 			�ж�hash�е�ĳ�������Ƿ���ڣ�hexists myhash1 username�����ڷ���1�����򷵻�0
	 * 			��ȡhash���ֶεĸ�����hlen myhash1
	 * 			��ȡhash�������ֶε����ƣ�hkeys myhash1
	 * 			��ȡhash�������ֶε�ֵ��hvals myhash1 
	 */
	public static void demo3() {
		
	}
}
