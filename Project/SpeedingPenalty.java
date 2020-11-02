
/**
 * @author Paul Davies, for Programming Techniques 2019 Assignment. The
 *         data fields and methods are static. The four array data fields
 *         act in parallel. Hence, determining the location of the speeding
 *         offence in the ExcessSpeed array provides the index of the Demerit
 *         Points, Fine and Licence Suspension in the other arrays.
 *
 *         This class must be used in the assignment. Do not change the existing
 *         data fields or methods. You may add other methods to the class as
 *         needed.
 *
 */
public class SpeedingPenalty {

    static private int[] ExcessSpeed = {0, 10, 20, 30, 45};
    static private int[] DemeritPoints = {1, 3, 4, 5, 6};
    static private float[] Fine = {360.0f, 481.0f, 599.0f, 1441.0f, 3762.0f};
    static private boolean[] LicenceSuspension = {false, false, false, true, true}; // false is No, true is Yes

    /**
     * determines the array index for the speeding offence
     *
     * @param overSpeed - number of km/h over the speed limit
     * @return the array index where overSpeed is found, return -1 if overSpeed
     * is not found
     */
    private static byte findPenaltyIndex(int overSpeed) {
        byte index = -1;

        if (overSpeed > 0) {
            for (int i = ExcessSpeed.length - 1; i >= 0; i--) {
                if (overSpeed > ExcessSpeed[i]) {
                    index = (byte) i;
                    break;
                }
            }
        }
        return index;
    }
    
    // Get the fine for an excess speed
    public static float getFine(int speed) {
        return Fine[findPenaltyIndex(speed)];
    }

    // Get the demerit points of a speed
    public static int getDemeritPoints(int speed) {
        return DemeritPoints[findPenaltyIndex(speed)];
    }

    // Return wehtehr a speed is automatically suspended
    public static boolean causesLicenseSuspension(int speed) {
        return LicenceSuspension[findPenaltyIndex(speed)];
    }
}
