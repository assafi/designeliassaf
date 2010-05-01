/**
 * Date: May 1, 2010
 * RelationalAlgebra - EmptyRelTest.java
 */
package relationTests;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import relationalalgebra.IRelation;
import relationalalgebra.concrete.BasicRelation;
import relationalalgebra.concrete.CartesianRelation;
import relationalalgebra.concrete.JoinRelation;
import relationalalgebra.concrete.ProjectionRelation;
import relationalalgebra.concrete.SelectionRelation;
import conditions.GreaterThenCondition;
import conditions.ICondition;
import conditions.Property;

/**
 * @author Assaf Israel & Eli Nazarov
 *
 */
public class EmptyRelTest {

	private static IRelation bRel1 = null;
	private static IRelation bRel2 = null;
	private static IRelation bRel3 = null;
	private static Property propName = new Property("name", String.class);
	private static Property propHeight = new Property("height", Integer.class);
	private static Property propFatherName = new Property("fatherName", String.class);
	
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		List<Property> properties = new ArrayList<Property>();
		properties.add(propName);
		properties.add(propHeight);
		bRel1 = new BasicRelation("Rel1", properties);
		bRel2 = new BasicRelation("Rel1", properties);
		
		Map<Property, Object> entry1 = new HashMap<Property, Object>();
		entry1.put(propName, "Roni");
		entry1.put(propHeight, 178);
		bRel1.add(entry1);
		
		Map<Property, Object> entry2 = new HashMap<Property, Object>();
		entry2.put(propName, "Shaul");
		entry2.put(propHeight, 185);
		bRel1.add(entry2);
		
		Map<Property, Object> entry3 = new HashMap<Property, Object>();
		entry3.put(propName, "Yoni");
		entry3.put(propHeight, 167);
		bRel1.add(entry3);
		
		/*
		 * Join relation setup
		 */
		List<Property> properties2 = new ArrayList<Property>();
		properties2.add(propName);
		properties2.add(propFatherName);
		bRel3 = new BasicRelation("Rel3", properties2);
		
		Map<Property, Object> entry4 = new HashMap<Property, Object>();
		entry4.put(propName, "Roni");
		entry4.put(propFatherName, "David");
		bRel3.add(entry4);
		
		Map<Property, Object> entry5 = new HashMap<Property, Object>();
		entry5.put(propName, "Milush");
		entry5.put(propFatherName, "Gregor");
		bRel3.add(entry5);
		
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
	public void testProjectionRelation() throws IOException {
		System.out.println("testProjectionRelation");
		Set<String> labels = new HashSet<String>();
		labels.add("name");
		IRelation projRel = new ProjectionRelation("ProhRel", bRel2, labels);
		
		Map<Property, Object> entry1 = new HashMap<Property, Object>();
		entry1.put(propName, "NEW");
		projRel.add(entry1);
		
		projRel.display();
		
		BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
		
		stdin.read();
	}
	
	@Test
	public void testCartesianRelation1() throws IOException {
		System.out.println("testCartesianRelation1");
		IRelation cartRel = new CartesianRelation("CartRel", bRel1, bRel2);
		
		/*
		 * should be empty
		 */
		cartRel.display();
		
		BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
		
		stdin.read();
	}
	
	@Test
	public void testCartesianRelation2() throws IOException {
		System.out.println("testCartesianRelation2");
		IRelation cartRel = new CartesianRelation("CartRelSame", bRel2, bRel2);
		
		cartRel.display();
		
		BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
		
		stdin.read();
	}
	
	@Test
	public void testSelectionRelation() throws IOException {
		System.out.println("testSelectionRelation");
		ICondition cond = new GreaterThenCondition(propHeight, 170);
		IRelation selectRel = new SelectionRelation("SelectRel", bRel2, cond);
		
		selectRel.display();
		
		BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
		
		stdin.read();
	}
	
	@Test
	public void testJoinRelation() throws IOException {
		System.out.println("testJoinRelation");

		IRelation joinRel = new JoinRelation("JoinRel", bRel2, bRel3);
		
		joinRel.display();
		
		BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
		
		stdin.read();
	}

}
