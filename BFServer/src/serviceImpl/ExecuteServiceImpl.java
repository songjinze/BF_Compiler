//请不要修改本文件名
package serviceImpl;

import java.rmi.RemoteException;
import java.util.ArrayList;

import service.ExecuteService;
import service.UserService;

public class ExecuteServiceImpl implements ExecuteService {

	/**
	 * 请实现该方法
	 */
	
	@Override
	public String execute(String code1, String param) throws RemoteException {
		// TODO Auto-generated method stub
		
		String code=code1;
		code=code.replaceAll(" ", "");
		if(code.startsWith("Ook")){
		code=code.replace("Ook", "");
		String temp="";
		for(int n=0;n<code.length();n+=2){
			switch(code.substring(n, n+2)){
			case ".?":temp+=">";break;
			case "?.":temp+="<";break;
			case "..":temp+="+";break;
			case "!!":temp+="-";break;
			case "!.":temp+=".";break;
			case ".!":temp+=",";break;
			case "!?":temp+="[";break;
			case "?!":temp+="]";break;
		}
		}
		code=temp;
		}
		
		int []container=new int[255];
		int index=0;
		String result="";
		
		
		ArrayList<Integer>IOContainer=new ArrayList<>();
		
		for(int n=0;n<param.length();n++){
			IOContainer.add(param.charAt(n)+0);
		}
		
		
		for(int n=0;n<255;n++){
			container[n]=0;
		}
		
		int indexForIOContainer=0;
		
		for(int n=0;n<code.length();n++){
			switch(code.charAt(n)){
			case '>':
				if(index==254){
					index=0;
				}else{
					index++;
				}
				break;
			case '<':
				if(index==0){
					index=254;
				}else{
					index--;
				}
				break;
			case '+':container[index]++;break;
			case '-':container[index]--;break;
			case '.':result+=(char)container[index]+"";break;
			case ',':
				container[index]=IOContainer.get(indexForIOContainer);
			    indexForIOContainer++;
				break;
			case '[':
				if(container[index]==0){
					int tempIndex=1;
					int count=0;
					while(code.charAt(n+tempIndex)!=']'||count!=0){
						if(code.charAt(n+tempIndex)=='[')
							count++;
						else if(code.charAt(n+tempIndex)==']')
							count--;
						tempIndex++;
					}
					n=n+tempIndex;
				}
				break;
			case ']':
				if(container[index]!=0){
					int tempIndex=1;
					int count=0;
			        while(code.charAt(n-tempIndex)!='['||count!=0){
			        	if(code.charAt(n-tempIndex)=='[')
							count++;
						else if(code.charAt(n-tempIndex)==']')
							count--;
			        	tempIndex++;
			        }
			        n=n-tempIndex;
				}
				break;
			}
		}
		
		
		return result;
	}

}
