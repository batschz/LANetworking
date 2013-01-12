package de.wehub.lanetworking;

import java.net.URL;

import org.apache.http.client.methods.HttpGet;

public class LAGetRequest extends LAAbstractRequest {

	public LAGetRequest(String url) throws Exception {
		super(new URL(url));
	}

	@Override
	protected void execute() throws Exception {
		// do nothing at the moment
		
	}

}
