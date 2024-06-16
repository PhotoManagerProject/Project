package Files.enums;

/**
 * Καθορίζει όλους τους διαφορετικούς τύπους προφίλ
 */
public enum ProfileType {
    PROFILE_1, PROFILE_2;

    /**
     * @return το επιλεγμένο PhotoType
     */
    public String toString() {
        return switch (this) {
            case PROFILE_1 -> "Profile1";
            case PROFILE_2 -> "Profile2";
        };
    }
}