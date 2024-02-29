package banking;

public class CDAccount extends Account {

	private double pastime = 0;

	public CDAccount(double apr, double balance) {
		super(apr, balance);
	}

	@Override
	public void setPassed(int time) {
		pastime += time;
	}

	@Override
	public boolean validDepositAmount(double amount) {
		return false;
	}

	@Override
	public boolean validWithdrawAmount(double amount) {
		if (checkWithdrawStatus()) {
			return amount >= balance;
		} else {
			return false;
		}
	}

	@Override
	public String getName() {
		return "Cd";
	}

	@Override
	public void calculateApr() {
		double aprPercentage = apr / 100;
		aprPercentage /= 12;

		for (int i = 0; i < 4 * pastime; i++) {
			double interest = balance * aprPercentage;
			balance += interest;
		}
	}

	private boolean checkWithdrawStatus() {
		return pastime >= 12;
	}
}
