package com.devdojo.springboot.javacliente;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.tomcat.util.codec.binary.Base64;
import org.apache.tomcat.util.http.fileupload.IOUtils;

public class JavaClientTest {

	public static void main(String[] args) {
		HttpURLConnection connection = null;
		BufferedReader reader = null;
		String user = "alice";
		String password = "devdojo";
		try {
			URL url = new URL("http://localhost:8080/v1/protected/students/1");
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setRequestProperty("Authorization", "Basic " + encodeUsernamePassword(user, password));
			System.out.println(encodeUsernamePassword(user, password));
			reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			StringBuilder jsonBuilder = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) {
				jsonBuilder.append(line);
			}
			System.out.println(jsonBuilder.toString());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(reader);
			if (connection != null) {
				connection.disconnect();
			}
		}
	}
	
	private static String encodeUsernamePassword(String user, String password) {
		String userPassword = user + ":" + password;
		return new String(Base64.encodeBase64(userPassword.getBytes()));
	}
}





