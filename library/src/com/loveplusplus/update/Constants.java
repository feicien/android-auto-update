package com.loveplusplus.update;

public class Constants {

	protected static final String APP_UPDATE_SERVER_URL = "http://192.168.1.115:8080/mlhwggl/api/app/update";
	
	// json {"url":"http://192.168.1.115:8080/xxx.apk","versionCode":2,"updateMessage":"版本更新信息"}
	//我这里服务器返回的json数据是这样的，可以根据实际情况修改下面参数的名称
	public static final String APK_DOWNLOAD_URL = "url";
	public static final String APK_UPDATE_CONTENT = "updateMessage";
	public static final String APK_VERSION_CODE = "versionCode";

}
