/**
 * COPYRIGHT (C) 2010 LY. ALL RIGHTS RESERVED.
 *
 * No part of this publication may be reproduced, stored in a retrieval system,
 * or transmitted, on any form or by any means, electronic, mechanical, photocopying,
 * recording, or otherwise, without the prior written permission of 3KW.
 *
 * Created By: zzqiang
 * Created On: 2013-10-14
 *
 * Amendment History:
 * 
 * Amended By       Amended On      Amendment Description
 * ------------     -----------     ---------------------------------------------
 *
 **/
package com.core.sgip;

import java.util.List;

import com.core.sgip.body.SGIPCommand;
import com.core.sgip.body.command.Submit;
import com.core.sgip.constant.SGIPConstant;
import com.core.sgip.factory.SGIPFactory;
import com.core.sgip.head.SGIPMsgHead;
import com.core.sgip.interf.SGIPByteData;
import com.core.sgip.util.SGIPUtils;

public class SGIPMsg implements SGIPByteData {

	private SGIPMsgHead head;

	private SGIPCommand command;

	public SGIPMsgHead getHead() {
		return head;
	}

	public void setHead(SGIPMsgHead head) {
		this.head = head;
	}

	public SGIPCommand getCommand() {
		return command;
	}

	public void setCommand(SGIPCommand command) {
		this.command = command;
	}

	private void calcute() {
		long messageLength = this.command.getCommandLength()
				+ SGIPMsgHead.HEAD_LENGTH;
		this.head.setMessageLength(messageLength);
	}

	@Override
	public byte[] getByteData() {
		byte[] commandBytes = command.getByteData();
		calcute();
		return SGIPUtils.mergeBytes(head.getByteData(), commandBytes);
	}

	public void setUserNumbers(List<String> listUserNumber,
			String messageContent) {
		if (head == null || command == null
				|| head.getCommandId() != SGIPConstant.SGIP_SUBMIT) {
			return;
		}
		Submit sub = (Submit) command;
		sub.setListUserNumber(listUserNumber);
		sub.setMessageContent(messageContent);
	}

	public void setUserSubMit(String spNumber, String serviceType,
			String reserve, String submitType, String phone) {
	//	String fee = cost+"";
		if (head == null || command == null
				|| head.getCommandId() != SGIPConstant.SGIP_SUBMIT) {
			return;
		}
		Submit sub = (Submit) command;
		if ("bysubmit".equals(submitType)) {

			sub.setMorelatetoMTFlag(0);
			sub.setFeeType(1);
			sub.setFreeValue("0");
			sub.setReportFlag(1);
		}
		else{
			switch (submitType.length()) {
			case 1:
				submitType = "00000"+submitType;
				break;
			case 2:
				submitType = "0000"+submitType;
				break;
			case 3:
				submitType = "000"+submitType;
				break;
			case 4:
				submitType = "00"+submitType;
				break;
			case 5:
				submitType = "0"+submitType;
				break;

			default:
				break;
			}
			sub.setMorelatetoMTFlag(0);
			sub.setFeeType(2);
			sub.setFreeValue(submitType);
			sub.setReportFlag(1);

		}
		
		System.out.println("reserve======111========="+reserve);
		if (!"".equals(reserve) && reserve != null) {
			System.out.println("this is run");
			sub.setReserve(reserve);
		}
		
		System.out.println("reserve=====222=========="+reserve);
		sub.setSpNumber(spNumber);
		sub.setServiceType(serviceType);
		sub.setChargeNumber(phone);
		//

	}

	private void initHead(byte[] source) {
		head = new SGIPMsgHead();
		byte[] headByte = new byte[SGIPMsgHead.HEAD_LENGTH];
		SGIPUtils.copyBytes(source, headByte, 1, SGIPMsgHead.HEAD_LENGTH, 1);
		head.initPropertiesByBytes(headByte);
	}

	private void initCommand(byte[] source) throws Exception {
		if (head == null || head.getCommandId() == null) {
			return;
		}
		long commandId = head.getCommandId();
		command = SGIPFactory.getCommandByCommandId(commandId);

		byte[] commandByte = new byte[source.length - SGIPMsgHead.HEAD_LENGTH];
		SGIPUtils.copyBytes(source, commandByte, SGIPMsgHead.HEAD_LENGTH + 1,
				commandByte.length, 1);
		command.initPropertiesByBytes(commandByte);
	}

	@Override
	public void initPropertiesByBytes(byte[] source) {
		initHead(source);
		try {
			initCommand(source);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public String toString() {
		return "SGIPMsg [head=" + head + ", command=" + command + "]";
	}
}
