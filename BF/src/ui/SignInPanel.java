package ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class SignInPanel {

	private MainFrame mainFrame;
	private JPanel signInPanel;
	private JTextField userNameField1;
	private JTextField userPasswordField1;
	private JTextField confirmPasswordField;
	private User user;
	private JFrame frame;
	
	private Font font=new Font("宋体",Font.BOLD,20);
    private Font welFont=new Font("宋体",Font.BOLD,30);
	
    private void setWelFont(Component c){
    	c.setFont(welFont);
    }
	private void setComFont(Component c){
		c.setFont(font);
	}
	
	public JPanel returnPanel(){
		return signInPanel;
	}
	private void setUser(User user){
		this.user=user;
	}
	private void setFrame(JFrame frame){
		this.frame=frame;
	}
	public SignInPanel(MainFrame mainFrame){
		this.mainFrame=mainFrame;
		setUser(mainFrame.returnUser());
		setFrame(mainFrame.returnFrame());
		setSignInPanel();
	}
	private void setSignInPanel(){
		/**
    	 * 创建注册界面
    	 */
    	signInPanel=new JPanel();
    	signInPanel.setPreferredSize(new Dimension(900,900));
    	signInPanel.setLayout(null);
    	
    	JLabel signUpLabel=new JLabel();
    	signUpLabel.setText("WelCome");
    	setWelFont(signUpLabel);
    	signUpLabel.setBounds(400,100,250,100);
    	signInPanel.add(signUpLabel);
    	
    	JLabel userNameLabel=new JLabel();
    	userNameLabel.setText("User's name:");
    	userNameLabel.setBounds(100,300,300,50);
    	setComFont(userNameLabel);
    	signInPanel.add(userNameLabel);
    	
    	JLabel passwordLabel=new JLabel();
    	passwordLabel.setText("Password:");
    	setComFont(passwordLabel);
    	passwordLabel.setBounds(100,400,300,50);
    	signInPanel.add(passwordLabel);
    	
    	JLabel confirmPasswordLabel=new JLabel();
    	confirmPasswordLabel.setText("Comfirm password:");
    	confirmPasswordLabel.setBounds(100,500,300,50);
    	setComFont(confirmPasswordLabel);
    	signInPanel.add(confirmPasswordLabel);
    	
    	userNameField1=new JTextField();
    	userNameField1.setBounds(500, 300, 300, 50);
    	setComFont(userNameField1);
    	signInPanel.add(userNameField1);
    	
    	userPasswordField1=new JTextField();
    	userPasswordField1.setBounds(500, 400, 300, 50);
    	setComFont(userPasswordField1);
    	signInPanel.add(userPasswordField1);
    	
    	confirmPasswordField=new JTextField();
    	confirmPasswordField.setBounds(500,500,300,50);
    	setComFont(confirmPasswordField);
    	signInPanel.add(confirmPasswordField);
    	
    	JButton returnButton=new JButton("Return");
    	returnButton.setBounds(500,600,100,50);
    	returnButton.addActionListener(new returnActionListener());
    	returnButton.setBackground(Color.WHITE);
    	JButton signInButton=new JButton("Sign in");
    	signInButton.setBounds(700,600,100,50);
    	signInButton.addActionListener(new SignInButtonActionListener());
    	signInButton.setBackground(Color.WHITE);
    	signInPanel.add(returnButton);
    	signInPanel.add(signInButton);
    	
	}
	
	class SignInButtonActionListener implements ActionListener{
        /**
         * 注册按钮响应事件
         */
		@Override
		public void actionPerformed(ActionEvent e) {
			boolean isSignIn=false;
			SignIn signin=new SignIn();
			
			String userID=userNameField1.getText();
			String userPassword=userPasswordField1.getText();
			String userConfirmPassword=confirmPasswordField.getText();
			
			if(userPassword.equals(userConfirmPassword)){
				userNameField1.setText("");
				userPasswordField1.setText("");
				confirmPasswordField.setText("");
				signin.getUser(userID, userPassword);
				isSignIn=signin.register();
				if(isSignIn){
					user.userID=userID;
					frame.remove(signInPanel);
					mainFrame.initializeMainFrame();
					frame.repaint();
					frame.setVisible(true);
				}else{
					JOptionPane.showMessageDialog(null, "用户名已存在","注册失败",JOptionPane.WARNING_MESSAGE);
				}
			}else{
				JOptionPane.showMessageDialog(null, "请确认两次密码输入一致","注册失败",JOptionPane.WARNING_MESSAGE);
				userPasswordField1.setText("");
				confirmPasswordField.setText("");
			}
		}
	}
	class returnActionListener implements ActionListener{
		/**
		 * 返回按钮响应事件
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			frame.remove(signInPanel);
			frame.add(mainFrame.returnLogInPanel());
			frame.repaint();
			frame.setVisible(true);
			
		}
		
	}
}
