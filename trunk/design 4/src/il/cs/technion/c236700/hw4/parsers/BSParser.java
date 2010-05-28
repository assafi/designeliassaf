package il.cs.technion.c236700.hw4.parsers;

import org.w3c.dom.Element;

import il.cs.technion.c236700.hw4.BestSeller;


public class BSParser extends XmlParser<BestSeller>{

	public BSParser(String xmlPath) {
		super(xmlPath,"xml/best_sellers.xsd", "book");
	}

	@Override
	protected BestSeller parseElement(Element e) {
		String name;
		int copies;
		name = e.getAttribute("name");
		copies = Integer.parseInt(e.getElementsByTagName("copies").item(0).getTextContent());
		return new BestSeller(name, copies);
	}
	

}
