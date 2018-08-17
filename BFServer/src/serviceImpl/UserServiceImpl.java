package serviceImpl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.rmi.RemoteException;

import service.UserService;

public class UserServiceImpl implements UserService{

	@Override
	public boolean login(String username, String password)  {
		/**
		 * 登陆
		 */
		boolean result=false;
		File userFile=new File("User");
		try {
			BufferedReader reader=new BufferedReader(new FileReader(userFile));
			String temp=reader.readLine();
			while(temp!=null){
				/*
				 * Sting数组,[0]保存用户名，[1]保存密码
				 */
			    String[]userInf=temp.split(" ");
			    if(userInf[0].equals(username)&&userInf[1].equals(password)){
			    	result=true;
			    	break;
			    }
			    temp=reader.readLine();
			}
		} catch (FileNotFoundException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}
		
		return result;
	}

	@Override
	public boolean logout(String username) throws RemoteException {
		/**
		 * 登出
		 */
		return true;
	}

	@Override
	public boolean signIn(String username, String password) throws RemoteException {

		/**
		 * 注册
		 */
		String userID=username;
		String userPassword=password;
		boolean alreadyHasUser=false;//是否已经有用户
		
		File userInf=new File("User");
		try{
			BufferedReader br=new BufferedReader(new FileReader(userInf));
			String temp=br.readLine();
			while(temp!=null){
				String []temps=temp.split(" ");
				if(temps[0].equals(userID)){
					alreadyHasUser=true;
					break;
				}
				temp=br.readLine();
			}
			br.close();
			//如果不存在用户，则写入User文件
			if(!alreadyHasUser){
				FileWriter fr=new FileWriter(userInf,true);
				fr.write("\n"+userID+" "+userPassword);
				fr.close();
			}
		}catch(IOException ex){
			ex.printStackTrace();
		}
		if(!alreadyHasUser){
			File fp=new File("File/"+userID);
			if(!fp.exists()){
				fp.mkdir();
			}
		}
	
		return !alreadyHasUser;
	}

}
