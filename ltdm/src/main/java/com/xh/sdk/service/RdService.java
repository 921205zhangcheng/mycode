package com.xh.sdk.service;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.apache.commons.httpclient.NameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xh.sdk.common.HttpClienthttpsUtil;
import com.xh.sdk.common.HttpPost;
import com.xh.sdk.common.LpTools;
import com.xh.sdk.common.memcached;
import com.xh.sdk.dao.userDao;
import com.xh.sdk.model.ChannelCodeInfo;
import com.xh.sdk.model.Gamecode;
import com.xh.sdk.model.Payinfo;
import com.xh.sdk.model.TuidingObj;
import com.xh.sdk.redis.RedisClientTemplate;
@Service
@Transactional(value = TxType.NOT_SUPPORTED)
public class RdService {

	@Autowired
	private RedisClientTemplate redis;

	@Autowired
	private userDao dao;

	@Autowired
	private HttpPost httppost;

	@Autowired
	private memcached mc;

	/**
	 * 接收同步数据
	 * 
	 * @param json
	 */

	@Async
	public void acceptResult(JSONObject json) {
		String orderid = json.getString("orderid");
		String param = json.getString("param") != null ? json
				.getString("param") : "";
		String result = json.getString("result");
		String resultmsg = json.getString("msg");
		String mobile = json.getString("mobile");
		String codeType = json.getString("codeType");
		String productId = json.getString("productId");
		//t退订用户
		if("1".equals(result)){
			acceptTUIDDINGOrder(json);
			return ;
		}	
		if (!"".equals(orderid)&&mc.get(orderid.toString()) != null) {
			JSONObject sjson = JSON.parseObject(mc.get(
					orderid.toString().trim()).toString());
			System.out.println("json=========" + json.toJSONString());
			Payinfo pay = JSON.toJavaObject(sjson, Payinfo.class);

			if (pay != null) {
				// System.out.println("pay is null");
				if (mobile != null && !"".equals(mobile)) {
					pay.setPhone(mobile);
				}

				//pay.setResult(result);
				
				// redis.hset("pay_info", param, JSON.toJSONString(pay));
				pay.setResult(result);
				pay.setTableName("pay_info_" + pay.getOrderId().substring(0, 8));
				pay.setResultDesc(resultmsg);
				mc.set(orderid, JSON.toJSONString(pay));
				if("succ".equals(result)){	
					//利用redis保存 包月成功数据
					if("2".equals(codeType)){
						
					redis.hset("bysucc", mobile+pay.getProductId(), JSON.toJSONString(pay));
					
					}
				    sendSyncContent(pay);
				}else{
				//退订用户	
					
					
				}
			}
		} else {
			
			if("2".equals(codeType)){
				Payinfo ss = new Payinfo();
				ss.setAddtime(new Date() );
				ss.setAppId("1002");
				redis.hset("bysucc", mobile+productId, JSON.toJSONString(ss));
				}
			
			acceptResultNoOrder(json);

			// 没有取得订单但是成功的数据或者没有透传参数的数据

		}
	}
	/**
	 * 没有订单数据 接收同步数据 
	 * 
	 * @param json
	 */

	public void acceptResultNoOrder(JSONObject json) {
		
		String cost = json.getString("cost");
		if(cost==null){
			cost = "0";
		}
		Payinfo pay = new Payinfo();
		pay.setPayType(json.getString("payType"));
		pay.setCost(Integer.valueOf(cost));
	    pay.setPhone(json.getString("mobile"));
	    pay.setResult(json.getString("result"));
	    pay.setResultDesc(json.getString("smscontent"));
	   // pay.setResultDesc( json.getString("result"));
	    
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String tableName = "pay_info_" + sdf.format(new Date());
	    pay.setTableName(tableName);
	    pay.setAddtime(new Date());	    
	    dao.savePayinfo(pay);
	}
	
	/**
	 *退订数据
	 * 
	 * @param json
	 */

