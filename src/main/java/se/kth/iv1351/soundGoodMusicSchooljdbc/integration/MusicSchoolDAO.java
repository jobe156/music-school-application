package se.kth.iv1351.soundGoodMusicSchooljdbc.integration;

import se.kth.iv1351.soundGoodMusicSchooljdbc.model.InstrumentRecord;
import se.kth.iv1351.soundGoodMusicSchooljdbc.model.InstrumentRecordDTO;
import se.kth.iv1351.soundGoodMusicSchooljdbc.model.StudentRentRecord;
import se.kth.iv1351.soundGoodMusicSchooljdbc.model.StudentRentRecordDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * A data access object (DAO) that provides access to the sound good music school database.
 */
public class MusicSchoolDAO {

    private Connection connection;
    private static final String INSTRUMENT_RECORD_TABLE_NAME = "instrument_record";
    private static final String STUDENT_RENT_RECORD_TABLE_NAME = "student_rent_record";

    private static final String INSTRUMENT_RECORD_ID_COLUMN_NAME = "instrument_record_id";
    private static final String INSTRUMENT_TYPE_COLUMN_NAME = "instrument_type";
    private static final String INSTRUMENT_NAME_COLUMN_NAME = "instrument_name";
    private static final String RENTED_STATUS_COLUMN_NAME = "rented_status";
    private static final String MONTHLY_PAYMENT_COLUMN_NAME = "monthly_payment";
    private static final String BRAND_COLUMN_NAME = "brand";
    private static final String STUDENT_ID_COLUMN_NAME = "student_id";
    private static final String DATE_OF_RENT_COLUMN_NAME = "date_of_rent";
    private static final String LAST_DATE_OF_LEASE_COLUMN_NAME = "last_date_of_rent";
    private static final String END_OF_RENT_COLUMN_NAME = "end_of_rent";
    private static final String STUDENT_RENT_RECORD_ID_COLUMN_NAME = "student_rent_record_id";

    private PreparedStatement createStudentRentRecordStmt;
    private PreparedStatement findAllInstrumentRecordsByInstrumentNameStmt;
    private PreparedStatement findInstrumentRecordByInstrumentRecordIdStmt;
    private PreparedStatement findStudentRentRecordStmt;
    private PreparedStatement getNumberOfStudentRentRecordsByStudentIdStmt;
    private PreparedStatement updateInstrumentRecordStmt;
    private PreparedStatement updateStudentRentRecordStmt;


    /**
     * Constructs a new instance of the DAO.
     * @throws MusicSchoolDBException If construction of DAO fails.
     */
    public MusicSchoolDAO() throws MusicSchoolDBException {
        try {
            connectToMusicSchoolDB();
            prepareStatements();
        } catch (SQLException exception) {
            throw new MusicSchoolDBException("Could not connect to datasource.", exception);
        }
    }

