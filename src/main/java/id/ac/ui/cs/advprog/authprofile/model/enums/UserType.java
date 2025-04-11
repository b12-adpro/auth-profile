package id.ac.ui.cs.advprog.authprofile.model.enums;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum UserType {
    FUNDRAISER("Fundraiser"),
    DONOR("Donor"),
    FUNDDONOR("Fundraiser & Donor"),
    ADMIN("Admin");

    private final String label;

    UserType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public static boolean contains(String input) {
        return Arrays.stream(UserType.values())
                     .anyMatch(type -> type.name().equalsIgnoreCase(input));
    }

    public static List<String> getAllNames() {
        return Arrays.stream(UserType.values())
                     .map(Enum::name)
                     .collect(Collectors.toList());
    }

    public static List<String> getAllLabels() {
        return Arrays.stream(UserType.values())
                     .map(UserType::getLabel)
                     .collect(Collectors.toList());
    }
}
