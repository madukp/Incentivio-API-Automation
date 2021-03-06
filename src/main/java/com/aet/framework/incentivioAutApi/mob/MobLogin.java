package com.aet.framework.incentivioAutApi.mob;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.aet.framework.incentivioAutApi.utilities.Constants;
import com.aet.framework.incentivioAutApi.utilities.PropertyFile;
import com.aet.framework.incentivioAutApi.utilities.Utilities;

public class MobLogin {
	HttpURLConnection connection = null;

	public String getAutherizationMobile() throws Exception {
		String baseUrl = Utilities.getDomain() + PropertyFile.readProperty("mob_login_url", Constants.FILE_BASE_URLS);
		System.out.println(baseUrl);
		System.out.println(baseUrl);

		try {
			URL url = new URL(baseUrl); // "https://incentqa.aeturnum.com/incentivio-mobile-api/oauth/token"
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod(Constants.POST);
			connection.setRequestProperty(Constants.ContentType, "application/x-www-form-urlencoded");

			connection.setRequestProperty(Constants.Authorization, "Basic Y2xpZW50YXBwOjEyMzQ1Ng==");
			connection.setRequestProperty(Constants.Accept, "application/json, text/plain, */*");

			connection.setUseCaches(false);
			connection.setDoOutput(true);

			String username = PropertyFile.readProperty("username", Constants.FILE_MOB_CREDENTAILS);
			String password = PropertyFile.readProperty("password", Constants.FILE_MOB_CREDENTAILS);
			String clientID = PropertyFile.readProperty("clientId", Constants.FILE_MOB_CREDENTAILS);
			String grantType = PropertyFile.readProperty("grant_type", Constants.FILE_MOB_CREDENTAILS);
			String scope = PropertyFile.readProperty("scope", Constants.FILE_MOB_CREDENTAILS);

			// Send request - PARAMETERIZED
			DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
			String request = "username=shihan.s@gmail.com&password=Asd123&clientId=94cf98f2-8514-40c0-bfb6-c04a52e32714&grant_type=password&scope=read%20write";
			wr.writeBytes(request);
			wr.close();

			// Get Response
			InputStream is = connection.getInputStream();
			BufferedReader rd = new BufferedReader(new InputStreamReader(is));
			StringBuilder response = new StringBuilder(); //

			String line;
			while ((line = rd.readLine()) != null) {
				response.append(line);
				response.append('\r');
			}
			rd.close();

			String token = response.toString().split(":")[1].split(",")[0].replace("\"", "");

			return token;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}
	}
}
