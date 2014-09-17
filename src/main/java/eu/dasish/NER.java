package eu.dasish;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class NER {

	public static final String FreelingURL = "http://ws04.iula.upf.edu/freelingner/";
	public static final String StaggerURL = "http://ws04.iula.upf.edu/stagger/";

	public static String freeling(String txt, String lang) {
		String projectName = "project" + System.currentTimeMillis()
				+ new Random().nextInt(100);
		Client client = Client.create();
		WebResource webRes1 = client.resource(FreelingURL + projectName);
		ClientResponse res1 = webRes1.put(ClientResponse.class);
		if (res1.getStatus() == 201) {
			// project is created
			WebResource webRes2 = client.resource(FreelingURL + projectName
					+ "/input/input.txt");
			ClientResponse res2 = webRes2.queryParam("language", lang)
					.queryParam("contents", txt)
					.queryParam("inputtemplate", "textinput")
					.post(ClientResponse.class);
			if (res2.getStatus() == 200) {
				// input is added
				ClientResponse res3 = webRes1.post(ClientResponse.class);
				if (res3.getStatus() == 202) {
					// processing started
					return projectName;
				}
			}
		}
		// something went wrong
		return null;
	}
	
	public static String stagger(String txt) {
		String projectName = "project" + System.currentTimeMillis()
				+ new Random().nextInt(100);
		Client client = Client.create();
		WebResource webRes1 = client.resource(StaggerURL + projectName);
		ClientResponse res1 = webRes1.put(ClientResponse.class);
		if (res1.getStatus() == 201) {
			// project is created
			WebResource webRes2 = client.resource(StaggerURL + projectName
					+ "/input/input.txt");
			ClientResponse res2 = webRes2.queryParam("contents", txt)
					.queryParam("inputtemplate", "textinput")
					.post(ClientResponse.class);
			if (res2.getStatus() == 200) {
				// input is added
				ClientResponse res3 = webRes1.post(ClientResponse.class);
				if (res3.getStatus() == 202) {
					// processing started
					return projectName;
				}
			}
		}
		// something went wrong
		return null;		
	}

	public static void danish(String txt, String email) {
		// Client client = RESTUtil.sslClient(); //Client.create();
		//
		// WebResource webResource =
		// client.resource("https://clarin.dk/tools/createByGoalChoice");
		//
		// final MultivaluedMap<String, String> formData = new
		// MultivaluedMapImpl();
		// formData.add("action", "batch");
		// formData.add("wrkflw", "NER");
		// formData.add("input", txt);
		// formData.add("mail2", email);
		//
		// ClientResponse response = webResource.type(
		// MediaType.APPLICATION_FORM_URLENCODED_TYPE).post(
		// ClientResponse.class, formData);
		//
		// if (response.getStatus() != 200) {
		// throw new RuntimeException("Failed : HTTP error code : "
		// + response.getStatus());
		// }

		// System.out.println(response.getStatus());
		// System.out.println("status: " + response.getEntity(String.class));
		// System.out.println(response);
		// System.out.println("length: " + response.getLength());
		// System.out.println("headers: " + response.getHeaders());
		// System.out.println("**************");

		Map<String, String> params = new HashMap<String, String>();
		params.put("action", "batch");
		params.put("wrkflw", "NER");
		params.put("text", txt);
		params.put("mail2", email);
		try {
			RESTUtil.httpsFormPost(
					"https://clarin.dk/tools/createByGoalChoice", params);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
