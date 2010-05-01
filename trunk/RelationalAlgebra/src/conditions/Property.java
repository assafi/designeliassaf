/**
 * Date: Apr 23, 2010
 * RelationalAlgebra - Property.java
 */
package conditions;

/**
 * Represents property which is the tuple (name,type).
 * @author Assaf Israel & Eli Nazarov
 *
 */
public class Property{

	private final String name;
	private final Class<?> type;

	public Property(String name, Class<?> type) {
		this.name = name;
		this.type = type;
	}

	public String getName() {
		return name;
	}
	
	public Class<?> getType() {
		return type;
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		
		if (!(o instanceof Property)) {
			return false;
		}
		
		Property p = (Property) o;
		if (name.equals(p.name) && type.equals(p.type)) {
			return true;
		}
		return false;
	}
	
	@Override
	public int hashCode(){
		return name.hashCode() + type.hashCode();
	}
}
