public abstract class Account {

	private final double apr;
	private double balance;

	public Account(double apr, double balance) {
		this.apr = apr;

		this.balance = balance;
	}

	public double getAPR() {
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

}
