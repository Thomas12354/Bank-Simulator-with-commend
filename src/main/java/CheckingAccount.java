public class CheckingAccount extends Account {
	public CheckingAccount(double apr, double balance) {
		super(apr, balance);
	}

	@Override
	public boolean validDepositAmount(double amount) {
		return amount <= 1000 && amount > -1;
	}
}
