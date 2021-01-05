package se.kth.iv1351.soundGoodMusicSchooljdbc.controller;

import se.kth.iv1351.soundGoodMusicSchooljdbc.integration.MusicSchoolDAO;
import se.kth.iv1351.soundGoodMusicSchooljdbc.integration.MusicSchoolDBException;
import se.kth.iv1351.soundGoodMusicSchooljdbc.model.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller of the application.
 */
public class Controller {
    private final MusicSchoolDAO musicSchoolDb;

    /**
     * Constructs a new instance of the controller, which receives a connection to the
     * sound good music school.
     *
     * @throws MusicSchoolDBException If no connection can be established with the
     * music school data base.
     */
    public Controller () throws MusicSchoolDBException {
        musicSchoolDb = new MusicSchoolDAO();
    }

    /**
     * Returns all instrument records that were found as <code>InstrumentRecordDTO</code>. If no records were found, an empty list
     * is returned.
     * @return list of <code>InstrumentRecordDTO</code>
     * @throws InstrumentRentalException if problems occurred when trying to fetch data from the database.
     */
    public List<InstrumentRecordDTO> getAllInstrumentRecordsByInstrumentType(String instrumentType) throws InstrumentRentalException {
        try {
            List <InstrumentRecordDTO> instrumentRecordDTOs = new ArrayList<>();
            List <InstrumentRecord> instrumentRecords = musicSchoolDb.findAllInstrumentRecordsByInstrumentName(instrumentType);
            for (InstrumentRecord ir : instrumentRecords ) {
                if ( !ir.getRentedStatus() )
                    instrumentRecordDTOs.add( new InstrumentRecordDTO ( ir ) );
            }
            return instrumentRecordDTOs;
        } catch (MusicSchoolDBException exception) {
            throw new InstrumentRentalException("Unable to list instrument records. ", exception );
        }
    }

    /**
     * Allows user to rent an instrument, if the user already has rented 2 instrument an exception is thrown to notify
     * the user.
     * @param studentId Id of the student that want to rent the instrument.
     * @param instrumentRecordId The instrument the student wants to rent.
     * @throws InstrumentRentalException If the user already is renting 2 instruments.
     */
    public void RentInstrument(int studentId, int instrumentRecordId) throws InstrumentRentalException {
        String failureMsg = "Could not perform rental";
        int numberOfStudentRentRecords;
        InstrumentRecord instrumentRecord;
        InstrumentRecordDTO instrumentRecordDto;
        StudentRentRecord studentRentRecord;
        StudentRentRecordDTO studentRentRecordDto;
        try {
            numberOfStudentRentRecords = musicSchoolDb.getNumberOfStudentRentRecordsByStudentId(studentId);
            if (numberOfStudentRentRecords < 2) {
                instrumentRecord = musicSchoolDb.findInstrumentRecordByInstrumentId(instrumentRecordId);
                instrumentRecord.setRentedStatus(true);
                instrumentRecordDto = new InstrumentRecordDTO(instrumentRecord);

                studentRentRecord = new StudentRentRecord(studentId, instrumentRecordId);
                studentRentRecordDto = new StudentRentRecordDTO(studentRentRecord);

                musicSchoolDb.rentInstrument(instrumentRecordDto, studentRentRecordDto);
            }
        } catch (Exception exception) {
            throw new InstrumentRentalException(failureMsg, exception);
        }

    }

    /**
     * Registers the termination of a rental in the database. if the corresponding student rent record has already been
     * ended, an exception is thrown.
     * @param studentRentRecordId the id of the student rent record which is associated with the rental.
     * @throws InstrumentRentalException Is thrown if a problem occurs with the database or if the rental has already
     * been ended.
     */
    public void endInstrumentRental ( int studentRentRecordId ) throws InstrumentRentalException {
        String failureMsg = "Could not end rental";
        StudentRentRecord studentRentRecord;
        StudentRentRecordDTO studentRentRecordDto;
        InstrumentRecord instrumentRecord;
        InstrumentRecordDTO instrumentRecordDto;
        try {
            studentRentRecord = musicSchoolDb.findStudentRentRecord(studentRentRecordId);
            studentRentRecord.endRental();
            studentRentRecordDto = new StudentRentRecordDTO(studentRentRecord);

            instrumentRecord = musicSchoolDb.findInstrumentRecordByInstrumentId(studentRentRecord.getInstrumentRecordId());
            instrumentRecord.setRentedStatus(false);
            instrumentRecordDto = new InstrumentRecordDTO(instrumentRecord);

            musicSchoolDb.endInstrumentRent(instrumentRecordDto, studentRentRecordDto);
        } catch (Exception exception) {
            throw new InstrumentRentalException(failureMsg, exception);
        }
    }

}
