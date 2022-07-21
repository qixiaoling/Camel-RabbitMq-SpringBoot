package BasicQueue;

import java.io.Serializable;

public class Payment implements Serializable {
    private String cardNumber;
    private Double paymentAmount;

    private String name;

    public Payment(String cardNumber, Double paymentAmount, String name){
        this.cardNumber = cardNumber;
        this.paymentAmount = paymentAmount;
        this.name = name;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public Double getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(Double paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
