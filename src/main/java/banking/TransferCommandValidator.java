package banking;

public class TransferCommandValidator extends CommandValidator {
	String senderId;
	String receiverId;

	public TransferCommandValidator(Bank bank) {
		super(bank);
	}

	@Override
	public boolean validate(String input) {

		double transferAmount;

		inputArray = inputProcess(input);

		if (!isEnoughInput(inputArray) || !isNumberString(inputArray[3])) {
			return false;
		}
		senderId = setSenderId();
		receiverId = setReceiverId();
		if (senderId.equals(receiverId)) {
			return false;
		}

		if (!isAccountExist(senderId) || !isAccountExist(receiverId)) {
			return false;
		}

		transferAmount = getTransferAmount(inputArray);

		boolean isBalanceValid = isValidTransferAmount(transferAmount);

		return isBalanceValid;

	}

	private String setSenderId() {
		return inputArray[1];
	}

	private String setReceiverId() {
		return inputArray[2];
	}

	private double getTransferAmount(String[] input_array) {
		return Double.parseDouble(input_array[3]);
	}

	private boolean isValidTransferAmount(double amount) {
		Account senderAccount = bank.getAccount().get(senderId);
		Account receiverAccount = bank.getAccount().get(receiverId);
		if (isCd(senderAccount, receiverAccount)) {
			return false;
		}
		return senderAccount.validWithdrawAmount(amount) && receiverAccount.validDepositAmount(amount);
	}

	private boolean isCd(Account senderAccount, Account receiverAccount) {
		return senderAccount.getName().equals("Cd") || receiverAccount.getName().equals("Cd");
	}
}
