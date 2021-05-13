package ca.sheriancollege.ghimirsh.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ca.sheriancollege.ghimirsh.bean.Ticket;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
	@Query("from ticket where show_time =?1 and show_date=?2")
	List<Ticket> getTicketsByShowtimeAndShowdate(String showTime, LocalDate showDate);
	
	@Query("from ticket where show_time =?1")
	List<Ticket> getTicketsByShowTime(String showTime);
	
	@Query("from ticket where show_date =?1")
	List<Ticket> getTicketsByShowDate(LocalDate showDate);
	
	
	@Query("from ticket where show_time =?1 and show_date=?2")
	List<Ticket> getSoldTickets(String showTime, LocalDateTime showDate);
}
