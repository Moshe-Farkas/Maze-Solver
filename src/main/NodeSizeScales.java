package main;

public class NodeSizeScales {

    private static int[] scales = new int[] {50, 40, 25, 20, 10};
    private static int scaleIndex = 0;

    public static int getNextIncreasingScale() {
        if (scaleIndex == 0) {
            return -1;
        }
        return scales[--scaleIndex];
    }

    public static int getNextDecreasingScale() {
        if (scaleIndex == scales.length - 1) {
            return -1;
        }
        return scales[++scaleIndex];
    }
}