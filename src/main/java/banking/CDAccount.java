package banking;

public class CDAccount extends Account {

	private double passedtime;

	public CDAccount(double apr, double balance) {
		super(apr, balance);
	}

	@Override
	public void setPassed() {
		passedtime++;
	}

	@Override
	public boolean validDepositAmount(double amount) {
		return false;
	}

	@Override
	public boolean validWithdrawAmount(double amount) {
		if (checkWithdrawStatus()) {
			return balance >= amount;
		} else {
			return false;
		}
	}

	@Override
	public String getName() {
		return "cd";
	}

	@Override
	public void calculateApr() {
		double aprPercentage = apr / 100;
		aprPercentage /= 12;

		for (int i = 0; i < 4; i++) {
			double interest = balance * aprPercentage;
			balance += interest;
		}
	}

	private boolean checkWithdrawStatus() {
		return passedtime >= 12;
	}
}
