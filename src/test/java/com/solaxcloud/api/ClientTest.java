package com.solaxcloud.api;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.jboss.resteasy.client.jaxrs.engines.ApacheHttpClient43Engine;
import org.jboss.resteasy.client.jaxrs.internal.LocalResteasyProviderFactory;
import org.jboss.resteasy.client.jaxrs.internal.ResteasyClientBuilderImpl;
import org.jboss.resteasy.plugins.providers.RegisterBuiltin;
import org.jboss.resteasy.spi.ResteasyProviderFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import jakarta.ws.rs.ProcessingException;
import jakarta.ws.rs.client.ResponseProcessingException;
import jakarta.ws.rs.core.MediaType;

@RunWith(MockitoJUnitRunner.class)
public class ClientTest {
	@Spy
	MockCloseableHttpClient httpClient;

	@Spy
	RequestConfig requestConfig;

	@Mock
	CloseableHttpResponse response;

	@Mock
	StatusLine statusLine;

	@Mock
	Header contentTypeHeader;

	@Mock
	HttpEntity entity;

	@Before
	public void setUp() {
		// Prepare mocks
		when(httpClient.getConfig()).thenReturn(requestConfig);
		when(response.getStatusLine()).thenReturn(statusLine);
		when(response.getEntity()).thenReturn(entity);
		when(contentTypeHeader.getName()).thenReturn(HttpHeaders.CONTENT_TYPE);
		when(contentTypeHeader.getValue()).thenReturn(MediaType.APPLICATION_JSON);
		// Inject mocked response into mocked http client
		httpClient.setResponse(response);
		// Prepare the resteasy stack to use existing providers and configuration from
		// class path
		ResteasyProviderFactory providerFactory = new LocalResteasyProviderFactory(RegisterBuiltin
				.getClientInitializedResteasyProviderFactory(Thread.currentThread().getContextClassLoader()));
		providerFactory.register(new ApacheHttpClient43Engine(httpClient));
		ResteasyClientBuilderImpl.setProviderFactory(providerFactory);
	}

	@Test
	public void wrong_token() throws Exception {
		String json = "{\"exception\":\"TokenId not available! Please check your tokenId!\",\"success\":false}";

		// And
		when(statusLine.getStatusCode()).thenReturn(HttpStatus.SC_OK);
		when(response.getAllHeaders()).thenReturn(new Header[] { contentTypeHeader });
		when(entity.getContent()).thenReturn(new ByteArrayInputStream(json.getBytes()))
				.thenReturn(new ByteArrayInputStream(json.getBytes())); // Two requests requires two streams
		// And
		Client client = createAuthenticatedClient();
		// When
		assertThatExceptionOfType(SolaxCloudException.class).isThrownBy(() -> {
			client.getRealTimeStatus();
		}).withMessage("TokenId not available! Please check your tokenId!");
	}

	@Test
	public void wrong_serial() throws Exception {
		String json = "{\"success\":false,\"exception\":\"Query success!\",\"result\":\"no auth!\"}";

		// And
		when(statusLine.getStatusCode()).thenReturn(HttpStatus.SC_OK);
		when(response.getAllHeaders()).thenReturn(new Header[] { contentTypeHeader });
		when(entity.getContent()).thenReturn(new ByteArrayInputStream(json.getBytes()))
				.thenReturn(new ByteArrayInputStream(json.getBytes())); // Two requests requires two streams
		// And
		Client client = createAuthenticatedClient();
		// When
		assertThatExceptionOfType(SolaxCloudException.class).isThrownBy(() -> {
			client.getRealTimeStatus();
		}).withMessage("no auth!");
	}

	@Test
	public void missing_serial() throws Exception {
		String json = "{\"success\":false,\"exception\":\"Query success!\",\"result\":\"sn invalid!\"}";

		// And
		when(statusLine.getStatusCode()).thenReturn(HttpStatus.SC_OK);
		when(response.getAllHeaders()).thenReturn(new Header[] { contentTypeHeader });
		when(entity.getContent()).thenReturn(new ByteArrayInputStream(json.getBytes()))
				.thenReturn(new ByteArrayInputStream(json.getBytes())); // Two requests requires two streams
		// And
		Client client = createAuthenticatedClient();
		// When
		assertThatExceptionOfType(SolaxCloudException.class).isThrownBy(() -> {
			client.getRealTimeStatus();
		}).withMessage("sn invalid!");
	}

