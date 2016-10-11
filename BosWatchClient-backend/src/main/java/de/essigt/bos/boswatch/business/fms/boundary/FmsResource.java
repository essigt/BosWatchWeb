package de.essigt.bos.boswatch.business.fms.boundary;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import de.essigt.bos.boswatch.business.fms.entity.FMS;
import de.essigt.bos.boswatch.business.fms.entity.FMSGroup;

@Stateless
@Path("fms")
@Produces(MediaType.APPLICATION_JSON)
public class FmsResource {

	@Inject
	private FmsService fs;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public JsonArray all(@Context UriInfo info) {
		JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();		
		fs.findAll().stream()
			.sorted((c1, c2) -> (int)(c2.getId() - c1.getId()) )
			.map(n -> createJsonObject(n))		
			.forEach(arrayBuilder::add);
		
		
		return arrayBuilder.build();
	}
	
	@GET
	@Path("latest")
	@Produces(MediaType.APPLICATION_JSON)
	public JsonObject latest() {
		FMS latest = fs.findLatest();
		return createJsonObject(latest);
	}

	@GET
	@Path("current")
	@Produces(MediaType.APPLICATION_JSON)
	public JsonArray current(@Context UriInfo info) {
		JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();		
//		fs.findCurrent().stream()
//			.sorted((c1, c2) -> (int)(c2.getId() - c1.getId()) )
//			.map(n -> createJsonObject(n))		
//			.forEach(arrayBuilder::add);
		
		//Group
		
		List<FMSGroup> groups = fs.findGroups();
		Map<FMSGroup, List<FMS>> map = new HashMap<>();
		
		groups.forEach( group -> map.put(group, new ArrayList<>()));
		
		for(FMS fms : fs.findCurrent()) {
			for(FMSGroup g : groups) {
				if(g.getMembers().contains(fms.getFms())) {
					map.get(g).add(fms);
				}
			}
		}
		
		for(FMSGroup g : groups) {
			arrayBuilder.add(buildJsonGroup(g, map.get(g)));	
		}
		
		return arrayBuilder.build();
	}


	/**
	 * 
	 * @param g
	 * @param list
	 * @return
	 */
	private JsonObject buildJsonGroup(FMSGroup g, List<FMS> list) {
		JsonObjectBuilder groupObject = Json.createObjectBuilder();
		groupObject.add("id", g.getId());
		groupObject.add("name", g.getName());
		groupObject.add("color", g.getColor());
		
		JsonArrayBuilder array = Json.createArrayBuilder();
		for(FMS fms : list) {
			array.add( createJsonObject(fms));
		}
		groupObject.add("members", array);
		
		return groupObject.build();
	}

	
	/**
	 * 
	 * @param n
	 * @return
	 */
	private JsonObject createJsonObject(FMS n) {
		JsonObjectBuilder json = Json.createObjectBuilder();
		json.add("id", n.getId());
		json.add("timestamp", n.getTime().toGMTString());
		json.add("status", n.getStatus());
		json.add("fms", n.getFms());
		json.add("vehicle", n.getFms().substring( n.getFms().length() - 4));
		json.add("description", n.getDescription());
		
		return json.build();
	}

}
