package com.java.gsm.client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

public class SendHttpPost {

	public void sendRequest(String latitude, String longitude) throws ClientProtocolException, IOException
	{
		DefaultHttpClient httpclient = new DefaultHttpClient();
    	HttpPost httpPost = new HttpPost("http://www.naveen-test.appspot.com/newBusLocation");
        List <NameValuePair> nvps = new ArrayList <NameValuePair>();
        nvps.add(new BasicNameValuePair("latitude",latitude ));
        nvps.add(new BasicNameValuePair("longitude",longitude ));
        httpPost.setEntity(new UrlEncodedFormEntity(nvps));
        HttpResponse response2 = httpclient.execute(httpPost);

        try {
            System.out.println(response2.getStatusLine());
        } finally {
            httpPost.releaseConnection();
        }
	}
}