	@Test
	public void fetch_success() throws Exception {
		// Given
		String json = "{\"success\":true,\"exception\":\"Query success!\","
				+ "\"result\":{\"inverterSN\":\"ABCDEFGHIJKL123\",\"sn\":\"ZXYWVUTSRQPO\",\"acpower\":0.0,\"yieldtoday\":0.8,"
				+ "\"yieldtotal\":1601.5,\"feedinpower\":-307.0,\"feedinenergy\":1391.04,\"consumeenergy\":2076.99,\"feedinpowerM2\":0.0,\"soc\":10.0,\"peps1\":0.0,\"peps2\":0.0,"
				+ "\"peps3\":0.0,\"inverterType\":\"3\",\"inverterStatus\":\"109\",\"uploadTime\":\"2022-01-26 20:19:46\",\"batPower\":0.0,\"powerdc1\":0.0,\"powerdc2\":0.0,"
				+ "\"powerdc3\":null,\"powerdc4\":null}" + "}";
		// And
		when(statusLine.getStatusCode()).thenReturn(HttpStatus.SC_OK);
		when(response.getAllHeaders()).thenReturn(new Header[] { contentTypeHeader });
		when(entity.getContent()).thenReturn(new ByteArrayInputStream(json.getBytes()))
				.thenReturn(new ByteArrayInputStream(json.getBytes())); // Two requests requires two streams
		// And
		Client client = createAuthenticatedClient();
		// When
		RealTimeStatus status = client.getRealTimeStatus();
		// Then
		checkResultedStatus(status);

		// Just for fun, we try it a second time
		RealTimeStatus status2 = client.getRealTimeStatus();
		checkResultedStatus(status2);
	}

