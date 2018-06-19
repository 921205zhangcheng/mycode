package com.xh.sdk.common;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.lang.StringUtils;
import org.dom4j.DocumentHelper;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

@Component
public class LpTools {

	// private final String sta = "http://www.happyplay.net/gamemanage/static" ;

	// private final String url = "http://www.happyplay.net/gamemanage";

	// private final String sta = "http://121.199.38.56:8899/gamemanage/static"
	// ;

	// private final String url = "http://121.199.38.56:8899/gamemanage";

	// private final String sta = "http://121.199.38.56:8080/gamemanage/static"
	// ;

	// private final String url = "http://121.199.38.56:8080/gamemanage";

	private static String DM_RDO_SA_MCPID = "xiugewh";

	private static String DM_RDO_SA_feeCode = "66000202";

	private static String DM_RDO_SA_cm = "M30I0003";

	private static String DM_RDO_SA_channelid = "9003";

	private static String DM_RDO_SA_orderurl = "http://42.121.109.16:9088/rdo/order.aspx";

	private static String DM_RDO_SA_orderncpurl = "http://42.121.109.16:9088/rdo/orderncp.aspx";

	private static String SDK_SHIPIN_URL = "http://121.41.26.254/crack/getsms.jsp";

	private static String SDK_SHIPIN_KEY = "074d225b9ec2da9b1119f94f6b393cec";

	static long[] ls = new long[1000];

	private static int li = 0;

	private final String sta = "http://localhost:8080/gamemanage/static";

	private final String url = "http://124.160.11.199:7007";

	private final String cardCharge = url
			+ "/crmService/chinaUnicom/cardCharge";// 卡缴费

	private final String cardCheck = url + "/crmService/chinaUnicom/cardCheck";// 卡有效性检查

	private final String cardInfoQuery = url
			+ "/crmService/chinaUnicom/cardInfoQuery";// 卡信息查询

	private final String chargeInfoQuery = url
			+ "/crmService/chinaUnicom/chargeInfoQuery";// 号码缴费信息查询

	private final String CX_MERCHANT = "436a67d6-61da-49e6-91c5-413e00c0cb9f";//

	private final String CX_CLIENTID = "0132";//

	private final String CX_CLIENTSECERET = "cnLiDAlpzKEe";//

	private final String CX_VERSION = "V1.0";//

	private final String QQ_KEY = "HPMpuy8ax6v8iCBc7cNdgsZmHA1SCoiH";
	// HPMpuy8ax6v8iCBc7cNdgsZmHA1SCoiH

	private final String CX_ACTION = "GetToken";//

	private final String supplier_id = "712456632";//
	private final String CX_URL = "http://183.61.119.21:1234/SkyHandler.ashx";
	private final String QQ_CHARGE = "http://61.160.223.56:3042/AgentCharge/Member";
	// private final String QQ_CHARGE
	// ="http://61.160.223.56:3041/AgentCharge/Member"

	private final String[] productCode = { "",
			"e8853fcf-6ccf-42a5-9313-413e00ee639f",
			"cd823e75-5b04-401b-8934-413e00ee6e0a",
			"2fc65262-012e-481e-b254-413e00ee63bf",
			"c32817e4-46fb-4440-b885-413e00ee58d5",
			"792aad0f-6770-4db6-966d-413e00ee72cb",
			"cff3f233-346d-41a0-acee-413e00ee590e" };

	public final static Map token = new HashMap();

	public String getQQ_KEY() {
		return QQ_KEY;
	}

	public String getCX_MERCHANT() {
		return CX_MERCHANT;
	}

	public String getCX_URL() {
		return CX_URL;
	}

	public static String getDM_RDO_SA_orderncpurl() {
		return DM_RDO_SA_orderncpurl;
	}

	public static String getDM_RDO_SA_MCPID() {
		return DM_RDO_SA_MCPID;
	}

	public static String getDM_RDO_SA_orderurl() {
		return DM_RDO_SA_orderurl;
	}

	public static String getDM_RDO_SA_feeCode() {
		return DM_RDO_SA_feeCode;
	}

	public static String getDM_RDO_SA_cm() {
		return DM_RDO_SA_cm;
	}

	public static String getDM_RDO_SA_channelid() {
		return DM_RDO_SA_channelid;
	}

	public static String getSDK_SHIPIN_URL() {
		return SDK_SHIPIN_URL;
	}

	public static String getSDK_SHIPIN_KEY() {
		return SDK_SHIPIN_KEY;
	}

	public String getCX_CLIENTID() {
		return CX_CLIENTID;
	}

