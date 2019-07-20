package com.oukele.bookshop.ssm.task;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.elastos.dma.service.wallet.EthWalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import com.oukele.bookshop.ssm.entity.Ticket;
import com.oukele.bookshop.ssm.entity.UserTicket;
import com.oukele.bookshop.ssm.service.TicketService;
import com.oukele.bookshop.ssm.service.UserTicketService;

/**
 * 
 * @Title:定时任务执行计划
 * @Description:
 * @Author:zhengpeng
 * @Since:20190720
 * @Version:1.1.0
 * 
 */

@Controller
public class TaskController {
	
	private static final Logger logger = org.apache.log4j.Logger
			.getLogger(TaskController.class);
	
	@Autowired
	private TicketService ticketService;
	private UserTicketService userTicketService;
	private String nodeurl = "http://52.205.30.16:8545";
    private EthWalletService ethWalletService = new EthWalletService(nodeurl);

    @Scheduled(cron = "*/10 * 5 * * ?")
    public void getTransferStatus() throws IOException {
    	
    	List<Ticket> list = ticketService.qryTicketStateInitList();
    	Ticket ticket = new Ticket();
    	logger.info("*****************循环更新活动状态为已上架**********************");
    	if(list != null && list.size()>0){
    		for (int i = 0; i < list.size(); i++) {
    			String txid = list.get(i).getTrestaddress();
    			logger.info("查詢合約：【" + txid + "】狀態");
    			Boolean transferStatus = ethWalletService.getStatusByHash(txid);
    			if(transferStatus){
    				ticket.setHashaddress(txid);
    				ticket.setRemark("2");
    				logger.info("更新合約：【" + txid + "】狀態");
    				ticketService.updateTicketState(ticket);
    				logger.info("更新合約：【" + txid + "】狀態成功");
    			}
			}
    	}
    	
    	logger.info("*****************循环更新持票状态**********************");
    	List<UserTicket> ulist = userTicketService.listUserTicketStatus();
    	UserTicket userTicket = new UserTicket();
    	if(ulist != null && ulist.size()>0){
    		for (int i = 0; i < ulist.size(); i++) {
    			String txid = list.get(i).getHashaddress();
    			logger.info("查詢合約：【" + txid + "】狀態");
    			Boolean transferStatus = ethWalletService.getStatusByHash(txid);
    			if(transferStatus){
    				userTicket.setHaddress(txid);
    				userTicket.setDid(ulist.get(i).getDid());
    				String remark = list.get(i).getRemark();
    				if("0".equals(remark)){
    					userTicket.setRemark("1");//持有
    				}else if("2".equals(remark)){
    					userTicket.setRemark("3");//转让中
    				}else if("5".equals(remark)){
    					userTicket.setRemark("6");//转增持有中
    				}else{
    					userTicket.setRemark("0");
    				}
    				logger.info("更新合約：【" + txid + "】狀態");
    				userTicketService.updateUserTicketState(userTicket);
    				logger.info("更新合約：【" + txid + "】狀態成功");
    			}
			}
    	}
    	
    }
    
	@Scheduled(cron = "*/20 * 5 * * ?")
	// 每5秒钟执行一次
	public void gettime() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		logger.info(df.format(new Date()));
	}

}
