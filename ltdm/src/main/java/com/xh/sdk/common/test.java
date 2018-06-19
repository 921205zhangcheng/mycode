package com.xh.sdk.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URLEncoder;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.xml.sax.SAXException;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.core.sgip.client.SGIPClient;
import com.huawei.smproxy.comm.sgip.message.SGIPSubmitMessage;
import com.huawei.smproxy.comm.sgip.message.SGIPSubmitRepMessage;
import com.thoughtworks.xstream.core.util.Base64Encoder;
import com.xh.sdk.model.Gamecode;
import com.xh.sdk.model.Payinfo;
import com.xh.sdk.model.Userinfo;

public class test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		Random random = new Random();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss"
				+ random.nextInt(1000000));
		String orderId = sdf.format(new Date());
		Payinfo pay = new Payinfo();
		pay.setOrderId(orderId);
		pay.setPhone("18658191488");
		pay.setIccid("WDM218");
		pay.setCost(15);
		pay.setProductId("10655502202501");
		pay.setCpparam("你好");
	TestLtdmFx();
		//TestXuelele(pay);
		//TestHttpsxll();
		//testGetProv();
		//TestXueleleMoni(pay);
		//TestHttpsxllconfirm();
		//Payinfo pay = new Payinfo();
		// TODO Auto-generated method stub

		//testSubmint();
              //lzydDDOxl(pay);
	  // System.out.println(dxyzf(pay));
		//System.out.println(ldxyzfConfirm(pay,"030135"));
		
		//System.out.println(Testmltyyd(pay));
		//System.out.println(TestMusicOrder(pay));
		//System.out.println(TestMusicOrderConfirm(pay, "4705"));
		//System.out.println(TestLMsdk());
		//testDeliver();
		//System.out.println(TestRd("http://127.0.0.1:8080/rd/dxmusictwo"));
		//System.out.println(TestMC("http://47.92.53.128:8080/sdk/test"));
		//NewByywSumbit()
//	System.out.println(	NewByywSumbit(pay));
		//getDateBefore(new Date(),3);
	//System.out.println(TestMC("http://47.92.51.159:8080/sdk/test"));
		//System.out.println(TestTONGJI("http://47.92.89.150/manage/ProductDay"));
		
		//System.out.println(TestData("http://47.92.89.150/manage/ProductDay"));
		//System.out.println(TestData("http://47.92.89.150/manage/ChannelMonth"));
		//System.out.println(TestLPqq("http://120.55.192.39/qq/Charge"));

	//System.out.println(TestgetPay("http://47.92.89.150/sdk/getPay"));
		//System.out.println(TestYLDM("http://47.92.89.150/rd/ylzxfdNotify"));
		
	//System.out.println(Testdyorder("http://47.92.89.150/sdk/dyorder"));
	//System.out.println(DealXuelele());
	//getLTUser();
	//Testltzy1(pay);
		
	}
	
	public static Date getDateBefore(Date d,int day){  
		   Calendar now =Calendar.getInstance();  
		   now.setTime(d);  
		   now.set(Calendar.DATE,now.get(Calendar.DATE)-day);     
	    SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
	   String startTime=sdf.format(now.getTime())+" 00:00:00";  
	   String endTime=sdf.format(now.getTime())+" 23:59:59"; 
		   
		   System.out.println(startTime);
		   System.out.println(endTime);
		   return now.getTime();  
  } 
	
	
	//handle message recieve sgipMsgHead is SGIPMsgHead [messageLength=64, commandId=5, sourceNodeNumber=100001, commandGenerateDate=302145624, commandNumber=1465457096] report detail:Report [submitSequenceNumberStr=30731902143021501261, reportType=0, userNumber=8618658191488, state=2, errorCode=100]
public static void testGetProv(){
	
	
	String str = "";
	String prov = "";
	JSONObject ss = new JSONObject();
	String phone = "8618658191488";
	
	JSONObject json = new JSONObject();
	NameValuePair[] pair = new NameValuePair[1];

	pair[0] = new NameValuePair("tel",phone.substring(2, phone.length()));
	
	str = HttpPost.doPostCmdByHttpClient(
			" https://tcc.taobao.com/cc/json/mobile_tel_segment.htm",
			pair);	
	json = JSON.parseObject(str.split("=")[1]);
	
	prov = json.getString("province");

	
	
}


