package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import rmi.RemoteHelper;

public class LogInPanel {

	private MainFrame mainFrame;
	private JFrame frame;
	private JPanel logInPanel;
	private JTextField userNameField;
	private JPasswordField userPasswordField;
	private User user;
	
	private Font font=new Font("宋体",Font.BOLD,20);

	private void setComFont(Component c){
		c.setFont(font);
	}
	
	private void setFrame(JFrame frame){
		this.frame=frame;
	}
	private void setUser(User user){
		this.user=user;
	}
	public LogInPanel(MainFrame mainFrame){
	    this.mainFrame=mainFrame;
		setFrame(mainFrame.returnFrame());
		setUser(mainFrame.returnUser());
		setLogInPanel();
		
	}
	public JPanel returnPanel(){
		return logInPanel;
	}
	
	private void setLogInPanel(){
		/**
		 * 创建登录界面
		 */
		frame.setTitle("BF Client");
		frame.setLayout(new BorderLayout());

		logInPanel=new JPanel();
		
		logInPanel.setPreferredSize(new Dimension(900,900));
		logInPanel.setLayout(null);
		
		userNameField=new JTextField();
		JLabel userID=new JLabel();
		userID.setText("UserID");
		userPasswordField=new JPasswordField();
		JLabel userPassword=new JLabel();
		userPassword.setText("Password");
		userNameField.setBounds(500, 200, 300, 50);
		userID.setBounds(100,200,300,50);
		userPasswordField.setBounds(500, 400,300,50);
		userPassword.setBounds(100, 400,300,50);
		
		
		JButton loginButton=new JButton("LogIn");
		loginButton.setBounds(500,600,100,50);
		loginButton.setBackground(Color.WHITE);
		loginButton.addActionListener(new LoginActionListener());
		
		JButton registerButton=new JButton("Register");
		registerButton.setBounds(700,600,100,50);
		registerButton.setBackground(Color.WHITE);
		registerButton.addActionListener(new RegisterActionListener());
		
		setComFont(userPasswordField);
		setComFont(userPassword);
		setComFont(userID);
		setComFont(userNameField);
		
		logInPanel.add(userPasswordField);
		logInPanel.add(userPassword);
		logInPanel.add(userID);
		logInPanel.add(userNameField);
		logInPanel.add(loginButton);
		logInPanel.add(registerButton);
		
	}
	
	class LoginActionListener implements ActionListener{

		/**
		 * 登陆响应事件
		 */
	
		@Override
		public void actionPerformed(ActionEvent e) {
			
			String userName=userNameField.getText();
			String password=userPasswordField.getText();
			boolean LogCorrect=isLog(userName,password);
			
			if(LogCorrect){
				//可登录
				user.userID=userName;
				userNameField.setText("");
				userPasswordField.setText("");
				frame.remove(logInPanel);
				mainFrame.initializeMainFrame();
				frame.repaint();
				frame.setVisible(true);
			}else{
				//登陆失败
				JOptionPane.showMessageDialog(null, "用户名或密码错误","登陆失败",JOptionPane.WARNING_MESSAGE);
			}
		}
	}
	class RegisterActionListener implements ActionListener{
		/**
		 * 注册按钮响应事件
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			frame.remove(logInPanel);
			frame.add(mainFrame.returnSignInPanel());
			frame.repaint();
			frame.setVisible(true);
		}
		
	}
	

	private boolean isLog(String userName,String userPassword){
		boolean isLogin=false;
		try {
			isLogin=RemoteHelper.getInstance().getUserService().login(userName, userPassword);
		} catch (RemoteException e) {
			
			e.printStackTrace();
		}
		return isLogin;
	}
	
	
	
}
