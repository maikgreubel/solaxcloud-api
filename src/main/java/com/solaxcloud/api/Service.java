package com.solaxcloud.api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

/**
 * This interface describes and works as proxy for a restful client using JAX-RS
 * 3.0 API.
 * 
 * @author Maik Greubel <maikgreubel@gmail.com>
 */
@Path("/")
public interface Service {

	/**
	 * Proxy method to retrieve the current data of inverter identified by serial
	 * number.
	 * 
	 * @param tokenId      the token to use for authentication against the solax
	 *                     cloud api
	 * @param serialNumber the serial number of communication device
	 * @return a wrapped response object type of {@link Response}
	 */
	@GET
	@Path("/getRealtimeInfo.do")
	@Produces({ MediaType.APPLICATION_JSON })
	Response getRealtimeInfo(@QueryParam("tokenId") String tokenId, @QueryParam("sn") String serialNumber);
}
