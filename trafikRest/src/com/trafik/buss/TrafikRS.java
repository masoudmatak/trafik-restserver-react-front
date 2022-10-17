package com.trafik.buss;


import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/trafik")
public class TrafikRS {
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String testMM() {
		return "masoud testar!";
	}
	
	/**
	 * om man vill, det går leverera svaret som en html-sida med inbyggt JavaScript och slippa ha React o detta fall
	 * @return
	 */
	@GET
	@Produces(MediaType.TEXT_HTML)
	public String svaraHtmlTabel() {
		return "<html><title>Bus stationer</title><body><h1>bus stationer!</h1>"
				+ "<center><table border=\"1\"><tr><td>AA</td><td>BB</td></tr><tr><td>Kungstragarden</td>"
				+ "<td>Slussen</td></tr></table></center></body></html>";
	}
	
	
}
