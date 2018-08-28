package imie.campus.model.enums;

public enum AnnounceStateEnum {
    /**
     * The announce has been just submitted and is waiting for moderation action
     */
    SUBMITTED,

    /**
     * The announce has been validated by a moderator, making it available for the users
     */
    VALIDATED,

    /**
     * Moderation team has been warned about an announce, so the announce is no longer
     * available, waiting for a decision
     */
    SUSPENDED,

    /**
     * Announce has been refused by a moderator after its submission
     */
    REFUSED,

    /**
     * Announce has been modofied, and now waits for owner validation
     */
    MODIFIED,

    /**
     * The announce has finally been removed from the system. It is still stored
     * in the database for history purpose, but it will be not available ever.
     */
    REMOVED
}
