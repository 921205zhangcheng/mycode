package com.xh.sdk.common;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpState;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.codehaus.jackson.JsonEncoding;
import org.springframework.stereotype.Component;

@Component
public class HttpPost {
	static class Mybuf {
		public byte buf[];
		public int size;

		public Mybuf(byte b[], int s) {
			buf = b;
			size = s;
		}
	}

	public static String mobileHttpPost(String requrl, String xml)
			throws Exception {
		PostMethod hm1 = null;
		String imsg = null;
		HttpClient hc = new HttpClient();
		try {
			hm1 = new PostMethod(requrl);
			hm1.setRequestHeader("Content-Type", "text/html;charset=UTF-8");
			RequestEntity re = new StringRequestEntity(xml, "text/xml", "utf-8");
			hm1.setRequestEntity(re);
			hm1.getParams().setSoTimeout(30000);
			hc.executeMethod(hm1);
			imsg = new String(hm1.getResponseBodyAsString().getBytes(
					"ISO-8859-1"), "UTF-8");
			// System.out.println("你好" + imsg);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			hm1.releaseConnection();
			hc.getHttpConnectionManager().closeIdleConnections(0);

		}
		return imsg;
	}

	/**
	 * http get 非登录，无参数
	 * 
	 * @param requrl
	 * @param cookies
	 * @return
	 * @throws Exception
	 */
	public String mobileHttpGet(String requrl) throws Exception {
		GetMethod hm1 = null;
		HttpClient hc = new HttpClient();
		String imsg = "";
		try {
			// hc.getParams().setCookiePolicy(CookiePolicy.RFC_2109);
			// HttpState initialState = new HttpState();
			// initialState.addCookie(cookie);
			// hc.setState(initialState);

			hm1 = new GetMethod(requrl);
			hm1.getParams().setSoTimeout(5000);
			hc.executeMethod(hm1);
			/*
			 * imsg = new String(hm1.getResponseBodyAsString().getBytes(
			 * "ISO-8859-1"), "UTF-8");
			 */
			imsg = hm1.getResponseBodyAsString();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			hm1.releaseConnection();
			hc.getHttpConnectionManager().closeIdleConnections(0);

		}

		// InputStream in = hm1.getResponseBodyAsStream();
		// System.out.println(convertStreamToString(in));

		return imsg;
	}

	/**
	 * http post 非登录，有参数
	 * 
	 * @param requrl
	 * @param cookies
	 * @return
	 * @throws Exception
	 */
	public static String mobileHttpGet(String requrl, NameValuePair[] pair)
			throws Exception {
	
		     
		GetMethod hm1 = null;
		HttpClient hc = new HttpClient();
		String imsg = "";
		String url = "";

		try {
			for (int i = 0; i < pair.length; i++) {

				if (url.equals("")) {
					url = url + "?" + pair[i].getName() + "="
							+ pair[i].getValue();
				} else {
					url = url + "&" + pair[i].getName() + "="
							+ pair[i].getValue();
				}

			}
			requrl = requrl+url;
			System.out.println("==========" + requrl);
			hm1 = new GetMethod(requrl);
		//	hm1.addRequestHeader("Content-type" , "text/html; charset=utf-8");
			hm1.getParams().setSoTimeout(30000);
			hc.executeMethod(hm1);
			imsg = hm1.getResponseBodyAsString();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			hm1.releaseConnection();
			hc.getHttpConnectionManager().closeIdleConnections(0);

		}

		// InputStream in = hm1.getResponseBodyAsStream();
		// System.out.println(convertStreamToString(in));

		return imsg;
	}

