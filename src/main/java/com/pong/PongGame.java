package com.pong;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class PongGame extends JPanel implements MouseMotionListener {
    static int width = 640; // this is the amount of pixels to the right side of the screen
    static int height = 480; // this is the amount of pixels to the top of the screen.
    private int userMouseY;
    private Paddle aiPaddle;
    private int playerScore;
    private int aiScore;
    private Ball ball;
    private Paddle pPaddle;
    private Speedup spZone;
    private SlowDown slZone;
    private Wall wall;
    // step 1 add any other private variables you may need to play the game.

    public PongGame() {

        aiPaddle = new Paddle(610, 240, 50, 9, Color.WHITE);
        JLabel pScore = new JLabel("0");
        JLabel aiScore = new JLabel("0");
        pScore.setBounds(280, 440, 20, 20);
        aiScore.setBounds(360, 440, 20, 20);
        pScore.setVisible(true);
        aiScore.setVisible(true);
        userMouseY = 0;
        addMouseMotionListener(this);
        ball = new Ball(320, 200, 9,4, Color.RED, 10);

        //create any other objects necessary to play the game.
        pPaddle = new Paddle(30, 240, 50, 9, Color.RED);
        spZone = new Speedup(252,0,200,50);
        slZone = new SlowDown(312,300,200,50);
        wall = new Wall(320,185,100,10,Color.ORANGE);
    }

    // precondition: None
    // postcondition: returns playerScore
    public int getPlayerScore() {
        return playerScore;
    }

    // precondition: None
    // postcondition: returns aiScore
    public int getAiScore() {
        return aiScore;
    }

    //precondition: All visual components are initialized, non-null, objects 
    //postcondition: A frame of the game is drawn onto the screen.
    public void paintComponent(Graphics g) {

        g.setColor(Color.BLACK);
        g.fillRect(0, 0, width, height);

        g.setColor(Color.WHITE);
        g.drawString("The Score is User:" + playerScore + " vs Ai:" + aiScore, 240, 20);
        ball.draw(g);
        aiPaddle.draw(g);
        
        //call the "draw" function of any visual component you'd like to show up on the screen.
        pPaddle.draw(g);
        spZone.draw(g);
        slZone.draw(g);
        wall.draw(g);
    }


    // precondition: all required visual components are intialized to non-null
    // values
    // postcondition: one frame of the game is "played"
    public void gameLogic() {
        //add commands here to make the game play propperly
        
        ball.moveBall();
        pPaddle.moveY(userMouseY);
        aiPaddle.moveY(ball.getY());

        if (slZone.isTouching(ball)&&ball.getChangeX()>5){
            ball.setChangeX((ball.getChangeX()*0.85));

        }
        if (spZone.isTouching(ball)&&ball.getChangeX()<22){
            ball.setChangeX((ball.getChangeX()*1.15));

        }
        if (aiPaddle.isTouching(ball)||pPaddle.isTouching(ball)||wall.isTouching(ball)) {
           ball.reverseX();
        }
        if (ball.getY()>=480||ball.getY()<=0){
            ball.reverseY();
        }
        pointScored();

    }

    // precondition: ball is a non-null object that exists in the world
    // postcondition: determines if either ai or the player score needs to be
    // updated and re-sets the ball
    // the player scores if the ball moves off the right edge of the screen (640
    // pixels) and the ai scores
    // if the ball goes off the left edge (0)
    public void pointScored() {

        if (ball.getX()>=640){
            ball.setX(320);
            ball.sety(240);
            playerScore++;

        }
        else if (ball.getX()<=0){
            ball.setX(320);
            ball.sety(240);
            aiScore++;
        }
    }

    // you do not need to edit the below methods, but please do not remove them as
    // they are required for the program to run.
    @Override
    public void mouseDragged(MouseEvent e) {
        //  Auto-generated method stub

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        //  Auto-generated method stub
        userMouseY = e.getY();
    }

}
