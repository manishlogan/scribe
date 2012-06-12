package client;

import java.util.Scanner;

import org.scribe.builder.ServiceBuilder;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuthService;

import api.TripItAPI;

public class TripItClient {
	
	//Basic url pattern will be https://api.tripit.com/v1/list/<Object-Type>, where object-type will be the one that we want to fetch 
	private static final String PROTECTED_RESOURCE = "https://api.tripit.com/v1/list/trip";	//This is to get the trips of user.
	
	public static void main(String[] args) {
		OAuthService service = new ServiceBuilder().provider(TripItAPI.class)
		.apiKey("apikey").
		apiSecret("apiSecret").debug().build();
		
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("Trying to obtain Request Token:");
		Token requestToken = service.getRequestToken();
		
		System.out.println("Please go to this URL and insert the verifier below:"+service.getAuthorizationUrl(requestToken));
		String verifierString = scanner.nextLine();
		Verifier verifier = new Verifier(verifierString);
		
		Token accessToken = service.getAccessToken(requestToken, verifier);
		System.out.println("Access Token accessed");
		
		System.out.println("Requesting protected Resource");
		
		//The below code is used to access the Protected Resource
		OAuthRequest request = new OAuthRequest(Verb.GET, PROTECTED_RESOURCE);

	    service.signRequest(accessToken, request);
	    request.addHeader("GData-Version", "3.0");
	    Response response = request.send();
	    
	    System.out.println(response.getCode());
	    System.out.println(response.getBody());
	}
}
