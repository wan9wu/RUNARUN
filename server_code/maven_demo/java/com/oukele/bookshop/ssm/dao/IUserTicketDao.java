package com.oukele.bookshop.ssm.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.oukele.bookshop.ssm.entity.UserTicket;

@Repository
public interface IUserTicketDao {

	/**
	 * 查询用户票务信息列表
	 * 
	 * @return
	 */
	List<UserTicket> listTicketByDid(UserTicket userticket);
	
	/**
	 * 查询待更新状态的链上记录
	 * @return
	 */
	List<UserTicket> listUserTicketStatus();
	
	/**
	 * 用户参加票务活动
	 * 
	 * @param ticket
	 */
	void addUserTicket(UserTicket userticket);
	
	/**
	 * 异步更新票务持有状态
	 * @param userticket
	 */
	void updateTicketState(UserTicket userticket);
}