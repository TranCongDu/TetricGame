package demo;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JButton;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
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
    JLabel lblscore = new JLabel("Score : ");
    int score =0;
    
    
    
    JTextArea txtscore2 = new JTextArea("");
    // brick: TYPE, Dimension, status (moving or died), rPos, cPos
    KeyListener keyControl = null;
    int iDelayMove = 400;
    JButton btnStart = new JButton(">");
    JButton btnNewGame = new JButton("New Game");
 
    public gameTetrixDemo() {
        // init
        setLayout(null);
        setSize(450, 550);
       
        cBoardGame_Tetrix.initGame();
        add(pGame);
        add(btnNewGame);
        add(lblscore);
        add( btnStart);
        add(txtscore2);
        pGame.setBounds(20, 20, 241, 421);
        
        btnNewGame.setBounds(280, 20, 80, 25);
        btnStart.setBounds(370, 20, 60, 25);
        Insets margin = new Insets(1, 1, 1, 1);
        btnNewGame.setMargin(margin);
        lblscore.setBounds(280, 60, 50, 30);
        txtscore2.setBounds(335, 65, 70, 20);
        txtscore2.setEditable(false);
        Font f = txtscore2.getFont();
        txtscore2.setFont(new Font(f.getName(), Font.BOLD, f.getSize() + 4));
        btnNewGame.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				tAutoRunDown.start();
				cBoardGame_Tetrix.initGame();
				 
			}
		});
       
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
					score = score + nMoveScore;
//					txtscore.setText(""+nMoveScore);
//					String s1 = txtscore.getText();
//					int number = Integer.parseInt(s1);
					//System.out.println(score);
					// scoring and set level here
					txtscore2.setText(""+score);
                    if( cBoardGame_Tetrix.isGameOver()){
                        //end of game
                        System.err.println("Game Overs");
                        tAutoRunDown.stop();
                        score = 0;
                    } else {
                        nNumberPieces++;
                        cBoardGame_Tetrix.newBrick();
System.out.println("Next: "+ cBoardGame_Tetrix.getNextBrickName());   
					String s=cBoardGame_Tetrix.getNextBrickName();
					
                    }
                }               
            }
        });        
    }

    /**
     * @param args
     */
    public void paintComponent(Graphics g) {
    	
    	super.paintComponents(g);
    	
    	
    	
    }
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        gameTetrixDemo mainWindow = new gameTetrixDemo();
        mainWindow.setDefaultCloseOperation(EXIT_ON_CLOSE);
        mainWindow.setVisible(true);
    }
}
