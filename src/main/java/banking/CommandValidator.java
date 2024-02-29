package banking;

public class CommandValidator {
	protected String command;
	protected String id = "";
	protected Bank bank;
	protected String[] inputArray;
	CreationCommandValidator creationCommandValidator;
	DepositCommandValidator depositCommandValidator;
	TransferCommandValidator transferCommandValidator;
	WithdrawCommandValidator withdrawCommandValidator;
	PassCommandValidator passCommandValidator;

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
		case "withdraw":
			withdrawCommandValidator = new WithdrawCommandValidator(bank);
			return withdrawCommandValidator.validate(input);
		case "transfer":
			transferCommandValidator = new TransferCommandValidator(bank);
			return transferCommandValidator.validate(input);
		case "pass":
			passCommandValidator = new PassCommandValidator(bank);
			return passCommandValidator.validate(input);
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

	protected boolean isEnoughInput(String[] inputArray) {

		switch (command) {
		case "create":
			String accountType = inputArray[1];
			return isSavingOrChecking(inputArray, accountType) || isCd(inputArray, accountType);

		case "deposit":
			return inputArray.length == 3;
		case "withdraw":
			return inputArray.length == 3;
		case "transfer":
			return inputArray.length == 4;
		case "pass":
			return inputArray.length == 2;
		default:
			return false;
		}
	}

	protected boolean isNumberString(String input) {
		return input.matches("[0-9]+");
	}

	protected boolean isDecimalNumberString(String input) {
		return input.matches("[0-9]+(\\.[0-9]+)?");
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

	private boolean isCd(String[] inputArray, String accountType) {
		return inputArray.length == 5 && (accountType.equals("cd"));
	}

	private boolean isSavingOrChecking(String[] inputArray, String accountType) {
		return inputArray.length == 4 && !accountType.equals("cd");
	}

}
