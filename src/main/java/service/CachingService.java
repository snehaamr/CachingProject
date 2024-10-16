package service;

import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import entity.Cache;
import repository.CachingRepository;

@Service
public class CachingService {
	
	Logger logger = LogManager.getLogger(CachingService.class);
	
	@Autowired
	private CachingRepository cacheRepository;

	ReentrantLock rl = new ReentrantLock();
	
	@Cacheable(value = "caches", key = "#id")
	public Optional<Cache> getCacheById(Long id) {
		logger.info("Retrieving cache entry by id--calling CacheRepository");
		Optional<Cache> result;
		rl.lock();
		try {
			result = cacheRepository.findById(id);
			rl.unlock();
		} catch(Exception ex) {
			logger.debug(ex.getMessage());
		}

		return result;
	}
	
	@Cacheable(value = "caches", key = "#id")
	public Cache saveCache(Cache cache) {
		logger.info("Saving cache entry--calling CacheRepository");
		rl.lock();
		
		return cacheRepository.save(cache);
		rl.unlock();
	}
    
	@CacheEvict(cacheNames="caches", allEntries=true) 
	public void deleteCache() {
		logger.info("Deleting all cache entries--calling CacheRepository");
		cacheRepository.deleteAll();
	}
	
	@CacheEvict(cacheNames="caches", key="#id")
	public void deleteById(Long id) {
		logger.info("Deleting cache entry by id--calling CacheRepository");
		 cacheRepository.deleteById(id);
	}
	
	@CacheEvict(cacheNames="caches", allEntries=true)
	public void clearCache() {
		logger.info("Clearing all cache entries from cache");
	}
}
