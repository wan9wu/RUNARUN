package com.oukele.bookshop.ssm.entity;

public class Book {
	private String bookid;
	private String bookname;

	public Book() {
	}

	public Book(String bookname, String bookid) {
		this.bookname = bookname;
		this.bookid = bookid;
	}

	public String getBookid() {
		return bookid;
	}

	public void setBookid(String bookid) {
		this.bookid = bookid;
	}

	public String getbookname() {
		return bookname;
	}

	public void setbookname(String bookname) {
		this.bookname = bookname;
	}

	@Override
	public String toString() {
		return "Book{" + "bookid=" + bookid + ", bookname='" + bookname + '\''
				+ '}';
	}
}