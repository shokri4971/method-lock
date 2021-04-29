package com.lock.test;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import com.amirsh71.methodlock.core.LockCacheService;
import com.amirsh71.methodlock.core.inmemory.LockCacheInMemoryServiceImpl;
import com.lock.test.LockService.Request;
import com.lock.test.LockTest.LockTestConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { LockTestConfig.class })
public class LockTest
{

    
    @Autowired
    LockService lockService;
    
    @Test
    public void testLock() throws IOException   
    {
        Assert.notNull(lockService);
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@");
        CompletableFuture.runAsync(()->{
            System.out.println("thread 1");
            lockService.test(new Request("user1", "food1"));
        });
        CompletableFuture.runAsync(()->{
            System.out.println("thread 2");
            lockService.test(new Request("user2", "food2"));
        });
    }

    public static class LockTestConfig
    {
        @Bean
        public LockCacheService lockCacheService()
        {
            return new LockCacheInMemoryServiceImpl();
        }

        @Bean
        LockService lockService()
        {
            return new LockService();
        }
    }

}