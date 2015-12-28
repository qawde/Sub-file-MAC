import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Frame;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Font;

import javax.swing.SwingConstants;

public class Login extends JFrame
{

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	public static JTextField username;
	private JPasswordField password;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					Login frame = new Login();
					frame.setVisible(true);
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Login()
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(240, 248, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		username = new JTextField();
		username.setBounds(194, 79, 139, 20);
		contentPane.add(username);
		username.setColumns(10);

		password = new JPasswordField();
		password.setBounds(194, 123, 139, 20);
		contentPane.add(password);
		password.setColumns(10);

		setTitle("Login");

		try
		{
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels())
			{
				if ("Nimbus".equals(info.getName()))
				{
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}

		JButton btnNewButton = new JButton("Login");
		btnNewButton.setFont(new Font("FrankRuehl", Font.PLAIN, 13));
		btnNewButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				String userName = username.getText();
				String passWord = password.getText();
				if (userName.equals("admin") && passWord.equals("111111"))
				{
					int accessID = 0;
					ChooseFunction regFace = new ChooseFunction(accessID);
					regFace.setVisible(true);
					dispose();
				}

				else if (userName.equals("user") && passWord.equals("222222"))
				{
					int accessID = 1;
					ChooseFunction regFace = new ChooseFunction(accessID);
					JFrame frame = new JFrame("");
					regFace.setVisible(true);
					dispose();
				}

				else if (userName.equalsIgnoreCase("iamuser") && passWord.equalsIgnoreCase("333333"))
				{
					int accessID = 2;
					ChooseFunction regFace = new ChooseFunction(accessID);
					JFrame frame = new JFrame("");
					regFace.setVisible(true);
					dispose();
				} else if (userName.equalsIgnoreCase("thisisuser") && passWord.equalsIgnoreCase("444444"))
				{
					int accessID = 3;
					ChooseFunction regFace = new ChooseFunction(accessID);
					JFrame frame = new JFrame("");
					regFace.setVisible(true);
					dispose();
				} else if (userName.equalsIgnoreCase("hiuser") && passWord.equalsIgnoreCase("555555"))
				{
					int accessID = 4;
					ChooseFunction regFace = new ChooseFunction(accessID);
					JFrame frame = new JFrame("");
					regFace.setVisible(true);
					dispose();
				}

				else
				{
					JOptionPane.showMessageDialog(null, "Wrong Username/Password");
					username.setText("");
					password.setText("");
					username.requestFocus();
				}

			}
		});
		btnNewButton.setBounds(170, 175, 89, 23);
		contentPane.add(btnNewButton);

		JLabel lblUsername = new JLabel("Username:");
		lblUsername.setFont(new Font("FrankRuehl", Font.PLAIN, 13));
		lblUsername.setBounds(110, 82, 81, 14);
		contentPane.add(lblUsername);

		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setFont(new Font("FrankRuehl", Font.PLAIN, 13));
		lblPassword.setBounds(110, 126, 81, 14);
		contentPane.add(lblPassword);

		JLabel lblWelcomePleaseLog = new JLabel("Welcome, Please log in");
		lblWelcomePleaseLog.setHorizontalAlignment(SwingConstants.CENTER);
		lblWelcomePleaseLog.setFont(new Font("FrankRuehl", Font.PLAIN, 13));
		lblWelcomePleaseLog.setBounds(154, 28, 179, 14);
		contentPane.add(lblWelcomePleaseLog);

	}
}
