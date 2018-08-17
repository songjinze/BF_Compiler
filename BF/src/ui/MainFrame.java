package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import rmi.RemoteHelper;


public class MainFrame {
	
	private MainFrame mainFrame;
	private JTextArea textArea;
	private JTextArea textAreaIO;

	private LogInPanel logInPanel;      //登录界面
	private SignInPanel signInPanel;    //注册界面
	
	private JLabel resultLabel;
	private JPanel textPanel;
	private JPanel codePanel;
	private JPanel IOPanel;
	
	private JFrame frame;

	private JMenuBar menuBar;
	private User user=new User();
	
	private Font font=new Font("宋体",Font.BOLD,20);

	public boolean isUndo;
	public boolean isRedo;
	public boolean isEditing;
	
	
	private void setComFont(Component c){
		c.setFont(font);
	}
	
	public String returnWord(){
		String result=textArea.getText();
		return result;
	}
	public JPanel returnSignInPanel(){
		return signInPanel.returnPanel();
	}
	public JPanel returnLogInPanel(){
		return logInPanel.returnPanel();
	}
	public User returnUser(){
		return user;
	}
	public JFrame returnFrame(){
		return frame;
	}
	public void setTextArea(String text){
		textArea.setText(text);
	}
	
	private void InitializeAll(){
		mainFrame=this;          //仅创建一次，其他引用均为该对象
		frame=new JFrame();
		logInPanel=new LogInPanel(mainFrame);
		signInPanel=new SignInPanel(mainFrame);
	}
	
