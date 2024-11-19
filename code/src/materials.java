
public class materials {

    private int materialID;
    private String materialName;
    private int materialPercent;

    // Constructor
    public materials (int materialID, String materialName, int materialPercent) {
        this.materialID = materialID;
        this.materialName = materialName;
        this.materialPercent = materialPercent;
    }

    // Getters

    public int getMaterialID() {
        return materialID;
    }

    public String getMaterialName() {
        return materialName;
    }

    public int getMaterialPercent() {
        return materialPercent;
    }

}
