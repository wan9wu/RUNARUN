package com.oukele.bookshop.ssm.service;

import java.util.List;

import com.oukele.bookshop.ssm.entity.UserTicket;

/**
 * 
 * 用户票务服务类
 * @author zhengpeng
 *
 */
public interface UserTicketService {
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
	
	
	/**
	 * 查询区块链待更新记录
	 * 
	 * @return
	 */
	List<UserTicket> listUserTicketStatus();
	
	/**
	 * 更新持票状态
	 * @param userticket
	 */
	void updateUserTicketState(UserTicket userticket);
}
