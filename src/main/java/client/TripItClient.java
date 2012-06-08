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
//	private static final String PROTECTED_RESOURCE_CREATE = "https://api.tripit.com/v1/create";	//This is to create a new trip for the user
	
	public static void main(String[] args) {
		OAuthService service = new ServiceBuilder().provider(TripItAPI.class)
		.apiKey("c7117cc02969ae61bbf4760e92b7c8cd59633b1d").
		apiSecret("a3d2c4b468b18053e6fda8696a5e2aa1c0861970").debug().build();
		
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("Trying to obtain Request Token:");
		Token requestToken = service.getRequestToken();
		
		System.out.println("Please go to this URL and insert the verifier below:"+service.getAuthorizationUrl(requestToken));
		String verifierString = scanner.nextLine();
		Verifier verifier = new Verifier(verifierString);
		
		Token accessToken = service.getAccessToken(requestToken, verifier);
		System.out.println("Access Token accessed");
		
		System.out.println("Requesting protected Resource");
		//The below code is used when we want to create a new object on the website.
//		OAuthRequest request = new OAuthRequest(Verb.POST, PROTECTED_RESOURCE_CREATE);
		
		//The below code is used to access the Protected Resource
		OAuthRequest request = new OAuthRequest(Verb.GET, PROTECTED_RESOURCE);

		//Data used to create a new Object 
//		String data = "<Request><Trip><start_date>2012-06-10</start_date><end_date>2012-06-10</end_date><display_name>Office</display_name><image_url>http://www.tripit.com/images/places/roadtrip.jpg</image_url><is_private>false</is_private><primary_location>Gandhinagar, India</primary_location><PrimaryLocationAddress><address>Gandhinagar, India</address><city>Gandhinagar</city><state>Gujarat</state><country>IN</country><latitude>23.21667</latitude><longitude>72.68333</longitude></PrimaryLocationAddress><TripPurposes><purpose_type_code>B</purpose_type_code><is_auto_generated>false</is_auto_generated></TripPurposes></Trip></Request>";
		//Add the data to request to create a new Object
		//		request.addBodyParameter("xml", data);
	    service.signRequest(accessToken, request);
	    request.addHeader("GData-Version", "3.0");
	    Response response = request.send();
	    
	    System.out.println(response.getCode());
	    System.out.println(response.getBody());
	}
}
