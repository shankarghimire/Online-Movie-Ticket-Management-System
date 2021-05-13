package ca.sheriancollege.ghimirsh.controllers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;

import java.util.ArrayList;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.hibernate.validator.internal.util.stereotypes.Lazy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

//import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;

import ca.sheriancollege.ghimirsh.bean.Movie;
import ca.sheriancollege.ghimirsh.bean.Seat;
import ca.sheriancollege.ghimirsh.bean.Ticket;
//import ca.sheriancollege.ghimirsh.bean.TicketCategory;
import ca.sheriancollege.ghimirsh.bean.User;
import ca.sheriancollege.ghimirsh.repository.MovieRepository;
import ca.sheriancollege.ghimirsh.repository.SeatRepository;
import ca.sheriancollege.ghimirsh.repository.TicketRepository;
import ca.sheriancollege.ghimirsh.repository.UserRepository;

@Controller
public class HomeController {

	@Lazy
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private SeatRepository seatRepository;

	@Autowired
	@Lazy
	MovieRepository movieRepository;

	@Autowired
	TicketRepository ticketRepository;

	@Autowired
	BCryptPasswordEncoder passwordEncoder;

	List<Ticket> soldTicketList = new ArrayList<>();

	private Set<String> availableSeatsInOrderedSet;

	List<String> movieNames = new ArrayList<>();
	String[] ticketCategory = { "General Admission", "Sheridan College Student", "PROG 32758 Students",
			"Senior Citizen", "Children" };;

	// List to hold information of currently purchased ticket

	List<Ticket> currentTicketList = new ArrayList<>();

	Ticket currentTicket = new Ticket();

	@GetMapping("/")
	public String goHome(Model model) {
		try {
			System.out.println("Testing / mapping from goHome() method in HomeController...");
			List<String> names = List.of("Titanic", "Avatar", "Hurt Lucker", "Mr Pi");
			model.addAttribute("names", names);
			User u1 = userRepository.findByUserName("user");
			System.out.println("=====================================================");
			System.out.println("User: " + u1);
			System.out.println("=====================================================");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println(e.getStackTrace());
		}

		return "index";
	}

	@GetMapping("/order-ticket")
	public String goOrderTicket(Model model, @ModelAttribute("ticket") Ticket ticket, HttpSession session) {

		try {
			System.out.println("Testing /order-ticket mapping from goOrderTicket method in HomeController...");

			// Clears the currentTicketList variable to hold the new ticket info

			// Adds movies names to the model
			List<String> movieNameList = getMovieNames();
			model.addAttribute("movieNames", movieNameList);

			// Adds the default ticket object to the model
			model.addAttribute("ticket", new Ticket());

			Ticket t1 = new Ticket();
			t1.setId(100L);
			t1.setMovieName("Testing");

			// Adds object to session
			session.setAttribute("msg1", "Shankar Ghimire");
			session.setAttribute("t1Session", t1);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println(e.getStackTrace());
		}

		return "order-ticket.html";
	}

	@PostMapping("/confirm-ticket")
	public String goTestTicket(Model model, @ModelAttribute("ticket") Ticket ticket, HttpSession session) {

		try {
			System.out.println("Testing /order-ticket mapping from goOrderTicket method in HomeController...");
			// System.out.println("#################################################");
			System.out.println(ticket);

			// Calls method to find the names of the movies
			findMoviesNames();

			/////////////////////////////////////////////////////////////////////////////////////////////////////////////
			// Codes to find the remaining vacant seats on specific time and date for the
			///////////////////////////////////////////////////////////////////////////////////////////////////////////// sle
			// Calls method : findRemainingSeats()
			findRemaingSeats(ticket);
//		System.out.println("//////////////////////////////////////////////////");
//		for(String s: availableSeatsInOrderedSet) {
//			System.out.println(s);
//		}
//				
			// method call to find the remaining Seats
			// findAvailableSeats();

			// Local Varibles to assign to model objects

//		String movieName = ticket.getMovieName();
//		String theaterName = ticket.getTheaterName();
//		String showDate = ticket.getShowDate().toString();
//		String showTime = ticket.getShowTime();

			// Adds to the model

			currentTicket = ticket;

			model.addAttribute("currentTicket", currentTicket);

			// Adds movies names to the model
			model.addAttribute("movieNames", movieNames);

			// Adds the remaining available seats in sorted order to the model
			model.addAttribute("availableSeats", availableSeatsInOrderedSet);

			// Adds the ticket category to the model
			model.addAttribute("ticketCategory", ticketCategory);

			// Adds the default ticket object to the model
			model.addAttribute("ticket", new Ticket());
//		
			// Adds object to session
			session.setAttribute("name", "Shankar");
			session.setAttribute("currentTicket", currentTicket);

		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println(e.getStackTrace());
		}

		// return "trial2.html";
		return "confirm-ticket";
	}

