package il.cs.technion.c236700.hw4;

public class BestSeller {
	final String name;
	final int copies;
	
	public BestSeller(String name, int copies) {
		this.name = name;
		this.copies = copies;
	}
	
	@Override
	public String toString() {
		return String.format("%1$s best-seller sold in %2$d copies", name, copies);
	}
}
