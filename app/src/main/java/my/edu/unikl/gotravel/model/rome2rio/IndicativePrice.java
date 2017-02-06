package my.edu.unikl.gotravel.model.rome2rio;

import java.io.Serializable;

public class IndicativePrice implements Serializable {

    private double price;
    private String currency;
    private int isFreeTransfer;
    private double nativePrice;
    private String nativeCurrency;

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public int isFreeTransfer() {
        return isFreeTransfer;
    }

    public void setIsFreeTransfer(int isFreeTransfer) {
        this.isFreeTransfer = isFreeTransfer;
    }

    public double getNativePrice() {
        return nativePrice;
    }

    public void setNativePrice(double nativePrice) {
        this.nativePrice = nativePrice;
    }

    public String getNativeCurrency() {
        return nativeCurrency;
    }

    public void setNativeCurrency(String nativeCurrency) {
        this.nativeCurrency = nativeCurrency;
    }

    @Override
    public String toString() {
        return "IndicativePrice{" +
                "price=" + price +
                ", currency='" + currency + '\'' +
                ", isFreeTransfer=" + isFreeTransfer +
                ", nativePrice=" + nativePrice +
                ", nativeCurrency='" + nativeCurrency + '\'' +
                '}';
    }
}
