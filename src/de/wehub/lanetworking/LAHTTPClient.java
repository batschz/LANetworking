package de.wehub.lanetworking;

import java.security.KeyStore;

import org.apache.http.HttpVersion;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;

public class LAHTTPClient extends DefaultHttpClient {
	
	protected static LAHTTPClient _instance = null; // the one and only HTTPClient instance
	protected boolean _trustAll = false;
	
	public LAHTTPClient() {
		super();
	}

	public LAHTTPClient(ClientConnectionManager conman, HttpParams params) {
		super(conman, params);
	}

	public LAHTTPClient(HttpParams params) {
		super(params);
	}
	
	public static void createClient() {
		createClient(false);
	}
	
	public static void createClient(boolean trustAll) {
		try {
			KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
	        trustStore.load(null, null);

	        SSLSocketFactory sf = new LAInvalidSSLSocketFactory(trustStore);
	        sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

	        HttpParams params = new BasicHttpParams();
	        HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
	        HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);
			
			SchemeRegistry registry = new SchemeRegistry();
		    registry.register(new Scheme("http", new PlainSocketFactory(), 80));
		    registry.register(new Scheme("https", (trustAll ? sf : SSLSocketFactory.getSocketFactory()), 443));
		    _instance = new LAHTTPClient(new ThreadSafeClientConnManager(params, registry), params);
		    _instance.setTrustAll(trustAll);
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public static LAHTTPClient getInstance() {
		if(_instance == null) {
			createClient();
		}
		return _instance;
	}
	
	public boolean isTrustAll() {
		return _trustAll;
	}
	
	protected void setTrustAll(boolean trustAll) {
		_trustAll = trustAll;
	}
	
}
