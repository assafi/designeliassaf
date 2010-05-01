package relationTests;


import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import conditions.*;

public class ConditionTests {

	private static Property name1 = null; 
	private static Property name2 = null; 
	private static Property int1 = null; 
	private static Property bool1 = null;
	
	private static Map<Property, Object> values = new HashMap<Property, Object>();
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		name1 = new Property("Name1", String.class);
		name2 = new Property("Name2", String.class);
		int1 = new Property("int1", Integer.class);
		bool1 = new Property("bool1", Boolean.class);
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void likeConditionTest() {
		
		ICondition likeCondition = new LikeCondition(name1, name2);
		
		values.put(name1, "Assaf");
		values.put(name2, "Assaf");
		assertTrue(likeCondition.check(values));
		
		values.put(name2, "assaf");
		assertFalse(likeCondition.check(values));
		
		values.put(name1, "eli");
		assertFalse(likeCondition.check(values));
		
		ICondition notLikeCondition = new NotCondition(likeCondition);
		assertTrue(notLikeCondition.check(values));
	}
	
	@Test
	public void complexConditionTest() {
		
		ICondition gteCondition = new GreaterThenCondition(int1, 3);
		values.put(int1, 4);
		assertTrue(gteCondition.check(values));
		
		values.put(int1, 3);
		assertFalse(gteCondition.check(values));
		
		ICondition notGteCondition = new NotCondition(gteCondition);
		assertTrue(notGteCondition.check(values));
		
		ICondition boolCondition = new BooleanCondition(bool1);
		values.put(bool1, true);
		assertTrue(boolCondition.check(values));
		
		ICondition andCondition = new AndCondition(notGteCondition, boolCondition);
		assertTrue(andCondition.check(values));
		
		values.put(int1, 10);
		assertFalse(andCondition.check(values));
		
		values.put(int1, 0);
		values.put(bool1, false);
		assertFalse(andCondition.check(values));
	}
}
