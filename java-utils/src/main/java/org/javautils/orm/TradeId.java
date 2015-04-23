package org.javautils.orm;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Embeddable;

@Embeddable
public class TradeId implements Comparable<TradeId>, Serializable {

	public static enum Type {
		MarkitWire(1, "MarkitWire"),
		Cme(2, "CME"),
		Lch(3, "LCH");
		
		private final int code;
		private final String tradeType;
		
		private Type(int code, String tradeType) {
			this.code = code;
			this.tradeType = tradeType;
		}
		public int getCode() {
			return code;
		}
		public String getTradeType() {
			return tradeType;
		}
	}

	private static final Map<Integer, Type> CODES = new HashMap<Integer, Type>();
	
	static {
		for(Type type: Type.values()) {
			CODES.put(type.code, type);
		}
	}
	
	private Type type;
	private String id;
	
	public TradeId() {
	}
	
	public TradeId(Type type, String id) {
		super();
		this.type = type;
		this.id = id.trim().toLowerCase();
	}
	
	public static TradeId markitwireId(long dealId) {
		return new TradeId(Type.MarkitWire, String.valueOf(dealId));
	}
	
	public static TradeId cmeId(long id) {
		return new TradeId(Type.Cme, String.valueOf(id));
	}
	
	public static TradeId lchId(long id) {
		return new TradeId(Type.Lch, String.valueOf(id));
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == this)
			return true;
		if(obj == null)
			return false;
		if(getClass() != obj.getClass())
			return false;
		TradeId other = (TradeId)obj;
		if(id == null) {
			if(other.id != null)
				return false;
		} else if(!id.equals(other.id))
			return false;
		if(type != other.type)
			return false;
		return true;
	}

	@Override
	public int compareTo(TradeId tradeId) {
		String me = type.name() + id;
		String other = tradeId.type.name() + tradeId.id;
		return me.compareTo(other);
	}
	
	@Override
	public String toString() {
		return "TradeId [type=" + type + ", id=" + id + "]";
	}
}
