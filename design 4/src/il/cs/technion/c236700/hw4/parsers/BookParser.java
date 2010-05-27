package il.cs.technion.c236700.hw4.parsers;

import il.cs.technion.c236700.hw4.Book;

import org.w3c.dom.Element;

public class BookParser extends XmlParser<Book> {
	
	public BookParser() {
		super("xml/books.xml", "xml/books.xsd", "book");
	}


	@Override
	protected Book parseElement(Element item) {
		String name, genre;
		int price;
		name = item.getAttribute("name");
		genre = item.getElementsByTagName("genre").item(0).getTextContent();
		price = Integer.parseInt(item.getElementsByTagName("price").item(0).getTextContent());
		return new Book(name, genre, price);
	}
	
}
