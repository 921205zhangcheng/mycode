package com.xh.sdk.control;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.xh.sdk.model.ReturnObj;
import com.xh.sdk.service.RdService;

@Controller
@RequestMapping("rd")
public class RdController {

	@Autowired
	private RdService rd;
	
	/**
	 * 乾巍翼支付同步接过返回接口
	 * @param request
	 * @param response
	 * @return
	 */
	
	@RequestMapping(value = "yzfnotify", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String yzfnotify(HttpServletRequest request, HttpServletResponse response) {	
		
	//	http://47.92.89.150/rd/tyydnotify
	//	http://47.92.89.150/rd/tyydgetAppInfo

		String ORDERSEQ = request.getParameter("ORDERSEQ") != null ? request
				.getParameter("ORDERSEQ") : "";

		String ORDERREQTRANSEQ = request.getParameter("ORDERREQTRANSEQ") != null ? request
				.getParameter("ORDERREQTRANSEQ") : "";
		
			
		String UPTRANSEQ = request.getParameter("UPTRANSEQ") != null ? request
						.getParameter("UPTRANSEQ") : "";

		String TRANDATE = request.getParameter("TRANDATE") != null ? request
						.getParameter("TRANDATE") : "";
	    String RETNCODE = request.getParameter("RETNCODE") != null ? request
								.getParameter("RETNCODE") : "";

		String RETNINFO = request.getParameter("RETNINFO") != null ? request
										.getParameter("RETNINFO") : "";						
	   String MAC = request.getParameter("MAC") != null ? request
								.getParameter("MAC") : "";
		
		
		JSONObject json = new JSONObject();
		ReturnObj re = new ReturnObj();
		
		
		//ORDERSEQ=20170419095448001&ORDERREQTRANSEQ=20170419095448000001&UPTRANSEQ=20161021&TRANDATE=20170419&ORDERAMOUNT=1&RETNCODE=0000&RETNINFO=成功&MAC=DGLFJLJLGJLJLGJSLGJSLGJFLGLFJ
		
		
		//Gamecode code = new Gamecode();
		try {
			json.put("orderid", ORDERSEQ);
			if("0000".equals(RETNCODE)){			
		    json.put("result", "succ");			
			}else{
				   json.put("msg", RETNINFO);		
			}

			  json.put("result", RETNCODE);	
			rd.acceptResult(json) ;
			  
		
		} catch (Exception e) {
			re.setStatu(ReturnObj.UNKNOW_ERROR);
			re.setErrorMsg(ReturnObj.UNKNOW_ERROR_MSG);
		}
		return "UPTRANSEQ_upTranSeq";  
	}
	
	
	
	/**
	 * 电信爱音乐同步接过返回接口15
	 * @param request
	 * @param response
	 * @return
	 */
	
	@RequestMapping(value = "dxmusictwo", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String dxmusictwo(HttpServletRequest request, HttpServletResponse response) {	
		String mobile = request.getParameter("mobile") != null ? request
				.getParameter("mobile") : "";

		String productid = request.getParameter("productid") != null ? request
				.getParameter("productid") : "";
		
			
		String state = request.getParameter("state") != null ? request
						.getParameter("state") : "";

		String time = request.getParameter("time") != null ? request
						.getParameter("time") : "";
	    String signature = request.getParameter("signature") != null ? request
								.getParameter("signature") : "";

		String deviceid = request.getParameter("deviceid") != null ? request
										.getParameter("deviceid") : "";						
	   String timestamp = request.getParameter("timestamp") != null ? request
								.getParameter("timestamp") : "";
		
		System.out.println("music 15  "+mobile+":"+state);
		JSONObject json = new JSONObject();
		ReturnObj re = new ReturnObj();
		try {
			json.put("orderid", "phoneOrder"+mobile);
			if("0".equals(state)){			
		    json.put("result", "succ");			
			}else{
				json.put("result", state);
			}
            json.put("mobile", mobile);
        	json.put("productId", "5007");
        	json.put("codeType", "2");
			 // json.put("result", state);	
			rd.acceptResult(json) ;
			  
		
		
		} catch (Exception e) {
			re.setStatu(ReturnObj.UNKNOW_ERROR);
			re.setErrorMsg(ReturnObj.UNKNOW_ERROR_MSG);
		}
		return "0000";  
	}
	
	

	/**
	 * 电信爱音乐同步接过返回接口10
	 * @param request
	 * @param response
	 * @return
	 */
	
	@RequestMapping(value = "dxmusicone", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String dxmusicone(HttpServletRequest request, HttpServletResponse response) {	
		String mobile = request.getParameter("mobile") != null ? request
				.getParameter("mobile") : "";

		String productid = request.getParameter("productid") != null ? request
				.getParameter("productid") : "";
		
			
		String state = request.getParameter("state") != null ? request
						.getParameter("state") : "";

		String time = request.getParameter("time") != null ? request
						.getParameter("time") : "";
	    String signature = request.getParameter("signature") != null ? request
								.getParameter("signature") : "";

		String deviceid = request.getParameter("deviceid") != null ? request
										.getParameter("deviceid") : "";						
	   String timestamp = request.getParameter("timestamp") != null ? request
								.getParameter("timestamp") : "";
	 System.out.println("music 10  "+mobile+":"+state);
		
		JSONObject json = new JSONObject();
		ReturnObj re = new ReturnObj();
		try {
			json.put("orderid", "phoneOrder"+mobile);
			if("0".equals(state)){			
		    json.put("result", "succ");			
			}else{
				json.put("result", state);
			}
            json.put("mobile", mobile);
			 // json.put("result", state);	
        	json.put("productId", "5006");
        	json.put("codeType", "2");
			rd.acceptResult(json) ;
			  
		
		} catch (Exception e) {
			re.setStatu(ReturnObj.UNKNOW_ERROR);
			re.setErrorMsg(ReturnObj.UNKNOW_ERROR_MSG);
		}
		return "0000";  
	}
	/**
	 * 电信爱音乐同步接过返回接口20
	 * @param request
	 * @param response
	 * @return
	 */
	
	@RequestMapping(value = "dxmusicthree", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String dxmusicfive(HttpServletRequest request, HttpServletResponse response) {	
		
	
		
		String mobile = request.getParameter("mobile") != null ? request
				.getParameter("mobile") : "";

		String productid = request.getParameter("productid") != null ? request
				.getParameter("productid") : "";
		
			
		String state = request.getParameter("state") != null ? request
						.getParameter("state") : "";

		String time = request.getParameter("time") != null ? request
						.getParameter("time") : "";
	    String signature = request.getParameter("signature") != null ? request
								.getParameter("signature") : "";

		String deviceid = request.getParameter("deviceid") != null ? request
										.getParameter("deviceid") : "";						
	   String timestamp = request.getParameter("timestamp") != null ? request
								.getParameter("timestamp") : "";
		
								System.out.println("music 20  "+mobile+":"+state);
		JSONObject json = new JSONObject();
		ReturnObj re = new ReturnObj();
		try {
			json.put("orderid", "phoneOrder"+mobile);
			if("0".equals(state)){			
		    json.put("result", "succ");			
			}else{
				json.put("result", state);
			}
            json.put("mobile", mobile);
			 // json.put("result", state);	
        	json.put("productId", "5008");
        	json.put("codeType", "2");
			rd.acceptResult(json) ;
			  
		
		} catch (Exception e) {
			re.setStatu(ReturnObj.UNKNOW_ERROR);
			re.setErrorMsg(ReturnObj.UNKNOW_ERROR_MSG);
		}
		return "0000";  
	}
	//http://115.159.53.238/comm/callback?mobile=xxxx&linkid=xxxx&spnumber=xxx&content=xxx&state=x
	
	/**
	 * 余露志信方达
	 * @param request
	 * @param response
	 * @return
	 */
	
	@RequestMapping(value = "ylzxfdNotify", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String ylzxfdNotify(HttpServletRequest request, HttpServletResponse response) {	
		
	System.out.println("ylzxfdNotify is run");
		
		String mobile = request.getParameter("mobile") != null ? request
				.getParameter("mobile").trim() : "";

		String linkid = request.getParameter("linkid") != null ? request
				.getParameter("linkid").trim() : "";
		
			
		String state = request.getParameter("state") != null ? request
						.getParameter("state").trim() : "";

		String spnumber = request.getParameter("spnumber") != null ? request
						.getParameter("spnumber") .trim(): "";
	    String content = request.getParameter("content") != null ? request
								.getParameter("content").trim() : "";

		JSONObject json = new JSONObject();
		ReturnObj re = new ReturnObj();
		try {
			json.put("orderid", "phoneOrder"+mobile);
			if("1".equals(state)){			
		    json.put("result", "succ");			
			}else if("0".equals(state)){
				json.put("result", "1");
			}else{		
				json.put("result", state);	
			}
            json.put("mobile", mobile);
			 // json.put("result", state);	
            if("Z20".equals(content)||"TDZ20".equals(content)){
            	json.put("productId", "50010");
            }
            if("LB1".equals(content)||"TDLB1".equals(content)){
            	json.put("productId", "50011");
            }
            json.put("smscontent", content);

        	json.put("codeType", "2");
			rd.acceptResult(json) ;
			  
		
		} catch (Exception e) {
			re.setStatu(ReturnObj.UNKNOW_ERROR);
			re.setErrorMsg(ReturnObj.UNKNOW_ERROR_MSG);
		}
		return "0000";  
	}
	/**
	 * 成都鼎元沃加同步接口20
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "cddynotify", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String cddynotify(HttpServletRequest request, HttpServletResponse response) {	
		
	//	http://47.92.89.150/rd/tyydnotify
	//	http://47.92.89.150/rd/tyydgetAppInfo
		
		
		System.out.println("cddywo is run");
		
		Enumeration enu=request.getParameterNames();  
		while(enu.hasMoreElements()){  
		String paraName=(String)enu.nextElement();  
		System.out.println(paraName+": "+request.getParameter(paraName));  
		}

		String result = request.getParameter("result") != null ? request
				.getParameter("result") : "";

		String orderId = request.getParameter("orderId") != null ? request
				.getParameter("orderId") : "";
		
			
		String orderSN = request.getParameter("orderSN") != null ? request
						.getParameter("orderSN") : "";

		String amount = request.getParameter("amount") != null ? request
						.getParameter("amount") : "";
	    String imsi = request.getParameter("imsi") != null ? request
								.getParameter("imsi") : "";

		String mobile = request.getParameter("mobile") != null ? request
										.getParameter("mobile") : "";						
	   String provinceName = request.getParameter("provinceName") != null ? request
								.getParameter("provinceName") : "";
		
		
		JSONObject json = new JSONObject();
		ReturnObj re = new ReturnObj();
		
		
		try {
			json.put("orderid", orderId);
			if("0".equals(result)){			
		    json.put("result", "succ");			
			}
			json.put("cost", "10");
		//	json.put("amount", amount);	
			System.out.println(json.toJSONString());
			rd.acceptResult(json) ;
			  
		
		} catch (Exception e) {
			re.setStatu(ReturnObj.UNKNOW_ERROR);
			re.setErrorMsg(ReturnObj.UNKNOW_ERROR_MSG);
		}
		return "ok";  
	}

	
	
	/**
	 * 成都鼎元联通在信同步接口
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "dyzxnotify", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String dyzxnotify(HttpServletRequest request, HttpServletResponse response) {
		
		
		//S;

	//	http://47.92.89.150/rd/tyydnotify
	//	http://47.92.89.150/rd/tyydgetAppInfo
		System.out.println("ltzx is run");
		Enumeration enu=request.getParameterNames();  
		while(enu.hasMoreElements()){  
		String paraName=(String)enu.nextElement();  
		System.out.println(paraName+": "+request.getParameter(paraName));  
		}

		String flag = request.getParameter("flag") != null ? request
				.getParameter("flag") : "";

		String mobile = request.getParameter("mobile") != null ? request
				.getParameter("mobile") : "";
		
			
		String linkid  = request.getParameter("linkid ") != null ? request
						.getParameter("linkid ") : "";


		
		JSONObject json = new JSONObject();
		json.put("mobile", mobile);
		json.put("payType", "4");
		json.put("orderid", "linkid");
		json.put("cost", "10");
		if("2".equals(flag)){
			
			json.put("result", "succ");
			
		}else{
			
			
			json.put("result",flag);	
		}
		rd.acceptResult(json) ;
		return "ok";  
	}
	/**
	 * 成都鼎元联通在信同步接口
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "ayynotify", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String ayynotify(HttpServletRequest request, HttpServletResponse response) {
		
		
		//S;

	//	http://47.92.89.150/rd/tyydnotify
	//	http://47.92.89.150/rd/tyydgetAppInfo
		//System.out.println("ltzx is run");
		Enumeration enu=request.getParameterNames();  
		while(enu.hasMoreElements()){  
		String paraName=(String)enu.nextElement();  
		System.out.println(paraName+": "+request.getParameter(paraName));  
		}

		String state = request.getParameter("state") != null ? request
				.getParameter("state") : "";

		String mobile = request.getParameter("mobile") != null ? request
				.getParameter("mobile") : "";
		
			
		String productid  = request.getParameter("productid ") != null ? request
						.getParameter("productid ") : "";


		
		JSONObject json = new JSONObject();
		json.put("mobile", mobile);
		json.put("payType", "4");
		json.put("orderid", "linkid");
		json.put("cost", "10");
		if("0".equals(state)){
			
			json.put("result", "succ");
			
		}else{
			
			
			json.put("result",state);	
		}
		rd.acceptResult(json) ;
		return "0000";  
	}
	/**
	 * 电信阅读包月返回接口30
	 * @param request
	 * @param response
	 * @return
	 */
	
	@RequestMapping(value = "dxydNotify", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String dxydNotify(HttpServletRequest request, HttpServletResponse response) {	
		
	
		
		String mobile = request.getParameter("phone") != null ? request
				.getParameter("phone") : "";

		String cporderno = request.getParameter("cporderno") != null ? request
				.getParameter("cporderno") : "";
		
			
		String state = request.getParameter("status") != null ? request
						.getParameter("status") : "";

		String time = request.getParameter("time") != null ? request
						.getParameter("time") : "";
	 
		String price = request.getParameter("price") != null ? request
										.getParameter("price") : "";						
		JSONObject json = new JSONObject();
		ReturnObj re = new ReturnObj();
		try {
			json.put("orderid", cporderno);
			if("0000".equals(state)){			
		    json.put("result", "succ");			
			}else{
				json.put("result", state);
			}
            json.put("mobile", mobile);
			 // json.put("result", state);	
        	//json.put("productId", "5008");
        	json.put("codeType", "2");
			rd.acceptResult(json) ;
			  
		
		} catch (Exception e) {
			re.setStatu(ReturnObj.UNKNOW_ERROR);
			re.setErrorMsg(ReturnObj.UNKNOW_ERROR_MSG);
		}
		return "ok";  
	}

}
