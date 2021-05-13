package ca.sheriancollege.ghimirsh.controllers;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.hibernate.validator.internal.util.stereotypes.Lazy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import ca.sheriancollege.ghimirsh.bean.Movie;
import ca.sheriancollege.ghimirsh.bean.User;
import ca.sheriancollege.ghimirsh.repository.MovieRepository;
import ca.sheriancollege.ghimirsh.repository.UserRepository;

@Controller
@RequestMapping("/admin")
public class AdminController {

	@Lazy
	@Autowired
	UserRepository userRepository;

	@Lazy
	@Autowired
	private MovieRepository movieRepository;

	@Lazy
	@Autowired
	BCryptPasswordEncoder passwordEncoder;

	@RequestMapping(value = "/movie-config", method = RequestMethod.GET)
	public String goAdminConfig() {
		System.out.println("Testing /admin mapping from goAdminConfig() method in AdminController...");
		return "/admin/admin-dashboard.html";
	}

	@RequestMapping(value = "/view-movies", method = RequestMethod.GET)
	public String goViewMovies(Model model) {
		try {
			System.out.println("============================================================");
			List<Movie> listMovies = movieRepository.findAll();
			for (Movie m : listMovies) {
				System.out.println(m.toString());
			}
			System.out.println("============================================================");
			model.addAttribute("listMovies", listMovies);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return "/admin/view-movies.html";
	}

	@RequestMapping(value = "/add-movie", method = RequestMethod.GET)
	public String goAddMovie(Model model) {
		System.out.println("Testing /admin/add-movie mapping from goAddMovie() method in AdminController...");

		return "/admin/add-movie.html";
	}

	@RequestMapping(value = "/save-movie", method = RequestMethod.POST)
	public String goSaveMovie(Model model, @ModelAttribute("movie") Movie movie,
			@RequestParam("movieImagePath") MultipartFile movieImagePath) {
		try {
			System.out.println("Testing /admin/save-movie mapping from goSaveMovie() method in AdminController...");
			System.out.println("Movie : " + movie.toString());

			// Processing and uploadinf image file

			if (movieImagePath.isEmpty()) {
				System.out.println("Image path is empty!");
			} else {
				// upload the file to folder
				movie.setImagePath(movieImagePath.getOriginalFilename());
				File saveFile = new ClassPathResource("static/images/movie-posters").getFile();
				Path path = Paths
						.get(saveFile.getAbsolutePath() + File.separator + movieImagePath.getOriginalFilename());
				Files.copy(movieImagePath.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
				System.out.println("Image has been uploaded");
			}

			// Save object-movie to database
			movieRepository.save(movie);

			System.out.println("The new ovie has been successfully added to the database!");

		} catch (Exception e) {

			System.out.println(e.getMessage());
		}

		return "redirect:/admin/view-movies";
	}

	@RequestMapping(value = "/edit-movie/{id}", method = RequestMethod.GET)
	public String goEditMovie(Model model, @PathVariable(value = "id") Long id) {

		try {
			System.out.println("Testing /admin/edit-movie mapping from goEditMovie() method in AdminController...");
			Optional<Movie> optionalMovie = movieRepository.findById(id);
			Movie movie = null;
			if (optionalMovie.isPresent()) {
				movie = optionalMovie.get();
			} else {
				System.out.println("Movie does not exists in the database: " + id);
			}

			model.addAttribute("movie", movie);
		} catch (Exception e) {
			System.out.println(e.getStackTrace());
		}

		return "/admin/edit-movie.html";
	}

	@RequestMapping(value = "/process-edit-movie", method = RequestMethod.GET)
	public String goProcessEditMovie(Model model, @ModelAttribute("movie") Movie movie) {
		try {
			System.out.println(
					"Testing /admin/process-edit-movie mapping from goProcessEditMovie() method in AdminController...");
			System.out.println("Movie: " + movie);

			// Updating movie in the database
			movieRepository.save(movie);
			System.out.println("Movie has been successfully updated in the database!");

		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println(e.getStackTrace());
		}

		return "redirect:/admin/view-movies";
	}

	@RequestMapping(value = "/delete-movie/{id}", method = RequestMethod.GET)
	public String goDeleteMovie(Model model, @PathVariable(value = "id") Long id) {

		try {
			System.out.println("Testing /admin/delete-movie mapping from goDeleteMovie() method in AdminController...");

			// Delete Movie from database
			movieRepository.deleteById(id);
			System.out.println("The movie has been deleted from database!");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println(e.getStackTrace());
		}

		return "redirect:/admin/view-movies";
	}

	@RequestMapping(value = "/view-users", method = RequestMethod.GET)
	public String goViewUsers(Model model) {

		try {
			System.out.println("Testing... /admin/view-users mapping from goViewUser() method in AdminController...");

			List<User> listUsers = userRepository.findAll();
			model.addAttribute("listUsers", listUsers);

		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println(e.getStackTrace());
		}

		return "/admin/view-users.html";
	}

	@RequestMapping(value = "/delete-user/{id}", method = RequestMethod.GET)
	public String goDeleteUser(Model model, @PathVariable(value = "id") Long id) {

		try {
			System.out
					.println("Testing... /admin/delete-user mapping from goDeleteUser() method in AdminController...");

			// Delete user
			userRepository.deleteById(id);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println(e.getStackTrace());
		}

		return "redirect:/admin/view-users";
	}

	@RequestMapping(value = "/edit-user/{id}", method = RequestMethod.GET)
	public String goUpdateUser(Model model, @PathVariable(value = "id") Long id) {

		try {
			System.out
					.println("Testing... /admin/update-user mapping from goUpdateUser() method in AdminController...");

			Optional<User> optional = userRepository.findById(id);
			User user = null;
			if (optional.isPresent()) {
				user = optional.get();
			} else {
				// throw new RuntimeException("User not found for: " + id);
				System.out.println("User not found for the id : " + id);
			}

			model.addAttribute("user", user);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println(e.getStackTrace());
		}

		return "/admin/user-update.html";
	}

	@RequestMapping(value = "/user-register", method = RequestMethod.GET)
	public String goAddUser(Model model) {
		try {

		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println(e.getStackTrace());
		}

		System.out.println("Testing... /admin/view-users mapping from goViewUser() method in AdminController...");

		User user = new User();
		model.addAttribute("user", user);
		return "/admin/user-register.html";
	}

	@RequestMapping(value = "/process-register", method = RequestMethod.POST)
	public String doProcessRegister(Model model, @Valid @ModelAttribute("user") User user,
			@RequestParam(value = "agreement", defaultValue = "false") boolean agreement, BindingResult userResult,
			HttpSession session) throws Exception {

		try {
			System.out.println("Testing the /register mapping from goRegisterPage() method in HomeController...");
//		System.out.println("Agreement : " + agreement);
//		System.out.println("User : " + user.toString());

			if (!agreement) {
				System.out.println("You have not agreed the terms and condition");
				// throw new Exception ("You have not agreed the terms and conditions");
			}

//		if(userResult.hasErrors()) {
//			System.out.println("Error : " + userResult.toString());
//			model.addAttribute("user", user);
//			return "register.html";
//		}

			// user.setUserRole("ROLE_USER");
			// user.setEnabled(true);
			user.setEncryptedPassword(passwordEncoder.encode(user.getEncryptedPassword()));

			System.out.println("==============================================================");
			// System.out.println("User: " + user.toString());
			System.out.println("==============================================================");

			// save data to the database
			User result = this.userRepository.save(user);
			System.out.println("New User successfully saved to database. user id: " + result.getUserName());

			// to send the data back to the form again if any error occurs
			// model.addAttribute("roles", listRoles);
			model.addAttribute("user", new User());

		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println(e.getStackTrace());
		}

		return "redirect:/admin/view-users";
	}

	@RequestMapping(value = "/update-user", method = RequestMethod.POST)
	public String doUpdateUser(Model model, @Valid @ModelAttribute("user") User user,
			@RequestParam(value = "agreement", defaultValue = "false") boolean agreement, BindingResult userResult,
			HttpSession session) throws Exception {

		try {
			System.out.println("Testing the /register mapping from goRegisterPage() method in HomeController...");

			//
//			if(!agreement) {
//				System.out.println("You have not agreed the terms and condition");
//				//throw new Exception ("You have not agreed the terms and conditions");
//			}
//			
			// user.setEncryptedPassword(passwordEncoder.encode(user.getEncryptedPassword()));

			System.out.println("==============================================================");
			// System.out.println("User: " + user.toString());
			System.out.println("==============================================================");
			// save data to the database
			User result = this.userRepository.save(user);

			System.out.println("User successfully updated to database. user id: " + result.getUserName());
			System.out.println(user.toString());

			//
//			//to send the data back to the form again if any error occurs
//			//model.addAttribute("roles", listRoles);
//			model.addAttribute("user", new User());
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println(e.getStackTrace());
		}

		return "redirect:/admin/view-users";
	}

}
