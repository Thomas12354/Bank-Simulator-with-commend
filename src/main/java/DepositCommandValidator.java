public class DepositCommandValidator extends CommandValidator {
	public static final String VALID_DEPOSIT_COMMAND = "deposit";

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

		boolean isCommandValid = isCommandValid(VALID_DEPOSIT_COMMAND);
		boolean isIdValid = idStringCheck();

		boolean isBalanceValid = isValidDepositAmount(depositAmount);

		return isCommandValid && isIdValid && isBalanceValid && isAccountExist();

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
