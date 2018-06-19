package com.xh.sdk.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.apache.commons.httpclient.NameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.core.sgip.client.SGIPClient;
import com.huawei.smproxy.SGIPSMProxy;
import com.huawei.smproxy.comm.sgip.message.SGIPSubmitMessage;
import com.huawei.smproxy.comm.sgip.message.SGIPSubmitRepMessage;
import com.huawei.smproxy.util.Args;
import com.xh.sdk.common.HttpClienthttpsUtil;
import com.xh.sdk.common.HttpPost;
import com.xh.sdk.common.LpTools;
import com.xh.sdk.common.Mt;
import com.xh.sdk.common.SGIPSender;
import com.xh.sdk.common.memcached;
import com.xh.sdk.dao.userDao;
import com.xh.sdk.model.BillingCode;
import com.xh.sdk.model.Gamecode;
import com.xh.sdk.model.LtdmProductList;
import com.xh.sdk.model.Payinfo;
import com.xh.sdk.redis.RedisClientTemplate;

@Service
@Transactional(value = TxType.NOT_SUPPORTED)
public class cpCodeService {
	@Autowired
	private HttpPost httppost;

	@Autowired
	private LpTools lptools;

	@Autowired
	private userDao dao;

	@Autowired
	private memcached mc;

	@Autowired
	private RedisClientTemplate redis;

