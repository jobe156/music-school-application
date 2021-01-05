package se.kth.iv1351.soundGoodMusicSchooljdbc.model;

/**
 * An instance of an instrument record.
 */
public class InstrumentRecord {
    private  int InstrumentRecordId;
    private String instrumentType;
    private String instrumentName;
    private boolean rentedStatus;
    private String monthlyPayment;
    private String brand;

    /**
     * Constructs an instance of a {@link InstrumentRecord}.
     *
     * @param instrumentRecordId Id of the instrument record as an int.
     * @param instrumentType Instrument type of the instrument record as a string.
     * @param instrumentName Instrument name of the instrument record as a string.
     * @param rentedStatus A boolean value which states if the record is rented or not. True means that
     *                     it´s rented and false means that it can be rented.
     * @param monthlyPayment The amount to pay each month when renting the instrument.
     * @param brand The brand of the instrument.
     */
    public InstrumentRecord ( int instrumentRecordId, String instrumentType, String instrumentName, Boolean rentedStatus, String monthlyPayment , String brand ) {
        this.InstrumentRecordId = instrumentRecordId;
        this.instrumentType = instrumentType;
        this.instrumentName = instrumentName;
        this.rentedStatus = rentedStatus;
        this.monthlyPayment = monthlyPayment;
        this.brand = brand;
    }

    /**
     * Returns the InstrumentRecordId.
     * @return InstrumentRecordId.
     */
    public int getInstrumentRecordId () {
        return this.InstrumentRecordId;
    }

    /**
     * Returns the instrumentType.
     * @return instrumentType.
     */
    public String getInstrumentType () { return (this.instrumentType != null)?new String (this.instrumentType):null; }

    /**
     * Returns the instrumentName.
     * @return instrumentName.
     */
    public String getInstrumentName () {
        return new String (this.instrumentName);
    }

    /**
     * Returns the rentedStatus.
     * @return rentedStatus.
     */
    public Boolean getRentedStatus () {
        return this.rentedStatus;
    }

    /**
     * Returns the monthlyPayment.
     * @return monthlyPayment.
     */
    public String getMonthlyPayment () {
        return new String (this.monthlyPayment);
    }

    /**
     * Returns the brand.
     * @return brand.
     */
    public String getBrand () {
        return (this.brand != null)?new String (this.brand):null;
    }

    /**
     * Is used to set the rented state of the instrument recorde.
     * @param rentedStatus the state to which the recorde is set.
     * @throws InvalidInstrumentRentOperationException When someone ties to set the rented state to true, when it´s already
     * rented.
     */
    public void setRentedStatus (boolean rentedStatus) throws InvalidInstrumentRentOperationException {
        if (this.rentedStatus && rentedStatus) {
            throw new InvalidInstrumentRentOperationException("Cannot rent instrument that´s already rented");
        } else {
            this.rentedStatus = rentedStatus;
        }
    }
}
