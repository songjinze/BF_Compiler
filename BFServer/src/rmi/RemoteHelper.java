package rmi;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.ExportException;

public class RemoteHelper {
	public RemoteHelper(){
		initServer();
	}
	
	public void initServer(){
		//远程对象
		DataRemoteObject dataRemoteObject=null;
		try {
			dataRemoteObject = new DataRemoteObject();
			
			//创建端口
			LocateRegistry.createRegistry(8889);
			//端口绑定
			Naming.bind("rmi://localhost:8889/Sjz",
					dataRemoteObject);
			
		}catch(ExportException e){
			//若端口已被注册
			try{
				Naming.bind("rmi://localhost:8889/Sjz",
						dataRemoteObject);
			}catch(RemoteException ex){
				ex.printStackTrace();
			}catch(MalformedURLException ex){
				ex.printStackTrace();
			}catch(AlreadyBoundException ex){
				try{
					Naming.rebind("rmi://localhost:8889/Sjz",
							dataRemoteObject);
				}catch(Exception exc){
					exc.printStackTrace();
				}
			}
	    }catch (RemoteException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (AlreadyBoundException e) {
			try{
				Naming.rebind("rmi://localhost:8889/Sjz",
						dataRemoteObject);
			}catch(Exception ecx){
				ecx.printStackTrace();
			}
			e.printStackTrace();
		}
		
	}
}
