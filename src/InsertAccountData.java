import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

public class InsertAccountData {
	private static FileOutputStream fos;
	private static ObjectOutputStream oos;
	private static Account[] accounts = new Account[4];
	
	public static void main(String[] args) {
		
		// Dummy Data
		accounts[0] = new Account(Account.SAVINGS, 123456, 1111);
		accounts[1] = new Account(Account.CHECKING, 123456, 1111);
		accounts[2] = new Account(Account.SAVINGS, 654321, 2222);
		accounts[3] = new Account(Account.CHECKING, 654321, 2222);
		
		try {
			fos = new FileOutputStream(ATM.FILE_NAME);
			oos = new ObjectOutputStream(fos);
			oos.writeObject(accounts);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