	public String LtdmSubmit(Payinfo pay, LtdmProductList pro) {

		//String sendType = "bysubmit";
		String str = "";
		List<String> listUserNumber = new ArrayList<String>();
		listUserNumber.add(pay.getPhone());

		SGIPClient client = new SGIPClient();
		
		// ServiceType=ABCD，MoMtFlag=0，FeeType=2，FeeValue=**
		try {
			
			if ("1".equals(pro.getPayType())) {

				str = this.NewdbSumbit(pay,pro);
				
				if("error".equals(str)){
					str = this.NewdbSumbit(pay,pro);
				}

			}else{
				str = this.NewBySumbit(pay);
				if("error".equals(str)){
					str = this.NewBySumbit(pay);
				}
			
				
			}

			
			
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 30731902143021521401
		// 30731902143021521401

		return str;
	}

	/**
	 * 新增包月用户sumbit提交话单方法
	 * 
	 * @return
	 */

	public String NewBySumbit(Payinfo pay) {
		
		
		

		
		
		
		System.out.println("======================by ti jiao================================");
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
		int MorelatetoMTFlag = 0;
		int Priority = 0;
		Date ExpireTime = null;
		Date ScheduleTime = null;
		int ReportFlag = 1;
		int TP_pid = 0;
		int TP_udhi = 0;
		int MessageCoding = 8;
		int MessageType = 0;
		SGIPSubmitRepMessage returnMsg = null;

	
		try {
			byte[] MessageContent = "this is test message!".getBytes();
			int MessageLen = MessageContent.length;
			String reserve = null;
			if (pay.getPayId() != null && !"".equals(pay.getPayId())) {
				reserve = pay.getPayId();

			}

			SGIPSubmitMessage msg = null;
			msg = new SGIPSubmitMessage(SPNumber, ChargeNumber, UserNumber, CorpId,
					ServiceType, FeeType, FeeValue, GivenValue, AgentFlag,
					MorelatetoMTFlag, Priority, ExpireTime, ScheduleTime,
					ReportFlag, TP_pid, TP_udhi, MessageCoding, MessageType,
					MessageLen, MessageContent, reserve);
			System.out.println("submit ========================"+msg.toString());
			
			returnMsg = sender.send(msg);
	//		returnMsg = (SGIPSubmitRepMessage) sgipsmp.send(msg);

			if (null == returnMsg) {
				System.out.println("send message failed!");
				return "error";
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

	/**
	 * 新增点播用户sumbit提交话单方法
	 * 
	 * @return
	 */

	public String NewdbSumbit(Payinfo pay,LtdmProductList pro) {
		
		System.out.println("======================db ti jiao========================");
		String str = "";
		String feevalue = pay.getCost() + "";

		switch (feevalue.length()) {
		case 1:
			feevalue = "00000" + feevalue;
			break;
		case 2:
			feevalue = "0000" + feevalue;
			break;
		case 3:
			feevalue = "000" + feevalue;
			break;
		case 4:
			feevalue = "00" + feevalue;
			break;
		case 5:
			feevalue = "0" + feevalue;
			break;

		default:
			break;
		}

		SGIPSender sender = SGIPSender.getInstance();

		String SPNumber = pay.getProductId();
		String ChargeNumber = pay.getPhone();
		String[] UserNumber = { pay.getPhone() };
		String CorpId = "90214";
		String ServiceType = pay.getIccid();
		int FeeType = 2;
		String FeeValue = feevalue;
		String GivenValue = "0";
		int AgentFlag = 0;
		int MorelatetoMTFlag = 0;
		int Priority = 0;
		Date ExpireTime = null;
		Date ScheduleTime = null;
		int ReportFlag = 1;
		int TP_pid = 0;
		int TP_udhi = 0;
		int MessageCoding = 15;
		int MessageType = 0;

		SGIPSubmitRepMessage returnMsg = null;

		try {

			byte[] MessageContent = pro.getXxmassages().getBytes("gbk");
			int MessageLen = MessageContent.length;

			String reserve = null;
			if (pay.getPayId() != null && !"".equals(pay.getPayId())) {
				reserve = pay.getPayId();

			}

			SGIPSubmitMessage msg = null;
			msg = new SGIPSubmitMessage(SPNumber, ChargeNumber, UserNumber, CorpId,
					ServiceType, FeeType, FeeValue, GivenValue, AgentFlag,
					MorelatetoMTFlag, Priority, ExpireTime, ScheduleTime,
					ReportFlag, TP_pid, TP_udhi, MessageCoding, MessageType,
					MessageLen, MessageContent, reserve);
			System.out.println("submit ========================"+msg.toString());
			returnMsg = sender.send(msg);

			if (null == returnMsg) {
				System.out.println("send message failed!");
				return "error";
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

	public String LtdmNewSubmit(Payinfo pay, LtdmProductList pro) {
		String str = "";
		//
		// String sendType = "bysubmit";
		// List<String> listUserNumber = new ArrayList<String>();
		// listUserNumber.add(pay.getPhone());
		//
		// SGIPClient client = new SGIPClient();
		// if("1".equals(pro.getPayType())){
		//
		// sendType = pro.getFeevalue();
		//
		// }
		// Mt mt = new Mt();
		// try {
		// mt.send(pay.getPhone(), pay.getProductId(), pay.getIccid(),
		// pay.getPayId(), sendType,pay.getCpparam());
		// } catch (UnsupportedEncodingException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

		return str;
	}

	/**
	 * 联通中音代码触发验证码
	 * 
	 * @param pay
	 * @return
	 */
	public Gamecode ltzy(Payinfo pay, BillingCode jfcode) {
		Gamecode code = new Gamecode();
		// JSONObject json = new JSONObject();
		// Map m = new HashMap();
		// String str = "";
		//
		// if(pay.getPhone() == null || "".equals(pay.getPhone())){
		// code.setPayType("0");
		// return code;
		// }
		// String oc_id = "";
		// int cost = pay.getCost();
		// if(cost == 10){
		// oc_id = "119";
		// }else if(cost == 15){
		// oc_id = "150";
		// }
		// String PHPSESSID = "";
		// String __hash__ = "";
		// String re = "";
		// Map<String,String> result = new HashMap<String,String>();
		// Map<String,String> createMap = new HashMap<String,String>();
		// createMap.put("oc_id","150");
		// result =
		// HttpClienthttpsUtil.doPost("https://xuelele.10155.com/Wap/Order/buyCourse",createMap,
		// "UTF-8",null);
		// __hash__ = result.get("__hash__");
		// pay.setUa(__hash__);
		// PHPSESSID = "PHPSESSID="+result.get("PHPSESSID");
		//
		// createMap.put("accountID",pay.getPhone());
		// createMap.put("type","buy");
		// result =
		// HttpClienthttpsUtil.doPost("https://xuelele.10155.com/Wap/Order/sendVerifyCode",createMap,
		// "UTF-8",PHPSESSID);
		//
		// for (Entry<String, String> entry : result.entrySet()) {
		//
		// re = re+ entry.getKey()+"="+ entry.getValue()+";";
		//
		// }
		// re = re +PHPSESSID;
		// pay.setRe(re);

		String PHPSESSID = "";
		String __hash__ = "";
		String re = "";
		Map<String, String> result = new HashMap<String, String>();
		Map<String, String> createMap = new HashMap<String, String>();
		createMap.put("oc_id", jfcode.getConsumecode());
		result = HttpClienthttpsUtil.doPost(
				"https://xuelele.10155.com/Wap/Order/buyCourse", createMap,
				"UTF-8", null);
		__hash__ = result.get("__hash__");
		pay.setUa(__hash__);
		pay.setPayId(jfcode.getConsumecode());
		PHPSESSID = "PHPSESSID=" + result.get("PHPSESSID");
		createMap.put("accountID", pay.getPhone());
		createMap.put("type", "buy");
		result = HttpClienthttpsUtil.doPost(
				"https://xuelele.10155.com/Wap/Order/sendVerifyCode",
				createMap, "UTF-8", PHPSESSID);

		for (Entry<String, String> entry : result.entrySet()) {
			if (!"result".equals(entry.getKey())) {
				re = re + entry.getKey() + "=" + entry.getValue() + ";";
			}
		}
		re = re + PHPSESSID;
		pay.setRe(re);
		code.setRe(re);

		return code;
	}

	/**
	 * 电信翼支付按次下发验证码
	 * 
	 * @author think
	 * 
	 */
	public Gamecode dxyzf(Payinfo pay) {
		Gamecode code = new Gamecode();
		String str = "";
		String URL = "https://webpaywg.bestpay.com.cn/verifyCode.do"; // 接口地址
		String ORDERSEQ = pay.getOrderId(); // 订单号
		String ORDERREQTRANSEQ = pay.getOrderId() + "001";// 订单请求支付流水号
		String TELEPHONE = pay.getPhone();// 手机号
		String MERCHANTID = "02420201040589625";// 商户代码
		String FUNCTIONTYPE = "1";// 功能类型
		String ORDERAMOUNT = pay.getCost() + "";// 金额
		String keyString = "4DB0E1D95542C1C4D91E223CFA91F0D43EB851BA9C199313";// 数据key

		StringBuilder sb = new StringBuilder();// 组装mac加密明文串
		sb.append("MERCHANTID=").append(MERCHANTID);
		sb.append("&ORDERSEQ=").append(ORDERSEQ);
		sb.append("&ORDERREQTRANSEQ=").append(ORDERREQTRANSEQ);
		sb.append("&TELEPHONE=").append(TELEPHONE);
		sb.append("&KEY=").append(keyString);// 此处是商户的key
		System.out.println("mac原串：" + sb);

		String mac = (LpTools.getMD5(sb.toString())).toUpperCase();// 进行md5加密(商户自己封装MD5加密工具类，此处只提供参考)
		System.out.println("mac:" + mac);

		// 组装请求参数
		StringBuilder param = new StringBuilder();
		param.append("ORDERSEQ=").append(ORDERSEQ).append("&ORDERREQTRANSEQ=")
				.append(ORDERREQTRANSEQ).append("&TELEPHONE=")
				.append(TELEPHONE).append("&MERCHANTID=").append(MERCHANTID)
				.append("&FUNCTIONTYPE=").append(FUNCTIONTYPE)
				.append("&ORDERAMOUNT=").append("ORDERAMOUNT").append("&MAC=")
				.append(mac);
		NameValuePair[] pair = new NameValuePair[7];
		pair[0] = new NameValuePair("ORDERSEQ", ORDERSEQ);
		pair[1] = new NameValuePair("TELEPHONE", TELEPHONE);
		pair[2] = new NameValuePair("MERCHANTID", MERCHANTID);
		pair[3] = new NameValuePair("FUNCTIONTYPE", FUNCTIONTYPE);
		pair[4] = new NameValuePair("ORDERAMOUNT", ORDERAMOUNT);
		pair[5] = new NameValuePair("ORDERREQTRANSEQ", ORDERREQTRANSEQ);
		pair[6] = new NameValuePair("MAC", mac);
		try {
			str = HttpPost.mobileHttpGet(URL, pair);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return code;

	}

	/**
	 * 
	 * @param pay
	 * @param authCode
	 * @return
	 */

	public Gamecode ldxyzfConfirm(Payinfo pay, String authCode) {
		String str = "";
		Gamecode code = new Gamecode();
		String URL = "https://webpaywg.bestpay.com.cn/backBillPay.do"; // 接口地址
		String MERCHANTID = "02420201040589625";// 商户代码
		String SUBMERCHANTID = "";// 商户子代码
		String MERCHANTPWD = "114628";// 商户密码，交易key
		String MERCHANTPHONE = "4008011888";// 商户客服电话
		String ORDERSEQ = pay.getOrderId(); // 订单号
		String ORDERREQTRANSEQ = pay.getOrderId() + "001";// 订单请求支付流水号
		String ORDERAMOUNT = pay.getCost() + "";// 金额
		String ORDERREQTIME = LpTools.getCurrentDate();// 订单请求时间
		String USERACCOUNT = pay.getPhone();// 用户账号
		String USERIP = pay.getIp();// 用户IP
		String PHONENUM = pay.getPhone();// 扣费号码
		String VERIFYCODE = authCode;// 短信验证码
		String GOODPAYTYPE = "0";// 商品付费类型
		String GOODSCODE = "QIANWE";// 商品编码
		String GOODSNAME = URLEncoder.encode("测试商品");// 商品名称
		String GOODSNUM = "1";// 商品数量
		String ORDERDESC = URLEncoder.encode("测试");// 订单描述
		String ATTACH = "";// 附加信息
		String BACKMERCHANTURL = "http://127.0.0.1";// 支付结果通知地址
		String keyString = "4DB0E1D95542C1C4D91E223CFA91F0D43EB851BA9C199313";// 数据key

		StringBuilder sb = new StringBuilder();// 组装mac加密明文串
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
		sb.append("&KEY=").append(keyString);// 此处是商户的key
		System.out.println("mac原串：" + sb);
		String mac = (LpTools.getMD5(sb.toString())).toUpperCase();// 进行md5加密(商户自己封装MD5加密工具类，此处只提供参考)
		// String mac =
		// CryptTool.md5Digest(sb.toString());//进行md5加密(商户自己封装MD5加密工具类，此处只提供参考)
		System.out.println("mac:" + mac);

		// //组装请求参数
		// StringBuilder param = new StringBuilder();
		// param.append("MERCHANTID=").append(MERCHANTID)
		// .append("&SUBMERCHANTID=").append(SUBMERCHANTID)
		// .append("&MERCHANTPWD=").append(MERCHANTPWD)
		// .append("&MERCHANTPHONE=").append(MERCHANTPHONE)
		// .append("&ORDERSEQ=").append(ORDERSEQ)
		// .append("&ORDERREQTRANSEQ=").append(ORDERREQTRANSEQ)
		// .append("&ORDERAMOUNT=").append(ORDERAMOUNT)
		// .append("&ORDERREQTIME=").append(ORDERREQTIME)
		// .append("&USERACCOUNT=").append(USERACCOUNT)
		// .append("&USERIP=").append(USERIP)
		// .append("&PHONENUM=").append(PHONENUM)
		// .append("&VERIFYCODE=").append(VERIFYCODE)
		// .append("&GOODPAYTYPE=").append(GOODPAYTYPE)
		// .append("&GOODSCODE=").append(GOODSCODE)
		// .append("&GOODSNAME=").append(GOODSNAME)
		// .append("&GOODSNUM=").append(GOODSNUM)
		// .append("&ORDERDESC=").append(ORDERDESC)
		// .append("&ATTACH=").append(ATTACH)
		// .append("&BACKMERCHANTURL=").append(BACKMERCHANTURL)
		// .append("&MAC=").append(mac);

		NameValuePair[] pair = new NameValuePair[20];
		pair[0] = new NameValuePair("MERCHANTID", MERCHANTID);
		pair[1] = new NameValuePair("SUBMERCHANTID", SUBMERCHANTID);
		pair[2] = new NameValuePair("MERCHANTPWD", MERCHANTPWD);
		pair[3] = new NameValuePair("MERCHANTPHONE", MERCHANTPHONE);
		pair[4] = new NameValuePair("ORDERSEQ", ORDERSEQ);
		pair[5] = new NameValuePair("ORDERREQTRANSEQ", ORDERREQTRANSEQ);
		pair[6] = new NameValuePair("ORDERAMOUNT", ORDERAMOUNT);
		pair[7] = new NameValuePair("ORDERREQTIME", ORDERREQTIME);
		pair[8] = new NameValuePair("USERACCOUNT", USERACCOUNT);
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
			System.out.println(str);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		code.setOrderId(str);

		return code;
	}

	/**
	 * 学乐乐验证码提交
	 * 
	 * @param pay
	 * @return
	 */
	public Map<String, String> ltzyConfirm(Payinfo pay, String authCode) {
		JSONObject json = new JSONObject();
		String str = "";
		Gamecode code = new Gamecode();
		if (pay.getPhone() == null || "".equals(pay.getPhone())) {
			code.setPayType("0");
			return null;
		}

		String cookie = pay.getRe();
		Map<String, String> result = new HashMap<String, String>();
		Map<String, String> createMap = new HashMap<String, String>();
		createMap.put("oc_id", pay.getPayId());
		createMap.put("accountID", pay.getPhone());
		createMap.put("verifyCode", authCode);
		createMap.put("__hash__", pay.getUa());
		// System.out.println("111111111111111111111111");
		if ("".equals(authCode)) {
			result.put("result", "验证码为空");
			return result;
		}
		result = HttpClienthttpsUtil.doPost(
				"https://xuelele.10155.com/Wap/Order/directTCPay", createMap,
				"utf-8", cookie);

		if (json != null && "1".equals(json.getString("status"))) {
			code.setSmsPort("1065889969");
			code.setPbPort("1065889969");
			code.setPbPortA("10086");
			code.setPbContent("您已成功订购咪咕互娱公司");
		}
		code.setRe(str);

		// String oc_id = "";
		// int cost = pay.getCost();
		// if(cost == 10){
		// oc_id = "119";
		// }else if(cost == 15){
		// oc_id = "117";
		// }
		// String ss = authCode;
		// String browser =pay.getBsc_lac();
		// try {
		// NameValuePair[] pair = new NameValuePair[5];
		// pair[0] = new NameValuePair("accountID", pay.getPhone());
		// pair[1] = new NameValuePair("oc_id", oc_id);
		// pair[2] = new NameValuePair("verifyCode", ss);
		// pair[3] = new NameValuePair("payType", "tcpay");
		// pair[4] = new NameValuePair("__hash__", pay.getUa());
		// str = httppost.doPostCmdByHttpClient1(
		// "http://xuelele.10155.com/Wap/Order/channelPay",
		// pair,pay.getRe(),oc_id);
		// if (json != null && "0".equals(json.getString("code"))) {
		// code.setSmsPort("1065889969");
		// code.setPbPort("1065889969");
		// code.setPbPortA("10086");
		// code.setPbContent("您已成功订购咪咕互娱公司");
		// }
		// code.setRe(str);
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		return result;
	}

	/**
	 * 鼎元联通沃加包月下发验证码
	 * 
	 * @author think
	 * 
	 */
	public Gamecode dyltwj(Payinfo pay) {
		Gamecode code = new Gamecode();
		String URL = "http://103.10.87.143/2016sy/common/dx_mo.jsp"; // 接口地址
		String re = "";
		NameValuePair[] pair = new NameValuePair[8];
		pair[0] = new NameValuePair("orderid", pay.getOrderId());
		pair[1] = new NameValuePair("paytype", "1");
		pair[2] = new NameValuePair("feetype", "2");
		pair[3] = new NameValuePair("price", "10");
		pair[4] = new NameValuePair("cpid", "9058");
		pair[5] = new NameValuePair("mobile", pay.getPhone());
		pair[6] = new NameValuePair("appname", pay.getBsc_cid());
		pair[7] = new NameValuePair("chargeName", pay.getBsc_lac());
		try {
			re = HttpPost.mobileHttpGets(URL, pair);
			code.setRe(re);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return code;

	}

	/**
	 * 鼎元联通沃加包月验证码提交
	 * 
	 * @author think
	 * 
	 */
	public Gamecode dyltwjConfirm(Payinfo pay, String authCode) {

		JSONObject json = JSON.parseObject(pay.getRe());
		Gamecode code = new Gamecode();
		String URL = json.getString("verifyurl"); // 接口地址
		String linkid = json.getString("linkid");
		// String re = "";
		NameValuePair[] pair = new NameValuePair[1];
		pair[0] = new NameValuePair("linkid", linkid);
		pair[1] = new NameValuePair("vcode", authCode);

		try {
			HttpPost.mobileHttpGet(URL, pair);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return code;

	}

	/**
	 * 测试 愛音樂提交订单接口
	 */
	public Gamecode DxMusicOrder(Payinfo pay, BillingCode jfcode) {

		Gamecode code = new Gamecode();

		String str = "";
		// String requrl =
		// "http://api.118100.cn/openapi/services/v2/package/packageservice/emplanunched.json";
		String requrl = "http://api.118100.cn/openapi/services/v2/package/packageservice/emplanunched.json";
		NameValuePair[] pair = new NameValuePair[3];
		pair[0] = new NameValuePair("mdn", pay.getPhone());
		pair[1] = new NameValuePair("package_id", jfcode.getConsumecode());
		pair[2] = new NameValuePair("column", "111");

		try {
			str = HttpPost.musicHttpGets(requrl, pair);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return code;

	}

	/**
	 * 测试 愛音樂提交验证码接口
	 */
	public static String DxMusicOrderConfirm(Payinfo pay, String authCode) {

		String str = "";
		String requrl = "http://api.118100.cn/openapi/services/v2/package/packageservice/subscribebyemp.json";

		NameValuePair[] pair = new NameValuePair[4];
		pair[0] = new NameValuePair("mdn", pay.getPhone());
		pair[1] = new NameValuePair("package_id", pay.getPayId());
		pair[2] = new NameValuePair("random_key", authCode);
		pair[3] = new NameValuePair("column", "111");

		try {
			str = HttpPost.musicHttpGets(requrl, pair);
			System.out.println(str);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return str;

	}

	/**
	 * 米粒天翼阅读验证码下发
	 */
	public static Gamecode mltyyd(Payinfo pay) {

		Gamecode code = new Gamecode();
		String str = "";
		String requrl = "http://p.miliroom.com:9500/SmsPayServer/apipay/getpassage";

		String timestr = (new Date()).getTime() + "";

		String prov = LpTools.getPhoneprov(pay.getPhone());

		NameValuePair[] pair = new NameValuePair[14];
		pair[0] = new NameValuePair("provider", "3");
		pair[1] = new NameValuePair("appid", "21843300");
		pair[2] = new NameValuePair("cpid", "11296");
		pair[3] = new NameValuePair("price", "30");
		pair[4] = new NameValuePair("timestamp", timestr);
		pair[5] = new NameValuePair("province", prov);
		pair[6] = new NameValuePair("phonenum", pay.getPhone());
		pair[7] = new NameValuePair("imsi", pay.getImsi());
		pair[8] = new NameValuePair("imei", pay.getImei());
		pair[9] = new NameValuePair("appname", "test");
		pair[10] = new NameValuePair("itemname", "test");
		pair[11] = new NameValuePair("userip", "127.0.0.1");
		pair[12] = new NameValuePair("cporderno", pay.getOrderId());
		pair[13] = new NameValuePair("channelcode", "1");

		try {
			str = HttpPost.musicHttpGets(requrl, pair);
			// System.out.println(str);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		code.setRe(str);

		return code;

	}

	/**
	 * 米粒天翼阅读验证码提交
	 */
	public static String mltyydOrderConfirm(Payinfo pay, String authCode) {

		String str = "";
		String requrl = "";
		str = pay.getRe();
		JSONObject json = JSON.parseObject(str);
		if (json != null && "00000".equals(json.get("code"))) {

			JSONArray arr = json.getJSONArray("array");
			JSONObject json1 = (JSONObject) arr.get(0);
			if (json1 != null && "psp".equals(json1.get("type"))) {
				// code.setRe(json1.getString("nexturl"));
				requrl = json1.getString("nexturl");
			}

		}
		Gamecode code = new Gamecode();
		requrl = requrl + authCode;

		try {
			str = HttpPost.musicHttpGets(requrl, null);
			// System.out.println(str);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// code.setRe(str);

		return str;

	}

}
