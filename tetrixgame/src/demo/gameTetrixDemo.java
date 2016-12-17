package demo;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
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
    JLabel lblHighScore = new JLabel("High Score ");
    JLabel lblimage = new JLabel("");
    JLabel lblNEXT = new JLabel("NEXT");
    JLabel lblhighscore = new JLabel("");
    
    int score =0;
    
    //new Images
    ImageIcon imgO = new ImageIcon("images/O.png");
    ImageIcon imgS = new ImageIcon("images/S.jpg");
    ImageIcon imgZ = new ImageIcon("images/Z.jpg");
    ImageIcon imgJ = new ImageIcon("images/J.jpg");
    ImageIcon imgI = new ImageIcon("images/I.jpg");
    ImageIcon imgT = new ImageIcon("images/T.jpg");
    ImageIcon imgL = new ImageIcon("images/L.jpg");

    JTextArea txtscore2 = new JTextArea("");
    // brick: TYPE, Dimension, status (moving or died), rPos, cPos
    KeyListener keyControl = null;
    int iDelayMove = 350 ;
   
    JButton btnStart = new JButton("Resume");
    JButton btnStop = new JButton("Stop");
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
        add(btnStop);
        add(lblimage);
        add(lblNEXT);
        add(lblHighScore);
        add(lblhighscore);

        pGame.setBounds(20, 20, 241, 421);
        
        btnNewGame.setBounds(300, 20, 80, 25);
        btnStart.setBounds(300, 50, 80, 25);
        btnStop.setBounds(300, 80, 80, 25);
        
        //set margin for button
        Insets margin = new Insets(1, 1, 1, 1);
        btnNewGame.setMargin(margin);
        btnStart.setMargin(margin);
        btnStop.setMargin(margin);
        
        lblscore.setBounds(280, 110, 50, 30);
        lblHighScore.setBounds(300, 300, 120, 30);
        lblhighscore.setBounds(335, 340, 120, 30);
        lblimage.setBounds(320, 180, 80, 80);
        lblNEXT.setBounds(320, 155, 80, 30);


        txtscore2.setBounds(335, 115, 70, 20);
        txtscore2.setEditable(false);
        Font f = txtscore2.getFont();
        txtscore2.setFont(new Font(f.getName(), Font.BOLD, f.getSize() + 4));
        lblNEXT.setFont(new Font(f.getName(), Font.BOLD, f.getSize() + 8));
        lblHighScore.setFont(new Font(f.getName(), Font.BOLD, f.getSize() + 7));
        lblhighscore.setFont(new Font(f.getName(), Font.BOLD, f.getSize() + 5));
        btnStop.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				tAutoRunDown.stop();
				cBoardGame_Tetrix.initGame();
				score=0;
			}
		});
        
       
        
        btnNewGame.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				tAutoRunDown.start();
				cBoardGame_Tetrix.initGame();
				pGame.requestFocus();
				tAutoRunDown.setInitialDelay(1000);
				score =0;


				
			}
		});
       
        btnStart.addActionListener( new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                //throw new UnsupportedOperationException("Not supported yet.");
                if( tAutoRunDown.isRunning()){
                	tAutoRunDown.stop();
                    btnStart.setText("Continues");
                }
                else{
  					
  					tAutoRunDown.setInitialDelay(1000);
  					tAutoRunDown.start();
  					pGame.requestFocus();
  					btnStart.setText("Resume");
  					
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
//                    String s1;
//                    BufferedReader bfrIn = new BufferedReader(new InputStreamReader(System.in));
//                    try {
//						BufferedWriter bfrOut = new BufferedWriter(new FileWriter("Data/highScore.txt"));
//						do{
//							s1 = String.valueOf(score);
//							s1=bfrIn.readLine();
//							bfrOut.write(s1);
//							bfrOut.newLine();
//						}while(!s1.equalsIgnoreCase("eof"));
//				        System.out.print("Write Successful");
//				       
//                    } catch(IOException e1) {
//                        System.out.println("Error during reading/writing");
//                    }
					
					txtscore2.setText(""+score);
                    if( cBoardGame_Tetrix.isGameOver()){
                        //end of game
                        System.err.println("Game Overs");
                        tAutoRunDown.stop();

                    } else {
                        nNumberPieces++;
                        cBoardGame_Tetrix.newBrick();
                        System.out.println("Next: "+ cBoardGame_Tetrix.getNextBrickName());   
					String s=cBoardGame_Tetrix.getNextBrickName();
					 
					if (s=="O"){
						lblimage.setIcon(imgO);
					}else{
					if (s=="J"){
						lblimage.setIcon(imgJ);
					}else{
					if (s=="I"){
						lblimage.setIcon(imgI);
					}else{
					if (s=="L"){
						lblimage.setIcon(imgL);
					}else{
					if (s=="T"){
						lblimage.setIcon(imgT);
					}else{
					if (s=="S"){
						lblimage.setIcon(imgS);
					}else{
					if (s=="Z"){
						lblimage.setIcon(imgZ);
					}
					}}}}}
					}
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
    	g.drawImage(imgO.getImage(), 300, 100, 100, 100, null);
    	
    	
    }
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        gameTetrixDemo mainWindow = new gameTetrixDemo();
        mainWindow.setDefaultCloseOperation(EXIT_ON_CLOSE);
        mainWindow.setVisible(true);
    }
}
