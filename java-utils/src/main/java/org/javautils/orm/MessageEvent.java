package org.javautils.orm;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Table(name="TXM_MESSAGE")
@Inheritance(strategy = InheritanceType.JOINED)
public class MessageEvent {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long txmMessageId;
	private Long linkedMessageId;
	private String linkedMessageRelation;
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="trade_id_group_id")
	private TradeIdGroup tradeIdGroup;
	@ElementCollection(fetch=FetchType.EAGER)
	@JoinTable(name="MESSAGE_STATUS", joinColumns=@JoinColumn(name="txm_message_id"))
	@Fetch(FetchMode.SELECT)
	private List<MessageStatus> statuses = new ArrayList<MessageStatus>();
	@Enumerated(EnumType.STRING)
	private PayloadType payloadType;
	@Enumerated(EnumType.STRING)
	private BusinessLine businessLine;
	private Date creationTimestamp;
	private String senderHostName;
	private String senderProcessId;
	private String senderName;
	private String eventName;
	private String transportMessageId;
	private Date transportTimestamp;
	private String actingUser;
	private String source;
	
	public Long getTxmMessageId() {
		return txmMessageId;
	}
	public void setTxmMessageId(Long txmMessageId) {
		this.txmMessageId = txmMessageId;
	}
	public Long getLinkedMessageId() {
		return linkedMessageId;
	}
	public void setLinkedMessageId(Long linkedMessageId) {
		this.linkedMessageId = linkedMessageId;
	}
	public String getLinkedMessageRelation() {
		return linkedMessageRelation;
	}
	public void setLinkedMessageRelation(String linkedMessageRelation) {
		this.linkedMessageRelation = linkedMessageRelation;
	}
	public TradeIdGroup getTradeIdGroup() {
		return tradeIdGroup;
	}
	public void setTradeIdGroup(TradeIdGroup tradeIdGroup) {
		this.tradeIdGroup = tradeIdGroup;
	}
	public List<MessageStatus> getStatuses() {
		return statuses;
	}
	public void setStatuses(List<MessageStatus> statuses) {
		this.statuses = statuses;
	}
	public PayloadType getPayloadType() {
		return payloadType;
	}
	public void setPayloadType(PayloadType payloadType) {
		this.payloadType = payloadType;
	}
	public BusinessLine getBusinessLine() {
		return businessLine;
	}
	public void setBusinessLine(BusinessLine businessLine) {
		this.businessLine = businessLine;
	}
	public Date getCreationTimestamp() {
		return creationTimestamp;
	}
	public void setCreationTimestamp(Date creationTimestamp) {
		this.creationTimestamp = creationTimestamp;
	}
	public String getSenderHostName() {
		return senderHostName;
	}
	public void setSenderHostName(String senderHostName) {
		this.senderHostName = senderHostName;
	}
	public String getSenderProcessId() {
		return senderProcessId;
	}
	public void setSenderProcessId(String senderProcessId) {
		this.senderProcessId = senderProcessId;
	}
	public String getSenderName() {
		return senderName;
	}
	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}
	public String getEventName() {
		return eventName;
	}
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}
	public String getTransportMessageId() {
		return transportMessageId;
	}
	public void setTransportMessageId(String transportMessageId) {
		this.transportMessageId = transportMessageId;
	}
	public Date getTransportTimestamp() {
		return transportTimestamp;
	}
	public void setTransportTimestamp(Date transportTimestamp) {
		this.transportTimestamp = transportTimestamp;
	}
	public String getActingUser() {
		return actingUser;
	}
	public void setActingUser(String actingUser) {
		this.actingUser = actingUser;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	
	@PrePersist
	void prePersist() {
		creationTimestamp = new Timestamp(System.currentTimeMillis());
	}
	
	public Set<String> getExcludedFieldNames() {
		Set<String> names = new HashSet<String>();
		return names;
	}
	
	@Override
	public String toString() {
		Set<String> excludedFieldNames = getExcludedFieldNames();
		return new ReflectionToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).
				setExcludeFieldNames(excludedFieldNames.toArray(new String[excludedFieldNames.size()])).
				toString();
	}
}
