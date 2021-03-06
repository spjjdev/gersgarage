package com.gersgarage.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.gersgarage.gersgarage.ResourceNotFoundException;
import com.gersgarage.model.*;
import com.gersgarage.repository.*;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api") // custom naming
public class GersgarageController {

	@Autowired
	private customerRepository customerRepository;
	@Autowired
	private vehicleRepository vehicleRepository;
	@Autowired
	private bookingRepository bookingRepository;
	@Autowired
	private bookingTypeRepository bookingTypeRepository;
	@Autowired
	private invoiceRepository invoiceRepository;
	@Autowired
	private mechanicRepository mechanicRepository;
	@Autowired
	private suppliesRepository suppliesRepository;

	@GetMapping("hello")
	public String hello() {
		return "Hello World";
	}

	// endpoints from https://www.youtube.com/watch?v=eWbGV3LLwVQ

	// retrieves all customers from the database
	@GetMapping("/customers")
	public List<customer> getAllCustomers() {
		List<customer> allCustomers = this.customerRepository.findAll();
		return allCustomers;
	}

	// returns a customer when searched by ID
	@GetMapping("/customers/{email}")
	public ResponseEntity<customer> getCustomerById(@PathVariable(value = "email") String email)
			throws ResourceNotFoundException {
		customer customer = customerRepository.findById(email)
				.orElseThrow(() -> new ResourceNotFoundException("Customer not found for this email" + email));
		return ResponseEntity.ok().body(customer);
	}

	// updates the fields in the customer
	@PutMapping("/customers/{email}")
	public void updateCustomer(@PathVariable(value = "email") String email, String first_name, String last_name,
			String password, String phone_num) throws ResourceNotFoundException {
		customer customer = customerRepository.findById(email)
				.orElseThrow(() -> new ResourceNotFoundException("Customer not found for this email" + email));
		this.customerRepository.delete(customer);
		customer.setEmail(email);
		customer.setFirst_name(first_name);
		customer.setLast_name(last_name);
		customer.setPassword(password);
		customer.setPhone_num(phone_num);
	}

	// deletea a customer
	@DeleteMapping("/customers/{email}")
	public Map<String, Boolean> deleteCustomer(@PathVariable(value = "email") String email)
			throws ResourceNotFoundException {
		customer customer = customerRepository.findById(email)
				.orElseThrow(() -> new ResourceNotFoundException("Customer not found for this email" + email));
		this.customerRepository.delete(customer);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);

