package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BankTest {
	public static final double APR = 3;
	public static final double DEPOSIT_AMOUNT = 100.5;
	public static final double WITHDRAW_AMOUNT = 50.5;
	public static final String ID_FIRST_ACCOUNT = "12345678";
	public static final String ID_SECOND_ACCOUNT = "12345679";
	public static final double CHECKING_AND_DEPOSIT_STARTING_BALANCE = 0;
	public static final double CD_ACCOUNT_STARTING_BALANCE = 1000;

	Bank bank;

	@BeforeEach
	void setUp() {
		bank = new Bank();
	}

	@Test
	void bank_has_no_account_initially() {
		Bank bank = new Bank();
		assertTrue(bank.getAccount().isEmpty());
	}

	@Test
	void add_one_account_to_bank() {
		bank.addCheckingAccount(ID_FIRST_ACCOUNT, APR, CHECKING_AND_DEPOSIT_STARTING_BALANCE);
		assertEquals(1, bank.getAccount().size());
	}

	@Test
	void add_two_account_to_bank() {
		bank.addCheckingAccount(ID_FIRST_ACCOUNT, APR, CHECKING_AND_DEPOSIT_STARTING_BALANCE);
		bank.addSavingAccount(ID_SECOND_ACCOUNT, APR, CHECKING_AND_DEPOSIT_STARTING_BALANCE);
		assertEquals(2, bank.getAccount().size());

	}

	@Test
	void retrieve_a_account() {
		bank.addCheckingAccount(ID_FIRST_ACCOUNT, APR, CHECKING_AND_DEPOSIT_STARTING_BALANCE);
		assertTrue(bank.getAccount().containsKey(ID_FIRST_ACCOUNT));
	}

	@Test
	void deposit_money_by_id() {
		bank.addCheckingAccount(ID_FIRST_ACCOUNT, APR, CHECKING_AND_DEPOSIT_STARTING_BALANCE);
		bank.getAccount().get(ID_FIRST_ACCOUNT).deposit(DEPOSIT_AMOUNT);
		Account actual = bank.getAccount().get(ID_FIRST_ACCOUNT);
		assertEquals(DEPOSIT_AMOUNT, actual.getBalance());
	}

	@Test
	void withdraw_money_by_id() {
		bank.addCDAccount(ID_FIRST_ACCOUNT, APR, CD_ACCOUNT_STARTING_BALANCE);
		bank.getAccount().get(ID_FIRST_ACCOUNT).withdraw(WITHDRAW_AMOUNT);
		Account actual = bank.getAccount().get(ID_FIRST_ACCOUNT);
		assertEquals(CD_ACCOUNT_STARTING_BALANCE - WITHDRAW_AMOUNT, actual.getBalance());
	}

	@Test
	void deposit_money_twice() {
		bank.addCheckingAccount(ID_FIRST_ACCOUNT, APR, CHECKING_AND_DEPOSIT_STARTING_BALANCE);
		bank.getAccount().get(ID_FIRST_ACCOUNT).deposit(DEPOSIT_AMOUNT);
		bank.getAccount().get(ID_FIRST_ACCOUNT).deposit(DEPOSIT_AMOUNT);
		Account actual = bank.getAccount().get(ID_FIRST_ACCOUNT);
		assertEquals(DEPOSIT_AMOUNT * 2, actual.getBalance());
	}

	@Test
	void withdraw_money_twice() {
		bank.addCDAccount(ID_FIRST_ACCOUNT, APR, CD_ACCOUNT_STARTING_BALANCE);
		bank.getAccount().get(ID_FIRST_ACCOUNT).withdraw(WITHDRAW_AMOUNT);
		bank.getAccount().get(ID_FIRST_ACCOUNT).withdraw(WITHDRAW_AMOUNT);
		Account actual = bank.getAccount().get(ID_FIRST_ACCOUNT);
		assertEquals(CD_ACCOUNT_STARTING_BALANCE - WITHDRAW_AMOUNT * 2, actual.getBalance());
	}

}