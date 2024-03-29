package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Date;
import java.util.Random;

import model.*;

// represents a game GUI
// references CardLayoutDemo
public class GameGUI {


//    public GameGUI() {
//        newGame();
//    }

//    private void newGame() {
//        //boolean playAgain = true;
//        game = new Game();
//        playRound(MAX_TRIES);
//
//    }
//
//
//
//    private void playRound(int maxTries) {
//        Random random = new Random();
//        Date date = new Date();
//        fishesCaught.setDateCaught(date.toString());
//        for (int i = 0; i < maxTries; i++) {
//            int fishAvail = game.getFishesTotal().getFishList().size();
//            int randomIndex = random.nextInt(fishAvail);
//            Fish currentFish = game.getFishesTotal().getFishList().get(randomIndex);
//
//            // print letter to fish
//            char letter = currentFish.getLetter();
//            fishDrawing.setLetter(letter);
//            newGamePanel.revalidate();
//            newGamePanel.repaint();
//
//            newGamePanel.addKeyListener(new KeyAdapter() {
//                @Override
//                public void keyTyped(KeyEvent e) {
//                    String typedKey = String.valueOf(e.getKeyChar());
//                    if (typedKey.equalsIgnoreCase(String.valueOf(letter))) {
//                        //new FishCaughtDrawing(); // show that fish caught
//                        fishesCaught.addFish(currentFish);
//                        game.getFishesTotal().getFishList().remove(randomIndex);
//                        if (currentFish.isLargest()) {
//                            System.out.println("Largest fish caught!");
//                        }
//                    } else {
//                        //new FishGoneDrawing();
//                        System.out.println("fish swam away");
//                    }
//                }
//            });
//        }
//    }


    private static void createAndShowGUI() {
        //set up the window
        JFrame frame = new JFrame("Fishing Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(490, 400));
        frame.add(new ParentPanel());

        //display the window
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}