package banking;

public class WithdrawCommandValidator extends CommandValidator {
	public WithdrawCommandValidator(Bank bank) {
		super(bank);
	}

	@Override
	public boolean validate(String input) {

		double withdrawAmount;

		inputArray = inputProcess(input);

		if (!isEnoughInput(inputArray) || (!isNumberString(inputArray[2]) && !isDecimalNumberString(inputArray[2]))) {
			return false;
		}
		id = setId();

		if (!isAccountExist(id)) {
			return false;
		}

		withdrawAmount = getWithdrawAmount(inputArray);

		boolean isIdValid = idStringCheck();

		boolean isBalanceValid = isValidWithdrawAmount(withdrawAmount);

		return isIdValid && isBalanceValid;

	}

	private String setId() {
		return inputArray[1];
	}

	private double getWithdrawAmount(String[] input_array) {
		return Double.parseDouble(input_array[2]);
	}

	private boolean isValidWithdrawAmount(double amount) {
		Account account = bank.getAccount().get(id);

		return account.validWithdrawAmount(amount);
	}
}
