package com.wehub.lanetworking;

import org.apache.http.HttpResponse;

public class LASimpleHTTPOperation extends LAAbstractHTTPOperation {

	protected LASimpleHTTPOperationListener _listener = null;
	
	public LASimpleHTTPOperation(LAAbstractRequest request,LASimpleHTTPOperationListener listener) {
		super(request);
		_listener = listener;
	}
	
	public interface LASimpleHTTPOperationListener extends LAAbstractHTTPOperationListener{
		public void onSuccess(String response);
	}

	@Override
	public Object convertResponse(HttpResponse response) throws Exception {
		return getResponseString(response);
	}

	@Override
	protected boolean onFinished(Object result, Exception ex) {
		if(super.onFinished(result, ex)) {
			_listener.onSuccess((String)result);
			return true;
		}
		return false;
	}

	@Override
	protected LAAbstractHTTPOperationListener getListener() {
		return _listener;
	}

	@Override
	protected Class<?> getExpectedClass() {
		return String.class;
	}
}
