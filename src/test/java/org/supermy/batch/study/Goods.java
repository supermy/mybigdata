package org.supermy.batch.study;


import java.util.Date;

/**
 * 商品信息类.
 */
public class Goods {
    /** isin号 */
    private String isin;
    /** 数量 */
    private int quantity;
    /** 价格 */
    private double price;
    /** 客户 */
    private String customer;
    /** 购入日期 */
    private Date buyDay;
	/**
	 * @return the isin
	 */
	public String getIsin() {
		return isin;
	}
	/**
	 * @param isin the isin to set
	 */
	public void setIsin(String isin) {
		this.isin = isin;
	}
	/**
	 * @return the quantity
	 */
	public int getQuantity() {
		return quantity;
	}
	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	/**
	 * @return the price
	 */
	public double getPrice() {
		return price;
	}
	/**
	 * @param price the price to set
	 */
	public void setPrice(double price) {
		this.price = price;
	}
	/**
	 * @return the customer
	 */
	public String getCustomer() {
		return customer;
	}
	/**
	 * @param customer the customer to set
	 */
	public void setCustomer(String customer) {
		this.customer = customer;
	}
	/**
	 * @return the buyDay
	 */
	public Date getBuyDay() {
		return buyDay;
	}
	/**
	 * @param buyDay the buyDay to set
	 */
	public void setBuyDay(Date buyDay) {
		this.buyDay = buyDay;
	}
    
    
}