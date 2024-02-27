package banking;

public class CommandValidator {
	protected String command;
	protected String id = "";
	protected Bank bank;
	protected String[] inputArray;
	CreationCommandValidator creationCommandValidator;
	DepositCommandValidator depositCommandValidator;

	public CommandValidator(Bank bank) {
		this.bank = bank;
	}

	public boolean validate(String input) {
		inputArray = inputProcess(input);

		switch (command) {
		case "create":
			creationCommandValidator = new CreationCommandValidator(bank);
			return creationCommandValidator.validate(input);

		case "deposit":
			depositCommandValidator = new DepositCommandValidator(bank);
			return depositCommandValidator.validate(input);
		default:
			return false;
		}

	}

	protected String[] inputProcess(String input) {
		inputArray = inputStringConvert(input);
		command = setCommand();
		return inputArray;
	}

	protected String[] inputStringConvert(String input) {
		input = input.toLowerCase();
		inputArray = input.split("\\s");
		return inputArray;
	}

	protected boolean idStringCheck() {
		return idLengthCheck() && isNumberString(id);
	}

	protected boolean isExtraSpace(String[] input_array) {
		for (String input : input_array) {
			if (input.isEmpty()) {
				return true;
			}
		}
		return false;
	}

	protected boolean isEnoughInput(String[] input_array) {
		String accountType = input_array[1];

		switch (command) {
		case "create":
			return isSavingOrChecking(input_array, accountType) || isCd(input_array, accountType);

		case "deposit":
			return input_array.length == 3;
		case "transfer":
			return input_array.length == 4;
		default:
			return false;
		}
	}

	protected boolean isNumberString(String input) {
		return input.matches("[0-9]+");
	}

	protected boolean isAccountExist(String id) {
		return bank.isAccountExist(id);
	}

	private boolean idLengthCheck() {
		return id.length() == 8;
	}

	private String setCommand() {
		return inputArray[0];
	}

	private boolean isCd(String[] input_array, String accountType) {
		return input_array.length == 5 && (accountType.equals("cd"));
	}

	private boolean isSavingOrChecking(String[] input_array, String accountType) {
		return input_array.length == 4 && !accountType.equals("cd");
	}

}