		return response;

	}

	// adds a customer to the database
	@PostMapping("/add-customer")
	public String addCustomer(@RequestBody customer customer) {
		customerRepository.save(customer);
		return customer + "is registered to Ger's Garage";
	}

	// retreives all vehicles
	@GetMapping("/vehicles")
	public List<vehicle> getVehicle() {
		List<vehicle> allVehicles = this.vehicleRepository.findAll();
		return allVehicles;
	}

	// adds a vehicle to the database
	@PostMapping("add-vehicle")
	public void addVehicle(@RequestBody vehicle vehicle) {
		vehicleRepository.save(vehicle);
	}

	// returns a vehicle searched by ID
	@GetMapping("/vehicles/{reg}")
	public ResponseEntity<vehicle> getVehicleById(@PathVariable(value = "reg") String reg)
			throws ResourceNotFoundException {
		vehicle vehicle = vehicleRepository.findById(reg)
				.orElseThrow(() -> new ResourceNotFoundException("Vehicle not found for this registration" + reg));
		return ResponseEntity.ok().body(vehicle);
	}

	// deletes a vehicle
	@DeleteMapping("/vehicles/{reg}")
	public Map<String, Boolean> deleteVehicle(@PathVariable(value = "reg") String reg)
			throws ResourceNotFoundException {
		vehicle vehicle = vehicleRepository.findById(reg)
				.orElseThrow(() -> new ResourceNotFoundException("Vehicle not found for this registration" + reg));
		this.vehicleRepository.delete(vehicle);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);

		return response;

	}

	// retreives all bookings from the database
	@GetMapping("/bookings")
	public List<booking> getBookings() {
		return this.bookingRepository.findAll();
	}

	// returns a booking searched by ID
	@GetMapping("/bookings/{booking_id}")
	public ResponseEntity<booking> getBookingById(@PathVariable(value = "booking_id") Long booking_id)
			throws ResourceNotFoundException {
		booking booking = bookingRepository.findById(booking_id)
				.orElseThrow(() -> new ResourceNotFoundException("Booking not found for this ID" + booking_id));
		return ResponseEntity.ok().body(booking);
	}

	// adds a booking to the database
	@PostMapping("/add-booking")
	public String addBooking(@RequestBody booking booking) {
		bookingRepository.save(booking);
		return booking + "is registered to Ger's Garage";
	}

	// updates fields in the booking
	@PutMapping("/bookings/{booking_id}")
	public ResponseEntity<booking> assignMechanic(@PathVariable(value = "booking_id") Long booking_id,
			@Validated @RequestBody booking bookingDetails) throws ResourceNotFoundException {
		booking booking = bookingRepository.findById(booking_id)
				.orElseThrow(() -> new ResourceNotFoundException("Booking not found for this ID" + booking_id));
		booking.setMechanic(bookingDetails.getMechanic());
		booking.setTimedate(bookingDetails.getTimedate());
		booking.setType(bookingDetails.getType());
		booking.setVehicle(bookingDetails.getVehicle());

		return ResponseEntity.ok().body(booking);
	}

	// deletes a booking
	@DeleteMapping("/bookings/{booking_id}")
	public Map<String, Boolean> deleteBooking(@PathVariable(value = "booking_id") Long booking_id)
			throws ResourceNotFoundException {
		booking booking = bookingRepository.findById(booking_id)
				.orElseThrow(() -> new ResourceNotFoundException("Booking not found for this ID" + booking_id));
		this.bookingRepository.delete(booking);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);

		return response;

	}

	// gets booking types
	@GetMapping("/booking-type")
	public List<booking_type> getBookingType() {
		List<booking_type> allBookingType = this.bookingTypeRepository.findAll();
		return allBookingType;
	}

	// gets all invoices
	@GetMapping("/invoice")
	public List<invoice> getAllInvoices() {
		List<invoice> allInvoices = this.invoiceRepository.findAll();
		return allInvoices;
	}

	// get an invoice by ID
	@GetMapping("/invoice/{invoice_id}")
	public ResponseEntity<invoice> getInvoiceById(@PathVariable(value = "invoice_id") Long invoice_id)
			throws ResourceNotFoundException {
		invoice invoice = invoiceRepository.findById(invoice_id)
				.orElseThrow(() -> new ResourceNotFoundException("invoice not found for this ID" + invoice_id));
		return ResponseEntity.ok().body(invoice);
	}

	// deletes an invoice
	@DeleteMapping("/invoice/{invoice_id}")
	public Map<String, Boolean> deleteInvoice(@PathVariable(value = "invoice_id") Long invoice_id)
			throws ResourceNotFoundException {
		invoice invoice = invoiceRepository.findById(invoice_id)
				.orElseThrow(() -> new ResourceNotFoundException("Invoice not found for this email" + invoice_id));
		this.invoiceRepository.delete(invoice);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);

		return response;

	}

	// gets all mechanics
	@GetMapping("/mechanics")
	public List<mechanic> getMechanics() {
		List<mechanic> allMechanics = this.mechanicRepository.findAll();
		return allMechanics;
	}

	// gets all supplies
	@GetMapping("/supplies")
	public List<supplies> getSupplies() {
		List<supplies> allSupplies = this.suppliesRepository.findAll();
		return allSupplies;

	}

	// adds a new supply
	@PostMapping("/add-supply")
	public String addSupply(@RequestBody supplies supplies) {
		suppliesRepository.save(supplies);
		return supplies + "is added  to supply list";
	}

	// gets a supply by ID
	@DeleteMapping("/supplies/{supplies_id}")
	public Map<String, Boolean> deletesupply(@PathVariable(value = "supplies_id") Long supplies_id)
			throws ResourceNotFoundException {
		supplies supplies = suppliesRepository.findById(supplies_id)
				.orElseThrow(() -> new ResourceNotFoundException("Item not found for this ID" + supplies_id));
		this.suppliesRepository.delete(supplies);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);

		return response;

	}

}
