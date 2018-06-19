package com.xh.sdk.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.xh.sdk.redis.RedisClientTemplate;

@Component
public class QuartzdsService {

	@Autowired
	private RedisClientTemplate redis;
	
	@Autowired
	private StatisticsService st;


	@Autowired
	private RdService rd;

	@Scheduled(cron = "0 0/5 * * * ?")
	public void xuelele() {
		System.out.println("quartz is run");
		rd.checkIf();
		

	}

	/**
	 * 每日执行，数据查询
	 * 每天两点触发
	 */

	@Scheduled(cron = "	0 0 2 * * ?")
	public void DayCommand() {
//		System.out.println("quartz is run");
//		rd.checkIf();
		

	}
	
	
	/**
	 * 每月执行，数据查询
	 * 每月 1日晚4点触发
	 */
	
	@Scheduled(cron = "0 0 4 1 * ?")
	public void MonthCommand() {
//		System.out.println("quartz is run");
//		rd.checkIf();
//		

	}
//	@Scheduled(cron = "0 0/15 * * * ?")
//	public void getCilentIp() {
//		try {
//			NameValuePair[] pair = new NameValuePair[0];
//			String str = HttpPost.httpGets(
//					"http://api.xicidaili.com/free2016.txt", pair);
//			String[] sourceStrArray = str.split("\\\r\\\n");
//			for (int i = 0; i < sourceStrArray.length; i++) {
//				redis.hset("clientIp", i+"", sourceStrArray[i]);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

}
