package imie.campus.core.entities;

import java.io.Serializable;

public interface Identifiable<PK extends Serializable> {
    PK primaryKey();
}
