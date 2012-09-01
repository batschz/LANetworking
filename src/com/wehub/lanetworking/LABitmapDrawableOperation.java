package com.wehub.lanetworking;

import org.apache.http.HttpResponse;

import android.graphics.drawable.BitmapDrawable;

public class LABitmapDrawableOperation extends LAAbstractHTTPOperation {

	protected LABitmapDrawableOperationListener _listener = null;
	
	public LABitmapDrawableOperation(LAAbstractRequest request, LABitmapDrawableOperationListener listener) {
		super(request);
		_listener = listener;
	}
	
	public interface LABitmapDrawableOperationListener extends LAAbstractHTTPOperationListener {
		public void onSuccess(BitmapDrawable bitmapDrawable);
		public void onFailure(Exception ex);
	}

	@Override
	public Object convertResponse(HttpResponse response) throws Exception {
		return new BitmapDrawable(response.getEntity().getContent());
	}
	
	@Override
	protected boolean onFinished(Object result, Exception ex) {
		if(super.onFinished(result, ex)) {
			_listener.onSuccess((BitmapDrawable)result);
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
		return BitmapDrawable.class;
	}
}
