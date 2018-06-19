package com.xh.sdk.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xh.sdk.dao.userDao;
import com.xh.sdk.model.StatisticsObj;
import com.xh.sdk.model.TuidingObj;

@Service
@Transactional(value = TxType.NOT_SUPPORTED)

public class StatisticsService {


	@Autowired
	private userDao dao;
	/**
	 * 按天查询订购数据
	 * @param time
	 * @param productId
	 * @param codeType
	 * @param yystype
	 * @return
	 */
	
	public JSONArray getProductDay(String time,String productId,String codeType,String yystype){
		System.out.println("ProductDay is run");
		
	
		 
		 String retime = time.replace("-", "");
		 
		String str = "";
		JSONArray arr = new JSONArray();
		StatisticsObj st = new StatisticsObj();
		
		st.setTableName(retime);
		st.setProductId(productId);
		st.setCodeType(codeType);
		st.setYystype(yystype);
		
		List<HashMap> list =dao.getProductDay(st);
	     //System.out.println(list.size());
		if(list!=null&&list.size()>0){
		
			for(HashMap map:list){
				JSONObject json = new JSONObject();
                json.put("createTime", time);
                json.put("appId", map.get("appId"));
                json.put("cost", map.get("cost"));
                json.put("productId", map.get("productId"));
                json.put("pyname", map.get("pyname"));
                json.put("times", map.get("times"));
				//String result = JSON.toJSONString(map);
				arr.add(json);
			}			
		}
		
		
		
		//System.out.println( arr.toJSONString());
		
	return arr;
	}
	/**
	 * 按天查询退订数据
	 * @param time
	 * @return
	 */
	public JSONArray getTuidingDay(String time){
	 System.out.println(time);
		// String retime = time.replace("-", "");
		 
		String str = "";
		JSONArray arr = new JSONArray();
		TuidingObj st = new TuidingObj();
		DateFormat fmt =new SimpleDateFormat("yyyy-MM-dd");
		Date date=new Date();
		try {
			date = fmt.parse(time);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		st.setAddtime(date);
		
		 //System.out.println("this is run");
		List<HashMap> list =dao.getTuidingDay(st);
		
		// System.out.println("this is ru11111n");
	     //System.out.println(list.size());
		if(list!=null&&list.size()>0){
		
			for(HashMap map:list){
				JSONObject json = new JSONObject();
                json.put("createTime", time);
                json.put("appId", map.get("appId"));        
                json.put("productId", map.get("productId"));    
                json.put("pyname", map.get("pyname"));    
                json.put("times", map.get("times"));
				//String result = JSON.toJSONString(map);
				arr.add(json);
			}			
		}
		
		
		
		//System.out.println( arr.toJSONString());
		
	return arr;
	}
	
	
	//获取指定月份的天数
    public static int getDaysByYearMonth(int year, int month) {

        Calendar a = Calendar.getInstance();
        a.set(Calendar.YEAR, year);
        a.set(Calendar.MONTH, month - 1);
        a.set(Calendar.DATE, 1);
        a.roll(Calendar.DATE, -1);
        int maxDate = a.get(Calendar.DATE);
        return maxDate;
    }
	
	
	
	/**
	 * 按月份渠道对账
	 * @param time
	 * @return
	 */
	public JSONArray getChannelDuizhang(String appid,String time){
	 //System.out.println(time);
		// String retime = time.replace("-", "");
		JSONArray result = new JSONArray();
		
		DateFormat fmt =new SimpleDateFormat("yyyy-MM-dd");
		Date date=new Date();
		try {
			date = fmt.parse(time);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		  Calendar cal = Calendar.getInstance();
	        cal.setTime(date);//month 为指定月份任意日期
	        int year = cal.get(Calendar.YEAR);
	        int m = cal.get(Calendar.MONTH);
	        int dayNumOfMonth = getDaysByYearMonth(year, m);
	        cal.set(Calendar.DAY_OF_MONTH, 1);// 从一号开始

	        for (int i = 0; i < dayNumOfMonth; i++, cal.add(Calendar.DATE, 1)) {
	        	//JSONArray arr = new JSONArray();
	            Date d = cal.getTime();
	            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
	            String df = simpleDateFormat.format(d);
	            
	            StatisticsObj st = new StatisticsObj();
	    		
	    		st.setTableName(df);
	    		st.setAppId(appid);
	    		List<HashMap> list =dao.getChannelMonth(st);     
	    		System.out.println(df+"-----------------"+list.size());
	    		if(list!=null&&list.size()>0){
	    			
	    			for(HashMap map:list){
	    				JSONObject json = new JSONObject();
	                    json.put("createTime", df);
	                    json.put("appId", map.get("appId"));
	                    json.put("cost", map.get("cost"));
	                    json.put("productId", map.get("productId"));
	                    json.put("pyname", map.get("pyname"));
	                    json.put("times", map.get("times"));
	    				//String result = JSON.toJSONString(map);
	                    result.add(json);
	    			}			
	    		} 
	            
	            
	  }
		
		
		

		
		
		//System.out.println( arr.toJSONString());
		
	return result;
	}
	
	
	
	
	
}
