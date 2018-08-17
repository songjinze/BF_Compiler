package serviceImpl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import service.IOService;

public class IOServiceImpl implements IOService{
	
	private ExecuteServiceImpl executeService;
	@Override
	public boolean writeFile(String file, String userId, String fileName,String language) {
		/**
		 * 写文件
		 */
		File f = new File("File/"+userId + "/" + userId+ "_" + fileName+"."+language);
		if(!f.exists()){
			try {
				f.createNewFile();
			} catch (IOException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}
		try {
			System.out.println(file);
			FileWriter fw = new FileWriter(f, true);
			fw.write("\n*Version*\n");
			fw.write(file);
			fw.flush();
			fw.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public String readFile(String userId, String fileName) {
		// TODO Auto-generated method stub
		/**
		 * 读文件
		 */
		File f=new File("File/"+userId+"/"+fileName);
		FileReader fr;
		String result="";
		ArrayList<String>tempList=new ArrayList<>();
		String tempStr="";
		try {
			fr = new FileReader(f);
			BufferedReader bf=new BufferedReader(fr);
			String temp=bf.readLine();
			while(temp!=null){
				if(!temp.equals("*Version*")){
					tempStr+=temp+"\n";
				}else{
					tempList.add(0, tempStr);
					tempStr="";
				}
				temp=bf.readLine();
			}
			tempList.add(0,tempStr);
			
			bf.close();
		} catch (FileNotFoundException e) {
			// TODO 自动生成的 catch 块
			JOptionPane.showMessageDialog(null, "文件不存在！");
		}catch(IOException e){
			e.printStackTrace();
		}
		
		result=tempList.get(0);
		return result;
	}

	@Override
	public String[] readFileList(String userId) {
		// TODO Auto-generated method stub
		/**
		 * 读取文件列表
		 */
		File f=new File("File/"+userId);
		String[]result=f.list();
		if(result.length==0){
			result=null;
		}
		return result;
	}

	@Override
	public String RunCode(String code, String param) throws RemoteException {
		// TODO 自动生成的方法存根
		/**
		 * 执行
		 */
		executeService=new ExecuteServiceImpl();
		String result=executeService.execute(code, param);
		return result;
	}

	@Override
	public String readHistoryFile(String userId, String fileName, int version) throws RemoteException {
		// TODO 自动生成的方法存根
		/**
		 * 读取历史纪录
		 * 
		 */
		if(userId!=null&&!fileName.equals("null.null")){
		File f=new File("File/"+userId+"/"+fileName);
		FileReader fr;
		String result="";
		int count=0;
		boolean hasVersion=false;
		try {
			fr = new FileReader(f);
			BufferedReader bf=new BufferedReader(fr);
			String temp=bf.readLine();
			while(temp!=null){
				if(temp.equals("*Version*")){
					count++;
					if(count==version){
						hasVersion=true;
						temp=bf.readLine();
						break;
					}
				}
				temp=bf.readLine();
			}
			if(hasVersion){
			    while(temp!=null&&!temp.equals("*Version*")){
				    result+=temp+"\n";
				    temp=bf.readLine();
			}
			}else{
				result=null;
			}
			bf.close();
		} catch (FileNotFoundException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}
		
		return result;
	}else{
		return null;
	}
	}
}
