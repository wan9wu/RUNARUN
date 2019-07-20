package com.oukele.bookshop.ssm.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.oukele.bookshop.ssm.entity.Ticket;

@Repository
public interface ITicketDao {
	/**
	 * 查询票务信息列表
	 * 
	 * @return
	 */
	List<Ticket> listAll();

	/**
	 * 新增票务活动
	 * 
	 * @param ticket
	 */
	void addTicket(Ticket ticket);
}