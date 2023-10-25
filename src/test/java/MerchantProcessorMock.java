import java.util.HashMap;
import java.util.Map;

public class MerchantProcessorMock implements IMerchantProcessor{

    private Map<String, Boolean> stolenCards = new HashMap<>();
    private Map<String, Double> accountBalances = new HashMap<>();

    public MerchantProcessorMock(){
        stolenCards.put("1111111111111111", true);

        accountBalances.put("1234567890123456", 100.0);
        accountBalances.put("22222222222222222", 100.0);
        accountBalances.put("1111111111111111", 100.0);
    }

    @Override
    public String processPayment(Double amount, Card card) {
        if(stolenCards.keySet().contains(card.number)){
            throw new RuntimeException("Card is stolen");
        }

        if(accountBalances.get(card.number) < amount){
            throw new RuntimeException("Card balance exceeded");
        }

        return "transactionId";
    }


}
