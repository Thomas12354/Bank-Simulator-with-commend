package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AccountTest {
	public static final double APR = 3;
	public static final double DEPOSIT_AMOUNT = 100.5;
	public static final double WITHDRAW_AMOUNT = 50.5;
	public static final double CHECKING_AND_DEPOSIT_ACCOUNT_STARTING_BALANCE = 0;
	public static final double CD_ACCOUNT_STARTING_BALANCE = 1000;

	Account checkingAccount;
	Account savingAccount;
	Account cdAccount;

	@BeforeEach
	void setUp() {
		checkingAccount = new CheckingAccount(APR, CHECKING_AND_DEPOSIT_ACCOUNT_STARTING_BALANCE);
		savingAccount = new SavingAccount(APR, CHECKING_AND_DEPOSIT_ACCOUNT_STARTING_BALANCE);
		cdAccount = new CDAccount(APR, CD_ACCOUNT_STARTING_BALANCE);
	}

	@Test
	void account_has_apr() {
		assertEquals(APR, checkingAccount.getApr());
		assertEquals(APR, savingAccount.getApr());
		assertEquals(APR, cdAccount.getApr());
	}

	@Test
	void account_has_correct_balance_after_deposit() {
		checkingAccount.deposit(DEPOSIT_AMOUNT);
		savingAccount.deposit(DEPOSIT_AMOUNT);
		cdAccount.deposit(DEPOSIT_AMOUNT);

		assertEquals(DEPOSIT_AMOUNT, checkingAccount.getBalance());
		assertEquals(DEPOSIT_AMOUNT, savingAccount.getBalance());
		assertEquals(CD_ACCOUNT_STARTING_BALANCE + DEPOSIT_AMOUNT, cdAccount.getBalance());
	}

	@Test
	void account_has_correct_balance_after_withdraw() {
		checkingAccount.deposit(DEPOSIT_AMOUNT);
		savingAccount.deposit(DEPOSIT_AMOUNT);
		cdAccount.deposit(DEPOSIT_AMOUNT);

		checkingAccount.withdraw(WITHDRAW_AMOUNT);
		savingAccount.withdraw(WITHDRAW_AMOUNT);
		cdAccount.withdraw(WITHDRAW_AMOUNT);

		assertEquals(DEPOSIT_AMOUNT - WITHDRAW_AMOUNT, checkingAccount.getBalance());
		assertEquals(DEPOSIT_AMOUNT - WITHDRAW_AMOUNT, savingAccount.getBalance());
		assertEquals(CD_ACCOUNT_STARTING_BALANCE + DEPOSIT_AMOUNT - WITHDRAW_AMOUNT, cdAccount.getBalance());
	}

	@Test
	void account_cant_go_below_0() {
		checkingAccount.withdraw(WITHDRAW_AMOUNT);
		savingAccount.withdraw(WITHDRAW_AMOUNT);
		cdAccount.withdraw(1200);
		assertEquals(0, checkingAccount.getBalance());
		assertEquals(0, savingAccount.getBalance());
		assertEquals(0, cdAccount.getBalance());
	}

	@Test
	void account_has_correct_balance_after_depositing_twice() {
		checkingAccount.deposit(DEPOSIT_AMOUNT);
		savingAccount.deposit(DEPOSIT_AMOUNT);
		cdAccount.deposit(DEPOSIT_AMOUNT);

		checkingAccount.deposit(DEPOSIT_AMOUNT);
		savingAccount.deposit(DEPOSIT_AMOUNT);
		cdAccount.deposit(DEPOSIT_AMOUNT);

		assertEquals(DEPOSIT_AMOUNT * 2, checkingAccount.getBalance());
		assertEquals(DEPOSIT_AMOUNT * 2, savingAccount.getBalance());
		assertEquals(CD_ACCOUNT_STARTING_BALANCE + DEPOSIT_AMOUNT * 2, cdAccount.getBalance());
	}

	@Test
	void account_has_correct_balance_after_withdraw_twice() {

		checkingAccount.withdraw(WITHDRAW_AMOUNT);
		savingAccount.withdraw(WITHDRAW_AMOUNT);
		cdAccount.withdraw(WITHDRAW_AMOUNT);

		checkingAccount.withdraw(WITHDRAW_AMOUNT);
		savingAccount.withdraw(WITHDRAW_AMOUNT);
		cdAccount.withdraw(WITHDRAW_AMOUNT);

		assertEquals(CHECKING_AND_DEPOSIT_ACCOUNT_STARTING_BALANCE, checkingAccount.getBalance());
		assertEquals(CHECKING_AND_DEPOSIT_ACCOUNT_STARTING_BALANCE, savingAccount.getBalance());
		assertEquals(CD_ACCOUNT_STARTING_BALANCE - WITHDRAW_AMOUNT * 2, cdAccount.getBalance());
	}

	@Test
	void account_has_correct_balance_after_withdraw_all_balance() {
		checkingAccount.deposit(DEPOSIT_AMOUNT);

		checkingAccount.withdraw(DEPOSIT_AMOUNT * 2);

		assertEquals(0, checkingAccount.getBalance());

	}
}