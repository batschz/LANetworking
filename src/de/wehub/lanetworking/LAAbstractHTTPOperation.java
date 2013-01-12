package de.wehub.lanetworking;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;

public abstract class LAAbstractHTTPOperation extends LAAbstractOperation {
	
	public LAAbstractHTTPOperation(LAAbstractRequest request) {
		super(request);
	}
	
	public interface LAAbstractHTTPOperationListener {
		public void onFailure(Exception ex);
	}
	
	protected abstract LAAbstractHTTPOperationListener getListener();
	
	protected abstract Object convertResponse(LAAbstractRequest request) throws Exception;
	protected abstract Class<?> getExpectedClass();

	@Override
	protected LAOperationResult onPostProcess(LAAbstractRequest request,
			Exception ex) {
		LAOperationResult result = null;
		try {
			if(request != null) {
				if(request.getConnection().getResponseCode() == HttpStatus.SC_OK) {
					result = new LAOperationResult(convertResponse(request));
				} else {
					throw new Exception(request.getConnection().getResponseMessage());
				}
			} else {
				throw ex;
			}
		} catch(Exception innerEx) {
			result = new LAOperationResult(innerEx);
		}
		return result;
	}
	
	@Override
	protected boolean onFinished(Object result, Exception ex) {
		
		if(getListener() != null) {
			if(ex == null && result != null) {
				if(result.getClass().equals(getExpectedClass())) {
					return true;
				} else {
					getListener().onFailure(new Exception("Invalid response format, " + getExpectedClass().getName() +" expected"));
				}
			} else {
				getListener().onFailure(ex);
			}
		}
		return false;
	}
}
