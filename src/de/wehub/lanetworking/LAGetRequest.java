package de.wehub.lanetworking;

import org.apache.http.client.methods.HttpGet;

public class LAGetRequest extends LAAbstractRequest {

	public LAGetRequest(String url) {
		super(new HttpGet(url));
	}

}
