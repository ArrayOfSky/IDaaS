package com.gaoyifeng.IDaaS.types.sdk;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Redis工具类
 * 对stringRedisTemplate进行封装
 *
 * @author 22449
 */
@Component
public class RedisUtils {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    //TODO 通用

    /**
     * 查看符合模板的所有key
     */
    public Set<String> keys(String key) {
        return stringRedisTemplate.keys(key);
    }

    /**
     * 删除一个指定的key
     */
    public Boolean del(String key) {
        return stringRedisTemplate.delete(key);
    }

    /**
     * 判断是否有key
     */
    public Boolean exists(String key) {
        return stringRedisTemplate.hasKey(key);
    }

    /**
     * 为key设置过期时间
     */
    public Boolean expire(String key, long timeout, TimeUnit unit) {
        return stringRedisTemplate.expire(key, timeout, unit);
    }

    /**
     * 判断key的剩余存活时间
     */
    public Long ttl(String key) {
        return stringRedisTemplate.getExpire(key);
    }

    //TODO Object
    public void objectSet(String key, Object object, Long time, TimeUnit unit) {
        String stringObject = JackSonUtil.encode(object);
        stringSet(key, stringObject, time, unit);
    }

    /**
     * 获取Object
     */
    public <T> T objectGet(String key, Class<T> clazz) {
        String stringObject = stringGet(key);
        return JackSonUtil.parse(clazz, stringObject);
    }

    //TODO String

    /**
     * 存入String
     */
    public void set(String key, String value) {
        stringRedisTemplate.opsForValue().set(key, value);
    }
    public void stringSet(String key, String value) {
        stringRedisTemplate.opsForValue().set(key, value);
    }
    /**
     * 如果不存在则设置
     */
    public Boolean stringSetNx(String key, String value) {
        return stringRedisTemplate.opsForValue().setIfAbsent(key, value);
    }

    public Boolean stringSetNx(String key, String value, int ttl, TimeUnit seconds) {
        return stringRedisTemplate.opsForValue().setIfAbsent(key, value, ttl, seconds);
    }

    public void stringSet(String key, Object value, Long time, TimeUnit unit) {
        stringRedisTemplate.opsForValue().set(key, JackSonUtil.encode(value), time, unit);
    }

    public List<String> stringMutiGet(List<String> key){
        return stringRedisTemplate.opsForValue().multiGet(key);
    }


    /**
     * 增加(自增长), 负数则为自减
     */
    public Long incrBy(String key, long increment) {
        return stringRedisTemplate.opsForValue().increment(key, increment);
    }

    public Long incrBy(String key,Long increment, Long time, TimeUnit unit){
        Long i = incrBy(key,increment);
        setEx(key,time,unit);
        return i;
    }


    /**
     * 获取指定 key 的值
     */
    public String get(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }

    /**
     * 获取指定 key 的值
     */
    public String stringGet(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }

    //TODO HASH

    public void hashPut(String key, Map map) {
        stringRedisTemplate.opsForHash().putAll(key, map);
    }
    public void hashPut(String key, Map map, Long time, TimeUnit unit) {
        stringRedisTemplate.opsForHash().putAll(key, map);
        stringRedisTemplate.expire(key,time,unit);
    }
    public void hashPut(String key, String field, String value) {
        stringRedisTemplate.opsForHash().put(key,field,value);
    }
    public void hashPut(String key,String filed,String value,Long time, TimeUnit unit){
        stringRedisTemplate.opsForHash().put(key,filed,value);
        stringRedisTemplate.expire(key,time,unit);
    }

    public Object hashGet(String key, String filed) {
        return stringRedisTemplate.opsForHash().get(key, filed);
    }

    public Map hashGetAll(String key) {
        return stringRedisTemplate.opsForHash().entries(key);
    }

    public Long hashIncrBy(String key, String filed, Integer num) {
        return stringRedisTemplate.opsForHash().increment(key, filed, num);
    }

    public void hashDel(String key,String filed){
        stringRedisTemplate.opsForHash().delete(key,filed);
    }

    public Long hashSize(String key){
        return stringRedisTemplate.opsForHash().size(key);
    }


    //TODO SET

    /**
     * set添加元素
     */
    public Long sAdd(String key, String... values) {
        return stringRedisTemplate.opsForSet().add(key, values);
    }

    public Long setSize(String key) {
        return stringRedisTemplate.opsForSet().size(key);
    }

    /**
     * set添加元素
     */
    public Long setAdd(String key, String... values) {
        return sAdd(key, values);
    }

    /**
     * set获取全部
     */
    public Set<String> setGetAll(String key){
        return stringRedisTemplate.opsForSet().members(key);
    }

    /**
     * set添加元素
     */
    public Long setAdd(String key, List<String> list) {
        return sAdd(key, list.toArray(new String[0]));
    }

    public void setAddOne(String key, String value, Long ttl, TimeUnit unit){
        stringRedisTemplate.opsForSet().add(key,value);
        stringRedisTemplate.expire(key,ttl,unit);
    }

    /**
     * 为set添加元素
     */
    public void setAdd(String key,Set<String> strings){
        stringRedisTemplate.opsForSet().add(key,strings.toArray(new String[0]));
    }

    /**
     * set移除元素
     */
    public Long sRemove(String key, Object... values) {
        return stringRedisTemplate.opsForSet().remove(key, values);
    }

    /**
     * set移除元素
     */
    public Long setRemove(String key, Object... values) {
        return stringRedisTemplate.opsForSet().remove(key, values);
    }

