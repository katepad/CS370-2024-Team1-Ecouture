package model;

import java.util.ArrayList;
import java.util.List;

public class clothingItem {
    private String title;
    private final String type;
    private final String acquireMethod;
    private final String brand;
    private final List<String> materials;
    private final List<Integer> percentages;
    private final int userID;
    private final int clothesID;

    //constructor
    public clothingItem(String title, String type, String acquireMethod, String brand, int userID, int clothesID) {
        this.title = title;
        this.type = type;
        this.acquireMethod = acquireMethod;
        this.brand = brand;
        this.userID = userID;
        this.materials = new ArrayList<>();
        this.percentages = new ArrayList<>();
        this.clothesID = clothesID;
    }

    //get title
    public String getTitle() {
        return title;
    }

    //set title
    public void setTitle(String title) {
        this.title = title;
    }

    //getters

    public String getType() {
        return type;
    }

    public String getAcquireMethod() {
        return acquireMethod;
    }

    public String getBrand() {
        return brand;
    }

    public List<String> getMaterials() {
        return materials;
    }

    //add material
    public void addMaterial(String material, int percentage) {
        this.materials.add(material);
        this.percentages.add(percentage);
    }

    //get percentages
    public List<Integer> getPercentages() {
        return percentages;
    }

    public int getUserId() {
        return userID;
    }

    public int getClothesID() {
        return clothesID;
    }
}