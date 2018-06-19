package com.xh.sdk.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.apache.commons.httpclient.NameValuePair;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.core.sgip.body.command.Report;
import com.core.sgip.util.SGIPUtils;
import com.google.gson.annotations.JsonAdapter;
import com.huawei.smproxy.comm.sgip.message.SGIPSubmitMessage;
import com.huawei.smproxy.comm.sgip.message.SGIPSubmitRepMessage;
import com.xh.sdk.common.BigExcelReader;
import com.xh.sdk.common.HttpPost;
import com.xh.sdk.common.LpTools;
import com.xh.sdk.common.SGIPSender;
import com.xh.sdk.common.memcached;
import com.xh.sdk.dao.userDao;
import com.xh.sdk.model.BillingCode;
import com.xh.sdk.model.ChannelCodeInfo;
import com.xh.sdk.model.DxprovCode;
import com.xh.sdk.model.Gamecode;
import com.xh.sdk.model.LtdmChannelInfo;
import com.xh.sdk.model.LtdmOldUser;
import com.xh.sdk.model.LtdmProductInfo;
import com.xh.sdk.model.LtdmProductList;
import com.xh.sdk.model.Payinfo;
import com.xh.sdk.model.ReturnObj;
import com.xh.sdk.model.SGIPReportMessage;
import com.xh.sdk.redis.RedisClientTemplate;
import com.xh.sdk.redis.RedisDao;

@Service
@Transactional(value = TxType.NOT_SUPPORTED)
public class SdkService {

	@Autowired
	private HttpPost httppost;

	@Autowired
	private userDao dao;

	@Autowired
	private memcached mc;

	@Autowired
	private cpCodeService cp;

	@Autowired
	private RedisClientTemplate redis;

	@Autowired
	private RedisDao rd;

	/**
	 * 根据payType获取代码信息
	 * 
	 * @param codeid
	 * @param pay
	 * @param jfcode
	 * @return
	 */
	public Gamecode getCodeByPayType(Payinfo pay, BillingCode jfcode) {
		Gamecode code = new Gamecode();

		int codeid = Integer.valueOf(jfcode.getType());
		System.out.println("-------------------------" + codeid
				+ "--------------------");
		switch (codeid) {
		case 1: // 联通学乐乐代码
			code = cp.ltzy(pay, jfcode);
			// code.setPayType("3");
			break;
		case 2: // 翼支付
			code = cp.dxyzf(pay);
			// code.setPayType("3");
			break;
		case 3: // 鼎元联通沃加
			code = cp.dyltwj(pay);
			// code.setPayType("3");
			break;
		case 5: // 电信爱音乐
			code = cp.DxMusicOrder(pay, jfcode);
			// code.setPayType("3");
			break;
		case 6: // 电信阅读（米粒）
			code = cpCodeService.mltyyd(pay);
			// code.setPayType("3");
			break;
		case 7: // 电信短回（余露）
			// code = cp.mltyyd(pay);
			// code.setPayType("3");
			code.setSmsPort("10660478");
			if ("50010".equals(pay.getProductId())) {
				code.setSmsContent("Z20");
			} else {
				code.setSmsContent("LB1");
			}
			// code.setPbPort("");
			break;

		default:
			pay.setRe(code.getRe());
			pay.setResult("-1");
			pay.setResultDesc("no code");
			code.setPayType("0");
			break;
		}
		return code;
	}

	/**
	 * 获取省份
	 * 
	 * @param iccid
	 * @param yys
	 *            运营商
	 * @param ip
	 *            请求IP地址
	 * @param imsi
	 *            用户imsi
	 * @return
	 */
	public String getProvince(String iccid, String yys, String ip, String imsi) {
		String province = "";
		Boolean flag = true;
		if (iccid.length() < 13) {
			province = getProvinceByBD(ip);
			iccid = getProvCodeByName(province);
			flag = false;
		}

		if (flag && "yd".equals(yys)) {
			iccid = iccid.substring(8, 10);
		}

		if (flag && "lt".equals(yys)) {
			province = getProvinceByBD(ip);
			iccid = getProvCodeByName(province);
		}

		if (flag && iccid.length() > 15 && "dx".equals(yys)) {
			iccid = iccid.substring(10, 13);
			iccid = getDxProvCode(iccid);
		}
		if (flag && "".equals(iccid)) {
			province = getProvinceByBD(ip);
			iccid = getProvCodeByName(province);
		}
		return iccid;
	}

	/**
	 * 电信用户根据iccid获取省份
	 * 
	 * @param sid
	 * @return
	 */
	private String getDxProvCode(String sid) {
		String provid = "";
		List list = redis.lrange("dxList", 0, redis.llen("dxList"));
		if (list == null || list.size() <= 0) {
			System.out.println("redis get null ! function getDxProvCode()");
			list = dao.dxfind();
			for (int i = 0; i < list.size(); i++) {
				DxprovCode ss = (DxprovCode) list.get(i);
				if (ss.getCitycode().contains(sid)) {
					provid = ss.getProvid();
					break;
				}
			}
		} else {
			for (int i = 0; i < list.size(); i++) {
				String s = (String) list.get(i);
				DxprovCode ss = JSON.parseObject(s, DxprovCode.class);
				if (ss.getCitycode().contains(sid)) {
					provid = ss.getProvid();
					break;
				}
			}
		}

		return provid;
	}

