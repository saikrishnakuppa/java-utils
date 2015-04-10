package org.javautils.messaging;

import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueReceiver;
import javax.jms.QueueSession;
import javax.jms.Session;

public abstract class JmsMessageListener implements javax.jms.MessageListener {

	private QueueConnectionFactory factory;
	private QueueConnection connection;
	private QueueSession session;
	private Queue queue;
	private QueueReceiver queueReceiver;
	private boolean transactional;
	
	public JmsMessageListener(QueueConnectionFactory factory, Queue queue, boolean transactional) {
	}
	
	public synchronized void start() throws JMSException {
		try {
			connection = factory.createQueueConnection();
			session = connection.createQueueSession(transactional, Session.AUTO_ACKNOWLEDGE);
			queueReceiver = session.createReceiver(queue);
			queueReceiver.setMessageListener(this);
			connection.start();
			
		} catch(JMSException e){}
	}
	
	protected void commit() throws JMSException {
		if(session != null)
			session.commit();
	}
	
	protected void rollback() throws JMSException {
		if(session != null)
			session.rollback();
	}
	
	public synchronized void stop() throws JMSException {
		connection.stop();
	}
	
	public synchronized void dispose() throws JMSException {
		connection.close();
	}
	
}
