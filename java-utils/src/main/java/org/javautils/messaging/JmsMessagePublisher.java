package org.javautils.messaging;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;

public class JmsMessagePublisher {

	private QueueConnectionFactory factory;
	private QueueConnection connection;
	private QueueSession session;
	private Queue queue;
	private QueueSender sender;
	
	/**
	 * new com.ibm.mq.jms.MQQueueConnectionFactory(port, host, channel, queueManager, securityExit, transactionType);
	 * new com.ibm.mq.jms.MQQueue(queueName);
	 * @param factory
	 * @param queue
	 */
	public JmsMessagePublisher(QueueConnectionFactory factory, Queue queue) {
		this.factory = factory;
		this.queue = queue;
	}
	
	public synchronized void createSessionAndSender() throws JMSException {
		try {
			connection = factory.createQueueConnection();
			session = connection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
			sender = session.createSender(queue);
		} catch(JMSException e){}
	}

	public void publish(Message message) throws JMSException {
		if(sender == null)
			createSessionAndSender();
		sender.send(message);
	}
	
	public void publish(String content) throws JMSException {
		if(sender == null)
			createSessionAndSender();
		publish(session.createTextMessage(content));
	}
}
