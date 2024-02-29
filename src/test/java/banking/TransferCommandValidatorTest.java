package banking;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TransferCommandValidatorTest {
	public static final String SAVINGS_ACCOUNT_ID1 = "12345678";
	public static final String SAVINGS_ACCOUNT_ID2 = "12345688";
	public static final String CHECKING_ACCOUNT_ID1 = "12345679";
	public static final String CHECKING_ACCOUNT_ID2 = "12345677";
	public static final String CD_ACCOUNT_ID = "99999999";
	public static final double APR = 3;
	public static final double CHECKING_AND_DEPOSIT_STARTING_BALANCE = 0;
	public static final double CD_ACCOUNT_STARTING_BALANCE = 1000;
	DepositCommandValidator depositCommandValidator;
	TransferCommandValidator transferCommandValidator;
	Bank bank;
	boolean actual;

	@BeforeEach
	public void setUp() {
		bank = new Bank();
		bank.addSavingAccount(SAVINGS_ACCOUNT_ID1, APR, CHECKING_AND_DEPOSIT_STARTING_BALANCE);
		bank.addSavingAccount(SAVINGS_ACCOUNT_ID2, APR, CHECKING_AND_DEPOSIT_STARTING_BALANCE);
		bank.addCDAccount(CD_ACCOUNT_ID, APR, CD_ACCOUNT_STARTING_BALANCE);
		bank.addCheckingAccount(CHECKING_ACCOUNT_ID1, APR, CHECKING_AND_DEPOSIT_STARTING_BALANCE);
		bank.addCheckingAccount(CHECKING_ACCOUNT_ID2, APR, CHECKING_AND_DEPOSIT_STARTING_BALANCE);

		depositCommandValidator = new DepositCommandValidator(bank);
		transferCommandValidator = new TransferCommandValidator(bank);
	}

	@Test
	public void transfer_from_an_saving_to_saving_account_with_valid_command() {

		actual = transferCommandValidator.validate("Transfer 12345678 12345688 200");
		assertTrue(actual);
	}

	@Test
	public void transfer_from_an_saving_to_saving_account_with_zero() {

		actual = transferCommandValidator.validate("Transfer 12345678 12345688 0");
		assertTrue(actual);
	}

	@Test
	public void transfer_from_an_saving_to_saving_account_with_maximum_amount() {

		actual = transferCommandValidator.validate("Transfer 12345678 12345688 1000");
		assertTrue(actual);
	}

	@Test
	public void transfer_from_an_saving_to_saving_account_with_exceed_maximum() {

		actual = transferCommandValidator.validate("Transfer 12345678 12345688 1100");
		assertFalse(actual);
	}

	// saving_to checking
	@Test
	public void transfer_from_an_saving_to_checking_account_with_valid_command() {
		bank.depositById(SAVINGS_ACCOUNT_ID1, 200);

		actual = transferCommandValidator.validate("Transfer 12345678 12345679 200");
		assertTrue(actual);
	}

	@Test
	public void transfer_from_an_saving_to_checking_account_with_zero() {

		actual = transferCommandValidator.validate("Transfer 12345678 12345679 0");
		assertTrue(actual);
	}

	@Test
	public void transfer_from_an_saving_to_checking_account_with_maximum_amount() {

		actual = transferCommandValidator.validate("Transfer 12345678 12345679 1000");
		assertTrue(actual);
	}

	@Test
	public void transfer_from_an_saving_to_checking_account_with_exceed_maximum() {

		actual = transferCommandValidator.validate("Transfer 12345678 12345679 1100");
		assertFalse(actual);
	}

	// checking to checking
	@Test
	public void transfer_from_an_checking_to_checking_account_with_valid_command() {

		actual = transferCommandValidator.validate("Transfer 12345679 12345677 200");
		assertTrue(actual);
	}

	@Test
	public void transfer_from_an_checking_to_checking_account_with_zero() {

		actual = transferCommandValidator.validate("Transfer 12345679 12345677 0");
		assertTrue(actual);
	}

	@Test
	public void transfer_from_an_checking_to_checking_account_with_maximum_amount() {

		actual = transferCommandValidator.validate("Transfer 12345679 12345677 400");
		assertTrue(actual);
	}

	@Test
	public void transfer_from_an_checking_to_checking_account_with_exceed_maximum() {

		actual = transferCommandValidator.validate("Transfer 12345679 12345677 1100");
		assertFalse(actual);
	}

	// checking to saving
	@Test
	public void transfer_from_an_checking_to_saving_account_with_valid_command() {

		actual = transferCommandValidator.validate("Transfer 12345679 12345678 200");
		assertTrue(actual);
	}

	@Test
	public void transfer_from_an_checking_to_saving_account_with_zero() {

		actual = transferCommandValidator.validate("Transfer 12345679 12345678 0");
		assertTrue(actual);
	}

	@Test
	public void transfer_from_an_checking_to_saving_account_with_maximum_amount() {

		actual = transferCommandValidator.validate("Transfer 12345679 12345678 400");
		assertTrue(actual);
	}

	@Test
	public void transfer_from_an_checking_to_saving_account_with_exceed_maximum() {

		actual = transferCommandValidator.validate("Transfer 12345679 12345678 1100");
		assertFalse(actual);
	}

	@Test
	public void transfer_from_an_cd_to_saving_account_with_exceed_maximum() {

		actual = transferCommandValidator.validate("Transfer 12345678 99999999 1100");
		assertFalse(actual);
	}

	@Test
	public void transfer_from_an_saving_to_cd_account_with_exceed_maximum() {

		actual = transferCommandValidator.validate("Transfer 12345678 99999999 1100");
		assertFalse(actual);
	}

	@Test
	public void transfer_with_same_id() {

		actual = transferCommandValidator.validate("Transfer 12345678 12345678 1100");
		assertFalse(actual);
	}

	@Test
	public void transfer_with_missing_one_id() {

		actual = transferCommandValidator.validate("Transfer  12345678 1100");
		assertFalse(actual);
	}

	@Test
	public void transfer_with_missing_all_id() {

		actual = transferCommandValidator.validate("Transfer  1100");
		assertFalse(actual);
	}

	@Test
	public void transfer_with_one_typo_id() {

		actual = transferCommandValidator.validate("Transfer 1@345678 12345678 1100");
		assertFalse(actual);
	}

	@Test
	public void transfer_with_back_id_typo() {

		actual = transferCommandValidator.validate("Transfer 12345678 12345@78 1100");
		assertFalse(actual);
	}

	@Test
	public void transfer_with_amount_typo() {

		actual = transferCommandValidator.validate("Transfer 12345678 12345677 1@00");
		assertFalse(actual);
	}

	@Test
	public void transfer_with_missing_amount() {

		actual = transferCommandValidator.validate("Transfer 12345678 12345677 ");
		assertFalse(actual);
	}

	@Test
	public void transfer_with_missing_transfer() {

		actual = transferCommandValidator.validate("12345678 12345677 1000");
		assertFalse(actual);
	}

	@Test
	public void transfer_with_transfer_typo() {

		actual = transferCommandValidator.validate("Tran@fer 12345678 12345677 1000");
		assertFalse(actual);
	}

	@Test
	public void transfer_with_extra_argument() {
		actual = transferCommandValidator.validate("Transfer 12345678 12345677 1000 foo");
		assertFalse(actual);
	}
}
