package se.kth.iv1351.soundGoodMusicSchooljdbc.integration;

/**
 * Exception thrown when calls to the sound good music school database fails.
 */
public class MusicSchoolDBException extends Exception {

    /**
     * Constructs a new instance of the exception with an give reason for it being thrown.
     *
     * @param reason Reason for why the exception was thrown.
     */
    public MusicSchoolDBException ( String reason ) {
        super ( reason );
    }

    /**
     * Constructs a new instance of the exception with an give reason for it being thrown and
     * the root cause of it being thrown.
     *
     * @param reason Reason for why the exception was thrown.
     * @param rootCause The exception that caused this exception to be thrown.
     */
    public MusicSchoolDBException ( String reason, Throwable rootCause ) {
        super ( reason, rootCause );
    }
}
