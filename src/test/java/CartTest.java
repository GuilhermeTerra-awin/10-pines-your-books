import java.time.LocalDate;
import java.time.MonthDay;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CartTest{

    @Test
    public void cartsAreCreatedEmpty (){
        assertTrue(TestDataFactory.createCartWithCatalogWithProducts().isEmpty());
    }

    @Test
    public void cartIsNotEmptyAfterAddingProducts (){
        Cart cart = TestDataFactory.createCartWithCatalogWithProducts();

        cart.add(TestDataFactory.productSellBySupermarket());

        assertFalse(cart.isEmpty());
    }

    @Test
    public void cartContainsAddedProduct(){
        Cart cart = TestDataFactory.createCartWithCatalogWithProducts();

        cart.add(TestDataFactory.productSellBySupermarket());

        assertTrue(cart.contains(TestDataFactory.productSellBySupermarket()));
    }

    @Test
    public void cartCanContainManyProducts(){
        Cart cart =TestDataFactory. createCartWithCatalogWithProducts();

        cart.add(TestDataFactory.productSellBySupermarket());
        cart.add(TestDataFactory.otherProductSellBySupermarket());

        assertTrue(cart.contains(TestDataFactory.productSellBySupermarket()));
        assertTrue(cart.contains(TestDataFactory.otherProductSellBySupermarket()));
    }

    @Test
    public void testCanNotAddProductsNotSellByTheSupermarket() {
        Cart cart = TestDataFactory.createCartWithCatalogWithProducts();

        try {
            cart.add(TestDataFactory.productNotSellBySupermarket());
            fail(); //assertTrue(false)
        } catch (RuntimeException e) {
            assertEquals(Cart.PRODUCT_IS_NOT_SELL_BY_SUPERMARKET,e.getMessage());
            assertTrue(cart.isEmpty());
        }
    }

    @Test
    public void testCanNotAddLessThanOneProduct() {
        Cart cart = TestDataFactory.createCartWithCatalogWithProducts();

        try {
            cart.add(TestDataFactory.productSellBySupermarket(),0);
            fail();
        } catch (RuntimeException e) {
            assertEquals(Cart.PRODUCT_QUANTITY_MUST_BE_STRICTLY_POSITIVE,e.getMessage());
            assertTrue(cart.isEmpty());
        }
    }

    @Test
    public void testCanAddManyProductsAtTheSameTime() {
        Cart cart =TestDataFactory. createCartWithCatalogWithProducts();

        cart.add(TestDataFactory.productSellBySupermarket(),2);

        assertEquals(2,cart.numberOf(TestDataFactory.productSellBySupermarket()));
    }

    @Test
    public void testCanAddSameProductMoreThanOnce() {
        Cart cart = TestDataFactory.createCartWithCatalogWithProducts();

        cart.add(TestDataFactory.productSellBySupermarket(),2);
        cart.add(TestDataFactory.productSellBySupermarket(),3);

        assertEquals(5,cart.numberOf(TestDataFactory.productSellBySupermarket()));
    }

    @Test
    public void testCanAddManyDifferentProductsAtTheSameTime() {
        Cart cart = TestDataFactory.createCartWithCatalogWithProducts();

        cart.add(TestDataFactory.productSellBySupermarket(),2);
        cart.add(TestDataFactory.otherProductSellBySupermarket(),3);

        assertEquals(2,cart.numberOf(TestDataFactory.productSellBySupermarket()));
        assertEquals(3,cart.numberOf(TestDataFactory.otherProductSellBySupermarket()));
    }



}
