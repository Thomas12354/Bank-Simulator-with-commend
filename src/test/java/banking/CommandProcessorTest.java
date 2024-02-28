package banking;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CommandProcessorTest {
	public static final String ID = "12345678";
	public static final double APR = 3;
	public static final double CD_ACCOUNT_STARTING_BALANCE = 2000;
	public static final double CHECKING_AND_SAVING_ACCOUNT_STARTING_BALANCE = 0;
	public static final double DEPOSIT_AMOUNT = 100;
	public static final String CHECKING_ACCOUNT_TYPE = "checking";
	public static final String CD_ACCOUNT_TYPE = "cd";
	public static final String SAVINGS_ACCOUNT_TYPE = "savings";
	public static final int DEDUCT_AMOUNT = 25;

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
		commandProcessor.processCommand("Create cd 12345678 3 2000");
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
		bank.depositById(ID, 100);
		commandProcessor.processCommand("deposit 12345678 100");
		assertEquals(DEPOSIT_AMOUNT + 100, bank.getAccount().get(ID).getBalance());
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
	public void pass_one_month_with_correct_balance_cd_account() {
		bank.addCDAccount(ID, 2.1, CD_ACCOUNT_STARTING_BALANCE);
		commandProcessor.processCommand("Pass 1");

		assertEquals(2014.0367928937578, bank.getAccount().get(ID).getBalance());
	}
}