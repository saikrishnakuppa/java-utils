package org.javautils.xml;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.junit.Test;

public class TextXmlUtils {

	@Test
	public void testSimpleStaxParser() {
		
		String xml = "<config><book id=\"1\" name=\"abc\"/><person id=\"1\" name=\"xyz\"/><person id=\"2\" name=\"abc\"/></config>";
		
		class A extends SimpleStaxParser {
			class Book {
				String id;
				String name;
			}
			
			class Person {
				String id;
				String name;
			}
			
			List<Book> books = new ArrayList<Book>();
			List<Person> persons = new ArrayList<Person>();
			Book b = null;
			Person p = null;

			public List<Book> getBooks() {
				return books;
			}
			
			@Override
			protected void startElement(Stack<Element> elements) {
				Element current = elements.peek();
				if(current == null)
					return;
				if(current.name.equalsIgnoreCase("book")) {
					b = new Book();
					b.id = current.attributes.get("id");
					b.name = current.attributes.get("name");
				}
				if(current.name.equalsIgnoreCase("person")) {
					p = new Person();
					p.id = current.attributes.get("id");
					p.name = current.attributes.get("name");
				}
			}
			
			@Override
			protected void endElement(Stack<Element> elements) {
				Element current = elements.peek();
				if(current == null)
					return;
				if(current.name.equalsIgnoreCase("book")) {
					books.add(b);
				}
				if(current.name.equalsIgnoreCase("person")) {
					persons.add(p);
				}
			}
		}
		A a = new A();
		a.parse(xml);
		assertEquals(1, a.books.size());
		assertEquals(2, a.persons.size());
		assertEquals("1", a.books.get(0).id);
		assertEquals("abc", a.books.get(0).name);
		assertEquals("1", a.persons.get(0).id);
		assertEquals("xyz", a.persons.get(0).name);
		assertEquals("2", a.persons.get(1).id);
		assertEquals("abc", a.persons.get(1).name);
	}
	
	@Test
	public void testJaxbXmlParser() {
		String xml = "<config><book id=\"1\" name=\"abc\"/><person id=\"1\" name=\"xyz\"/><person id=\"2\" name=\"abc\"/></config>";
		JaxbWrapper parser = new JaxbWrapper(Config.class);
		Config config = parser.unmarshall(xml);
		assertEquals(1, config.book.size());
		assertEquals(2, config.person.size());
		assertEquals("1", config.book.get(0).id);
		assertEquals("abc", config.book.get(0).name);
		assertEquals("1", config.person.get(0).id);
		assertEquals("xyz", config.person.get(0).name);
		assertEquals("2", config.person.get(1).id);
		assertEquals("abc", config.person.get(1).name);
	}
	
	@XmlRootElement
	@XmlAccessorType(XmlAccessType.FIELD)
	public static class Config {
		@XmlElement
		List<Person> person;
		@XmlElement
		List<Book> book;
	}
	
	@XmlAccessorType(XmlAccessType.FIELD)
	public static class Person {
		@XmlAttribute
		String id;
		@XmlAttribute
		String name;
	}

	@XmlAccessorType(XmlAccessType.FIELD)
	public static class Book {
		@XmlAttribute
		String id;
		@XmlAttribute
		String name;
	}
}
