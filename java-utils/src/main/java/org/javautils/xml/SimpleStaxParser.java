package org.javautils.xml;

import java.util.Stack;

import javax.xml.stream.XMLInputFactory;

public abstract class SimpleStaxParser {

	static final protected Stop STOP = new Stop();
	static private class Stop extends RuntimeException {
		
	}
	static final private XMLInputFactory factory;
	static {
		factory = XMLInputFactory.newInstance();
		factory.setProperty(XMLInputFactory.IS_COALESCING, true);
	}
	abstract protected void startElement(Stack<Element> elements);
	abstract protected void endElement(Stack<Element> elements);
	
	public void parse(String xml) {
		parse(xml, false, false);
	}
	
	public void parse(String xml, boolean useQualifiedAttributeName, boolean includeNamespaces) {
		try {
			
		} catch(Stop stop) {
			
		} catch(Throwable e) {
			throw new RuntimeException(String.format("Failed to parse xml[%s]", xml), e);
		}
	}
	
	protected void stop() {
		throw STOP;
	}
}
