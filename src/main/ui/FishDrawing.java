package ui;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

// represents a fish drawing on a JPanel
public class FishDrawing extends JPanel {
    private static final int FISH_BODY_X = 180;
    private static final int FISH_BODY_Y = 170;
    private static final int FISH_WIDTH = 100;
    private static final int FISH_HEIGHT = 50;
    private char letter;
    private boolean isCaught;

    // EFFECT: constructs a fish drawing
    public FishDrawing() {
    }

    // setters
    public void setLetter(char letter) {
        this.letter = letter;
        repaint();
    }

    public void setCaught(boolean isCaught) {
        this.isCaught = isCaught;
        repaint();
    }

    @Override
    // MODIFIES: this
    // EFFECTS: paints a fish with fishing rod in a pond
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2D = (Graphics2D) g;
        g2D.scale(1.1, 1.1);

        // set background
        g2D.setColor(new Color(96, 173, 201));
        g2D.fillRect(0, 100, getWidth(), getHeight());


        drawFishingRod(g2D);
        if (!isCaught) {
            drawTail(g2D);
            drawBody(g2D);
            drawBubbles(g2D);
            drawLetter(g2D);
        }
    }

    // MODIFIES: this
    // EFFECTS: draws the fish letter
    public void drawLetter(Graphics2D g2D) {
        if (letter != '\u0000') {
            // Draw letter
            g2D.setColor(Color.BLACK);
            g2D.setFont(new Font("Arial", Font.BOLD, 20));
            g2D.drawString(String.valueOf(letter), 288, 180);
        }
    }

    // MODIFIES: this
    // EFFECTS: draws bubbles
    public void drawBubbles(Graphics2D g2D) {
        g2D.setColor(Color.white);
        g2D.drawOval(282, 165, 22, 22);
        g2D.drawOval(300, 148, 15, 15);
        g2D.drawOval(280, 138, 12, 12);
    }

    // MODIFIES: this
    // EFFECTS: draws fish body
    public void drawBody(Graphics2D g2D) {
        Color startColor = new Color(72, 36, 6, 221);
        Color endColor = new Color(213, 112, 26, 221);
        GradientPaint gradient = new GradientPaint(190, 165, startColor, 190, 215, endColor);
        g2D.setPaint(gradient);
        g2D.fillOval(FISH_BODY_X, FISH_BODY_Y, FISH_WIDTH, FISH_HEIGHT);

        // Draw fish eye
        Random random = new Random();
        Color eyeColor = new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256));
        g2D.setColor(eyeColor);
        g2D.fillOval(255, 182, 10, 10);
    }


    // MODIFIES: this
    // EFFECTS: draws fishtail
    public void drawTail(Graphics2D g2D) {
        g2D.setColor(new Color(213, 112, 26, 221));
        int[] xcoords1 = {80 + 70, 125 + 70, 100 + 70};
        int[] ycoords1 = {165, 195, 195};
        int n1 = 3;
        g2D.fillPolygon(xcoords1, ycoords1, n1);

        g2D.setColor(new Color(213, 112, 26, 221));
        int[] xcoords2 = {80 + 70, 125 + 70, 100 + 70};
        int[] ycoords2 = {225, 195, 195};
        int n2 = 3;
        g2D.fillPolygon(xcoords2, ycoords2, n2);
    }

    // MODIFIES: this
    // EFFECTS: draws fishing rod
    public void drawFishingRod(Graphics2D g2D) {
        g2D.setColor(new Color(91, 64, 27));
        g2D.setStroke(new BasicStroke(5));
        g2D.drawLine(255, 30, 450, 50);

        g2D.setColor(new Color(91, 64, 27));
        g2D.setStroke(new BasicStroke(1));
        g2D.drawLine(255, 30, 255, 150);
    }

}



