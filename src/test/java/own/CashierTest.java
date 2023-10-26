package own;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CashierTest {


    Cashier cashier;
    IMerchantProcessor procesor;

    @BeforeEach
    public void init() {
        // create stub for own.IMerchantProcessor
        procesor = new IMerchantProcessor() {
            @Override
            public String processPayment(Double amount, Card card) {
                if (card.number.equals("1111111111111111")) {
                    throw new RuntimeException("own.Card is stolen");
                }
                if (card.number.equals("22222222222222222")) {
                    throw new RuntimeException("own.Card balance exceeded");
                }
                if (card.number.equals("3333333333333333")) {
                    throw new RuntimeException("own.Card balance exceeded");
                }
                return "transactionId";

            }
        };

        // create own.Cashier instance with stub
        cashier = new Cashier(procesor);

        // mock own.IMerchantProcessor with Mockito
        IMerchantProcessor procesorMock = Mockito.mock(IMerchantProcessor.class);
        Mockito.when(procesorMock.processPayment(Mockito.anyDouble(), Mockito.any(Card.class))).thenReturn("transactionId");

        //mock processPayment with stolen card
        Mockito.when(procesorMock.processPayment(Mockito.anyDouble(), Mockito.any(Card.class))).thenThrow(new RuntimeException("own.Card is stolen"));


    }

    @Test
    public void testCashierMustThrowExceptionWhenCreditCardIsExpired() {
        var cart = TestDataFactory.createCartWithCatalogWithProducts();
        cart.add(TestDataFactory.productSellBySupermarket());

        var e = assertThrows(RuntimeException.class, () -> cashier.checkout(cart, TestDataFactory.createExpiredCard()));
        assertEquals("own.Card is expired", e.getMessage());

    }

    @Test
    public void testCashierMustThrowExceptionWhenCreditCardIsStolen() {
        var cart = TestDataFactory.createCartWithCatalogWithProducts();
        cart.add(TestDataFactory.productSellBySupermarket());

        var e = assertThrows(RuntimeException.class, () -> cashier.checkout(cart, TestDataFactory.createStolenCard()));
        assertEquals("own.Card is stolen", e.getMessage());
    }

    @Test
    public void testCashierMustThrowExceptionWhenCreditCardBalanceIsExceeded() {

        var cart = TestDataFactory.createCartWithCatalogWithProducts();
        cart.add(TestDataFactory.otherProductSellBySupermarket());
        cart.add(TestDataFactory.otherProductSellBySupermarket());

        var e = assertThrows(RuntimeException.class, () -> cashier.checkout(cart, TestDataFactory.createUnbalancedCard()));
        assertEquals("own.Card balance exceeded", e.getMessage());
    }
}
