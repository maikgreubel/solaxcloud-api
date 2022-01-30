package com.solaxcloud.api;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.BooleanNode;
import com.fasterxml.jackson.databind.node.TextNode;

/**
 * This class provides a custom serializer in order to fix certain aspects of
 * response json values.
 * <p>
 * The API may returns a response wrapped around a complex type for real time
 * status information in succeeding case. For the failure the same field name
 * may be used as string literal, which breaks the JAX-RS specifications.<br/>
 * <br/>
 * To provide a seamless and not happening-unexpecting-behaviour of this API
 * implementation, this deserializer checks the result response fields for
 * "exception" and "success" in order to decide whether or not the string
 * literal interpretation of "result" should be used.
 * </p>
 * 
 * @author Maik Greubel <maikgreubel@gmail.com>
 */
public class ResponseDeserializer extends StdDeserializer<Response> {

	public ResponseDeserializer() {
		this(Response.class);
	}

	protected ResponseDeserializer(Class<?> vc) {
		super(vc);
	}

	private static final long serialVersionUID = 8172866561083140286L;

	/**
	 * Perform the deserialization of response into a {@link Response} object.
	 * 
	 * @return a valid object type of {@link Response} containing either valid real
	 *         time information in corresponding {@link RealTimeStatus} object or
	 *         the failure cause.
	 */
	@Override
	public Response deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		Response response = new Response();

		JsonNode node = p.getCodec().readTree(p);
		JsonNode result = node.get("result");
		BooleanNode success = (BooleanNode) node.get("success");
		if (!success.asBoolean()) {
			response.setSuccess("false");
			if (result instanceof TextNode) {
				response.setException(result.textValue());
			} else {
				response.setException(node.get("exception").textValue());
			}
		} else {
			response.setSuccess(String.valueOf(node.get("success").booleanValue()));
			response.setException(node.get("exception").textValue());

			RealTimeStatus status = new RealTimeStatus();
			status.setAcpower(result.get("acpower").doubleValue());
			status.setBatPower(result.get("batPower").doubleValue());
			status.setConsumeenergy(result.get("consumeenergy").doubleValue());
			status.setFeedinenergy(result.get("feedinenergy").doubleValue());
			status.setFeedinpower(result.get("feedinpower").doubleValue());
			status.setFeedinpowerM2(result.get("feedinpowerM2").doubleValue());
			status.setInverterSN(result.get("inverterSN").textValue());
			status.setInverterStatus(InverterStatus.fromValue(result.get("inverterStatus").textValue()));
			status.setInverterType(InverterType.fromValue(result.get("inverterType").textValue()));
			status.setPeps1(result.get("peps1").doubleValue());
			status.setPeps2(result.get("peps2").doubleValue());
			status.setPeps3(result.get("peps3").doubleValue());
			status.setPowerdc1(result.get("powerdc1").doubleValue());
			status.setPowerdc2(result.get("powerdc2").doubleValue());
			if (result.get("powerdc3") != null && !result.get("powerdc3").isNull()) {
				status.setPowerdc3(result.get("powerdc3").asDouble());
			}
			if (result.get("powerdc4") != null && !result.get("powerdc4").isNull()) {
				status.setPowerdc4(result.get("powerdc4").asDouble());
			}
			status.setSn(result.get("sn").textValue());
			status.setSoc(result.get("soc").doubleValue());
			try {
				status.setUploadTime(LocalDateTime.parse(result.get("uploadTime").textValue(),
						DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
			} catch (DateTimeParseException e) {
				throw new JsonParseException(p, e.getMessage(), e);
			}
			status.setYieldtoday(result.get("yieldtoday").doubleValue());
			status.setYieldtotal(result.get("yieldtotal").doubleValue());
			response.setResult(status);
		}

		return response;
	}

}
