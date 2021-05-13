package ca.sheriancollege.ghimirsh.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ca.sheriancollege.ghimirsh.bean.Movie;


public interface MovieRepository extends JpaRepository<Movie, Long>  {

	@Query("from Movie")
	List<Movie> findAll();
}
