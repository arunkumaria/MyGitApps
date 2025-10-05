package com.own.bis.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.own.bis.model.Book;

@Repository
public interface BookRepo extends MongoRepository<Book, String> {
	Book findByBookid(String bookid);

	void deleteByBookid(String bookid);
}
