package banking;

public class TransferCommandValidator extends CommandValidator {
	public TransferCommandValidator(Bank bank) {
		super(bank);
	}

	@Override
	public boolean validate(String input) {

		double transferAmount;

		inputArray = inputProcess(input);

		if (isExtraSpace(inputArray) || !isEnoughInput(inputArray) || !isNumberString(inputArray[3])) {
			return false;
		}
		String senderId = setSenderId();
		String recevierId = setReeciverId();

		if (!isAccountExist(senderId) && !isAccountExist(recevierId)) {
			return false;
		}

		transferAmount = getTransferAmount(inputArray);

		boolean isIdValid = idStringCheck();

		boolean isBalanceValid = isValidTransferAmount(transferAmount);

		return isIdValid && isBalanceValid;

	}

	private String setSenderId() {
		return inputArray[1];
	}

	private String setReeciverId() {
		return inputArray[2];
	}

	private double getTransferAmount(String[] input_array) {
		return Double.parseDouble(input_array[3]);
	}

	private boolean isValidTransferAmount(double amount) {
		Account account = bank.getAccount().get(id);
		return account != null && account.validDepositAmount(amount);
	}
}
