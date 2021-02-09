import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

//TODO Put your name here.

public class MineSweeper extends JPanel {
    private Square[][] board;

    public static final int SIZE = 30;
    public static final int NUM_MINES = 30;

    public MineSweeper(int width, int height) {
        setSize(width, height);

        board = new Square[15][15];
        for(int mineCount=0; mineCount<NUM_MINES; mineCount++) {
        	int row = (int)(Math.random()*15);
        	int col = (int)(Math.random()*15);
        	if(board[row][col] != null) {
        		mineCount--;
        	}else {
        		board[row][col] = new Square(true, row, col, board);
        	}
        }
        
        
        for (int i = 0; i < board.length; i++){
            for (int j = 0; j < board[0].length; j++) {
                if(board[i][j] == null) {
                	board[i][j] = new Square(false, i, j, board);
                }
            }
        }
        //Here is a good spot to calc each Square's neighborMines (after all squares are initialized)
        //Maybe write a method in Square that finds how many
        //mines are around it, and call that method on each Square here.
        for (int i = 0; i < board.length; i++){
            for (int j = 0; j < board[0].length; j++) {
            	board[i][j].calcNeighborMines();
            }
        }

        setupMouseListener();
    }


    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        for (int r = 0; r < board.length; r++)
            for (int c = 0; c < board[0].length; c++) {
                board[r][c].draw(g2);
            }
    }

    public void setupMouseListener(){
        addMouseListener(new MouseListener() {
            @Override
            public void mousePressed(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();

                int r = y / SIZE;
                int c = x / SIZE;
                
                
                board[r][c].revealCell();
                if(e.getButton() == MouseEvent.BUTTON1) {
                    	board[r][c].click();
                }else if(SwingUtilities.isRightMouseButton(e)) {
                    	board[r][c].click(1);
                    }
                repaint();
            }

            @Override
            public void mouseClicked(MouseEvent e) {
            	
            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
    }

    
    //sets ups the panel and frame.  Probably not much to modify here.
    public static void main(String[] args) {
        JFrame window = new JFrame("Minesweeper");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setBounds(0, 0, 600, 600 + 22); //(x, y, w, h) 22 due to title bar.

        MineSweeper panel = new MineSweeper(600, 600);

        panel.setFocusable(true);
        panel.grabFocus();

        window.add(panel);
        window.setVisible(true);
        window.setResizable(false);
    }

}