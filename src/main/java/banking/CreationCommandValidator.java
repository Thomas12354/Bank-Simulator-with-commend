package banking;

public class CreationCommandValidator extends CommandValidator {
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

		inputArray = inputProcess(input);

		if (!isEnoughInput(inputArray)) {
			return false;
		}

		accountType = defineAccountType();

		id = setId();
		apr = setApr();

		if (isCdAccount() && isNumberString(inputArray[4])) {
			balance = setBalance(inputArray);
		}

		if (isAccountExist(id)) {
			return false;
		}

		boolean isAccountTypeValid = isAccountTypeValid();

		boolean isIdValid = idStringCheck();

		boolean isAprValid = isAccountTypeValid && isAprValid();

		return isIdValid && isAprValid;

	}

	private String defineAccountType() {
		return inputArray[1];
	}

	private String setId() {
		return inputArray[2];
	}

	private double setApr() {
		return Double.parseDouble(inputArray[3]);
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
