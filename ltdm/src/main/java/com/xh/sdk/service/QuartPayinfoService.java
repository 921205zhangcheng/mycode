package com.xh.sdk.service;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.xh.sdk.dao.userDao;
import com.xh.sdk.model.Payinfo;
import com.xh.sdk.model.PayinfoDay;

@Component
public class QuartPayinfoService{
	@Autowired
	private userDao dao;
	@Scheduled(cron = "0 0 1 * * ?")
	//往pay_day表里统计每天成功数据
	public void statisticsPayinfo() throws IOException{	
		String tableName=getbeforeTime();
		//String tableName="20180328";
		Payinfo  payinfo=new Payinfo();		
		payinfo.setTableName(tableName);
		List<PayinfoDay> payinfoDays=dao.getPayInfosum(payinfo);
		if(payinfoDays.size()>0){
		for(PayinfoDay payinfoDay:payinfoDays){
			payinfoDay.setAddtime(tableName);
			dao.savePayinfoDay(payinfoDay);
		}
		}
		
		System.out.print("=====jieshu");
	}
	
	 //获取前一天的时间
		public static String getbeforeTime(){
		Date dNow = new Date();   //当前时间
		Date dBefore = new Date();
		Calendar calendar = Calendar.getInstance(); //得到日历
		calendar.setTime(dNow);//把当前时间赋给日历
		calendar.add(Calendar.DAY_OF_MONTH, -1);  //设置为前一天
		dBefore = calendar.getTime(); 
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");// 设置日期格式
		String time = df.format(dBefore);// new Date()为获取当前系统时间
		return time ;
		}
		
		//往老用户里面插入数据
		@Scheduled(cron = "0 0 2 * * ?")
		public void insetrold_user() throws IOException{	
			String tableName=getbeforeTime();
			//String tableName="20180331";
			Payinfo  payinfo=new Payinfo();		
			payinfo.setTableName(tableName);		
			dao.insetrolduser(payinfo);	
			System.out.print("=====insetrold_user");
		}
}
