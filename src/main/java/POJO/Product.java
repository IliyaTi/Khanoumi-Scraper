package POJO;

import java.util.ArrayList;

public class Product {

    private int id;
    private String name;
    private String brand;
    private ArrayList<String> prices;
    private ArrayList<String> sizeDescs;
    private ArrayList<String> colorDescs;
    private ArrayList<String> sizes;
    private ArrayList<String> colors;
    private int count;

    public Product(){}

    public Product(String name) {
        this.name = name;
    }

    public Product(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Product(int id, String name, ArrayList<String> price, ArrayList<String> sizes, ArrayList<String> colors) {
        this.id = id;
        this.name = name;
        this.prices = price;
        this.sizes = sizes;
        this.colors = colors;
    }

    public Product(int id, String name, ArrayList<String> prices, ArrayList<String> sizeDescs, ArrayList<String> colorDescs, ArrayList<String> sizes, ArrayList<String> colors, int count) {
        this.id = id;
        this.name = name;
        this.prices = prices;
        this.sizeDescs = sizeDescs;
        this.colorDescs = colorDescs;
        this.sizes = sizes;
        this.colors = colors;
        this.count = count;
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

    public ArrayList<String> getPrices() {
        return prices;
    }

    public void setPrices(ArrayList<String> prices) {
        this.prices = prices;
    }

    public ArrayList<String> getSizes() {
        return sizes;
    }

    public void setSizes(ArrayList<String> sizes) {
        this.sizes = sizes;
    }

    public ArrayList<String> getColors() {
        return colors;
    }

    public void setColors(ArrayList<String> colors) {
        this.colors = colors;
    }

    public int getCount() { return this.count; };

    public void setCount(int count) {this.count = count; }

    public ArrayList<String> getSizeDescs() {
        return sizeDescs;
    }

    public void setSizeDescs(ArrayList<String> sizeDescs) {
        this.sizeDescs = sizeDescs;
    }

    public ArrayList<String> getColorDescs() {
        return colorDescs;
    }

    public void setColorDescs(ArrayList<String> colorDescs) {
        this.colorDescs = colorDescs;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }
}
