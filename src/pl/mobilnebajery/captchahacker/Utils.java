package pl.mobilnebajery.captchahacker;

public class Utils {

	public static final String CAPTCHA_ID_START = "http://www.google.com/recaptcha/api/challenge?k=";
	public static final String CAPTCHA_RESPONSE_FIELD = "recaptcha_response_field";
	public static final String CAPTCHA_CHALENGE_FIELD = "recaptcha_challenge_field";
	public static final String CAPTCHA_IMAGE_URL = "http://www.google.com/recaptcha/api/image?c=";
	public static final String CAPTCHA_VERIFICATION_URL = "http://www.google.com/recaptcha/api/verify";
	
    public static String substringBetween(String str, String open, String close) {
        if (str == null || open == null || close == null) {
            return null;
        }
        int start = str.indexOf(open);
        if (start != -1) {
            int end = str.indexOf(close, start + open.length());
            if (end != -1) {
                return str.substring(start + open.length(), end);
            }
        }
        return null;
    }
}
