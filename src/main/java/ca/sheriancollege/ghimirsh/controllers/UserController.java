package ca.sheriancollege.ghimirsh.controllers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpSession;

import org.hibernate.validator.internal.util.stereotypes.Lazy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ca.sheriancollege.ghimirsh.bean.Movie;
import ca.sheriancollege.ghimirsh.bean.Seat;
import ca.sheriancollege.ghimirsh.bean.Ticket;
import ca.sheriancollege.ghimirsh.bean.User;
import ca.sheriancollege.ghimirsh.repository.MovieRepository;
import ca.sheriancollege.ghimirsh.repository.SeatRepository;
import ca.sheriancollege.ghimirsh.repository.TicketRepository;
import ca.sheriancollege.ghimirsh.repository.UserRepository;

@Controller
@RequestMapping(value = "/user")
public class UserController {

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

	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
	public String goTicketPurchase(Model model, Principal principal) {

		try {
			System.out.println("Testing /purchase-ticket mapping from goTicketPurchase() method in UserController...");
			String userName = principal.getName();
			// User user = userRepository.getUserByUserName(userName);
			User user = userRepository.findByUserName(userName);
			System.out.println("User : " + user);
			model.addAttribute("user", user);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println(e.getStackTrace());
		}

		return "/user/user-dashboard.html";
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

		return "/user/user-order-ticket.html";
	}

	@PostMapping("/confirm-ticket")
	public String goConfirmTicket(Model model, @ModelAttribute("ticket") Ticket ticket, HttpSession session) {

		try {

			System.out.println("Testing /order-ticket mapping from goOrderTicket method in UserController...");
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
		return "/user/user-confirm-ticket";
	}

	// Method to process the purchase-ticket information
	@RequestMapping(value = "/process-ticket", method = RequestMethod.POST)
	public String doProcessTicket(@ModelAttribute("ticket") Ticket ticket, Model model, HttpSession session,
			Principal principal) {

		try {

			System.out.println("Testing.../process-ticket mapping of doProcessTicket() method of HomeContnroller...");
			System.out.println("#############################################");
			System.out.println("Ticket: " + ticket);
			// Extract basic information from this ticket
			String seatNumber = ticket.getSeatNo();
			String ticketCategory = ticket.getTicketCategory();
			double price = ticket.getPrice();

			// Extract User Id
			String userName = principal.getName();
			// User user = userRepository.getUserByUserName(userName);
			// Ticket currentTicket = (Ticket) session.getAttribute("currentTicket");
			// Updates the current ticket information
			currentTicket.setSeatNo(seatNumber);
			currentTicket.setTicketCategory(ticketCategory);
			currentTicket.setPrice(price);
			currentTicket.setPurchaseBy(userName);

			System.out.println("Current Ticket Info : ");
			System.out.println(currentTicket);

//		System.out.println("________________________________________");
//		soldTicketList.add(ticket);
//		for(Ticket t: soldTicketList) {
//			System.out.println(t);
//		}

			// method call to find the remaining seats
			// findAvailableSeats();

//		model.addAttribute("movieNames", movieNames);
//		model.addAttribute("availableSeats", availableSeatsInOrderedSet);
//		model.addAttribute("soldTicketList", soldTicketList);
//		model.addAttribute("ticketCategory", ticketCategory);

			// Saves the record to database
			ticketRepository.save(currentTicket);

			// Extract the last inserted record
			List<Ticket> allTickets = ticketRepository.findAll();
			if (allTickets.size() > 0) {
				int lastIndex = allTickets.size() - 1;
				Ticket lastTicket = allTickets.get(lastIndex);
				currentTicketList.add(lastTicket);
			}

			System.out.println("===========================================");
			for (Ticket ct : currentTicketList) {
				System.out.println(ct);
			}
			System.out.println("===========================================");
			model.addAttribute("soldTicketList", currentTicketList);

			// model.addAttribute("order", new Ticket());

		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println(e.getStackTrace());
		}

		// return "purchase-ticket";
		return "user/user-print-ticket.html";
	}

	@GetMapping("/print-user-ticket")
	public String redirectToHomePage() {

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

		try {
			// Extract and assigns all seats of the theater
			List<Seat> allSeatsList = seatRepository.findAll();

			// Extrast only seat number and assigns to Set collection
			Set<String> allSeats = new TreeSet<>();
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

	////////////////////////////////////////////////////////////////////////////
	@GetMapping("/discount-purchase")
	public String goPurchaseTicket(Model model) {
		
		try {
			System.out.println("Testing /purchase-ticket mapping from goPurchaseTicket() method in HomeController...");

			// method call to find the remaining Seats
			findAvailableSeats();

			// Adds movies names to the model
			model.addAttribute("movieNames", movieNames);

			// Adds the remaining available seats in sorted order to the model
			model.addAttribute("availableSeats", availableSeatsInOrderedSet);

			// Adds the ticket category to the model
			model.addAttribute("ticketCategory", ticketCategory);

			// Adds the default ticket object to the model
			model.addAttribute("ticket", new Ticket());
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println(e.getStackTrace());
		}

		return "/user/discount-purchase-ticket.html";
	}

	private void findAvailableSeats() {

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
			// model.addAttribute("movieNames", movieNames);

			// Select all seats from theater_seats
			List<Seat> seatList = new ArrayList<>();
			seatList = seatRepository.findAll();

			// select all sold seats from in specified show time and date

			System.out.println("Testing : " + seatList);
			List<String> allSeats = new ArrayList<String>();
			for (Seat s : seatList) {
				allSeats.add(s.getSeatNo());
			}

			// Extract all Seats of the Theater
			Set<String> totalSeatsSet = new HashSet<>();
			for (Seat s : seatList) {
				totalSeatsSet.add(s.getSeatNo());
			}

			// Extract only the sold seats
			Set<String> soldSeats = new HashSet<>();
			for (Ticket t : soldTicketList) {
				soldSeats.add(t.getSeatNo());
			}
			// Sets to hold sold seats
			Set<String> remaingSeats = totalSeatsSet;
			remaingSeats.removeAll(soldSeats);
			// Assign the seats to TreeSet to arrange in order
			availableSeatsInOrderedSet = new TreeSet<>(remaingSeats);

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
				localDate = LocalDate.of(ticket.getShowDate().getYear(), ticket.getShowDate().getMonth(),
						ticket.getShowDate().getDayOfMonth());
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
