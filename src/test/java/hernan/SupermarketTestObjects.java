package hernan;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.fail;


public class SupermarketTestObjects {

    private LocalDate today;

    public Object productSellBySupermarket() {
        return "productSellBySupermarket";
    }

    public Object otherProductSellBySupermarket() {
        return "otherProductSellBySupermarket";
    }

    public Object productNotSellBySupermarket() {
        return "productNotSellBySupermarket";
    }

    public Map<Object, Double> catalogWithProducts() {
        Map<Object, Double> catalog = new HashMap<>();

        catalog.put(productSellBySupermarket(), productSellBySupermarketPrice());
        catalog.put(otherProductSellBySupermarket(), otherProductSellBySupermarketPrice());

        return catalog;
    }

    public Cart createCartWithCatalogWithProducts() {
        return new Cart(catalogWithProducts());
    }

    public double productSellBySupermarketPrice() {
        return 10.00;
    }

    public double otherProductSellBySupermarketPrice() {
        return 15.35;
    }

    public CreditCard expiredCreditCard() {
        return new CreditCard(YearMonth.from(today()).minusMonths(1));
    }

    public CreditCard notExpiredCreditCard() {
        return new CreditCard(YearMonth.from(today()).plusMonths(1));
    }

    public LocalDate today() {
        if (today == null) today = LocalDate.now();
        return today;
    }

    public ArrayList<Object> emptySalesBook() {
        return new ArrayList<Object>();
    }

    public MerchantProcessor alwaysSuccessMerchantProcessor() {
        return (creditCard, amount) -> {
        };

//		Para Java pre 1.8
//		return new MerchantProcessor() {
//			@Override
//			public void debit(CreditCard creditCard, double amount) {
//				
//			}
//		};
    }

    public MerchantProcessor failMerchantProcessor() {
        return (creditCard, amount) -> fail();

//		Para Java pre 1.8
//		return new MerchantProcessor() {
//			@Override
//			public void debit(CreditCard creditCard, double amount) {
//				fail();
//			}
//		};
    }
}
