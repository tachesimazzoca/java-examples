package integration.step;

import com.github.tachesimazzoca.java.examples.jbehave.Cart;
import com.github.tachesimazzoca.java.examples.jbehave.CartItem;
import com.github.tachesimazzoca.java.examples.jbehave.Item;
import org.jbehave.core.annotations.AfterStories;
import org.jbehave.core.annotations.AfterStory;
import org.jbehave.core.annotations.BeforeStories;
import org.jbehave.core.annotations.BeforeStory;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import static org.junit.Assert.*;

public class CartSteps {
    private Cart cart;

    @BeforeStories
    public void beforeStories() {
        System.out.println("beforeStories");
    }

    @BeforeStory
    public void beforeStory() {
        System.out.println("beforeStory");
    }

    @Given("a cart")
    public void initialize() {
        cart = new Cart();
    }

    @When("I add an item (code:$code, price:$price, quantity:$quantity)")
    public void addItem(String code, int price, int quantity) {
        Item item = new Item(code, price);
        cart.addCartItem(new CartItem(item, quantity));
    }

    @When("I remove the item (code:$code)")
    public void removeItem(String code) {
        cart.removeCartItem(code);
    }

    @Then("the total amount is $total")
    public void calculateTotal(int total) {
        assertEquals(total, cart.calculateTotal());
    }

    @AfterStory
    public void afterStory() {
        System.out.println("afterStory");
    }

    @AfterStories
    public void afterStories() {
        System.out.println("afterStories");
    }
}
