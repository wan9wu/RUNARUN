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

}
