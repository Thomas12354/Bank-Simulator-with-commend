import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CommandStorageTest {
	public static final String INVALID_INPUT = "cat checking 12345678 0.6";

	CommandStorage commandStorage;

	@BeforeEach
	public void setUp() {
		commandStorage = new CommandStorage();
	}

	@Test
	public void adding_a_command_into_storage() {
		commandStorage.addInvalidCommand(INVALID_INPUT);
		assertTrue(commandStorage.getInvalidCommands().contains(INVALID_INPUT));
	}

}
