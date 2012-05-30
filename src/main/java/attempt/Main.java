package attempt;

import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.TwitterApi;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuthService;

public class Main {
	public static void main(String[] args) {
		OAuthService service = new ServiceBuilder().provider(TwitterApi.class).apiKey("xyz").apiSecret("pqr")
		.build();
		
		Token requestToken = service.getRequestToken();
		
		String authUrl = service.getAuthorizationUrl(requestToken);
		
		Verifier verifier = new Verifier("hgjgks");
		Token accessToken = service.getAccessToken(requestToken, verifier);
		
		OAuthRequest request = new OAuthRequest(Verb.GET, "http://api.twitter.com/1/account/verify_credentials.xml");
		
		service.signRequest(accessToken, request);
		
		Response response = request.send();
		
		System.out.println(response.getBody());
	}
}