	/**
	 * 通过省份名匹配省份ID
	 * 
	 * @param sid
	 * @return
	 */
	private String getProvCodeByName(String province) {
		String provid = "";
		// System.out.println("sid======" + sid);
		// List list = codeMap.get("dxList");
		List list = redis.lrange("dxList", 0, redis.llen("dxList"));
		if (list == null || list.size() <= 0) {
			list = dao.dxfind();
			for (int i = 0; i < list.size(); i++) {
				DxprovCode ss = (DxprovCode) list.get(i);
				if (ss.getProv().contains(province)) {
					provid = ss.getProvid();
					break;
				}
			}
		} else {
			for (int i = 0; i < list.size(); i++) {
				String s = (String) list.get(i);
				DxprovCode ss = JSON.parseObject(s, DxprovCode.class);
				if (ss.getProv().contains(province)) {
					provid = ss.getProvid();
					break;
				}
			}
		}

		return provid;
	}

	/**
	 * 檢查用戶運營商屬性
	 * 
	 * @param userinfo
	 * @return
	 */
	public String checkUser(String imsi) {
		String str = "";
		if (imsi == null || "".equals(imsi)) {
			str = "wk";
		} else if (imsi.startsWith("46000") || imsi.startsWith("46002")
				|| imsi.startsWith("46007")) {
			str = "yd";
		} else if (imsi.startsWith("46001") || imsi.startsWith("46006")) {
			str = "lt";
		} else if (imsi.startsWith("46003") || imsi.startsWith("46011")
				|| imsi.startsWith("46005")) {
			str = "dx";
		}
		return str;
	}

	/**
	 * 1支付方式 短信确认发送
	 * 
	 * @param orderId
	 * @param status
	 */
	@Async
	public void confirm(Payinfo pay, String status) {
		// System.out.println("33333333333333333");
		String orderId = pay.getOrderId();
		pay.setSmsresult(status);
		mc.set(orderId, JSON.toJSONString(pay));
		pay.setTableName("pay_info_" + orderId.substring(0, 8));
		dao.confirmPayInfo(pay);
		// System.out.println("44444444444444444444");
	}

