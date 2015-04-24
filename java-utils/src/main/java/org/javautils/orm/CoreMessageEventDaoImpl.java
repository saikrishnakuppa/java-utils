package org.javautils.orm;


import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ListJoin;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.SetJoin;
import javax.persistence.criteria.Subquery;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public class CoreMessageEventDaoImpl extends AbstractDbDao implements CoreMessageEventDao {

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public MessageEvent findById(long id) {
		return entityManager.find(MessageEvent.class, id);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<MessageEvent> findByMessageId2(Long messageId) {
		Query cb = entityManager.createQuery("select t from MessageEvent t where t.tradeIdGroup in "
				+ " ( select t1.tradeIdGroup from MessageEvent t1 where t1.txmMessageId=:messageId)");
		cb.setParameter("messageId", messageId);
		return (List<MessageEvent>)cb.getResultList();
	}
	
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<MessageEvent> findByMessageId(Long messageId) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<MessageEvent> cq = cb.createQuery(MessageEvent.class);
		Root<MessageEvent> messageEventRoot = cq.from(MessageEvent.class);
		
		Subquery<MessageEvent> sq = cq.subquery(MessageEvent.class);
		Root<MessageEvent> messageEventSubRoot = sq.from(MessageEvent.class);
		
		sq.select(messageEventSubRoot.get(MessageEvent_.tradeIdGroup).get("tradeIdGroupId"));
		sq.where(cb.equal(messageEventSubRoot.get(MessageEvent_.txmMessageId), cb.parameter(Long.class, "messageId")));
		
		cq.select(messageEventRoot);
		cq.where(cb.in(messageEventRoot.get(MessageEvent_.tradeIdGroup).get("tradeIdGroupId")).value(sq));
		cq.orderBy(cb.desc(messageEventRoot.get(MessageEvent_.creationTimestamp)));
		
		TypedQuery<MessageEvent> typedQuery = entityManager.createQuery(cq);
		typedQuery.setParameter("messageId", messageId);
		return typedQuery.getResultList();
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public <T extends MessageEvent> T save(T messageEvent) {
		System.out.println("Beging save " + messageEvent);
		T savedMessageEvent = entityManager.merge(messageEvent);
		System.out.println("End save " + savedMessageEvent);
		return savedMessageEvent;
	}
	
	@Override
	public List<MessageEvent> findByTradeId(TradeId tradeId) {
		MessageEventQuery query = new MessageEventQuery();
		query.setTradeId(tradeId);
		return query(query);
	}
	
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<MessageEvent> query(MessageEventQuery query) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<MessageEvent> cq = builder.createQuery(MessageEvent.class);
		return doQuery(builder,cq,cq.from(MessageEvent.class),query,new ArrayList<Predicate>());
	}

	protected <T extends MessageEvent> List<T> doQuery(CriteriaBuilder builder, CriteriaQuery<T> cq, Root<T> messageEvent, MessageEventQuery query, List<Predicate> predicates) {
		if(query.getTradeId() != null) {
			SetJoin<TradeIdGroup, TradeIdGroupMember> tradeIdJoin = messageEvent.join(MessageEvent_.tradeIdGroup).join(TradeIdGroup_.members);
			predicates.add(builder.equal(tradeIdJoin.get(TradeIdGroupMember_.tradeId), query.getTradeId()));
		}
		if(query.getTradeIds() != null) {
			SetJoin<TradeIdGroup, TradeIdGroupMember> tradeIdJoin = messageEvent.join(MessageEvent_.tradeIdGroup).join(TradeIdGroup_.members);
			predicates.add(tradeIdJoin.get(TradeIdGroupMember_.tradeId).in(query.getTradeId()));
		}
		if(query.getTradeIdGroupId() != null) {
			predicates.add(builder.equal(messageEvent.get(MessageEvent_.tradeIdGroup).get("tradeIdGroupId"), query.getTradeIdGroupId()));
		}
		if(query.getCreationTimestampStart() != null) {
			predicates.add(builder.greaterThanOrEqualTo(messageEvent.get(MessageEvent_.creationTimestamp), query.getCreationTimestampStart()));
		}
		if(query.getCreationTimestampEnd() != null) {
			predicates.add(builder.lessThanOrEqualTo(messageEvent.get(MessageEvent_.creationTimestamp), query.getCreationTimestampEnd()));
		}
		if(query.getMessageStatus() != null) {
			ListJoin<T, MessageStatus> messageStatusJoin = messageEvent.join(MessageEvent_.statuses);
			predicates.add(builder.equal(messageStatusJoin.get(MessageStatus_.status), query.getMessageStatus()));
		}
		if(query.getPayloadType() != null) {
			predicates.add(builder.equal(messageEvent.get(MessageEvent_.payloadType), query.getPayloadType()));
		} else if(query.getPayloadTypes() != null) {
			predicates.add(messageEvent.in(MessageEvent_.payloadType).in(query.getPayloadTypes()));
		}
		if(query.getBusinessLine() != null) {
			predicates.add(builder.equal(messageEvent.get(MessageEvent_.businessLine), query.getBusinessLine()));
		}
		if(query.getBusinessLine() != null) {
			predicates.add(builder.equal(messageEvent.get(MessageEvent_.businessLine), query.getBusinessLine()));
		}
		if(query.getTxmMessageId() != null) {
			predicates.add(builder.equal(messageEvent.get(MessageEvent_.txmMessageId), query.getTxmMessageId()));
		}
		if(query.getEventName() != null) {
			predicates.add(builder.equal(messageEvent.get(MessageEvent_.eventName), query.getEventName()));
		}
		if(query.getSource() != null) {
			predicates.add(builder.equal(messageEvent.get(MessageEvent_.source), query.getSource()));
		}
		cq.where(predicates.toArray(new Predicate[]{}));
		
		if(query.getOrderBy() != null) {
			switch(query.getOrderBy()) {
				case CreationTimestampAsc: 
					cq.orderBy(builder.asc(messageEvent.get(MessageEvent_.creationTimestamp)),
							builder.asc(messageEvent.get(MessageEvent_.txmMessageId)));
					break;
				case CreationTimestampDesc:
					cq.orderBy(builder.desc(messageEvent.get(MessageEvent_.creationTimestamp)),
							builder.desc(messageEvent.get(MessageEvent_.txmMessageId)));
					break;
				default:
					break;
			}
		}
		
		if(query.getMaxResults() == null) {
			cq.distinct(true);
		}
		
		TypedQuery<T> typedQuery = entityManager.createQuery(cq);
		if(query.getMaxResults() != null) {
			typedQuery.setMaxResults(query.getMaxResults());
		}
		return typedQuery.getResultList();
	}
	
	public static void main(String[] args) throws Exception {
		AnnotationConfigApplicationContext appCtx = new AnnotationConfigApplicationContext(DataSourceConfig.class);
		appCtx.registerShutdownHook();
		CoreMessageEventDao messageDao = (CoreMessageEventDao)appCtx.getBean("coreMessageEventDao");
		MessageEvent messageEvent = new MessageEvent();
		messageEvent.setSenderHostName("SaiKuppa_Host");
		messageEvent.setSenderName("SaiKuppa");
		messageEvent.setSenderProcessId("SK_Process");
		messageEvent.setEventName("New");
		messageEvent.setPayloadType(PayloadType.FpmlNotification);
		messageEvent.setBusinessLine(BusinessLine.FI);
		messageEvent.setSource("CME");
		TradeIdGroup group = new TradeIdGroup();
		group.addTradeId(TradeId.markitwireId(22222));
		messageEvent.setTradeIdGroup(group);
//		MessageEvent savedMessageEvent = messageDao.save(messageEvent);
//		System.out.println(messageDao.findById(savedMessageEvent.getTxmMessageId()));
//		System.out.println(messageDao.findByTradeId(TradeId.markitwireId(22222)));
		System.out.println(messageDao.findByMessageId(1l));
	}
}
