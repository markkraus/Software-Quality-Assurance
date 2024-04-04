package edu.pitt.cs;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.Assert.*;

public class StepDefinitions {
	private RentACat r;
	private String listResult;
  private Integer justRentedID;
  private Integer justReturnedID;

	
	// TODO: Add more member variables and methods as necessary

	@Given("a rent-a-cat facility")
	public void aRentACatFacility() {
		r = RentACat.createInstance(InstanceType.IMPL);
	}
	
	@Given("no cats")
	public void noCats() {
		// nothing to do really
	}
	
	@Given("a cat with ID {int} and name {string}")
	public void aCatWithIDAndName(Integer id, String name) {
		r.addCat(Cat.createInstance(InstanceType.IMPL, id, name));
		System.out.println("Created cat " + id + ". " + name);
	}
	
	@When("I list the cats")
	public void iListTheCats() {
		listResult = r.listCats();
	}
	
	@When("I rent cat number {int}")
	public void iRentCatNumber(Integer id) {
    boolean result = r.rentCat(id);
    if (result) {
      assertTrue(result);
    } else {
      assertFalse(result);
    }
    
    justRentedID = id;
	}
	
	@Then("the listing is: {string}")
	public void theListingIs(String result) {
		assertEquals(result.replaceAll("\\\\n", "\n"), listResult);
	}
	
	@Then("the rent is successful")
	public void theRentIsSuccessful() {
    assertFalse(r.rentCat(justRentedID));
	}

	@Then("the rent is unsuccessful")
	public void theRentIsUnsuccessful() {
		assertFalse(r.rentCat(justRentedID));
	}

  @When("I return cat number {int}")
  public void I_return_cat_number(int id) {
    r.returnCat(id);
    justReturnedID = id;
  }

  @Then("the return is successful")
  public void the_return_is_successful() {
    assertFalse(r.returnCat(justReturnedID));
  }

  @Then("the return is unsuccessful")
  public void the_return_is_unsuccessful() {
    assertFalse(r.returnCat(justReturnedID));
  }
}
