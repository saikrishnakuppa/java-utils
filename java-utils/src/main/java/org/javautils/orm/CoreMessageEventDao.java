package org.javautils.orm;

import java.util.List;

public interface CoreMessageEventDao {

	public MessageEvent findById(long id);
	public <T extends MessageEvent> T save(T messageEvent);
	public List<MessageEvent> findByTradeId(TradeId tradeId);
	List<MessageEvent> query(MessageEventQuery query);
	List<MessageEvent> findByMessageId(Long messageId);
}
