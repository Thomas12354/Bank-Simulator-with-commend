package banking;

public class CheckingAccount extends Account {

	public static final int MAX_DEPOSIT_AMOUNT = 1000;

	public static final int MAX_WITHDRAW_AMOUNT = 400;

	public CheckingAccount(double apr, double balance) {
		super(apr, balance);
	}

	@Override
	public boolean validDepositAmount(double amount) {
		return amount <= MAX_DEPOSIT_AMOUNT;
	}

	@Override
	public String getName() {
		return "Checking";
	}

	@Override
	public void setPassed(int time) {

	}

	@Override
	public boolean validWithdrawAmount(double amount) {
		return amount <= MAX_WITHDRAW_AMOUNT;
	}

}
