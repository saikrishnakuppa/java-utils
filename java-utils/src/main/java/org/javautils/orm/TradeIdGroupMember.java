package org.javautils.orm;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="TRADE_ID")
public class TradeIdGroupMember {

	@EmbeddedId
	private TradeId tradeId;

	public TradeIdGroupMember() {
	}
	
	public TradeIdGroupMember(TradeId tradeId) {
		super();
		this.tradeId = tradeId;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = result * prime + ((tradeId == null) ? 0 : tradeId.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		
		if(this == obj)
			return true;
		if(obj == null)
			return false;
		if(this.getClass() != obj.getClass())
			return false;
		TradeIdGroupMember other = (TradeIdGroupMember)obj;
		if(tradeId == null) {
			if(other.tradeId != null)
				return false;
		} else if(!tradeId.equals(other.tradeId))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return String.valueOf(tradeId);
	}
}
