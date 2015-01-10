package pl.mobilnebajery.captchahacker;

public class Captcha {
	private String challengeUrl;
	public void setChallengeUrl(String challengeUrl) {
		this.challengeUrl = challengeUrl;
	}
	public String getChallengeUrl() {
		return this.challengeUrl;
	}
	
	private String challenge;
	public void setChallenge(String challenge) {
		this.challenge = challenge;
	}
	public String getChallenge() {
		return this.challenge;
	}
	
	private String imageUrl;
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public String getImageUrl() {
		return this.imageUrl;
	}
	
	private byte[] imageData;
	public void setImageData(byte[] imageData) {
		this.imageData = imageData;
	}
	public byte[] getImageData() {
		return this.imageData;
	}
	
	private String responseUrl;
	public void setResponseUrl(String responseUrl) {
		this.responseUrl = responseUrl;
	}
	public String getResponseUrl() {
		return this.responseUrl;
	}	
}
