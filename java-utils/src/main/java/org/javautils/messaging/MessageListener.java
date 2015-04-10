package org.javautils.messaging;

public interface MessageListener<T> {

	void onMessage(String topic, String sender, Integer messageProperty, T content, String error);
}