public static String NewByywSumbit(Payinfo pay) {
	String str = "";

	SGIPSender sender = SGIPSender.getInstance();

	String SPNumber = pay.getProductId();
	String ChargeNumber = pay.getPhone();
	String[] UserNumber = { pay.getPhone() };
	String CorpId = "90214";
	String ServiceType = pay.getIccid();
	int FeeType = 1;
	String FeeValue = "0";
	String GivenValue = "0";
	int AgentFlag = 0;
	int MorelatetoMTFlag = 1;
	int Priority = 0;
	Date ExpireTime = null;
	Date ScheduleTime = null;
	int ReportFlag = 1;
	int TP_pid = 0;
	int TP_udhi = 0;
	int MessageCoding = 8;
	int MessageType = 0;

	byte[] MessageContent = pay.getCpparam().getBytes();
	int MessageLen = MessageContent.length;

	String reserve = null;

	SGIPSubmitMessage msg = null;
	msg = new SGIPSubmitMessage(SPNumber, ChargeNumber, UserNumber, CorpId,
			ServiceType, FeeType, FeeValue, GivenValue, AgentFlag,
			MorelatetoMTFlag, Priority, ExpireTime, ScheduleTime,
			ReportFlag, TP_pid, TP_udhi, MessageCoding, MessageType,
			MessageLen, MessageContent, reserve);

	SGIPSubmitRepMessage returnMsg = null;

	try {
		returnMsg = sender.send(msg);

		if (null == returnMsg) {
			System.out.println("send message failed!");
		} else {
			System.out.println(returnMsg);
		}

	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	String rgex = "=(.*?),";
	return LpTools.getSubUtilSimple(returnMsg.toString(), rgex);

}






public static void testDeliver(){
JSONObject json = new JSONObject();

json.put("sPNumber", "10655502202509");
json.put("userNumber", "8613151830003");
json.put("msgContent", "TD90");

NameValuePair[] pair = new NameValuePair[1];

pair[0] = new NameValuePair("deliver",json.toJSONString());

HttpPost.doPostCmdByHttpClient(
		"http://47.92.53.128:8080/sdk/Ltdm",
		pair);	



	
	
}


public static void TestLtdmFx(){
	SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-DD HH:MM:SS");
	String AccessTime = sdf.format(new Date());
	String SpNumber = "106555022025020";
	String UserNumber = "18658191488";
	String ServiceTag = "90";
	String EncodeStr="";
	
	
	try {
		 byte[] key=new BASE64Decoder().decodeBuffer("6WzIgLavQ067yhf3/98D7IAops2hnbv4"); 
	      byte[] data=(SpNumber + "$"+ UserNumber + "$"+ 
	  			ServiceTag + "$"+ AccessTime).getBytes("UTF-8");      
	      byte[] str3= Des3.des3EncodeECB(key,data );
		EncodeStr = URLEncoder.encode(new BASE64Encoder().encode(str3));
		
		
		NameValuePair[] pair = new NameValuePair[3];

		pair[0] = new NameValuePair("SpNumber",SpNumber);
		pair[1] = new NameValuePair("AccessTime",URLEncoder.encode(AccessTime));
		pair[2] = new NameValuePair("EncodeStr",EncodeStr);
		
		String str = HttpPost.mobileHttpGet("http://220.194.49.20:9133/WebSubscription.aspx", pair);
		System.out.println(str);
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}  
	
	
	
	
	  
	
	//= "";
	//http://220.194.49.20:9133/WebSubscription.aspx?SpNumber=30165&AccessTime=2004-01-01 
	//	10:10:10&EncodeStr=KIQWNWEQREQWK921343KJASDFASD
	
	
	
	
	
	
}




	public static void testSubmint(){
		 SGIPClient client = new SGIPClient();
		List<String> listUserNumber = new ArrayList<String>();
		listUserNumber.add("18658191488");	
		
	    Mt mt = new Mt();
		
    try {
		//System.out.println("================="+client.startCommunication(listUserNumber, "10655502130", "WDM211", "", "bysubmit", "222"));
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		
		
		
	}
	
	
	
	
	public static void getLTUser(){
	   
	   
		try {
			 String filepath = "d:/235.xlsx";  
			
			 BigExcelReader reader = new BigExcelReader(filepath);
		     
		        // 执行解析  
		        reader.parse();  
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OpenXML4JException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        
        
   
		
	
	}
	
	
	
	
	
	public static String DealXuelele(){
		
		Map<String,Integer> map = new HashMap<String,Integer>();
		String prov = "";
		JSONArray arr = new JSONArray();
		JSONObject ss = new JSONObject();
		String str = "";
		List list = readFileByLines("E:/work/xuelelephone.txt");
		//list.add("18658191488");
		
		for(int i = 0;i<list.size();i++){
			JSONObject json = new JSONObject();
			NameValuePair[] pair = new NameValuePair[1];

			pair[0] = new NameValuePair("tel", list.get(i).toString());
			
			str = HttpPost.doPostCmdByHttpClient(
					" https://tcc.taobao.com/cc/json/mobile_tel_segment.htm",
					pair);	
			json = JSON.parseObject(str.split("=")[1]);
			System.out.println(json.toJSONString());
			prov = json.getString("province");
			
			if(map.get(prov)!=null){
				map.put(prov, map.get(prov)+1);
			}else{
				map.put(prov, 1);
			}			
		}
		
		
		
		  for (String key : map.keySet()) {
			  JSONObject result = new JSONObject();
			   System.out.println( key + " = " + map.get(key));
			   result.put("省份", key);
			   result.put("数量", map.get(key));
			   arr.add(result);
			  }
             ss.put("data", arr);
         	ExcelUtil.createExcel("e:/20180212联通音乐闻震.xlsx", ss);
		
		return "" ;
		
	}
	
	/**
	 * 联通学乐乐查询订购结果
	 * 
	 * @param pay
	 * @return
	 */
	
	
//	宁桓宇 产品ID 4900642000
//
//	Appkey 3000005529 
//
//	Secret A8ED7494F0BC20AF
	public static Gamecode Testltzy1(Payinfo pay) {

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
	/**
	 * 学乐乐模拟
	 * @param pay
	 * @return
	 */
	public static String TestXueleleMoni(Payinfo pay) {
		String cookie = "verify=0785db7a5e58daa591ae104fea5b0129;PHPSESSID=2j5rn1o6o6c882lva6fuq0n830";
		String PHPSESSID = "";
		String __hash__ = "";
		String re = "";
		Map<String,String> result = new HashMap<String,String>();  
		Map<String,String> createMap = new HashMap<String,String>();  
        createMap.put("oc_id","150");  
//        createMap.put("ocs_id","5906");  
//        createMap.put("resolution","720p");  
//        createMap.put("accountID","18658191488");
//        createMap.put("type","buy");
       //https://xuelele.10155.com/Wap/Course/info
        //https://xuelele.10155.com/Wap/Course/getSectionInfo
        //https://xuelele.10155.com/Wap/Order/buyCourse
        
     
        	 result = HttpClienthttpsUtil.doPost("https://xuelele.10155.com/Wap/Course/Info/oc_id/150",createMap, "UTF-8",cookie); 
        	 
        	   String ss = result.get("result");
        	   ss = ss.substring(ss.indexOf("user_id"));
        	   ss = ss.substring(0,ss.indexOf("\","));
        	   ss= ss.substring(ss.indexOf("\"")+1);
        	     //ss.substring( ss.indexOf("user_id");
        	 //System.out.println("result:"+ss);
        
//        result = HttpClienthttpsUtil.doPost("https://xuelele.10155.com/Wap/Course/getSectionInfo",createMap, "UTF-8",cookie); 
//        
//        JSONObject json = JSON.parseObject(result.get("result"));
//        JSONObject jsons = JSON.parseObject(json.getString("resultdata")); 
//        String videojson = jsons.getString("videoUrl");
//        
//      HttpClienthttpsUtil.doPost(videojson,createMap, "UTF-8",cookie);
//        
//      HttpClienthttpsUtil.doPost("https://xuelele.10155.com/Wap/Index/pullNotify",createMap, "UTF-8",cookie);  
//      createMap.put("event","0");  
//      createMap.put("login_type","0");  
//      createMap.put("user_id","150");  
//      createMap.put("phone","150");  
//      createMap.put("oc_name",URLEncoder.encode("快男明星刘心的欢乐弹唱音乐讲堂"));  
//      createMap.put("occ_id","150");  
//      createMap.put("occ_name",URLEncoder.encode("第1课：自我介绍及教学主题曲目《风》"));  
//      createMap.put("ocs_id","150");  
//      createMap.put("ocs_name",URLEncoder.encode("1-自我介绍-刘心"));  
//      createMap.put("freeFlow","1");  
//      createMap.put("videoUrl","videojson");  
//      createMap.put("videoSource","1");  
//      createMap.put("videoResolution","720p");  
//      createMap.put("ip","");  
//      createMap.put("videoSource","1");  
//      createMap.put("address","");  
//      createMap.put("showp","");  
//      createMap.put("lg","zh-cn");  
//      createMap.put("uuid","");  
//      HttpClienthttpsUtil.doPost("https://r.xuelele.10155.com/rapi/Rest/ActionPoint/courseVideo",createMap, "UTF-8",cookie);  
//      
//      
//      createMap.clear();
//      
//      createMap.put("oc_id", "");	 
//      createMap.put("ocs_id", "");	
//      createMap.put("time", "");	
//      createMap.put("duration", "");	
//      
//      
//      HttpClienthttpsUtil.doPost("https://xuelele.10155.com/Wap/Course/cvRecord",createMap, "UTF-8",cookie);  
//        	 
//        	 event:0
//        	 login_type:0
//        	 user_id:1174319
//        	 phone:18658191488
//        	 oc_id:150
//        	 oc_name:快男明星刘心的欢乐弹唱音乐讲堂
//        	 occ_id:1182
//        	 occ_name:第1课：自我介绍及教学主题曲目《风》
//        	 ocs_id:5906
//        	 ocs_name:1-自我介绍-刘心
//        	 freeFlow:1
//        	 videoUrl:http://xuelele.10155.com:8080/vod/mp4:path1/resource/45/720p/20171208112712_41368.720p.mp4/playlist.m3u8?wowzatokenstarttime=1517987985&wowzatokenendtime=1517995305&wowzatokenhash=iTItHl5DXKro4pf4mYr3bplraV4zpccuYTWCBN5a6d4=
//        	 videoSource:1
//        	 videoResolution:720p
//        	 ip:218.108.42.228
//        	 address:浙江省杭州市
//        	 showp:1920x1080
//        	 lg:zh-cn
//        	 uuid:64eeebd33fe5e15b-x5a7aa7e066652-1d7l9v0-08fh7f3e
//        	 
//        	 
//        	 202
//       
   
       // System.out.println("result:"+httpOrgCreateTestRtn);
        
//        for (Entry<String, String> entry : result.entrySet()) {
//              //Map.entry<Integer,String> 映射项（键-值对）  有几个方法：用上面的名字entry
//        	             //entry.getKey() ;entry.getValue(); entry.setValue();
//        	             //map.entrySet()  返回此映射中包含的映射关系的 Set视图。
//        	     cookie =  cookie+ entry.getKey()+"="+ entry.getValue()+";";
//       __hash__ 	         }
//        __hash__ = result.get("__hash__");
//        PHPSESSID = "PHPSESSID="+result.get("PHPSESSID");    
//        createMap.put("accountID","18658191488");
//        createMap.put("type","buy");
//        result =  HttpClienthttpsUtil.doPost("https://xuelele.10155.com/Wap/Order/sendVerifyCode",createMap, "UTF-8",PHPSESSID);  
//       
//        for (Entry<String, String> entry : result.entrySet()) {
//     
//	     re =  re+ entry.getKey()+"="+ entry.getValue()+";";
//	  
//	         } 
//       re =  re +PHPSESSID;
//        System.out.println("XLL_distinctid=================="+result.get("XLL_distinctid"));
      // System.out.println("re=================="+result.get("result"));
      // System.out.println("videojson=================="+videojson);
	    return "";
	}
	
	/**
	 * taobao获取省份接口
	 */
	public static String TestHttpsxll(){
		
		String PHPSESSID = "";
		String __hash__ = "";
		String re = "";
		Map<String,String> result = new HashMap<String,String>();  
		Map<String,String> createMap = new HashMap<String,String>();  
        createMap.put("oc_id","150");  
//        createMap.put("accountID","18658191488");
//        createMap.put("type","buy");
       //https://xuelele.10155.com/Wap/Course/info
        //https://xuelele.10155.com/Wap/Course/getSectionInfo
        //https://xuelele.10155.com/Wap/Order/buyCourse
        result = HttpClienthttpsUtil.doPost("https://xuelele.10155.com/Wap/Order/buyCourse",createMap, "UTF-8",null);  
       // System.out.println("result:"+httpOrgCreateTestRtn);
        
//        for (Entry<String, String> entry : result.entrySet()) {
//              //Map.entry<Integer,String> 映射项（键-值对）  有几个方法：用上面的名字entry
//        	             //entry.getKey() ;entry.getValue(); entry.setValue();
//        	             //map.entrySet()  返回此映射中包含的映射关系的 Set视图。
//        	     cookie =  cookie+ entry.getKey()+"="+ entry.getValue()+";";
//       __hash__ 	         }
        __hash__ = result.get("__hash__");
        PHPSESSID = "PHPSESSID="+result.get("PHPSESSID");    
        createMap.put("accountID","18658191488");
        createMap.put("type","buy");
        result =  HttpClienthttpsUtil.doPost("https://xuelele.10155.com/Wap/Order/sendVerifyCode",createMap, "UTF-8",PHPSESSID);  
       
        for (Entry<String, String> entry : result.entrySet()) {
     
	     re =  re+ entry.getKey()+"="+ entry.getValue()+";";
	  
	         } 
       re =  re +PHPSESSID;
       
       System.out.println("re=================="+re);
       System.out.println("__hash__=================="+__hash__);
	    return "";
	
	}
	
	
	/**
	 * taobao获取省份接口
	 */
	public static String TestHttpsxllconfirm(){
		String cookie = "verify=0785db7a5e58daa591ae104fea5b0129;PHPSESSID=2j5rn1o6o6c882lva6fuq0n830";
		Map<String,String> result = new HashMap<String,String>();  
		Map<String,String> createMap = new HashMap<String,String>();  
        createMap.put("oc_id","150");  
//        createMap.put("accountID","18658191488");
//        createMap.put("verifyCode","6062");
//        createMap.put("__hash__","f168c40a15929076ecf3ff9d0f23050a_7821644378f4fe8e96bb9a30cf61a65c");
//       
       // result = HttpClienthttpsUtil.doPost("https://xuelele.10155.com/Wap/Order/directTCPay",createMap, "utf-8",cookie);  
        
        
        
        
        result = HttpClienthttpsUtil.doPost("https://xuelele.10155.com/Wap/Course/Info/oc_id/150",createMap, "utf-8",cookie);  
        
        
       // System.out.println("result:"+httpOrgCreateTestRtn);
        
        for (Entry<String, String> entry : result.entrySet()) {
            System.out.println(entry.getKey()+"=================="+entry.getValue());
   	   //  re =  re+ entry.getKey()+"="+ entry.getValue()+";";
   	  
   	         }   
    
	    return "";
	
	}
	/**
	
	/**
	 * taobao获取省份接口
	 */
	public static String getPhoneprov(){
		String str =Pingyin.getAllFirstLetter("内蒙古").toUpperCase();
		
		 
		 if(str!=null&&str.length()>2){ 
			 str = str.substring(0, 2);
		 }
	    return str;
	
	}
	/**
	 * 测试学乐乐验证码下发
	 * @param pay
	 */
	public static void TestXuelele(Payinfo pay){
		JSONObject json = new JSONObject();
		Map m = new HashMap();
		String str = "";
		Gamecode code = new Gamecode();
//		if(pay.getPhone() == null || "".equals(pay.getPhone())){
//			code.setPayType("0");
//			return code;
//		}
		String oc_id = "";
		int cost = pay.getCost();
//		if(cost == 10){
//			oc_id = "119";
//		}else if(cost == 15){
//			oc_id = "117";
//		}
		Cookie[] c= null;
		String[] strArray={"Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10_6_8; en-us) AppleWebKit/534.50 (KHTML, like Gecko) Version/5.1 Safari/534.50",
				"Mozilla/5.0 (Windows; U; Windows NT 6.1; en-us) AppleWebKit/534.50 (KHTML, like Gecko) Version/5.1 Safari/534.50",
				"Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0",
				"Mozilla/5.0 (Macintosh; Intel Mac OS X 10.6; rv:2.0.1) Gecko/20100101 Firefox/4.0.1",
				"Mozilla/5.0 (Windows NT 6.1; rv:2.0.1) Gecko/20100101 Firefox/4.0.1",
				"Opera/9.80 (Macintosh; Intel Mac OS X 10.6.8; U; en) Presto/2.8.131 Version/11.11",
				"Opera/9.80 (Windows NT 6.1; U; en) Presto/2.8.131 Version/11.11",
				"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_0) AppleWebKit/535.11 (KHTML, like Gecko) Chrome/17.0.963.56 Safari/535.11",
				"Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; TencentTraveler 4.0)",
				"Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; Trident/4.0; SE 2.X MetaSr 1.0; SE 2.X MetaSr 1.0; .NET CLR 2.0.50727; SE 2.X MetaSr 1.0)",
				"Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; 360SE)",
				"Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; Trident/4.0; InfoPath.2; .NET4.0C; .NET4.0E; .NET CLR 2.0.50727; 360SE)",
				"Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; Trident/4.0; SE 2.X MetaSr 1.0; SE 2.X MetaSr 1.0; .NET CLR 2.0.50727; SE 2.X MetaSr 1.0)",
				"Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; Trident/4.0; TencentTraveler 4.0; .NET CLR 2.0.50727)",
				"Mozilla/5.0 (Windows NT 6.1; rv:2.0.1) Gecko/20100101 Firefox/4.0.1",
				"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36"
				};
		
		java.util.Random random=new java.util.Random();// 定义随机类
		int result=random.nextInt(15);
		String browser = "";
		pay.setBsc_lac(browser);
		browser = strArray[result];

		try {
			NameValuePair[] pair = new NameValuePair[1];
		
			pair[0] = new NameValuePair("oc_id", oc_id);
		
			m = HttpPost.mobileHttpGets("https://xuelele.10155.com/Wap/Order/buyCourse",
							pair,null,0,oc_id);
			if(m.containsKey("cookies")){
				c = (Cookie[]) m.get("cookies");
			}		
			pay.setRe(str);
		} catch (Exception e) {
		}
		try {
			NameValuePair[] pair = new NameValuePair[3];
			pair[0] = new NameValuePair("accountID", pay.getPhone());
			pair[1] = new NameValuePair("oc_id", oc_id);	
			pair[2] = new NameValuePair("type", "buy");		

	//	https://xuelele.10155.com/Wap/Order/sendVerifyCode
			m = HttpPost.mobileHttpGets(
							"https://xuelele.10155.com/Wap/Order/sendVerifyCode",pair,c,1,oc_id);
			if(m.containsKey("cookies")){
				c = (Cookie[]) m.get("cookies");
				for (int i = 0; i < c.length; i++) {
					m.put(c[i].getName(), c[i].getValue());
				}
			}
			
			if(m.containsKey("PHPSESSID") && m.containsKey("verify")){
				str = "PHPSESSID="+m.get("PHPSESSID")+";verify="+m.get("verify");
			}
			System.out.println(str);
			code.setSmsPort("106910719154");
			code.setPbPort("106910719154");
			code.setPbPortA("10010");
			code.setPbContent("办理成功");
			code.setRe(str);	
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 米粒天翼阅读验证码下发
	 */
	public static String Testmltyyd(Payinfo pay){
		
        Gamecode code = new Gamecode();
      String str = "";
      String requrl = "http://p.miliroom.com:9500/SmsPayServer/apipay/getpassage";
		
      String timestr = (new Date()).getTime()+"";
      
        String prov = LpTools.getPhoneprov(pay.getPhone());
        
		NameValuePair[] pair = new NameValuePair[14];
		pair[0] = new NameValuePair("provider","3");
		pair[1] = new NameValuePair("appid","21843300");
		pair[2] = new NameValuePair("cpid","11296");
		pair[3] = new NameValuePair("price","1500");
		pair[4] = new NameValuePair("timestamp",timestr);
		pair[5] = new NameValuePair("province",prov);
		pair[6] = new NameValuePair("phonenum",pay.getPhone());
		pair[7] = new NameValuePair("imsi",pay.getImsi());
		pair[8] = new NameValuePair("imei",pay.getImei());
		pair[9] = new NameValuePair("appname","test");
		pair[10] = new NameValuePair("itemname","test");
		pair[11] = new NameValuePair("userip","127.0.0.1");
		pair[12] = new NameValuePair("cporderno",pay.getOrderId());
		pair[13] = new NameValuePair("channelcode","1");
	
		try {
			str = HttpPost.musicHttpGets(requrl, pair);
			//System.out.println(str);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		code.setRe(str);

	return str;	
		
	}
	/**
	 * 测试 获取代码接口
	 */
	public static String TestgetPay(String requrl){

      String str = "";
		
		NameValuePair[] pair = new NameValuePair[9];
		pair[0] = new NameValuePair("imsi","460036581140346");
		pair[1] = new NameValuePair("imei", "A00000556361E0");	
		pair[2] = new NameValuePair("iccid", "89860315045713904379");
		pair[3] = new NameValuePair("phone", "18658191488");
		pair[4] = new NameValuePair("cost", "15");
		pair[5] = new NameValuePair("appId", "1001");
		pair[6] = new NameValuePair("productId", "50012");
		pair[7] = new NameValuePair("cpparam", "newtest");
		pair[8] = new NameValuePair("ip", "218.108.42.228");
		try {
			str = HttpPost.mobileHttpGet(requrl, pair);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	return str;	
		
	}
	
	
	/**
	 * 测试 获取代码接口
	 */
	public static String TestData(String requrl){
		
      String str = "";
		
		NameValuePair[] pair = new NameValuePair[2];
		pair[0] = new NameValuePair("starttime","2018-01-10");
		pair[1] = new NameValuePair("appid","1002");
	
		try {
			str = HttpPost.mobileHttpGet(requrl, pair);
			
			
			JSONObject json = JSON.parseObject(str);
			
			ExcelUtil.createExcel("e:/201801音乐鼎发渠道.xlsx", json);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	return str;	
		
	}
	
	
	/**
	 * 测试 获取代码接口
	 */
	public static String TestRd(String requrl){

      String str = "";
     // ={"mobile":"15355434975","orderid":"phoneOrder15355434975","result":"0"}
    //  {"mobile":"15391012617","orderid":"phoneOrder15391012617","result":"succ"}

		NameValuePair[] pair = new NameValuePair[2];
		pair[0] = new NameValuePair("mobile","15391012617");
		pair[1] = new NameValuePair("state", "1");	

		try {
			str = HttpPost.mobileHttpGet(requrl, pair);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	return str;	
		
	}
	
	
	/**
	 * 测试 李鹏qq接口
	 */
	public static String TestLPqq(String requrl){
		Random random = new Random();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss"
				+ random.nextInt(1000000));
		String orderId = sdf.format(new Date());
      String str = "";
      String sign = "4016333001"+orderId+"x1k4pfuo6g";
      
       sign =   LpTools.getMD5(sign).toLowerCase();
		
		NameValuePair[] pair = new NameValuePair[5];
		pair[0] = new NameValuePair("channelId","30");
		pair[1] = new NameValuePair("ProductID", "01");	
		pair[2] = new NameValuePair("Account", "401633");
		pair[3] = new NameValuePair("OrderId", orderId);
		pair[4] = new NameValuePair("skey", sign);
		try {
			str = HttpPost.mobileHttpGet(requrl, pair);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	return str;	
		
	}
	
	/**
	 * 
	 */
	public static String TestLMsdk(){
		
		
		String str = "";
		
		Userinfo user  = new Userinfo();
		user.setIccid("89860315045713904379");
		user.setImei("A00000556361E0");
		user.setImsi("460036581140345");
		user.setAppId("test002");
		user.setPhone("18967199935");
		user.setProductId("1012");
		
	
		//115.29.160.68/sdk/getPay?payType=141&appId=5003&productId=6003&cost=15&phone=18658191488&imsi=460017881979441&imei=866046025781192&iccid=89860113917520369315&ip=120.199.11.146
		NameValuePair[] pair = new NameValuePair[2];
		pair[0] = new NameValuePair("userInfo",	JSON.toJSONString(user));
		pair[1] = new NameValuePair("num", "10");	
		
		try {
			str = HttpPost.mobileHttpGets("http://115.29.160.68/sdk/sdkpay", pair);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	return str;		
		
		
		
	}
	
	/**
	 * 测试 提交验证码接口
	 */
	public static String Testdyorder(String requrl){

      String str = "";
		
		NameValuePair[] pair = new NameValuePair[2];
		pair[0] = new NameValuePair("orderId","20180119052551857312");
		pair[1] = new NameValuePair("authCode", "931294");	
	
		try {
			str = HttpPost.mobileHttpGet(requrl, pair);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	return str;	
		
	}
	
	
	
	/**
	 * 测试 统计接口
	 */
	public static String TestTONGJI(String requrl){

		
		 String retime = "2017-11-10".replace("-", "");
		 System.out.println(retime);
		
		
      String str = "";
		
		NameValuePair[] pair = new NameValuePair[4];
		pair[0] = new NameValuePair("starttime","2017-11-10");
		pair[1] = new NameValuePair("productId", "");	
		pair[2] = new NameValuePair("codeType", "");	
		pair[3] = new NameValuePair("yystype", "");	
	
		try {
			str = HttpPost.mobileHttpGet(requrl, pair);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	return str;	
		
	}
	
	/**
	 * 测试 提交验证码接口
	 */
	public static String TestMC(String requrl){

     String str = "";
//      String soap = "Sequence_Id=1,Result=0";
//      String rgex = "=(.*?),";
//     System.out.println(LpTools.getSubUtilSimple(soap, rgex));
		
		NameValuePair[] pair = new NameValuePair[3];
		pair[0] = new NameValuePair("phone","18691455610");
		pair[1] = new NameValuePair("serviceType","WDM207");
		pair[2] = new NameValuePair("fee","001000");

	
		try {
			str = HttpPost.mobileHttpGet(requrl, pair);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	return str;	
		
	}
	/**
	 * 测试余露代码
	 */
	public static String TestYLDM(String requrl){

      String str = "";
		
		NameValuePair[] pair = new NameValuePair[4];
		pair[0] = new NameValuePair("mobile","18658191488");
		pair[1] = new NameValuePair("state","1");
		pair[2] = new NameValuePair("spnumber","1222222");
		pair[3] = new NameValuePair("content","Z20");
		try {
			str = HttpPost.mobileHttpGet(requrl, pair);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	return str;	
		
	}
	
	
	public static Gamecode lzydDDOxl(Payinfo pay) {
		JSONObject json = new JSONObject();
		Gamecode code = new Gamecode();
		String str = "";
		int cost = pay.getCost();
		String charge_point_id = "";
		if(cost == 15){
			charge_point_id = "10255";
		}else if(cost == 20){
			charge_point_id = "10255";
		}
		NameValuePair[] pair = new NameValuePair[10];
		pair[0] = new NameValuePair("client_key","8bc56cf0bafb2650146f3e48cb85d257");
		pair[1] = new NameValuePair("charge_point_id", charge_point_id);
		pair[2] = new NameValuePair("mobile", pay.getPhone());
		pair[3] = new NameValuePair("imsi", pay.getImsi());
		pair[4] = new NameValuePair("imei", pay.getImei());
		pair[5] = new NameValuePair("iccid", pay.getIccid());
		pair[6] = new NameValuePair("ip", pay.getIp());
		pair[7] = new NameValuePair("lac", pay.getBsc_lac());
		pair[8] = new NameValuePair("ua", "vivo_vivo_vivo");
		pair[9] = new NameValuePair("cid", pay.getBsc_cid());
		try {
			str = HttpPost.mobileHttpGet(
							"http://114.55.32.149/sp-service/lianZhuSpService/createOrder",
							pair);
		json = JSON.parseObject(str);
		System.out.println(str);	
			if("0".equals(json.getString("code"))){
	        code.setSmsContent(json.getString("message_content"));
	         code.setSmsPort(json.getString("sender_number"));
				code.setPbPort("10658077");
				code.setPbPortA("10086");
				code.setPbContent("品牌合作业务");
			    code.setPayType("1");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return code;
	}
	
	/**
	 * 测试 愛音樂提交订单接口
	 */
	public static String TestMusicOrder(Payinfo pay){
//		
//		DEVICEID   1000010408540
//
//		DEVICE_SECRET  u6gWn4K6rgZA
//		
//		data = auth-device_id+auth-channelid+auth-time_stamp+id+idtype+format

      String str = "";
     // String requrl = "http://api.118100.cn/openapi/services/v2/package/packageservice/emplanunched.json";
      String requrl =  "http://api.118100.cn/openapi/services/v2/package/packageservice/emplanunched.json";
		NameValuePair[] pair = new NameValuePair[3];
		pair[0] = new NameValuePair("mdn",pay.getPhone());
		pair[1] = new NameValuePair("package_id","135000000000000246502");
		pair[2] = new NameValuePair("column","111");

	
		try {
			str = HttpPost.musicHttpGets(requrl, pair);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	return str;	
		
	}
	
	/**
	 * 测试 愛音樂提交验证码接口
	 */
	public static String TestMusicOrderConfirm(Payinfo pay,String authCode){

      String str = "";
      String requrl = "http://api.118100.cn/openapi/services/v2/package/packageservice/subscribebyemp.json";
		
		NameValuePair[] pair = new NameValuePair[4];
		pair[0] = new NameValuePair("mdn",pay.getPhone());
		pair[1] = new NameValuePair("package_id","135000000000000246502");
		pair[2] = new NameValuePair("random_key",authCode);
		pair[3] = new NameValuePair("column","111");
		
	
		try {
			str = HttpPost.musicHttpGets(requrl, pair);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	return str;	
		
	}
	/**
	 * 
	 * @author think
	 *
	 */
	

	

	
	
	 /**
	  * 
	  * @param pay
	  * @return
	  */
		public static String dxyzf(Payinfo pay){
			
	
			//https://webpaywg.bestpay.com.cn/verifyCode.do?ORDERSEQ=20171218042804993910&TELEPHONE=15356719070&MERCHANTID=2420201040589620&FUNCTIONTYPE=1&ORDERAMOUNT=1&ORDERREQTRANSEQ=20171218042804993910001&MAC=66C07BD9370BEFDD3E9227078615FEB7

			String str = "";
		        String URL = "https://webpaywg.bestpay.com.cn/verifyCode.do";    //接口地址
		        String ORDERSEQ = pay.getOrderId(); //订单号
		        String ORDERREQTRANSEQ = pay.getOrderId()+"001";//订单请求支付流水号
		        String TELEPHONE = pay.getPhone();//手机号
		        String MERCHANTID = "02420201040589625";//商户代码
		        String FUNCTIONTYPE = "1";//功能类型
		        String ORDERAMOUNT = pay.getCost()+"";//金额
		        String keyString = "4DB0E1D95542C1C4D91E223CFA91F0D43EB851BA9C199313";//数据key

		        StringBuilder sb = new StringBuilder();//组装mac加密明文串
		        sb.append("MERCHANTID=").append(MERCHANTID);
		        sb.append("&ORDERSEQ=").append(ORDERSEQ);
		        sb.append("&ORDERREQTRANSEQ=").append(ORDERREQTRANSEQ);
		        sb.append("&TELEPHONE=").append(TELEPHONE);
		        sb.append("&KEY=").append(keyString);//此处是商户的key
		        System.out.println("mac原串：" + sb);

		        String mac = (LpTools.getMD5(sb.toString())).toUpperCase();//进行md5加密(商户自己封装MD5加密工具类，此处只提供参考)
		        System.out.println("mac:" + mac);

		        //组装请求参数
		        StringBuilder param = new StringBuilder();
		        param.append("ORDERSEQ=").append(ORDERSEQ)
		                .append("&ORDERREQTRANSEQ=").append(ORDERREQTRANSEQ)
		                .append("&TELEPHONE=").append(TELEPHONE)
		                .append("&MERCHANTID=").append(MERCHANTID)
		                .append("&FUNCTIONTYPE=").append(FUNCTIONTYPE)
		                .append("&ORDERAMOUNT=").append("ORDERAMOUNT")
		                .append("&MAC=").append(mac);
		    	NameValuePair[] pair = new NameValuePair[7];
				pair[0] = new NameValuePair("ORDERSEQ",ORDERSEQ);
				pair[1] = new NameValuePair("TELEPHONE", TELEPHONE);	  
				pair[2] = new NameValuePair("MERCHANTID",MERCHANTID);
				pair[3] = new NameValuePair("FUNCTIONTYPE", FUNCTIONTYPE);	
				pair[4] = new NameValuePair("ORDERAMOUNT", ORDERAMOUNT);
				pair[5] = new NameValuePair("ORDERREQTRANSEQ", ORDERREQTRANSEQ);	
				pair[6] = new NameValuePair("MAC", mac);
				try {
					str =HttpPost.mobileHttpGet(URL, pair);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		
		   return str;

		}
		
	    /**
	     * 以行为单位读取文件，常用于读面向行的格式化文件
	     */
	    public static List readFileByLines(String fileName) {
	    	List<String> list =new  ArrayList<String> ();
	        File file = new File(fileName);
	        BufferedReader reader = null;
	        try {
	            System.out.println("以行为单位读取文件内容，一次读一整行：");
	            reader = new BufferedReader(new FileReader(file));
	            String tempString = null;
	            int line = 1;
	            // 一次读入一行，直到读入null为文件结束
	            while ((tempString = reader.readLine()) != null) {
	                // 显示行号
	               // System.out.println("line " + line + ": " + tempString);
	            	list.add(tempString);
	                line++;
	            }
	            reader.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        } finally {
	            if (reader != null) {
	                try {
	                    reader.close();
	                } catch (IOException e1) {
	                }
	            }
	        }
	   return list; }


	        /**
	         * 向文件中写入内容
	         * @param filepath 文件路径与名称
	         * @param newstr  写入的内容
	         * @return
	         * @throws IOException
	         */
	        public static boolean writeFileContent(String filepath,String newstr) throws IOException{
	            Boolean bool = false;
	            String filein = newstr+"\r\n";//新写入的行，换行
	            String temp  = "";
	            
	            FileInputStream fis = null;
	            InputStreamReader isr = null;
	            BufferedReader br = null;
	            FileOutputStream fos  = null;
	            PrintWriter pw = null;
	            try {
	                File file = new File(filepath);//文件路径(包括文件名称)
	                //将文件读入输入流
	                fis = new FileInputStream(file);
	                isr = new InputStreamReader(fis);
	                br = new BufferedReader(isr);
	                StringBuffer buffer = new StringBuffer();
	                
	                //文件原有内容
	                for(int i=0;(temp =br.readLine())!=null;i++){
	                    buffer.append(temp);
	                    // 行与行之间的分隔符 相当于“\n”
	                    buffer = buffer.append(System.getProperty("line.separator"));
	                }
	                buffer.append(filein);
	                
	                fos = new FileOutputStream(file);
	                pw = new PrintWriter(fos);
	                pw.write(buffer.toString().toCharArray());
	                pw.flush();
	                bool = true;
	            } catch (Exception e) {
	                // TODO: handle exception
	                e.printStackTrace();
	            }finally {
	                //不要忘记关闭
	                if (pw != null) {
	                    pw.close();
	                }
	                if (fos != null) {
	                    fos.close();
	                }
	                if (br != null) {
	                    br.close();
	                }
	                if (isr != null) {
	                    isr.close();
	                }
	                if (fis != null) {
	                    fis.close();
	                }
	            }
	            return bool;
	        }
		
		
		public static String ldxyzfConfirm(Payinfo pay,String authCode){
			//Gamecode code = new Gamecode();
			String str = "";
			 String URL = "https://webpaywg.bestpay.com.cn/backBillPay.do";    //接口地址
		        String MERCHANTID = "02420201040589625";//商户代码
		        String SUBMERCHANTID = "";//商户子代码
		        String MERCHANTPWD = "114628";//商户密码，交易key
		        String MERCHANTPHONE = "4008011888";//商户客服电话
		        String ORDERSEQ = pay.getOrderId(); //订单号
		        String ORDERREQTRANSEQ = pay.getOrderId()+"001";//订单请求支付流水号
		        String ORDERAMOUNT = pay.getCost()+"";//金额
		        String ORDERREQTIME =LpTools.getCurrentDate();//订单请求时间
		        String USERACCOUNT = pay.getPhone();//用户账号
		        String USERIP = pay.getIp();//用户IP
		        String PHONENUM = pay.getPhone();//扣费号码
		        String VERIFYCODE = authCode;//短信验证码
		        String GOODPAYTYPE = "0";//商品付费类型
		        String GOODSCODE = "QIANWE";//商品编码
		        String GOODSNAME = URLEncoder.encode("测试商品");//商品名称
		        String GOODSNUM = "1";//商品数量
		        String ORDERDESC =  URLEncoder.encode("测试");//订单描述
		        String ATTACH = "";//附加信息
		        String BACKMERCHANTURL = "http://127.0.0.1";//支付结果通知地址
		        String keyString = "4DB0E1D95542C1C4D91E223CFA91F0D43EB851BA9C199313";//数据key

		        StringBuilder sb = new StringBuilder();//组装mac加密明文串
		        sb.append("MERCHANTID=").append(MERCHANTID);
		        sb.append("&MERCHANTPWD=").append(MERCHANTPWD);
		        sb.append("&ORDERSEQ=").append(ORDERSEQ);
		        sb.append("&ORDERREQTRANSEQ=").append(ORDERREQTRANSEQ);
		        sb.append("&ORDERREQTIME=").append(ORDERREQTIME);
		        sb.append("&ORDERAMOUNT=").append(ORDERAMOUNT);
		        sb.append("&USERACCOUNT=").append(USERACCOUNT);
		        sb.append("&USERIP=").append(USERIP);
		        sb.append("&PHONENUM=").append(PHONENUM);
		        sb.append("&GOODPAYTYPE=").append(GOODPAYTYPE);
		        sb.append("&GOODSCODE=").append(GOODSCODE);
		        sb.append("&GOODSNUM=").append(GOODSNUM);
		        sb.append("&KEY=").append(keyString);//此处是商户的key
		        System.out.println("mac原串：" + sb);
		        String mac = (LpTools.getMD5(sb.toString())).toUpperCase();//进行md5加密(商户自己封装MD5加密工具类，此处只提供参考)
		        //String mac = CryptTool.md5Digest(sb.toString());//进行md5加密(商户自己封装MD5加密工具类，此处只提供参考)
		        System.out.println("mac:" + mac);

//		        //组装请求参数
//		        StringBuilder param = new StringBuilder();
//		        param.append("MERCHANTID=").append(MERCHANTID)
//		                .append("&SUBMERCHANTID=").append(SUBMERCHANTID)
//		                .append("&MERCHANTPWD=").append(MERCHANTPWD)
//		                .append("&MERCHANTPHONE=").append(MERCHANTPHONE)
//		                .append("&ORDERSEQ=").append(ORDERSEQ)
//		                .append("&ORDERREQTRANSEQ=").append(ORDERREQTRANSEQ)
//		                .append("&ORDERAMOUNT=").append(ORDERAMOUNT)
//		                .append("&ORDERREQTIME=").append(ORDERREQTIME)
//		                .append("&USERACCOUNT=").append(USERACCOUNT)
//		                .append("&USERIP=").append(USERIP)
//		                .append("&PHONENUM=").append(PHONENUM)
//		                .append("&VERIFYCODE=").append(VERIFYCODE)
//		                .append("&GOODPAYTYPE=").append(GOODPAYTYPE)
//		                .append("&GOODSCODE=").append(GOODSCODE)
//		                .append("&GOODSNAME=").append(GOODSNAME)
//		                .append("&GOODSNUM=").append(GOODSNUM)
//		                .append("&ORDERDESC=").append(ORDERDESC)
//		                .append("&ATTACH=").append(ATTACH)
//		                .append("&BACKMERCHANTURL=").append(BACKMERCHANTURL)
//		                .append("&MAC=").append(mac);

		    	NameValuePair[] pair = new NameValuePair[20];
				pair[0] = new NameValuePair("MERCHANTID",MERCHANTID);
				pair[1] = new NameValuePair("SUBMERCHANTID", SUBMERCHANTID);
				pair[2] = new NameValuePair("MERCHANTPWD", MERCHANTPWD);	
				pair[3] = new NameValuePair("MERCHANTPHONE", MERCHANTPHONE);	
				pair[4] = new NameValuePair("ORDERSEQ", ORDERSEQ);	
				pair[5] = new NameValuePair("ORDERREQTRANSEQ", ORDERREQTRANSEQ);	
				pair[6] = new NameValuePair("ORDERAMOUNT", ORDERAMOUNT);
				pair[7] = new NameValuePair("ORDERREQTIME", ORDERREQTIME);	
				pair[8] = new NameValuePair("USERACCOUNT",USERACCOUNT);
				pair[9] = new NameValuePair("USERIP", USERIP);	
				pair[10] = new NameValuePair("PHONENUM", PHONENUM);
				pair[11] = new NameValuePair("VERIFYCODE", VERIFYCODE);	
				pair[12] = new NameValuePair("GOODPAYTYPE", GOODPAYTYPE);	
				pair[13] = new NameValuePair("GOODSCODE", GOODSCODE);	
				pair[14] = new NameValuePair("GOODSNAME", GOODSNAME);	
				pair[15] = new NameValuePair("GOODSNUM", GOODSNUM);	
				pair[16] = new NameValuePair("ORDERDESC", ORDERDESC);	
				pair[17] = new NameValuePair("ATTACH", ATTACH);	
				pair[18] = new NameValuePair("BACKMERCHANTURL", BACKMERCHANTURL);	
				pair[19] = new NameValuePair("MAC", mac);
				try {
					str = HttpPost.mobileHttpGet(URL, pair);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
			
			
			
			
			   return str;
			   }

}
