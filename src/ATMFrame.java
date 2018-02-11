import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ATMFrame extends JFrame {
	
	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
		new ATMFrame();
	}
	
	private final Dimension WINDOW_SIZE = new Dimension(570, 220),
							LEFT_BTN_SIZE = new Dimension(40, 40),
							RIGHT_BTN_SIZE = new Dimension(55, 60);
	
	private final int STEP_ENTER_CUST_NUM = 0,
					  STEP_ENTER_PIN = 1,
					  STEP_SELECT_ACCOUNT = 2,
					  STEP_DISPLAY_ACCOUNT = 3;
	
	private String[] buttonLabels = { "7", "8", "9", "4", "5", "6", "1", "2", "3", "0", ".", "CE" };
	
	private JPanel mainPanel;
	
	private JPanel leftPanel;
	private JPanel leftBtnPanel;
	private JPanel centerPanel;
	private JPanel rightPanel;
	
	private JTextField field;
	private JScrollPane displayScrollPane;
	private JTextArea display;
	private JButton btnA;
	private JButton btnB;
	private JButton btnC;
	
	private ATM atm;
	private Account selectedAccount;
	
	private int inputCustNum = 0;
	private int inputPin = 0;
	private int step;
	
	public ATMFrame() {
		super();
		
		atm = new ATM();
		
		mainPanel = new JPanel();
		leftPanel = new JPanel();
		leftBtnPanel = new JPanel(new GridLayout(4,3));
		centerPanel = new JPanel();
		rightPanel = new JPanel();
		
		field = new JTextField();
		display = new JTextArea(11,30);
		display.setWrapStyleWord(true);
		
		displayScrollPane = new JScrollPane(display, 
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		displayScrollPane.setBorder(BorderFactory.createEmptyBorder());
		
		// setup the A, B, C buttons
		setupABCButtons();
		
		// add the buttons to the left panel
		createInputButtons();
		
		leftPanel.setLayout(new BorderLayout());
		leftPanel.add(field, BorderLayout.NORTH);
		leftPanel.add(leftBtnPanel, BorderLayout.SOUTH);
		
		centerPanel.add(displayScrollPane);
		
		rightPanel.setLayout(new GridLayout(3,1));
		rightPanel.add(btnA);
		rightPanel.add(btnB);
		rightPanel.add(btnC);
		
		mainPanel.add(leftPanel, BorderLayout.WEST);
		mainPanel.add(centerPanel, BorderLayout.CENTER);
		mainPanel.add(rightPanel, BorderLayout.EAST);
		
		this.add(mainPanel);
		
		this.setTitle("ATM");
		this.setSize(WINDOW_SIZE);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		
		step1EnterCustNumber();
	}
	
	private void setupABCButtons() {
		btnA = new JButton("A");
		btnB = new JButton("B");
		btnC = new JButton("C");
		
		btnA.setPreferredSize(RIGHT_BTN_SIZE);
		btnB.setPreferredSize(RIGHT_BTN_SIZE);
		btnC.setPreferredSize(RIGHT_BTN_SIZE);
		
		btnA.addActionListener(new ABCButtonListener());
		btnB.addActionListener(new ABCButtonListener());
		btnC.addActionListener(new ABCButtonListener());
	}
	
	private void createInputButtons() {
		for(String label : buttonLabels) {
			JButton btn = new JButton(label);
			btn.setPreferredSize(LEFT_BTN_SIZE);
			btn.addActionListener(new InputButtonListener());
			leftBtnPanel.add(btn);
		}
	}
	
	private class InputButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String input = e.getActionCommand();
			if(input == "CE") {
				clearField();
				return;
			}
			field.setText(field.getText() + input);
		}
	}
	
	private class ABCButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String command = e.getActionCommand();
			switch(command) {
			case "A":
				buttonAHandler();
				break;
			case "B":
				buttonBHandler();
				break;
			case "C":
				buttonCHandler();
				break;
			}
			field.grabFocus();
		}
		
	}
	
	private void step1EnterCustNumber() {
		display.setText(ATM.custNumText);
		clearField();
		step = STEP_ENTER_CUST_NUM;
	}
	
	private void step2EnterPinNumber() {
		display.setText(ATM.pinText);
		clearField();
		step = STEP_ENTER_PIN;
	}
	
	private void step3SelectAccount() {
		display.setText(String.format(ATM.selectAcctText, inputCustNum));
		clearField();
		step = STEP_SELECT_ACCOUNT;
	}
	
	private void displayAccount() {
		display.setText(String.format(ATM.displayAcctText, selectedAccount.getLabel(), selectedAccount.getBalance()));
		clearField();
		step = STEP_DISPLAY_ACCOUNT;
	}
	
	private void buttonAHandler() {
		switch(step) {
		case STEP_ENTER_CUST_NUM: // submit customer number
			inputCustNum = (int)getInput();
			if(inputCustNum == 0) {
				step1EnterCustNumber();
				return;
			}
			step2EnterPinNumber();
			break;
		case STEP_ENTER_PIN: // submit pin
			inputPin = (int)getInput();
			if(inputPin == 0) {
				step2EnterPinNumber();
				return;
			}
			
			if(atm.verifyCredentials(inputCustNum, inputPin)) {
				// continue to step 3
				step3SelectAccount();
			}
			else {
				// go back to step 1
				step1EnterCustNumber();
				JOptionPane.showMessageDialog(mainPanel, 
						"Incorrect customer number/PIN combination.\nPlease try again.", 
						"Unable to Log In", 
						JOptionPane.ERROR_MESSAGE);
			}
			
			break;
		case STEP_SELECT_ACCOUNT:
			selectedAccount = atm.getCheckingInstance();
			displayAccount();
			break;
		case STEP_DISPLAY_ACCOUNT: 
			selectedAccount.withdraw(getInput());
			displayAccount(); // refresh account display
			break;
			
		}
	}
	
	private void buttonBHandler() {
		switch(step) {
		case STEP_SELECT_ACCOUNT:
			selectedAccount = atm.getSavingsInstance();
			displayAccount();
			break;
		case STEP_DISPLAY_ACCOUNT:
			selectedAccount.deposit(getInput());
			displayAccount(); // refresh account display
			break;
		}
	}
	
	private void buttonCHandler() {
		switch(step) {
		case STEP_SELECT_ACCOUNT:
			step1EnterCustNumber();
			break;
		case STEP_DISPLAY_ACCOUNT:
			step3SelectAccount();
			break;
		}
	}
	
	private double getInput() {
		if(field.getText().isEmpty()) 
			return 0;
		return Double.parseDouble(field.getText());
	}
	
	private void clearField() {
		field.setText("");
	}

}