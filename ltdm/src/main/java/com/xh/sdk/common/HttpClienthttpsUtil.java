package com.xh.sdk.common;

import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.http.Header;
import org.apache.http.HttpEntity;  
import org.apache.http.HttpResponse;  
import org.apache.http.HttpStatus;  
import org.apache.http.NameValuePair;  
import org.apache.http.client.HttpClient;  
import org.apache.http.client.config.RequestConfig;  
import org.apache.http.client.entity.UrlEncodedFormEntity;  
import org.apache.http.client.methods.CloseableHttpResponse;  
import org.apache.http.client.methods.HttpPost;  
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;  
import org.apache.http.conn.ssl.SSLContextBuilder;  
import org.apache.http.conn.ssl.TrustStrategy;  
import org.apache.http.conn.ssl.X509HostnameVerifier;  
import org.apache.http.impl.client.CloseableHttpClient;  
import org.apache.http.impl.client.HttpClients;  
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;  
import org.apache.http.message.BasicNameValuePair;  
import org.apache.http.util.EntityUtils;  
  
import javax.net.ssl.SSLContext;  
import javax.net.ssl.SSLException;  
import javax.net.ssl.SSLSession;  
import javax.net.ssl.SSLSocket;  
import java.io.IOException;  
import java.nio.charset.Charset;  
import java.security.GeneralSecurityException;  
import java.security.cert.CertificateException;  
import java.security.cert.X509Certificate;  
import java.util.ArrayList;  
import java.util.HashMap;  
import java.util.Iterator;
import java.util.List;  
import java.util.Map;  
import java.util.Map.Entry;
/* 
 * 利用HttpClient进行post请求的工具类 
 */  
public class HttpClienthttpsUtil {  
	
    private static PoolingHttpClientConnectionManager connMgr;  
    private static RequestConfig requestConfig;  
    private static final int MAX_TIMEOUT = 7000;  
	
    public static Map<String,String> doPostssss(String url,Map<String,String> map,String charset,String cookie){  
    	Map<String,String> c = new HashMap<String,String>();  
        HttpClient httpClient = null;  
        HttpPost httpPost = null;  
        String result = null;  
        try{  
            httpClient = new com.xh.sdk.common.SSLClient();  
            httpPost = new HttpPost(url);  
            
            //设置参数  
            List<NameValuePair> list = new ArrayList<NameValuePair>();  
            Iterator iterator = map.entrySet().iterator();  
            while(iterator.hasNext()){  
                Entry<String,String> elem = (Entry<String, String>) iterator.next();  
                list.add(new BasicNameValuePair(elem.getKey(),elem.getValue()));  
            }  
            if(list.size() > 0){  
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list,charset);  
                httpPost.setEntity(entity);  
            }  
            httpPost.setHeader("cookie",cookie);
            httpPost.setHeader("Host", "xuelele.10155.com");
            httpPost.setHeader("Connection", "keep-alive");
			//hm1.setRequestHeader("Content-Length", "31");
            httpPost.setHeader("Accept", "*/*");
            httpPost.setHeader("Origin", "http://xuelele.10155.com");
            httpPost.setHeader("X-Requested-With", "XMLHttpRequest");
            httpPost.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");

            httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            httpPost.setHeader("Referer", "https://xuelele.10155.com/Wap/Order/buyCourse?oc_id=150");
            //httpPost.setHeader("Accept-Encoding", "gzip, deflate");
            httpPost.setHeader("Accept-Language", "zh-CN,zh;q=0.8");
            
            httpPost.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,
					"UTF-8");

            HttpResponse response = httpClient.execute(httpPost);  
            
    		if(response.getHeaders("__hash__")!=null){
    			
    	           Header[] sheaders = response.getHeaders("__hash__");
    	            
                   for(int i=0;i<sheaders.length;i++){
                	
                	   c.put("__hash__", sheaders[i].getValue());
                	
                }
    		
			}
			
            
            
