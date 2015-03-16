import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;


public class LoginInterface extends JFrame {
	private final JLabel lblUsername = new JLabel("Username");
	private final JTextField textField = new JTextField();
	private final JPasswordField passwordField = new JPasswordField();
	private final JLabel lblPassword = new JLabel("Password");
	private final JLabel lblPleaseConfirmThe = new JLabel("Please confirm the password");
	private final JPasswordField passwordField_1 = new JPasswordField();
	private final JButton btnLoginNow = new JButton("Login Now");
	private final JLabel lblNewLabel = new JLabel("");
	private final JLabel lblNoAccountRegister = new JLabel("No account? Register Now!");
	private final JLabel lblForgetYourPassword = new JLabel("Forget your password? Fine Now.");
	public LoginInterface() {
		textField.setBounds(249, 87, 172, 33);
		textField.setColumns(10);
		initGUI();
	}
	private void initGUI() {
		getContentPane().setLayout(null);
		lblUsername.setHorizontalAlignment(SwingConstants.CENTER);
		lblUsername.setBounds(107, 87, 127, 33);
		
		getContentPane().add(lblUsername);
		
		getContentPane().add(textField);
		passwordField.setBounds(249, 155, 172, 33);
		
		getContentPane().add(passwordField);
		lblPassword.setHorizontalAlignment(SwingConstants.CENTER);
		lblPassword.setBounds(107, 156, 127, 32);
		
		getContentPane().add(lblPassword);
		lblPleaseConfirmThe.setHorizontalAlignment(SwingConstants.CENTER);
		lblPleaseConfirmThe.setBounds(32, 217, 202, 33);
		
		getContentPane().add(lblPleaseConfirmThe);
		passwordField_1.setBounds(249, 217, 172, 33);
		
		getContentPane().add(passwordField_1);
		btnLoginNow.setBounds(249, 280, 172, 41);
		
		getContentPane().add(btnLoginNow);
		lblNewLabel.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel.setBounds(454, 93, 160, 157);
		
		getContentPane().add(lblNewLabel);
		lblNoAccountRegister.setBounds(77, 340, 218, 41);
		
		getContentPane().add(lblNoAccountRegister);
		lblForgetYourPassword.setHorizontalAlignment(SwingConstants.LEFT);
		lblForgetYourPassword.setBounds(326, 338, 238, 44);
		
		getContentPane().add(lblForgetYourPassword);
	}
}
