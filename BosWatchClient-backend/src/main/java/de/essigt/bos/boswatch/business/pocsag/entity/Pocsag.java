package de.essigt.bos.boswatch.business.pocsag.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;


@Entity
@Table(name = "bos_pocsag")
public class Pocsag {

	@Id
	private Long id;

	@Temporal(TemporalType.TIMESTAMP)
	private Date time;

	@Column(length = 7)
	private String ric;

	@Min(1)
	@Max(4)
	private int function;

	private char functionChar;

	private String msg;

	private int bitrate;

	private String description;


	public Pocsag() {
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public Date getTime() {
		return time;
	}


	public void setTime(Date date) {
		this.time = date;
	}


	public String getRic() {
		return ric;
	}


	public void setRic(String ric) {
		this.ric = ric;
	}


	public int getFunction() {
		return function;
	}


	public void setFunction(int funktion) {
		this.function = funktion;
	}


	public char getFunctionChar() {
		return functionChar;
	}


	public void setFunctionChar(char funktionChar) {
		this.functionChar = funktionChar;
	}


	public String getMsg() {
		return msg;
	}


	public void setMsg(String msg) {
		this.msg = msg;
	}


	public int getBitrate() {
		return bitrate;
	}


	public void setBitrate(int bitrate) {
		this.bitrate = bitrate;
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
		result = prime * result + bitrate;
		result = prime * result + ((time == null) ? 0 : time.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + function;
		result = prime * result + functionChar;
		result = prime * result + ((msg == null) ? 0 : msg.hashCode());
		result = prime * result + ((ric == null) ? 0 : ric.hashCode());
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
		Pocsag other = (Pocsag) obj;
		if (bitrate != other.bitrate)
			return false;
		if (time == null) {
			if (other.time != null)
				return false;
		} else if (!time.equals(other.time))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (function != other.function)
			return false;
		if (functionChar != other.functionChar)
			return false;
		if (msg == null) {
			if (other.msg != null)
				return false;
		} else if (!msg.equals(other.msg))
			return false;
		if (ric == null) {
			if (other.ric != null)
				return false;
		} else if (!ric.equals(other.ric))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "Pocsag [time=" + time + ", ric=" + ric + ", function=" + function + ", msg=" + msg + "]";
	}


}
