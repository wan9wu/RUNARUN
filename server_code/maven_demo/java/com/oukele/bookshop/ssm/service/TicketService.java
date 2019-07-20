package com.oukele.bookshop.ssm.service;

import java.util.List;
import java.util.Map;

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
	
	/**
	 * 查询待上架票务信息
	 * @return
	 */
	List<Ticket> qryTicketStateInitList();
	
	/**
	 * 更新票务信息的状态
	 * @param ticket
	 */
	void updateTicketState(Ticket ticket);
	
	/**
	 * 更新票務數量
	 * @param ticket
	 */
	void updateTicketCount(Ticket ticket);
	
	Ticket qryTicketByTicketId(Ticket ticket);

}
