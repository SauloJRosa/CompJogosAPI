package br.com.GamesPlat.api.controller.connection;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;

public class ConexaoAPIExterna {
	
	protected JSONObject Conexao(String url) {

		try {

			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();

			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			JSONObject myresponse = new JSONObject(response.toString());

			return myresponse;

		} catch (Exception e) {
			System.out.println(e);
		}
		return null;

	}
	
	protected JSONObject ConexaoPost(String url, String body) {
		
		try {
			
			String post_data = body;
			        	
			URL urlpost = new URL(url);
			HttpURLConnection httpURLConnection = (HttpURLConnection) urlpost.openConnection();
			httpURLConnection.setRequestMethod("POST");
			
			// adding header
			httpURLConnection.setRequestProperty("Content-Type", "application/json");
			httpURLConnection.setDoOutput(true);

			// Adding Post Data
			OutputStream outputStream = httpURLConnection.getOutputStream();
			outputStream.write(post_data.getBytes());
			outputStream.flush();
			outputStream.close();

			String line = "";
			InputStreamReader inputStreamReader = new InputStreamReader(httpURLConnection.getInputStream());
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			StringBuilder response = new StringBuilder();
			while ((line = bufferedReader.readLine()) != null) {
				response.append(line);
			}
			bufferedReader.close();
			
			JSONObject myresponse = new JSONObject(response.toString());
			
			return myresponse;
			
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
		
		
		
	}
	
}
