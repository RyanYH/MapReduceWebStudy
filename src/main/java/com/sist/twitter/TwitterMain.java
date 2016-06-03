package com.sist.twitter;

import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;

import org.springframework.stereotype.Component;

import twitter4j.Logger;
import twitter4j.Query;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
/*
 * 1. Mapper(단어분석) => 형태소 분석
 * 2. Reducer -> 분석결과누적
 * 3. Driver(실행파일)
 * ====================================
 * 분석
 *   = 단순분석(구조가 있는 경우)
 *   = 복합분석(정규식)
 */
@Component
public class TwitterMain {	
	
	public void Twitt(String title) throws Exception {
		
		Logger logger = Logger.getLogger(TwitterMain.class);
	       
		Twitter twitter = TwitterFactory.getSingleton();

		Query query = new Query(title);
		query.setCount(30);
		twitter4j.QueryResult result = twitter.search(query);
		File file = new File("/home/sist/bigdataStudy/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/MapReduceWebProject/desc.txt");
		if(file.exists())
			file.delete();
		FileWriter fw=new FileWriter("/home/sist/bigdataStudy/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/MapReduceWebProject/desc.txt",true);
		
		for(Status status:result.getTweets())
		{
			if(result.hasNext()){
				  twitter4j.QueryResult nextResult = twitter.search(result.nextQuery());
				  //System.out.println(nextResult);
				  System.out.println("@"+status.getText()+":"+new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(status.getCreatedAt()));
				fw.write(status.getText());
				fw.append("\n");
				//logger.info(status.getText());
			}
		}
		fw.close();
	}
}
