package lab4.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;


import lab4.data.GameGrid;

/**
 * A panel providing a graphical view of the game board
 */

public class GamePanel extends JPanel implements Observer{

	public final int UNIT_SIZE = 20;
	public GameGrid grid;
	private Graphics2D g2;
	
	/**
	 * The constructor
	 * 
	 * @param grid The grid that is to be displayed
	 */
	public GamePanel(GameGrid grid){
		this.grid = grid;
		grid.addObserver(this);
		Dimension d = new Dimension(grid.getSize()*UNIT_SIZE+1, grid.getSize()*UNIT_SIZE+1);
		this.setMinimumSize(d);
		this.setPreferredSize(d);
		this.setBackground(Color.WHITE);
		this.setVisible(true);
	
	}

	/**
	 * Returns a grid position given pixel coordinates
	 * of the panel
	 * 
	 * @param x the x coordinates
	 * @param y the y coordinates
	 * @return an integer array containing the [x, y] grid position
	 */
	public int[] getGridPosition(int x, int y){
		int[] temp = {x, y};
		System.out.println("Mouse click on "+temp);
		return temp;
	}
	
	public void update(Observable arg0, Object arg1) {
		this.repaint();
	}
	
	
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		int i = 0;
		int j = 0;
		int row = grid.getSize();
		int column = grid.getSize();
		
		while(i < row) {
			j = 0;
			while(j < column) {
				if(grid.getLocation(i, j) == GameGrid.ME) {
					drawSquare(i*UNIT_SIZE, j*UNIT_SIZE, UNIT_SIZE, UNIT_SIZE, Color.BLUE, g);
				} else if (grid.getLocation(i, j) == GameGrid.OTHER) {
					drawSquare(i*UNIT_SIZE, j*UNIT_SIZE, UNIT_SIZE, UNIT_SIZE, Color.RED, g);
				} else {
					drawSquare(i*UNIT_SIZE, j*UNIT_SIZE, UNIT_SIZE, UNIT_SIZE, Color.BLACK, g);
				}
				j++;
			} i++;
		} 
		
		
		
	}
	
	//Skapar en rektangel med given storlek och färg.
	private void drawSquare(int x, int y, int width, int height, Color color, Graphics g) {
		g.setColor(color);
		g.drawRect(x, y, width, height);
	}
	
}
