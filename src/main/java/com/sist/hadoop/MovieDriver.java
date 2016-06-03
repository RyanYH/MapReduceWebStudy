package com.sist.hadoop;

import java.io.File;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.springframework.stereotype.Component;

import com.sist.movie.MovieDataManager;

@Component
public class MovieDriver {
	public void movieMapReduce()
	{
		try{
			Configuration conf =
					new Configuration();
			Job job = new Job(conf,"MovieCount");
			job.setJarByClass(MovieDriver.class);
			job.setMapperClass(MovieMapper.class);
			job.setReducerClass(MovieReducer.class);
			job.setMapOutputKeyClass(Text.class);
			job.setOutputValueClass(IntWritable.class);
			
			FileInputFormat.addInputPath(job, new Path("/home/sist/bigdataStudy/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/MapReduceWebProject/desc.txt"));
			File dir = new File("/home/sist/bigdataStudy/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/MapReduceWebProject/output");
			if(dir.exists())
			{
				File[] files = dir.listFiles();
				for(File f:files)
				{
					f.delete();
				}
				dir.delete();
			}	
			FileOutputFormat.setOutputPath(job, new Path("/home/sist/bigdataStudy/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/MapReduceWebProject/output"));
			
			MovieDataManager m = new MovieDataManager();
			
			job.waitForCompletion(true);
		}catch(Exception ex){
			
		}
	}
}
