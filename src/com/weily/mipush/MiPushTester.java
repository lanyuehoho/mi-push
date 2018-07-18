package com.weily.mipush;

import java.util.ArrayList;
import java.util.List;

import com.xiaomi.xmpush.server.Constants;
import com.xiaomi.xmpush.server.Message;
import com.xiaomi.xmpush.server.Result;
import com.xiaomi.xmpush.server.Sender;
import com.xiaomi.xmpush.server.TargetedMessage;

import sun.rmi.runtime.Log;

public class MiPushTester {

//	private static final String MY_PACKAGE_NAME = "com.yuntongxun.mipushdemo";
//	private static final String MY_PACKAGE_NAME = "com.yuntongxun.imdemo";
//	private static final String MY_PACKAGE_NAME = "com.yuntongxun.xiaomipush";
//	private static final String MY_PACKAGE_NAME = "com.yuntongxun.ecdemo";
	private static final String MY_PACKAGE_NAME = "com.yuntongxun.yuntxpush";
	
	//包名： com.yuntongxun.ecdemo
	//AppID： 2882303761517477127
	//AppKey： 5461747749127
	//AppSecret： HvmEMx31Cpd+f0J8lFWpKg==

//	private static final String APP_SECRET_KEY = "fycmHzED8YZglX1pmy/HqQ==";
//	private static final String APP_SECRET_KEY = "3tjDt+aD6YEgZBrmZ0rhtQ==";
//	private static final String APP_SECRET_KEY = "VqkL6fCksp9Tue48q8qVOw==";
	private static final String APP_SECRET_KEY = "GcHFgh+1RlMnX7KBWfmFbw==";
	
	public static void main(String[] args) throws Exception {
		Constants.useOfficial();
		
		MiPushTester main = new MiPushTester();
		main.buildMessage();
		
	}
	
	private Message buildMessage() throws Exception {
		 int random = (int)(Math.random()*100);
	     String messagePayload = "{\"name\":\"tom\",\"age\":19}" + random;
	     String title = "notification title " + random;
	     String description = "notification description " + random;
	     String[] packageNames = {"com.yuntongxun.xiaomipush","com.yuntongxun.ecdemo"};
	     Message message = new Message.Builder()
	             .title(title)
	             .description(description).payload(messagePayload)
//	             .restrictedPackageName(MY_PACKAGE_NAME)
	             .passThrough(0)  //消息使用透传方式,0：通知栏方式通知
	             .notifyType(1)     // 使用默认提示音提示
//	             .extra(Constants.EXTRA_PARAM_NOTIFY_FOREGROUND, "0")
	             .restrictedPackageNames(packageNames)
	             .notifyId(random)
	             .extra("msgDomain","{\"text\":\"1234\",\"gred\":\"cwsdfa\"}")
	             .extra("ecMessage","{\"from\":\"jack\",\"to\":\"tom\",\"msgTime\":\"01982098902839048\"}")
//	             .extra(com.xiaomi.xmpush.server.Constants.EXTRA_PARAM_NOTIFY_EFFECT, com.xiaomi.xmpush.server.Constants.NOTIFY_LAUNCHER_ACTIVITY)
	             .extra(com.xiaomi.xmpush.server.Constants.EXTRA_PARAM_NOTIFY_EFFECT, com.xiaomi.xmpush.server.Constants.NOTIFY_ACTIVITY)
	             .extra(com.xiaomi.xmpush.server.Constants.EXTRA_PARAM_INTENT_URI, "intent:#Intent;launchFlags=0x10000000;component=com.yuntongxun.yuntxpush/.activity.Main2Activity;i.age=18;S.name=zhangsan;end")
	             .build();
	     
	     Sender sender = new Sender(APP_SECRET_KEY);
	     String regId = "RSK5WuDxGbjz8qIkNMVQ7snzhS6mZmRHfjxunqXhfVg=";
	     Result result = sender.sendNoRetry(message, regId); //Result对于sendToAlias()，broadcast()和send()调用方式完全一样
//	     Result result = sender.sendToAlias(message, "100863", 0);
	     StringBuilder log = new StringBuilder();
	     log.append("data:").append(result.getData()).append(",")
	     	.append("errorCode:").append(result.getErrorCode().getValue()).append(",")
	     	.append("reason:").append(result.getReason()).append(",")
	     	.append("messageId:").append(result.getMessageId()).append(",");
	     System.out.println(log);
	     return message;
	}
	
	private List<TargetedMessage> buildMessages() throws Exception {
	     List<TargetedMessage> messages = new ArrayList<TargetedMessage>();
	     TargetedMessage message1 = new TargetedMessage();
	     message1.setTarget(TargetedMessage.TARGET_TYPE_ALIAS, "alias1");
	     message1.setMessage(buildMessage());
	     messages.add(message1);
	     TargetedMessage message2 = new TargetedMessage();
	     message2.setTarget(TargetedMessage.TARGET_TYPE_ALIAS, "alias2");
	     message2.setMessage(buildMessage());
	     messages.add(message2);
	     return messages;
	}
	
