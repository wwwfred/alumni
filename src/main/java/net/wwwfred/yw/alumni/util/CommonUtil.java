package net.wwwfred.yw.alumni.util;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import net.wwwfred.framework.core.exception.FrameworkRuntimeException;
import net.wwwfred.framework.core.web.ServletUtil;
import net.wwwfred.framework.util.io.IOUtil;
import net.wwwfred.framework.util.json.JSONUtil;
import net.wwwfred.framework.util.log.LogUtil;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class CommonUtil {


	public static void sendMessage(String mobilephone, String content) {
		// TODO Auto-generated method stub
		
		String bodyData = "account="+"cf_wwwfred"+"&password="+"www123"+"&mobile="+mobilephone+"&content="+content;
		Charset encoding = Charset.forName("UTF-8");
		long startTime = System.currentTimeMillis();
		try {
			String responseXml = new String(IOUtil.getByteArrayFromHttpUrl("http://106.ihuyi.cn/webservice/sms.php?method=Submit",bodyData.getBytes(encoding)
					,new Object[][]{{"ContentType","application/x-www-form-urlencoded;charset=GBK"}}),encoding);
			LogUtil.i("useTime="+(System.currentTimeMillis()-startTime)+",发送短信,mobilePhone="+mobilephone+",content="+content+",responseXml="+responseXml);

			Document doc = DocumentHelper.parseText(responseXml);
			Element root = doc.getRootElement();
			String code = root.elementText("code");
			String msg = root.elementText("msg");
			//String smsid = root.elementText("smsid");
			if(!"2".equals(code)){
				throw new FrameworkRuntimeException(msg);
			}
		} catch (Exception e) {
			// TODO: handle exception
			LogUtil.w("useTime="+(System.currentTimeMillis()-startTime)+",sendMessage illegal", e);
//			throw e instanceof TeshehuiRuntimeException?((TeshehuiRuntimeException)e):new TeshehuiRuntimeException(e);
		}
	}
	
	public static void sendMessage2(String mobilePhone,String checkCode)
	{
		String url = "http://sms.market.alicloudapi.com/singleSendSms";
		String encoding = "UTF-8";
	    Map<String, String> querys = new HashMap<String, String>();
	    querys.put("ParamString", "{\"checkCode\":\""+checkCode+"\"}");
	    querys.put("RecNum", mobilePhone);
	    querys.put("SignName", "MINI小应用");
	    querys.put("TemplateCode", "SMS_43910005");
	    long startTime = System.currentTimeMillis();
    	try {
    		String responseData = new String(IOUtil.getByteArrayFromHttpUrl(url+"?"+ServletUtil.mapToPostParam(querys, encoding),null, new Object[][]{{"Authorization","APPCODE 7560693a535e48038691d32fd9d8cb6f"}}),Charset.forName(encoding));
    		Map<String,String> responseMap = JSONUtil.toMap(responseData, String.class);
    		if(responseMap.get("success")==null||!Boolean.parseBoolean(responseMap.get("success")))
    			throw new FrameworkRuntimeException(responseMap.get("message"));
    		LogUtil.i("useTime="+(System.currentTimeMillis()-startTime)+",sendMessage2 success,mobile="+mobilePhone+",checkCode="+checkCode+",responseData="+responseData);
    	} catch (Exception e) {
			// TODO: handle exception
			LogUtil.w("useTime="+(System.currentTimeMillis()-startTime)+",sendMessage2 illegal,mobile="+mobilePhone+",checkCode="+checkCode,e);
//			throw (e instanceof TeshehuiRuntimeException)?((TeshehuiRuntimeException)e):new TeshehuiRuntimeException(e);
    	}
	}
	
	public static void main(String[] args) {
		sendMessage2("15171466149", "323222");
	}

	
}
