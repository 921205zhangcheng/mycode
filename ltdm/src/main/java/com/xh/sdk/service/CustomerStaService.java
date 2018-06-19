package com.xh.sdk.service;


import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.xh.sdk.common.memcached;
import com.xh.sdk.dao.userDao;
import com.xh.sdk.model.BillingCode;
import com.xh.sdk.model.BlackPhone;
import com.xh.sdk.model.Gamecode;
import com.xh.sdk.model.LtdmProductList;
import com.xh.sdk.model.Payinfo;
import com.xh.sdk.model.ReturnObj;
import com.xh.sdk.redis.RedisClientTemplate;

@Service
@Transactional(value = TxType.NOT_SUPPORTED)
public class CustomerStaService {


	@Autowired
	private memcached mc;
	@Autowired
	private RedisClientTemplate redis;
	@Autowired
	private userDao dao;

	/**
	 * 根据phone获取订购信息(查询平台)
	 * 
	 * @param phone
	 * @return
	 */	
	public List<LtdmProductList> getPayinfosByph(String phone) {
		// TODO Auto-generated method stub
		List<String> comts= dao.getLtdmmoContents();
		List<LtdmProductList> LtdmProductList=new 	ArrayList<LtdmProductList>();
		for(String  comt:comts){
			Payinfo payone=JSON.parseObject(
					redis.hget("callbackoder",("86"+phone+comt.toUpperCase())),	Payinfo.class);
			if(payone!=null&&payone.getProductId()!=null&&"".equals(payone.getProductId())==false){
				List<LtdmProductList>	LtdmProductList1= dao.getLtdmProductListById(payone);
				if(LtdmProductList1.size()>0){
				for(LtdmProductList s:LtdmProductList1){
					s.setPayaddtime(payone.getAddtime());
					LtdmProductList.add(s);					
				}
				}
			}
			
		}
		return LtdmProductList;
	}


	/**
	 * 根据phone屏蔽黑名单
	 * 
	 * @param phone
	 * @return
	 */	
	public ReturnObj toblackphone(String phone) {
		// TODO Auto-generated method stub
		ReturnObj re = new ReturnObj();
		List<BlackPhone> list1 = dao.getBlackphoneByPhone(phone);
		if(list1.size()>0){
		re.setStatu("0");
		re.setErrorMsg("此号码黑名单已存在!");
		}else{
		BlackPhone blackphone=new BlackPhone();
		blackphone.setPhone(phone);
		dao.saveBlcakPhone(blackphone);	
		redis.hset("blackphone", phone, JSON.toJSONString(blackphone));
		re.setStatu("1");
		re.setErrorMsg("屏蔽成功!");
		}
		return re;
	}

	
}
