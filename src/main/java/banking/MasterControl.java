package banking;

import java.util.List;

public class MasterControl {
	private CommandValidator commandValidator;
	private CommandProcessor commandProcessor;
	private CommandStorage commandStorage;

	public MasterControl(CommandValidator commandValidator, CommandProcessor commandProcessor,
			CommandStorage commandStorage) {
		this.commandValidator = commandValidator;
		this.commandProcessor = commandProcessor;
		this.commandStorage = commandStorage;
	}

	public static String inputStringConvert(String input) {
		input = input.toLowerCase();
		String command;
		command = input.split("\\s")[0];
		return command;
	}

	public List<String> start(List<String> input) {
		for (String command : input) {
			if (commandValidator.validate(command)) {
				commandProcessor.processCommand(command);
				String commandSplit = inputStringConvert(command);
				if (!commandSplit.equals("pass") && !commandSplit.equals("create")) {
					commandStorage.addValidCommand(command);
				}
			} else {
				commandStorage.addInvalidCommand(command);
			}
		}
		return commandStorage.getCommands();
	}
}
