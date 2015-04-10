package org.javautils.xml;

import java.io.StringReader;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Stack;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;

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
			XMLStreamReader parser = null;
			synchronized(factory) {
				parser = factory.createXMLStreamReader(new StringReader(xml));
			}
			Stack<Element> elementStack = new Stack<Element>();
			while(parser.hasNext()) {
				int event = parser.next();
				if(event == XMLStreamConstants.START_ELEMENT) {
					String qNamePrefix = parser.getName().getPrefix();
					String qNameLocal = parser.getName().getLocalPart();
					String qName = qNamePrefix == null ? qNameLocal : qNamePrefix + ":" + qNameLocal;
					int attributeCount = parser.getAttributeCount();
					int namespaceCount = includeNamespaces ? parser.getNamespaceCount() : 0;
					int nAttributes = attributeCount + namespaceCount;
					Element element = null;
					if(nAttributes == 0)
						element = new Element(qName, parser.getLocalName());
					else {
						Map<String, String> attributes = new LinkedHashMap<String, String>();
						for(int i=0; i<attributeCount; ++i) {
							String name = (useQualifiedAttributeName ? parser.getAttributePrefix(i) + ":" : "") + parser.getAttributeLocalName(i);
							String value = parser.getAttributeValue(i).trim();
							attributes.put(name, value);
						}
						for(int i=0; i<namespaceCount; ++i) {
							String name = "xmlns:"+ parser.getNamespacePrefix(i);
							String value = parser.getNamespaceURI(i);
							attributes.put(name, value);
						}
						if(attributes.size() == 1) {
							Entry<String, String> entry = attributes.entrySet().iterator().next();
							attributes = Collections.singletonMap(entry.getKey(), entry.getValue());
						}
						element = new Element(qName, parser.getLocalName(), attributes);
					}
					if(!elementStack.isEmpty())
						elementStack.peek().setLeaf(false);
					elementStack.push(element);
					elementStack.peek().setLeaf(true);
					startElement(elementStack);
				}
				if(event == XMLStreamConstants.END_ELEMENT) {
					endElement(elementStack);
					elementStack.pop();
				}
				if(event == XMLStreamConstants.CHARACTERS) {
					if(!elementStack.isEmpty()) {
						elementStack.peek().appendText(parser.getText().trim());
					}
				}
			}
		} catch(Stop stop) {
			
		} catch(Throwable e) {
			throw new RuntimeException(String.format("Failed to parse xml[%s]", xml), e);
		}
	}
	
	protected void stop() {
		throw STOP;
	}
}
