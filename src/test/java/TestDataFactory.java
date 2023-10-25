import java.time.MonthDay;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestDataFactory {
    public static Cart createCartWithCatalogWithProducts() {
        return new Cart(catalogWithProducts());
    }

    public static Map<Object, Double> catalogWithProducts() {
        Map<Object, Double> catalog = new HashMap<>();

        catalog.put(productSellBySupermarket(), 30.0);
        catalog.put(otherProductSellBySupermarket(), 60.0);

        return catalog;
    }

    public static Object productNotSellBySupermarket() {
        return "productNotSellBySupermarket";
    }

    public static Object otherProductSellBySupermarket() {
        return "otherProductSellBySupermarket";
    }

    public static Object productSellBySupermarket() {
        return "productSellBySupermarket";
    }

    public static Card createValidCard() {
        return new Card("22222222222222222", "name", YearMonth.of(2050, 10));
    }

    public static Card createExpiredCard() {
        return new Card("1234567890123456", "name", YearMonth.of(2022, 10));
    }

    public static Card createStolenCard() {
        return new Card("1111111111111111", "test", YearMonth.of(2024, 11));
    }
}
