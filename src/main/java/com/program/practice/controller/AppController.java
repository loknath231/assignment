package com.program.practice.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.program.practice.dto.DailyTransactionDTO;
import com.program.practice.dto.Interest;
import com.program.practice.dto.UserDTO;
import com.program.practice.entity.Transaction;
import com.program.practice.entity.User;
import com.program.practice.exception.ResourceNotfoundException;
import com.program.practice.service.AppServiceImpl;

/**
 * @author lokkumar8
 *
 */
@RestController
@RequestMapping("/api/v1/account")
public class AppController {
	@Autowired
	private AppServiceImpl appServiceImpl;

//open account and save in user table
	@PostMapping("/open")
	public User processAccountOpening(@RequestBody UserDTO user) {
		return appServiceImpl.processAccountOpening(user);
	}

//parse the request for daily transaction, save in transaction table and return daily interest, and total balance 
	@PostMapping("/save")
	public Interest saveTransaction(@RequestBody DailyTransactionDTO dailyTransactions) {
		return appServiceImpl.saveTransaction(dailyTransactions).get(0);
	}

	/**
	 * API to delete user account once account is closed
	 * 
	 * @param bsb
	 * @return
	 * @throws ResourceNotfoundException
	 */
	@DeleteMapping("/delete/{bsb}")
	public Map<String, Boolean> deleteAccount(@PathVariable(value = "bsb") Long bsb) throws ResourceNotfoundException {
		return appServiceImpl.deleteAccount(bsb);
	}

	@GetMapping("/get/{bsb}")
	public ResponseEntity<Transaction> getAccountById(@PathVariable(value = "id") Long id)
			throws ResourceNotfoundException {
		return ResponseEntity.ok().body(appServiceImpl.getTransactionById(id));
	}

	/**
	 * to get the monthly balance , interest, and final amount after interest
	 * 
	 * @param inputDate
	 * @return
	 */
	@GetMapping("/monthly/{month}")
	public ResponseEntity<Interest> getMonthlyBalance(@PathVariable String inputDate) {
		LocalDate date = LocalDate.parse(inputDate, DateTimeFormatter.ofPattern("yyyy-MMM-yy"));
		return ResponseEntity.ok().body(appServiceImpl.getMonthlyInterestAccountWise(date));
	}
}