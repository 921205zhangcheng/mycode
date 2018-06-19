package com.xh.sdk.control;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xh.sdk.model.ReturnObj;
import com.xh.sdk.service.StatisticsService;

@Controller
@RequestMapping("manage")
public class ManagerController {
	
	@Autowired
	private StatisticsService st;

	@RequestMapping(value = "ProductDay", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String ProductDay(HttpServletRequest request, HttpServletResponse response) {	
		JSONObject json = new JSONObject();
		//String result = "";
		ReturnObj re = new ReturnObj();
		String time = request.getParameter("starttime") != null ? request
				.getParameter("starttime") : "";
		String productId = request.getParameter("productId") != null ? request
				.getParameter("productId") : "";
	    String codeType = request.getParameter("codeType") != null ? request
						.getParameter("codeType") : "";

	    String yystype = request.getParameter("yystype") != null ? request
						.getParameter("yystype") : "";
		
		//Gamecode code = new Gamecode();
						System.out.println("cProductDay is run");		
		try {
			
			
			
		JSONArray	result=  st.getProductDay(time, productId, codeType, yystype);
			System.out.println(result);	
			if(result!=null&&!"".equals(result)){	
				
				json.put("statu", "0");
				json.put("data", result);
				
			}else{
	     		json.put("statu", ReturnObj.T_RESULT);
				json.put("data",ReturnObj.T_RESULT_MSG);
				//re.setT_result(ReturnObj.T_RESULT_MSG);
			}
			
		} catch (Exception e) {
			json.put("statu", ReturnObj.UNKNOW_ERROR);
			json.put("data",ReturnObj.UNKNOW_ERROR_MSG);
		}
		System.out.println(json.toJSONString());		
		return json.toJSONString();  
	}
/**
 * 
 * @param request
 * @param response
 * @return
 */

	@RequestMapping(value = "ChannelMonth", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String ChannelMonth(HttpServletRequest request, HttpServletResponse response) {	
		JSONObject json = new JSONObject();
		//String result = "";
		ReturnObj re = new ReturnObj();
		String time = request.getParameter("starttime") != null ? request
				.getParameter("starttime") : "";
		String appid = request.getParameter("appid") != null ? request
				.getParameter("appid") : "";
//	    String codeType = request.getParameter("codeType") != null ? request
//						.getParameter("codeType") : "";
//
//	    String yystype = request.getParameter("yystype") != null ? request
//						.getParameter("yystype") : "";
//		
//		//Gamecode code = new Gamecode();
//						System.out.println("cProductDay is run");		
		try {
			
			
			
			JSONArray result=  st.getChannelDuizhang(appid, time);
                if(result!=null&&!"".equals(result)){	
				
				json.put("statu", "0");
				json.put("data", result);
				
			}else{
	     		json.put("statu", ReturnObj.T_RESULT);
				json.put("data",ReturnObj.T_RESULT_MSG);
				//re.setT_result(ReturnObj.T_RESULT_MSG);
			}
			
		} catch (Exception e) {
			json.put("statu", ReturnObj.UNKNOW_ERROR);
			json.put("data",ReturnObj.UNKNOW_ERROR_MSG);
		}
		System.out.println(json.toJSONString());		
		return json.toJSONString();  
	}

	@RequestMapping(value = "tuidingDay", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String tuidingDay(HttpServletRequest request, HttpServletResponse response) {	
		JSONObject json = new JSONObject();
		//String result = "";
		ReturnObj re = new ReturnObj();
		String time = request.getParameter("starttime") != null ? request
				.getParameter("starttime") : "";
//		String productId = request.getParameter("productId") != null ? request
//				.getParameter("productId") : "";
//	    String codeType = request.getParameter("codeType") != null ? request
//						.getParameter("codeType") : "";
//
//	    String yystype = request.getParameter("yystype") != null ? request
//						.getParameter("yystype") : "";
//		
//		//Gamecode code = new Gamecode();
//						System.out.println("cProductDay is run");		
		try {
			
			
			
			JSONArray result=  st.getTuidingDay(time);
                if(result!=null&&!"".equals(result)){	
				
				json.put("statu", "0");
				json.put("data", result);
				
			}else{
	     		json.put("statu", ReturnObj.T_RESULT);
				json.put("data",ReturnObj.T_RESULT_MSG);
				//re.setT_result(ReturnObj.T_RESULT_MSG);
			}
			
		} catch (Exception e) {
			json.put("statu", ReturnObj.UNKNOW_ERROR);
			json.put("data",ReturnObj.UNKNOW_ERROR_MSG);
		}
		System.out.println(json.toJSONString());		
		return json.toJSONString();  
	}
}
