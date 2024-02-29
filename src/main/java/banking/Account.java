package banking;

public abstract class Account {

	protected final double apr;
	protected double balance;

	protected Account(double apr, double balance) {
		this.apr = apr;

		this.balance = balance;
	}

	public double getApr() {
		return apr;
	}

	public void deposit(double amount) {
		balance += amount;
	}

	public void withdraw(double amount) {
		if (amount > balance) {
			balance = 0;
		} else {
			balance -= amount;
		}
	}

	public void deductBalance() {
		balance -= 25;
	}

	public double getBalance() {
		return balance;
	}

	public abstract boolean validDepositAmount(double amount);

	public abstract boolean validWithdrawAmount(double amount);

	public abstract String getName();

	public void calculateApr() {
		double aprPercentage = apr / 100;
		aprPercentage /= 12;
		double interest = balance * aprPercentage;
		balance += interest;
	}

	public abstract void setPassed(int time);

}
