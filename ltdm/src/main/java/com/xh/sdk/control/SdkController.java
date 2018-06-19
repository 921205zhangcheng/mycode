package com.xh.sdk.control;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.core.sgip.body.command.Deliver;
import com.core.sgip.body.command.Report;
import com.core.sgip.head.SGIPMsgHead;
import com.core.sgip.util.SGIPUtils;
import com.xh.sdk.model.Payinfo;
import com.xh.sdk.model.ReturnObj;
import com.xh.sdk.model.SGIPDeliverMessage;
import com.xh.sdk.model.SGIPReportMessage;
import com.xh.sdk.service.SdkService;

@Controller
@RequestMapping("sdk")
public class SdkController {

	@Autowired
	private SdkService payservice;

	@RequestMapping(value = "getPay", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String getPay(HttpServletRequest request,
			HttpServletResponse response) {
		JSONObject json = new JSONObject();
		ReturnObj re = new ReturnObj();
		// Gamecode code = new Gamecode();
		try {
			String imsi = request.getParameter("imsi");
			String imei = request.getParameter("imei");
			String iccid = request.getParameter("iccid");
			String phone = request.getParameter("phone");
			String ip = request.getParameter("ip");
			String cost = request.getParameter("cost");
			String appId = request.getParameter("appId");
			String productId = request.getParameter("productId");
			String cpparam = request.getParameter("cpparam");

			String appname = request.getParameter("appname");
			String chargeName = request.getParameter("chargeName");
			// String payType = request.getParameter("payType");
			Payinfo p = new Payinfo();
			p.setIp(ip);
			p.setImsi(imsi);
			p.setImei(imei);
			p.setIccid(iccid);
			p.setPhone(phone);
			p.setBsc_cid(appname);
			p.setBsc_lac(chargeName);
			p.setCost(Integer.parseInt(cost));
			p.setAppId(appId);
			p.setCpparam(cpparam);
			p.setProductId(productId);
			p.setPayType(cpparam);
			re = payservice.getPayData(p);
			// json = new JSONObject(re)

			System.out.println(productId + "-------------" + productId);

		} catch (Exception e) {
			re.setStatu(ReturnObj.UNKNOW_ERROR);
			re.setErrorMsg(ReturnObj.UNKNOW_ERROR_MSG);
		}
		return JSON.toJSONString(re);
	}
/**
 * 
 * @param request
 * @param response
 * @return
 */
	
	@RequestMapping(value = "LtdmXd", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String getLtdmPay(HttpServletRequest request,
			HttpServletResponse response) {
//		JSONObject json = new JSONObject();
//		ReturnObj re = new ReturnObj();
//		Payinfo pay = new Payinfo();
//		String appId= request.getParameter("appId")==null?"":request.getParameter("appId");
//		String productId = request.getParameter("productId")==null?"":request.getParameter("productId");
//		String phone = request.getParameter("phone")==null?"0":request.getParameter("phone");
		String xdKey= request.getParameter("xdKey");
		if(xdKey!=null&&"xiaomuyaoxiadan".equals(xdKey)){
			
			payservice.OldUserSumbit();
		}	
		return "";
	}
	
	
	
	
	
	
	
	
	
	
	@RequestMapping(value = "Ltdm", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String getLtdm(HttpServletRequest request,
			HttpServletResponse response) {
		JSONObject json = new JSONObject();
		ReturnObj re = new ReturnObj();
		Payinfo pay = new Payinfo();
		// Gamecode code = new Gamecode();
		System.out.println("recive sgip");
		// String head = request.getParameter("head");
		String deliver = request.getParameter("deliver");
		String report = request.getParameter("report");

		if (deliver != null) {

			json = JSONObject.parseObject(deliver);
			System.out.println("deliverjson=========" + json.toJSONString());
			// ={"bytes":"AAGc7xJcOftTmWdZAAAAABJcOfkAAAABADg2MTg2NTgxOTE0ODgAAAAAAAAAAAAAAAAAAAAAAAA=",
			// "commandId":5,"errorCode":0,"reportType":0,"reserve":"","sequenceId":1402562393,
			// "srcNodeId":105711,"state":0,"submitSequenceNumber":"AAAAAQ==","timeStamp":308034043,"userNumber":"8618658191488"}

			// {"bytes":"AAAAARJcOj+SzP6PODYxNTU0OTc1NDQxNwAAAAAAAAAAMTA2NTU1MDIAAAAAAAAAAAAAAAAAAAAPAAAABTAwMDAwOTAwMDIzNzY=",
			// "commandId":4,"msgContent":"MDAwMDA=","msgFmt":15,"msgLength":5,
			// "reserve":"90002376","sPNumber":"10655502","sequenceId":-1832059249,"srcNodeId":1,
			// "timeStamp":308034111,"tpPid":0,"tpUdhi":0,"userNumber":"8615549754417"}

			// System.out.println("deliver========="+aa.toString());

			// 处理deliver
			pay.setProductId(json.getString("sPNumber"));
			pay.setPhone(json.getString("userNumber"));
			pay.setCpparam(new String(json.getBytes("msgContent")));
			// pay.setOrderId(ss.getSequenceNumberStr());
			if (!"".equals(json.getString("reserve"))
					&& json.getString("reserve") != null) {
				pay.setPayId(json.getString("reserve"));
			}
			payservice.getLtdmData(pay);
		}
		if (report != null) {

			json = JSONObject.parseObject(report);
			System.out.println("reportjson=========" + json.toJSONString());
			// SGIPReportMessage cc = JSON.parseObject(report,
			// SGIPReportMessage.class);
			// System.out.println("report========="+cc.toString());

			payservice.getLtdmReport(json);

		}
		// SGIPMsgHead ss =JSON.parseObject(head, SGIPMsgHead.class);

		return "";
	}

	/**
	 * 
	 * 短验方式提交验证码接口
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws JSONException
	 */
	@RequestMapping(value = "dyorder", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String dyorder(HttpServletRequest request,
			HttpServletResponse response) {
		JSONObject json = new JSONObject();

		String orderId = request.getParameter("orderId") != null ? request
				.getParameter("orderId") : "";

		String authCode = request.getParameter("authCode") != null ? request
				.getParameter("authCode") : "";

		System.out.println("sms code=========" + authCode);
		System.out.println("orderId=========" + orderId);
		try {
			payservice.dyPay(orderId, authCode);
		} catch (Exception e) {
			e.printStackTrace();
			return "fail";
		}

		return "ok";
	}

	/**
	 * 
	 * 短验方式提交验证码接口
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws JSONException
	 */
	@RequestMapping(value = "test", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String test(HttpServletRequest request, HttpServletResponse response) {
		String fee = request.getParameter("fee") != null ? request
				.getParameter("fee") : "";
		String phone = request.getParameter("phone") != null ? request
				.getParameter("phone") : "";
		String serviceType = request.getParameter("serviceType") != null ? request
				.getParameter("serviceType") : "";
		JSONObject json = new JSONObject();

		String str = "";

		try {
			str = payservice.test();
		} catch (Exception e) {
			return "fail";
		}

		return str;
	}
	
	/*
	 * 联通动漫反向定制接口
	 */
	@RequestMapping(value = "HttpLtdm", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String getHttpLtdm(HttpServletRequest request,
			HttpServletResponse response) {
		Payinfo pay=new Payinfo();
		String sign=request.getParameter("sign")==null?"":request.getParameter("sign");
		String appId=request.getParameter("appId")==null?"":request.getParameter("appId");
		String productId=request.getParameter("productId")==null?"":request.getParameter("productId");
		String phone=request.getParameter("phone")==null?"":request.getParameter("phone");
		String cost=request.getParameter("cost")==null?"":request.getParameter("cost");
		String cpparm=request.getParameter("cpparm");
		if (sign==null||appId==null||productId==null||phone==null||cost==null) {
			
			return "";
		}
		ReturnObj re=payservice.getLtdmMo(pay,sign,appId,productId,phone,cost);
		return JSON.toJSONString(re);
	}

}