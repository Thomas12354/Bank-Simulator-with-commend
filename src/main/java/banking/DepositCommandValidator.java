package banking;

public class DepositCommandValidator extends CommandValidator {

	public DepositCommandValidator(Bank bank) {
		super(bank);
	}

	@Override
	public boolean validate(String input) {

		double depositAmount;

		input_array = inputProcess(input);

		if (isExtraSpace(input_array) || !isEnoughInput(input_array) || !isNumberString(input_array[2])) {
			return false;
		}
		id = setId();

		if (!isAccountExist()) {
			return false;
		}

		depositAmount = getDepositAmount(input_array);

		boolean isIdValid = idStringCheck();

		boolean isBalanceValid = isValidDepositAmount(depositAmount);

		return isIdValid && isBalanceValid && isAccountExist();

	}

	private String setId() {
		return input_array[1];
	}

	private double getDepositAmount(String[] input_array) {
		return Double.parseDouble(input_array[2]);
	}

	private boolean isValidDepositAmount(double amount) {

		Account account = bank.getAccount().get(id);
		return account != null && account.validDepositAmount(amount);
	}

}
