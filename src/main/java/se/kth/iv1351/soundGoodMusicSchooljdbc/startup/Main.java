package se.kth.iv1351.soundGoodMusicSchooljdbc.startup;

import se.kth.iv1351.soundGoodMusicSchooljdbc.controller.Controller;
import se.kth.iv1351.soundGoodMusicSchooljdbc.integration.MusicSchoolDBException;
import se.kth.iv1351.soundGoodMusicSchooljdbc.view.BlockingInterpreter;

/**
 * Starts the sound good music school application.
 */
public class Main {
    /**
     * @param args There are no command line arguments.
     */
    public static void main (String[] args) {
        try {
            new BlockingInterpreter( new Controller() ).handleCmds();
        } catch (MusicSchoolDBException e) {
            System.out.println("Could not connect to school db");
            e.printStackTrace();
        }

    }
}
