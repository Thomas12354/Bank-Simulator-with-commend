public class SavingAccount extends Account {
	public SavingAccount(double apr, double balance) {
		super(apr, balance);
	}

	@Override
	public boolean validDepositAmount(double amount) {
		return amount <= 2500 && amount > -1;
	}
}
