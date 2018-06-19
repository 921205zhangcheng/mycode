package com.xh.sdk.control;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xh.sdk.model.LtdmProductList;
import com.xh.sdk.model.Payinfo;
import com.xh.sdk.model.ReturnObj;
import com.xh.sdk.service.CustomerStaService;
import com.xh.sdk.service.SdkService;

@Controller
@RequestMapping("customer")
public class CustomerStatisController {

	@Autowired
	private CustomerStaService customerservice;
	
	@RequestMapping(value = "getPayinfosByphone", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String getPay(HttpServletRequest request, HttpServletResponse response) {	
		JSONObject json = new JSONObject();
		try {
			
			String phone = request.getParameter("phone");			
		    System.out.println(phone+"-------------"+phone);		    
		    List<LtdmProductList> payinfos=customerservice.getPayinfosByph(phone);	
		    if(payinfos.size()>0){
		    json.put("statu", "1");
			json.put("errorMsg", "查询成功!");
		    json.put("phonecall", payinfos);
		    }else{
		     json.put("statu", "0");
			json.put("errorMsg", "没有数据!");
		    }
		
		} catch (Exception e) {
			json.put("statu", ReturnObj.UNKNOW_ERROR);
			json.put("errorMsg", ReturnObj.UNKNOW_ERROR_MSG);
		}
		return json.toString();  
	}

	
	@RequestMapping(value = "toblackphone", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String toblackphone(HttpServletRequest request, HttpServletResponse response) {	
		ReturnObj re = new ReturnObj();
		try {
			
			String phone = request.getParameter("phone");			
		    System.out.println("-------------"+phone);		    
		    re=customerservice.toblackphone(phone.trim());		   
		} catch (Exception e) {
			re.setStatu("0");
			re.setErrorMsg("屏蔽失败!");		
		}
		return JSON.toJSONString(re);  
	}


}