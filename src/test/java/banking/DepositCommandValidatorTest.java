package banking;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DepositCommandValidatorTest {
	public static final String SAVINGS_ACCOUNT_ID = "12345678";
	public static final String CHECKING_ACCOUNT_ID = "12345679";
	public static final String CD_ACCOUNT_ID = "99999999";
	public static final double APR = 3;
	public static final double CHECKING_AND_DEPOSIT_STARTING_BALANCE = 0;
	public static final double CD_ACCOUNT_STARTING_BALANCE = 1000;
	DepositCommandValidator depositCommandValidator;
	Bank bank;
	boolean actual;

	@BeforeEach
	public void setUp() {
		bank = new Bank();
		bank.addSavingAccount(SAVINGS_ACCOUNT_ID, APR, CHECKING_AND_DEPOSIT_STARTING_BALANCE);
		bank.addCDAccount(CD_ACCOUNT_ID, APR, CD_ACCOUNT_STARTING_BALANCE);
		bank.addCheckingAccount(CHECKING_ACCOUNT_ID, APR, CHECKING_AND_DEPOSIT_STARTING_BALANCE);

		depositCommandValidator = new DepositCommandValidator(bank);
	}

	@Test
	public void deposit_into_an_account_with_valid_comment() {
		actual = depositCommandValidator.validate("Deposit 12345678 500");
		assertTrue(actual);
	}

	@Test
	public void deposit_into_an_account_with_0_deposit() {
		actual = depositCommandValidator.validate("Deposit 12345678 0");
		assertTrue(actual);
	}

	@Test
	public void deposit_into_a_checking_account_within_amount() {
		actual = depositCommandValidator.validate("Deposit 12345679 1000");
		assertTrue(actual);
	}

	@Test
	public void deposit_into_a_savings_account_within_amount() {
		actual = depositCommandValidator.validate("Deposit 12345678 2500");
		assertTrue(actual);
	}

	@Test
	public void deposit_into_an_account_with_a_not_existing_id() {
		actual = depositCommandValidator.validate("Deposit 11111111 500");
		assertFalse(actual);
	}

	@Test
	public void deposit_into_an_account_with_missing_deposit_command() {
		actual = depositCommandValidator.validate(" 12345678 500");
		assertFalse(actual);
	}

	@Test
	public void deposit_into_an_account_with_missing_ID() {
		actual = depositCommandValidator.validate("Deposit 3000");
		assertFalse(actual);
	}

	@Test
	public void deposit_into_an_account_with_missing_amount() {
		actual = depositCommandValidator.validate("Deposit 12345678");
		assertFalse(actual);
	}

	@Test
	public void deposit_into_a_checking_account_exceed_amount() {
		actual = depositCommandValidator.validate("Deposit 12345679 3000");
		assertFalse(actual);
	}

	@Test
	public void deposit_into_a_savings_account_exceed_amount() {
		actual = depositCommandValidator.validate("Deposit 12345678 3000");
		assertFalse(actual);
	}

	@Test
	public void deposit_into_a_cd() {
		actual = depositCommandValidator.validate("Deposit 99999999 1000");
		assertFalse(actual);
	}

	@Test
	public void deposit_into_an_account_with_negative_value() {
		actual = depositCommandValidator.validate("Deposit 12345678 -1000");
		assertFalse(actual);
	}

	@Test
	public void deposit_into_an_account_with_wrong_id_command() {
		actual = depositCommandValidator.validate("Deposit savings 500");
		assertFalse(actual);
	}

	@Test
	public void deposit_into_an_account_with_wrong_deposit_value_command() {
		actual = depositCommandValidator.validate("Deposit 12345678 savings");
		assertFalse(actual);
	}
}
