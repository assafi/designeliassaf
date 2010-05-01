/**
 * Date: Apr 30, 2010
 * RelationalAlgebra - BasicRelationTest.java
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
public class RelationTest {

	private static IRelation bRel1 = null;
	private static IRelation bRel2 = null;
	private static IRelation bRel3 = null;
	private static IRelation bRel4 = null;
	private static Property propName = new Property("name", String.class);
	private static Property propHeight = new Property("height", Integer.class);
	private static Property propFatherName = new Property("fatherName", String.class);
	private static Property propHieght2 = new Property("height", Boolean.class);
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		List<Property> properties = new ArrayList<Property>();
		properties.add(propName);
		properties.add(propHeight);
		bRel1 = new BasicRelation("Rel1", properties);
		bRel2 = new BasicRelation("Rel2", properties);
		
		Map<Property, Object> entry1 = new HashMap<Property, Object>();
		entry1.put(propName, "Roni");
		entry1.put(propHeight, 178);
		bRel1.add(entry1);
		bRel2.add(entry1);
		
		Map<Property, Object> entry2 = new HashMap<Property, Object>();
		entry2.put(propName, "Shaul");
		entry2.put(propHeight, 185);
		bRel1.add(entry2);
		bRel2.add(entry2);
		
		Map<Property, Object> entry3 = new HashMap<Property, Object>();
		entry3.put(propName, "Yoni");
		entry3.put(propHeight, 167);
		bRel1.add(entry3);
		bRel2.add(entry3);
		
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
		
		List<Property> properties3 = new ArrayList<Property>();
		properties3.add(propName);
		properties3.add(propFatherName);
		properties3.add(propHieght2);
		bRel4 = new BasicRelation("Rel4", properties3);
		
		Map<Property, Object> entry6 = new HashMap<Property, Object>();
		entry6.put(propName, "Roni");
		entry6.put(propFatherName, "David");
		entry6.put(propHieght2, true);
		bRel4.add(entry6);
		
		Map<Property, Object> entry7 = new HashMap<Property, Object>();
		entry7.put(propName, "Milush");
		entry7.put(propFatherName, "Gregor");
		entry7.put(propHieght2, true);
		bRel4.add(entry7);
		
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
	public void testBasicRelation() throws IOException {
		System.out.println("testBasicRelation");
		
		bRel1.display();
		
		BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
	
		stdin.read();

	}
	
	@Test
	public void testProjectionRelation() throws IOException {
		System.out.println("testProjectionRelation");
		Set<String> labels = new HashSet<String>();
		labels.add("name");
		IRelation projRel = new ProjectionRelation("ProhRel", bRel1, labels);
		
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
		
		cartRel.display();
		
		BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
		
		stdin.read();
	}
	
	@Test
	public void testCartesianRelation2() throws IOException {
		System.out.println("testCartesianRelation2");
		IRelation cartRel = new CartesianRelation("CartRelSame", bRel1, bRel1);
		
		cartRel.display();
		
		BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
		
		stdin.read();
	}
	
	@Test
	public void testSelectionRelation() throws IOException {
		System.out.println("testSelectionRelation");
		ICondition cond = new GreaterThenCondition(propHeight, 170);
		IRelation selectRel = new SelectionRelation("SelectRel", bRel1, cond);
		
		selectRel.display();
		
		BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
		
		stdin.read();
	}
	
	@Test
	public void testJoinRelation() throws IOException {
		System.out.println("testJoinRelation");

		IRelation joinRel = new JoinRelation("JoinRel", bRel1, bRel3);
		
		joinRel.display();
		
		BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
		
		stdin.read();
	}
	
	
	@Test
	public void testJoinRelation2() throws IOException {
		System.out.println("testJoinRelation2");

		IRelation selectRel = new JoinRelation("JoinRel", bRel1, bRel4);
		
		selectRel.display();
		
		BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
		
		stdin.read();
	}
	
	@Test
	public void testComplex() throws IOException {
		System.out.println("testComplex");
		
		IRelation join1 = new JoinRelation("J1", bRel1, bRel3);
		IRelation cart2 = new CartesianRelation("C2",join1, bRel2);
		
		Property propInerHeight = new Property("J1.height", Integer.class);
		ICondition cond = new GreaterThenCondition(propInerHeight, 170);
		IRelation select2 = new SelectionRelation("S2", cart2, cond);
		
		Set<String> labels = new HashSet<String>();
		labels.add("Rel2.height");
		labels.add("J1.height");
		labels.add("fatherName");
		
		IRelation proj2 = new ProjectionRelation("P2", select2, labels); 
		
		proj2.display();
		
		BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
		
		stdin.read();
	}
}
