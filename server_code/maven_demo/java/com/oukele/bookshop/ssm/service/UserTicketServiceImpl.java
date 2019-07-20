package com.oukele.bookshop.ssm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oukele.bookshop.ssm.dao.IUserTicketDao;
import com.oukele.bookshop.ssm.entity.UserTicket;

@Service
public class UserTicketServiceImpl implements UserTicketService {

	@Autowired
	private IUserTicketDao userTicketDao;
	
	/**
	 * 已参加活动
	 */
	@Override
	public List<UserTicket> listTicketByDid(UserTicket userticket) {
		return userTicketDao.listTicketByDid(userticket);
	}
	
	/**
	 * 活动报名
	 */
	@Override
	public void addUserTicket(UserTicket userticket) {
		userTicketDao.addUserTicket(userticket);
	}

}