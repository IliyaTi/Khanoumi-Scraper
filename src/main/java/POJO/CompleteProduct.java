package POJO;

public class CompleteProduct {

    private int id;
    private String name;
    private int price;
    private int discount;
    private String size;
    private String color;
    private String sizeId;
    private String colorId;
    private String link;
    private int count;

    public CompleteProduct(int id, String name, int price, int discount, String size, String color, String sizeId, String colorId) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.discount = discount;
        this.size = size;
        this.color = color;
        this.sizeId = sizeId;
        this.colorId = colorId;
    }

    public CompleteProduct(int id, String name, int price, int discount, String sizeId, String colorId, String link, int count) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.discount = discount;
        this.sizeId = sizeId;
        this.colorId = colorId;
        this.link = link;
        this.count = count;
    }

    public CompleteProduct(int id, String name, int price, int discount, String sizeId, String colorId) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.discount = discount;
        this.sizeId = sizeId;
        this.colorId = colorId;
//        this.link = link;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getSizeId() {
        return sizeId;
    }

    public void setSizeId(String sizeId) {
        this.sizeId = sizeId;
    }

    public String getColorId() {
        return colorId;
    }

    public void setColorId(String colorId) {
        this.colorId = colorId;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
