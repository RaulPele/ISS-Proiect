package employeesmonitor.model;

import java.io.Serializable;

public abstract class Entity<ID> implements Serializable {
    private ID  ID;

    public ID getID() {
        return ID;
    }

    public void setID(ID ID) {
        this.ID = ID;
    }
}
