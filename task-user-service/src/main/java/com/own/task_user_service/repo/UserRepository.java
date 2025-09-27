package com.own.task_user_service.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import com.own.task_user_service.model.*;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
	@Query("{email :?0}")
	User findByEmail(String email);
}