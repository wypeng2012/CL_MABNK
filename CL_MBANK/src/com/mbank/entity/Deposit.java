package com.mbank.entity;


public class Deposit {
	private long deposit_id;
	private long client_id;
	private double balance;
	private String type;
	private double estimated_balance;
	private String opening_date;
	private String closing_date;
	
	public Deposit(){}
	
	public Deposit(long deposit_id, long client_id, double balance,
			String type, long estimated_balance, String opening_date,
			String closing_date) {
		this.deposit_id = deposit_id;
		this.client_id = client_id;
		this.balance = balance;
		this.type = type;
		this.estimated_balance = estimated_balance;
		this.opening_date = opening_date;
		this.closing_date = closing_date;
	}

	public long getDeposit_id() {
		return deposit_id;
	}
	public void setDeposit_id(long deposit_id) {
		this.deposit_id = deposit_id;
	}
	public long getClient_id() {
		return client_id;
	}
	public void setClient_id(long client_id) {
		this.client_id = client_id;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public double getEstimated_balance() {
		return estimated_balance;
	}
	public void setEstimated_balance(double estimated_balance) {
		this.estimated_balance = estimated_balance;
	}
	public String getOpening_date() {
		return opening_date;
	}
	public void setOpening_date(String opening_date) {
		this.opening_date = opening_date;
	}
	public String getClosing_date() {
		return closing_date;
	}
	public void setClosing_date(String closing_date) {
		this.closing_date = closing_date;
	}
	
	
}
