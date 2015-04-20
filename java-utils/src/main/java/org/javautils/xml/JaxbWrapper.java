package org.javautils.xml;

import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class JaxbWrapper {

	private final ThreadLocal<Marshaller> marshaller;
	private final ThreadLocal<Unmarshaller> unmarshaller;
	
	public JaxbWrapper(Class<?>... classes) {
		final JAXBContext context;
		try {
			context = JAXBContext.newInstance(classes);
		} catch(JAXBException e) {
			throw new IllegalStateException(e);
		}
		marshaller = new ThreadLocal<Marshaller>() {
			protected Marshaller initialValue() {
				try {
					return context.createMarshaller();
				} catch (JAXBException e) {
					throw new IllegalStateException(e);
				}
			}
		};
		unmarshaller = new ThreadLocal<Unmarshaller>() {
			protected Unmarshaller initialValue() {
				try {
					return context.createUnmarshaller();
				} catch (JAXBException e) {
					throw new IllegalStateException(e);
				}
			}
		};
	}
	
	public void marshall(Writer writer, Object object) {
		try {
			marshaller.get().marshal(object, writer);
		} catch (JAXBException e) {
			throw new RuntimeException("Unexpected marshal exception", e);
		}
	}
	
	public String marshall(Object object) {
		StringWriter writer = new StringWriter();
		marshall(writer, object);
		return writer.toString();
	}
	
	public <T> T unmarshall(String xml) {
		try {
			return (T) unmarshaller.get().unmarshal(new StringReader(xml));
		} catch (JAXBException e) {
			throw new RuntimeException("Unexpected marshal exception", e);
		}
	}
}
