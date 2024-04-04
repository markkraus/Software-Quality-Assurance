package edu.pitt.cs;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Method;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import org.mockito.*;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RentACatUnitTest {

  /**
   * The test fixture for this JUnit test. Test fixture: a fixed state of a set of
   * objects used as a baseline for running tests. The test fixture is initialized
   * using the @Before setUp method which runs before every test case. The test
   * fixture is removed using the @After tearDown method which runs after each
   * test case.
   */

  RentACat r; // Object to test
  Cat c1; // First cat object
  Cat c2; // Second cat object
  Cat c3; // Third cat object

  ByteArrayOutputStream out; // Output stream for testing system output
  PrintStream stdout; // Print stream to hold the original stdout stream
  String newline = System.lineSeparator(); // Platform independent newline ("\n" or "\r\n") for use in assertEquals

  @Before
  public void setUp() throws Exception {
    // INITIALIZE THE TEST FIXTURE

    // 1. Create a new RentACat object and assign to r using a call to
    // RentACat.createInstance(InstanceType).
    // Passing InstanceType.IMPL as the first parameter will create a real RentACat
    // object using your RentACatImpl implementation.
    // Passing InstanceType.MOCK as the first parameter will create a mock RentACat
    // object using Mockito.
    // Which type is the correct choice for this unit test? I'll leave it up to you.
    // The answer is in the Unit Testing Part 2 lecture. :)
    r = RentACat.createInstance(InstanceType.IMPL);

    // 2. Create a Cat with ID 1 and name "Jennyanydots", assign to c1 using a call
    // to Cat.createInstance(InstanceType, int, String).
    // Passing InstanceType.IMPL as the first parameter will create a real cat using
    // your CatImpl implementation.
    // Passing InstanceType.MOCK as the first parameter will create a mock cat using
    // Mockito.
    // Which type is the correct choice for this unit test? Again, I'll leave it up
    // to you.
    c1 = Cat.createInstance(InstanceType.MOCK, 1, "Jennyanydots");
    

    // 3. Create a Cat with ID 2 and name "Old Deuteronomy", assign to c2 using a
    // call to Cat.createInstance(InstanceType, int, String).
    c2 = Cat.createInstance(InstanceType.MOCK, 2, "Old Deuteronomy");

    // 4. Create a Cat with ID 3 and name "Mistoffelees", assign to c3 using a call
    // to Cat.createInstance(InstanceType, int, String).
    c3 = Cat.createInstance(InstanceType.MOCK, 3, "Mistoffelees");

    // 5. Redirect system output from stdout to the "out" stream
    // First, make a back up of System.out (which is the stdout to the console)
    stdout = System.out;
    // Second, update System.out to the PrintStream created from "out"
    out = new ByteArrayOutputStream();
    System.setOut(new PrintStream(out));
  }

  @After
  public void tearDown() throws Exception {
    // Restore System.out to the original stdout
    System.setOut(stdout);

    // Not necessary strictly speaking since the references will be overwritten in
    // the next setUp call anyway and Java has automatic garbage collection.
    r = null;
    c1 = null;
    c2 = null;
    c3 = null;
  }

  /**
   * Test case for Cat getCat(int id).
   * 
   * <pre>
   * Preconditions: r has no cats.
   * Execution steps: Call getCat(2).
   * Postconditions: Return value is null.
   *                 System output is "Invalid cat ID." + newline.
   * </pre>
   * 
   * Hint: You will need to use Java reflection to invoke the private getCat(int)
   * method. efer to the Unit Testing Part 1 lecture and the textbook appendix
   * hapter on using reflection on how to do this. Please use r.getClass() to get
   * the class object of r instead of hardcoding it as RentACatImpl.
   */
  @Test
  public void testGetCatNullNumCats0() throws Exception {

    Class<?> rentACatClass = r.getClass();
    // Get the private method getCat(int) from the class

    Method getCatMethod = rentACatClass.getDeclaredMethod("getCat", int.class);

    // Make the method accessible, since it's private
    getCatMethod.setAccessible(true);

    // Invoke the private method with an argument (e.g., 2)
    Object result = getCatMethod.invoke(r, 2);
    assertNull(result);
    assertEquals("Invalid cat ID.\n", out.toString());
  }

  /**
   * Test case for Cat getCat(int id).
   * 
   * <pre>
   * Preconditions: c1, c2, and c3 are added to r using addCat(Cat c).
   * Execution steps: Call getCat(2).
   * Postconditions: Return value is not null.
   *                 Returned cat has an ID of 2.
   * </pre>
   * 
   * Hint: You will need to use Java reflection to invoke the private getCat(int)
   * method. efer to the Unit Testing Part 1 lecture and the textbook appendix
   * hapter on using reflection on how to do this. Please use r.getClass() to get
   * the class object of r instead of hardcoding it as RentACatImpl.
   */
  @Test
  public void testGetCatNumCats3() throws Exception {
    Class<?> rentACatClass = r.getClass();
    r.addCat(c1);
    r.addCat(c2);
    r.addCat(c3);
    when(c2.getId()).thenReturn(2);
    
    // Get the private method getCat(int) from the class

    Method getCatMethod = rentACatClass.getDeclaredMethod("getCat", int.class);

    // Make the method accessible, since it's private
    getCatMethod.setAccessible(true);

    // Invoke the private method with an argument (e.g., 2)
    Object result = getCatMethod.invoke(r, 2);
    assertNotNull("Expected not null for invalid cat ID.", result);
    assertEquals(2, c2.getId());
  }

  /**
   * Test case for String listCats().
   * 
   * <pre>
   * Preconditions: r has no cats.
   * Execution steps: Call listCats().
   * Postconditions: Return value is "".
   * </pre>
   */
  @Test
  public void testListCatsNumCats0() {
    String result;
    result = r.listCats();
    assertEquals("", result);
  }

  /**
   * Test case for String listCats().
   * 
   * <pre>
   * Preconditions: c1, c2, and c3 are added to r using addCat(Cat c).
   * Execution steps: Call listCats().
   * Postconditions: Return value is "ID 1. Jennyanydots\nID 2. Old
   *                 Deuteronomy\nID 3. Mistoffelees\n".
   * </pre>
   */
  @Test
  public void testListCatsNumCats3() {
    r.addCat(c1);
    r.addCat(c2);
    r.addCat(c3);
    when(c1.getId()).thenReturn(1);
    when(c2.getId()).thenReturn(2);
    when(c3.getId()).thenReturn(3);
    when(c1.getName()).thenReturn("Jennyanydots");
    when(c2.getName()).thenReturn("Old Deuteronomy");
    when(c3.getName()).thenReturn("Mistoffelees");

    String result;
    result = r.listCats();
    assertEquals("ID 1. Jennyanydots\nID 2. Old Deuteronomy\nID 3. Mistoffelees\n", result);
  }

  /**
   * Test case for boolean renameCat(int id, String name).
   * 
   * <pre>
   * Preconditions: r has no cats.
   * Execution steps: Call renameCat(2, "Garfield").
   * Postconditions: Return value is false.
   *                 c2 is not renamed to "Garfield".
   *                 System output is "Invalid cat ID." + newline.
   * </pre>
   * 
   * Hint: You may need to use behavior verification for this one. See
   * sample_code/junit_example/LinkedListUnitTest.java in the course repository to
   * see examples.
   */
  @Test
  public void testRenameFailureNumCats0() {
    assertFalse(r.renameCat(2, "Garfield"));
    assertNotEquals("Garfield", c2.getName());
    assertEquals("Invalid cat ID.\n", out.toString());
  }

  /**
   * Test case for boolean renameCat(int id, String name).
   * 
   * <pre>
   * Preconditions: c1, c2, and c3 are added to r using addCat(Cat c).
   * Execution steps: Call renameCat(2, "Garfield").
   * Postconditions: Return value is true.
   *                 c2 is renamed to "Garfield".
   * </pre>
   * 
   * Hint: You may need to use behavior verification for this one. See
   * sample_code/junit_example/LinkedListUnitTest.java in the course repository to
   * see examples.
   */
  @Test
  public void testRenameNumCat3() {
    r.addCat(c1);
    r.addCat(c2);
    r.addCat(c3);
    

    // Call renameCat(2, "Garfield")
    assertTrue(r.renameCat(2, "Garfield"));

    // Verify that renameCat was called with the expected arguments
    verify(c2).renameCat("Garfield");
  }

  /**
   * Test case for boolean rentCat(int id).
   * 
   * <pre>
   * Preconditions: c1, c2, and c3 are added to r using addCat(Cat c).
   * Execution steps: Call rentCat(2).
   * Postconditions: Return value is true.
   *                 c2 is rented as a result of the execution steps.
   *                 System output is "Old Deuteronomy has been rented." + newline
   * </pre>
   * 
   * Hint: You may need to use behavior verification for this one. See
   * sample_code/junit_example/LinkedListUnitTest.java in the course repository to
   * see examples.
   */
  @Test
  public void testRentCatNumCats3() {
    r.addCat(c1);
    r.addCat(c2);
    r.addCat(c3);

    doAnswer(invocation -> {
      ((Cat) invocation.getMock()).returnCat();
      return true;
    }).when(c2).rentCat();

    // Call rentCat(2)
    assertTrue(r.rentCat(2));

    // Verify that rentCat was called on the c2 mock
    verify(c2).rentCat();

    doAnswer(invocation -> {
      ((Cat) invocation.getMock()).returnCat();
      return true;
    }).when(c2).getRented();

    // Verify the rented state of c2
    assertTrue(c2.getRented());

    // Verify the system output
    assertEquals("Old Deuteronomy has been rented.\n", out.toString());
  }

  /**
   * Test case for boolean rentCat(int id).
   * 
   * <pre>
   * Preconditions: c1, c2, and c3 are added to r using addCat(Cat c).
   *                c2 is rented.
   * Execution steps: Call rentCat(2).
   * Postconditions: Return value is false.
   *                 c2 is not rented as a result of the execution steps.
   *                 System output is "Sorry, Old Deuteronomy is not here!" + newline
   * </pre>
   * 
   * Hint: You may need to use behavior verification for this one. See
   * sample_code/junit_example/LinkedListUnitTest.java in the course repository to
   * see examples.
   */
  @Test
  public void testRentCatFailureNumCats3() {
    r.addCat(c1);
    r.addCat(c2);
    r.addCat(c3);

    // Setting the state of getRented() to true for c2
    when(c2.getRented()).thenReturn(true);

    // Mocking rentCat() to set the rented flag to true and return false
    doAnswer(invocation -> {
      ((Cat) invocation.getMock()).rentCat();
      return false;
    }).when(c2).rentCat();

    assertFalse(r.rentCat(2));
    assertTrue(c2.getRented());
    assertEquals("Sorry, Old Deuteronomy is not here!\n", out.toString());
  }

  /**
   * Test case for boolean returnCat(int id).
   * 
   * <pre>
   * Preconditions: c1, c2, and c3 are added to r using addCat(Cat c).
   *                c2 is rented.
   * Execution steps: Call returnCat(2).
   * Postconditions: Return value is true.
   *                 c2 is returned as a result of the execution steps.
   *                 System output is "Welcome back, Old Deuteronomy!" + newline
   * </pre>
   * 
   * Hint: You may need to use behavior verification for this one. See
   * sample_code/junit_example/LinkedListUnitTest.java in the course repository to
   * see examples.
   */
  @Test
  public void testReturnCatNumCats3() {
    // Add cats, rent c2
    r.addCat(c1);
    r.addCat(c2);
    r.addCat(c3);
    when(c2.getRented()).thenReturn(true);

    c2.rentCat();
    verify(c2).rentCat();

    r.returnCat(2);

    // c2 is returned
    assertTrue(c2.getRented());
    assertEquals("Welcome back, Old Deuteronomy!\n", out.toString());
  }

  /**
   * Test case for boolean returnCat(int id).
   * 
   * <pre>
   * Preconditions: c1, c2, and c3 are added to r using addCat(Cat c).
   * Execution steps: Call returnCat(2).
   * Postconditions: Return value is false.
   *                 c2 is not returned as a result of the execution steps.
   *                 System output is "Old Deuteronomy is already here!" + newline
   * </pre>
   * 
   * Hint: You may need to use behavior verification for this one. See
   * sample_code/junit_example/LinkedListUnitTest.java in the course repository to
   * see examples.
   */
  @Test
  public void testReturnFailureCatNumCats3() {
    r.addCat(c1);
    r.addCat(c2);
    r.addCat(c3);
    assertFalse(r.returnCat(2));
    assertFalse(c2.getRented());
    assertEquals("Old Deuteronomy is already here!\n", out.toString());
  }

}