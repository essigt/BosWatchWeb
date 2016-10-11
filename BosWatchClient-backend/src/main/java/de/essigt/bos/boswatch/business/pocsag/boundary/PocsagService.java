package de.essigt.bos.boswatch.business.pocsag.boundary;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import de.essigt.bos.boswatch.business.pocsag.entity.Pocsag;



/**
 * 
 * @author essigt
 *
 */
@Stateless
public class PocsagService {


	@PersistenceContext
	EntityManager em;


	/**
	 * 
	 * @return
	 */
	public List<Pocsag> findLast600Messages() {
		TypedQuery<Pocsag> query = em.createQuery("SELECT msg FROM Pocsag msg WHERE msg.msg <> null AND msg.msg <> '' AND msg.ric NOT LIKE '0113%' AND msg.ric NOT LIKE '%0141100%' ORDER BY msg.time DESC", Pocsag.class);
		query.setMaxResults(600);

		return query.getResultList();
	}
	
	
	public Pocsag findLastest() {
		TypedQuery<Pocsag> query = em.createQuery("SELECT msg FROM Pocsag msg WHERE msg.msg <> null AND msg.msg <> '' AND msg.ric NOT LIKE '0113%' AND msg.ric NOT LIKE '%0141100%' ORDER BY msg.id DESC", Pocsag.class);
		query.setMaxResults(1);
		return query.getSingleResult();
	}


	public List<Pocsag> findAllMessages() {
		TypedQuery<Pocsag> query = em.createQuery("SELECT msg FROM Pocsag msg WHERE msg.msg <> null AND msg.msg <> '' AND msg.ric NOT LIKE '0113%' ORDER BY msg.time DESC", Pocsag.class);		

		return query.getResultList();
	}


}
