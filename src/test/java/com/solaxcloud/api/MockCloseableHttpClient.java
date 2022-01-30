package com.solaxcloud.api;

import java.io.IOException;

import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.Configurable;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.protocol.HttpContext;

/**
 * This class provides a basic mockable version of the
 * {@link CloseableHttpClient} in combination with {@link Configurable}.
 * 
 * @author Maik Greubel <maikgreubel@gmail.com>
 */
abstract class MockCloseableHttpClient extends CloseableHttpClient implements Configurable {

	private CloseableHttpResponse response;

	public void setResponse(CloseableHttpResponse response) {
		this.response = response;
	}

	@Override
	protected CloseableHttpResponse doExecute(HttpHost target, HttpRequest request, HttpContext context)
			throws IOException, ClientProtocolException {
		return response;
	}
}
