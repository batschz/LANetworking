package de.wehub.lanetworking;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthScope;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import android.util.Log;

import de.wehub.lanetworking.LAAbstractOperation.LAOperationResult;

public class LAPostRequest extends LAAbstractRequest {

	protected List<NameValuePair> _postValues = new ArrayList<NameValuePair>();
	
	public LAPostRequest(String url) throws Exception {
		super(new URL(url));
	}

	public void addPostValue(String key, String	value) {
		_postValues.add(new BasicNameValuePair(key,value));
	}

	public void execute() throws Exception {
		Log.e("NETWORK 1",Calendar.getInstance().toString());
	     _connection.setDoOutput(true);
	     _connection.setChunkedStreamingMode(0);
	     _connection.setRequestProperty("Connection","Keep-Alive");
	     Log.e("NETWORK 2",Calendar.getInstance().toString());
	     OutputStream out = new BufferedOutputStream(_connection.getOutputStream());
	     Log.e("NETWORK 3",Calendar.getInstance().toString());
	     new UrlEncodedFormEntity(_postValues,HTTP.UTF_8).writeTo(out);
	     Log.e("NETWORK 4",Calendar.getInstance().toString());
	}

}
