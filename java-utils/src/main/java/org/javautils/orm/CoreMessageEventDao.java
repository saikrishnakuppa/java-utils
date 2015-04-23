package org.javautils.orm;

public interface CoreMessageEventDao {

	public MessageEvent findById(long id);
	public <T extends MessageEvent> T save(T messageEvent);
}
