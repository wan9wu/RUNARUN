package com.oukele.bookshop.ssm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oukele.bookshop.ssm.dao.IBookDao;
import com.oukele.bookshop.ssm.entity.Book;

@Service
public class BookServiceImpl implements BookService {

	// 使用 dao 中的接口
	@Autowired
	private IBookDao bookDao;

	@Override
	public List<Book> listAll() {
		return bookDao.listAll();
	}
}