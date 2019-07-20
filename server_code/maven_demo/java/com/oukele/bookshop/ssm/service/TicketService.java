package com.oukele.bookshop.ssm.service;

import java.util.List;

import com.oukele.bookshop.ssm.entity.Ticket;

public interface TicketService {
	/**
	 * 查询票务信息列表
	 * 
	 * @return
	 */
	List<Ticket> listAll();

	/**
	 * 票务活动新增
	 * 
	 * @param ticket
	 */
	void addTicket(Ticket ticket);
	
	List<Ticket> qryTicketStateInitList();

}
