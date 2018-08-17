package ui;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Undo implements Runnable{

	private MainFrame mainFrame;
	private ArrayList<String>list;
	
	public Undo(MainFrame mainFrame){
		this.mainFrame=mainFrame;
	}
	/**
	 * 可撤销10次
	 */
	public void run(){
		list=new ArrayList<>();
		String firstWord=mainFrame.returnWord();
		int count=0;
		while(mainFrame.isEditing){
			count++;
			
			if(mainFrame.isUndo){
				int index=list.size();
				if(index!=0&&index!=1){
				String temp=list.get(index-2);
				mainFrame.setTextArea(temp);
				list.remove(index-1);
				}
				mainFrame.isUndo=false;
			}
			if(mainFrame.isRedo){
				list.clear();
				list.add(firstWord);
				mainFrame.setTextArea(firstWord);
				mainFrame.isRedo=false;
			}
			if(count==100){
				count=0;
				String word=mainFrame.returnWord();
				int tempCount=list.size();
				if(tempCount==0){
					list.add("");
					tempCount++;
				}
				
				String lastWord;
				
				lastWord=list.get(tempCount-1);
			    if(!lastWord.equals(word)){
			    	list.add(word);
			    	if(tempCount==10){
			    		list.remove(0);
			    	}
			    	System.out.println("Save success");
			    }
			}
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}
		System.out.println("Thread close");
	}
	
	/**
	 * 
	 * 搁置的方法
	 * 文件反复使用bufferdReader使用mark计数过大，暂时不建议使用
	 */
	
	public void useLessMethod() {
		
		File f=new File("Undo");
		FileWriter fw;
		if(!f.exists()){
			try {
				f.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			try {
				fw = new FileWriter(f,false);
				fw.write("");
				fw.flush();
			} catch (IOException e1) {
				// TODO 自动生成的 catch 块
				e1.printStackTrace();
			}
			System.out.println("deletSuccess");
			try {
				f.createNewFile();
			} catch (IOException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}
		try{
		fw=new FileWriter(f,true);
		FileReader fr=new FileReader(f);
		BufferedReader bf=new BufferedReader(fr);
		BufferedReader bf1=new BufferedReader(fr);
		int count=0;
		int saveNum=0;      //当前保存过的版本数
		int undoNum=0;      //当前撤销到的版本数
		while(mainFrame.isEditing){
	
			count++;
			if(mainFrame.isUndo){
				
			}
			if(count==100){
				count=0;
				String word=mainFrame.returnWord();
			    String lastWord="";
			    String temp=bf.readLine();
			    boolean isLastWord=false;
			    while(temp!=null){
				    if(temp.equals("*lastWord*"+saveNum)){
					    isLastWord=true;
					    temp=bf.readLine();
					    break;
				    }
				    temp=bf.readLine();
			    }
			    while(temp!=null){
					lastWord=lastWord+temp+"\n";
			    }
			    System.out.println(lastWord);
			    if(!lastWord.equals(word+"\n")){
			    	saveNum++;
				    fw.write("*lastWord*"+saveNum+"\n");
				    fw.write(word+"\n");
				    fw.flush();
				    System.out.println("Save Success");
			    }
			}
			Thread.sleep(50);
		}
		System.out.println("Thread close");
		fw.close();
		bf.close();
		}catch (InterruptedException e) {
				e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}
		
	}

}
