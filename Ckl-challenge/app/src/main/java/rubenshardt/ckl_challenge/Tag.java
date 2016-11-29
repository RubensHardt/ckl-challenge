package rubenshardt.ckl_challenge;

import io.realm.RealmObject;

/**
 * Created by rubenshardtjunior on 11/28/16.
 */

public class Tag extends RealmObject {

    private int id;
    private String label;

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