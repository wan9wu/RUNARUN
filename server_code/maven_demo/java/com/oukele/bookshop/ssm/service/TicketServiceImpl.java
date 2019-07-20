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
		// TODO Auto-generated method stub
		return null;
	}
}