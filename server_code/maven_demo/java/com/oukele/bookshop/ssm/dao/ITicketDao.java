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
	 * 更新票务庫存信息
	 * @param ticket
	 */
	void updateTicketCount(Ticket ticket);
	
	
	Ticket qryTicketByTicketId(Ticket ticket);
	
}