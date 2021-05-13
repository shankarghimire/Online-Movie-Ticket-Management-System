package ca.sheriancollege.ghimirsh.bean;

import java.io.Serializable;

import java.time.LocalDateTime;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

@Entity(name="ticket")
@Table(name="ticket")
public class Ticket implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="movie_name")
	private String movieName;
	
	@Column(name="theater_name")
	private String theaterName;
	
	@Column(name="seat_no")
	private String seatNo;
	
	@Column(name="show_time")
	private String showTime;
	
	@Column(name="show_date")
	 @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	private LocalDateTime showDate;
	
	@Column(name="ticket_category")
	private String ticketCategory;
	
	@Column(name="price")
	private double price;
	
	@Column(name="purchase_date")
	 @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	private LocalDateTime purchaseDate = LocalDateTime.now();
	
	@Column(name="purchase_by")
	private String purchaseBy;
	
	
	public Ticket() {
		
	}
	public Ticket(Long id, String movieName, String theaterName, String seatNo, String showTime, LocalDateTime showDate,
			String ticketCategory, double price, String purchaseBy, LocalDateTime purchaseDate) {
		super();
		this.id = id;
		this.movieName = movieName;
		this.theaterName = theaterName;
		this.seatNo = seatNo;
		this.showTime = showTime;
		this.showDate = showDate;
		this.ticketCategory = ticketCategory;
		this.price = price;
		this.purchaseBy = purchaseBy;
		this.purchaseDate = purchaseDate;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getMovieName() {
		return movieName;
	}
	public void setMovieName(String movieName) {
		this.movieName = movieName;
	}
	public String getTheaterName() {
		return theaterName;
	}
	public void setTheaterName(String theaterName) {
		this.theaterName = theaterName;
	}
	public String getSeatNo() {
		return seatNo;
	}
	public void setSeatNo(String seatNo) {
		this.seatNo = seatNo;
	}
	public String getShowTime() {
		return showTime;
	}
	public void setShowTime(String showTime) {
		this.showTime = showTime;
	}
	public LocalDateTime getShowDate() {
		return showDate;
	}
	public void setShowDate(LocalDateTime showDate) {
		this.showDate = showDate;
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
	public String getPurchaseBy() {
		return purchaseBy;
	}
	public void setPurchaseBy(String purchaseBy) {
		this.purchaseBy = purchaseBy;
	}
	public LocalDateTime getPurchaseDate() {
		return purchaseDate;
	}
	public void setPurchaseDate(LocalDateTime purchaseDate) {
		this.purchaseDate = purchaseDate;
	}
	@Override
	public String toString() {
		return "Ticket [id=" + id + ", movieName=" + movieName + ", theaterName=" + theaterName + ", seatNo=" + seatNo
				+ ", showTime=" + showTime + ", showDate=" + showDate + ", ticketCategory=" + ticketCategory
				+ ", price=" + price + ", purchaseBy=" + purchaseBy + ", purchaseDate=" + purchaseDate + "]";
	}
	
	
	
}
