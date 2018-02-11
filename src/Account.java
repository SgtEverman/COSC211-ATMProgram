public class Account {

	public final static int SAVINGS = 0;
	public final static int CHECKING = 1;
	
	private int type;
	private int customerNumber = 0;
	private int customerPin = 0;
	private double balance = 0;

	public Account(int type, int custNum, int custPin) {
		this.type = type;
		this.customerNumber = custNum;
		this.customerPin = custPin;
	}
	
	public boolean isCorrectDetails(int num, int pin) {
		return (num == customerNumber && pin == customerPin);
	}
	
	public double deposit(double amount) {
		if(amount > 0)
			balance += amount;
		return balance;
	}
	
	public double withdraw(double amount) {
		if(amount > 0 && amount <= balance)
			balance -= amount;
		return balance;
	}
	
	public void setBalance(double value) {
		balance = value;
	}
	
	public double getBalance() {
		return balance;
	}
	
	public boolean isType(int type) {
		return (this.type == type);
	}
	
	public String getLabel() {
		return this.isType(CHECKING) ? "Checking" : "Savings";
	}
	
	@Override
	public String toString() {
		return "Customer Number: " + customerNumber + ", PIN: " + customerPin;
	}
}
