import java.io.File;
import java.util.Scanner;

public class ATM {

	public static final String FILE_NAME = "customers.txt";
	public static final String custNumText = "Enter customer number: → \npress A when done\nA = OK";
	public static final String pinText = "Enter customer PIN: → \nA = OK";
	public static final String selectAcctText = "Select Account: → \nA = Checking\nB = Savings\nC = Exit\n\nCustomer #: %d";
	public static final String displayAcctText = "%s Balance = $%.2f\nEnter amount and select transaction: →\n" +
												 "A = Withdraw\nB = Deposit\nC = Cancel";

	private Account savings = null;
	private Account checking = null;
	private File file = null;
	private Scanner scanner = null;
	
	public ATM() {
		file = new File(FILE_NAME);
	}
	
	private void setupFileRead() {
		try {
			scanner = new Scanner(file);
		} catch (Exception e) {
			System.out.println("Data file not found.");
			System.exit(0);
		}
	}
	
	public Account getSavingsInstance() {
		return savings;
	}

	public Account getCheckingInstance() {
		return checking;
	}
	
	public boolean verifyCredentials(int inputCustNum, int inputPin) {
		setupFileRead();
		while(scanner.hasNext()) {
			String[] parts = scanner.nextLine().split(",");
			int num = Integer.parseInt(parts[0]);
			int pin = Integer.parseInt(parts[1]);
			// check if this line matches the info the user entered
			if(inputCustNum == num && inputPin == pin) {
				this.checking = new Account(Account.CHECKING, num, pin);
				this.savings = new Account(Account.SAVINGS, num, pin);
				return true;
			}
		}
		return false;
	}
	
}
