package pl.mobilnebajery.captchahacker;

import java.util.Map;

public interface ICaptchaHacker {

	public String confirmCaptcha(Captcha captcha, String code, String url,
			Map<String, String> params) throws Exception;

	public Captcha getCaptcha(String url) throws Exception;
}