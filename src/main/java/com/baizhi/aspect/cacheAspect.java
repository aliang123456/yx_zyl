package com.baizhi.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class cacheAspect {
    @Autowired
    private RedisTemplate redisTemplate;

    //环绕通知
    @Around("execution(* com.baizhi.service.*Impl.quary*(..))")
    public Object AddCache(ProceedingJoinPoint joinPoint){
        StringBuilder sb=new StringBuilder();
        //获取类全路径
        String name = joinPoint.getTarget().getClass().getName();
        //获取方法名
        String name1 = joinPoint.getSignature().getName();
        sb.append(name).append(name1);
        //实参值
        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            sb.append(arg);
        }
        //操作redis中hash类型
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        HashOperations hashOperations = redisTemplate.opsForHash();

        Object obj=null;
        //判断redis中有没有
        if(hashOperations.hasKey(name,sb.toString())){
            //有从redis中获取
            obj=hashOperations.get(name,sb.toString());
        }else{
            //没有放行
            try{
                obj = joinPoint.proceed();
            }catch(Throwable throwable){
                throwable.printStackTrace();
            }
            //在redis中存储
            hashOperations.put(name,sb.toString(),obj);
        }
        return obj;
    }

    //后置通知
    @After("@annotation(com.baizhi.annotation.DeleteCache)")
    public void delCache(JoinPoint joinPoint){
        String name = joinPoint.getTarget().getClass().getName();
        //删除
        redisTemplate.delete(name);

    }

}
