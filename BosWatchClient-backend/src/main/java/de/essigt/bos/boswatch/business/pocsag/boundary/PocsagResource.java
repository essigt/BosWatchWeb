package de.essigt.bos.boswatch.business.pocsag.boundary;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import de.essigt.bos.boswatch.business.pocsag.entity.Pocsag;


@Stateless
@Path("pocsag")
@Produces(MediaType.APPLICATION_JSON)
public class PocsagResource {

	@Inject
	private PocsagService ps;


	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public JsonArray all(@Context UriInfo info) {
		JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
		List<Pocsag> messages = ps.findLastMessagesAndNotELD().stream()
				.filter(m -> !m.getMsg().trim().isEmpty())
				.filter(m -> checkRic(m.getRic(), 114000, 150000))
				.collect( Collectors.toList());
		
		Map<PocsagHeader, List<Pocsag>> grouped = groupByMsgAndTimeRange(messages, 5);
		List<Pocsag> merged = grouped.values().stream().map( group -> mergeGroup(group)).collect( Collectors.toList() );
		
		merged.stream().
			sorted((c1, c2) -> (int)(c2.getId() - c1.getId()) ).
			map(n -> buildJson(n)).
			forEach(arrayBuilder::add);
		
		return arrayBuilder.build();
	}
	
	/**
	 * Retuns the latest pocsag message
	 * @return
	 */
	@GET
	@Path("latest")
	@Produces(MediaType.APPLICATION_JSON)
	public JsonObject latest() {
		Pocsag latest = ps.findLastestNotELD();
		return buildJson(latest);
	}
	/**
	 * 
	 * @param messages
	 * @return
	 */
	private Map<PocsagHeader, List<Pocsag>> groupByMsgAndTimeRange(List<Pocsag> messages, int minutes) {
		Map<PocsagHeader, List<Pocsag>> grouped = new HashMap<>();
		
		for(Pocsag pocsag : messages) {
			PocsagHeader bestHeader = null;
			for(PocsagHeader header : grouped.keySet()) {
				if(header.getMsg().equals(pocsag.getMsg())) {
					if(bestHeader == null) {
						bestHeader = header;
					} else {
						GregorianCalendar cal = new GregorianCalendar();
						cal.setTime(bestHeader.getTime());
						cal.add(GregorianCalendar.MINUTE, minutes);
						
						if(bestHeader.getTime().before(pocsag.getTime()) && cal.getTime().after(pocsag.getTime())) {
							bestHeader = header;
						}
					}
				}
			}
			
			if(bestHeader == null) {
				bestHeader = new PocsagHeader(pocsag);
				grouped.put(bestHeader, new ArrayList<>());
			}
			
			grouped.get(bestHeader).add(pocsag);			
		}
		
		return grouped;
	}

	/**
	 * 
	 * @param group
	 * @return
	 */
	private Pocsag mergeGroup(List<Pocsag> group) {
		Pocsag first = group.get(0);
		Pocsag pocsag = new Pocsag();
		pocsag.setMsg(first.getMsg());
		pocsag.setTime(first.getTime());
		pocsag.setFunctionChar(first.getFunctionChar());
		pocsag.setFunction(first.getFunction());
		pocsag.setId( first.getId() );
		
		pocsag.setRic( group.stream().map(p -> p.getRic()).distinct().collect( Collectors.joining(", ")));		
		return pocsag;
	}


	/**
	 * 
	 * @param pocsag
	 * @return
	 */
	JsonObject buildJson(Pocsag pocsag) {
		JsonObjectBuilder response = Json.createObjectBuilder();

		response.add("id", pocsag.getId());
		String msg = "";
		
		if(pocsag.getMsg().contains(",")) {
			msg = pocsag.getMsg().split(",")[0];
		} else if(pocsag.getRic().trim().equals("0141100")) {
			msg = "-- encrypted --";
		}else {
			msg = pocsag.getMsg();
		}
		response.add("ric", pocsag.getRic());
		response.add("timestamp", pocsag.getTime().toGMTString()); //TODO: Check format
		

		//Old: Pattern pattern = Pattern.compile("([\\wßüäö\\-]+(\\sStraße)?)\\s+(\\d+)", Pattern.CASE_INSENSITIVE);
		Pattern pattern = Pattern.compile("([\\wßüäö\\-]+(\\sStraße)?)\\s+(\\d+\\w?)\\s+(\\(FG-\\w\\w\\)\\s)?([\\wßüäö\\-]+)", Pattern.CASE_INSENSITIVE);
		
		Matcher matcher = pattern.matcher(pocsag.getMsg());
		if (matcher.find()) {			
			String addr = matcher.group(0);
			msg = msg.replace(addr, "");
			
			String street = matcher.group(1);
			String number = matcher.group(3);
			String afuest = matcher.group(4);
			String city = matcher.group(5);

			response.add("addr", street + " " + number + ", " + city);
		}
		response.add("msg", msg);

		return response.build();
	}



	private boolean checkRic(String ric, int lowerBound, int upperBound) {
		try {
			Integer ricNumber = Integer.valueOf(ric);

			return ricNumber >= lowerBound && ricNumber <= upperBound;
		} catch (Exception ex) {
			return false;
		}
	}
	
	
	/**
	 * helper class for grouping of pocsag messages
	 * @author essigt
	 *
	 */
	private class PocsagHeader {
		private String msg;
		private Date time;
		
		public PocsagHeader(Pocsag pocsag) {
			this.msg = pocsag.getMsg();
			this.time = pocsag.getTime();
		}
		
		public String getMsg() {
			return msg;
		}

		public Date getTime() {
			return time;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((msg == null) ? 0 : msg.hashCode());
			result = prime * result + ((time == null) ? 0 : time.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			PocsagHeader other = (PocsagHeader) obj;
			if (msg == null) {
				if (other.msg != null)
					return false;
			} else if (!msg.equals(other.msg))
				return false;
			if (time == null) {
				if (other.time != null)
					return false;
			} else if (!time.equals(other.time))
				return false;
			return true;
		}
	}
}
