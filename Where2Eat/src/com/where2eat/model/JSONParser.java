package com.where2eat.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.StrictMode;



public class JSONParser {

static InputStream is = null;
static JSONObject jObj = null;
static String json = "";

public JSONParser() {
    // TODO Auto-generated constructor stub
}

public String getJSONFromURL(String stringUrl) {
	try {
	avoidConnectionRestriction();
		
	HttpClient httpclient = new DefaultHttpClient();
	//HttpPost httppost = new HttpPost("http://searchit.no-ip.info:8080/david/");
	
	//String url = "http://searchit.no-ip.info:8080/androidServices/login?webservice=true&service=restaurantServlet&name=hola&cooking=parrilla&username=USER&password=123";
	//String url = "http://localhost:8081/androidServices/login?webservice=true&service=restaurantServlet&name=hola&cooking=parrilla&username=USER&password=123";
	 
	HttpGet httpget = new HttpGet(stringUrl);
	
	//httppost.setEntity(new StringEntity(postData));
	//httppost.setHeader("host", "http://searchit.no-ip.info:8080/david/");
	//HttpResponse response = httpclient.execute(httppost);
	 HttpResponse response = httpclient.execute(httpget);
	 
	 HttpEntity entity = response.getEntity();
	 
	 HttpEntity httpEntity = response.getEntity();

     is = entity.getContent();
     
     

	} catch (ClientProtocolException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	try {
	        BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
	        StringBuilder sb = new StringBuilder();
	        String line = null;

	        while((line = reader.readLine()) != null){
	            sb.append(line + "\n");
	            //Log.e("test: ", sb.toString());
	        }

	        json = sb.toString();
	        is.close();
	    } catch (Exception e) {
	        // TODO Auto-generated catch block
	        //Log.e("buffer error", "Error converting result " + e.toString());
	    }
    return json;
}

@TargetApi(Build.VERSION_CODES.GINGERBREAD)
public void avoidConnectionRestriction(){
	
	if (android.os.Build.VERSION.SDK_INT > 9) {
	      StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
	      StrictMode.setThreadPolicy(policy);
	}
}


}