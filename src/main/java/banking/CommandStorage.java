package banking;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class CommandStorage {
	private final ArrayList<String> invalidStorage;
	private final ArrayList<String> validStorage;
	private final ArrayList<String> idStorage;
	private final ArrayList<String> accountStateStorage;

	Bank bank;

	CommandStorage(Bank bank) {
		invalidStorage = new ArrayList<>();
		validStorage = new ArrayList<>();
		this.bank = bank;
		idStorage = new ArrayList<>();
		accountStateStorage = new ArrayList<>();

	}

	public void addValidCommand(String input) {
		validStorage.add(input);

	}

	public void addInvalidCommand(String input) {
		invalidStorage.add(input);
	}

	public ArrayList<String> getInvalidCommands() {
		return invalidStorage;
	}

	public ArrayList<String> getCommands() {
		getOpenAccountState();
		ArrayList<String> outputList = new ArrayList<>();
		for (int i = 0; i < accountStateStorage.size(); i++) {
			addOpenAccount(outputList, i);
			for (String fullCommand : validStorage) {
				String[] commandArray = inputStringConvert(fullCommand);
				if (!outputList.contains(fullCommand)
						&& (commandArray[0].equals("transfer") || idStorage.get(i).equals(commandArray[1]))) {
					outputList.add(fullCommand);
				}
			}
		}

		addInvalidCommandToFinalOutput(outputList);
		return outputList;
	}

	private void addOpenAccount(ArrayList<String> commands, int i) {
		commands.add(accountStateStorage.get(i));
	}

	private void addInvalidCommandToFinalOutput(ArrayList<String> commands) {
		commands.addAll(invalidStorage);
	}

	private void getOpenAccountState() {
		DecimalFormat decimalFormat = new DecimalFormat("0.00");
		for (String id : bank.getAccount().keySet()) {
			idStorage.add(id);
			accountStateStorage.add(
					getName(id) + " " + id + " " + getBalance(decimalFormat, id) + " " + getApr(decimalFormat, id));
		}

	}

	private String getApr(DecimalFormat decimalFormat, String id) {
		return decimalFormat.format(bank.getAccount().get(id).getApr());
	}

	private String getBalance(DecimalFormat decimalFormat, String id) {
		return decimalFormat.format(bank.getAccount().get(id).getBalance());
	}

	private String getName(String id) {
		return bank.getAccount().get(id).getName();
	}

	private String[] inputStringConvert(String input) {
		input = input.toLowerCase();
		String[] command;
		command = input.split("\\s");
		return command;
	}
}
