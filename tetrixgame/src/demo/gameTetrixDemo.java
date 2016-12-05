package demo;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JButton;

import javax.swing.JFrame;
import javax.swing.Timer;
//import tetrix.gameData;
//import tetrix02.cTetrixPanel;

import pBoardGame.cBoardPanel;
import pTetrixGame.cBoardGame_Tetrix;

/**
 *
 */
/**
 * @author KTTH
 *
 */
public class gameTetrixDemo extends JFrame {

    cBoardPanel pGame = new cBoardPanel();
    int nNumberPieces = 0;
    Timer tAutoRunDown;
    // brick: TYPE, Dimension, status (moving or died), rPos, cPos
    KeyListener keyControl = null;
    int iDelayMove = 400;
    JButton btnStart = new JButton(">");
 
    public gameTetrixDemo() {
        // init
        setLayout(null);
        setSize(450, 550);

        cBoardGame_Tetrix.initGame();

        add(pGame);
        add( btnStart);
        pGame.setBounds(20, 20, 241, 421);
        btnStart.setBounds(300, 20, 60, 25);
                
        btnStart.addActionListener( new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                //throw new UnsupportedOperationException("Not supported yet.");
                if( tAutoRunDown.isRunning()){
                	tAutoRunDown.stop();
                    btnStart.setText(">");
                }
                else{
  					cBoardGame_Tetrix.newBrick();
  					tAutoRunDown.setInitialDelay(1000);
  					tAutoRunDown.start();
  					pGame.requestFocus();
  					btnStart.setText("||");
                }
            }
        });

        keyControl = new KeyListener() {

            @Override
            public void keyTyped(KeyEvent arg0) {
                // TODO Auto-generated method stub
            }

            @Override
            public void keyReleased(KeyEvent arg0) {
                // TODO Auto-generated method stub
                if (arg0.getKeyCode() == KeyEvent.VK_LEFT) {
                    // move left
                    if (cBoardGame_Tetrix.checkMoveLeft()) {
                    	cBoardGame_Tetrix.moveLeft();                       
                    }
                }
                if (arg0.getKeyCode() == KeyEvent.VK_RIGHT) {
                    // move right
                    if (cBoardGame_Tetrix.checkMoveRight()) {
                    	cBoardGame_Tetrix.moveRight();                    	
                    }
                }
              
                if (arg0.getKeyCode() == KeyEvent.VK_UP) {
                    if (cBoardGame_Tetrix.checkRotate()) {
                    	cBoardGame_Tetrix.rotate();   
                    }
                }
            }

            @Override
            public void keyPressed(KeyEvent arg0) {
                // TODO Auto-generated method stub
             }
        };

        pGame.addKeyListener(keyControl);
        this.addKeyListener(keyControl);
        // pGame.aData[10][3]=2;

      
        tAutoRunDown = new Timer(iDelayMove, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                // TODO Auto-generated method stub
                boolean bl = cBoardGame_Tetrix.checkMoveDown();
                if ( bl ) {
                	// can move more
                	cBoardGame_Tetrix.moveDown();
                } else {
                	// died, could not move
                	int iStoppedX = cBoardGame_Tetrix.getActiveRow();                        
                    int nMoveScore = cBoardGame_Tetrix.clearFullRow();
System.out.println("Collapsed "+ nMoveScore + " Line(s).");
					// scoring and set level here
                    if( cBoardGame_Tetrix.isGameOver()){
                        //end of game
                        System.err.println("Game Overs");
                        tAutoRunDown.stop();
                    } else {
                        nNumberPieces++;
                        cBoardGame_Tetrix.newBrick();
System.out.println("Next: "+ cBoardGame_Tetrix.getNextBrickName());                        
                    }
                }               
            }
        });        
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        gameTetrixDemo mainWindow = new gameTetrixDemo();
        mainWindow.setDefaultCloseOperation(EXIT_ON_CLOSE);
        mainWindow.setVisible(true);
    }
}
