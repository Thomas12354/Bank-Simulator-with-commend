public class CreationCommandValidator extends CommandValidator {
	private static final String VALID_CREATE_COMMAND = "create";
	private static final double MIN_BALANCE_CD = 1000.0;
	private static final double MAX_BALANCE_CD = 10000.0;
	private static final String ACCOUNT_TYPE_CD = "cd";
	private static final String ACCOUNT_TYPE_SAVINGS = "savings";
	private static final String ACCOUNT_TYPE_CHECKING = "checking";
	private static final double MIN_APR = 0.0;
	private static final double MAX_APR = 10.0;
	private String accountType = "";
	private double balance;
	private double apr;

	public CreationCommandValidator(Bank bank) {
		super(bank);
	}

	@Override
	public boolean validate(String input) {

		input_array = inputProcess(input);

		if (isExtraSpace(input_array) || !isEnoughInput(input_array)) {
			return false;
		}

		accountType = defineAccountType();

		id = setId();
		apr = setApr();

		if (isCdAccount() && isNumberString(input_array[4])) {
			balance = setBalance(input_array);
		}

		if (isAccountExist()) {
			return false;
		}

		boolean isAccountTypeValid = isAccountTypeValid();

		boolean isCommandValid = isCommandValid(VALID_CREATE_COMMAND);
		boolean isIdValid = idStringCheck() && !isAccountExist();

		boolean isAprValid = isAccountTypeValid && isAprValid();

		return isCommandValid && isIdValid && isAprValid && isAccountTypeValid;

	}

	private String defineAccountType() {
		return input_array[1];
	}

	private String setId() {
		return input_array[2];
	}

	private double setApr() {
		return Double.parseDouble(input_array[3]);
	}

	private double setBalance(String[] input_array) {
		return Double.parseDouble(input_array[4]);
	}

	private boolean isCdAccount() {
		return accountType.equals(ACCOUNT_TYPE_CD);
	}

	public boolean isAprValid() {
		return apr >= MIN_APR && apr <= MAX_APR;
	}

	private boolean isAccountTypeValid() {
		return accountType.equals(ACCOUNT_TYPE_SAVINGS) || accountType.equals(ACCOUNT_TYPE_CHECKING)
				|| (isCdAccount() && validCreateBalanceForCd());
	}

	private boolean validCreateBalanceForCd() {
		return balance >= MIN_BALANCE_CD && balance <= MAX_BALANCE_CD;
	}

}
