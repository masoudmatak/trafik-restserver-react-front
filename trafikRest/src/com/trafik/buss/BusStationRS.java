package com.trafik.buss;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/stationer")
public class BusStationRS {
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getTable() throws Exception {
		Fetcher fetcher = new Fetcher();
		return fetcher.getTopTenStation();
	}

}