	// Method to process the purchase-ticket information
	@RequestMapping(value = "/process-ticket", method = RequestMethod.POST)
	public String doProcessTicket(@ModelAttribute("ticket") Ticket ticket, Model model, HttpSession session) {

		try {

			System.out.println("Testing.../process-ticket mapping of doProcessTicket() method of HomeContnroller...");
			System.out.println("#############################################");
			System.out.println("Ticket: " + ticket);
			// Extract basic information from this ticket
			String seatNumber = ticket.getSeatNo();
			String ticketCategory = ticket.getTicketCategory();
			double price = ticket.getPrice();

			// Ticket currentTicket = (Ticket) session.getAttribute("currentTicket");
			// Updates the current ticket information
			currentTicket.setSeatNo(seatNumber);
			currentTicket.setTicketCategory(ticketCategory);
			currentTicket.setPrice(price);

			System.out.println("Current Ticket Info : ");
			System.out.println(currentTicket);

//		System.out.println("________________________________________");
//		soldTicketList.add(ticket);
//		for(Ticket t: soldTicketList) {
//			System.out.println(t);
//		}

			// Saves the record to database
			ticketRepository.save(currentTicket);

			// Extract the last inserted record
			List<Ticket> allTickets = ticketRepository.findAll();
			if (allTickets.size() > 0) {
				int lastIndex = allTickets.size() - 1;
				Ticket lastTicket = allTickets.get(lastIndex);
				currentTicketList.add(lastTicket);
			}

			System.out.println("Testing...!===========================================");
			for (Ticket ct : currentTicketList) {
				System.out.println(ct);
			}
			System.out.println("Testing...!===========================================");

			model.addAttribute("soldTicketList", currentTicketList);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println(e.getStackTrace());
		}

		// model.addAttribute("order", new Ticket());
		// return "purchase-ticket";
		return "print-ticket.html";
	}

	@GetMapping("/print-ticket")
	public String redirectToHomePage() {

		try {

		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println(e.getStackTrace());
		}

//		System.out.println("==================================");
//		for(Ticket t: currentTicketList) {
//			System.out.println(t);
//		}
//		System.out.println("==================================");

		// Call method to print ticket
		try {
			printTicket();
			System.out.println("Tickets are printed successfully!!!!");

			// Clears the currentTicketList to hold next tickets purchased by other
			// customers
			currentTicketList.clear();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "redirect:/";
	}

	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public String searchTicket(Model model) {
		try {
			System.out.println("Testing /test mapping ....");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println(e.getStackTrace());
		}

		return "test.html";
	}

	@GetMapping("/about-us")
	public String goAboutUs() {
		try {
			System.out.println("Testing... /about-us mapping from goAboutUs() method in HomeController...");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println(e.getStackTrace());
		}

		return "about-us.html";
	}

	@GetMapping("/contact-us")
	public String goContactUs() {
		try {
			System.out.println("Testing... /contact-us mapping from goContactUs() method in HomeController...");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println(e.getStackTrace());
		}

		return "contact-us.html";
	}

	@GetMapping("/login")
	public String goLoginPage() {

		try {
			System.out.println("Testing... the /log-in mapping from goLoginPage() method in HomeController...");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println(e.getStackTrace());
		}

		return "login.html";
	}

	@GetMapping("/register")
	public String goRegisterPage(Model model) {
		try {
			System.out.println("Testing the /register mapping from goRegisterPage() method in HomeController...");
			model.addAttribute("user", new User());
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println(e.getStackTrace());
		}

		return "register.html";
	}

	@RequestMapping(value = "/do-register", method = RequestMethod.POST)
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

			if (userResult.hasErrors()) {
				System.out.println("Error : " + userResult.toString());
				model.addAttribute("user", user);
				return "register.html";
			}

			user.setUserRole("ROLE_USER");
			user.setEnabled(true);
			user.setEncryptedPassword(passwordEncoder.encode(user.getEncryptedPassword()));

			// save data to the database
			User result = this.userRepository.save(user);
			System.out.println("New User successfully saved to database. user id: " + result.getUserName());
			// to send the data back to the form again if any error occurs
			model.addAttribute("user", new User());

		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println(e.getStackTrace());
		}

