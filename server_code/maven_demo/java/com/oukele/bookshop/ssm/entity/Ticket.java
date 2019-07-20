package com.oukele.bookshop.ssm.entity;

/**
 * 票务信息实体类
 * 
 * @author zhengpeng
 * 
 */
public class Ticket {
	private String id;
	private String name;
	private String production;
	private String condition;
	private String price;
	private String count;
	/*票務狀態： 默认-0     已发布-1   已上架-2 */
	private String remark;
	private String hashaddress;
	private String trestaddress;
	private String contractaddress;

	public Ticket() {

	}

	public Ticket(String id, String name, String production, String condition,
			String price, String count, String remark, String hashaddress,
			String trestaddress, String contractaddress) {
		this.id = id;
		this.name = name;
		this.production = production;
		this.condition = condition;
		this.price = price;
		this.count = count;
		this.remark = remark;
		this.remark = hashaddress;
		this.remark = trestaddress;
		this.remark = contractaddress;

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProduction() {
		return production;
	}

	public void setProduction(String production) {
		this.production = production;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getHashaddress() {
		return hashaddress;
	}

	public void setHashaddress(String hashaddress) {
		this.hashaddress = hashaddress;
	}

	public String getTrestaddress() {
		return trestaddress;
	}

	public void setTrestaddress(String trestaddress) {
		this.trestaddress = trestaddress;
	}

	public String getContractaddress() {
		return contractaddress;
	}

	public void setContractaddress(String contractaddress) {
		this.contractaddress = contractaddress;
	}

}