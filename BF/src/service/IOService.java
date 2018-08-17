//服务器IOService的Stub，内容相同
package service;

import java.rmi.Remote;
import java.rmi.RemoteException;
public interface IOService extends Remote{
	public boolean writeFile(String file, String userId, String fileName,String language)throws RemoteException;
	
	public String readFile(String userId, String fileName)throws RemoteException;
	
	public String[] readFileList(String userId)throws RemoteException;
	
	public String readHistoryFile(String userId,String fileName,int version)throws RemoteException;
	
	public String RunCode(String code, String param)throws RemoteException;
}
