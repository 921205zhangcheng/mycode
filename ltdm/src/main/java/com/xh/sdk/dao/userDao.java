package com.xh.sdk.dao;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.xh.sdk.model.BillingCode;
import com.xh.sdk.model.BlackPhone;
import com.xh.sdk.model.ChannelCodeInfo;
import com.xh.sdk.model.ChannelInfo;
import com.xh.sdk.model.DxprovCode;
import com.xh.sdk.model.LtdmChannelInfo;
import com.xh.sdk.model.LtdmOldUser;
import com.xh.sdk.model.LtdmProductInfo;
import com.xh.sdk.model.LtdmProductList;
import com.xh.sdk.model.Payinfo;
import com.xh.sdk.model.PayinfoDay;
import com.xh.sdk.model.StatisticsObj;
import com.xh.sdk.model.TuidingObj;



@Repository
public interface userDao {
	

	/**
	 * 新增订单
	 * @param pay
	 * @throws Exception
	 */
	public void savePayinfo(Payinfo pay);


	
	/**
	 * 获取代码列表
	 * @return
	 * @throws Exception
	 */
	public List<BillingCode> find();
	
	/**
	 * 根据订单号修改订单发送状态
	 * @param result
	 * @param orderId
	 */
	public void confirmPayInfo(Payinfo p);
	
	/**
	 * 修改订单的result标志
	 * @param pay
	 * @throws Exception
	 */
	public void updatePayinfoByOrderId(Payinfo pay);
	
	/**
	 * 获取省份列表
	 * @return
	 */
	public List<DxprovCode> dxfind();
	
	/**
	 * 获取渠道列表
	 */
	public List<ChannelInfo> getChannelInfo();
	
	
	
	/**
	 * 获取黑名单
	 */
	public List<BlackPhone> getBlackphone();

		
	/**
	 * 
	 * 获取所有代码渠道对应
	 */
	public List<ChannelCodeInfo> getChannelCodeInfo();
	
	
	/**
	 * 
	 * 获取联通动漫产品列表
	 */
	public List<LtdmProductList> getLtdmProductList();
	
	/**
	 * 
	 * 获取联通动漫产品列表
	 */
	public List<LtdmProductInfo> getLtdmProductInfo();
	
	/**
	 * 
	 * 按产品日报表
	 */
	public List<HashMap> getProductDay(StatisticsObj st);
	
	
	
	/**
	 * 
	 * 退订日报表
	 */
	public List<HashMap> getTuidingDay(TuidingObj st);
	
	
	/**
	 * 
	 * 保存退订订单
	 */
	public void saveTuiding(TuidingObj st);
	
	
	
	/**
	 * 
	 * 获取所有订购老用户
	 */
	public List<LtdmOldUser>getLtdmOldUserList();
	
	
	/**
	 * 
	 * 获取所有符合下单规则的老用户
	 */
	public List<LtdmOldUser>getXdOldUserList();
	
	
	
	
	/**
	 * 
	 * 更改老用户订单
	 */
	public void saveLtdmOldUser(LtdmOldUser st);
	/**
	 * 存入141代码
	 * @param pay
	 */
//	public void saveWFPay(Payinfo pay);
//
//	public void confirmwfPay(Payinfo pay);

/**
 * 按渠道月报
 * @param st
 * @return
 */

	public List<HashMap> getChannelMonth(StatisticsObj st);
	//新增插入redis代码2018-03-13
	 public List<LtdmChannelInfo> getLtdmChannelInfo();

	 public List<PayinfoDay> getPayInfosum(Payinfo payinfo);



	public void savePayinfoDay(PayinfoDay payinfoDay);


//2016-03-27yuhc  查询所有指令数据
	public List<String> getLtdmmoContents();


  //通过id查询产品数据
	public List<LtdmProductList> getLtdmProductListById(Payinfo payone);


   //从黑名单表里查询是否存在这个手机号
	public List<BlackPhone> getBlackphoneByPhone(String phone);


    //往黑名单保存数据,屏蔽功能
	public void saveBlcakPhone(BlackPhone blackphone);


   //往老用户表里插数据
	public void insetrolduser(Payinfo payinfo);
	 
	//获取每个PayInfo表里的成功数据
	public List<Payinfo> getPayInfos(Payinfo payinfo);
	 
}
