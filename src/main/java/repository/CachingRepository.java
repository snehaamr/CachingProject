package repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import entity.Cache;

@Repository
public interface CachingRepository extends JpaRepository<Cache,Long> {

}