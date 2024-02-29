package banking;

public class CommandProcessor {
	private static final String ACCOUNT_TYPE_CD = "cd";
	private static final String ACCOUNT_TYPE_SAVINGS = "savings";
	private static final String ACCOUNT_TYPE_CHECKING = "checking";
	Bank bank;
	private String id;
	private double balance;
	private String[] inputArray;

	public CommandProcessor(Bank bank) {
		this.bank = bank;
	}

	public void processCommand(String input) {
		inputArray = inputStringConvert(input);
		String command = inputArray[0];
		switch (command) {
		case "create":
			account_creation();
			break;
		case "deposit":
			deposit();
			break;
		case "withdraw":
			withdraw();
			break;
		case "transfer":
			transfer();
			break;
		case "pass":
			setTime();
			break;
		default:
			break;
		}

	}

	private void transfer() {
		String senderId = setSenderId();
		String receiverId = setReceiverId();
		double transferAmount = getTransferAmount();
		bank.transferById(senderId, receiverId, transferAmount);
	}

	private double getTransferAmount() {
		return Double.parseDouble(inputArray[3]);
	}

	private void withdraw() {
		id = setId(1);
		double withdrawAmount = getWithdrawAmount();
		bank.withdrawById(id, withdrawAmount);
	}

	private String[] inputStringConvert(String input) {
		input = input.toLowerCase();
		inputArray = input.split("\\s");
		return inputArray;
	}

	private void deposit() {
		id = setId(1);
		double depositAmount = getDepositAmount();
		bank.depositById(id, depositAmount);
	}

	private void setTime() {
		int time = convertTime();
		bank.setTime(time);

	}

	private int convertTime() {
		return Integer.parseInt(inputArray[1]);
	}

	private void account_creation() {
		String accountType;
		double apr;

		accountType = inputArray[1];
		id = setId(2);
		apr = setApr();

		switch (accountType) {
		case ACCOUNT_TYPE_CD:
			balance = Double.parseDouble(inputArray[4]);
			bank.addCDAccount(id, apr, balance);
			break;
		case ACCOUNT_TYPE_SAVINGS:
			bank.addSavingAccount(id, apr, balance);
			break;
		case ACCOUNT_TYPE_CHECKING:
			bank.addCheckingAccount(id, apr, balance);
			break;
		default:
			break;
		}
	}

	private String setId(int x) {
		return inputArray[x];
	}

	private double setApr() {
		return Double.parseDouble(inputArray[3]);
	}

	private double getDepositAmount() {
		return Double.parseDouble(inputArray[2]);
	}

	private double getWithdrawAmount() {
		return Double.parseDouble(inputArray[2]);
	}

	private String setSenderId() {
		return inputArray[1];
	}

	private String setReceiverId() {
		return inputArray[2];
	}

}
