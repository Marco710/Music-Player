package com.example.musicplayer1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import com.example.musicplayer2.LrcModel;
import com.example.musicplayer2.Model;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import android.text.TextUtils;
//扫描类，用于扫描歌曲信息并显示在ListView中
public class Saomiao {
	public  ArrayList<Model> query(Context mcontext){
		
		ArrayList<Model> arraylist=null;
		//扫描->游标 ，扫描本地是否有歌曲
		Cursor c=mcontext.getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null, null);
		 
		if(c!=null){
			//
			arraylist=new ArrayList<Model>();
			
			//一个个存放到arraylist中
			
			while(c.moveToNext() ){
				
				
				Model model;
				model=new Model();
				//扫描本地文件，得到歌曲的相关信息
				String music_name=c.getString(c.getColumnIndex(MediaStore.Audio.Media.TITLE));
				
				String singer=c.getString(c.getColumnIndex(MediaStore.Audio.Media.ARTIST));
				
				String path=c.getString(c.getColumnIndex(MediaStore.Audio.Media.DATA));
				
				//System.out.println(""+music_name);
				//System.out.println(""+singer);
				//System.out.println(""+path);
				//设置值到Model的封装类中	
				model.setMusic_name(music_name);
				model.setSinger(singer);
				model.setPath(path);
				//将model值加入到数组中
				arraylist.add(model);
				
			}
			
			
		}
		return arraylist;
		
	}
	public static  ArrayList<LrcModel> redlrc(String path){
		
		ArrayList<LrcModel> alist=new ArrayList<LrcModel>();
		File f=new File(path.replace(".mp3", ".lrc"));
		
		try {
			FileInputStream fs=new FileInputStream(f);
			
			InputStreamReader inputstreamreader=new InputStreamReader(fs,"utf-8");
			
			BufferedReader br=new BufferedReader(inputstreamreader);
			String s="";
			
			while(null!=(s=br.readLine())){
				
				if(!TextUtils.isEmpty(s)){
					LrcModel lrcmodel=new LrcModel();
					
					String lylrc=s.replace("[", "");
					
					String data_ly[]=lylrc.split("]");
					if(data_ly.length>1){
					
						String time=data_ly[0];
						lrcmodel.setTime(lrcdata(time));
						
						String lrc=data_ly[1];
						lrcmodel.setIrc(lrc);
						//System.out.println("..............."+lrcmodel.getIrc());
				
						
						alist.add(lrcmodel);
						//System.out.println(".................."+alist.get(0).getIrc());
				
					}
				}
				
			}
			
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return alist;
		
	}
	
	public static int lrcdata(String time){
		time=time.replace(":", "#");
		time=time.replace(".", "#");
		
		String mTime[]=time.split("#");
		
		int mtime=Integer.parseInt(mTime[0]);
		int stime=Integer.parseInt(mTime[1]);
		int mitime=Integer.parseInt(mTime[2]);
		
		int ctime=(mtime*60+stime)*1000+mitime*10;
	
		return ctime;
		
		
	}
	

}