	public void acceptTUIDDINGOrder(JSONObject json) {
		TuidingObj st = new TuidingObj();
	   // pay.setResultDesc( json.getString("result"));
		Payinfo pay =JSON.parseObject(redis.hget("bysucc", json.getString("mobile") + json.getString("productId")), Payinfo.class) ;
		if(pay!=null){
		st.setAppId(pay.getAppId());
		st.setDgtime(pay.getAddtime());
		}
		st.setPhone(json.getString("mobile"));
		st.setStatu(json.getString("result"));
		st.setProductId(json.getString("productId"));
		
 	    dao.saveTuiding(st);
	}
	/**
	 * 模拟数据
	 */
	@Async
	public void xuelelemoni(Payinfo pay){
		   try {	
		String cookie = pay.getRe();
		String PHPSESSID = "";
		String __hash__ = "";
		String re = "";
		Map<String,String> result = new HashMap<String,String>();  
		Map<String,String> createMap = new HashMap<String,String>();  
        createMap.put("oc_id",pay.getPayId());  
//        createMap.put("ocs_id","5906");  
//        createMap.put("resolution","720p");  
//        createMap.put("accountID","18658191488");
//        createMap.put("type","buy");
       //https://xuelele.10155.com/Wap/Course/info
        //https://xuelele.10155.com/Wap/Course/getSectionInfo
        //https://xuelele.10155.com/Wap/Order/buyCourse
        
     
       result = HttpClienthttpsUtil.doPost("https://xuelele.10155.com/Wap/Course/Info/oc_id/"+pay.getPayId(),createMap, "UTF-8",cookie);  
        	// System.out.println("result:"+result.get("result"));
       
       
       String ss = result.get("result");
	   ss = ss.substring(ss.indexOf("user_id"));
	   ss = ss.substring(0,ss.indexOf("\","));
	   ss= ss.substring(ss.indexOf("\"")+1);
    
       createMap.put("ocs_id","5906");  
       createMap.put("resolution","720p");  
        
        result = HttpClienthttpsUtil.doPost("https://xuelele.10155.com/Wap/Course/getSectionInfo",createMap, "UTF-8",cookie); 
        
  	  
        
        JSONObject json = JSON.parseObject(result.get("result"));
        JSONObject jsons = JSON.parseObject(json.getString("resultdata")); 
        String videojson = jsons.getString("videoUrl");
        
      HttpClienthttpsUtil.doPost(videojson,createMap, "UTF-8",cookie);
        
      HttpClienthttpsUtil.doPost("https://xuelele.10155.com/Wap/Index/pullNotify",createMap, "UTF-8",cookie);  
      createMap.put("event","0");  
      createMap.put("login_type","0");  
      createMap.put("user_id",ss);  
      createMap.put("phone",pay.getPhone());  
      createMap.put("oc_name",URLEncoder.encode("快男明星刘心的欢乐弹唱音乐讲堂"));  
      createMap.put("occ_id","150");  
      createMap.put("occ_name",URLEncoder.encode("第1课：自我介绍及教学主题曲目《风》"));  
      createMap.put("ocs_id","5906");  
      createMap.put("ocs_name",URLEncoder.encode("1-自我介绍-刘心"));  
      createMap.put("freeFlow","1");  
      createMap.put("videoUrl","videojson");  
      createMap.put("videoSource","1");  
      createMap.put("videoResolution","720p");  
      createMap.put("ip","");  
      createMap.put("videoSource","1");  
      createMap.put("address","");  
      createMap.put("showp","");  
      createMap.put("lg","zh-cn");  
      createMap.put("uuid","");  
      HttpClienthttpsUtil.doPost("https://r.xuelele.10155.com/rapi/Rest/ActionPoint/courseVideo",createMap, "UTF-8",cookie);  
      
      int j = 0;  
      for(int i = 0;i<15;i++){  	  
      int Temp=(int) Math.round(Math.random()*5+10);
      j = j+Temp;
      createMap.clear();
      createMap.put("oc_id", pay.getPayId());	 
      createMap.put("ocs_id", "5906");	
      createMap.put("time", j+"");	
      createMap.put("duration", "202");	
      HttpClienthttpsUtil.doPost("https://xuelele.10155.com/Wap/Course/cvRecord",createMap, "UTF-8",cookie);  

		Thread.sleep(1000*Temp);
      }
      } catch (InterruptedException e) {
  		// TODO Auto-generated catch block
  		System.out.println(e);
  	}
     
	
	
	
	
	
	
	}
	
	/**
	 * 联通学乐乐 代码成功查询
	 */

	public void checkIf() {
		List<Payinfo> paylist = new ArrayList<Payinfo>();
		Payinfo p = new Payinfo();
		SimpleDateFormat s = new SimpleDateFormat("yyyyMMdd");
		p.setTableName("pay_info_" + s.format(new Date()));
		if(mc.get("xuelele")!=null){
		paylist = JSON.parseArray(mc.get("xuelele").toString(), Payinfo.class);
		}
		if (paylist != null && paylist.size() > 0) {
			
	
			mc.delete("xuelele");
	
			for (int i = 0; i < paylist.size(); i++) {
				Payinfo pay = paylist.get(i);
				
				System.out.println("订单"+pay.getOrderId()+"执行同步");
				JSONObject json = new JSONObject();
				try {
					Gamecode code = ltzy1(pay);
					json = JSON.parseObject(code.getRe());
					if ("000000".equals(json.getString("returnCode"))) {
						JSONArray arry = (JSONArray) json.get("body");
						if (arry.size() > 0) {
							JSONObject sjson = arry.getJSONObject(0);
							sjson = JSON.parseObject(sjson
									.getString("productInfo"));
							if (("0000002469".equals(sjson
									.getString("productId")) && "1500"
									.equals(sjson.getString("price")))
									|| ("0000002470".equals(sjson
											.getString("productId")) && "1500"
											.equals(sjson.getString("price")))) {
								pay.setResult("succ");
								sendSyncContent(pay);
								
//								if("0000002469".equals(sjson
//										.getString("productId"))){
//									int Temp=(int) Math.round(Math.random()*100);
//									if(Temp<15){
//										xuelelemoni(pay);
//									}
//									
//								}
						
							}
						}
					}
				} catch (Exception e) {
				}
			}
		}
	}

