package com.mbank.entity;



public class Activity {
	private long id;
	private long client_id;
	private double amount;
	private String activity_date;
	private double commission;
	private String description;

	public Activity() {
	}

	public Activity(long id, long client_id, double amount,
			String activity_date, double commission, String description) {

		this.id = id;
		this.client_id = client_id;
		this.amount = amount;
		this.activity_date = activity_date;
		this.commission = commission;
		this.description = description;
	}
   
	public Activity(long client_id, double amount, double commission,
			String description) {
		super();
		this.client_id = client_id;
		this.amount = amount;
		this.commission = commission;
		this.description = description;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getClient_id() {
		return client_id;
	}

	public void setClient_id(long client_id) {
		this.client_id = client_id;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getActivity_date() {
		return activity_date;
	}

	public void setActivity_date(String activity_date) {
		this.activity_date = activity_date;
	}

	public double getCommission() {
		return commission;
	}

	public void setCommission(double commission) {
		this.commission = commission;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