	private void sendMessage() throws Exception {
	     Constants.useOfficial();
	     String regId = null;
	     Sender sender = new Sender(APP_SECRET_KEY);
	     String messagePayload= "This is a message";
	     String title = "notification title";
	     String description = "notification description";
	     Message message = new Message.Builder()
	                .title(title)
	                .description(description).payload(messagePayload)
	                .restrictedPackageName(MY_PACKAGE_NAME)
	                .notifyType(1)     // 使用默认提示音提示
	                .build();
	     sender.send(message, regId, 0); //根据regID，发送消息到指定设备上，不重试。
	}

	private void sendMessages() throws Exception {
	     Constants.useOfficial();
	     Sender sender = new Sender(APP_SECRET_KEY);
	     List<TargetedMessage> messages = new ArrayList<TargetedMessage>();
	     TargetedMessage targetedMessage1 = new TargetedMessage();
	     targetedMessage1.setTarget(TargetedMessage.TARGET_TYPE_ALIAS, "alias1");
	     String messagePayload1= "This is a message1";
	     String title1 = "notification title1";
	     String description1 = "notification description1";
	     Message message1 = new Message.Builder()
	                .title(title1)
	                .description(description1).payload(messagePayload1)
	                .restrictedPackageName(MY_PACKAGE_NAME)
	                .notifyType(1)     // 使用默认提示音提示
	                .build();
	     targetedMessage1.setMessage(message1);
	     messages.add(targetedMessage1);
	     TargetedMessage targetedMessage2 = new TargetedMessage();
	     targetedMessage1.setTarget(TargetedMessage.TARGET_TYPE_ALIAS, "alias2");
	     String messagePayload2= "This is a message2";
	     String title2 = "notification title2";
	     String description2 = "notification description2";
	     Message message2 = new Message.Builder()
	                .title(title2)
	                .description(description2).payload(messagePayload2)
	                .restrictedPackageName(MY_PACKAGE_NAME)
	                .notifyType(1)     // 使用默认提示音提示
	                .build();
	     targetedMessage2.setMessage(message2);
	     messages.add(targetedMessage2);
	     sender.send(messages, 0); //根据alias，发送消息到指定设备上，不重试。
	}

	private void sendMessageToAlias() throws Exception {
	     Constants.useOfficial();
	     Sender sender = new Sender(APP_SECRET_KEY);
	     String messagePayload = "This is a message";
	     String title = "notification title";
	     String description = "notification description";
	     String alias = "testAlias";    //alias非空白，不能包含逗号，长度小于128。
	     Message message = new Message.Builder()
	                .title(title)
	                .description(description).payload(messagePayload)
	                .restrictedPackageName(MY_PACKAGE_NAME)
	                .notifyType(1)     // 使用默认提示音提示
	                .build();
	     sender.sendToAlias(message, alias, 0); //根据alias，发送消息到指定设备上，不重试。
	}

	private void sendMessageToAliases() throws Exception {
	     Constants.useOfficial();
	     Sender sender = new Sender(APP_SECRET_KEY);
	     String messagePayload = "This is a message";
	     String title = "notification title";
	     String description = "notification description";
	     List<String> aliasList = new ArrayList<String>();
	     aliasList.add("testAlias1");  //alias非空白，不能包含逗号，长度小于128。
	     aliasList.add("testAlias2");  //alias非空白，不能包含逗号，长度小于128。
	     aliasList.add("testAlias3");  //alias非空白，不能包含逗号，长度小于128。
	     Message message = new Message.Builder()
	                .title(title)
	                .description(description).payload(messagePayload)
	                .restrictedPackageName(MY_PACKAGE_NAME)
	                .notifyType(1)     // 使用默认提示音提示
	                .build();
	     sender.sendToAlias(message, aliasList, 0); //根据aliasList，发送消息到指定设备上，不重试。
	}

	private void sendBroadcast() throws Exception {
	     Constants.useOfficial();
	     Sender sender = new Sender(APP_SECRET_KEY);
	     String messagePayload = "This is a message";
	     String title = "notification title";
	     String description = "notification description";
	     String topic = "testTopic";
	     Message message = new Message.Builder()
	                .title(title)
	                .description(description).payload(messagePayload)
	                .restrictedPackageName(MY_PACKAGE_NAME)
	                .notifyType(1)     // 使用默认提示音提示
	                .build();
	     sender.broadcast(message, topic, 0); //根据topic，发送消息到指定一组设备上，不重试。
	}
}
