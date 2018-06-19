package com.xh.sdk.common;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.util.List;

import com.huawei.smproxy.SGIPSMProxy;
import com.huawei.smproxy.comm.sgip.message.SGIPMessage;
import com.huawei.smproxy.comm.sgip.message.SGIPSubmitMessage;
import com.huawei.smproxy.comm.sgip.message.SGIPSubmitRepMessage;
import com.huawei.smproxy.util.Args;

public class Mt {
	// private static String SPNumber = "10655502";//接入号码
	// private static String ChargeNumber = "8618658191488"; // 计费号码，白名单
	// private static String ServiceType = "WDM217";//服务类型

	public static String send(String phone, String SPNumber,
			String ServiceType, String reserve, String submitType,
			String content) throws UnsupportedEncodingException {

		String host = "211.94.164.35"; // 主机名，网关IP
		int port = 8804; // 端口号
		String CorpId = "90214"; // 企业代码
		String login_Name = "10655502"; // 登陆名
		String login_PassWord = "W90214"; // 登陆密码
		int FeeType = 01;
		String FeeValue = "";
		int MorelatetoMTFlag = 0;
		int ReportFlag = 1;

		String str = "";
		// int srcnode = new BigInteger("*******").intValue();
		// //源节点编号(nodeid)，这一步非常重要，华为包中，该字段类型为int，而接入协议中要求在企业代码前加上30000，这样就超过了int的取值范围，所以需要用BigInteger转一下就可以了
		Args argstr = new Args();// 设定连接参数
		argstr.set("host", host);
		argstr.set("port", port);
		argstr.set("transaction-timeout", 10); // 操作超时时间(单位：秒)
		argstr.set("read-timeout", 15); // 物理连接读操作超时时间(单位：秒)
		// argstr.set("source-addr", srcnode); // SP…ID(最大为六位字符)
		argstr.set("login-name", login_Name);
		argstr.set("login-pass", login_PassWord);
		argstr.set("debug", "false");

		// 连接登陆
		SGIPSMProxy sgipsmp = new SGIPSMProxy(argstr); // 这里 连接到短信息网关
		try {
			// connect表示向SMG登陆,登录名与密码分别是SMG向SP分配的用户名与密码,调用这个接口方法，向SMG发送Bind命令消息。
			// 如果发送消息超时或通信异常则抛出异常，需要调用者捕获处理。
			boolean reslut = sgipsmp.connect(); // 登陆得到true和false
			if (reslut) {
				System.out.println("连接成功...........");
			} else {
				System.out.println("连接失败(用户名或密码错误)...........");
				return "";
			}
		} catch (Exception ex) {
			System.out.println("网络异常...........");
			ex.printStackTrace();
			return "";
		}
		String[] UserNumber = { phone };// 接收短信的手机号码，前边要加上86
		// String content = "test";
		byte[] MessageContent = content.getBytes("GB2312");
		System.out.println("短信内容: " + content);
		try {

			if ("bysubmit".equals(submitType)) {

				MorelatetoMTFlag = 0;
				;
				FeeType = 01;
				;
				FeeValue = "0";
				ReportFlag = 1;

			} else {
				switch (submitType.length()) {
				case 1:
					submitType = "00000" + submitType;
					break;
				case 2:
					submitType = "0000" + submitType;
					break;
				case 3:
					submitType = "000" + submitType;
					break;
				case 4:
					submitType = "00" + submitType;
					break;
				case 5:
					submitType = "0" + submitType;
					break;

				default:
					break;
				}

				MorelatetoMTFlag = 0;
				;
				FeeType = 02;
				FeeValue = submitType;
				ReportFlag = 1;

			}

			// 下发短息
			SGIPSubmitMessage sgipsubmit = new SGIPSubmitMessage(SPNumber, // SP的接入号码
					phone, // 付费号码 string
					UserNumber, // 接收该短消息的手机号，最多100个号码 string[]
					CorpId, // 企业代码，取值范围为0～99999 string
					ServiceType, // 业务代码，由SP定义 stirng
					FeeType, // 计费类型 int
					FeeValue, // 该条短消息的收费值 stirng
					"0", // 赠送用户的话费 string
					0, // 代收费标志0：应收1：实收 int
					MorelatetoMTFlag, // 引起MT消息的原因 int
					06, // 优先级0～9从低 到高，默认为0 int
					null, // 短消息寿命的终止时间 date
					null, // 短消息定时发送的时间 date
					ReportFlag, // 状态报告标记 int
					0, // GSM协议类型 int
					0, // GSM协议类型 int
					15, // 短消息的编码格式 int
					0, // 信息类型 int
					MessageContent.length, // 短消息内容长度 int
					MessageContent, // 短消息的内容 btye[]
					reserve // 保留，扩展用 string
			);
			// 收到的响应消息转换成rep

			int status = ProcessSubmitRep(sgipsmp.send(sgipsubmit));
		
			System.out.println("getSequenceId===" + sgipsubmit.getSequenceId());
			System.out.println("getSrcNodeId===" + sgipsubmit.getSrcNodeId());
			System.out.println("getTimeStamp===" + sgipsubmit.getTimeStamp());

			System.out.println(status);
			if (status == 0) {
				System.out.println("消息发送成功..........");
			} else {
				System.out.println("消息发送失败..........");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return str;
	}

	private static int ProcessSubmitRep(SGIPMessage msg) {
		// 收到的响应消息转换成repMsg
		SGIPSubmitRepMessage repMsg = (SGIPSubmitRepMessage) msg;
		System.out.println(repMsg.getSrcNodeId());
		System.out.println("status:::::::" + repMsg.getResult());
		if (repMsg != null && repMsg.getResult() == 0) {
			System.out.println("发送成功：：：");
		}
		return repMsg.getResult();
	}

}