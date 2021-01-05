package se.kth.iv1351.soundGoodMusicSchooljdbc.model;

/**
 * Thrown when the requested action cant be carried out.
 */
public class InstrumentRentalException extends Exception{

    /**
     * Constructs a new instance of the exception with an give reason for it being thrown.
     *
     * @param reason Reason for why the exception was thrown.
     */
    public InstrumentRentalException ( String reason ) {
        super ( reason );
    }

    /**
     * Create a new instance thrown because of the specified reason and exception.
     *
     * @param reason    Why the exception was thrown.
     * @param rootCause The exception that caused this exception to be thrown.
     */
    public InstrumentRentalException ( String reason, Throwable rootCause ) {
        super ( reason, rootCause );
    }
}
