package il.cs.technion.c236700.hw4;

public class Book {
	final String name;
	final String genre;
	final int price;

	public Book(String name, String genre, int price) {
		this.name = name;
		this.genre = genre;
		this.price = price;
	}

	@Override
	public String toString() {
		return String.format("%1$s, p: %2$d nis, genre: %3$s", name, price, genre);
	}
}// End Book