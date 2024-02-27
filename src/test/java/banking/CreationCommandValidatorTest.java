package banking;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CreationCommandValidatorTest {
	public static final String ID_FIRST_ACCOUNT = "99999999";
	public static final double APR = 3;
	public static final double CHECKING_AND_DEPOSIT_STARTING_BALANCE = 0;
	CreationCommandValidator creationCommandValidator;
	Bank bank;
	boolean actual;

	@BeforeEach
	public void setUp() {
		bank = new Bank();
		creationCommandValidator = new CreationCommandValidator(bank);
	}

	@Test
	public void create_an_account_with_valid_comment() {
		actual = creationCommandValidator.validate("Create checking 12345678 0.6");
		assertTrue(actual);
	}

	@Test
	public void create_an_saving_account_with_valid_comment() {
		actual = creationCommandValidator.validate("Create savings 12345678 0.6");
		assertTrue(actual);
	}

	@Test
	public void create_an_account_with_extra_space_in_end() {
		actual = creationCommandValidator.validate("Create checking 12345678 0.6 ");
		assertTrue(actual);
	}

	@Test
	public void create_an_account_with_capital_words() {
		actual = creationCommandValidator.validate("creAte cHecKing 98765432 0.01");
		assertTrue(actual);
	}

	@Test
	public void create_an_account_with_zero_apr() {
		actual = creationCommandValidator.validate("Create checking 12345678 0");
		assertTrue(actual);
	}

	@Test
	public void create_cd_account_with_valid_command() {
		actual = creationCommandValidator.validate("Create cd 12345678 1.5 10000");
		assertTrue(actual);
	}

	@Test
	public void create_an_account_with_duplicate_id() {
		bank.addSavingAccount(ID_FIRST_ACCOUNT, APR, CHECKING_AND_DEPOSIT_STARTING_BALANCE);
		actual = creationCommandValidator.validate("Create checking 99999999 0.6");
		assertFalse(actual);
	}

	@Test
	public void create_an_account_with_missing_create() {
		actual = creationCommandValidator.validate("checking 12345678 -0.6");
		assertFalse(actual);
	}

	@Test
	public void create_an_account_with_typo_in_create() {
		actual = creationCommandValidator.validate("Creite cd 12345678 1.5 10000");
		assertFalse(actual);
	}

	@Test
	public void create_an_account_with_missing_account_type() {
		actual = creationCommandValidator.validate("Create 12345678 -0.6");
		assertFalse(actual);
	}

	@Test
	public void create_an_account_with_invalid_apr() {
		actual = creationCommandValidator.validate("Create checking 12345678 11");
		assertFalse(actual);
	}

	@Test
	public void create_an_account_with_negative_apr() {
		actual = creationCommandValidator.validate("Create checking 12345678 -0.6");
		assertFalse(actual);
	}

	@Test
	public void create_an_account_with_wrong_id() {
		actual = creationCommandValidator.validate("Create checking 1345678 -0.6");
		assertFalse(actual);
	}

	@Test
	public void create_an_account_with_wrong_account_type() {
		actual = creationCommandValidator.validate("Create cat 1345678 -0.6");
		assertFalse(actual);
	}

	@Test
	public void create_an_account_with_empty_string() {
		actual = creationCommandValidator.validate("");
		assertFalse(actual);
	}

	@Test
	public void create_an_account_with_extra_space_in_middle() {
		actual = creationCommandValidator.validate("Create checking  12345678 -0.6");
		assertFalse(actual);
	}

	@Test
	public void create_an_account_with_wrong_id_in_typing() {
		actual = creationCommandValidator.validate("Create savings 12345@78 0.5");
		assertFalse(actual);
	}

	@Test
	public void create_an_account_with_wrong_command_in_middle() {
		actual = creationCommandValidator.validate("Create savings acd 0.6");
		assertFalse(actual);
	}

	@Test
	public void create_an_account_with_insufficient_id() {
		actual = creationCommandValidator.validate("Create savings 12345 0.6");
		assertFalse(actual);
	}

	@Test
	public void create_an_account_with_missing_argument() {
		actual = creationCommandValidator.validate("Create savings");
		assertFalse(actual);
	}

	@Test
	public void create_cd_account_with_exceed_limit() {
		actual = creationCommandValidator.validate("Create cd 12345678 1.5 20000");
		assertFalse(actual);
	}

	@Test
	public void create_cd_account_with_negative_amount() {
		actual = creationCommandValidator.validate("Create cd 12345678 1.5 -10000");
		assertFalse(actual);
	}

	@Test
	public void create_cd_account_with_invalid_amount() {
		actual = creationCommandValidator.validate("Create cd 12345678 1.5 asdb");
		assertFalse(actual);
	}

	@Test
	public void create_cd_account_with_missing_balance() {
		actual = creationCommandValidator.validate("Create cd 12345678 1.5");
		assertFalse(actual);
	}
}
