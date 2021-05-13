package ca.sheriancollege.ghimirsh.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name="theater_seat")
@Table(name="theater_seat")
public class Seat implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long id;
	
	@Column(name="seat_no")
	public String seatNo;

	public Seat() {
		
	}

	public Seat(Long id, String seatNo) {
		this.id = id;
		this.seatNo = seatNo;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSeatNo() {
		return seatNo;
	}

	public void setSeatNo(String seatNo) {
		this.seatNo = seatNo;
	}

	@Override
	public String toString() {
		return "Seat [id=" + id + ", seatNo=" + seatNo + "]";
	}
	
	
	
	
}
