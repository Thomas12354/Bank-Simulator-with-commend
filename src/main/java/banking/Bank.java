package banking;

import java.util.HashMap;
import java.util.Map;

public class Bank {
	private Map<String, Account> accounts;

	Bank() {
		accounts = new HashMap<>();
	}

	public Map<String, Account> getAccount() {
		return accounts;
	}

	public void addCheckingAccount(String id, double apr, double balance) {
		accounts.put(id, new CheckingAccount(apr, balance));
	}

	public void addSavingAccount(String id, double apr, double balance) {
		accounts.put(id, new SavingAccount(apr, balance));
	}

	public void addCDAccount(String id, double apr, double balance) {
		accounts.put(id, new CDAccount(apr, balance));
	}

	public boolean isAccountExist(String id) {
		return accounts.containsKey(id);
	}

	public void depositById(String id, double depositAmount) {
		accounts.get(id).deposit(depositAmount);
	}
}
