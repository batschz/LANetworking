package de.wehub.lanetworking;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

public class LAPostRequest extends LAAbstractRequest {

	protected List<NameValuePair> _postValues = new ArrayList<NameValuePair>();
	
	public LAPostRequest(String url) {
		super(new HttpPost(url));
	}
	
	@Override
	public void initRequest() {
		super.initRequest();
		try {
			((HttpPost)getRequest()).setEntity(new UrlEncodedFormEntity(_postValues,HTTP.UTF_8));
		} catch(Exception ex) {} 
	}
	
	public void addPostValue(String key, String	value) {
		_postValues.add(new BasicNameValuePair(key,value));
	}

}
