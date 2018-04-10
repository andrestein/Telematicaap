package rest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONArray;
import org.json.JSONObject;

import converter.XJMapper;
import util.Config;


@Path("/data")
public class DataRest {

	@POST
	@Path("/{path}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addData(@PathParam("path") String path, String json) {
		System.out.println(json);
		return request(json, "add", path);
	}
	
	@POST
	@Path("/matrix")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getMatriz(String info) {
		JSONObject data = null;
		try {
			data = new JSONObject(info);
			String entityid = data.getString("entityid");
			String prev = data.getString("prev");
			String actual = data.getString("actual");
			return Response.ok().entity(data.toString()).build();
		} catch (Exception e) {
			e.printStackTrace();
			JSONObject json = new JSONObject();
			json.put("error", e.getMessage());
			return Response.serverError().entity(json.toString()).build();
		}
	}

	/*
	 * Crea la respuesta al cliente
	 */
	private Response request(String json, String type, String path) {
		Response response;
		String result = "";
		try {
			path = path.replace("+", "/").replace("%2B", "/").replace("%5B", "[").replace("%5D", "]");
			response = Response.ok().entity(result).build();
		} catch (Exception e) {
			e.printStackTrace();
			response = Response.serverError().entity(e.getMessage()).build();
		}
		return response;
	}
}