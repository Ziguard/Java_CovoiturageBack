package imie.campus.model.enums;

import imie.campus.model.entities.*;

import java.util.Arrays;

public enum AnnounceTypeEnum {
    CARPOOLING(Carpooling.class),
    EXCHANGE(Exchange.class),
    SERVICE(Service.class),
    HOUSING(Housing.class);

    private final Class<? extends Announce> associatedClass;

    AnnounceTypeEnum(Class<? extends Announce> associatedClass) {
        this.associatedClass = associatedClass;
    }

    public Class<? extends Announce> getAssociatedClass() {
        return associatedClass;
    }

    public static AnnounceTypeEnum fromAnnounceClass(
            Class<?> announceClass) {
        return Arrays.stream(values())
                .filter(type -> type.getAssociatedClass().equals(announceClass))
                .findFirst()
                .orElse(null);
    }
}
