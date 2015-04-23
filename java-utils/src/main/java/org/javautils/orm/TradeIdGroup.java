package org.javautils.orm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.MapKeyColumn;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
public class TradeIdGroup {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long tradeIdGroupId;
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name="trade_id_group_id", nullable=false)
	private Set<TradeIdGroupMember> members = new HashSet<TradeIdGroupMember>();
//	@OneToMany(mappedBy="tradeIdGroup", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//	private Set<TradeIdLink> tradeIdLinks = new HashSet<TradeIdLink>();
	@ElementCollection(fetch=FetchType.EAGER)
	@MapKeyColumn(name="attribute_name")
	@Column(name="attribute_value")
	@CollectionTable(name="TRADE_ATTRIBUTE", joinColumns=@JoinColumn(name="trade_id_group_id"))
	private Map<String, String> attributes = new HashMap<String, String>();
	
	public Long getTradeIdGroupId() {
		return tradeIdGroupId;
	}
	public void setTradeIdGroupId(Long tradeIdGroupId) {
		this.tradeIdGroupId = tradeIdGroupId;
	}
	public Set<TradeIdGroupMember> getMembers() {
		return members;
	}
	public void setMembers(Set<TradeIdGroupMember> members) {
		this.members = members;
	}
//	public Set<TradeIdLink> getTradeIdLinks() {
//		return tradeIdLinks;
//	}
//	public void setTradeIdLinks(Set<TradeIdLink> tradeIdLinks) {
//		this.tradeIdLinks = tradeIdLinks;
//	}
	public Map<String, String> getAttributes() {
		return attributes;
	}
	public void setAttributes(Map<String, String> attributes) {
		this.attributes = attributes;
	}
	public void addTradeId(TradeId tradeId) {
		members.add(new TradeIdGroupMember(tradeId));
	}
}
