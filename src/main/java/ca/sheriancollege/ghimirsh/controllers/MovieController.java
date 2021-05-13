package ca.sheriancollege.ghimirsh.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ca.sheriancollege.ghimirsh.bean.Movie;
import ca.sheriancollege.ghimirsh.repository.MovieRepository;

@Controller
public class MovieController {

	@Autowired
	MovieRepository movieRepository;

	@RequestMapping(value = "/movie", method = RequestMethod.GET)
	public String goMovePage(Model model) {
		try {
			System.out.println("Testing.../move mapping of goMoviePage() method of Movie Controller...");
			List<Movie> movieList = movieRepository.findAll();
			System.out.println("Movies: " + movieList);
			model.addAttribute("movieList", movieList);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println(e.getStackTrace());
		}

		return "movie.html";
	}
}
