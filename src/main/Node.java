package main;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Node {

    public String type;
    public BufferedImage image;

    private static BufferedImage originalEmpty;
    private static BufferedImage originalWall;
    private static BufferedImage originalStart;
    private static BufferedImage originalEnd;
    private static BufferedImage originalSolution;
    private static BufferedImage originalVisited;

    private static BufferedImage resizedEmpty;
    private static BufferedImage resizedWall;
    private static BufferedImage resizedStart;
    private static BufferedImage resizedEnd;
    private static BufferedImage resizedSolution;
    private static BufferedImage resizedVisited;

    public Node(String nodeType) {
        setImage(nodeType);
        this.type = nodeType;
    }

    private void setImage(String nodeType) {
        switch (nodeType) {
            case "empty" -> image = resizedEmpty;
            case "wall" -> image = resizedWall;
            case "start" -> image = resizedStart;
            case "end" -> image = resizedEnd;
            case "solution" -> image = resizedSolution;
            case "visited" -> image = resizedVisited;
        }
    }

    public static void initImage() {
        try {
            originalEmpty = ImageIO.read(new File("src\\resources\\empty.png"));
            originalWall = ImageIO.read(new File("src\\resources\\wall.png"));
            originalStart = ImageIO.read(new File("src\\resources\\start.png"));
            originalEnd = ImageIO.read(new File("src\\resources\\end.png"));
            originalSolution = ImageIO.read(new File("src\\resources\\solution.png"));
            originalVisited = ImageIO.read(new File("src\\resources\\visited.png"));
        }
        catch (IOException e) {
            System.err.print("\n---------\n" + e.getMessage());
        }
    }

    public static void rescaleImage() {
        int targetSize = NodeCoordinator.NODE_SIZE;
        Image resultingImage = originalWall.getScaledInstance(targetSize, targetSize, Image.SCALE_DEFAULT);
        BufferedImage outputImage = new BufferedImage(targetSize, targetSize, BufferedImage.TYPE_INT_RGB);
        outputImage.getGraphics().drawImage(resultingImage, 0, 0, null);
        resizedWall = outputImage;

        resultingImage = originalStart.getScaledInstance(targetSize, targetSize, Image.SCALE_DEFAULT);
        outputImage = new BufferedImage(targetSize, targetSize, BufferedImage.TYPE_INT_RGB);
        outputImage.getGraphics().drawImage(resultingImage, 0, 0, null);
        resizedStart = outputImage;

        resultingImage = originalEnd.getScaledInstance(targetSize, targetSize, Image.SCALE_DEFAULT);
        outputImage = new BufferedImage(targetSize, targetSize, BufferedImage.TYPE_INT_RGB);
        outputImage.getGraphics().drawImage(resultingImage, 0, 0, null);
        resizedEnd = outputImage;

        resultingImage = originalEmpty.getScaledInstance(targetSize, targetSize, Image.SCALE_DEFAULT);
        outputImage = new BufferedImage(targetSize, targetSize, BufferedImage.TYPE_INT_RGB);
        outputImage.getGraphics().drawImage(resultingImage, 0, 0, null);
        resizedEmpty = outputImage;

        resultingImage = originalSolution.getScaledInstance(targetSize, targetSize, Image.SCALE_DEFAULT);
        outputImage = new BufferedImage(targetSize, targetSize, BufferedImage.TYPE_INT_RGB);
        outputImage.getGraphics().drawImage(resultingImage, 0, 0, null);
        resizedSolution = outputImage;

        resultingImage = originalVisited.getScaledInstance(targetSize, targetSize, Image.SCALE_DEFAULT);
        outputImage = new BufferedImage(targetSize, targetSize, BufferedImage.TYPE_INT_RGB);
        outputImage.getGraphics().drawImage(resultingImage, 0, 0, null);
        resizedVisited = outputImage;
    }
}
