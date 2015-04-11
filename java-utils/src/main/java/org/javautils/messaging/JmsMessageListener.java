package org.javautils.messaging;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueReceiver;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;

public class JmsMessageListener implements javax.jms.MessageListener {

	private QueueConnectionFactory factory;
	private QueueConnection connection;
	private QueueSession session;
	private Queue queue;
	private QueueReceiver receiver;
	private boolean transactional;
	
	public JmsMessageListener(QueueConnectionFactory factory, Queue queue, boolean transactional) {
		this.factory = factory;
		this.queue = queue;
		this.transactional = transactional;
	}

	@Override
	public void onMessage(Message message) {
		int retry=0;
		try {
			retry = message.getIntProperty("JMSXDeliveryCount");
		} catch(JMSException e) {
			
		}
		try {
			String content = ((TextMessage)message).getText();
			//TODO handle
		} catch (JMSException e) {
			e.printStackTrace();
			// MAX_RETRY = 10
			if(retry < 10) {
				try {
					rollback();
				} catch (JMSException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} else {
				try {
					commit();
				} catch (JMSException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
	}
	
	public synchronized void start() throws JMSException {
		try {
			connection = factory.createQueueConnection();
			session = connection.createQueueSession(transactional, Session.AUTO_ACKNOWLEDGE);
			receiver = session.createReceiver(queue);
			receiver.setMessageListener(this);
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
