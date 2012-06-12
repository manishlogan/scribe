package api;

import org.scribe.builder.api.DefaultApi10a;
import org.scribe.model.Token;

public class TripItAPI extends DefaultApi10a{
	/**
	 * Authorization URL used to obtain Verifier for the application
	 * For a web-based application, change the oauth_callback from oob to the websites url.
	 * */
	private static String AUTHORIZATION_URL = "https://www.tripit.com/oauth/authorize?oauth_token=%s"+"&oauth_callback=oob";
	
	@Override
	public String getAccessTokenEndpoint() {
		//Get Access Token from TripIt.com
		return "https://api.tripit.com/oauth/access_token";
	}
	
	@Override
	public String getRequestTokenEndpoint() {
		//Get Request Token from TripIt.com
		return "https://api.tripit.com/oauth/request_token";
	}
	
	@Override
	public String getAuthorizationUrl(Token requestToken) {
		return String.format(AUTHORIZATION_URL, requestToken.getToken());
	}
}
