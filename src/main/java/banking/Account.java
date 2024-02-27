package banking;

public abstract class Account {

	private final double apr;
	private double balance;

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

	public double getBalance() {
		return balance;
	}

	public abstract boolean validDepositAmount(double amount);

	public abstract String getName();

}