	private void checkResultedStatus(RealTimeStatus status) {
		assertThat(status).isNotNull();
		assertThat(status.getAcpower()).isEqualTo(0.0);
		assertThat(status.getBatPower()).isEqualTo(0.0);
		assertThat(status.getConsumeenergy()).isEqualTo(2076.99);
		assertThat(status.getFeedinenergy()).isEqualTo(1391.04);
		assertThat(status.getFeedinpower()).isEqualTo(-307.0);
		assertThat(status.getFeedinpowerM2()).isEqualTo(0.0);
		assertThat(status.getId()).isNull();
		assertThat(status.getInverterSN()).isEqualTo("ABCDEFGHIJKL123");
		assertThat(status.getInverterStatus()).isEqualByComparingTo(InverterStatus.IDLE_MODE);
		assertThat(status.getInverterType()).isEqualByComparingTo(InverterType.X1_HYBRID_FIT);
		assertThat(status.getPeps1()).isEqualTo(0.0);
		assertThat(status.getPeps2()).isEqualTo(0.0);
		assertThat(status.getPeps3()).isEqualTo(0.0);
		assertThat(status.getPowerdc1()).isEqualTo(0.0);
		assertThat(status.getPowerdc2()).isEqualTo(0.0);
		assertThat(status.getPowerdc3()).isNull();
		assertThat(status.getPowerdc4()).isNull();
		assertThat(status.getSn()).isEqualTo("ZXYWVUTSRQPO");
		assertThat(status.getSoc()).isEqualTo(10.0);
		assertThat(status.getUploadTime()).isEqualTo(
				LocalDateTime.parse("2022-01-26 20:19:46", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
		assertThat(status.getYieldtoday()).isEqualTo(0.8);
		assertThat(status.getYieldtotal()).isEqualTo(1601.5);
	}

	@Test
	public void special_field() throws Exception {
		// Given
		String json = "{\"success\":true,\"exception\":\"Query success!\","
				+ "\"result\":{\"inverterSN\":\"ABCDEFGHIJKL123\",\"sn\":\"ZXYWVUTSRQPO\",\"acpower\":0.0,\"yieldtoday\":0.8,"
				+ "\"yieldtotal\":1601.5,\"feedinpower\":-307.0,\"feedinenergy\":1391.04,\"consumeenergy\":2076.99,\"feedinpowerM2\":0.0,\"soc\":10.0,\"peps1\":0.0,\"peps2\":0.0,"
				+ "\"peps3\":0.0,\"inverterType\":\"3\",\"inverterStatus\":\"109\",\"uploadTime\":\"2022-01-26 20:19:46\",\"batPower\":0.0,\"powerdc1\":0.0,\"powerdc2\":0.0,"
				+ "\"powerdc3\":\"1.0\",\"powerdc4\":\"2.0\"}" + "}";
		// And
		when(statusLine.getStatusCode()).thenReturn(HttpStatus.SC_OK);
		when(response.getAllHeaders()).thenReturn(new Header[] { contentTypeHeader });
		when(entity.getContent()).thenReturn(new ByteArrayInputStream(json.getBytes()))
				.thenReturn(new ByteArrayInputStream(json.getBytes())); // Two requests requires two streams
		// And
		Client client = createAuthenticatedClient();
		// When
		RealTimeStatus status = client.getRealTimeStatus();
		// Then
		assertThat(status).isNotNull();
		assertThat(status.getAcpower()).isEqualTo(0.0);
		assertThat(status.getBatPower()).isEqualTo(0.0);
		assertThat(status.getConsumeenergy()).isEqualTo(2076.99);
		assertThat(status.getFeedinenergy()).isEqualTo(1391.04);
		assertThat(status.getFeedinpower()).isEqualTo(-307.0);
		assertThat(status.getFeedinpowerM2()).isEqualTo(0.0);
		assertThat(status.getId()).isNull();
		assertThat(status.getInverterSN()).isEqualTo("ABCDEFGHIJKL123");
		assertThat(status.getInverterStatus()).isEqualByComparingTo(InverterStatus.IDLE_MODE);
		assertThat(status.getInverterType()).isEqualByComparingTo(InverterType.X1_HYBRID_FIT);
		assertThat(status.getPeps1()).isEqualTo(0.0);
		assertThat(status.getPeps2()).isEqualTo(0.0);
		assertThat(status.getPeps3()).isEqualTo(0.0);
		assertThat(status.getPowerdc1()).isEqualTo(0.0);
		assertThat(status.getPowerdc2()).isEqualTo(0.0);
		assertThat(status.getPowerdc3()).isEqualByComparingTo(new Double(1.0));
		assertThat(status.getPowerdc4()).isEqualByComparingTo(new Double(2.0));
		assertThat(status.getSn()).isEqualTo("ZXYWVUTSRQPO");
		assertThat(status.getSoc()).isEqualTo(10.0);
		assertThat(status.getYieldtoday()).isEqualTo(0.8);
		assertThat(status.getYieldtotal()).isEqualTo(1601.5);
	}

	@Test
	public void wrong_upload_time_format() throws Exception {
		// Given
		String json = "{\"success\":true,\"exception\":\"Query success!\","
				+ "\"result\":{\"inverterSN\":\"ABCDEFGHIJKL123\",\"sn\":\"ZXYWVUTSRQPO\",\"acpower\":0.0,\"yieldtoday\":0.8,"
				+ "\"yieldtotal\":1601.5,\"feedinpower\":-307.0,\"feedinenergy\":1391.04,\"consumeenergy\":2076.99,\"feedinpowerM2\":0.0,\"soc\":10.0,\"peps1\":0.0,\"peps2\":0.0,"
				+ "\"peps3\":0.0,\"inverterType\":\"3\",\"inverterStatus\":\"109\",\"uploadTime\":\"2022-01-26T20:19:46.462-02:00\",\"batPower\":0.0,\"powerdc1\":0.0,\"powerdc2\":0.0,"
				+ "\"powerdc3\":\"1.0\",\"powerdc4\":\"2.0\"}" + "}";
		// And
		when(statusLine.getStatusCode()).thenReturn(HttpStatus.SC_OK);
		when(response.getAllHeaders()).thenReturn(new Header[] { contentTypeHeader });
		when(entity.getContent()).thenReturn(new ByteArrayInputStream(json.getBytes()))
				.thenReturn(new ByteArrayInputStream(json.getBytes())); // Two requests requires two streams
		// And
		Client client = createAuthenticatedClient();
		// When
		assertThatExceptionOfType(SolaxCloudException.class).isThrownBy(() -> {
			client.getRealTimeStatus();
		}).withCauseInstanceOf(ResponseProcessingException.class).withCauseInstanceOf(ProcessingException.class)
				.withRootCauseInstanceOf(DateTimeParseException.class);

	}

	/**** Private stuff ****/

	private Client createAuthenticatedClient() {
		Client client = new Client(new Client.Authentication() {
			@Override
			public String getToken() {
				return "1234919203482034892048920";
			}

			@Override
			public String getSerialNumber() {
				return "ZXYWVUTSRQPO";
			}
		});
		return client;
	}
}
