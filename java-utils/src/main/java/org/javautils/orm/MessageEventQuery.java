package org.javautils.orm;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class MessageEventQuery {

	private OrderBy orderBy = OrderBy.CreationTimestampAsc;
	private Integer maxResults;
	private TradeId tradeId;
	private Set<TradeId> tradeIds;
	private Long tradeIdGroupId;
	private Date creationTimestampStart;
	private Date creationTimestampEnd;
	private String messageStatus;
	private PayloadType payloadType;
	private List<PayloadType> payloadTypes;
	private BusinessLine businessLine;
	private String region;
	private Long txmMessageId;
	private String eventName;
	private String source;
	
	public OrderBy getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(OrderBy orderBy) {
		this.orderBy = orderBy;
	}

	public Integer getMaxResults() {
		return maxResults;
	}

	public void setMaxResults(Integer maxResults) {
		this.maxResults = maxResults;
	}

	public TradeId getTradeId() {
		return tradeId;
	}

	public void setTradeId(TradeId tradeId) {
		this.tradeId = tradeId;
	}

	public Set<TradeId> getTradeIds() {
		return tradeIds;
	}

	public void setTradeIds(Set<TradeId> tradeIds) {
		this.tradeIds = tradeIds;
	}

	public Long getTradeIdGroupId() {
		return tradeIdGroupId;
	}

	public void setTradeIdGroupId(Long tradeIdGroupId) {
		this.tradeIdGroupId = tradeIdGroupId;
	}

	public Date getCreationTimestampStart() {
		return creationTimestampStart;
	}

	public void setCreationTimestampStart(Date creationTimestampStart) {
		this.creationTimestampStart = creationTimestampStart;
	}

	public Date getCreationTimestampEnd() {
		return creationTimestampEnd;
	}

	public void setCreationTimestampEnd(Date creationTimestampEnd) {
		this.creationTimestampEnd = creationTimestampEnd;
	}

	public String getMessageStatus() {
		return messageStatus;
	}

	public void setMessageStatus(String messageStatus) {
		this.messageStatus = messageStatus;
	}

	public PayloadType getPayloadType() {
		return payloadType;
	}

	public void setPayloadType(PayloadType payloadType) {
		this.payloadType = payloadType;
	}

	public List<PayloadType> getPayloadTypes() {
		return payloadTypes;
	}

	public void setPayloadTypes(List<PayloadType> payloadTypes) {
		this.payloadTypes = payloadTypes;
	}

	public BusinessLine getBusinessLine() {
		return businessLine;
	}

	public void setBusinessLine(BusinessLine businessLine) {
		this.businessLine = businessLine;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public Long getTxmMessageId() {
		return txmMessageId;
	}

	public void setTxmMessageId(Long txmMessageId) {
		this.txmMessageId = txmMessageId;
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public MessageEventQuery() {
	}
	
	public static enum OrderBy {
		CreationTimestampAsc, CreationTimestampDesc
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
