package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CDAccountTest {

	public static final double CD_ACCOUNT_STARTING_BALANCE = 1000;
	public static final double APR = 3;
	CDAccount cdAccount;

	@BeforeEach
	void setUp() {
		cdAccount = new CDAccount(APR, CD_ACCOUNT_STARTING_BALANCE);
	}

	@Test
	void cd_account_has_correct_balance_initially() {
		assertEquals(CD_ACCOUNT_STARTING_BALANCE, cdAccount.getBalance());
	}
}
