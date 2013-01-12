package de.wehub.lanetworking;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import org.apache.http.auth.Credentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.methods.HttpRequestBase;

import de.wehub.lanetworking.LAAbstractOperation.LAOperationResult;

public abstract class LAAbstractRequest {
	
	protected URL _url;
	protected Credentials _credentials = null;
	protected HttpURLConnection _connection = null;
	
	public LAAbstractRequest(URL url) {
		_url = url;
	}
	
	public void initRequest() {}
	
	public HttpURLConnection getConnection() {
		return _connection;
	}
	
	public void setCredentials(String username, String password) {
		_credentials = new UsernamePasswordCredentials(username,password);
	}
	
	public Credentials getCredentials() {
		return _credentials;
	}
	
	protected void init() throws Exception {
		_connection = (HttpURLConnection) _url.openConnection();
	}
	
	protected abstract void execute() throws Exception;
	
	protected void cleanup() {
		if(_connection != null) {
			_connection.disconnect();
		}
	}
	
}