	/**
	 * 发送同步数据
	 * 
	 * @param pay
	 */
	@Async
	public void sendSyncContent(Payinfo pay) {
		String appId = pay.getAppId();
		String productId = pay.getProductId();
		ChannelCodeInfo code =JSON.parseObject(redis.hget("ChannelCodeInfo", appId + productId), ChannelCodeInfo.class) ;
		String str = "";
		String url = code.getUrl();
		String probability = code.getProbability();

		int p;
		if (url != null && (!"".equals(url))) {
			int val = (int) (Math.random() * 100 + 1);
			//String probability = redis.hget("probability", appId + productId);
			try {
				p = Integer.valueOf(probability);
			} catch (Exception e) {
				p = 80;
			}
			if (val < p) {
				// cp.sendSyncContent(url,pay);

				try {
					if ("succ".equals(pay.getResult())) {
						pay.setResult("0");
					}
					NameValuePair[] pair = new NameValuePair[8];
					SimpleDateFormat formatter = new SimpleDateFormat(
							"yyyy-MM-dd HH:mm:ss");
					String dateString = formatter.format(new Date());
					pair[0] = new NameValuePair("cpparam", pay.getCpparam());
					pair[1] = new NameValuePair("money", pay.getCost() + "");
					pair[2] = new NameValuePair("imsi", pay.getImsi());
					pair[3] = new NameValuePair("imei", pay.getImei());
					pair[4] = new NameValuePair("time",
							URLEncoder.encode(dateString));
					pair[5] = new NameValuePair("phone", pay.getPhone());
					pair[6] = new NameValuePair("status", pay.getResult());
					pair[7] = new NameValuePair("linkid", pay.getOrderId());
					str = HttpPost.mobileHttpGet(url, pair);
					if ("ok".equals(str)) {

						pay.setSycstatu("0");

					}

				} catch (Exception e) {
					e.printStackTrace();
				}

			}else{
				pay.setResult("0");
				pay.setAppId("1002");
			}

		}
		dao.updatePayinfoByOrderId(pay);
		//dao.savePayinfo(pay);
	}

	/**
	 * 联通学乐乐查询订购结果
	 * 
	 * @param pay
	 * @return
	 */
	public Gamecode ltzy1(Payinfo pay) {

		String productId=pay.getProductId();
		
		
		Gamecode code = new Gamecode();
		if (pay.getPhone() == null || "".equals(pay.getPhone())) {
			code.setPayType("0");
			return code;
		}
		int cost = pay.getCost();

		// "0000002445"
		String str = "";
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		String dateString = formatter.format(new Date());
		try {
			String phone = URLEncoder.encode(pay.getPhone(), "UTF-8");
			String appkey = URLEncoder.encode("3000005529", "UTF-8");
			String timestamp = URLEncoder.encode(dateString, "UTF-8");
			String verifyParam = URLEncoder.encode("0000002470", "UTF-8");
			if ("50012".equals(productId)) {
				verifyParam = "0000002469";
			} else if ("50013".equals(productId)) {
				verifyParam = "0000002470";
			}
			
		
			String verifyType = URLEncoder.encode("10001", "UTF-8");
			String sign = "accountID" + phone + "appkey" + appkey
					+ "isBrief0productID" + verifyParam + "timestamp"
					+ timestamp + "A8ED7494F0BC20AF";
			sign = LpTools.getMD5(sign).toUpperCase();
			NameValuePair[] pair = new NameValuePair[6];

			pair[0] = new NameValuePair("accountID", pay.getPhone());
			pair[1] = new NameValuePair("productID", verifyParam);
			pair[3] = new NameValuePair("appkey", "3000005529");
			pair[4] = new NameValuePair("timestamp", dateString);
			pair[5] = new NameValuePair("sign", sign);
			pair[2] = new NameValuePair("isBrief", "0");

			str = HttpPost.doPostCmdByHttpClient(
					"http://157.255.23.15/api/v2/Product/qrySubedProducts",
					pair);

			System.out.println(str);
			code.setRe(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return code;
	}
}
