package banking;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CommandProcessorTest {
	public static final String ID = "12345678";
	public static final double APR = 3;
	public static final double CD_ACCOUNT_STARTING_BALANCE = 1000;
	public static final double CHECKING_AND_SAVING_ACCOUNT_STARTING_BALANCE = 0;
	public static final double DEPOSIT_AMOUNT = 100;
	public static final String CHECKING_ACCOUNT_TYPE = "Checking";
	public static final String CD_ACCOUNT_TYPE = "Cd";
	public static final String SAVINGS_ACCOUNT_TYPE = "Savings";
	public static final int DEDUCT_AMOUNT = 25;
	public static final String SAVINGS_ACCOUNT_ID1 = "12345678";
	public static final String SAVINGS_ACCOUNT_ID2 = "12345688";
	public static final String CHECKING_ACCOUNT_ID1 = "12345679";
	public static final String CHECKING_ACCOUNT_ID2 = "12345677";
	public static final String CD_ACCOUNT_ID = "99999999";
	Bank bank;
	CommandProcessor commandProcessor;

	@BeforeEach
	public void setUp() {
		bank = new Bank();
		commandProcessor = new CommandProcessor(bank);

	}

	@Test
	public void create_an_checking_account_with_correct_ID_and_APR() {
		commandProcessor.processCommand("Create checking 12345678 3");
		assertEquals(CHECKING_ACCOUNT_TYPE, bank.getAccount().get(ID).getName());
		assertTrue(bank.isAccountExist(ID));
		assertEquals(APR, bank.getAccount().get(ID).getApr());
		assertEquals(CHECKING_AND_SAVING_ACCOUNT_STARTING_BALANCE, bank.getAccount().get(ID).getBalance());
	}

	@Test
	public void create_an_saving_account_with_correct_ID_and_APR() {
		commandProcessor.processCommand("Create savings 12345678 3");
		assertEquals(SAVINGS_ACCOUNT_TYPE, bank.getAccount().get(ID).getName());
		assertTrue(bank.isAccountExist(ID));
		assertEquals(APR, bank.getAccount().get(ID).getApr());
		assertEquals(CHECKING_AND_SAVING_ACCOUNT_STARTING_BALANCE, bank.getAccount().get(ID).getBalance());

	}

	@Test
	public void create_an_checking_account_with_capital_word() {
		commandProcessor.processCommand("creAte cHecKing 12345678 3");
		assertEquals(CHECKING_ACCOUNT_TYPE, bank.getAccount().get(ID).getName());
		assertTrue(bank.isAccountExist(ID));
		assertEquals(APR, bank.getAccount().get(ID).getApr());
		assertEquals(0, bank.getAccount().get(ID).getBalance());
	}

	@Test
	public void create_an_account_with_extra_space_in_end() {
		commandProcessor.processCommand("Create checking 12345678 3");
		assertEquals(CHECKING_ACCOUNT_TYPE, bank.getAccount().get(ID).getName());
		assertTrue(bank.isAccountExist(ID));
		assertEquals(APR, bank.getAccount().get(ID).getApr());
		assertEquals(0, bank.getAccount().get(ID).getBalance());
	}

	@Test
	public void create_cd_account_with_correct_ID_and_APR_balance() {
		commandProcessor.processCommand("Create cd 12345678 3 1000");
		assertEquals(CD_ACCOUNT_TYPE, bank.getAccount().get(ID).getName());
		assertTrue(bank.isAccountExist(ID));
		assertEquals(APR, bank.getAccount().get(ID).getApr());
		assertEquals(CD_ACCOUNT_STARTING_BALANCE, bank.getAccount().get(ID).getBalance());
	}

	@Test
	public void deposit_into_an_new_account() {
		bank.addCheckingAccount(ID, APR, CHECKING_AND_SAVING_ACCOUNT_STARTING_BALANCE);
		commandProcessor.processCommand("deposit 12345678 100");
		assertEquals(DEPOSIT_AMOUNT, bank.getAccount().get(ID).getBalance());
	}

	@Test
	public void deposit_zero_into_an_new_account() {
		bank.addCheckingAccount(ID, APR, CHECKING_AND_SAVING_ACCOUNT_STARTING_BALANCE);
		commandProcessor.processCommand("deposit 12345678 0");
		assertEquals(0, bank.getAccount().get(ID).getBalance());
	}

	@Test
	public void deposit_into_an_account_having_100_already() {
		bank.addCheckingAccount(ID, APR, CHECKING_AND_SAVING_ACCOUNT_STARTING_BALANCE);
		bank.depositById(ID, DEPOSIT_AMOUNT);
		commandProcessor.processCommand("deposit 12345678 100");
		assertEquals(DEPOSIT_AMOUNT + DEPOSIT_AMOUNT, bank.getAccount().get(ID).getBalance());
	}

	@Test
	public void withdraw_from_an_saving_account_with_valid_comment() {
		bank.addCheckingAccount(SAVINGS_ACCOUNT_ID1, APR, CHECKING_AND_SAVING_ACCOUNT_STARTING_BALANCE);

		bank.depositById(SAVINGS_ACCOUNT_ID1, DEPOSIT_AMOUNT);
		commandProcessor.processCommand("Withdraw 12345678 100");
		assertEquals(DEPOSIT_AMOUNT - 100, bank.getAccount().get(SAVINGS_ACCOUNT_ID1).getBalance());

	}

	@Test
	public void withdraw_from_an_saving_account_with_max_amount() {
		bank.addSavingAccount(SAVINGS_ACCOUNT_ID1, APR, CHECKING_AND_SAVING_ACCOUNT_STARTING_BALANCE);

		bank.depositById(SAVINGS_ACCOUNT_ID1, 1200);
		commandProcessor.processCommand("Withdraw 12345678 1000");
		assertEquals(1200 - 1000, bank.getAccount().get(SAVINGS_ACCOUNT_ID1).getBalance());

	}

	@Test
	public void withdraw_from_an_saving_account_with_zero_amount() {
		bank.addSavingAccount(SAVINGS_ACCOUNT_ID1, APR, CHECKING_AND_SAVING_ACCOUNT_STARTING_BALANCE);

		bank.depositById(SAVINGS_ACCOUNT_ID1, DEPOSIT_AMOUNT);
		commandProcessor.processCommand("Withdraw 12345678 0");
		assertEquals(DEPOSIT_AMOUNT - 0, bank.getAccount().get(SAVINGS_ACCOUNT_ID1).getBalance());
	}

	@Test
	public void withdraw_from_an_checking_account_with_max_amount() {
		bank.addCheckingAccount(CHECKING_ACCOUNT_ID1, APR, CHECKING_AND_SAVING_ACCOUNT_STARTING_BALANCE);
		bank.depositById(CHECKING_ACCOUNT_ID1, 600);
		commandProcessor.processCommand("Withdraw 12345679 400");
		assertEquals(600 - 400, bank.getAccount().get(CHECKING_ACCOUNT_ID1).getBalance());
	}

	@Test
	public void withdraw_from_an_checking_account_with_zero_amount() {
		bank.addCheckingAccount(CHECKING_ACCOUNT_ID1, APR, CHECKING_AND_SAVING_ACCOUNT_STARTING_BALANCE);
		bank.depositById(CHECKING_ACCOUNT_ID1, DEPOSIT_AMOUNT);
		commandProcessor.processCommand("Withdraw 12345679 0");
		assertEquals(DEPOSIT_AMOUNT, bank.getAccount().get(CHECKING_ACCOUNT_ID1).getBalance());
	}

	@Test
	public void withdraw_from_an_cd_account_with_valid_command() {
		bank.addCDAccount(CD_ACCOUNT_ID, APR, CD_ACCOUNT_STARTING_BALANCE);

		bank.setTime(12);
		commandProcessor.processCommand("Withdraw 99999999 1127.3280210399332");
		assertEquals(0, bank.getAccount().get(CD_ACCOUNT_ID).getBalance());
	}

	@Test
	public void withdraw_from_an_cd_account_with_exceed_balance() {
		bank.addCDAccount(CD_ACCOUNT_ID, APR, CD_ACCOUNT_STARTING_BALANCE);

		bank.setTime(12);

		commandProcessor.processCommand("Withdraw 99999999 2000");
		assertEquals(0, bank.getAccount().get(CD_ACCOUNT_ID).getBalance());
	}

	@Test
	public void transfer_from_an_saving_to_saving_account_with_valid_command() {
		bank.addCheckingAccount(SAVINGS_ACCOUNT_ID1, APR, CHECKING_AND_SAVING_ACCOUNT_STARTING_BALANCE);
		bank.addCheckingAccount(SAVINGS_ACCOUNT_ID2, APR, CHECKING_AND_SAVING_ACCOUNT_STARTING_BALANCE);
		bank.depositById(SAVINGS_ACCOUNT_ID1, DEPOSIT_AMOUNT);

		commandProcessor.processCommand("Transfer 12345678 12345688 100");
		assertEquals(0, bank.getAccount().get(SAVINGS_ACCOUNT_ID1).getBalance());
		assertEquals(DEPOSIT_AMOUNT, bank.getAccount().get(SAVINGS_ACCOUNT_ID2).getBalance());
	}

	@Test
	public void transfer_from_an_saving_to_saving_account_with_exceed_balance_of_sender() {
		bank.addCheckingAccount(SAVINGS_ACCOUNT_ID1, APR, CHECKING_AND_SAVING_ACCOUNT_STARTING_BALANCE);
		bank.addCheckingAccount(SAVINGS_ACCOUNT_ID2, APR, CHECKING_AND_SAVING_ACCOUNT_STARTING_BALANCE);
		bank.depositById(SAVINGS_ACCOUNT_ID1, DEPOSIT_AMOUNT);

		commandProcessor.processCommand("Transfer 12345678 12345688 300");
		assertEquals(0, bank.getAccount().get(SAVINGS_ACCOUNT_ID1).getBalance());
		assertEquals(DEPOSIT_AMOUNT, bank.getAccount().get(SAVINGS_ACCOUNT_ID2).getBalance());
	}

	@Test
	public void transfer_from_an_saving_to_saving_account_with_zero() {
		bank.addCheckingAccount(SAVINGS_ACCOUNT_ID1, APR, CHECKING_AND_SAVING_ACCOUNT_STARTING_BALANCE);
		bank.addCheckingAccount(SAVINGS_ACCOUNT_ID2, APR, CHECKING_AND_SAVING_ACCOUNT_STARTING_BALANCE);
		bank.depositById(SAVINGS_ACCOUNT_ID1, DEPOSIT_AMOUNT);

		commandProcessor.processCommand("Transfer 12345678 12345688 300");
		assertEquals(0, bank.getAccount().get(SAVINGS_ACCOUNT_ID1).getBalance());
		assertEquals(DEPOSIT_AMOUNT, bank.getAccount().get(SAVINGS_ACCOUNT_ID2).getBalance());
	}

	@Test
	public void transfer_from_an_saving_to_saving_account_with_maximum_amount() {
		bank.addCheckingAccount(SAVINGS_ACCOUNT_ID1, APR, CHECKING_AND_SAVING_ACCOUNT_STARTING_BALANCE);
		bank.addCheckingAccount(SAVINGS_ACCOUNT_ID2, APR, CHECKING_AND_SAVING_ACCOUNT_STARTING_BALANCE);
		bank.depositById(SAVINGS_ACCOUNT_ID1, 1000);

		commandProcessor.processCommand("Transfer 12345678 12345688 1000");
		assertEquals(0, bank.getAccount().get(SAVINGS_ACCOUNT_ID1).getBalance());
		assertEquals(1000, bank.getAccount().get(SAVINGS_ACCOUNT_ID2).getBalance());
	}

	@Test
	public void transfer_from_an_saving_to_checking_account_with_valid_command() {
		bank.addCheckingAccount(SAVINGS_ACCOUNT_ID1, APR, CHECKING_AND_SAVING_ACCOUNT_STARTING_BALANCE);
		bank.addCheckingAccount(CHECKING_ACCOUNT_ID1, APR, CHECKING_AND_SAVING_ACCOUNT_STARTING_BALANCE);
		bank.depositById(SAVINGS_ACCOUNT_ID1, 200);

		commandProcessor.processCommand("Transfer 12345678 12345679 100");
		assertEquals(100, bank.getAccount().get(SAVINGS_ACCOUNT_ID1).getBalance());
		assertEquals(100, bank.getAccount().get(CHECKING_ACCOUNT_ID1).getBalance());
	}

	@Test
	public void transfer_from_an_saving_to_checking_account_with_zero() {
		bank.addCheckingAccount(SAVINGS_ACCOUNT_ID1, APR, CHECKING_AND_SAVING_ACCOUNT_STARTING_BALANCE);
		bank.addCheckingAccount(CHECKING_ACCOUNT_ID1, APR, CHECKING_AND_SAVING_ACCOUNT_STARTING_BALANCE);
		bank.depositById(SAVINGS_ACCOUNT_ID1, 200);

		commandProcessor.processCommand("Transfer 12345678 12345679 0");
		assertEquals(200, bank.getAccount().get(SAVINGS_ACCOUNT_ID1).getBalance());
		assertEquals(0, bank.getAccount().get(CHECKING_ACCOUNT_ID1).getBalance());
	}

	@Test
	public void transfer_from_an_saving_to_checking_account_with_maximum_amount() {
		bank.addCheckingAccount(SAVINGS_ACCOUNT_ID1, APR, CHECKING_AND_SAVING_ACCOUNT_STARTING_BALANCE);
		bank.addCheckingAccount(CHECKING_ACCOUNT_ID1, APR, CHECKING_AND_SAVING_ACCOUNT_STARTING_BALANCE);
		bank.depositById(SAVINGS_ACCOUNT_ID1, 2000);

		commandProcessor.processCommand("Transfer 12345678 12345679 1000");
		assertEquals(1000, bank.getAccount().get(SAVINGS_ACCOUNT_ID1).getBalance());
		assertEquals(1000, bank.getAccount().get(CHECKING_ACCOUNT_ID1).getBalance());
	}

	@Test
	public void transfer_from_an_checking_to_checking_account_with_valid_command() {
		bank.addCheckingAccount(CHECKING_ACCOUNT_ID1, APR, CHECKING_AND_SAVING_ACCOUNT_STARTING_BALANCE);
		bank.addCheckingAccount(CHECKING_ACCOUNT_ID2, APR, CHECKING_AND_SAVING_ACCOUNT_STARTING_BALANCE);
		bank.depositById(CHECKING_ACCOUNT_ID1, 200);

		commandProcessor.processCommand("Transfer 12345679 12345677 200");
		assertEquals(0, bank.getAccount().get(CHECKING_ACCOUNT_ID1).getBalance());
		assertEquals(200, bank.getAccount().get(CHECKING_ACCOUNT_ID2).getBalance());
	}

	@Test
	public void transfer_from_an_checking_to_checking_account_with_zero() {
		bank.addCheckingAccount(CHECKING_ACCOUNT_ID1, APR, CHECKING_AND_SAVING_ACCOUNT_STARTING_BALANCE);
		bank.addCheckingAccount(CHECKING_ACCOUNT_ID2, APR, CHECKING_AND_SAVING_ACCOUNT_STARTING_BALANCE);
		bank.depositById(CHECKING_ACCOUNT_ID1, 200);

		commandProcessor.processCommand("Transfer 12345679 12345677 0");
		assertEquals(200, bank.getAccount().get(CHECKING_ACCOUNT_ID1).getBalance());
		assertEquals(0, bank.getAccount().get(CHECKING_ACCOUNT_ID2).getBalance());
	}

	@Test
	public void transfer_from_an_checking_to_checking_account_with_maximum_amount() {
		bank.addCheckingAccount(CHECKING_ACCOUNT_ID1, APR, CHECKING_AND_SAVING_ACCOUNT_STARTING_BALANCE);
		bank.addCheckingAccount(CHECKING_ACCOUNT_ID2, APR, CHECKING_AND_SAVING_ACCOUNT_STARTING_BALANCE);
		bank.depositById(CHECKING_ACCOUNT_ID1, 500);

		commandProcessor.processCommand("Transfer 12345679 12345677 400");
		assertEquals(100, bank.getAccount().get(CHECKING_ACCOUNT_ID1).getBalance());
		assertEquals(400, bank.getAccount().get(CHECKING_ACCOUNT_ID2).getBalance());
	}

	@Test
	public void transfer_from_an_checking_to_saving_account_with_valid_command() {

		bank.addCheckingAccount(CHECKING_ACCOUNT_ID1, APR, CHECKING_AND_SAVING_ACCOUNT_STARTING_BALANCE);
		bank.addSavingAccount(SAVINGS_ACCOUNT_ID1, APR, CHECKING_AND_SAVING_ACCOUNT_STARTING_BALANCE);
		bank.depositById(CHECKING_ACCOUNT_ID1, 400);

		commandProcessor.processCommand("Transfer 12345679 12345678 400");
		assertEquals(0, bank.getAccount().get(CHECKING_ACCOUNT_ID1).getBalance());
		assertEquals(400, bank.getAccount().get(SAVINGS_ACCOUNT_ID1).getBalance());
	}

	@Test
	public void transfer_from_an_checking_to_saving_account_with_maximum_amount() {

		bank.addCheckingAccount(CHECKING_ACCOUNT_ID1, APR, CHECKING_AND_SAVING_ACCOUNT_STARTING_BALANCE);
		bank.addSavingAccount(SAVINGS_ACCOUNT_ID1, APR, CHECKING_AND_SAVING_ACCOUNT_STARTING_BALANCE);
		bank.depositById(CHECKING_ACCOUNT_ID1, 500);

		commandProcessor.processCommand("Transfer 12345679 12345678 400");
		assertEquals(500 - 400, bank.getAccount().get(CHECKING_ACCOUNT_ID1).getBalance());
		assertEquals(400, bank.getAccount().get(SAVINGS_ACCOUNT_ID1).getBalance());
	}

	@Test
	public void pass_time_with_valid_comment() {
		commandProcessor.processCommand("Pass 1");
		assertEquals(1, bank.getTime());
	}

	@Test
	public void pass_time_with_maximum_month() {
		commandProcessor.processCommand("pass 60");
		assertEquals(60, bank.getTime());
	}

	@Test
	public void pass_time_with_account_closed() {
		bank.addCheckingAccount(ID, APR, CHECKING_AND_SAVING_ACCOUNT_STARTING_BALANCE);

		commandProcessor.processCommand("Pass 1");

		assertFalse(bank.getAccount().containsKey(ID));
	}

	@Test
	public void pass_time_with_account_below_one_hundred() {
		bank.addCheckingAccount(ID, APR, CHECKING_AND_SAVING_ACCOUNT_STARTING_BALANCE);
		bank.depositById(ID, 98);
		commandProcessor.processCommand("Pass 1");

		assertEquals(98 - DEDUCT_AMOUNT, bank.getAccount().get(ID).getBalance());
	}

	@Test
	public void pass_time_with_account_one_hundred() {
		bank.addCheckingAccount(ID, APR, CHECKING_AND_SAVING_ACCOUNT_STARTING_BALANCE);
		bank.depositById(ID, 100);
		commandProcessor.processCommand("Pass 1");

		assertEquals(100.25, bank.getAccount().get(ID).getBalance());
	}

	@Test
	public void pass_one_month_with_correct_balance_checking_account() {
		bank.addCheckingAccount(ID, APR, CHECKING_AND_SAVING_ACCOUNT_STARTING_BALANCE);
		bank.depositById(ID, DEPOSIT_AMOUNT * 10);
		commandProcessor.processCommand("Pass 1");

		assertEquals(1002.5, bank.getAccount().get(ID).getBalance());
	}

	@Test
	public void pass_one_month_with_correct_balance_saving_account() {
		bank.addSavingAccount(ID, APR, CHECKING_AND_SAVING_ACCOUNT_STARTING_BALANCE);
		bank.depositById(ID, DEPOSIT_AMOUNT * 10);
		commandProcessor.processCommand("Pass 1");

		assertEquals(1002.5, bank.getAccount().get(ID).getBalance());
	}

	@Test
	public void pass_twelve_month_with_correct_balance_cd_account() {
		bank.addCDAccount(ID, APR, CD_ACCOUNT_STARTING_BALANCE);
		commandProcessor.processCommand("Pass 12");

		assertEquals(1127.3280210399332, bank.getAccount().get(ID).getBalance());
	}

}