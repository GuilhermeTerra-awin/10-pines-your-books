package hernan;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class Cart {

    public static final String PRODUCT_IS_NOT_SELL_BY_SUPERMARKET = "Product is not sell by supermarket";
    public static final String PRODUCT_QUANTITY_MUST_BE_STRICTLY_POSITIVE = "Product quantity must be strictly positive";

    private final Map<Object, Double> catalog;
    private final List<Object> products = new ArrayList<Object>();

    public Cart(Map<Object, Double> catalog) {
        this.catalog = catalog;
    }

    public void add(Object aProduct) {
        add(aProduct, 1);
    }

    public boolean isEmpty() {
        return products.isEmpty();
    }

    public boolean contains(Object aProduct) {
        return products.contains(aProduct);
    }

    public void add(Object aProduct, int aQuantity) {
        assertQuantityIsStrictlyPositive(aQuantity);
        assertProductIsSellBySupermarket(aProduct);

        for (int i = 0; i < aQuantity; i++)
            products.add(aProduct);
    }

    public long numberOf(Object aProduct) {
        return products.stream().filter(addedProduct -> addedProduct.equals(aProduct)).count();

//		Para Java pre 1.8
//		long count = 0;
//		for (Object product : products) 
//			if(aProduct.equals(product))
//				count ++;
//
//		return count;
    }

    public void assertProductIsSellBySupermarket(Object aProduct) {
        if (!catalog.containsKey(aProduct)) throw new RuntimeException(PRODUCT_IS_NOT_SELL_BY_SUPERMARKET);
    }

    public void assertQuantityIsStrictlyPositive(int aQuantity) {
        if (aQuantity < 1) throw new RuntimeException(PRODUCT_QUANTITY_MUST_BE_STRICTLY_POSITIVE);
    }

    public double total() {
        return products.stream().
                mapToDouble(addedProduct -> priceOf(addedProduct)).
                reduce(0.0, (total, price) -> total + price);

//		Para Java pre 1.8
//		double total = 0;
//		for (Object product : products) 
//			total += priceOf(product);
//		
//		return total;
    }

    private Double priceOf(Object aProduct) {
        return catalog.get(aProduct);
    }


}
