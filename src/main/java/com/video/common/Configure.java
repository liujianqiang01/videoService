package com.video.common;

public class Configure {
	private static String key = "TgyJpbqnUn45aLizzgdAJsaMyyRiHqfH";

	//小程序ID	
	private static String appID = "wx4ff803a9d7c7f8ff";
	//商户号
	private static String mch_id = "1519800311";
	//
	private static String secret = "d48f8005f7ab92421b8ee9b8604dbc75";

	private static String spbill_create_ip = "192.168.1.100";

	public static String getSecret() {
		return secret;
	}

	public static void setSecret(String secret) {
		Configure.secret = secret;
	}

	public static String getKey() {
		return key;
	}

	public static void setKey(String key) {
		Configure.key = key;
	}

	public static String getAppID() {
		return appID;
	}

	public static void setAppID(String appID) {
		Configure.appID = appID;
	}

	public static String getMch_id() {
		return mch_id;
	}

	public static void setMch_id(String mch_id) {
		Configure.mch_id = mch_id;
	}

	public static String getSpbill_create_ip() {
		return spbill_create_ip;
	}

	public static void setSpbill_create_ip(String spbill_create_ip) {
		Configure.spbill_create_ip = spbill_create_ip;
	}
}
