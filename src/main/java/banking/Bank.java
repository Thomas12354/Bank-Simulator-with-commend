package banking;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Bank {
	private final Map<String, Account> accounts;
	private final ArrayList<String> removeList;
	private int time = 0;

	Bank() {
		accounts = new HashMap<>();
		removeList = new ArrayList<>();
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
			account.setPassed(time);

			checkBalance(id, account, balance);
		}
		closeAccount();
	}

	private void checkBalance(String id, Account account, double balance) {
		if (balance == 0) {
			saveCloseAccountId(id);
		} else if (balance < 100) {
			deductBalance(id);
		} else {
			account.calculateApr();
		}
	}

	private void deductBalance(String id) {
		accounts.get(id).deductBalance();
	}

	private void saveCloseAccountId(String id) {
		removeList.add(id);
	}

	private void closeAccount() {
		for (String id : removeList) {
			accounts.remove(id);
		}
	}

	public void transferById(String senderId, String receiverId, double transferAmount) {
		Account sender = accounts.get(senderId);
		Account receiver = accounts.get(receiverId);

		if (transferAmount > sender.getBalance()) {
			transferAmount = sender.getBalance();
		}
		sender.withdraw(transferAmount);
		receiver.deposit(transferAmount);

	}
}
