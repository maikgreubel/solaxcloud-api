package com.solaxcloud.api;

import org.jboss.resteasy.client.jaxrs.ClientHttpEngine;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.jboss.resteasy.plugins.providers.jackson.ResteasyJackson2Provider;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.core.UriBuilder;

/**
 * This class provides a sophisticated way to retrieve data from solax cloud via
 * API.
 * <p>
 * We use resteasy as restful client api to create a service proxy defined by
 * {@link Service}.
 * </p>
 * 
 * @author Maik Greubel <maikgreubel@gmail.com>
 */
public class Client {

	/**
	 * Save resteasy client instance object in order to cleanly close the connection
	 */
	private ResteasyClient restEasyClient;

	/**
	 * This callback makes it possible to inject authentication information into
	 * client.
	 */
	public interface Authentication {
		/**
		 * Retrieve the api token.
		 * 
		 * @return the token string
		 */
		String getToken();

		/**
		 * Retrieve the serial number of communication module
		 * 
		 * @return the serial number string
		 */
		String getSerialNumber();
	}

	private Authentication authentication;

	private ClientHttpEngine engine;

	private static final String ENDPOINT_ADDRESS = "https://www.solaxcloud.com:9443/proxy/api";

	/**
	 * Create a new client using {@link Authentication} callback.
	 * 
	 * @param authentication
	 */
	public Client(Authentication authentication) {
		this.authentication = authentication;
	}

	/**
	 * Inject a {@link ClientHttpEngine} instance created before.
	 * 
	 * @param httpEngine the client engine to inject
	 * @return fluent interface
	 */
	public Client httpEngine(ClientHttpEngine httpEngine) {
		engine = httpEngine;
		return this;
	}

	/**
	 * Retrieve current status from cloud.
	 * 
	 * @return the readen status
	 */
	public RealTimeStatus getRealTimeStatus() throws SolaxCloudException {
		try {
			Response response = createServiceProxy().getRealtimeInfo(authentication.getToken(),
					authentication.getSerialNumber());
			if (response.isSuccess()) {
				return response.getResult();
			}
			throw new SolaxCloudException(response.getException());
		} catch (ProcessingException pex) {
			throw new SolaxCloudException(pex);
		} finally {
			restEasyClient.close();
		}
	}

	/**
	 * Create a service proxy instance.
	 * 
	 * @return a proxy instance type of {@link Service}
	 */
	private Service createServiceProxy() {
		restEasyClient = new ResteasyClientBuilder().register(createDeserializationProvider()).httpEngine(engine)
				.build();
		ResteasyWebTarget target = restEasyClient.target(UriBuilder.fromPath(ENDPOINT_ADDRESS));
		return target.proxy(Service.class);
	}

	private class ResponseDeserializerProvider extends ResteasyJackson2Provider {

	}

	/**
	 * Create a customer deserialization provider to fix bad json fields.
	 * 
	 * @return provider for deserialization type of {@link ResteasyJackson2Provider}
	 */
	private ResteasyJackson2Provider createDeserializationProvider() {

		ResponseDeserializerProvider responseDeserializerProvider = new ResponseDeserializerProvider();
		ObjectMapper mapper = new ObjectMapper();
		SimpleModule responseDeserializerModule = new SimpleModule("responseDeserializerModule",
				new Version(0, 0, 1, "SNAPSHOT", "com.solaxcloud", "api"));
		responseDeserializerModule.addDeserializer(Response.class, new ResponseDeserializer());
		mapper.registerModule(responseDeserializerModule);
		responseDeserializerProvider.setMapper(mapper);
		return responseDeserializerProvider;
	}
}
