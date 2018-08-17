package rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import service.ExecuteService;
import service.IOService;
import service.UserService;
import serviceImpl.IOServiceImpl;
import serviceImpl.UserServiceImpl;
import serviceImpl.ExecuteServiceImpl;

public class DataRemoteObject extends UnicastRemoteObject implements IOService, UserService{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4029039744279087114L;
	private IOService iOService;
	private UserService userService;
	protected DataRemoteObject() throws RemoteException {
		iOService = new IOServiceImpl();
		userService = new UserServiceImpl();
	}

	@Override
	public boolean writeFile(String file, String userId, String fileName,String language) throws RemoteException{
		// TODO Auto-generated method stub
		return iOService.writeFile(file, userId, fileName,language);
	}

	@Override
	public String readFile(String userId, String fileName) throws RemoteException{
		// TODO Auto-generated method stub
		return iOService.readFile(userId, fileName);
	}

	@Override
	public String[] readFileList(String userId) throws RemoteException{
		// TODO Auto-generated method stub
		return iOService.readFileList(userId);
	}

	@Override
	public boolean login(String username, String password) throws RemoteException {
		// TODO Auto-generated method stub
		return userService.login(username, password);
	}

	@Override
	public boolean logout(String username) throws RemoteException {
		// TODO Auto-generated method stub
		return userService.logout(username);
	}

	@Override
	public String RunCode(String code, String param) throws RemoteException {
		// TODO 自动生成的方法存根
		return iOService.RunCode(code, param);
	}

	@Override
	public boolean signIn(String username, String password) throws RemoteException {
		// TODO 自动生成的方法存根
		return userService.signIn(username, password);
	}

	@Override
	public String readHistoryFile(String userId, String fileName, int version) throws RemoteException {
		// TODO 自动生成的方法存根
		return iOService.readHistoryFile(userId, fileName, version);
	}

	
}
