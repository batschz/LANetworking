package de.wehub.lanetworking;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.util.Calendar;

import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;

import android.os.AsyncTask;
import android.util.Log;

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
			Log.e("NETWORK 10",Calendar.getInstance().toString());
			result = onPostProcess(_request,null);
			Log.e("NETWORK 11",Calendar.getInstance().toString());

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
	
	protected String readFully(InputStream inputStream)
	        throws IOException {
		Log.e("NETWORK 20",Calendar.getInstance().toString());
	    ByteArrayOutputStream baos = new ByteArrayOutputStream();
	    byte[] buffer = new byte[4096];
	    int length = 0;
	    while ((length = inputStream.read(buffer)) != -1) {
	        baos.write(buffer, 0, length);
	    }
	    Log.e("NETWORK 21",Calendar.getInstance().toString());
	    String out =  new String(baos.toByteArray());
	    Log.e("NETWORK 22",Calendar.getInstance().toString());
	    return out;
	}
	
	protected String getResponseString(LAAbstractRequest request) throws Exception {
		/*
		Log.e("NETWORK 20",Calendar.getInstance().toString());
		BufferedReader br = new BufferedReader(new InputStreamReader(request.getConnection().getInputStream()));
		Log.e("NETWORK 21",Calendar.getInstance().toString());
		StringBuilder sb = new StringBuilder();
		String line;
		Log.e("NETWORK 22",Calendar.getInstance().toString());
		while ((line = br.readLine()) != null) {
			sb.append(line);
		} 
		Log.e("NETWORK 23",Calendar.getInstance().toString());
		String response = sb.toString();
		br.close();
		return response;
		*/
		return readFully(request.getConnection().getInputStream());
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
