package banking;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CommandValidatorTest {
	public static final String ID_FIRST_ACCOUNT = "99999999";
	public static final String ID_SECOND_ACCOUNT = "12345678";
	public static final double DEPOSIT_AMOUNT = 200;

	public static final double APR = 3;
	public static final double CHECKING_AND_SAVING_STARTING_BALANCE = 0;
	CommandValidator commandValidator;
	boolean actual;
	Bank bank;

	@BeforeEach
	public void setUp() {
		bank = new Bank();
		commandValidator = new CommandValidator(bank);
	}

	@Test
	public void create_an_account_with_valid_comment() {
		actual = commandValidator.validate("Create checking 12345678 0.6");
		assertTrue(actual);
	}

	@Test
	public void deposit_into_an_account_with_valid_comment() {
		bank.addSavingAccount(ID_FIRST_ACCOUNT, APR, CHECKING_AND_SAVING_STARTING_BALANCE);
		actual = commandValidator.validate("Deposit 99999999 500");
		assertTrue(actual);
	}

	@Test
	public void deposit_into_an_account_with_invalid_comment() {
		bank.addSavingAccount(ID_FIRST_ACCOUNT, APR, CHECKING_AND_SAVING_STARTING_BALANCE);
		actual = commandValidator.validate("Deposit 98555555 500");
		assertFalse(actual);
	}

	@Test
	public void completely_wrong_comment() {
		actual = commandValidator.validate("des sd 500");
		assertFalse(actual);
	}

	@Test
	public void withdraw_from_an_saving_account_with_valid_comment() {
		bank.addSavingAccount(ID_FIRST_ACCOUNT, APR, CHECKING_AND_SAVING_STARTING_BALANCE);
		bank.depositById(ID_FIRST_ACCOUNT, DEPOSIT_AMOUNT);
		actual = commandValidator.validate("Withdraw 99999999 100");
		assertTrue(actual);
	}

	@Test
	public void withdraw_from_an_checking_account_with_invalid_id() {
		actual = commandValidator.validate("Withdraw 1234569 100");
		assertFalse(actual);
	}

	@Test
	public void transfer_from_an_saving_to_saving_account_with_valid_command() {
		bank.addCheckingAccount(ID_FIRST_ACCOUNT, APR, CHECKING_AND_SAVING_STARTING_BALANCE);
		bank.addCheckingAccount(ID_SECOND_ACCOUNT, APR, CHECKING_AND_SAVING_STARTING_BALANCE);
		bank.depositById(ID_FIRST_ACCOUNT, DEPOSIT_AMOUNT);
		actual = commandValidator.validate("Transfer 99999999 12345678 200");
		assertTrue(actual);
	}

	@Test
	public void pass_time_with_valid_comment() {
		actual = commandValidator.validate("Pass 1");
		assertTrue(actual);
	}

	@Test
	public void pass_time_with_exceed_month() {
		actual = commandValidator.validate("pass 65");
		assertFalse(actual);
	}

	@Test
	public void transfer_from_an_checking_to_saving_account_with_exceed_maximum() {

		actual = commandValidator.validate("Transfer 12345679 12345678 1100");
		assertFalse(actual);
	}

	@Test
	public void pass_time_with_extra_space() {
		actual = commandValidator.validate("Pass  1");
		assertFalse(actual);
	}

	@Test
	public void deposit_into_closed_account() {
		bank.addCheckingAccount(ID_FIRST_ACCOUNT, APR, CHECKING_AND_SAVING_STARTING_BALANCE);
		bank.setTime(1);

		actual = commandValidator.validate("Deposit 99999999 500");
		assertFalse(actual);
	}

	@Test
	public void withdraw_from_closed_account() {
		bank.addCheckingAccount(ID_FIRST_ACCOUNT, APR, CHECKING_AND_SAVING_STARTING_BALANCE);
		bank.setTime(1);

		actual = commandValidator.validate("withdraw 99999999 500");
		assertFalse(actual);
	}

	@Test
	public void transfer_to_an_closed_account() {
		bank.addCheckingAccount(ID_FIRST_ACCOUNT, APR, CHECKING_AND_SAVING_STARTING_BALANCE);
		bank.addCheckingAccount(ID_SECOND_ACCOUNT, APR, CHECKING_AND_SAVING_STARTING_BALANCE);
		bank.depositById(ID_SECOND_ACCOUNT, DEPOSIT_AMOUNT);
		bank.setTime(1);

		actual = commandValidator.validate("transfer 12345678 99999999 500");
		assertFalse(actual);
	}

	@Test
	public void transfer_from_an_closed_account() {
		bank.addCheckingAccount(ID_FIRST_ACCOUNT, APR, CHECKING_AND_SAVING_STARTING_BALANCE);
		bank.addCheckingAccount(ID_SECOND_ACCOUNT, APR, CHECKING_AND_SAVING_STARTING_BALANCE);
		bank.depositById(ID_SECOND_ACCOUNT, DEPOSIT_AMOUNT);
		bank.setTime(1);

		actual = commandValidator.validate("transfer 99999999 12345678 500");
		assertFalse(actual);
	}
}
