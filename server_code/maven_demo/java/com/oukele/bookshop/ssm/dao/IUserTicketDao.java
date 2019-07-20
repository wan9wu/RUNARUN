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
	 * 用户参加票务活动
	 * 
	 * @param ticket
	 */
	void addUserTicket(UserTicket userticket);
}