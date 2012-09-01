package de.wehub.lanetworking;

import org.apache.http.HttpResponse;
import org.json.JSONObject;

public class LAJSONOperation extends LAAbstractHTTPOperation {
	
	protected LAJSONOperationListener _listener = null;
	
	public LAJSONOperation(LAAbstractRequest request, LAJSONOperationListener listener) {
		super(request);
		_listener = listener;
	}
	
	public interface LAJSONOperationListener extends LAAbstractHTTPOperationListener{
		public void onSuccess(JSONObject json);
		
	}

	@Override
	protected boolean onFinished(Object result, Exception ex) {
		if(super.onFinished(result, ex)) {
			_listener.onSuccess((JSONObject)result);
			return true;
		}
		return false;
	}
	

	@Override
	public Object convertResponse(HttpResponse response) throws Exception {
		return new JSONObject(getResponseString(response));
	}

	@Override
	protected LAAbstractHTTPOperationListener getListener() {
		return _listener;
	}

	@Override
	protected Class<?> getExpectedClass() {
		return JSONObject.class;
	}
}
