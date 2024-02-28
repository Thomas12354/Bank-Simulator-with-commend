package banking;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PassCommandValidatorTest {
	PassCommandValidator passCommandValidator;
	Bank bank;
	boolean actual;

	@BeforeEach
	public void setUp() {
		bank = new Bank();
		passCommandValidator = new PassCommandValidator(bank);
	}

	@Test
	public void pass_time_with_valid_comment() {
		actual = passCommandValidator.validate("Pass 1");
		assertTrue(actual);
	}

	@Test
	public void pass_time_with_maximum_month() {
		actual = passCommandValidator.validate("pass 60");
		assertTrue(actual);
	}

	@Test
	public void pass_time_with_zero_month() {
		actual = passCommandValidator.validate("pass 0");
		assertFalse(actual);
	}

	@Test
	public void pass_time_with_exceed_month() {
		actual = passCommandValidator.validate("pass 65");
		assertFalse(actual);
	}

	@Test
	public void pass_time_with_negative_month() {
		actual = passCommandValidator.validate("pass -6");
		assertFalse(actual);
	}

	@Test
	public void pass_time_with_half_month() {
		actual = passCommandValidator.validate("pass 0.5");
		assertFalse(actual);
	}
}