		return "register.html";
	}

	@GetMapping("/admin")
	public String goAdminConfig() {

		try {
			System.out.println("Testing /admin mapping from goAdminConfig() method in HomeController...");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println(e.getStackTrace());
		}

		return "/admin/movie-config.html";
	}

	@GetMapping("/access-denied")
	public String goAccessDenied() {
		try {
			System.out.println("Testing /access-denied mapping from goAccessDenied() method in HomeController...");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println(e.getStackTrace());
		}

		return "/error/access-denied.html";
	}

	private void findMoviesNames() {

		try {
			// Select from movie table to populate MovieSelect element
			List<Movie> movieList = movieRepository.findAll();

			System.out.println("Movies List: " + movieList);
			// List to hold only Movie names

			movieNames.clear();
			// loop to extract only names from movieList and collect in movieNames list
			for (Movie m : movieList) {
				movieNames.add(m.getMovieName());
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println(e.getStackTrace());
		}

	}

	private List<String> getMovieNames() {
		List<Movie> movieList = movieRepository.findAll();
		List<String> movieNames = new ArrayList<>();
		try {
			
			for (Movie m : movieList) {
				movieNames.add(m.getMovieName());
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println(e.getStackTrace());
		}

		return movieNames;

	}

	private void findRemaingSeats(Ticket ticket) {
		// Extract and assigns all seats of the theater
					List<Seat> allSeatsList = seatRepository.findAll();

					// Extrast only seat number and assigns to Set collection
					Set<String> allSeats = new TreeSet<>();
		try {
			
			for (Seat s : allSeatsList) {
				allSeats.add(s.getSeatNo());
			}

			// Declare variables for Search Criteria in database
			String showTime = ticket.getShowTime();
			LocalDate showDate = LocalDate.of(ticket.getShowDate().getYear(), ticket.getShowDate().getMonth(),
					ticket.getShowDate().getDayOfMonth());

			// Extract all tockets sold on given date and given showtime
			// to extract the Seats sold for that time

			List<Ticket> soldTickets = ticketRepository.getTicketsByShowtimeAndShowdate(showTime, showDate);

			// Extract only the Seat Number and assigns to the Set collection
			Set<String> soldSeats = new TreeSet<>();
			for (Ticket t : soldTickets) {
				soldSeats.add(t.getSeatNo());
			}

			Set<String> freeSeats = new TreeSet<>();
			freeSeats = allSeats;
			freeSeats.removeAll(soldSeats);

			// Assigns the Seats into
			availableSeatsInOrderedSet = freeSeats;

		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println(e.getStackTrace());
		}

	}

	private void printTicket() throws IOException {

		try {
			File f = new File("C:\\Receipts");
			if (!f.exists()) {
				System.out.println("Not exists");
				boolean res = f.mkdir();
				if (res) {
					System.out.println("Folder created");
				} else {
					System.out.println("Already exist");
				}

			}

			System.out.println("==================================");
			for (Ticket t : currentTicketList) {
				System.out.println(t);
			}
			System.out.println("==================================");

			BufferedWriter bw = new BufferedWriter(new FileWriter("C:\\Receipts\\Receipt.txt"));
//		bw.write("Shanakr");
//		bw.newLine();
//		bw.write("Ghimire");
//		bw.close();

			Ticket ticket;
			String purchaseBy = "";
			String purchaseDate = "";
			LocalDate localDate;
			double totalAmount = 0;
			if (currentTicketList.size() > 0) {
				ticket = currentTicketList.get(0);
				purchaseBy = ticket.getPurchaseBy();
				purchaseDate = ticket.getPurchaseDate().toLocalDate().toString();
				// localDate = LocalDate.of(ticket.getShowDate().getYear(),
				// ticket.getShowDate().getMonth(), ticket.getShowDate().getDayOfMonth());
			}

			bw.write("Purchased Date : " + purchaseDate);
			bw.newLine();
			bw.write("Customer Name : " + purchaseBy);
			bw.newLine();
			bw.write(
					"=============================================================================================================================================");
			bw.newLine();
			bw.write(
					"Ticket#\tMovie Name\tTheater Name\t\t\tSeat Number\tShow Time\tShowDate\tTicket Category\t\t\tPrice");
			bw.newLine();
			bw.write("--\t----------\t------------\t\t\t-----------\t----------\t--------\t--------------\t\t\t----");
			for (Ticket t : currentTicketList) {

				bw.newLine();
				localDate = LocalDate.of(t.getShowDate().getYear(), t.getShowDate().getMonth(),
						t.getShowDate().getDayOfMonth());
				String data = t.getId().toString() + "\t" + t.getMovieName() + "\t\t" + t.getTheaterName() + "\t"
						+ t.getSeatNo() + "\t\t" + t.getShowTime().toString() + "\t\t"
						+ t.getShowDate().toLocalDate().toString() + "\t" + t.getTicketCategory() + "\t"
						+ Double.toString(t.getPrice());
				totalAmount += t.getPrice();
				bw.write(data);
				// bw.newLine();
			}
			bw.newLine();
			bw.write("\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t    =============");
			bw.newLine();
			bw.write("\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t" + Double.toString(totalAmount));
			bw.newLine();
			bw.newLine();
			bw.write("Thank you!☺");
			bw.close();

		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println(e.getStackTrace());
		}

	}

}
