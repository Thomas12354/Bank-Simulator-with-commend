public class CommandProcessor {
	private static final String ACCOUNT_TYPE_CD = "cd";
	private static final String ACCOUNT_TYPE_SAVINGS = "savings";
	private static final String ACCOUNT_TYPE_CHECKING = "checking";
	Bank bank;
	private String id;
	private double balance;
	private String[] input_array;

	public CommandProcessor(Bank bank) {
		this.bank = bank;
	}

	public void processCommand(String input) {
		input_array = inputStringConvert(input);
		String command = input_array[0];
		switch (command) {
		case "create":
			account_creation();
			break;
		case "deposit":
			deposit();
			break;
		default:
			return;
		}

	}

	private String[] inputStringConvert(String input) {
		input = input.toLowerCase();
		input_array = input.split("\\s");
		return input_array;
	}

	private void deposit() {
		id = setId(1);
		double depositAmount = getDepositAmount();
		bank.depositById(id, depositAmount);
	}

	private void account_creation() {
		String accountType;
		double apr;

		accountType = input_array[1];
		id = setId(2);
		apr = setApr();

		switch (accountType) {
		case ACCOUNT_TYPE_CD:
			balance = Double.parseDouble(input_array[4]);
			bank.addCDAccount(id, apr, balance);
			break;
		case ACCOUNT_TYPE_SAVINGS:
			bank.addSavingAccount(id, apr, balance);
			break;
		case ACCOUNT_TYPE_CHECKING:
			bank.addCheckingAccount(id, apr, balance);
			break;
		default:
			return;
		}
	}

	private String setId(int x) {
		return input_array[x];
	}

	private double setApr() {
		return Double.parseDouble(input_array[3]);
	}

	private double getDepositAmount() {
		return Double.parseDouble(input_array[2]);
	}
}
