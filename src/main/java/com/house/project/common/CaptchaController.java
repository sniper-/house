package com.house.project.common;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import javax.servlet.http.HttpServletResponse;

import com.house.common.utils.StringUtils;
import com.house.framework.manager.SmsManager;
import com.house.project.business.domain.vo.SmsVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.house.common.constant.Constants;
import com.house.common.utils.IdUtils;
import com.house.common.utils.VerifyCodeUtils;
import com.house.common.utils.sign.Base64;
import com.house.framework.redis.RedisCache;
import com.house.framework.web.domain.AjaxResult;

/**
 * 验证码操作处理
 * 
 * @author vls-house
 */
@CrossOrigin
@RestController
public class CaptchaController
{
    @Autowired
    private RedisCache redisCache;
    @Autowired
    private SmsManager smsManager;

    /**
     * 生成图形验证码
     */
    @ApiOperation("生成图形验证码")
    @GetMapping("/captchaImage")
    public AjaxResult getCode(HttpServletResponse response) throws IOException
    {
        // 生成随机字串
        String verifyCode = VerifyCodeUtils.generateVerifyCode(4);
        // 唯一标识
        String uuid = IdUtils.simpleUUID();
        String verifyKey = Constants.CAPTCHA_CODE_KEY + uuid;

        redisCache.setCacheObject(verifyKey, verifyCode, Constants.CAPTCHA_EXPIRATION, TimeUnit.MINUTES);
        // 生成图片
        int w = 111, h = 36;
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        VerifyCodeUtils.outputImage(w, h, stream, verifyCode);
        try
        {
            AjaxResult ajax = AjaxResult.success();
            ajax.put("uuid", uuid);
            ajax.put("img", Base64.encode(stream.toByteArray()));
            return ajax;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return AjaxResult.error(e.getMessage());
        }
        finally
        {
            stream.close();
        }
    }

    /**
     * 生成短信验证码
     * @param sms 参数
     * @return 结果
     */
    @ApiOperation("生成短信验证码")
    @PostMapping("/captchaSms")
    public AjaxResult getSmsCode(@RequestBody SmsVo sms) {
        if (StringUtils.isNull(sms) || StringUtils.isEmpty(sms.getPhone())) {
            return AjaxResult.error("手机号不能为空");
        }
        String verifyCode = VerifyCodeUtils.randomVerifyCode();
        String content = "您重置密码的验证码是：" + verifyCode + "。为保障信息安全，请勿告诉他人。【筑梦悠然】";
        String param = "{\"reqHdr\":{\"key\":\"\",\"tranCode\":\"MB1001\"},\"reqBody\":{\"tel\":\"" + sms.getPhone() + "\",\"content\":\"" + content + "\",\"sys\":\"房产导购平台\"}}";
        smsManager.sendSms(sms.getPhone(), param);
        // 唯一标识
        String uuid = IdUtils.simpleUUID();
        String verifyKey = Constants.CAPTCHA_CODE_KEY + uuid;
        redisCache.setCacheObject(verifyKey, verifyCode, Constants.CAPTCHA_EXPIRATION, TimeUnit.MINUTES);
        AjaxResult ajax = AjaxResult.success();
        ajax.put("uuid", uuid);
        return ajax;
    }
}
