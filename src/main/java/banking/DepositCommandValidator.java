package banking;

public class DepositCommandValidator extends CommandValidator {

	public DepositCommandValidator(Bank bank) {
		super(bank);
	}

	@Override
	public boolean validate(String input) {

		double depositAmount;

		inputArray = inputProcess(input);

		if (isExtraSpace(inputArray) || !isEnoughInput(inputArray) || !isNumberString(inputArray[2])) {
			return false;
		}
		id = setId();

		if (!isAccountExist(id)) {
			return false;
		}

		depositAmount = getDepositAmount(inputArray);

		boolean isIdValid = idStringCheck();

		boolean isBalanceValid = isValidDepositAmount(depositAmount);

		return isIdValid && isBalanceValid;

	}

	private String setId() {
		return inputArray[1];
	}

	private double getDepositAmount(String[] input_array) {
		return Double.parseDouble(input_array[2]);
	}

	private boolean isValidDepositAmount(double amount) {

		Account account = bank.getAccount().get(id);
		return account.validDepositAmount(amount);
	}

}
