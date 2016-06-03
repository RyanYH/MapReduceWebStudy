package com.sist.hadoop;

import java.io.File;
import java.io.FileWriter;
import java.util.List;

import org.apache.hadoop.hdfs.server.namenode.status_jsp;

import twitter4j.FilterQuery;
import twitter4j.Query;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;

/*
 * 1. Mapper(단어분석) => 형태소 분석
 * 2. Reducer -> 분석결과누적
 * 3. Driver(실행파일)
 * ====================================
 * 분석
 *   = 단순분석(구조가 있는 경우)
 *   = 복합분석(정규식)
 */
public class MainClass {	
	public static void main(String[] args)
	throws Exception
	{
//		TwitterStream ts = new TwitterStreamFactory().getInstance();
//		TwitterListener list = new TwitterListener();
//
//		ts.addListener(list);
////		
//		String[] data = {"문재인","안철수","김무성",
//						   "오세훈","박원순","유승민",
//						   "김부겸","정의화","반기문",
//						   "안희정"};
//		
		Twitter twitter = TwitterFactory.getSingleton();
		String keyword = "곡성";
		Query query = new Query(keyword);
		query.setCount(500);
		query.setSince("2015-01-01");
		twitter4j.QueryResult result = twitter.search(query);
		File file = new File("/home/sist/bigdataStudy/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/MapReduceWebProject/desc.txt");
		if(file.exists())
			file.delete();
		FileWriter fw=new FileWriter("/home/sist/bigdataStudy/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/MapReduceWebProject/desc.txt",true);
		for(Status status:result.getTweets())
		{
			System.out.println("@"+status.getText());
			fw.write(status.getText());
			fw.append("\n");
	
		}
		fw.close();
		List<Status> status = result.getTweets();

/*		FilterQuery fq = new FilterQuery();
		fq.track(data);
		ts.filter(fq);*/
		
	}
}