	public MainFrame() {
		InitializeAll();
		frame.getContentPane().add(logInPanel.returnPanel(),BorderLayout.CENTER);
		frame.setBackground(Color.LIGHT_GRAY);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setSize(900, 900);
		frame.setLocation(400, 200);
		frame.setVisible(true);
	}
	public void initializeMainFrame(){

		/**
		 * 加载主界面
		 */
		isEditing=true;
		menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		JMenu RunMenu=new JMenu("Run");
		JMenu HistoryMenu=new JMenu("History");
		JMenu Log=new JMenu("Log");
		JMenu Edit=new JMenu("Edit");
		
		setComFont(fileMenu);
		setComFont(RunMenu);
		setComFont(HistoryMenu);
		setComFont(Log);
		setComFont(Edit);
		
		menuBar.add(fileMenu);
		menuBar.add(RunMenu);
		menuBar.add(HistoryMenu);
		menuBar.add(Log);
		menuBar.add(Edit);
		
		JMenuItem newMenuItem = new JMenuItem("New");
		setComFont(newMenuItem);
		fileMenu.add(newMenuItem);
		JMenuItem openMenuItem = new JMenuItem("Open");
		fileMenu.add(openMenuItem);
		setComFont(openMenuItem);
		JMenuItem openListMenuItem=new JMenuItem("OpenFileList");
		fileMenu.add(openListMenuItem);
		setComFont(openListMenuItem);
		JMenu saveMenu = new JMenu("Save");
		setComFont(saveMenu);
		fileMenu.add(saveMenu);
		
		JMenuItem saveBFMenuItem=new JMenuItem("BF");
		JMenuItem saveOokMenuItem=new JMenuItem("Ook");
		setComFont(saveBFMenuItem);
		setComFont(saveOokMenuItem);
		saveMenu.add(saveBFMenuItem);
		saveMenu.add(saveOokMenuItem);
		
		JMenuItem runMenuItem = new JMenuItem("Execute");
		setComFont(runMenuItem);
		RunMenu.add(runMenuItem);
		frame.setJMenuBar(menuBar);

		JMenuItem history1=new JMenuItem("Version1");
		JMenuItem history2=new JMenuItem("Version2");
		JMenuItem history3=new JMenuItem("Version3");
		JMenuItem history4=new JMenuItem("Version4");
		JMenuItem history5=new JMenuItem("Version5");
		setComFont(history1);
		setComFont(history2);
		setComFont(history3);
		setComFont(history4);
		setComFont(history5);
		history1.addActionListener(new HistoryActionListener());
		history2.addActionListener(new HistoryActionListener());
		history3.addActionListener(new HistoryActionListener());
		history4.addActionListener(new HistoryActionListener());
		history5.addActionListener(new HistoryActionListener());
		HistoryMenu.add(history5);
		HistoryMenu.add(history4);
		HistoryMenu.add(history3);
		HistoryMenu.add(history2);
		HistoryMenu.add(history1);
		
		JMenuItem LogOut=new JMenuItem("LogOut");
		setComFont(LogOut);
		Log.add(LogOut);
		
		newMenuItem.addActionListener(new NewActionListener());
		openMenuItem.addActionListener(new OpenActionListener());
		openListMenuItem.addActionListener(new OpenListActionListener());
		saveBFMenuItem.addActionListener(new SaveActionListener());
		saveOokMenuItem.addActionListener(new SaveActionListener());
		runMenuItem.addActionListener(new RunActionListener());
		
		LogOut.addActionListener(new LogOutActionListener());
		

		JMenuItem undoMenuItem=new JMenuItem("Undo");
		JMenuItem redoMenuItem=new JMenuItem("Redo");
		setComFont(undoMenuItem);
		setComFont(redoMenuItem);
		Edit.add(undoMenuItem);
		Edit.add(redoMenuItem);
		
		undoMenuItem.addActionListener(new UndoActionListener());
		redoMenuItem.addActionListener(new RedoActionListener());
		
		textPanel=new JPanel();
		textPanel.setLayout(new FlowLayout());
		textPanel.setPreferredSize(new Dimension(900,200));
		
		codePanel=new JPanel();
		codePanel.setPreferredSize(new Dimension(830,500));
		
		IOPanel=new JPanel();
		IOPanel.setPreferredSize(new Dimension(830,200));
		
		textArea = new JTextArea();
		textAreaIO=new JTextArea();
		textArea.setMargin(new Insets(10, 10, 10, 10));
		textAreaIO.setMargin(new Insets(10,10,10,10));
		textArea.setBackground(Color.WHITE);
		textAreaIO.setBackground(Color.WHITE);
		setComFont(textArea);
		setComFont(textAreaIO);
		textArea.setPreferredSize(new Dimension(840,700));
		textAreaIO.setPreferredSize(new Dimension(840,200));
		
		codePanel.add(textArea);
		IOPanel.add(textAreaIO);
		
		frame.getContentPane().add(codePanel, BorderLayout.NORTH);
        frame.getContentPane().add(IOPanel, BorderLayout.CENTER);
		// 显示结果
		resultLabel = new JLabel();
		resultLabel.setPreferredSize(new Dimension(840,200));
		resultLabel.setBackground(Color.WHITE);
		resultLabel.setText("result");
		setComFont(resultLabel);
		
		textPanel.add(resultLabel);
		frame.getContentPane().add(textPanel, BorderLayout.SOUTH);
		
		Thread t=new Thread(new Undo(mainFrame));
		t.start();
	}
	
	
	class HistoryActionListener implements ActionListener{

