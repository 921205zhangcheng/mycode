package com.xh.sdk.redis;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.alibaba.fastjson.JSON;
import com.xh.sdk.dao.userDao;
import com.xh.sdk.model.BillingCode;
import com.xh.sdk.model.BlackPhone;
import com.xh.sdk.model.ChannelCodeInfo;
import com.xh.sdk.model.ChannelInfo;
import com.xh.sdk.model.DxprovCode;
import com.xh.sdk.model.LtdmChannelInfo;
import com.xh.sdk.model.LtdmProductInfo;
import com.xh.sdk.model.LtdmProductList;

@Repository
public class RedisDao {

	@Autowired
	private RedisClientTemplate redis;
	
	@Autowired
	private userDao userdao;
	/**
	 * @param args
	 *            清空所有redis数据 同步所有数据 到redis
	 */
	public void synallToRedis() {
		
		System.out.println("test is run");
		
		List<LtdmProductInfo> list3 = userdao.getLtdmProductInfo();
		List<DxprovCode> list4 = userdao.dxfind();
		List<LtdmProductList> list5 = userdao.getLtdmProductList();
		List<LtdmChannelInfo> list6 = userdao.getLtdmChannelInfo();
		this.synDxprovCodeToRedis(list4, "0");
		this.synLtdmProductListToRedis(list5, "0");
	    this.synLtdmChannelInfoToRedis(list6, "0");
		this.synLtdmProductInfoToRedis(list3, "0");
		this.synLtdmChanelProductToRedis(list5, "0");


	}
/**
 * 更新产品表进入redis
 * @param list
 * @param flag
 */
	private void synLtdmProductInfoToRedis(List<LtdmProductInfo> list,
			String flag) {
		// TODO Auto-generated method stub
		if("0".equals(flag)){
		redis.del("LtdmProduct");
		}
		
		System.out.println("LtdmProduct========"+list.size());
		
		for (int i = 0; i < list.size(); i++) {
			LtdmProductInfo code = list.get(i);	
			
			System.out.println("LtdmProduct=================="+JSON.toJSONString(code));
			redis.hset("LtdmProduct", code.getServiceType(), JSON.toJSONString(code));			
		}
		
	}

	
	
	/**
	 * 更新电信省份对应表
	 * @param list
	 * @param flag
	 * flag = '0' 全部更新，flag = '1';部分更新        
	 */

	
	private void synLtdmChanelProductToRedis(List<LtdmProductList> list,String flag) {
		if("0".equals(flag)){
		redis.del("LtdmChanelProduct");
		}
		for (int i = 0; i < list.size(); i++) {
			LtdmProductList code = list.get(i);	
			redis.hset("LtdmChanelProduct", code.getServiceType()+"100"+code.getCompanyId(), JSON.toJSONString(code));			
		}

	}
	
	
	
	/**
	 * @param args
	 *  更新渠道代码对应表进入redis
	 *   flag = '0' 全部更新，flag = '1';部分更新        
	 */
	private void synChannelCodeToRedis(List<ChannelCodeInfo> list,String flag) {
		if("0".equals(flag)){
		redis.del("ChannelCodeInfo");
		}
		for (int i = 0; i < list.size(); i++) {
			ChannelCodeInfo code = list.get(i);	
			redis.hset("ChannelCodeInfo", code.getAppId()+code.getProductId(), JSON.toJSONString(code));			
		}

	}
	
	/**
	 * @param args
	 *  更新代码对应表进入redis
	 *   flag = '0' 全部更新，flag = '1';部分更新        
	 */
	private void synBillingCodeToRedis(List<BillingCode> list,String flag) {
		if("0".equals(flag)){
		redis.del("BillingCode");
		}
		for (int i = 0; i < list.size(); i++) {
			BillingCode code = list.get(i);	
			redis.hset("BillingCode", "500"+code.getId()+"", JSON.toJSONString(code));			
		}

	}
	
	/**
	 * @param args
	 *  更新黑名单进入redis
	 *   flag = '0' 全部更新，flag = '1';部分更新        
	 */
	private void synblackphoneToRedis(List<BlackPhone> list,String flag) {
		if("0".equals(flag)){
		redis.del("blackphone");
		}
		for (int i = 0; i < list.size(); i++) {
			BlackPhone code = list.get(i);	
			redis.hset("blackphone", code.getPhone(), JSON.toJSONString(code));			
		}

	}
	
	/**
	 * @param args
	 *  更新渠道信息进入redis
	 *   flag = '0' 全部更新，flag = '1';部分更新        
	 */
	private void synChannelInfoToRedis(List<ChannelInfo> list,String flag) {
		if("0".equals(flag)){
		redis.del("ChannelInfo");
		}
		for (int i = 0; i < list.size(); i++) {
			ChannelInfo code = list.get(i);	
			redis.hset("ChannelInfo", "100"+code.getId(), JSON.toJSONString(code));			
		}

	}
	/**
	 * 更新电信省份对应表
	 * @param list
	 * @param flag
	 * flag = '0' 全部更新，flag = '1';部分更新        
	 */

	
	private void synDxprovCodeToRedis(List<DxprovCode> list,String flag) {
		if("0".equals(flag)){
		redis.del("dxlist");
		}
		for (int i = 0; i < list.size(); i++) {
			DxprovCode dc = list.get(i);
			redis.rpush("dxList", JSON.toJSONString(dc));			
		}

	}
	
	/**
	 * 更新电信省份对应表
	 * @param list
	 * @param flag
	 * flag = '0' 全部更新，flag = '1';部分更新        
	 */

	
	private void synLtdmProductListToRedis(List<LtdmProductList> list,String flag) {
		if("0".equals(flag)){
		redis.del("LtdmList");
		}
		for (int i = 0; i < list.size(); i++) {
			LtdmProductList code = list.get(i);	
			redis.hset("LtdmList", code.getSpNumber()+code.getMoContent(), JSON.toJSONString(code));			
		}

	}
	//redis 增加ltdmChannelInfo2018-03-13
		/**
		 * @param args
		 *  更新渠道信息进入redis
		 *   flag = '0' 全部更新，flag = '1';部分更新        
		 */
		private void synLtdmChannelInfoToRedis(List<LtdmChannelInfo> list,String flag) {
			if("0".equals(flag)){
				redis.del("dxlist");
				}
				for (int i = 0; i < list.size(); i++) {
					LtdmChannelInfo dc = list.get(i);
					redis.rpush("LtdmChannelInfo", JSON.toJSONString(dc));			
				}

		}
}
