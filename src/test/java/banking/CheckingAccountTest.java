package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CheckingAccountTest {
	public static final double APR = 3;
	public static final double CHECKING_ACCOUNT_STARTING_BALANCE = 0;

	CheckingAccount checkingAccount;

	@BeforeEach
	void setUp() {
		checkingAccount = new CheckingAccount(APR, CHECKING_ACCOUNT_STARTING_BALANCE);
	}

	@Test
	void checking_account_has_correct_balance_initially() {
		assertEquals(CHECKING_ACCOUNT_STARTING_BALANCE, checkingAccount.getBalance());
	}
}
