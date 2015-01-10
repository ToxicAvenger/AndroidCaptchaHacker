package pl.mobilnebajery.captchahacker;

import java.util.LinkedHashMap;
import java.util.Map;

import pl.mobilnebajery.captchahacker.browser.AbstractBrowser;
import pl.mobilnebajery.captchahacker.browser.DummyBrowser;
import pl.mobilnebajery.captchahacker.browser.PageData;

public class CaptchaHacker implements ICaptchaHacker {

	private AbstractBrowser browser;
	
	public CaptchaHacker() {
		browser = new DummyBrowser();
	}
	
	public Captcha getCaptcha(String url) throws Exception {
		
		PageData data = browser.getPageData(url);
		
		if(data != null) {
			String page = data.getStringData();
			
			if(page != null && page.length() > 0) {
				
				if(page.contains(Utils.CAPTCHA_RESPONSE_FIELD) && page.contains(Utils.CAPTCHA_CHALENGE_FIELD)) {
				
					String challengeUrl = getChallengeUrl(page);
					String challenge = getChallenge(challengeUrl);
					String imageUrl = getImageUrl(challenge);	
					byte[] imageData =	getImageData(imageUrl);
					
					Captcha captcha = new Captcha();
					captcha.setChallengeUrl(challengeUrl);
					captcha.setChallenge(challenge);
					captcha.setImageUrl(imageUrl);
					captcha.setImageData(imageData);
					
					return captcha;
				}
			}
		}
		
		return null;
	}
	
	public String confirmCaptcha(Captcha captcha, String code, String url, Map<String, String> params) throws Exception {
		
		Map<String, String> input = new LinkedHashMap<String, String>(params); 
		input.put(Utils.CAPTCHA_CHALENGE_FIELD, captcha.getChallenge());
		input.put(Utils.CAPTCHA_RESPONSE_FIELD, code);
		
		PageData data = browser.getPostPageData(captcha.getResponseUrl(), url, input);
		
		if(data != null) {
			
			return data.getStringData();
		}
		
		return null;
	}
	
	private byte[] getImageData(String url) throws Exception {

		PageData data = browser.getPageData(url);

		if (data != null) {
			return data.getByteData();
		}

		return null;
	}
	
	private String getChallengeUrl(String page) {
			
		return Utils.CAPTCHA_ID_START + Utils.substringBetween(page, Utils.CAPTCHA_ID_START, "\"");
	}
	
	private String getImageUrl(String page) {
		
		return Utils.CAPTCHA_IMAGE_URL + page;
	}
	
	private String getChallenge(String challengeUrl) throws Exception {
		
		PageData data = browser.getPageData(challengeUrl);

		if (data != null) {
			String page = data.getStringData();
			
			if(page != null) {
				
				return Utils.substringBetween(page, "challenge : '", "',");
			}
		}
		
		return null;
	}	
}