    /**
     * 判断集合是否包含value
     */
    public Boolean sIsMember(String key, Object value) {
        return stringRedisTemplate.opsForSet().isMember(key, value);
    }

    /**
     * 判断集合是否包含value
     */
    public Boolean setIsMember(String key, Object value) {
        return stringRedisTemplate.opsForSet().isMember(key, value);
    }
    public void setDel(String key,String value){
        stringRedisTemplate.opsForSet().remove(key,value);
    }


    //TODO LIST
    public Long listLeftAdd(String key, String value) {
        return stringRedisTemplate.opsForList().leftPush(key, value);
    }

    public Long listRightAdd(String key,String value ){
        return stringRedisTemplate.opsForList().leftPush(key,value);
    }
    public Long listRightAddAll(String key,String... values){
        return stringRedisTemplate.opsForList().rightPushAll(key,values);
    }
    public List<String> listGet(String key,Integer start,Integer limit){
        return stringRedisTemplate.opsForList().range(key,start*limit,(start+1)*limit - 1);
    }
    public List<String> listGetAll(String key) {
        return stringRedisTemplate.opsForList().range(key, 0, -1);
    }

    public void listLeftAddAndRightPop(String key, String value) {
        stringRedisTemplate.opsForList().rightPopAndLeftPush(key, value);
    }

    public Long listGetSize(String key) {
        return stringRedisTemplate.opsForList().size(key);
    }


    //TODO SortedSet
    public Boolean zSetAdd(String key, String value, Integer score) {
        return stringRedisTemplate.opsForZSet().add(key, value, score);
    }

    public Boolean zSetAdd(String key, String value, Long score) {
        return stringRedisTemplate.opsForZSet().add(key, value, score);
    }

    /**
     * 获取全部
     */
    public Set<ZSetOperations.TypedTuple<String>> zSetGetAll(String key) {
        return stringRedisTemplate.opsForZSet().rangeWithScores(key, 0, -1);
    }

    /**
     * 增加值
     */
    public Double zSetIncrey(String key, String value, Double score) {
        return stringRedisTemplate.opsForZSet().incrementScore(key, value, score);
    }

    /**
     * 获取指定元素在集合中的索引，索引从0开始
     */
    public Long zSetGetIndex(String key, String value) {
        return stringRedisTemplate.opsForZSet().reverseRank(key, value);
    }

    /**
     * 获取指定范围的数据
     */
    public List<String> zSetGetRange(String key, Integer start, Integer size) {
        Set<ZSetOperations.TypedTuple<String>> results = stringRedisTemplate.opsForZSet().rangeWithScores(key, start - 1, size);
        List<Double> list = new ArrayList<>();
        HashMap<Double,String> map = new HashMap<>();
        for (ZSetOperations.TypedTuple<String> result : results) {
            String value = result.getValue();
            double score = result.getScore();
            list.add(score);
            map.put(score,value);
        }
        Collections.sort(list,Collections.reverseOrder());
        List<String> list1 = new ArrayList<>();
        for(Double temp : list){
            list1.add(map.get(temp));
        }
        return list1;
    }
























    /**
     * 存入Object
     */
    public void setObject(String key, Object object) {
        String stringObject = JackSonUtil.encode(object);
        set(key, stringObject);
    }


    public Long lAddList(String key, String... values) {
        return stringRedisTemplate.opsForList().leftPushAll(key, values);
    }

    /**
     * 设置过期时间
     */
    public void setEx(String key, long timeout, TimeUnit unit) {
        stringRedisTemplate.expire(key, timeout, unit);
    }


    //TODO 查询

    /**
     * 获取Object
     */
    public <T> T getObject(String key, Class<T> clazz) {
        String stringObject = get(key);
        return JackSonUtil.parse(clazz, stringObject);
    }

    /**
     * 根据前缀获取所有的key
     */
    public Set<String> getListKey(String prefix) {
        return stringRedisTemplate.keys(prefix.concat("*"));
    }


    public List<String> getListKey(String key, Long start, Long limit) {
        List<String> list = new ArrayList<>();
        Long size = stringRedisTemplate.opsForList().size(key);
        Long count = start * limit;
        if (size < count) {
            for (long i = limit * (start - 1); i < size; i++) {
                list.add(stringRedisTemplate.opsForList().leftPop(key));
            }
        } else {
            for (long i = limit * (start - 1); i < count; i++) {
                list.add(stringRedisTemplate.opsForList().leftPop(key));
            }
        }
        return list;
    }

    public List<String> getListKeyNoDelete(String key, Long start, Long limit) {
        List<String> list = new ArrayList<>();
        Long size = stringRedisTemplate.opsForList().size(key);
        Long count = start * limit;
        if (size < count) {
            for (long i = limit * (start - 1); i < size; i++) {
                list.add(stringRedisTemplate.opsForList().index(key, i));
            }
        } else {
            for (long i = limit * (start - 1); i < count; i++) {
                list.add(stringRedisTemplate.opsForList().index(key, i));
            }
        }
        return list;
    }


    /**
     * 是否存在key
     */
    public Boolean hasKey(String key) {
        return stringRedisTemplate.hasKey(key);
    }

    /**
     * 删除key
     */
    public void delete(String key) {
        stringRedisTemplate.delete(key);
    }

    /**
     * 批量删除key
     */
    public void delete(Collection<String> keys) {
        stringRedisTemplate.delete(keys);
    }

    /**
     * 根据前缀删除缓存
     */
    public void allDel(String key) {
        Set<String> keys = stringRedisTemplate.keys(key + "*");
        stringRedisTemplate.delete(keys);
    }

}
