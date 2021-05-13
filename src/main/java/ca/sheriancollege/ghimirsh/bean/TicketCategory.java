package ca.sheriancollege.ghimirsh.bean;

import java.io.Serializable;

public class TicketCategory implements Serializable  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String ticketCategory;
	private double price;
	public TicketCategory() {
		super();
	}
	public TicketCategory(String ticketCategory, double price) {
		super();
		this.ticketCategory = ticketCategory;
		this.price = price;
	}
	public String getTicketCategory() {
		return ticketCategory;
	}
	public void setTicketCategory(String ticketCategory) {
		this.ticketCategory = ticketCategory;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	@Override
	public String toString() {
		return "TicketCategory [ticketCategory=" + ticketCategory + ", price=" + price + "]";
	}
	
}
