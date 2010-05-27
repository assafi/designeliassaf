package il.cs.technion.c236700.hw4.parsers;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public abstract class XmlParser<T> {
	private final String xml, xsd;
	final private String tagName;
	
	public XmlParser(String xmlFile, String xsdFile, String tagName) {
		this.xml = xmlFile;
		this.xsd = xsdFile;
		this.tagName = tagName;
	}
	
	protected abstract T parseElement(Element e); 
	
	public Iterable<T> parse() throws Exception{
		SchemaFactory factory = 
            SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
		Schema hw4Schema = factory.newSchema(new File(xsd));
		hw4Schema.newValidator().validate(new StreamSource(xml));
		DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		File xmlFile = new File(xml);
		if (!xmlFile.exists())
			throw new FileNotFoundException(xml);
		Element docelem = docBuilder.parse(xmlFile).getDocumentElement();
		LinkedList<T> elements = new LinkedList<T>();
		NodeList bookNodes = docelem.getElementsByTagName(tagName);
		for (int i=0 ; i < bookNodes.getLength() ; i++ )
			elements.add( parseElement( (Element) bookNodes.item(i) ) );
		return elements;
	}
}