	/**
	 * 异步保存数据
	 * 
	 * @param <T>
	 * @param sk
	 */
	@Async
	public void savePayinfo(Payinfo pay) {
		try {
			dao.savePayinfo(pay);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 短验支付方式提交验证码处理
	 * 
	 * @param orderId
	 * @param authCode
	 */
	public void dyPay(String orderId, String authCode) {
		String str = "";
		JSONObject json = JSON.parseObject(mc.get(orderId).toString());
		Map<String, String> result = new HashMap<String, String>();
		Payinfo pay = JSON.toJavaObject(json, Payinfo.class);
		List<Payinfo> list = new ArrayList<Payinfo>();

		if (pay != null) {
			String payType = pay.getPayType();
			System.out.println("payid=========" + payType);
			System.out.println("authCode=========" + authCode);
			if ("1".equals(payType)) {

				result = cp.ltzyConfirm(pay, authCode);

				// System.out.println("22222222222222222");
				if (mc.get("xuelele") != null) {
					list = JSON.parseArray(mc.get("xuelele").toString(),
							Payinfo.class);
				}
				list.add(pay);

				mc.set("xuelele", JSON.toJSONString(list));
			}

			if ("2".equals(payType)) {

				cp.ldxyzfConfirm(pay, authCode);
				System.out.println();

			}
			if ("3".equals(payType)) {

				cp.dyltwjConfirm(pay, authCode);

			}
			if ("5".equals(payType)) {

				str = cpCodeService.DxMusicOrderConfirm(pay, authCode);

			}
			if ("6".equals(payType)) {

				str = cpCodeService.mltyydOrderConfirm(pay, authCode);

			}

		}

		if (result.get("result") != null && result.get("result").length() > 250) {
			pay.setResultDesc("result is too long");
		} else {
			pay.setResultDesc(result.get("result"));
		}
		pay.setRe(str);
		confirm(pay, "-1");
	}

	/**
	 * 调用百度的接口通过ip获取省份
	 * 
	 * @param ip
	 * @return
	 */
	public String getProvinceByBD(String ip) {
		JSONObject json = new JSONObject();
		String province = "";
		try {

			String str = "";

			str = HttpPost.sendGet("http://api.map.baidu.com/location/ip", ip);
			json = JSON.parseObject(str);
			if (json != null) {
				if (json.getString("content") != null) {
					JSONObject json1 = new JSONObject();
					json1 = JSON.parseObject(json.getString("content"));
					province = json1.getString("address");
					if (province.length() > 2) {
						province = province.substring(0, 2);
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return province;
	}

	/**
	 * 获取代码
	 * 
	 * @param valueOf
	 * @param pay
	 * @param cpparam
	 * @return
	 */
	public ReturnObj getPayData(Payinfo pay) {

		ReturnObj re = new ReturnObj();
		re.setStatu("0");
		BillingCode billingcode = new BillingCode();
		String appId = pay.getAppId();
		String productId = pay.getProductId();

		String yys = checkUser(pay.getImsi());
		String prov = getProvince(pay.getIccid(), yys, pay.getIp(),
				pay.getImsi());
		Random random = new Random();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss"
				+ random.nextInt(1000000));
		String orderId = sdf.format(new Date());
		pay.setOrderId(orderId);
		pay.setTableName("pay_info_" + orderId.substring(0, 8));
		pay.setProvince(prov);
		pay.setAddtime(new Date());
		Gamecode code = new Gamecode();

		// 检测渠道和代码对应

		if (redis.hget("ChannelCodeInfo", appId + productId) == null) {

			re.setStatu(ReturnObj.NO_CHANNEL);
			re.setErrorMsg(ReturnObj.NO_CHANNEL_MSG);
			// return re;
		} else {
			ChannelCodeInfo channelcode = JSON.parseObject(
					redis.hget("ChannelCodeInfo", appId + productId),
					ChannelCodeInfo.class);

			if (!"1".equals(channelcode.getIsUse())) {

				re.setStatu(ReturnObj.NO_CODE);
				re.setErrorMsg(ReturnObj.NO_CODE_MSG);

			}
		}

		// 检测黑名单

		if (isBlackPhone(pay.getPhone(), pay.getImsi())) {

			re.setStatu(ReturnObj.BLACK_PHONE);
			re.setErrorMsg(ReturnObj.BLACK_PHONE_MSG);
			// return re;
		}

		// 选择代码

		if ("0".equals(re.getStatu())) {
			billingcode = JSON.parseObject(
					redis.hget("BillingCode", productId), BillingCode.class);

			if (billingcode == null) {
				re.setStatu(ReturnObj.NO_CODE);
				re.setErrorMsg(ReturnObj.NO_CODE_MSG);
			} else {
				pay.setPayId(billingcode.getId() + "");
				pay.setPayType(billingcode.getType());
				pay.setCost(Integer.valueOf(billingcode.getPrice()));

			}
		}
		// 检测省份和运营商

		// String province = billingcode.getPronvice();
		//
		// if (!yys.equals(billingcode.getYystype())) {
		//
		// re.setStatu(ReturnObj.YYS_PHONE);
		// re.setErrorMsg(ReturnObj.YYS_PHONE_MSG);
		//
		// }
		//
		// if (!province.contains(prov)) {
		// re.setStatu(ReturnObj.PROV_BLOCK);
		// re.setErrorMsg(ReturnObj.BLACK_PHONE_MSG);
		// }

		// 检测代码限量
		// if(isLimited(billingcode)){
		//
		// re.setStatu(ReturnObj.CODE_LIMIT);
		// re.setErrorMsg(ReturnObj.CODE_LIMIT_MSG);
		// }

		// 检测用户限量
		// 获取代码

		System.out.println("start get code");
		if ("0".equals(re.getStatu())) {
			// mc.s
			code = getCodeByPayType(pay, billingcode);
			pay.setRe(code.getRe());
			pay.setPayId(billingcode.getConsumecode());
			pay.setPayType(billingcode.getType());
			if ("2".equals(billingcode.getCodeType())) {
				mc.set("phoneOrder" + pay.getPhone(), 3600,
						JSON.toJSONString(pay));
			}
			mc.set(pay.getOrderId(), JSON.toJSONString(pay));
			this.savePayinfo(pay);
		} else {
			pay.setSmsresult(re.getStatu());
			pay.setResultDesc(re.getErrorMsg());
			this.savePayinfo(pay);

		}
		re.setOrderId(orderId);
		re.setCode(code);

		return re;
	}

	/*
	 * 验证黑名单
	 */
	private boolean isBlackPhone(String phone, String imsi) {

		boolean is = false;
		if (phone == null || "".equals("phone")) {
			return is;
		}
		phone = phone.substring(2, phone.length());
		String m = redis.hget("blackphone", phone);
		if (m != null) {
			is = true;
		}
		return is;
	}

	/**
	 * 判断代码限量
	 * 
	 * @param pay
	 * @param code
	 * @return
	 */

	private boolean isLimited(BillingCode code) {
		boolean is = false;
		String key = "DAYLIMIT" + code.getId();

		String mkey = "MONTHLIMIT" + code.getId();
		// System.out.println("getkey==========" + key);
		String limited = mc.get(key.trim());
		String mlimited = mc.get(mkey.trim());
		// System.out.println("limited========" + limited);
		int num = limited != null ? Integer.valueOf(limited) : 0;
		int linum = code.getLimit() != null ? Integer.valueOf(code.getLimit())
				: 1000000;

		int mnum = mlimited != null ? Integer.valueOf(mlimited) : 0;
		int mlinum = code.getMlimit() != null ? Integer.valueOf(code
				.getMlimit()) : 1000000;

		if (linum > num && mlinum > mnum) {
			// System.out.println("day=" + num + "mon=" + mnum);
			return true;
		} else {
			// System.out.println("limit is run ------" + "day========" + num
			// + "mon==========" + mnum);

		}

		return is;
	}

	public String test() {

		rd.synallToRedis();

		// OldUserSumbit();

		String str = "";

		return str;
	}

	

	/**
	 * 老用户提交话单方法
	 * 
	 * @return
	 */

	public String OldUserSumbit() {
		// mc.set(key, expires, value)
		String str = "";
		List<LtdmOldUser> list = dao.getXdOldUserList();

		System.out.println("list=======" + list.size());
		for (int i = 0; i < list.size(); i++) {

			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
			String orderId = sdf.format(new Date());

			LtdmOldUser user = list.get(i);
		//	user.setEndtime(endtime);
			String feevalue = user.getFeevalue();

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

			String squen = insertOldUser(user.getPhone(),
					user.getServiceType(), feevalue);
			System.out.println("xd" + squen + "86" + user.getPhone()
					+ orderId.substring(0, 8));

			mc.set("xd" + squen + "86" + user.getPhone()
					+ orderId.substring(0, 8), 3600, JSON.toJSONString(user));
			if (i % 100 == 0) {
				System.out.println("this is " + i);

			}
		}

		return str;

	}

	/**
	 * 下单专用方法
	 * 
	 * @param phone
	 * @param serviceType
	 * @param fee
	 */
	public String insertOldUser(String phone, String serviceType, String fee) {
		// LtdmProductList pro = new LtdmProductList();
		// String productCode = "";
		// System.out.println("result ==========1111111111111=");
		// Payinfo pay = new Payinfo();
		// pay.setPhone("8618696237266");
		// pay.setIccid("WDM217");
		// pay.setProductId("10655502");
		// pay.setCpparam("89");
		// pay.setCost(1000);

		SGIPSender sender = SGIPSender.getInstance();

		String SPNumber = "10655502";
		String ChargeNumber = phone;
		String[] UserNumber = { phone };
		String CorpId = "90214";
		String ServiceType = serviceType;
		int FeeType = 3;
		String FeeValue = fee;
		String GivenValue = "0";
		int AgentFlag = 0;
		int MorelatetoMTFlag = 2;
		int Priority = 0;
		Date ExpireTime = null;
		Date ScheduleTime = null;
		int ReportFlag = 3;
		int TP_pid = 0;
		int TP_udhi = 0;
		int MessageCoding = 8;
		int MessageType = 0;

		byte[] MessageContent = "this is test message!".getBytes();
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

	// String str = cp.LtdmSubmit(pay, pro, "yzsubmit");

	// System.out.println("result ===========" + str);

	public String getServiceType() {
		String str = "";

		return str;
	}

	/**
	 * 联通动漫反向定制方式cp触发计费接口
	 * 
	 * @param appId
	 * @param productId
	 * @param phone
	 * @return
	 */

	

	/**
	 * 联通动漫接收sgip 给用户形成订购关系
	 * 
	 * @param pay
	 */
	public void getLtdmData(Payinfo pay) {
		// TODO Auto-generated method stub
		// LtdmProductList pro = new LtdmProductList();

		Random random = new Random();
		// String orderId="";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss"
				+ random.nextInt(1000000));
		String orderId = sdf.format(new Date());

		pay.setTableName("pay_info_" + orderId.substring(0, 8));
		// /获取用户省份
		String provId = testprove(pay);
		pay.setProvince(provId);
		pay.setAddtime(new Date());
		// 退订判断 直接记录
		if (pay.getCpparam() != null)

		{ // 非00000退订
			if (pay.getCpparam().toUpperCase().contains("TD")) {

				// 2018-03-19

				dao.savePayinfo(pay);
				LTDMsendSyncntTD(pay);
				return;
			}

			// 0000退订，退订所有业务
			if (pay.getCpparam().length() > 3
					&& "0000".equals(pay.getCpparam().substring(0, 4))) {

				dao.savePayinfo(pay);
				return;

			}

		}

		// 过滤黑名单
		if (isBlackPhone(pay.getPhone(), pay.getImsi())) {

			pay.setResultDesc("black phone");
			dao.savePayinfo(pay);
			return;
		}

		// 订购产品 获取产品编号渠道编号
		String productid = SGIPUtils.dealString(pay.getProductId());
		LtdmProductList pro = JSON.parseObject(
				redis.hget("LtdmList", productid + pay.getCpparam()),
				LtdmProductList.class);

		// 判断产品
		if (pro == null || "0".equals(pro.getIsuse())) {
			pay.setResultDesc("no product");

		} else {
			int cost = pro.getFeevalue() == null ? 0 : Integer.valueOf(pro
					.getFeevalue());

			pay.setCost(cost);

			// 手机获取省份，存入订单表
			// 通过省份获取省份编码
			// 用省份编码和产品的开通身份对比
			// yuhc

			boolean flag = provefiert(pro.getProv(), provId);
			if (!flag) {
				pay.setResultDesc("Province is not matching");
				dao.savePayinfo(pay);
				return;
			}

			// 过滤限量2018-03-19
			// m+端口号+指令 ===当前金额
			// d+端口号+指令 ===当前金额

			// String limitresult = this.isLimit(pay, pro);
			// if(!"ok".equals(limitresult)){
			// pay.setResultDesc(limitresult);
			// dao.savePayinfo(pay);
			// }

			// }
			// 2018-03-19

			// 判断没有linkid
			// System.out.println("1111111111111111111111111111111111111111111");
			if ("".equals(pay.getPayId()) || pay.getPayId() == null) {

				pay.setResultDesc("no LinkId");
			} else {

				pay.setIccid(pro.getServiceType());

				System.out.println("pay================="
						+ JSON.toJSONString(pay));

				String squene = cp.LtdmSubmit(pay, pro);
				if (!"error".equals(squene)) {
					String orderid = squene + pay.getPhone()
							+ orderId.substring(0, 8);
					// System.out.println("2222222222222");
					pay.setOrderId(orderid);
					if (!"".equals(pay.getOrderId())
							&& pay.getOrderId() != null) {
						mc.set(pay.getOrderId(), 3600, JSON.toJSONString(pay));
					}
				} else {

					pay.setResultDesc(squene);

				}
				// System.out.println("3333333333333");
			}

		}
		// System.out.println("44444444444444");
		dao.savePayinfo(pay);

	}

	/**
	 * report处理方法
	 * 
	 * @param report
	 */
	public void getLtdmReport(JSONObject report) {
		// TODO Auto-generated method stub

		Random random = new Random();
		// String orderId="";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss"
				+ random.nextInt(1000000));
		String orderId = sdf.format(new Date());

		orderId = report.getString("commandNumber")
				+ report.getString("userNumber") + orderId.substring(0, 8);

		// System.out.println(new
		// String(report.getBytes("submitSequenceNumber")));

		System.out.println("orderId==========" + mc.get(orderId));
		System.out.println("xdorderId==========" + mc.get("xd" + orderId));

		if (report != null && mc.get(orderId) != null) {

			JSONObject sjson = JSON.parseObject(mc.get(orderId).toString());
			Payinfo pay = JSON.toJavaObject(sjson, Payinfo.class);
			// 對比號碼
			if (pay.getPhone().equals(report.getString("userNumber"))) {

				pay.setResult(report.getString("state"));
				pay.setResultDesc(report.getString("errorCode"));
				if ("0".equals(pay.getResult()) && "0".equals(pay.getResult())) {
					// dao.updatePayinfoByOrderId(pay);
					pay.setResult("0");
					// 在mc里面 加限量
					huidiaoxianl(pay);
					// 在redis里面加成功数据 手机号 + 指令
					redis.hset("callbackoder",
							pay.getPhone() + pay.getCpparam(),
							JSON.toJSONString(pay));

					LTDMsendSyncnt(pay);
				} else if ("100".equals(pay.getResultDesc())
						&& "2".equals(pay.getResult())) {

					pay.setResult("0");
					// 在mc里面 加限量
					huidiaoxianl(pay);
					// 在redis里面加成功数据 手机号 + 指令
					redis.hset("callbackoder",
							pay.getPhone() + pay.getCpparam(),
							JSON.toJSONString(pay));

					LTDMsendSyncnt(pay);

				} else {
					pay.setResult(report.getString("errorCode"));
					pay.setResultDesc(report.getString("state"));

					dao.updatePayinfoByOrderId(pay);

				}

			}

		} else {

			if (report != null && mc.get("xd" + orderId) != null) {
				
			     sdf = new SimpleDateFormat("yyyyMMddhhmmss");
				

				System.out.println("orderId==========" + orderId);

				JSONObject sjson = JSON.parseObject(mc.get("xd" + orderId)
						.toString());
				LtdmOldUser user = JSON.toJavaObject(sjson, LtdmOldUser.class);

				user.setStatu(report.getString("state"));
				user.setErrorCode(report.getString("errorCode"));
				user.setEndtime( sdf.format(new Date()));
				dao.saveLtdmOldUser(user);

			}

		}

	}

	// yuhc---省份过滤代码
	public String testprove(Payinfo pay) {
		// 过滤省份
		// 2018-03-12 yuhc
		String str = "";
		String prov = "";
		String phone = pay.getPhone();

		JSONObject json = new JSONObject();
		NameValuePair[] pair = new NameValuePair[1];

		if (phone.length()>12) {
			pair[0] = new NameValuePair("tel", phone.substring(2, phone.length()));
		}else{
			pair[0] = new NameValuePair("tel", phone);
		}

		str = HttpPost.doPostCmdByHttpClient(
				" https://tcc.taobao.com/cc/json/mobile_tel_segment.htm", pair);
		json = JSON.parseObject(str.split("=")[1]);
		System.out.println(json.toJSONString());
		prov = json.getString("province");

		String provid = "";
		List list = redis.lrange("dxList", 0, redis.llen("dxList"));
		if (list == null || list.size() <= 0) {
			System.out.println("redis get null ! function getDxProvCode()");
			list = dao.dxfind();
			for (int i = 0; i < list.size(); i++) {
				DxprovCode ss = (DxprovCode) list.get(i);
				if (ss.getProv().contains(prov)) {
					provid = ss.getProvid();
					break;
				}
			}
		} else {
			for (int i = 0; i < list.size(); i++) {
				String s = (String) list.get(i);
				DxprovCode ss = JSON.parseObject(s, DxprovCode.class);
				if (ss.getProv().contains(prov)) {
					provid = ss.getProvid();
					break;
				}
			}
		}
		return provid;

	}

	private boolean provefiert(String prov, String provId) {
		// TODO Auto-generated method stub

		String productprov = prov;
		boolean flag = false;
		if ("".equals(productprov) == false && productprov != null) {
			String[] aa = productprov.split("\\|");
			if (aa == null || aa.length <= 0) {
				flag = false;
			} else {
				for (int j = 0; j < aa.length; j++) {
					if (aa[j].contains(provId)) {
						flag = true;
						break;
					}
				}
			}
		}
		return flag;
	}

	// yuhc风控
	private String testcompany(String company) {
		// TODO Auto-generated method stub
		String wLimit = "";
		List list = redis.lrange("LtdmChannelInfo", 0,
				redis.llen("LtdmChannelInfo"));
		if (list == null || list.size() <= 0) {
			System.out
					.println("redis get null ! function getLtdmChannelInfo()");
			list = dao.getLtdmChannelInfo();
			for (int i = 0; i < list.size(); i++) {
				LtdmChannelInfo ss = (LtdmChannelInfo) list.get(i);
				if (ss.getCompanyname().contains(company)) {
					wLimit = ss.getwLimit();
					break;
				}
			}
		} else {
			for (int i = 0; i < list.size(); i++) {
				String s = (String) list.get(i);
				LtdmChannelInfo ss = JSON.parseObject(s, LtdmChannelInfo.class);
				if (ss.getCompanyname().contains(company)) {
					wLimit = ss.getwLimit();
					break;
				}
			}
		}
		return wLimit;
	}

	/**
	 * LTDM发送同步数据
	 * 
	 * @param pay
	 */
	@Async
	public void LTDMsendSyncnt(Payinfo pay) {
		String appId = pay.getAppId();
		String productId = pay.getProductId();

		LtdmProductList code = new LtdmProductList();
		code = JSON.parseObject(
				redis.hget("LtdmList", pay.getProductId() + pay.getCpparam()),
				LtdmProductList.class);

		String str = "";
		String url = code.getSynUrl();
		// String probability = code.getProbability();
		if (url != null && (!"".equals(url))) {
			try {
				String phone = pay.getPhone();

				NameValuePair[] pair = new NameValuePair[6];
				SimpleDateFormat formatter = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				String dateString = formatter.format(new Date());
				pair[0] = new NameValuePair("msgcontent", pay.getCpparam());
				pair[1] = new NameValuePair("time",
						URLEncoder.encode(dateString));
				pair[2] = new NameValuePair("phone", phone.substring(2,
						phone.length()));
				pair[3] = new NameValuePair("orderId", pay.getOrderId());
				pair[4] = new NameValuePair("sPNumber", pay.getProductId());
				pair[5] = new NameValuePair("state", pay.getResult());

				int val = (int) (Math.random() * 100 + 1);
				// String probability = redis.hget("probability", appId +
				// productId);
				if (val < 11 && pay.getProductId().contains("1065550220250")) {
					pay.setProductId("106555022025022");
					pay.setSycstatu("0");

				} else {

					str = HttpPost.mobileHttpGet(url, pair);
					pay.setSycstatu("0");

				}

			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		dao.updatePayinfoByOrderId(pay);
		// dao.savePayinfo(pay);
	}

	@Async
	private void huidiaoxianl(Payinfo pay) {
		// TODO Auto-generated method stub
		long current = System.currentTimeMillis();// 当前时间毫秒数
		long zero = current / (1000 * 3600 * 24) * (1000 * 3600 * 24)
				- TimeZone.getDefault().getRawOffset();// 今天零点零分零秒的毫秒数
		long twelve = zero + 24 * 60 * 60 * 1000 - 1;// 今天23点59分59秒的毫秒数

		int dstime = (int) ((twelve - current) / 1000);
		// String
		// dlimit=mc.get("d"+pay.getProductId()+pay.getCpparam())==null?"0":mc.get("d"+pay.getProductId()+pay.getCpparam())+"";
		// mc.set("d"+pay.getProductId()+pay.getCpparam(), dstime,
		// (Integer.valueOf(dlimit)+pay.getCost())+"");
		// mc.delete(key)
		mc.set("d" + pay.getProductId() + pay.getCpparam(), dstime,
				pay.getCost() + "");
		Calendar ca = Calendar.getInstance();
		ca.set(Calendar.DAY_OF_MONTH,
				ca.getActualMaximum(Calendar.DAY_OF_MONTH));
		ca.set(Calendar.HOUR_OF_DAY, 23);
		ca.set(Calendar.MINUTE, 59);
		ca.set(Calendar.SECOND, 59);
		int mstime = (int) ((ca.getTimeInMillis() - current) / 1000);
		// String
		// mlimit=mc.get("m"+pay.getProductId()+pay.getCpparam())==null?"0":mc.get("m"+pay.getProductId()+pay.getCpparam())+"";
		// mc.set("m"+pay.getProductId()+pay.getCpparam(), mstime,
		// (Integer.valueOf(mlimit)+pay.getCost())+"");
		mc.set("m" + pay.getProductId() + pay.getCpparam(), mstime,
				pay.getCost() + "");
	}

	/**
	 * 退订LTDM发送同步数据
	 * 
	 * @param pay
	 * @return
	 */
	@Async
	public void LTDMsendSyncntTD(Payinfo pay) {
		Payinfo payone = JSON.parseObject(
				redis.hget("callbackoder", (pay.getPhone() + pay.getCpparam()
						.toUpperCase()).replace("TD", "")), Payinfo.class);
		if (payone != null) {
			LtdmProductList proone = JSON.parseObject(
					redis.hget("LtdmList",
							payone.getProductId() + payone.getCpparam()),
					LtdmProductList.class);

			String str = "";
			if (proone != null && "1".equals(proone.getHavetd())) {
				String url = proone.getSynUrl();
				if (url != null && (!"".equals(url))) {
					try {

						NameValuePair[] pair = new NameValuePair[6];
						SimpleDateFormat formatter = new SimpleDateFormat(
								"yyyy-MM-dd HH:mm:ss");
						String dateString = formatter.format(new Date());
						pair[0] = new NameValuePair("msgcontent",
								pay.getCpparam());
						pair[1] = new NameValuePair("time",
								URLEncoder.encode(dateString));
						pair[2] = new NameValuePair("phone", pay.getPhone());
						pair[3] = new NameValuePair("orderId",
								payone.getOrderId());
						pair[4] = new NameValuePair("sPNumber",
								pay.getProductId());
						pair[5] = new NameValuePair("state", "0");
						str = HttpPost.mobileHttpGet(url, pair);
						if ("ok".equals(str)) {
							pay.setSycstatu("0");

						}

					} catch (Exception e) {
						e.printStackTrace();
					}

				}
				dao.updatePayinfoByOrderId(pay);
				// dao.savePayinfo(pay);
			}
		}
	}

	public String isLimit(Payinfo pay, LtdmProductList pro) {
		String str = "ok";

		int feevalue = Integer.parseInt(pro.getFeevalue());
		// if(pro.getSpNumber()!=null&&pro.getMoContent()!=null){
		String mlimit = mc.get("m" + pro.getSpNumber() + pro.getMoContent()) == null ? "0"
				: mc.get("m" + pro.getSpNumber() + pro.getMoContent()) + "";
		String dlimit = mc.get("d" + pro.getSpNumber() + pro.getMoContent()) == null ? "0"
				: mc.get("d" + pro.getSpNumber() + pro.getMoContent()) + "";
		int dlimit1 = Integer.valueOf(dlimit) + feevalue;
		int mlimit1 = Integer.valueOf(mlimit) + feevalue;

		if (pro.getDayLimit() != null && "".equals(pro.getDayLimit()) == false) {
			if (dlimit1 > Integer.parseInt(pro.getDayLimit())) {

				return "dlimit is not matching";
			}
		}
		if (pro.getMLimit() != null && "".equals(pro.getMLimit()) == false) {
			if (mlimit1 > Integer.parseInt(pro.getMLimit())) {

				return "mlimit is not matching";
			}
		}

		return str;
	}
	
	/*
	 * 随机获取八位数字字符串
	 */
	public String genRandomNum(){  
	     int  maxNum = 10;  
	     int i;  
	     int count = 0;  
	     char[] str = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };      
	     StringBuffer pwd = new StringBuffer("");  
	     Random r = new Random();  
	      while(count < 8){  
	       i = Math.abs(r.nextInt(maxNum));     
	       if (i >= 0 && i < str.length) {  
	        pwd.append(str[i]);  
	        count ++;  
	       }  
	      }  
	      return pwd.toString();  
	}
	/**
	 *  联通动漫反向定制方式cp触发计费接口
	 * @param pay
	 * @param sign
	 * @param appId
	 * @param productId
	 * @param phone
	 * @param cost
	 * @return
	 */
	public ReturnObj getLtdmMo(Payinfo pay, String sign, String appId, String productId,
			String phone, String cost) {
			// TODO Auto-generated method stub
	    	/**
	    	 * 获取LtdmChannelInfo渠道对象
	    	 */
	    	ReturnObj re = new ReturnObj();
			LtdmChannelInfo ss = null;
			List list = redis.lrange("LtdmChannelInfo", 0,
			redis.llen("LtdmChannelInfo"));
			if (list == null || list.size() <= 0) {
				System.out.println("redis get null ! function getLtdmChannelInfo()");
				list = dao.getLtdmChannelInfo();
				for (int i = 0; i < list.size(); i++) {
					 ss = (LtdmChannelInfo) list.get(i);
					if (("100"+ss.getId()).equals(appId)) {
						//确认appid存在对应的渠道对象，则为pay的appid赋值
						pay.setAppId(appId);
						
						break;
					}
				}
			} else {
				for (int i = 0; i < list.size(); i++) {
					String s = (String) list.get(i);
					ss = JSON.parseObject(s, LtdmChannelInfo.class);
					if (("100"+ss.getId()).equals(appId)) {
						//确认appid存在对应的渠道对象，则为pay的appid赋值
						pay.setAppId(appId);
						
						break;
					}
				}
			}
			
			/*
			 * 检验秘钥串
			 */
			String secretKey=ss.getSecretkey();
			String key=LpTools.getMD5(appId+productId+phone+cost+secretKey);
			if(!key.equals(sign)){
				re.setStatu(ReturnObj.T_RESULT);
				re.setT_result(ReturnObj.T_RESULT_MSG);
				return re;
			}
			
			/**
			 * 根据appId 和productId 还有cost 确定渠道请求的产品
			 */
			String r=redis.hget("LtdmChanelProduct", appId + productId);
			if (r==null) {
				re.setStatu(ReturnObj.NO_CHANNEL);
				re.setErrorMsg(ReturnObj.NO_CHANNEL_MSG);
				System.out.println("appId和productId与渠道不匹配");
				return re;
			}
			JSONObject json=new JSONObject();
			json=JSONObject.parseObject(r);
			String serviceType=json.getString("serviceType");
			String r1=redis.hget("LtdmProduct", serviceType);
			if (r1==null) {
				re.setStatu(ReturnObj.NO_CHANNEL);
				re.setErrorMsg(ReturnObj.NO_CHANNEL_MSG);
				System.out.println("appId和productId与渠道不匹配");
				return re;
			}
			
			JSONObject j=JSONObject.parseObject(r1);
			String cost1=j.getString("feevalue");
			if(!(cost.equals(cost1))){
				re.setStatu(ReturnObj.NO_CHANNEL);
				re.setErrorMsg(ReturnObj.NO_CHANNEL_MSG);
				System.out.println("appId和productId与渠道不匹配");
				return re;
			}else{
				pay.setProductId(productId);
				pay.setCost(Integer.parseInt(cost));
				
				re.setStatu("0");
				re.setErrorMsg("");
			}
			
			/**
			 * 对手机号，进行黑名单鉴权
			 */
			if (isBlackPhone(phone, pay.getImsi())) {
				re.setErrorMsg(ReturnObj.BLACK_PHONE_MSG);
				re.setStatu(ReturnObj.BLACK_PHONE);
				pay.setPhone(phone);
				pay.setResultDesc("black phone");
				dao.savePayinfo(pay);
				return re;
			}
			pay.setPhone(phone);
			
			/**
		     * 对渠道，产品进行省份鉴权
		     */
			String prov=testprove(pay);
			if (prov==null) {
				re.setErrorMsg(ReturnObj.UNKNOW_ERROR_MSG);
				re.setStatu(ReturnObj.UNKNOW_ERROR);
				return re;
			}
			String pro=json.getString("prov");
			String[] pros=pro.split("\\|");
			if(pros==null){
				re.setErrorMsg(ReturnObj.UNKNOW_ERROR_MSG);
				re.setStatu(ReturnObj.UNKNOW_ERROR);
				return re;
			}
			
			for (String string : pros) {
				if(!prov.equals(string)){
					re.setErrorMsg(ReturnObj.UNKNOW_ERROR_MSG);
					re.setStatu(ReturnObj.UNKNOW_ERROR);
					return re;
				}
			}
			/*
			 * 根据productId和随机八位数字字符串生成orderID
			 */
			pay.setOrderId(productId+genRandomNum());
			dao.savePayinfo(pay);
			return re;
			
		}
}