		/**
		 * 历史版本响应事件
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO 自动生成的方法存根
			isEditing=false;
			String cmd=e.getActionCommand();
			int version=0;
			String code=null;
			switch(cmd){
			case "Version1":version=1;break;
			case "Version2":version=2;break;
			case "Version3":version=3;break;
			case "Version4":version=4;break;
			case "Version5":version=5;break;
			}
			try {
				code=RemoteHelper.getInstance().getIOService().readHistoryFile(user.userID,user.filename+"."+user.language,version);
			} catch (RemoteException e1) {
				// TODO 自动生成的 catch 块
				e1.printStackTrace();
			}
			if(code==null){
				JOptionPane.showMessageDialog(frame, "没有更早的版本");
			}else{
			    textArea.setText(code);
			}
			isEditing=true;
			Thread t=new Thread(new Undo(mainFrame));
			t.start();
		}
		
	}
	class RunActionListener implements ActionListener {
		/**
		 * 运行代码按钮响应事件
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			
				String code=textArea.getText();
				String param=textAreaIO.getText();
				String result="result";
				try {
					result = RemoteHelper.getInstance().getIOService().RunCode(code, param);
				} catch (RemoteException e1) {
				
					e1.printStackTrace();
				}
				resultLabel.setText(result);
			
		}
	}

	class NewActionListener implements ActionListener{

		/**
		 * 新建文件响应事件
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO 自动生成的方法存根
			isEditing=false;
			if(user.filename!=null){
			int choose=JOptionPane.showConfirmDialog(frame, "Save or not?","是否保存？",JOptionPane.YES_NO_OPTION);
			if(choose==1){
				String code = textArea.getText();
				
				try {
					RemoteHelper.getInstance().getIOService().writeFile(code, user.userID, user.filename,user.language);
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}
				user.filename=null;
				user.language=null;
				isEditing=true;
				Thread t=new Thread(new Undo(mainFrame));
				t.start();
			}else{
				user.filename=null;
				user.language=null;
				textArea.setText("");
				textAreaIO.setText("");
				resultLabel.setText("Result");
				isEditing=true;
				Thread t=new Thread(new Undo(mainFrame));
				t.start();
			}
			}
		}
		
	}
	
	class OpenActionListener implements ActionListener{

		/**
		 * 打开文件响应事件
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			isEditing=false;
			String filename=(String)JOptionPane.showInputDialog(frame,"Please input your file name:");
			if(filename!=null){
			String code=null;
			try {
				code=RemoteHelper.getInstance().getIOService().readFile(user.userID,filename);
			} catch (RemoteException e1) {
				// TODO 自动生成的 catch 块
				e1.printStackTrace();
			}
			user.filename=filename.split("\\.")[0];
			user.language=filename.split("\\.")[1];
			textArea.setText(code);
			textAreaIO.setText("");
			resultLabel.setText("Result");
			frame.repaint();
			frame.setVisible(true);
			System.out.println(user.filename+"   "+user.language);
			isEditing=true;
			Thread t=new Thread(new Undo(mainFrame));
			t.start();
		}
	}
	}
	
	class OpenListActionListener implements ActionListener{

		/**
		 * 打开文件列表响应事件
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			
			String[] fileList=null;
			try {
				fileList=RemoteHelper.getInstance().getIOService().readFileList(user.userID);
			} catch (RemoteException ex) {
				// TODO 自动生成的 catch 块
				ex.printStackTrace();
			}
			if(fileList!=null){
				isEditing=false;
			String filename=(String) JOptionPane.showInputDialog(frame,"Choose File","File",JOptionPane.INFORMATION_MESSAGE,null,fileList,fileList[0]);
			String code=null;
			if(filename!=null){
				
			try {
				code=RemoteHelper.getInstance().getIOService().readFile(user.userID,filename);
			} catch (RemoteException e1) {
				// TODO 自动生成的 catch 块
				e1.printStackTrace();
			}
			user.filename=filename.split("\\.")[0];
			user.language=filename.split("\\.")[1];
			textArea.setText(code);
			textAreaIO.setText("");
			resultLabel.setText("Result");
			frame.repaint();
			frame.setVisible(true);
			System.out.println(user.filename+"   "+user.language);
			isEditing=true;
			Thread t=new Thread(new Undo(mainFrame));
			t.start();
		}
			}else{
				JOptionPane.showMessageDialog(frame, "没有文件");
			}
		}
	}
	class SaveActionListener implements ActionListener {

		/**
		 * 保存文件响应事件
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			String code = textArea.getText();
			String language=e.getActionCommand();
			String name;
			if(user.filename==null){
				name=JOptionPane.showInputDialog(frame,"Input a name");
				user.filename=user.userID+"_"+name;
			}else{
				name=user.filename.split("_")[1];
			}
			try {
				RemoteHelper.getInstance().getIOService().writeFile(code, user.userID, name,language);
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}
			user.language=language;
		}
	}
	
	class LogOutActionListener implements ActionListener{

		/**
		 * 重新登陆
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			isEditing=false;
			user.userID=null;
			user.filename=null;
			user.language=null;
			isEditing=false;
			frame.remove(textPanel);
			frame.remove(codePanel);
			frame.remove(IOPanel);
			frame.setJMenuBar(null);
			frame.add(logInPanel.returnPanel());
			frame.repaint();
			frame.setVisible(true);
		}
		
	}
	class UndoActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			isUndo=true;
			
		}
		
	}
	class RedoActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			isRedo=true;
			
		}
		
	}
}