            Header[] headers= response.getAllHeaders();
            for(int i=0;i<headers.length;i++){
            	
            	
            	if("Set-Cookie".equals(headers[i].getName())){
            		c.put(headers[i].getValue().split(";")[0].split("=")[0], headers[i].getValue().split(";")[0].split("=")[1]);
            	}
            	
            }
            
          
            if(response != null){  
                HttpEntity resEntity = response.getEntity();  
                if(resEntity != null){  
                    result = EntityUtils.toString(resEntity,"UTF-8");  
//                    System.out.println("Response content:"  
//                            + new String(result.getBytes("ISO-8859-1"),"UTF-8"));  
                }  
            }  
        }catch(Exception ex){  
            ex.printStackTrace();  
        }  

        return c;  
    }  
    
    
    /** 
     * 发送 SSL POST 请求（HTTPS），K-V形式 
     * @param apiUrl API接口URL 
     * @param params 参数map 
     * @return 
     */  
    public static Map<String,String> doPost(String apiUrl, Map<String, String> params,String charset,String cookie) {  
        CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(createSSLConnSocketFactory()).setConnectionManager(connMgr).setDefaultRequestConfig(requestConfig).build();  
        HttpPost httpPost = new HttpPost(apiUrl);  
        CloseableHttpResponse response = null;  
        String httpStr = null;  
    	Map<String,String> c = new HashMap<String,String>();  
  
        try {  
            httpPost.setConfig(requestConfig);  
            List<NameValuePair> pairList = new ArrayList<NameValuePair>(params.size());  
            for (Map.Entry<String, String> entry : params.entrySet()) {  
                NameValuePair pair = new BasicNameValuePair(entry.getKey(), entry  
                        .getValue().toString());  
                pairList.add(pair);  
            }  
            
            httpPost.setHeader("cookie",cookie);
            httpPost.setHeader("Host", "xuelele.10155.com");
            httpPost.setHeader("Connection", "keep-alive");
			//hm1.setRequestHeader("Content-Length", "31");
            httpPost.setHeader("Accept", "*/*");
            httpPost.setHeader("Origin", "http://xuelele.10155.com");
            httpPost.setHeader("X-Requested-With", "XMLHttpRequest");
            httpPost.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");

            httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            httpPost.setHeader("Referer", "https://xuelele.10155.com/Wap/Order/buyCourse?oc_id=150");
            //httpPost.setHeader("Accept-Encoding", "gzip, deflate");
            httpPost.setHeader("Accept-Language", "zh-CN,zh;q=0.8");
            
            httpPost.setEntity(new UrlEncodedFormEntity(pairList, Charset.forName("utf-8")));  
            response = httpClient.execute(httpPost);  
            int statusCode = response.getStatusLine().getStatusCode();  
            if (statusCode != HttpStatus.SC_OK) {  
                return null;  
            }  
            
            
    		if(response.getHeaders("__hash__")!=null){
    			
    	           Header[] sheaders = response.getHeaders("__hash__");
    	            
                   for(int i=0;i<sheaders.length;i++){
                	
                	   c.put("__hash__", sheaders[i].getValue());
                	
                }
    		
			}
			
            
            
            Header[] headers= response.getAllHeaders();
            for(int i=0;i<headers.length;i++){
            	
            	
            	if("Set-Cookie".equals(headers[i].getName())){
            		c.put(headers[i].getValue().split(";")[0].split("=")[0], headers[i].getValue().split(";")[0].split("=")[1]);
            	}
            	
            }
            
            HttpEntity entity = response.getEntity();  
            if (entity == null) {  
                return null;  
            }  
            httpStr = EntityUtils.toString(entity, "utf-8");  
            c.put("result", httpStr);
        } catch (Exception e) {  
            e.printStackTrace();  
        } finally {  
            if (response != null) {  
                try {  
                    EntityUtils.consume(response.getEntity());  
                } catch (IOException e) {  
                    e.printStackTrace();  
                }  
            }  
        }  
        return c;  
    }  
    
    /** 
     * 创建SSL安全连接 
     * 
     * @return 
     */  
    private static SSLConnectionSocketFactory createSSLConnSocketFactory() {  
        SSLConnectionSocketFactory sslsf = null;  
        try {  
            SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {  
  
                @Override
				public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {  
                    return true;  
                }  
            }).build();  
            sslsf = new SSLConnectionSocketFactory(sslContext, new X509HostnameVerifier() {  
  
                @Override  
                public boolean verify(String arg0, SSLSession arg1) {  
                    return true;  
                }  
  
                @Override  
                public void verify(String host, SSLSocket ssl) throws IOException {  
                }  
  
                @Override  
                public void verify(String host, X509Certificate cert) throws SSLException {  
                }  
  
                @Override  
                public void verify(String host, String[] cns, String[] subjectAlts) throws SSLException {  
                }  
            });  
        } catch (GeneralSecurityException e) {  
            e.printStackTrace();  
        }  
        return sslsf;  
    }  
}  