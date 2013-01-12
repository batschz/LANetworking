package de.wehub.lanetworking;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;

import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;

import android.os.AsyncTask;

public abstract class LAAbstractOperation extends AsyncTask<Void, Void, LAAbstractOperation.LAOperationResult> {

	protected LAAbstractRequest _request = null;
	
	public LAAbstractOperation(LAAbstractRequest request) {
		_request = request;
	}
	
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
	}
	
	protected abstract LAOperationResult onPostProcess(LAAbstractRequest connection, Exception ex);
	protected abstract boolean onFinished(Object result, Exception ex);
	
	@Override
	protected final void onPostExecute(LAOperationResult result) {
		super.onPostExecute(result);
		if(result != null) {
			if(result.isFailed()) {
				onFinished(null, result.getException());
			} else {
				onFinished(result.getResult(),null);
			}
		} else {
			onFinished(null,new Exception("Please do not return null in the onPostProcess method"));
		}
	}
	
	@Override
	protected LAOperationResult doInBackground(Void... arg0) {
		LAOperationResult result = null;
		try {
			_request.init();
			_request.execute();
			result = onPostProcess(_request,null);

		} catch(Exception ex) {
			result = onPostProcess(null,ex);
		} finally {
			_request.cleanup();
		}
		
		return result;
	}
	
	
	public void start() {
		execute();
	}
	
	protected String getResponseString(LAAbstractRequest request) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(request.getConnection().getInputStream()));
		StringBuilder sb = new StringBuilder();
		String line;
		while ((line = br.readLine()) != null) {
			sb.append(line);
		} 
		String response = sb.toString();
		br.close();
		return response;
	}
	
	protected class LAOperationResult {
		Object _result = null;
		
		public LAOperationResult(Object result) {
			_result = result;
		}
		
		public boolean isFailed() {
			if(_result instanceof Exception) {
				return true;
			}
			return false;
		}
		
		public Exception getException() {
			if(isFailed()) {
				return (Exception)_result;
			}
			return null;
		}
		
		public Object getResult() {
			return _result;
		}

	}
}
