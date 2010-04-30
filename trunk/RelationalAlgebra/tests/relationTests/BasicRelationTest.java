/**
 * Date: Apr 30, 2010
 * RelationalAlgebra - BasicRelationTest.java
 */
package relationTests;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import conditions.Property;

import relationalalgebra.IRelation;
import relationalalgebra.concrete.BasicRelation;

/**
 * @author Assaf Israel & Eli Nazarov
 *
 */
public class BasicRelationTest {

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}


	@Test
	public void testBasicRelation() {
		Property propName = new Property("name", String.class);
		Property propHieght = new Property("height", Integer.class);
		Set<Property> properties = new HashSet<Property>();
		properties.add(propName);
		properties.add(propHieght);
		IRelation bRel = new BasicRelation("Rel1", properties);
		
		Map<Property, Object> entry1 = new HashMap<Property, Object>();
		entry1.put(propName, "Roni");
		entry1.put(propHieght, 178);
		bRel.add(entry1);
		
		Map<Property, Object> entry2 = new HashMap<Property, Object>();
		entry2.put(propName, "Shaul");
		entry2.put(propHieght, 185);
		bRel.add(entry2);
		
		Map<Property, Object> entry3 = new HashMap<Property, Object>();
		entry3.put(propName, "Yoni");
		entry3.put(propHieght, 167);
		bRel.add(entry3);
		
		bRel.display();
	}

}
