import java.time.LocalDate;
import java.time.YearMonth;


public class Cashier {

    private final IMerchantProcessor merchantProcessor;

    public Cashier(IMerchantProcessor merchantProcessor){
        this.merchantProcessor = merchantProcessor;
    }
    
    public String checkout(Cart cart, Card card) {

        asserCartIsNotEmpty(cart.isEmpty(), "Cart is empty");
        assertCardIsNotExpired(card);

 return        merchantProcessor.processPayment(cart.getTotal(), card);

    }

    private void assertCardIsNotExpired(Card card) {
        if(card.isExpired(YearMonth.now()))
        {
            throw new RuntimeException("Card is expired");
        }
    }

    private void asserCartIsNotEmpty(boolean cartWithCatalogWithProducts, String Cart_is_empty) {
        if (cartWithCatalogWithProducts) {
            throw new RuntimeException(Cart_is_empty);
        }
    }
}
