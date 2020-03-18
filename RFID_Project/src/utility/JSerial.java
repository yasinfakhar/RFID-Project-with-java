package utility;

import com.fazecast.jSerialComm.SerialPort;
import java.io.InputStream;

/**
 *
 * @author vubon
 */
public class JSerial {
    public static SerialPort arduinoPort = null;
    public static int PACKET_SIZE_IN_BYTES = 8;


    public void scanCard() {

            arduinoPort = SerialPort.getCommPort("COM5");
            arduinoPort.openPort();
            PacketListener listener = new PacketListener();
            arduinoPort.addDataListener(listener);


////-------------------give it a rest--------------------------------
            try {
                System.out.println("wait 3 second");
                Thread.sleep(3000);
                System.out.println("done");
            } catch (Exception e) {
                e.printStackTrace();
            }
            arduinoPort.removeDataListener();
            arduinoPort.closePort();

    }

}

