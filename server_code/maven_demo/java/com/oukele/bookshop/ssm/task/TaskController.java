package com.oukele.bookshop.ssm.task;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import com.oukele.bookshop.ssm.service.TicketService;

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
	
//	private String nodeurl = "http://52.205.30.16:8545";
//    private EthWalletService ethWalletService = new EthWalletService(nodeurl);
//
//    private BigInteger gasPrice=BigInteger.valueOf(1000000000);
//    private BigInteger gasLimit=BigInteger.valueOf(60000);
    
    
    @Scheduled(cron = "* */50 * * * ?")
    public void getTransferStatus() throws IOException {
    	
    	ticketService.qryTicketStateInitList();
    	
//        String txid = "0xb4628a779ce00cb5dd0b321325554802f701600516fed8c292511c831151f06a";
//        Boolean transferStatus = ethWalletService.getStatusByHash(txid);
//        System.out.println(transferStatus);
    }
    
    
	@Scheduled(cron = "*/50 * * * * ?")
	// 每5秒钟执行一次
	public void gettime() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		logger.info(df.format(new Date()));
	}

}
