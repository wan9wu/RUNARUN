package com.oukele.bookshop.ssm.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.oukele.bookshop.ssm.entity.Book;

@Repository
public interface IBookDao {
	List<Book> listAll();
}