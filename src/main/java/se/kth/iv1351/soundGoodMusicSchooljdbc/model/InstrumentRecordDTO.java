package se.kth.iv1351.soundGoodMusicSchooljdbc.model;

/**
 * Instance of an instrument recorde DTO.
 */
public class InstrumentRecordDTO {
    private  int InstrumentRecordId;
    private String instrumentType;
    private String instrumentName;
    private boolean rentedStatus;
    private String monthlyPayment;
    private String brand;

    public InstrumentRecordDTO(InstrumentRecord instrumentRecord) {
        this.InstrumentRecordId = instrumentRecord.getInstrumentRecordId();
        this.instrumentType = instrumentRecord.getInstrumentType();
        this.instrumentName = instrumentRecord.getInstrumentName();
        this.rentedStatus = instrumentRecord.getRentedStatus();
        this.monthlyPayment = instrumentRecord.getMonthlyPayment();
        this.brand = instrumentRecord.getBrand();
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
        return (brand != null)?new String (this.brand):null;
    }
}
