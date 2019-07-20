package com.oukele.bookshop.ssm.web;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.oukele.bookshop.ssm.entity.Ticket;
import com.oukele.bookshop.ssm.entity.UserTicket;
import com.oukele.bookshop.ssm.service.TicketService;
import com.oukele.bookshop.ssm.service.UserTicketService;

/**
 * 
 * @Title:用户报名活动
 * @Description:
 * @Author:zhengpeng
 * @Since:20190720
 * @Version:1.1.0
 * 
 */
@Controller
public class UserTicketController {

	private static final Logger logger = org.apache.log4j.Logger
			.getLogger(UserTicketController.class);

	@Autowired
	private UserTicketService userTicketService;
	
	@Autowired
	private TicketService ticketService;
	
	/**
	 * 活动报名
	 * 
	 * @param userid
	 * @param username
	 * @param did
	 * @param ticketdid
	 * @param haddress
	 * @param taddress
	 * @param caddress
	 * @param publickey
	 * @param privatekey
	 * @param remark
	 * @throws Exception
	 */
	@RequestMapping(value = "/addUserTicket")
	@ResponseBody
	public void addUserTicket(String userid, String username, String did,
			String ticketdid, String haddress, String taddress,
			String caddress, String publickey, String privatekey, String remark)
			throws Exception {
		logger.info(("用户:[" + username + "]报名参加[" + ticketdid + "]活动"));
		UserTicket userTicket = new UserTicket();
		userTicket.setUserid(userid);
		userTicket.setUsername(username);
		userTicket.setDid(did);
		userTicket.setTicketdid(ticketdid);
		userTicket.setHaddress(haddress);
		userTicket.setTaddress(taddress);
		userTicket.setCaddress(caddress);
		userTicket.setPrivatekey(privatekey);
		userTicket.setPublickey(publickey);
		userTicket.setRemark("1");
		
		/**
		 *数据库事务一致性后续加入
		 */
		userTicketService.addUserTicket(userTicket);
		Ticket ticket = new Ticket();
		ticket.setId(ticketdid);
		ticketService.updateTicketCount(ticket);
		logger.info("****************活动报名成功****************");
	}
	
	
	/**
	 * 查询票务信息列表
	 * 
	 * @return
	 */
	@RequestMapping(value = "/qryUserTicketList")
	@ResponseBody
	public List<Ticket> qryUserTicketList(String did) {
		logger.info("查询用户did为【" + did + "】的用户参加活动信息列表");
		UserTicket userTicket = new UserTicket();
		userTicket.setDid(did);
		List<Ticket> l = new ArrayList<Ticket>();
		List<UserTicket> list = userTicketService.listTicketByDid(userTicket);
		Ticket ticket = new Ticket();
		if(list != null && list.size()>0){
			for (int i = 0; i < list.size(); i++) {
				ticket.setId(list.get(i).getTicketdid());
				Ticket tk = ticketService.qryTicketByTicketId(ticket);
				l.add(tk);
			}
		}
		logger.info("****************持有活动列表查询完成****************");
		return l;
	}
}