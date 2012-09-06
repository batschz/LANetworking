package de.wehub.lanetworking;

import org.apache.http.auth.Credentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.methods.HttpRequestBase;

public abstract class LAAbstractRequest {
	
	protected HttpRequestBase _request = null;
	protected Credentials _credentials = null;
	
	public LAAbstractRequest(HttpRequestBase request) {
		_request = request;
	}
	
	public void initRequest() {}
	
	public HttpRequestBase getRequest() {
		return _request;
	}
	
	public void setCredentials(String username, String password) {
		_credentials = new UsernamePasswordCredentials(username,password);
	}
	
	public Credentials getCredentials() {
		return _credentials;
	}
	
}
