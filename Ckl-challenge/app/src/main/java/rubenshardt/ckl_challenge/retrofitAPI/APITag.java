package rubenshardt.ckl_challenge.retrofitAPI;

/**
 * Created by rubenshardtjunior on 11/29/16.
 */

public class APITag {

    private int id;
    private String label;

    public APITag (int id, String label) {
        this.id = id;
        this.label = label;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

}