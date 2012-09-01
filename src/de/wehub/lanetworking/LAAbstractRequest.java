package de.wehub.lanetworking;

import org.apache.http.client.methods.HttpRequestBase;

public abstract class LAAbstractRequest {
	
	protected HttpRequestBase _request = null;
	
	public LAAbstractRequest(HttpRequestBase request) {
		_request = request;
	}
	
	public HttpRequestBase getRequest() {
		return _request;
	}
	
	
	
}
