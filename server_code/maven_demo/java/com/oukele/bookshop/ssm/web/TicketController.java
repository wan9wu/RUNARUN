package com.oukele.bookshop.ssm.web;

import java.util.List;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.oukele.bookshop.ssm.entity.Ticket;
import com.oukele.bookshop.ssm.service.TicketService;

/**
 * 
 * @Title:票务项目控制
 * @Description:
 * @Author:zhengpeng
 * @Since:20190720
 * @Version:1.1.0
 * 
 */

@Controller
public class TicketController {

	private static final Logger logger = org.apache.log4j.Logger
			.getLogger(TicketController.class);

	@Autowired
	private TicketService ticketService;

	/**
	 * 新增票务信息
	 * 
	 * @param id
	 * @param name
	 * @param production
	 * @param condition
	 * @param price
	 * @param count
	 * @param remark
	 * @param hashaddress
	 * @param trestaddress
	 * @param contractaddress
	 * @throws Exception
	 */
	@RequestMapping(value = "/addTicket")
	@ResponseBody
	public void addTicket(String id, String name, String production,
			String condition, String price, String count, String remark,
			String hashaddress, String trestaddress, String contractaddress)
			throws Exception {
		logger.info(("****************开始新增票务信息****************"));
		Ticket ticket = new Ticket();
		ticket.setId(id);
		ticket.setName(name);
		ticket.setCondition(condition);
		ticket.setProduction(production);
		ticket.setPrice(price);
		ticket.setCount(count);
		ticket.setHashaddress(hashaddress);
		ticket.setTrestaddress(trestaddress);
		ticket.setContractaddress(contractaddress);
		ticketService.addTicket(ticket);
		logger.info("****************票务信息新增成功****************");
	}

	/**
	 * 查询票务信息列表
	 * 
	 * @return
	 */
	@RequestMapping(value = "/qryTicketList")
	@ResponseBody
	public List<Ticket> getJsonList() {
		logger.info("****************查询票务信息列表****************");
		List<Ticket> list = ticketService.listAll();
		logger.info("****************票务信息列表查询成功****************");
		return list;
	}

	/**
	 * 测试数据
	 * 
	 * @return
	 */
	@RequestMapping(value = "/tickets")
	public ModelAndView getList() {
		ModelAndView mv = new ModelAndView("ticketlist");
		List<Ticket> list = ticketService.listAll();
		mv.addObject("ticket", list);
		return mv;
	}

}