package id.ac.ui.cs.advprog.authprofile.model.enums;

import java.util.ArrayList;
import java.util.List;

public enum UserType {
    FUNDRAISER("FUNDRAISER"),
    DONOR("DONOR"),
    FUNDDONOR("FUNDDONOR"),
    ADMIN("ADMIN");

    private final String value;

    UserType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static boolean contains(String param) {
        for (UserType userType : UserType.values()) {
            if (userType.name().equalsIgnoreCase(param)) {
                return true;
            }
        }
        return false;
    }

    public static List<String> getAll() {
        List<String> types = new ArrayList<>();
        for (UserType userType : UserType.values()) {
            types.add(userType.name());
        }
        return types;
    }
}
