package controller;

import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import entity.Cache;
import service.CachingService;

@RestController
public class CachingController {
	
	@Autowired
	private CachingService cacheService;
	
	Logger logger = LogManager.getLogger(CachingController.class);
	
	@PostMapping("/cache")
	Cache postCacheEntry(@RequestBody Cache cache) {
		logger.info("Saving cache entry--calling CacheService");
		return cacheService.saveCache(cache);
	}
	
	@DeleteMapping("/cache/{id}")
	void deleteCacheEntry(@PathVariable Long id) {
		logger.info("Deleting cache entry--calling CacheService");
		cacheService.deleteById(id);
	}
	
	@DeleteMapping("/cache")
	void deleteCache() {
		logger.info("Deleting all cache entries--calling CacheService");
		cacheService.deleteCache();
	}
	
	@GetMapping("/cache/{id}")
	Optional<Cache> getCacheEntry(@PathVariable Long id) {
		logger.info("Retrieving cache entry by id--calling CacheService");
		return cacheService.getCacheById(id);
	}
	
	@DeleteMapping("/cache/clear")
	void clearCache() {
		logger.info("Clearing all cache entries from the cache--calling CacheService");
		cacheService.clearCache();
	}

}