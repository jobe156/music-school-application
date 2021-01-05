package se.kth.iv1351.soundGoodMusicSchooljdbc.model;

import java.time.LocalDate;

/**
 * A DTO of an student Rent record instance.
 */
public class StudentRentRecordDTO {
    int studentRentRecordId;
    int studentId;
    int instrumentRecordId;
    LocalDate dateOfRent;
    LocalDate lastDateOfLease;
    LocalDate endOfRent;

    /**
     * Constructs an instance of a student rent record dto.
     *
     * @param studentRentRecord The corresponding <code>StudentRentRecord</code>.
     */
    public StudentRentRecordDTO ( StudentRentRecord studentRentRecord ) {
        this.studentRentRecordId = studentRentRecord.getStudentRentRecordeId();
        this.studentId = studentRentRecord.getStudentId();
        this.instrumentRecordId = studentRentRecord.getInstrumentRecordId();
        this.dateOfRent = studentRentRecord.getDateOfRentAsLocalDate();
        this.lastDateOfLease = studentRentRecord.getLastDateOfLeaseAsLocalDate();
        this.endOfRent = studentRentRecord.getEndOfRentAsLocalDate();
    }

    /**
     * Returns the student rent record id.
     * @return studentRentRecordId.
     */
    public int getStudentRentRecordId () {
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
