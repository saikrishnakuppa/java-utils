package org.javautils.orm;


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
	@Transactional(propagation = Propagation.REQUIRED)
	public <T extends MessageEvent> T save(T messageEvent) {
		System.out.println("Beging save " + messageEvent);
		T savedMessageEvent = entityManager.merge(messageEvent);
		System.out.println("End save " + savedMessageEvent);
		return savedMessageEvent;
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
		group.addTradeId(TradeId.markitwireId(12345));
		messageEvent.setTradeIdGroup(group);
		MessageEvent savedMessageEvent = messageDao.save(messageEvent);
		System.out.println(messageDao.findById(savedMessageEvent.getTxmMessageId()));
	}
}
