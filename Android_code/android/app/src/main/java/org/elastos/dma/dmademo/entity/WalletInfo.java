package org.elastos.dma.dmademo.entity;

import java.io.Serializable;
import java.util.ArrayList;


/**
 * @author zhouxianxian 资产余额 老版本
 *
 */
public class WalletInfo implements Serializable {
	

	/**
	 * 为了去掉警告 serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	private static WalletInfo walletInfo;
	private static ArrayList<WalletInfo> walletInfoList;
	private String logo,name,balance,tokenAddress,walletAddress;
	private double worth;
	private double ethbalance;
	public static WalletInfo getInstance(){
		if(walletInfo ==null){
			walletInfo = new WalletInfo();
		}
		return walletInfo;
	}

	public static ArrayList<WalletInfo> getWalletInfoList() {
		return walletInfoList;
	}

	public static void setWalletInfoList(ArrayList<WalletInfo> walletInfoList) {
		WalletInfo.walletInfoList = walletInfoList;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBalance() {
		return balance;
	}

	public void setBalance(String balance) {
		this.balance = balance;
	}

	public String getTokenAddress() {
		return tokenAddress;
	}

	public void setTokenAddress(String tokenAddress) {
		this.tokenAddress = tokenAddress;
	}

	public double getWorth() {
		return worth;
	}

	public void setWorth(double worth) {
		this.worth = worth;
	}

	public double getEthbalance() {
		return ethbalance;
	}

	public void setEthbalance(double ethbalance) {
		this.ethbalance = ethbalance;
	}

	public String getWalletAddress() {
		return walletAddress;
	}

	public void setWalletAddress(String walletAddress) {
		this.walletAddress = walletAddress;
	}
}
