package com.xh.sdk.common;



import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonEncoding;

import sun.misc.BASE64Encoder;

public class musicTest {

	private static final Logger logger = Logger.getLogger(musicTest.class);

	/* 爱音乐分配的deviceID */
	public static final String DEVICEID = "1000010408540";

	/* 爱音乐分配的devicePwd */
	public static final String DEVICE_SECRET = "u6gWn4K6rgZA";

	/* 爱音乐分配的渠道号 */
	public static final String CHANNELID = "4288";

	/* 加密方法， 固定取值 HmacSHA1 */
	public static final String SIGNATUREMETHOD = "HmacSHA1";

	/* 接口地址 */
	public static final String InterfaceURL = "http://api.118100.cn/openapi/services/v2/package/packageservice/emplanunched.json";

	/**
	 * 签名加密函数
	 * 
	 * @param secret
	 *            加密密钥
	 * @param data
	 *            明文
	 * @return 签名密文
	 * */
	public static String generateMacSignature(String secret, String data) {
		byte[] byteHMAC = null;
		try {
			Mac mac = Mac.getInstance(SIGNATUREMETHOD);
			SecretKey secretKey = new SecretKeySpec(secret.getBytes("utf-8"),
					SIGNATUREMETHOD);

			mac.init(secretKey);
			byteHMAC = mac.doFinal(data.getBytes("utf-8"));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		String result = new BASE64Encoder().encode(byteHMAC);
		return result;
	}

	/**
	 * 设置鉴权相关的header
	 * 
	 * @param method
	 *            被设置的http请求
	 * @param nvps
	 *            请求携带的参数
	 * */
	public static void setHeader(HttpMethod method, List<NameValuePair> nvps) {
		String auth_deviceid = DEVICEID;
		String auth_channelid = CHANNELID;
		String auth_signature_method = SIGNATUREMETHOD;
		String auth_device_secret = DEVICE_SECRET;
		java.text.SimpleDateFormat date = new SimpleDateFormat("yyyyMMddhhmmss");
		String timestamp = date.format(new Date());
		method.setRequestHeader("auth-deviceid", auth_deviceid);
		method.setRequestHeader("auth-channelid", auth_channelid);
		method.setRequestHeader("auth-timestamp", timestamp);
		method.setRequestHeader("auth-signature-method", auth_signature_method);

		/* 根据传入的参数计算签名 */
		StringBuffer sb = new StringBuffer();
		sb.append(auth_deviceid).append("&");
		sb.append(auth_channelid).append("&");
		sb.append(timestamp).append("&");
		if (nvps != null && nvps.size() > 0) {
			for (NameValuePair n : nvps) {
				sb.append(n.getValue()).append("&");
			}
		}
		String data = sb.substring(0, sb.length() - 1);
		System.out.println(data);
		String auth_signature = generateMacSignature(auth_device_secret, data);
		method.setRequestHeader("auth-signature", auth_signature);
	}

	public static String httpPostTool(List<NameValuePair> nvps, String url) {
		long stime = System.currentTimeMillis();
		HttpClient client = new HttpClient();
		client.getParams().setContentCharset("UTF-8");
		client.getParams().setHttpElementCharset("UTF-8");
		System.out.println("request url-->" + url);

		PostMethod postMethod = new PostMethod(url);
		NameValuePair[] nvpArr = new NameValuePair[nvps.size()];
		nvps.toArray(nvpArr);
		postMethod.setRequestBody(nvpArr);
		setHeader(postMethod, nvps);
		int sc = 0; // 响应吗
		String rspStr = null; // 响应消息体
		try {
			sc = client.executeMethod(postMethod);
			if (sc == HttpStatus.SC_OK) {
				byte[] rsp = postMethod.getResponseBody();
				rspStr = new String(rsp, JsonEncoding.UTF8.getJavaName());
			}
		} catch (HttpException e) {
			logger.error(e);
		} catch (IOException e) {
			logger.error(e);
		} finally {
			postMethod.releaseConnection();
		}

		logger.debug("http exe time:" + (System.currentTimeMillis() - stime)
				+ " ms");

		logger.info(sc + "--" + rspStr);
		return rspStr;
	}

	public static void main(String[] args) {
		// 请求参数
		String mdn = "18967199935";

		// 请求结果
		String result = null;

		// 构造请求参数nvp
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
	
		nvps.add(new NameValuePair("mdn", mdn));
		nvps.add(new NameValuePair("package_id", "135000000000000246502"));
		nvps.add(new NameValuePair("column", "1111"));
	

		// 发起请求
		result = httpPostTool(nvps, InterfaceURL);

		System.out.println("result-->" + result);
	}
}