package de.essigt.bos.boswatch.business.fms.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

@Entity
@Table(name = "bos_fms")
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public class FMS {
	
	@Id
	private Long id;

	@Temporal(TemporalType.TIMESTAMP)
	private Date time;
	
	@Size(max=8)
	private String fms;
	
	@Size(max=1)
	private String status;
	
	@Size(max=1)
	private String direction;
	
	@Size(max=10)	
	private String directionText;
	
	@Size(max=3)
	private String tsi;
	
	private String description;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public String getFms() {
		return fms;
	}

	public void setFms(String fms) {
		this.fms = fms;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String directions) {
		this.direction = directions;
	}

	public String getDirectionText() {
		return directionText;
	}

	public void setDirectionText(String directionsText) {
		this.directionText = directionsText;
	}

	public String getTsi() {
		return tsi;
	}

	public void setTsi(String tsi) {
		this.tsi = tsi;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((direction == null) ? 0 : direction.hashCode());
		result = prime * result + ((directionText == null) ? 0 : directionText.hashCode());
		result = prime * result + ((fms == null) ? 0 : fms.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((time == null) ? 0 : time.hashCode());
		result = prime * result + ((tsi == null) ? 0 : tsi.hashCode());
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
		FMS other = (FMS) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (direction == null) {
			if (other.direction != null)
				return false;
		} else if (!direction.equals(other.direction))
			return false;
		if (directionText == null) {
			if (other.directionText != null)
				return false;
		} else if (!directionText.equals(other.directionText))
			return false;
		if (fms == null) {
			if (other.fms != null)
				return false;
		} else if (!fms.equals(other.fms))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (time == null) {
			if (other.time != null)
				return false;
		} else if (!time.equals(other.time))
			return false;
		if (tsi == null) {
			if (other.tsi != null)
				return false;
		} else if (!tsi.equals(other.tsi))
			return false;
		return true;
	}

	
}