	public String getCX_CLIENTSECERET() {
		return CX_CLIENTSECERET;
	}

	public String getCX_VERSION() {
		return CX_VERSION;
	}

	public String getCX_ACTION() {
		return CX_ACTION;
	}

	private final String key = "c1b95d1d2f49a24df342fd3456746b285";//

	// c1b95d1d2f49a24df342fd3456746b285
	// c1b95d1d2f49a24c654199b56746b285
	// c1b95d1d2f49a24c654199b56746b285
	// c1b95d1d2f49a24c654199b56746b285
	private final String tstime() {
		String str = "";

		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		long time1;
		try {
			time1 = sf.parse("1970-01-01 00:00:00 UTC").getTime();
			Date date = new Date();
			long time2 = date.getTime();
			long time = (time2 - time1);
			// System.out.println(time);
			str = time + "";
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return str;
	}

	private final String ts = new Date().getTime() + "";//

	private final String tld = formattime();

	public String getTs() {
		return ts;
	}

	public String getTld() {
		return tld;
	}

	public static String formattime() {
		String str = "";
		Date date = new Date();
		SimpleDateFormat timeFormater = new SimpleDateFormat("yyyyMM");
		str = timeFormater.format(date);

		return str;

	}

	public static String QQ_OrderDay() {
		String str = "";
		Date date = new Date();
		SimpleDateFormat timeFormater = new SimpleDateFormat("yyyyMMdd");
		str = timeFormater.format(date);

		return str;

	}

	public static String QQ_StampTime() {
		String str = "";
		Date date = new Date();
		SimpleDateFormat timeFormater = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		str = timeFormater.format(date);

		return str;

	}

	public static String QQ_OrderID() {

		return UUID.randomUUID().toString().replaceAll("-", "");

	}

	public synchronized static long getPK() {
		long lo = getpk();
		for (int i = 0; i < 1000; i++) {
			long lt = ls[i];
			if (lt == lo) {
				lo = getPK();
				break;
			}
		}
		ls[li] = lo;
		li++;
		if (li == 1000) {
			li = 0;
		}
		return lo;
	}

	private static long getpk() {
		String a = (String.valueOf(System.currentTimeMillis()))
				.substring(3, 13);
		String d = (String.valueOf(Math.random())).substring(2, 8);
		return Long.parseLong(a + d);
	}

	public static String QQ_MD5(String plainText) {
		String result = null;
		// 首先判断是否为空
		if (StringUtils.isBlank(plainText)) {
			return null;
		}
		try {
			// 首先进行实例化和初始化
			MessageDigest md = MessageDigest.getInstance("MD5");
			// 得到一个操作系统默认的字节编码格式的字节数组
			byte[] btInput = plainText.getBytes();
			// 对得到的字节数组进行处理
			md.update(btInput);
			// 进行哈希计算并返回结果
			byte[] btResult = md.digest();
			// 进行哈希计算后得到的数据的长度
			StringBuffer sb = new StringBuffer();
			for (byte b : btResult) {
				int bt = b & 0xff;
				if (bt < 16) {
					sb.append(0);
				}
				sb.append(Integer.toHexString(bt));
			}
			result = sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return result;
	}

	public String getcardCharge() {

		return cardCharge;

	}

	public String getcardCheck() {

		return cardCheck;

	}

	public String getcardInfoQuery() {

		return cardInfoQuery;

	}

	public String getchargeInfoQuery() {

		return chargeInfoQuery;

	}

	public String getSta() {

		return sta;

	}

	public String getUrl() {
		return url;
	}

	public String getCardCharge() {
		return cardCharge;
	}

	public String getQQ_CHARGE() {
		return QQ_CHARGE;
	}

	public String getCardCheck() {
		return cardCheck;
	}

	public String getCardInfoQuery() {
		return cardInfoQuery;
	}

	public String getSupplier_id() {
		return supplier_id;
	}

	public String getChargeInfoQuery() {
		return chargeInfoQuery;
	}

	public String getKey() {
		return key;
	}

	public String getToken() {
		if (token.get("token") == null) {
			return null;
		}
		String str = (String) token.get("token");

		return str;
	}

	public long getTokentime() {
		if (token.get("time") == null) {
			return 0;
		}
		long str = Long.valueOf(token.get("time").toString());
		return str;
	}

	public void putToken(String ss) {
		token.put("token", ss);
		Date now = new Date();
		token.put("time", now.getTime());

	}

	/**
	 * 
	 * @Title: ts
	 * @Description: 用于拼接静态文件的访问路径
	 * @param s
	 * @return
	 * @return String
	 */
	public String ts(String s) {

		return sta + s;

	}

	/**
	 * @Title: tr
	 * @Description: 用于动态url的拼接
	 * @param s
	 * @return String
	 */
	public String tr(String s) {

		return url + s;

	}

	public static String getFromBASE64(String s) {
		if (s == null)
			return null;
		BASE64Decoder decoder = new BASE64Decoder();
		try {
			byte[] b = decoder.decodeBuffer(s);
			return new String(b);
		} catch (Exception e) {
			return null;
		}
	}

	public static byte[] decode(String str) {
		byte[] bt = null;
		try {
			sun.misc.BASE64Decoder decoder = new sun.misc.BASE64Decoder();
			bt = decoder.decodeBuffer(str);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return bt;
	}

	/**
	 * @param list
	 * @return
	 */
	public static JSONObject domXml(String xmlStr) {
		JSONObject pos = new JSONObject();
		Element element = null;

		try {
			StringReader sr = new StringReader(xmlStr.trim());
			InputSource is = new InputSource(sr);
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document dt = builder.parse(is);

			element = dt.getDocumentElement();// 得到一个elment根元素
			// System.out.println("根元素：" + element.getNodeName()); // 获得根节点
			NodeList childNodes = element.getChildNodes(); // 获得根元素下的子节点

			// System.out.println(childNodes.getLength());
			for (int i = 0; i < childNodes.getLength(); i++) // 遍历这些子节点
			{
				Node node1 = childNodes.item(i); // childNodes.item(i);
				// 获得每个对应位置i的结点
				pos.put(node1.getNodeName(), node1.getTextContent());
				// System.out.println(node1.getNodeName()+"==========="+
				// node1.getTextContent());
			}
		} catch (Exception e) {
			// System.out.println(e);
		}
		// System.out.println(pos.toJSONString());
		return pos;
	}

	public static String getMD5(String key) {
		String HEX_CHARS = "0123456789abcdef";
		String str = "";
		byte[] data;
		try {
			data = key.getBytes("utf-8");
			byte[] b = MessageDigest.getInstance("MD5").digest(data);

			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < b.length; i++) {
				sb.append(HEX_CHARS.charAt(b[i] >>> 4 & 0x0F));
				sb.append(HEX_CHARS.charAt(b[i] & 0x0F));
			}
			str = sb.toString();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return str;
	}
/**
 * 爱音乐生成key
 * @param secret
 * @param data
 * @return
 */
	
	public static String generateMacSignature(String secret, String data) {
		/* 加密方法， 固定取值 HmacSHA1 */
	 String SIGNATUREMETHOD = "HmacSHA1";
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
	 * 按字母顺序拼接
	 */
	public static String getPJsign(NameValuePair[] pair) {
		String str = "";
		ArrayList<String> list = new ArrayList<String>();
		for (int i = 0; i < pair.length; i++) {
			if (pair[i] != null) {
				list.add(pair[i].getName() + pair[i].getValue());
			}

		}
		Collections.sort(list);
		for (String tmp : list) {
			str = str + tmp;
		}

		return str;
	}

	/**
	 * 构造xml文件
	 */
	public static String  CreateXMl(NameValuePair[] pair) {
		// 创建document对象
		String str = "";
		  org.dom4j.Document doc = DocumentHelper.createDocument();
		  
	       // doc.addComment("this is a comment");
	        // 2. 构造根元素
	        org.dom4j.Element rootElmt = doc.addElement("mission");
	       // rootElmt.addNamespace("test", "www.test.com");
	        for(int i=0;i<pair.length;i++){
	        	rootElmt.addElement(pair[i].getName()).setText(pair[i].getValue());       	
	        }
	        str = doc.asXML().replaceAll("\n", "");
	      //  System.out.println(doc.asXML().replaceAll("\n", ""));
	        return str;
	}
	
	

	/**
	 * 构造xml文件
	 */
    public static String createDom(NameValuePair[] pair){
        Document doc;
        Element school;
    	String str = "";
        try{
            //得到DOM解析器的工厂实例
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            //从DOM工厂中获得DOM解析器
            DocumentBuilder dbBuilder = dbFactory.newDocumentBuilder();
            //创建文档树模型对象
            doc = dbBuilder.newDocument();
            if(doc != null){
                //创建school元素
                school = doc.createElement("mission");
                
                
                for(int i=0;i<pair.length;i++){
    	        	//rootElmt.addElement(pair[i].getName()).setText(pair[i].getValue());  
                	Element name = null;
                	 name = doc.createElement(pair[i].getName());
                     name.appendChild(doc.createTextNode(pair[i].getValue()));
                     school.appendChild(name);
    	        }
                     doc.appendChild(school);
               str=toStringFromDoc(doc);
               System.out.println("xml=============="+str);
            }
        }catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return str;
    }
    
    public static String getBase64(String str) {  
        byte[] b = null;  
        String s = null;  
        try {  
            b = str.getBytes("utf-8");  
        } catch (UnsupportedEncodingException e) {  
            e.printStackTrace();  
        }  
        if (b != null) {  
        	sun.misc.BASE64Encoder encoder = new sun.misc.BASE64Encoder();
            s = encoder.encode(b);  
        }  
        return s;  
    }  
    
    public static String toStringFromDoc(Document document) {  
        String result = null;  
        if (document != null) {  
            StringWriter strWtr = new StringWriter();  
            StreamResult strResult = new StreamResult(strWtr);  
            TransformerFactory tfac = TransformerFactory.newInstance();  
            try {  
                javax.xml.transform.Transformer t = tfac.newTransformer();  
                t.setOutputProperty(OutputKeys.ENCODING, "UTF-8");  
                t.setOutputProperty(OutputKeys.INDENT, "yes");  
                t.setOutputProperty(OutputKeys.METHOD, "xml"); // xml, html,  
                // text  
                t.setOutputProperty(  
                        "{http://xml.apache.org/xslt}indent-amount", "4");  
				t.transform(new DOMSource(document.getDocumentElement()),  
                        strResult);  
            } catch (Exception e) {  
                System.err.println("XML.toString(Document): " + e);  
            }  
            result = strResult.getWriter().toString();  
            try {  
                strWtr.close();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
  
        return result;  
    }
    /**
     * 
     * @return
     */
    public static String getCurrentDate() {
 		Calendar cal = Calendar.getInstance();
 		int year = cal.get(Calendar.YEAR);
 		int month = cal.get(Calendar.MONTH) + 1;
 		int day = cal.get(Calendar.DAY_OF_MONTH);
 		int hour = cal.get(Calendar.HOUR_OF_DAY);
 		int minute = cal.get(Calendar.MINUTE);
 		int second = cal.get(Calendar.SECOND);
 		String cDate = Integer.toString(year);
 		if (month < 10) {
 			cDate = cDate + "0" + Integer.toString(month);
 		} else {
 			cDate = cDate + Integer.toString(month);
 		}
 		if (day < 10) {
 			cDate = cDate + "0" + Integer.toString(day);
 		} else {
 			cDate = cDate + Integer.toString(day);
 		}
 		if (hour < 10) {
 			cDate = cDate + "0" + Integer.toString(hour);
 		} else {
 			cDate = cDate + Integer.toString(hour);
 		}
 		if (minute < 10) {
 			cDate = cDate + "0" + Integer.toString(minute);
 		} else {
 			cDate = cDate + Integer.toString(minute);
 		}
 		if (second < 10) {
 			cDate = cDate + "0" + Integer.toString(second);
 		} else {
 			cDate = cDate + Integer.toString(second);
 		}
 		return cDate.trim();
 	}
    
    
    
	/**
	 * taobao获取省份接口
	 */
	public static String getPhoneprov(String phone){
		String str = "";
		String prov = "";
		NameValuePair[] pair = new NameValuePair[1];
		pair[0] = new NameValuePair("tel",phone);
		try {
			str = HttpPost.mobileHttpGet("http://tcc.taobao.com/cc/json/mobile_tel_segment.htm", pair);
			
			str=str.split("=")[1];
			//System.out.println(i);
			JSONObject json = JSON.parseObject(str);
            prov = json.get("province").toString();
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		 prov = Pingyin.getAllFirstLetter(prov);
		 
		 if(prov!=null&&prov.length()>2){ 
			 prov = prov.substring(0, 2);
		 }
	     return prov.toUpperCase();
	     
	     
	     
	     
	
	}
    /** 
     * 返回单个字符串，若匹配到多个的话就返回第一个，方法与getSubUtil一样 
     * @param soap 
     * @param rgex 
     * @return 
     */  
    public static String getSubUtilSimple(String soap,String rgex){  
        Pattern pattern = Pattern.compile(rgex);// 匹配的模式  
        Matcher m = pattern.matcher(soap);  
        while(m.find()){  
            return m.group(1);  
        }  
        return "";  
    }  

	
	
	
}
