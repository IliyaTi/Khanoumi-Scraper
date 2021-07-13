package POJO;

public class PriceChangeResponse {

    private boolean success;
    private int colorPrice;
    private int weightPrice;
    private String code;
    private int baseprice;
    private int discount;
    private String expiration;
    private int buyNumberLimit;
    private int Color_PriceID;
    private int remainTime;


    public PriceChangeResponse (){}

    public PriceChangeResponse(boolean success, int colorPrice, int weightPrice, String code, int basePrice, int discount, String expiration, int buyNumberLimit, int color_PriceID, int remainTime) {
        this.success = success;
        this.colorPrice = colorPrice;
        this.weightPrice = weightPrice;
        this.code = code;
        this.baseprice = basePrice;
        this.discount = discount;
        this.expiration = expiration;
        this.buyNumberLimit = buyNumberLimit;
        Color_PriceID = color_PriceID;
        this.remainTime = remainTime;
    }



    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getColorPrice() {
        return colorPrice;
    }

    public void setColorPrice(int colorPrice) {
        this.colorPrice = colorPrice;
    }

    public int getWeightPrice() {
        return weightPrice;
    }

    public void setWeightPrice(int weightPrice) {
        this.weightPrice = weightPrice;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getBasePrice() {
        return baseprice;
    }

    public void setBasePrice(int basePrice) {
        this.baseprice = basePrice;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public String getExpiration() {
        return expiration;
    }

    public void setExpiration(String expiration) {
        this.expiration = expiration;
    }

    public int getBuyNumberLimit() {
        return buyNumberLimit;
    }

    public void setBuyNumberLimit(int buyNumberLimit) {
        this.buyNumberLimit = buyNumberLimit;
    }

    public int getColor_PriceID() {
        return Color_PriceID;
    }

    public void setColor_PriceID(int color_PriceID) {
        Color_PriceID = color_PriceID;
    }

    public int getRemainTime() {
        return remainTime;
    }

    public void setRemainTime(int remainTime) {
        this.remainTime = remainTime;
    }
}