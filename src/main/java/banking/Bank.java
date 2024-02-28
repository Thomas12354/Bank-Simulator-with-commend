package banking;

import java.util.HashMap;
import java.util.Map;

public class Bank {
	private Map<String, Account> accounts;
	private int time = 0;

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

	public void withdrawById(String id, double depositAmount) {
		accounts.get(id).withdraw(depositAmount);
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
		passProcess();

	}

	private void passProcess() {
		for (String id : accounts.keySet()) {
			Account account = accounts.get(id);
			double balance = account.getBalance();
			account.setPassed();

			checkBalance(id, account, balance);

		}
	}

	private void checkBalance(String id, Account account, double balance) {
		if (balance == 0) {
			closeAccount(id);
		} else if (balance < 100) {
			deductBalance(id);
		} else {
			account.calculateApr();
		}
	}

	private void deductBalance(String id) {
		accounts.get(id).deductBalance();
	}

	private void closeAccount(String id) {
		accounts.remove(id);
	}

}
