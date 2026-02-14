package main;

import javax.swing.SwingUtilities;
import ui.RoomSetupFrame;

public class Main {

    public static void main(String[] args) {

        // Always run Swing inside Event Dispatch Thread
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {

                new RoomSetupFrame();

            }
        });

    }
}
