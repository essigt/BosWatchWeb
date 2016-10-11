package de.essigt.bos.boswatch.business.fms.boundary;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import de.essigt.bos.boswatch.business.fms.entity.FMS;
import de.essigt.bos.boswatch.business.fms.entity.FMSGroup;
import de.essigt.bos.boswatch.business.fms.entity.FMSLatest;

@Stateless
public class FmsService {
	
	@PersistenceContext
	EntityManager em;
	
	
	public List<FMS> findAll() {
		return em.createQuery("SELECT f FROM FMS f ORDER BY f.id DESC", FMS.class)
				.setMaxResults(50)
				.getResultList();
	}
	
	public FMS findLatest() {
		return em.createQuery("SELECT f FROM FMS f ORDER BY f.id DESC", FMS.class)
				.setMaxResults(1)
				.getResultList().get(0);
	}
	
	public List<FMSLatest> findCurrent() {
		return em.createQuery("SELECT f FROM FMSLatest f WHERE f.status <> 'f' ORDER BY f.id DESC", FMSLatest.class)				
				.getResultList();
	}

	public List<FMSGroup> findGroups() {
		return em.createQuery("SELECT g FROM FMSGroup g ORDER BY g.pos ASC", FMSGroup.class).getResultList();		
	}

}
