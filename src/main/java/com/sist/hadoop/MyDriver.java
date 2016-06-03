package com.sist.hadoop;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.rosuda.REngine.Rserve.RConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.hadoop.mapreduce.JobRunner;
import org.springframework.stereotype.Component;

import com.sist.movie.MovieDataManager;

import java.io.File;
import java.util.*;

@Component("md")
public class MyDriver {
	@Autowired
	private JobRunner jobRunner;
	@Autowired
	private Configuration conf;
/*	@Autowired
	private MovieDataManager mgr;*/
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		ApplicationContext app=new ClassPathXmlApplicationContext("app.xml");
		MyDriver md=(MyDriver)app.getBean("md");
		MovieDataManager mgr =
					(MovieDataManager) app.getBean("mgr");
		// md.jobRunner.call();
		/*md.fileRemove();
		System.out.println("파일 삭제 완료");
		md.copyFromLocal();
		System.out.println("저장 완료");
		md.jobRunner.call();
		System.out.println("MapReducer 완료");
		md.copyToLocal();
		System.out.println("Local Copy 완료");
		md.rGraph();
		System.out.println("그래프 완료");*/
		Scanner scan = new Scanner(System.in);
		System.out.print("영화제목을 입력하세요. : ");
		String title=scan.next();
		File file = new File("/home/sist/desc.txt");
		if(file.exists())
			file.delete();
		for(int i=1;i<=3;i++)
		{
			String json = mgr.movie_review(title, i);
			mgr.json_parse(json);
		}
		md.jobCall();
	}
	public void jobCall()
	{
		try{
			jobRunner.call();
		}catch(Exception ex){
			
		}
	}
	public void copyFromLocal() {
		try {
			FileSystem fs=FileSystem.get(conf);
			fs.copyFromLocalFile(new Path("/home/sist/desc.txt"), new Path("/input/desc.txt"));
			fs.close();
		}
		catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	public void copyToLocal() {
		try {
			FileSystem fs=FileSystem.get(conf);
			fs.copyToLocalFile(new Path("/output/part-r-00000"), new Path("/home/sist/part-r-00000"));
			fs.close();
		}
		catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	public void fileRemove() {
		try {
			FileSystem fs=FileSystem.get(conf);
			if(fs.exists(new Path("/output"))) {
				fs.delete(new Path("/output"), true);
			}
			if(fs.exists(new Path("/input"))) {
				fs.delete(new Path("/input"), true);
			}
		}
		catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	public void rGraph() {
		try {
			RConnection rc=new RConnection();
			/*rc.voidEval("library(ggplot2)");*/
			rc.voidEval("data<-read.table(\"/home/sist/part-r-00000\")");
			rc.voidEval("png(\"/home/sist/my.png\")");
			/*rc.voidEval("plot(data, aes(x=data$V1, y=data$V2))+geom_bar(stat=\"identity\")");*/
			rc.voidEval("barplot(data$V2,names.arg=data$V1,main=\"영화 감성 분석\")");
			rc.voidEval("dev.off()");
			rc.close();
		}
		catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	
}
