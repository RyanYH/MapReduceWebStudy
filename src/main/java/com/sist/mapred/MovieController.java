package com.sist.mapred;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.*;
import com.sist.hadoop.MovieDriver;
import com.sist.mongo.RecommandDAO;
import com.sist.mongo.RecommandVO;
import com.sist.movie.*;
import com.sist.r.FeelVO;
import com.sist.r.MovieRGraph;
import com.sist.twitter.TwitterMain;

import java.io.*;

/*
* 1) Container
* 2) DI
* 3) AOP
* 4) Transaction
* 5) Security
* ==> applicationContext구조 작성방법
*
* 1.client request (image click)
* 2. title(no) => 다음(댓글)
* 3. 분석(맵리듀스) => 가장 높은 감성 =몽고디비 저장
* 4. R로 전송 => 그래프
* 5. 추천 영화 => 해당 영화?
*/
@Controller
public class MovieController {
	@Autowired
	private MovieDataManager mgr;
	@Autowired
	private MovieDriver md;
	@Autowired
	private MovieRGraph mr;
	@Autowired
	private RecommandDAO dao;
	@Autowired
	private ApacheDriver ad;
	@Autowired
	private TwitterMain tm;

	@RequestMapping("movie/list.do")
	public String movie_list(Model model) {
		// "/movie/list.jsp"
		List<MovieDTO> list = mgr.movieAllData();
		List<String> raList = mgr.movie_rank();
		List<String> reList = mgr.movie_reserve();
		List<String> bList = mgr.movie_boxoffice();
		model.addAttribute("list", list);
		model.addAttribute("raList", raList);
		model.addAttribute("reList", reList);
		model.addAttribute("bList", bList);
		return "movie/list";
	}

	@RequestMapping("movie/detail.do")
	public String movie_detail(int no, Model model) throws Exception {
		MovieDTO vo = mgr.movieDetail(no);
		File file = new File(
				"/home/sist/bigdataStudy/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/MapReduceWebProject/desc.txt");
		if (file.exists()) {
			file.delete();
		}
		file = new File(
				"/home/sist/bigdataStudy/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/MapReduceWebProject/desc.txt");
		file.createNewFile();
		for (int i = 1; i <= 3; i++) {
			String json = mgr.movie_review(vo.getTitle(), i);
			mgr.json_parse(json);
		}
		md.movieMapReduce();
		mr.rGraph();
		tm.Twitt(vo.getTitle());
		ad.YNDriver();
		
		File log = new File("/home/sist/bigdataStudy/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/MapReduceWebProject/output2/part-r-00000");
		BufferedReader in = new BufferedReader(new FileReader(log));
		String s;
		String postive;
		String num1;
		ArrayList<String> nlist = new ArrayList<String>();
		while ((s = in.readLine()) != null) {
			String[] split = s.split("\t");
			postive = split[0];
			num1 = split[1];
			nlist.add(postive);
			nlist.add(num1);
		}
		in.close();
		// 저장 (R)
		List<FeelVO> list = mr.movieFeelData();
		for (FeelVO fvo : list) {
			RecommandVO rvo = new RecommandVO();
			rvo.setTitle(vo.getTitle());
			rvo.setFeel(fvo.getFeeling());
			rvo.setCount(fvo.getCount());
			dao.recommandInsert(rvo);
		}
		model.addAttribute("nlist",nlist);
		model.addAttribute("vo", vo);
		return "movie/detail";
	}
	/*
	 * 			{
                    name: 'Joe',
                    data: [3, 4, 4, 2, 5]
            	}
	 * 
	 */
	@RequestMapping("movie/recommand.do")
	public String movie_recommand(String feel, Model model) {
		if (feel == null) {
			feel = "로맨스";
		}
		List<RecommandVO> list = dao.recommandAllData();
		RecommandVO rvo = new RecommandVO();
		for (RecommandVO vo : list) {
			if (feel.equals(vo.getFeel())) {
				rvo = vo;
				break;
			}
		}
		List<String> flist = dao.recomandTitle(feel);
		List<MovieDTO> mlist = new ArrayList<MovieDTO>();
		for (String v : flist) {
			MovieDTO dd = mgr.movieDetail(v);
			mlist.add(dd);
		}
		List<RecommandVO> rlist = dao.recommandFeelData(feel);
		String data = "";
		for(RecommandVO rv:rlist)
		{
			if (feel.equals(rv.getFeel())) {
			data+="{name:'"+rv.getFeel()+"',"
					+"data:["+rv.getCount()+",1,1]"
					+"},";
			}
		}
		data = data.substring(0,data.lastIndexOf(','));	
		
		String data1="[";
		 for(RecommandVO rv:rlist)
		 {
			 data1+="['"+rv.getTitle()+"',"+rv.getCount()+"],";
		 }
		data1=data1.substring(0,data.lastIndexOf(','));
		data1+="]";
		
		List<String> slist = dao.recommandRegData();
		// MovieDTO d=mgr.movieDetail(rvo.getTitle());
		// model.addAttribute("dto", d);
		model.addAttribute("data", data);
		model.addAttribute("data1", data1);		
		model.addAttribute("list", list);
		model.addAttribute("feel", feel);
		model.addAttribute("rlist", rlist);
		model.addAttribute("mlist", mlist);
		model.addAttribute("slist", slist);
		return "movie/recommand";
	
	}

	/*
	 * 			{
		            type: 'column',
		            name: 'Joe',
		            data: [4, 3, 3, 9, 0]
		        },
	 * 
	 */
	@RequestMapping("movie/total.do")
	public String movie_total(Model model) {
		List<MovieDTO> list = mgr.movieAllData();
		String value="";
		for(MovieDTO d:list)
		{
			value+="{ type:'column' "
				+ ", name:'"+d.getTitle()+"'"
				+ ", data: ["+d.getLike()+","+d.getReserve()+","+d.getPercent()+"]"
		        +  "},";
		}
		value = value.substring(0,value.lastIndexOf(','));	
		model.addAttribute("value",value);
		model.addAttribute("list", list);
		return "movie/total";
	}
	
}