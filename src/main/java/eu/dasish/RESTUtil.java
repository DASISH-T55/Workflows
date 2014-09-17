package eu.dasish;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.client.urlconnection.HTTPSProperties;

public class RESTUtil {

	public static void httpsFormPost(String url, Map<String, String> data)
			throws IOException {
		URL siteUrl = new URL(url);
		HttpURLConnection conn = (HttpURLConnection) siteUrl.openConnection();
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Type",
				"application/x-www-form-urlencoded");
		conn.setUseCaches(true);
		conn.setDoOutput(true);
		conn.setDoInput(true);

		DataOutputStream out = new DataOutputStream(conn.getOutputStream());

		Set keys = data.keySet();
		Iterator keyIter = keys.iterator();
		String content = "";
		for (int i = 0; keyIter.hasNext(); i++) {
			Object key = keyIter.next();
			if (i != 0) {
				content += "&";
			}
			content += key + "=" + URLEncoder.encode(data.get(key), "UTF-8");
		}
		out.writeBytes(content);
		out.flush();
		out.close();
		BufferedReader in = new BufferedReader(new InputStreamReader(
				conn.getInputStream()));
		String line = "";
		while ((line = in.readLine()) != null) {
			System.out.println(line);
		}
		in.close();
	}

	public static ClientConfig configureClient() {
		System.setProperty("jsse.enableSNIExtension", "false");
		TrustManager[] certs = new TrustManager[] { new X509TrustManager() {
			@Override
			public X509Certificate[] getAcceptedIssuers() {
				return null;
			}

			@Override
			public void checkServerTrusted(X509Certificate[] chain,
					String authType) throws CertificateException {
			}

			@Override
			public void checkClientTrusted(X509Certificate[] chain,
					String authType) throws CertificateException {
			}
		} };
		SSLContext ctx = null;
		try {
			ctx = SSLContext.getInstance("SSL");
			ctx.init(null, certs, new SecureRandom());
		} catch (java.security.GeneralSecurityException ex) {
		}
		HttpsURLConnection.setDefaultSSLSocketFactory(ctx.getSocketFactory());
		ClientConfig config = new DefaultClientConfig();
		try {
			config.getProperties().put(
					HTTPSProperties.PROPERTY_HTTPS_PROPERTIES,
					new HTTPSProperties(new HostnameVerifier() {
						@Override
						public boolean verify(String hostname,
								SSLSession session) {
							return true;
						}
					}, ctx));
		} catch (Exception e) {
		}
		return config;
	}

	public static Client createClient() {
		return Client.create(configureClient());
	}

	public static void main(String[] args) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("action", "batch");
		params.put("wrkflw", "NER");
		params.put("outputformat", "TEI");
		params.put("text",
				"Man kan fremad se, at de har været udset til at læse, at der skal dannes par af ligheder.");
		params.put("mail2", "g.khomeriki@gmail.com");
		try {
			RESTUtil.httpsFormPost(
					"https://clarin.dk/tools/createByGoalChoice", params);
		} catch (IOException e) {
			e.printStackTrace();
		}

//		MultivaluedMap formData = new MultivaluedMapImpl();
//		formData.add("action", "batch");
//		formData.add("wrkflw", "NER");
//		String input = "Man kan fremad se, at de har været udset til at læse, at der skal dannes par af ligheder.";
//		formData.add("text", input);
//		formData.add("outputformat", "TEI");
//		formData.add("mail2", "g.khomeriki@gmail.com");
//		WebResource webResource = createClient().resource(
//				"https://clarin.dk/tools/createByGoalChoice");
//		ClientResponse response = webResource.type(
//				MediaType.APPLICATION_FORM_URLENCODED_TYPE).post(
//				ClientResponse.class, formData);
//		System.out.println("status: " + response.getStatus());
//		System.out.println("entity: " + response.getEntity(String.class));
	}

}
