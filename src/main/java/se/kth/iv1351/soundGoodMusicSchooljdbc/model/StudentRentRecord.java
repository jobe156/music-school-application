package se.kth.iv1351.soundGoodMusicSchooljdbc.model;


import java.time.LocalDate;


/**
 * Instance of a student rent recorde.
 */
public class StudentRentRecord {
    int studentRentRecordId;
    int studentId;
    int instrumentRecordId;
    LocalDate dateOfRent;
    LocalDate lastDateOfLease;
    LocalDate endOfRent;


    /**
     * Creates an instance of a student rent record.
     * @param studentId The id of the student, renting the instrument.
     * @param instrumentRecordId The id of the instrument record which contains information about the instrument being rented.
     */
    public StudentRentRecord (int studentId, int instrumentRecordId) {
        this.studentId = studentId;
        this.instrumentRecordId = instrumentRecordId;
        this.dateOfRent = LocalDate.now();
        this.lastDateOfLease = LocalDate.now().plusYears(1);
        this.endOfRent = null;
    }

    /**
     * Creates an instance of a student rent record.
     *
     * @param studentRentRecordId The id of the student rent record in the database.
     * @param studentId The id of the student, renting the instrument.
     * @param instrumentRecordId The id of the instrument record which contains information about the instrument being rented.
     * @param dateOfRent The data in which the rent took place.
     * @param lastDateOfLease The expiration data of the rent.
     * @param endOfDate The date the rental ended.
     */
    public StudentRentRecord (int studentRentRecordId, int studentId, int instrumentRecordId, LocalDate dateOfRent, LocalDate lastDateOfLease, LocalDate endOfDate) {
        this.studentRentRecordId = studentRentRecordId;
        this.studentId = studentId;
        this.instrumentRecordId = instrumentRecordId;
        this.dateOfRent = dateOfRent;
        this.lastDateOfLease = lastDateOfLease;
        this.endOfRent = endOfDate;
    }

    /**
     * Is used to end the rental.
     * @throws InvalidInstrumentRentOperationException Is thrown if the student rent record has already ended.
     */
    public void endRental () throws InvalidInstrumentRentOperationException {
        if (endOfRent != null)
            throw new InvalidInstrumentRentOperationException("Cannot end rental that has already been ended");
        else
            endOfRent = LocalDate.now();
    }

    /**
     * Returns the student rent record id.
     * @return studentRentRecordId.
     */
    public int getStudentRentRecordeId () {
        return this.studentRentRecordId;
    }

    /**
     * Returns the student id.
     * @return student id.
     */
    public int getStudentId () {
        return this.studentId;
    }

    /**
     * Returns the instrument record id.
     * @return instrument record id.
     */
    public int getInstrumentRecordId () {
        return this.instrumentRecordId;
    }

    /**
     * Returns the date of rent as an <code>LocalDate</code> instance.
     * @return date of rent.
     */
    public LocalDate getDateOfRentAsLocalDate () {
        return dateOfRent;
    }

    /**
     * Returns the last date of lease  as an <code>LocalDate</code> instance.
     * @return last date of lease.
     */
    public LocalDate getLastDateOfLeaseAsLocalDate () {
        return lastDateOfLease;
    }

    /**
     * Returns the end of rent as an <code>LocalDate</code> instance.
     * @return end of date.
     */
    public LocalDate getEndOfRentAsLocalDate () {
        return endOfRent;
    }

    /**
     * Returns the date of rent as a <code>java.sql.Date</code> instance.
     * @return date of rent.
     */
    public java.sql.Date getDateOfRentAsDate () {
        return java.sql.Date.valueOf( this.dateOfRent );
    }

    /**
     * Returns the last date of lease as a <code>java.sql.Date</code> instance.
     * @return last date of lease.
     */
    public java.sql.Date getLastDateOfLeaseAsDate () {
        return java.sql.Date.valueOf( this.lastDateOfLease );
    }

    /**
     * Returns the end of date as a <code>java.sql.Date</code> instance.
     * @return end of date.
     */
    public java.sql.Date getEndOfDateAsDate () {
        return java.sql.Date.valueOf( this.endOfRent);
    }
}