    private void connectToMusicSchoolDB () throws SQLException {
        connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/soundGoodMusicSchool",
                "postgres", "postgres");
        connection.setAutoCommit(false);
    }

    /**
     * Returns all instrument records with an instrument name equal to the given string. if no
     * Instrument records can be found, an empty list is returned.
     * @param instrumentName The instrument name.
     * @return A list with instances of <code>InstrumentRecord</code> which corresponds to all
     * instrument records that was found.
     * @throws MusicSchoolDBException If failed ti search for instrument records.
     */
    public List<InstrumentRecord> findAllInstrumentRecordsByInstrumentName(String instrumentName) throws MusicSchoolDBException {
        String failureMsg = "Could not list instrument records by given instrument name";
        ResultSet result = null;
        List<InstrumentRecord> instrumentRecords = new ArrayList<>();
        try {
            findAllInstrumentRecordsByInstrumentNameStmt.setString(1, instrumentName);
            result = findAllInstrumentRecordsByInstrumentNameStmt.executeQuery();
            while ( result.next() ) {
                instrumentRecords.add( new InstrumentRecord (
                        result.getInt(INSTRUMENT_RECORD_ID_COLUMN_NAME),
                        result.getString(INSTRUMENT_TYPE_COLUMN_NAME),
                        result.getString(INSTRUMENT_NAME_COLUMN_NAME),
                        result.getBoolean(RENTED_STATUS_COLUMN_NAME),
                        result.getString(MONTHLY_PAYMENT_COLUMN_NAME),
                        result.getString(BRAND_COLUMN_NAME) ) );
            }
            connection.commit();
        } catch ( SQLException exception ) {
            handleException( failureMsg, exception );
        } finally {
            closeResultSet( failureMsg, result );
        }
        return instrumentRecords;
    }

    /**
     * Tries to find a instrument record with the given instrument record id. Returns null if no record is found.
     * @param instrumentRecordId    The id of the sought after recorde.
     * @return  The record with the corresponding instrument record id.
     * @throws MusicSchoolDBException Is thrown if a problem occur when searching for the instrument record.
     */
    public InstrumentRecord findInstrumentRecordByInstrumentId (int instrumentRecordId) throws MusicSchoolDBException {
        String failureMsg = "Could not search for instrumentId";
        ResultSet result = null;
        InstrumentRecord instRec = null;
        try {
            findInstrumentRecordByInstrumentRecordIdStmt.setInt(1, instrumentRecordId);
            result = findInstrumentRecordByInstrumentRecordIdStmt.executeQuery();
            if ( result.next() )
                instRec = new InstrumentRecord (
                        result.getInt(INSTRUMENT_RECORD_ID_COLUMN_NAME),
                        result.getString(INSTRUMENT_TYPE_COLUMN_NAME),
                        result.getString(INSTRUMENT_NAME_COLUMN_NAME),
                        result.getBoolean(RENTED_STATUS_COLUMN_NAME),
                        result.getString(MONTHLY_PAYMENT_COLUMN_NAME),
                        result.getString(BRAND_COLUMN_NAME) );
            connection.commit();
        } catch (SQLException exception) {
            handleException(failureMsg, exception );
        } finally {
            closeResultSet( failureMsg, result );
        }
        return instRec;
    }

    /**
     * Tries to find a student rent record with the given student rent record id. Returns null if no record is found.
     * @param studentRentRecordId The id of the sought after recorde.
     * @return The recorde with the corresponding student rent recorde id.
     * @throws MusicSchoolDBException Is thrown if a problem occur when searching for the student rent record.
     */
    public StudentRentRecord findStudentRentRecord (int studentRentRecordId) throws MusicSchoolDBException {
        String failureMsg = "Could not search for student rent record";
        ResultSet result = null;
        StudentRentRecord studentRentRecord = null;
        try {
            findStudentRentRecordStmt.setInt(1, studentRentRecordId);
            result = findStudentRentRecordStmt.executeQuery();
            if( result.next() )
                studentRentRecord = new StudentRentRecord (
                    result.getInt(STUDENT_RENT_RECORD_ID_COLUMN_NAME),
                    result.getInt(STUDENT_ID_COLUMN_NAME),
                    result.getInt(INSTRUMENT_RECORD_ID_COLUMN_NAME),
                    result.getDate(DATE_OF_RENT_COLUMN_NAME).toLocalDate(),
                    result.getDate(LAST_DATE_OF_LEASE_COLUMN_NAME).toLocalDate(),
                    (result.getDate(END_OF_RENT_COLUMN_NAME) != null)? result.getDate(END_OF_RENT_COLUMN_NAME).toLocalDate() : null
                );
        } catch (SQLException exception) {
            handleException(failureMsg, exception);
        } finally {
            closeResultSet( failureMsg, result );
        }
        return studentRentRecord;
    }

    /**
     * Return the the current number of instruments a student has.
     * @param studentId The id of the student.
     * @return the number of currently rented instruments.
     * @throws MusicSchoolDBException Is thrown if a problem occurs with the database while searching.
     */
    public int getNumberOfStudentRentRecordsByStudentId ( int studentId ) throws MusicSchoolDBException {
        String failureMsg = "Could not list student rent records by given student id.";
        ResultSet result = null;
        int numberOfStudentRecords = -1;
        try {
            getNumberOfStudentRentRecordsByStudentIdStmt.setInt( 1, studentId);
            result = getNumberOfStudentRentRecordsByStudentIdStmt.executeQuery();
            if ( result.next() ) {
                numberOfStudentRecords = result.getInt("count");
            } else {
                numberOfStudentRecords = 0;
            }
            connection.commit();
        } catch (SQLException exception) {
            handleException( failureMsg, exception );
        } finally {
            closeResultSet (failureMsg, result);
        }

        return numberOfStudentRecords;
    }

    /**
     * Used to register that a student have rented an instrument.
     *
     * @param instrumentRecordDto The instrument record data after the rent will be done.
     * @param studentRentRecordDto The student rent record data containing information about the rent.
     * @throws MusicSchoolDBException Is thrown if a problem occurs with the database while trying to register the rent.
     */
    public void rentInstrument ( InstrumentRecordDTO instrumentRecordDto, StudentRentRecordDTO studentRentRecordDto ) throws MusicSchoolDBException {
        String failureMsg = "Could not rent instrument";
        int updatedRows = -1;
        try {
            createStudentRentRecordStmt.setInt(1, studentRentRecordDto.getInstrumentRecordId());
            createStudentRentRecordStmt.setInt(2, studentRentRecordDto.getStudentId());
            createStudentRentRecordStmt.setDate(3, studentRentRecordDto.getDateOfRentAsDate());
            createStudentRentRecordStmt.setDate(4, studentRentRecordDto.getLastDateOfRentAsDate());
            updatedRows = createStudentRentRecordStmt.executeUpdate();
            if ( updatedRows != 1 )
                handleException (failureMsg, null );
            updateInstrumentRecordStmt.setInt(1, instrumentRecordDto.getInstrumentRecordId());
            updateInstrumentRecordStmt.setString(2, instrumentRecordDto.getInstrumentType());
            updateInstrumentRecordStmt.setString(3, instrumentRecordDto.getInstrumentName());
            updateInstrumentRecordStmt.setBoolean(4, instrumentRecordDto.getRentedStatus());
            updateInstrumentRecordStmt.setString(5, instrumentRecordDto.getMonthlyPayment());
            updateInstrumentRecordStmt.setString(6, instrumentRecordDto.getBrand());
            updateInstrumentRecordStmt.setInt(7, instrumentRecordDto.getInstrumentRecordId());
            updatedRows = updateInstrumentRecordStmt.executeUpdate();
            if ( updatedRows != 1 )
                handleException ( failureMsg, null );
            connection.commit();
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
            handleException( failureMsg, null );
        }
    }

    /**
     * Terminates an existing rent.
     *
     * @param instrumentRecordDTO The data of the instrument record after the rent.
     * @param studentRentRecordDTO The student rent record data of the rent.
     * @throws MusicSchoolDBException Is thrown if a problem occurs with the database while trying to terminate the rent.
     */
    public void endInstrumentRent (InstrumentRecordDTO instrumentRecordDTO, StudentRentRecordDTO studentRentRecordDTO) throws MusicSchoolDBException {
        String failureMsg = "Could not end rental";
        int updatedRows = -1;
        try {
            updateStudentRentRecordStmt.setInt(1,  studentRentRecordDTO.getInstrumentRecordId());
            updateStudentRentRecordStmt.setInt(2,  studentRentRecordDTO.getStudentId());
            updateStudentRentRecordStmt.setDate(3,  studentRentRecordDTO.getDateOfRentAsDate());
            updateStudentRentRecordStmt.setDate(4,  studentRentRecordDTO.getLastDateOfRentAsDate());
            updateStudentRentRecordStmt.setDate(5,  studentRentRecordDTO.getEndOfDateAsDate());
            updateStudentRentRecordStmt.setInt(6,  studentRentRecordDTO.getStudentRentRecordId());
            updatedRows = updateStudentRentRecordStmt.executeUpdate();
            if ( updatedRows != 1 )
                handleException( failureMsg, null );
            updateInstrumentRecordStmt.setInt(1, instrumentRecordDTO.getInstrumentRecordId());
            updateInstrumentRecordStmt.setString(2, instrumentRecordDTO.getInstrumentType());
            updateInstrumentRecordStmt.setString(3, instrumentRecordDTO.getInstrumentName());
            updateInstrumentRecordStmt.setBoolean(4, instrumentRecordDTO.getRentedStatus());
            updateInstrumentRecordStmt.setString(5, instrumentRecordDTO.getMonthlyPayment());
            updateInstrumentRecordStmt.setString(6, instrumentRecordDTO.getBrand());
            updateInstrumentRecordStmt.setInt(7, instrumentRecordDTO.getInstrumentRecordId());
            updatedRows = updateInstrumentRecordStmt.executeUpdate();
            if ( updatedRows != 1 )
                handleException( failureMsg, null );
            connection.commit();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    private void handleException( String failureMsg, Exception cause ) throws MusicSchoolDBException {
        String completeFailureMsg = failureMsg;

        try {
            connection.rollback();
        } catch ( SQLException exception ) {
            completeFailureMsg = completeFailureMsg +
                    ". Also failed to rollback transaction because of: " + exception.getMessage();
        }

        if ( cause != null ) {
            throw new MusicSchoolDBException(completeFailureMsg, cause);
        } else {
            throw new MusicSchoolDBException(completeFailureMsg);
        }
    }

    private void closeResultSet( String failureMsg, ResultSet result ) throws MusicSchoolDBException {
        try {
            result.close();
        } catch ( SQLException exception ) {
            throw new MusicSchoolDBException(failureMsg + " Could not close result set.", exception);
        }
    }

    private void prepareStatements() throws SQLException {
        createStudentRentRecordStmt = connection.prepareStatement(
                "INSERT INTO " + STUDENT_RENT_RECORD_TABLE_NAME +
                    "(" + INSTRUMENT_RECORD_ID_COLUMN_NAME + ", " + STUDENT_ID_COLUMN_NAME + ", " + DATE_OF_RENT_COLUMN_NAME + ", " + LAST_DATE_OF_LEASE_COLUMN_NAME + ")" +
                    " VALUES ( ?, ?, ?, ?)");

        findAllInstrumentRecordsByInstrumentNameStmt = connection.prepareStatement(
                "SELECT *" +
                    " FROM " + INSTRUMENT_RECORD_TABLE_NAME +
                    " WHERE " + INSTRUMENT_NAME_COLUMN_NAME + " = ?");

        findInstrumentRecordByInstrumentRecordIdStmt = connection.prepareStatement(
                "SELECT *" +
                    " FROM " + INSTRUMENT_RECORD_TABLE_NAME +
                    " WHERE " + INSTRUMENT_RECORD_ID_COLUMN_NAME + " = ?");

        findStudentRentRecordStmt = connection.prepareStatement(
                "SELECT *" +
                    " FROM " + STUDENT_RENT_RECORD_TABLE_NAME +
                    " WHERE " + STUDENT_RENT_RECORD_ID_COLUMN_NAME + " = ? ");

        getNumberOfStudentRentRecordsByStudentIdStmt = connection.prepareStatement(
                "SELECT " + STUDENT_ID_COLUMN_NAME + ", COUNT(" + STUDENT_ID_COLUMN_NAME + ")" +
                    " FROM " + STUDENT_RENT_RECORD_TABLE_NAME +
                    " WHERE " + STUDENT_ID_COLUMN_NAME + " = ? AND " + END_OF_RENT_COLUMN_NAME + " IS NULL" +
                    " GROUP BY " + STUDENT_ID_COLUMN_NAME);

        updateInstrumentRecordStmt = connection.prepareStatement(
                "UPDATE " + INSTRUMENT_RECORD_TABLE_NAME +
                    " SET " + INSTRUMENT_RECORD_ID_COLUMN_NAME + " = ?, "
                    + INSTRUMENT_TYPE_COLUMN_NAME + " = ?, "
                    + INSTRUMENT_NAME_COLUMN_NAME + " = ?, "
                    + RENTED_STATUS_COLUMN_NAME + " = ?, "
                    + MONTHLY_PAYMENT_COLUMN_NAME + " = ?, "
                    + BRAND_COLUMN_NAME + " = ?" +
                    " WHERE " + INSTRUMENT_RECORD_ID_COLUMN_NAME + " = ?");

        updateStudentRentRecordStmt = connection.prepareStatement(
                "UPDATE " + STUDENT_RENT_RECORD_TABLE_NAME +
                    " SET " + INSTRUMENT_RECORD_ID_COLUMN_NAME + " = ?, "
                    + STUDENT_ID_COLUMN_NAME + " = ?, "
                    + DATE_OF_RENT_COLUMN_NAME + " = ?,"
                    + LAST_DATE_OF_LEASE_COLUMN_NAME + " = ?, "
                    + END_OF_RENT_COLUMN_NAME + " = ?" +
                        " WHERE " + STUDENT_RENT_RECORD_ID_COLUMN_NAME + " = ?");
    }
}
