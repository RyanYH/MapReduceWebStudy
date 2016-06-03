package com.sist.mapred;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
// 않~  못~  노잼~  않[가-핳] [A-Za-z] ^가[0-9]{1-3}
	/*
	 ^ 문자열의 시작
	 $ 문자열의 종료
	 . 임의의 한 문자 (문자의 종류 가리지 않음)
	   단, \ 는 넣을 수 없음
	 * 앞 문자가 없을 수도 무한정 많을 수도 있음
	 + 앞 문자가 하나 이상
	 ? 앞 문자가 없거나 하나있음
	 [] 문자의 집합이나 범위를 나타내며 두 문자 사이는 - 기호로 범위를      나타낸다. []내에서 ^가 선행하여 존재하면 not 을 나타낸다.
	 {} 횟수 또는 범위를 나타낸다.
	 () 소괄호 안의 문자를 하나의 문자로 인식 
	 | 패턴 안에서 or 연산을 수행할 때 사용
	 \s 공백 문자
	 \S 공백 문자가 아닌 나머지 문자
	 \w 알파벳이나 숫자
	 \W 알파벳이나 숫자를 제외한 문자
	 \d 숫자 [0-9]와 동일
	 \D 숫자를 제외한 모든 문자
	 \  정규표현식 역슬래시(\)는 확장 문자
	    역슬래시 다음에 일반 문자가 오면 특수문자로 취급하고 역슬래시 다음에 특수문자가 오면 그 문자 자체를 의미
	(?i) 앞 부분에 (?i) 라는 옵션을 넣어주면 대소문자를 구분하지 않음
 */
public class ApacheMapper extends Mapper<LongWritable, Text, Text, IntWritable> {
    private final IntWritable one=new IntWritable(1);
    private Text result=new Text();
    String[] yn = {"잼[가-핳]","추천","보고싶[가-핳]","가[가-핳]","대박","호평","바추[가-핳]","안[가-핳]","없[가-핳]","아까[가-핳]"};
    Pattern[] p = new Pattern[yn.length];
    @Override
    protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, IntWritable>.Context context)
            throws IOException, InterruptedException {
        for(int i=0;i<yn.length;i++)
        {
        	p[i]=Pattern.compile(yn[i]);
        }
    	Matcher[] m = new Matcher[yn.length];
    	for(int i=0;i<yn.length;i++)
    	{
    		m[i]=p[i].matcher(value.toString());
    		while(m[i].find())
    		{
    			if(i>=0 && i<=5)
    			{
    				result.set("긍정");
    			}
    			else
    			{
    				result.set("부정");
    			}
    			//result.set(m[i].group());
    			context.write(result, one);
    		}
    	}
    }  
}