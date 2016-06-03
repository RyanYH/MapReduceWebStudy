package com.sist.hadoop;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class MyReducer extends Reducer<Text, IntWritable, Text, IntWritable> {
	private IntWritable res=new IntWritable();
	// a [1,1,1,1,1] => a 5
	@Override
	protected void reduce(Text key, Iterable<IntWritable> iter,
			Reducer<Text, IntWritable, Text, IntWritable>.Context context) throws IOException, InterruptedException {
		int sum=0;
		for(IntWritable i:iter) {
			sum+=i.get();
		}
		res.set(sum);
		context.write(key, res);
	}
	
}