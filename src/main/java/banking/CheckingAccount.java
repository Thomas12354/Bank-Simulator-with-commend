package banking;

public class CheckingAccount extends Account {

	public static final int MIN_DEPOSIT_AMOUNT = -1;
	public static final int MAX_DEPOSIT_AMOUNT = 1000;

	public static final int MIN_WITHDRAW_AMOUNT = -1;
	public static final int MAX_WITHDRAW_AMOUNT = 400;

	public CheckingAccount(double apr, double balance) {
		super(apr, balance);
	}

	@Override
	public boolean validDepositAmount(double amount) {
		return amount <= MAX_DEPOSIT_AMOUNT && amount > MIN_DEPOSIT_AMOUNT;
	}

	@Override
	public String getName() {
		return "checking";
	}

	@Override
	public void setPassed() {

	}

	@Override
	public boolean validWithdrawAmount(double amount) {
		return amount <= MAX_WITHDRAW_AMOUNT && amount > MIN_WITHDRAW_AMOUNT;
	}

}
