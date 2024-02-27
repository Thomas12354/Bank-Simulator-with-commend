import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SavingAccountTest {
	public static final double APR = 3;
	public static final double SAVING_ACCOUNT_STARTING_BALANCE = 0;

	SavingAccount savingAccount;

	@BeforeEach
	void setUp() {
		savingAccount = new SavingAccount(APR, SAVING_ACCOUNT_STARTING_BALANCE);
	}

	@Test
	void saving_account_has_correct_balance_initially() {
		assertEquals(SAVING_ACCOUNT_STARTING_BALANCE, savingAccount.getBalance());
	}
}
