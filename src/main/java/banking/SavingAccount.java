package banking;

public class SavingAccount extends Account {

	public static final int MAX_DEPOSIT_AMOUNT = 2500;

	public static final int MAX_WITHDRAW_AMOUNT = 1000;

	private boolean isPassed = true;

	public SavingAccount(double apr, double balance) {
		super(apr, balance);
	}

	@Override
	public boolean validDepositAmount(double amount) {
		return amount <= MAX_DEPOSIT_AMOUNT;
	}

	@Override
	public String getName() {
		return "Savings";
	}

	@Override
	public boolean validWithdrawAmount(double amount) {
		if (checkWithdrawStatus()) {
			return amount <= MAX_WITHDRAW_AMOUNT;
		} else {
			return false;
		}
	}

	private boolean checkWithdrawStatus() {
		return isPassed;
	}

	@Override
	public void withdraw(double amount) {
		isPassed = false;
		if (amount > balance) {
			balance = 0;
		} else {
			balance -= amount;
		}
	}

	@Override
	public void setPassed(int time) {
		isPassed = true;
	}

}
