package ca.sheriancollege.ghimirsh.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

//import ca.sheriancollege.ghimirsh.bean.Movie;
import ca.sheriancollege.ghimirsh.bean.Seat;

public interface SeatRepository extends JpaRepository<Seat, Long> {

	@Query("from theater_seat")
	List<Seat> findAll();
}
