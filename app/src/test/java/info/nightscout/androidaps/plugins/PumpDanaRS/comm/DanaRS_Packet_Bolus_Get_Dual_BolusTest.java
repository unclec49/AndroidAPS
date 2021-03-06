package info.nightscout.androidaps.plugins.PumpDanaRS.comm;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import info.AAPSMocker;
import info.nightscout.androidaps.MainApp;
import info.nightscout.androidaps.logging.L;
import info.nightscout.androidaps.plugins.PumpDanaR.DanaRPump;
import info.nightscout.utils.SP;

import static org.junit.Assert.assertEquals;

/**
 * Created by Rumen on 06.08.2018.
 */

@RunWith(PowerMockRunner.class)
@PrepareForTest({MainApp.class, SP.class, L.class})
public class DanaRS_Packet_Bolus_Get_Dual_BolusTest {

    @Test
    public void runTest() {
        AAPSMocker.mockMainApp();
        AAPSMocker.mockApplicationContext();
        AAPSMocker.mockSP();
        AAPSMocker.mockL();
        DanaRS_Packet_Bolus_Get_Dual_Bolus packet = new DanaRS_Packet_Bolus_Get_Dual_Bolus();

        // test params
        assertEquals(null, packet.getRequestParams());

        // test message decoding
        double testValue = 0d;
        packet.handleMessage(createArray(10, (byte) testValue));
        assertEquals(testValue != 0d, packet.failed);
        testValue = 1d;
        packet.handleMessage(createArray(10, (byte) testValue));
        // is pump.bolustep set to 1
        DanaRPump pump = DanaRPump.getInstance();
        assertEquals((((byte)testValue & 0x000000FF) << 8) + ((byte) testValue & 0x000000FF), pump.bolusStep, 0);
        assertEquals(testValue != 0d, packet.failed);

        assertEquals("BOLUS__GET_DUAL_BOLUS", packet.getFriendlyName());
    }

    byte[] createArray(int length, byte fillWith){
        byte[] ret = new byte[length];
        for(int i = 0; i<length; i++){
            ret[i] = fillWith;
        }
        return ret;
    }

    double[] createArray(int length, double fillWith){
        double[] ret = new double[length];
        for(int i = 0; i<length; i++){
            ret[i] = fillWith;
        }
        return ret;
    }
}
