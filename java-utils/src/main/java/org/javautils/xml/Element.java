package org.javautils.xml;

import java.util.Collections;
import java.util.Map;

public class Element {

	final public String qName;
	final public String name;
	final public Map<String, String> attributes;
	private StringBuffer text = new StringBuffer();
	private boolean leaf = false;
	
	public Element(String qName, String name) {
		this(qName, name, Collections.<String, String>emptyMap());
	}
	public Element(String qName, String name, Map<String, String> attributes) {
		this.qName = qName.startsWith(":") ? qName.substring(1) : qName;
		this.name = name;
		this.attributes = Collections.unmodifiableMap(attributes);
	}

	public boolean isLeaf() {
		return leaf;
	}

	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}

	public String getqName() {
		return qName;
	}

	public String getName() {
		return name;
	}
	
	public void appendText(String text) {
		this.text.append(text);
	}
}
