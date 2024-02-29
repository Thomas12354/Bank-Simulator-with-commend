package banking;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class WithdrawCommandValidatorTest {
	public static final String SAVINGS_ACCOUNT_ID = "12345678";
	public static final String CHECKING_ACCOUNT_ID = "12345679";
	public static final String CD_ACCOUNT_ID = "99999999";
	public static final double APR = 3;
	public static final double CHECKING_AND_DEPOSIT_STARTING_BALANCE = 0;
	public static final double CD_ACCOUNT_STARTING_BALANCE = 1000;
	DepositCommandValidator depositCommandValidator;
	WithdrawCommandValidator withdrawCommandValidator;

	Bank bank;
	boolean actual;

	@BeforeEach
	public void setUp() {
		bank = new Bank();
		bank.addSavingAccount(SAVINGS_ACCOUNT_ID, APR, CHECKING_AND_DEPOSIT_STARTING_BALANCE);
		bank.addCDAccount(CD_ACCOUNT_ID, APR, CD_ACCOUNT_STARTING_BALANCE);
		bank.addCheckingAccount(CHECKING_ACCOUNT_ID, APR, CHECKING_AND_DEPOSIT_STARTING_BALANCE);

		depositCommandValidator = new DepositCommandValidator(bank);
		withdrawCommandValidator = new WithdrawCommandValidator(bank);
	}

	@Test
	public void withdraw_from_an_saving_account_with_valid_comment() {
		bank.depositById(SAVINGS_ACCOUNT_ID, 200);
		actual = withdrawCommandValidator.validate("Withdraw 12345678 100");
		assertTrue(actual);
	}

	@Test
	public void withdraw_from_an_saving_account_with_maximum_amount() {
		bank.depositById(SAVINGS_ACCOUNT_ID, 200);
		actual = withdrawCommandValidator.validate("Withdraw 12345678 1000");
		assertTrue(actual);
	}

	@Test
	public void withdraw_from_an_saving_account_with_exceed_maximum() {
		bank.depositById(SAVINGS_ACCOUNT_ID, 200);
		actual = withdrawCommandValidator.validate("Withdraw 12345678 2000");
		assertFalse(actual);
	}

	@Test
	public void withdraw_zero_from_an_saving_account() {
		bank.depositById(SAVINGS_ACCOUNT_ID, 200);
		actual = withdrawCommandValidator.validate("Withdraw 12345678 0");
		assertTrue(actual);
	}

	@Test
	public void withdraw_from_an_saving_account_with_negative_amount() {
		actual = withdrawCommandValidator.validate("Withdraw 12345678 -100");
		assertFalse(actual);
	}

	@Test
	public void withdraw_twice_from_an_saving_account_with_valid_comment() {
		bank.depositById(SAVINGS_ACCOUNT_ID, 200);
		bank.withdrawById(SAVINGS_ACCOUNT_ID, 100);

		actual = withdrawCommandValidator.validate("Withdraw 12345678 100");

		assertFalse(actual);
	}

	@Test
	public void withdraw_from_an_checking_account_with_maximum_amount() {
		bank.depositById(CHECKING_ACCOUNT_ID, 200);
		actual = withdrawCommandValidator.validate("Withdraw 12345679 400");
		assertTrue(actual);
	}

	@Test
	public void withdraw_from_an_checking_account_with_exceed_maximum() {
		bank.depositById(CHECKING_ACCOUNT_ID, 200);
		actual = withdrawCommandValidator.validate("Withdraw 12345679 1000");
		assertFalse(actual);
	}

	@Test
	public void withdraw_from_an_checking_account_with_zero_amount() {
		bank.depositById(CHECKING_ACCOUNT_ID, 200);
		actual = withdrawCommandValidator.validate("Withdraw 12345679 0");
		assertTrue(actual);
	}

	@Test
	public void withdraw_from_an_checking_account_with_negative_amount() {
		actual = withdrawCommandValidator.validate("Withdraw 12345679 -100");
		assertFalse(actual);
	}

	@Test
	public void withdraw_from_an_cd_account_with_valid_command() {
		bank.setTime(12);
		actual = withdrawCommandValidator.validate("Withdraw 99999999 1127.3280210399332");
		assertTrue(actual);
	}

	@Test
	public void withdraw_from_an_cd_account_with_exceed_balance() {
		bank.setTime(12);

		actual = withdrawCommandValidator.validate("Withdraw 99999999 2000");
		assertTrue(actual);
	}

	@Test
	public void withdraw_from_an_cd_account_with_zero_amount() {
		bank.setTime(12);

		actual = withdrawCommandValidator.validate("Withdraw 99999999 0");
		assertFalse(actual);
	}

	@Test
	public void withdraw_from_an_cd_account_with_negative_amount() {
		bank.setTime(12);

		actual = withdrawCommandValidator.validate("Withdraw 99999999 -100");
		assertFalse(actual);
	}

	@Test
	public void withdraw_from_an_cd_account_with_below_balance() {
		bank.setTime(12);

		actual = withdrawCommandValidator.validate("Withdraw 99999999 500");
		assertFalse(actual);
	}

	@Test
	public void withdraw_from_an_cd_account_with_not_reach_12_month() {
		bank.setTime(5);

		actual = withdrawCommandValidator.validate("Withdraw 99999999 1000");
		assertFalse(actual);
	}

	@Test
	public void withdraw_from_an_checking_account_with_invalid_id() {
		actual = withdrawCommandValidator.validate("Withdraw 1234569 100");
		assertFalse(actual);
	}

	@Test
	public void withdraw_from_an_checking_account_with_typo_id() {
		actual = withdrawCommandValidator.validate("Withdraw 12345@69 100");
		assertFalse(actual);
	}

	@Test
	public void withdraw_from_an_account_with_typo_command() {
		actual = withdrawCommandValidator.validate("Wit@draw 12345679 100");
		assertFalse(actual);
	}

	@Test
	public void withdraw_from_an_account_with_typo_balance() {
		actual = withdrawCommandValidator.validate("Withdraw 12345679 1@0");
		assertFalse(actual);
	}

	@Test
	public void withdraw_from_an_account_with_missing_withdraw() {
		actual = withdrawCommandValidator.validate(" 12345789 1@0");
		assertFalse(actual);
	}

	@Test
	public void withdraw_from_an_account_with_missing_id() {
		actual = withdrawCommandValidator.validate("Withdraw  100");
		assertFalse(actual);
	}

	@Test
	public void withdraw_from_an_account_with_missing_amount() {
		actual = withdrawCommandValidator.validate("Withdraw 12345679");
		assertFalse(actual);
	}

	@Test
	public void withdraw_from_an_account_with_wrong_amount() {
		actual = withdrawCommandValidator.validate("Withdraw 12345679 saving ");
		assertFalse(actual);
	}

	@Test
	public void withdraw_from_an_account_with_wrong_id() {
		actual = withdrawCommandValidator.validate("Withdraw saving 100");
		assertFalse(actual);
	}

	@Test
	public void withdraw_with_extra_argument() {
		actual = withdrawCommandValidator.validate("Withdraw 12345679 200 foo");
		assertFalse(actual);
	}
}
