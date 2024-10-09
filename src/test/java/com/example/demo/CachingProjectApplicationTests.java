package com.example.demo;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;

import app.CachingProjectApplication;
import entity.Cache;
import repository.CachingRepository;
import service.CachingService;

@SpringBootTest(classes = CachingProjectApplication.class)
class CachingProjectApplicationTests {
 
	@Autowired
	CacheManager cacheManager;
	
	@Autowired
	CachingRepository cacheRepository;
	
	@Autowired
	CachingService cacheService;
	
	@BeforeEach
	void setUp() {
	
		Cache c1 = new Cache((long) 1234, "cache1", "this is a test");
		Cache c2 = new Cache((long) 2345, "cache2", "this is a test");
		Cache c3 = new Cache((long) 3456, "cache3", "this is a test");
		
		cacheRepository.save(c1);
		cacheRepository.save(c2);
		cacheRepository.save(c3);
	}
	
	 private Optional<Cache> getCacheEntryById(Long id) {
	        return Optional.ofNullable(cacheManager.getCache("caches")).map(c -> c.get(id, Cache.class));
	    }
	
	 @Test
	 void getCacheEntryByID() {
		 Optional<Cache> c = cacheService.getCacheById((long) 1234);
		 
		 assertEquals(c, getCacheEntryById((long) 1234));
	 }
	 
	 @Test
	 void getCacheEntryByID_NotPresent() {
		 Optional<Cache> c = cacheService.getCacheById((long) 1111);
		 
		 assertEquals(c, getCacheEntryById((long) 1111));
		 assertEquals(c, null);
	 }
	 
	 @Test
	 void saveCacheEntry() {
		 Cache c4 = new Cache((long) 4567, "cache3", "this is a test");
		 cacheService.saveCache(c4);
		 
		 Optional<Cache> c5 = cacheService.getCacheById((long) 4567);
		 
		 assertEquals(c5, getCacheEntryById((long) 4567));
	 }
	 
	 @Test
	 void getCacheEntryByID_ClearCache() {
		 Optional<Cache> c = cacheService.getCacheById((long) 1234);
		 
		 cacheService.clearCache();
		 
		 assertNotEquals(c, getCacheEntryById((long) 1234));
	 }
	 
	 @Test
	 void getCacheEntryByID_DeleteCache() {
		 Optional<Cache> c = cacheService.getCacheById((long) 1234);
		 assertEquals(c, getCacheEntryById((long) 1234));
		 
		 cacheService.deleteCache();
		 
		 Optional<Cache> c1 = cacheService.getCacheById((long) 1234);
		 assertEquals(c1, null);
		 assertEquals(getCacheEntryById((long) 1234), null);
	 }

}
