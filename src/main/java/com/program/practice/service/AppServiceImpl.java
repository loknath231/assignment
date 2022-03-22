package com.program.practice.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.program.practice.dto.DailyTransactionDTO;
import com.program.practice.dto.Interest;
import com.program.practice.dto.TransactionDTO;
import com.program.practice.dto.UserDTO;
import com.program.practice.entity.Transaction;
import com.program.practice.entity.User;
import com.program.practice.exception.ResourceNotfoundException;
import com.program.practice.repository.TransactionRepository;
import com.program.practice.repository.UserRepository;

/**
 * @author lokkumar8
 *
 */
@Service
public class AppServiceImpl {

	private static final double INTEREST_RATE = 5L;

	@Autowired
	private TransactionRepository transactionRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	public AppServiceImpl(TransactionRepository transactionRepository, UserRepository userRepository) {
		this.transactionRepository = transactionRepository;
		this.userRepository = userRepository;
	}

	/**
	 * save in user table to open accounts
	 * 
	 * @param userDto
	 * @return
	 */
	public User processAccountOpening(UserDTO userDto) {
		User user = new User();
		user.setBsb(userDto.getBsb());
		user.setIdentification(userDto.getIdentification());
		user.setOpeningDate(userDto.getOpeningDate());
		return userRepository.save(user);
	}

	/**
	 * remove from user table to close accounts
	 * 
	 * @param bsb
	 * @return
	 */
	public HashMap<String, Boolean> deleteAccount(long bsb) throws ResourceNotfoundException {
		User user = userRepository.findById(bsb)
				.orElseThrow(() -> new ResourceNotfoundException("Transaction not found for this bsb :: " + bsb));
		userRepository.delete(user);
		HashMap<String, Boolean> response = new HashMap<String, Boolean>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}

	public User getUserDetails(Long bsb) {
		return userRepository.getById(bsb);
	}

	/**
	 * save all transactions to transaction table
	 * 
	 * @param dailyTransactionDTO
	 * @return
	 */
	public List<Interest> saveTransaction(DailyTransactionDTO dailyTransactionDTO) {
		List<Transaction> transactions = new ArrayList<>();
		for (TransactionDTO transactionDto : dailyTransactionDTO.getTransaction()) {
			Transaction transaction = new Transaction();
			transaction.setIdentification(transactionDto.getIdentification());
			transaction.setBalance(transactionDto.getBalance());
			transaction.setBsb(transactionDto.getBsb());
			transaction.setBalanceDate(dailyTransactionDTO.getBalanceDate());
			transactions.add(transaction);
		}
		List<Transaction> transactionList = transactionRepository.saveAll(transactions);
		return interestPojoMapperInterestsDaily(transactionList);
	}

	public Transaction getTransactionById(long identification) throws ResourceNotfoundException {
		return transactionRepository.findById(identification).orElseThrow(
				() -> new ResourceNotfoundException("transaction not found for this bsb :: " + identification));
	}

	public double processAccountEndOfDayBalances(long bsb, String date) {
		return transactionRepository.findAll().stream().filter(acc -> bsb == acc.getBsb())
				.filter(acc -> date.equals(acc.getBalanceDate())).collect(Collectors.toList()).stream()
				.collect(Collectors.summingDouble(Transaction::getBalance));
	}

	public Interest getCalculateInterestAsPerDate(Transaction trans) {
		double eodBalance = processAccountEndOfDayBalances(trans.getBsb(), trans.getBalanceDate());
		int noOfDays = LocalDate.now()
				.compareTo(LocalDate.parse(trans.getBalanceDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
		Interest interest = new Interest();
		interest.setBsb(trans.getBsb());
		interest.setBalance(eodBalance);
		interest.setIdentification(trans.getIdentification());
		interest.setOpeningDate(trans.getBalanceDate());
		interest.setInterestAmount((eodBalance * INTEREST_RATE * noOfDays) / 100);
		interest.setTotalBal(eodBalance + (eodBalance * INTEREST_RATE * noOfDays) / 100);
		return interest;
	}

	public List<Interest> interestPojoMapperInterestsDaily(List<Transaction> transactionList) {
		List<Interest> listInterest = new ArrayList<>();
		transactionList.forEach(trans -> listInterest.add(getCalculateInterestAsPerDate(trans)));
		return listInterest;
	}

	public Interest getCalculateInterestAsPerDate(TransactionDTO trans) {
		LocalDate today = LocalDate.now();
		int noOfDays = today.compareTo(trans.getOpeningDate());
		Interest interest = new Interest();
		interest.setBsb(trans.getBsb());
		interest.setBalance(trans.getBalance());
		interest.setInterestAmount((trans.getBalance() * INTEREST_RATE * noOfDays) / 100);
		interest.setTotalBal(trans.getBalance() + (trans.getBalance() * INTEREST_RATE * noOfDays) / 100);
		return interest;
	}

	public List<Interest> interestPojoMapperInterestsMonthly(List<Transaction> transactionList, LocalDate date) {
		List<Interest> listInterest = new ArrayList<>();

		transactionList
				.stream().filter(trans -> LocalDate
						.parse(trans.getBalanceDate(), DateTimeFormatter.ofPattern("yyyy-MMM-yy")).isEqual(date))
				.forEach(trans -> listInterest.add(getCalculateInterestMontly(trans)));
		return listInterest;
	}

	public List<Interest> getMonthlyInterestAccountWise(LocalDate date) {
		List<Transaction> transactionList = transactionRepository.findAll();
		return interestPojoMapperInterestsMonthly(transactionList, date);
	}

	public Interest getCalculateInterestMontly(Transaction trans) {
		int noOfDays = 30;
		Interest interest = new Interest();
		interest.setBsb(trans.getBsb());
		interest.setBalance(trans.getBalance());
		interest.setInterestAmount((trans.getBalance() * INTEREST_RATE * noOfDays) / 100);
		interest.setTotalBal(trans.getBalance() + (trans.getBalance() * INTEREST_RATE * noOfDays) / 100);
		return interest;
	}

}
