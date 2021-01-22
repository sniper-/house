package com.house.framework.manager;

import com.house.common.utils.http.HttpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SmsManager {

    private static Logger logger = LoggerFactory.getLogger(SmsManager.class);

    /** 设置请求的统一前缀 */
    @Value("${sms.url}")
    private String smsUrl;

    public void sendSms(String phone, String param) {
        try {
            String response = HttpUtils.sendPostJson(smsUrl, param);
            logger.info("sendSms发送短信号码[{}], 详细信息{}", phone, response);
        } catch (Exception e) {
            logger.error("发送乐信短信异常", e);
        }
    }
}
