import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.*;

public class Square {

    private boolean isMine, isRevealed, isFlag;
    private int neighborMines;
    private int r, c;
    private Square[][] board;

    public Square(boolean isMine, int r, int c, Square[][] board) {
        this.isMine = isMine;
        this.r = r;
        this.c = c;
        this.isRevealed = false;
        this.isFlag = false;
        this.board = board;
        neighborMines = 0;  //you'll want to code this properly.
                            //probably a numNeighbors method - probably similar to Life...
    }

    public void calcNeighborMines(){
    	int count = 0;
    	if(isMine) {
    		neighborMines = 1;
    		return;
    	}
    	for(int row=r-1; row<=r+1; row++) {
    		if(row<0 || row>14) continue;
    		for(int col=c-1; col<=c+1; col++) {
    			if(col<0 || col>14) continue;
    			if(board[row][col].isMine) count++;
    		}
    	}
    	neighborMines = count;
    }
    
    public void revealCell() {
    	if(isRevealed) return;
    	isRevealed = true;
    	if(neighborMines==0) {
    		for(int row=r-1; row<=r+1; row++) {
        		if(row<0 || row>14) continue;
        		for(int col=c-1; col<=c+1; col++) {
        			if(col<0 || col>14) continue;
        			board[row][col].revealCell();
        		}
        	}
    	}
    }
    
    public void draw(Graphics2D g2){
        int size = MineSweeper.SIZE;
        ArrayList<Color> colors = new ArrayList<Color>();
        colors.add(Color.BLUE);
        colors.add(Color.GREEN);
        colors.add(Color.MAGENTA);
        colors.add(Color.ORANGE);
        colors.add(Color.PINK);
        colors.add(Color.CYAN);
        colors.add(Color.YELLOW);
        colors.add(Color.GRAY);
        
        g2.drawString("# of Mines", 455, 10);
        g2.drawString("Red = Mine", 455, 25);
        g2.drawString("White = Flag", 455, 40);
        g2.drawString("Blue = 0", 455, 55);
        g2.drawString("Green = 1", 455, 70);
        g2.drawString("Magenta = 2", 455, 85);
        g2.drawString("Orange = 3", 455, 100);
        g2.drawString("Pink = 4", 455, 115);
        g2.drawString("Cyan = 5", 455, 130);
        g2.drawString("Yellow = 6", 455, 145);
        g2.drawString("Gray = 7", 455, 160);
        g2.drawString("Right click to", 455, 180);
        g2.drawString("add/remove flags", 455, 195);
        if (isRevealed) {
            if(isFlag) {
                g2.setColor(Color.WHITE);
            }else if(isMine) {
            	g2.setColor(Color.RED);
            	endGame(g2);
            }
            else{
            	g2.setColor(colors.get(neighborMines));
            }
            g2.fillRect(c * size, r * size, size, size);
        }else{
            g2.setColor(Color.GRAY);
            g2.fillRect(c * size, r * size, size, size);
        }
        g2.setColor(Color.BLACK);
        g2.drawRect(c * size, r * size, size, size);

    }

    public void endGame(Graphics2D g2) {
    	for(int row=0; row<board.length; row++) {
    		for(int col=0; col<board[0].length; col++) {
    			Square current = board[row][col];
    			current.isFlag = false;
    			if(current.isMine) current.isRevealed = true;
    		}
    	}
    	Font prevFont = g2.getFont();
    	g2.setFont(new Font("TimesRoman", Font.PLAIN, 50));
    	g2.drawString("GAME OVER", 150, 500);
    	g2.setFont(prevFont);
    }
    
    
    public void click(){
    	isRevealed = true;
    }
    
    public boolean isRevealed() {
    	return isRevealed;
    }
    
    private int flagCount = 0;
    public void click(int i) {
    	isFlag = true;
    	flagCount++;
    	if(flagCount==2) {
    		isFlag = false;
    		isRevealed = false;
    		flagCount = 0;
    	}
    }

}