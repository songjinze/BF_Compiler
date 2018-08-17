package ui;

import java.rmi.RemoteException;

import rmi.RemoteHelper;

public class SignIn{

	
	private String userID;
	private String userPassword;
	
	public void getUser(String userID,String userPassword){
		this.userID=userID;
		this.userPassword=userPassword;
	}
	
	
	public boolean register(){
		boolean isRegister=false;
		try {
			isRegister = RemoteHelper.getInstance().getUserService().signIn(userID, userPassword);
		} catch (RemoteException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}		
		return isRegister;
	}

}