	// 登录
	public static String mobileHttpGets(String requrl, NameValuePair[] data)
			throws Exception {
		HashMap map = new HashMap();

		PostMethod hm1 = null;
		HttpClient hc = new HttpClient();
		String imsg = "";
		// 模拟cookie
		try {

			hm1 = new PostMethod(requrl);
			hm1.getParams().setSoTimeout(5000);
			if (data != null) {
				hm1.setRequestBody(data);
			}

			hc.executeMethod(hm1);
			// imsg = new String(hm1.getResponseBodyAsString().getBytes(
			// "ISO-8859-1"), "UTF-8");
			imsg = hm1.getResponseBodyAsString();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			hm1.releaseConnection();
			hc.getHttpConnectionManager().closeIdleConnections(0);

		}
		System.out.println(imsg);
		return imsg;
	}
	
	
	public static void setHeader(HttpMethod method,NameValuePair[] nvps) {
		String auth_deviceid = "1000010408540";
		String auth_channelid = "4288";
		String auth_signature_method = "HmacSHA1";
		String auth_device_secret = "u6gWn4K6rgZA";
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
		if (nvps != null && nvps.length > 0) {
			for (NameValuePair n : nvps) {
				sb.append(n.getValue()).append("&");
			}
		}
		String data = sb.substring(0, sb.length() - 1);
		System.out.println(data);
		String auth_signature = LpTools.generateMacSignature(auth_device_secret, data);
		method.setRequestHeader("auth-signature", auth_signature);
	}
	
	
	// 登录
	public static String musicHttpGets(String url, NameValuePair[] nvps)
			throws Exception {
		long stime = System.currentTimeMillis();
		HttpClient client = new HttpClient();
		client.getParams().setContentCharset("UTF-8");
		client.getParams().setHttpElementCharset("UTF-8");
		System.out.println("request url-->" + url);

		PostMethod postMethod = new PostMethod(url);
		//NameValuePair[] nvpArr = new NameValuePair[nvps.length];
		//nvps.toArray(nvpArr);
		postMethod.setRequestBody(nvps);
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

		} catch (IOException e) {
	
		} finally {
			postMethod.releaseConnection();
		}

		
		return rspStr;
	}
	
	
	
	/*
	 * 
	 */
	 public static String sendGet(String url, String ip) {
	        String result = "";
	        String param = "ak=NAGD6TWt1N8Bxup0pNhRi8SwEQppCW1p&"+"ip="+ip;
	        BufferedReader in = null;
	        try {
	            String urlNameString = url + "?" + param;
	            URL realUrl = new URL(urlNameString);
	            // 打开和URL之间的连接
	            URLConnection connection = realUrl.openConnection();
	            // 设置通用的请求属性
	            connection.setRequestProperty("accept", "*/*");
	            connection.setRequestProperty("connection", "Keep-Alive");
	            connection.setRequestProperty("user-agent",
	                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
	            // 建立实际的连接
	            connection.connect();
	            // 获取所有响应头字段
	            Map<String, List<String>> map = connection.getHeaderFields();
	            // 遍历所有的响应头字段
//	            for (String key : map.keySet()) {
//	                System.out.println(key + "--->" + map.get(key));
//	            }
	            // 定义 BufferedReader输入流来读取URL的响应
	            in = new BufferedReader(new InputStreamReader(
	                    connection.getInputStream()));
	            String line;
	            while ((line = in.readLine()) != null) {
	                result += line;
	            }
	        } catch (Exception e) {
	            System.out.println("发送GET请求出现异常！" + e);
	            e.printStackTrace();
	        }
	        // 使用finally块来关闭输入流
	        finally {
	            try {
	                if (in != null) {
	                    in.close();
	                }
	            } catch (Exception e2) {
	                e2.printStackTrace();
	            }
	        }
	        return result;
	    }
	/**
	 * 晨信发送
	 */
	public static String sendPost(String url, String param, String ip
			) {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			conn.setRequestProperty("ak", "fIcoo80wkE94cj3sAy9x5z8yBtIMizAG");
			conn.setRequestProperty("ip", ip);
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			// 发送请求参数
			out.print(param);
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应 ]
			InputStreamReader isr = new InputStreamReader(
					conn.getInputStream(), "UTF-8");
			in = new BufferedReader(isr);
			// System.out.println("发送 POST 请求出现异常！" + in);

			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("发送 POST 请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输出流、输入流
		finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}

	public String doPostCmd(String strURL, String req) {
		String result = null;
		BufferedInputStream in = null;
		BufferedOutputStream out = null;
		try {
			URL url = new URL(strURL);
			URLConnection con = url.openConnection();
			con.setUseCaches(false);
			con.setDoInput(true);
			con.setDoOutput(true);
			out = new BufferedOutputStream(con.getOutputStream());
			byte outBuf[] = req.getBytes("UTF-8");
			out.write(outBuf);

			out.close();
			in = new BufferedInputStream(con.getInputStream());
			result = ReadByteStream(in);
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.print(ex);
			return "";
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
				}
			}
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
				}
			}
		}
		if (result == null) {
			return "";
		} else {
			return result;
		}
	}

	private static String ReadByteStream(BufferedInputStream in)
			throws IOException {
		LinkedList<Mybuf> bufList = new LinkedList<Mybuf>();
		int size = 0;
		byte buf[];
		do {
			buf = new byte[128];
			int num = in.read(buf);
			if (num == -1) {
				break;
			}
			size += num;
			bufList.add(new Mybuf(buf, num));
		} while (true);
		buf = new byte[size];
		int pos = 0;

		for (ListIterator<Mybuf> p = bufList.listIterator(); p.hasNext();) {
			Mybuf b = p.next();
			for (int i = 0; i < b.size;) {
				buf[pos] = b.buf[i];
				i++;
				pos++;
			}
		}
		return new String(buf, "UTF-8");
	}

	public static String doPostCmdByHttpClient(String strURL,
			NameValuePair[] data) {
		String result = null;
		HttpClient httpClient = new HttpClient();
		HttpConnectionManagerParams managerParams = httpClient
				.getHttpConnectionManager().getParams();
		// 设置连接超时时间(单位毫秒)
		managerParams.setConnectionTimeout(30000);
		// 设置读数据超时时间(单位毫秒)
		managerParams.setSoTimeout(300000);
		PostMethod postMethod = new PostMethod(strURL);
		try {
			postMethod.setRequestBody(data);
			httpClient.getParams().setContentCharset("UTF-8");
			int statusCode = httpClient.executeMethod(postMethod);
			System.out.println(statusCode);
			if (statusCode != HttpStatus.SC_OK) {
				throw new IllegalStateException("Method failed: "
						+ postMethod.getStatusLine());
			}
			result = postMethod.getResponseBodyAsString();
		} catch (Exception ex) {
			ex.printStackTrace();
			// throw new IllegalStateException(ex.toString());
			return "";

		} finally {
			// 释放连接
			postMethod.releaseConnection();
		}
		// System.out.println(result);
		return result;

	}
	
	public static String doPostSendJsonData(String url , String jsonObject) {
		BufferedReader reader = null;
		StringBuffer sb = new StringBuffer("");
        try {
            //创建连接
            URL u = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) u
                    .openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod("POST");
            connection.setUseCaches(false);
            connection.setInstanceFollowRedirects(true);
            connection.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");

            connection.connect();

            //POST请求
            DataOutputStream out = new DataOutputStream(
                    connection.getOutputStream());
            out.writeBytes(jsonObject);
            out.flush();
            out.close();

            //读取响应
            reader = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String lines;
            
            while ((lines = reader.readLine()) != null) {
                lines = new String(lines.getBytes(), "utf-8");
                sb.append(lines);
            }
            
            // 断开连接
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        } finally {
			// 释放连接       	
        	if(reader != null){
        		try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
        	}
		}
        return sb.toString();
	}
	
	/**
	 * http post 有参数
	 * 
	 * @param requrl
	 * @param cookies
	 * @return
	 * @throws Exception
	 */
	public static String httpGets(String requrl, NameValuePair[] pair)
			throws Exception {
	
		     
		GetMethod hm1 = null;
		HttpClient hc = new HttpClient();
		String imsg = "";
		String url = "";

		try {
			for (int i = 0; i < pair.length; i++) {
				url = url + "&" + pair[i].getName() + "=" + pair[i].getValue();

			}
			requrl = requrl+url;
			System.out.println("==========" + requrl);
			hm1 = new GetMethod(requrl);
		//	hm1.addRequestHeader("Content-type" , "text/html; charset=utf-8");
			hm1.getParams().setSoTimeout(10000);
			hc.executeMethod(hm1);
			imsg = hm1.getResponseBodyAsString();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			hm1.releaseConnection();
			hc.getHttpConnectionManager().closeIdleConnections(0);

		}
		return imsg;
	}
	
	/**
	 * http post 
	 * 联通学乐乐模拟cookie
	 * @param requrl
	 * @param cookies
	 * @return
	 * @throws Exception
	 */
    public static HashMap mobileHttpGets(String requrl, NameValuePair[] data,
			Cookie[] cookie, int flag, String oc_id) throws Exception {
		HashMap map = new HashMap();

		PostMethod hm1 = null;
		HttpClient hc = new HttpClient();
		String imsg = "";
		if (flag == 1) { // 需要登录
		} else if (flag == 0) { // 无需登录
			cookie = null;
		}
		try {
			hc.getParams().setCookiePolicy(CookiePolicy.RFC_2109);
			HttpState initialState = new HttpState();
			initialState.addCookies(cookie);
			hc.setState(initialState);
//			if(clientIp != null){
//				String a[] = clientIp.split(":");
//			}
			hm1 = new PostMethod(requrl);
			hm1.setRequestHeader("Host", "xuelele.10155.com");
			hm1.setRequestHeader("Connection", "keep-alive");
			//hm1.setRequestHeader("Content-Length", "31");
			hm1.setRequestHeader("Accept", "*/*");
			hm1.setRequestHeader("Origin", "http://xuelele.10155.com");
			hm1.setRequestHeader("X-Requested-With", "XMLHttpRequest");
			hm1.setRequestHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");

			hm1.setRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
			hm1.setRequestHeader("Referer", "https://xuelele.10155.com/Wap/Course/info?oc_id="+oc_id);
			hm1.setRequestHeader("Accept-Encoding", "gzip, deflate");
			hm1.setRequestHeader("Accept-Language", "zh-CN,zh;q=0.8");
			
			hm1.getParams().setSoTimeout(10000);
			hm1.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,
					"UTF-8");
			if (data != null) {
				hm1.setRequestBody(data);
			}

			hc.executeMethod(hm1);
			if(hm1.getResponseHeader("__hash__") != null){
				System.out.println(hm1.getResponseHeader("__hash__").getValue());
				map.put("__hash__",hm1.getResponseHeader("__hash__").getValue());
			}
			
			Cookie[] cookies = hc.getState().getCookies();
			if(cookies.length>0){
				map.put("cookies", cookies);
			}		
			for (int i = 0; i < cookies.length; i++) {
				map.put(cookies[i].getName(), cookies[i].getValue());
				
				System.out.println(cookies[i].getName()+"========="+cookies[i].getValue());

			}
			imsg = hm1.getResponseBodyAsString();
			


		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			hm1.releaseConnection();
			hc.getHttpConnectionManager().closeIdleConnections(0);
		}
		return map;	
    }
    
	
	/**
	 * http post 
	 * 联通学乐乐模拟cookie提交验证码
	 * @param requrl
	 * @param cookies
	 * @return
	 * @throws Exception
	 */
	public static String doPostCmdByHttpClient1(String strURL,
			NameValuePair[] data,String cookie,String oc_id) {
		String result = null;
		HttpClient httpClient = new HttpClient();
		HttpConnectionManagerParams managerParams = httpClient
				.getHttpConnectionManager().getParams();
		// 设置连接超时时间(单位毫秒)
		managerParams.setConnectionTimeout(30000);
		// 设置读数据超时时间(单位毫秒)
		managerParams.setSoTimeout(10000);
		PostMethod postMethod = new PostMethod(strURL);
		postMethod.setRequestHeader("Cookie",cookie);
		postMethod.setRequestHeader("Host", "xuelele.10155.com");
		postMethod.setRequestHeader("Connection", "keep-alive");
		postMethod.setRequestHeader("Accept", "*/*");
		postMethod.setRequestHeader("Content-Length","138");
		postMethod.setRequestHeader("Origin", "http://xuelele.10155.com");
		postMethod.setRequestHeader("X-Requested-With", "XMLHttpRequest");
		postMethod.setRequestHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
//
		postMethod.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
		postMethod.setRequestHeader("Referer", "http://xuelele.10155.com/Wap/Course/info/oc_id/"+oc_id);
		postMethod.setRequestHeader("Accept-Language", "zh-CN,zh;q=0.8");

		try {
			postMethod.setRequestBody(data);
			httpClient.getParams().setContentCharset("UTF-8");
			int statusCode = httpClient.executeMethod(postMethod);
			if (statusCode != HttpStatus.SC_OK) {
				throw new IllegalStateException("Method failed: "
						+ postMethod.getStatusLine());
			}
			result = postMethod.getResponseBodyAsString();
		} catch (Exception ex) {
			ex.printStackTrace();
			return "";

		} finally {
			// 释放连接
			postMethod.releaseConnection();
		}
		return result;

	}
}
