
public class Account {

	private enum AccountType {
		SAVINGS, CHECKING
	}
	
	private AccountType type;
	private int customerNumber = 0;
	private int customerPin = 0;
	private int balance = 0;

	public Account(AccountType type, int custNum, int custPin) {
		this.type = type;
		this.customerNumber = custNum;
		this.customerPin = custPin;
	}
	
	public double deposit(double amount) {
		this.balance += amount;
		return balance;
	}
	
	public double withdraw(double amount) {
		if(amount <= balance) {
			balance -= amount;
		}
		return balance;
	}
	
	public int getBalance() {
		return balance;
	}
}
