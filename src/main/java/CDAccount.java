public class CDAccount extends Account {

	public CDAccount(double apr, double balance) {
		super(apr, balance);
	}

	@Override
	public boolean validDepositAmount(double amount) {
		return false;
	}

	@Override
	public String getName() {
		return "cd";
	}
}
