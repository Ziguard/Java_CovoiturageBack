package imie.campus.utils.commons;

public class ValidationPatterns {

    private static final String DIACRITIC_CHARS_FR = "éèëêàâûùîôö";

    public static final String LOGIN = "[A-Za-z0-9#!\\-\\_]+";

    public static final String NAMES = "[A-Za-z\\- " + DIACRITIC_CHARS_FR + "]+";

    public static final String EMAIL = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";

    public static final String PHONE = "(0|\\+33|0033)[1-9][0-9]{8}";

    public static final String ISO_DATETIME = "[\\d]{4}(-[\\d]{2}){2}T[\\d]{2}(:[\\d]{2}){2}";
}
