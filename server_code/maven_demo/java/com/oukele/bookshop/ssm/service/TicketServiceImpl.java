package com.oukele.bookshop.ssm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oukele.bookshop.ssm.dao.ITicketDao;
import com.oukele.bookshop.ssm.entity.Ticket;

@Service
public class TicketServiceImpl implements TicketService {

	@Autowired
	private ITicketDao ticketDao;

	@Override
	public List<Ticket> listAll() {
		return ticketDao.listAll();
	}

	@Override
	public void addTicket(Ticket ticket) {
		ticketDao.addTicket(ticket);
	}

	@Override
	public List<Ticket> qryTicketStateInitList() {
		//查询状态为1的票务信息列表
		return ticketDao.qryTicketStateInitList();
	}

	@Override
	public void updateTicketState(Ticket ticket) {
		//更新票务信息状态为2，已上架状态
		ticketDao.updateTicketState(ticket);
	}

	@Override
	public void updateTicketCount(Ticket ticket) {
		ticketDao.updateTicketCount(ticket);
		
	}

	@Override
	public Ticket qryTicketByTicketId(Ticket ticket) {
		// TODO Auto-generated method stub
		return ticketDao.qryTicketByTicketId(ticket);
	}
}