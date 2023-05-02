package main;

public final class NodeCoordinator {
    private NodeCoordinator() {}

    public final static int GRID_SPACING = 3;
    public static int NODE_SIZE = 50;

    public static int boardSpaceToPixelSpace(int boardCoordinate) {
        return (boardCoordinate * NODE_SIZE) + (boardCoordinate * GRID_SPACING);
    }

    public static int pixelSpaceToBoardSpace(int pixelCoordinate) {
        return pixelCoordinate / (NODE_SIZE + GRID_SPACING);
    }
}
