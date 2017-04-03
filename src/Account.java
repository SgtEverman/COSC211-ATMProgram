
public class Account {

	private final int SAVINGS = 0;
	private final int CHECKING = 0;
	
	private int type;
	private int customerNumber = 0;
	private int customerPin = 0;
	private int balance = 0;

	public Account(int type, int custNum, int custPin) {
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
