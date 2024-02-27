package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CommandProcessorTest {
	public static final String ID = "12345678";
	public static final double APR = 0.6;
	public static final double CD_ACCOUNT_STARTING_BALANCE = 10000;
	public static final double CHECKING_AND_SAVING_ACCOUNT_STARTING_BALANCE = 0;
	public static final double DEPOSIT_AMOUNT = 100;
	public static final String CHECKING_ACCOUNT_TYPE = "checking";
	public static final String CD_ACCOUNT_TYPE = "cd";
	public static final String SAVINGS_ACCOUNT_TYPE = "savings";

	Bank bank;
	CommandProcessor commandProcessor;

	@BeforeEach
	public void setUp() {
		bank = new Bank();
		commandProcessor = new CommandProcessor(bank);
	}

	@Test
	public void create_an_checking_account_with_correct_ID_and_APR() {
		commandProcessor.processCommand("Create checking 12345678 0.6");
		assertEquals(CHECKING_ACCOUNT_TYPE, bank.getAccount().get(ID).getName());
		assertTrue(bank.isAccountExist(ID));
		assertEquals(APR, bank.getAccount().get(ID).getApr());
		assertEquals(CHECKING_AND_SAVING_ACCOUNT_STARTING_BALANCE, bank.getAccount().get(ID).getBalance());
	}

	@Test
	public void create_an_saving_account_with_correct_ID_and_APR() {
		commandProcessor.processCommand("Create savings 12345678 0.6");
		assertEquals(SAVINGS_ACCOUNT_TYPE, bank.getAccount().get(ID).getName());
		assertTrue(bank.isAccountExist(ID));
		assertEquals(APR, bank.getAccount().get(ID).getApr());
		assertEquals(CHECKING_AND_SAVING_ACCOUNT_STARTING_BALANCE, bank.getAccount().get(ID).getBalance());

	}

	@Test
	public void create_an_checking_account_with_capital_word() {
		commandProcessor.processCommand("creAte cHecKing 12345678 0.6");
		assertEquals(CHECKING_ACCOUNT_TYPE, bank.getAccount().get(ID).getName());
		assertTrue(bank.isAccountExist(ID));
		assertEquals(APR, bank.getAccount().get(ID).getApr());
		assertEquals(0, bank.getAccount().get(ID).getBalance());
	}

	@Test
	public void create_an_account_with_extra_space_in_end() {
		commandProcessor.processCommand("Create checking 12345678 0.6 ");
		assertEquals(CHECKING_ACCOUNT_TYPE, bank.getAccount().get(ID).getName());
		assertTrue(bank.isAccountExist(ID));
		assertEquals(APR, bank.getAccount().get(ID).getApr());
		assertEquals(0, bank.getAccount().get(ID).getBalance());
	}

	@Test
	public void create_cd_account_with_correct_ID_and_APR_balance() {
		commandProcessor.processCommand("Create cd 12345678 0.6 10000");
		assertEquals(CD_ACCOUNT_TYPE, bank.getAccount().get(ID).getName());
		assertTrue(bank.isAccountExist(ID));
		assertEquals(APR, bank.getAccount().get(ID).getApr());
		assertEquals(CD_ACCOUNT_STARTING_BALANCE, bank.getAccount().get(ID).getBalance());
	}

	@Test
	public void deposit_into_an_new_account() {
		bank.addCheckingAccount(ID, APR, 0);
		commandProcessor.processCommand("deposit 12345678 100");
		assertEquals(DEPOSIT_AMOUNT, bank.getAccount().get(ID).getBalance());
	}

	@Test
	public void deposit_zero_into_an_new_account() {
		bank.addCheckingAccount(ID, APR, 0);
		commandProcessor.processCommand("deposit 12345678 0");
		assertEquals(0, bank.getAccount().get(ID).getBalance());
	}

	@Test
	public void deposit_into_an_account_having_100_already() {
		bank.addCheckingAccount(ID, APR, 0);
		bank.depositById(ID, 100);
		commandProcessor.processCommand("deposit 12345678 100");
		assertEquals(DEPOSIT_AMOUNT + 100, bank.getAccount().get(ID).getBalance());
	}
}