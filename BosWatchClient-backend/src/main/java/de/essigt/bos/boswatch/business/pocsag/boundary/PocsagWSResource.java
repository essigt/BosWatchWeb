package de.essigt.bos.boswatch.business.pocsag.boundary;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.websocket.OnClose;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import de.essigt.bos.boswatch.business.pocsag.entity.Pocsag;


@Singleton
@ServerEndpoint("/alarms")
@ConcurrencyManagement(ConcurrencyManagementType.BEAN)
public class PocsagWSResource {

	private Session session;

	@Inject
	private PocsagService ps;

	private Pocsag prevPocsag = null;


	@OnOpen
	public void onOpen(Session session) {
		this.session = session;
	}


	@OnClose
	public void onClose() {
		this.session = null;
	}


	//@Schedule(hour = "*", minute = "*", second = "*/5", persistent=false)
	public void update() {
		List<Pocsag> latest = ps.findAllMessages().stream()
				.filter(m -> !m.getMsg().trim().isEmpty())
				// .filter(m -> checkRic(m.getRic(), 114000, 150000))
				.limit(1).collect(Collectors.toList());


		if (this.session != null && this.session.isOpen() /* && !latest.get(0).equals(prevPocsag) */) {
			prevPocsag = latest.get(0);
			try {
				this.session.getBasicRemote().sendText(prevPocsag.toString());
			} catch (IOException e) {
			}
		}
	}


	private boolean checkRic(String ric, int lowerBound, int upperBound) {
		try {
			Integer ricNumber = Integer.valueOf(ric);

			return ricNumber >= lowerBound && ricNumber <= upperBound;
		} catch (Exception ex) {
			return false;
		}
	}
}
