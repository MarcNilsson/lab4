package lab4.data;

import java.awt.Color;
import java.util.Observable;

/**
 * Represents the 2-d game grid
 */

public class GameGrid extends Observable{

	public static int EMPTY = 0;
	public static int ME = 1;
	public static int OTHER = 2;
	private int INROW = 5;
	private int[][] grid;
	/**
	 * Constructor
	 * 
	 * @param size The width/height of the game grid
	 * @return 
	 */
	public GameGrid(int size){
		grid = new int[size][size];
		int i = 0;
		int j = 0;
		while(i < size) {
			while(j < size) {
				grid[i][j] = EMPTY;
				j++;
			}
			i++;
		} 
	} 
	
	/**
	 * Reads a location of the grid
	 * 
	 * @param x The x coordinate
	 * @param y The y coordinate
	 * @return the value of the specified location
	 */
	public int getLocation(int x, int y){
		return grid[x][y];
	}
	
	/**
	 * Returns the size of the grid
	 * 
	 * @return the grid size
	 */
	public int getSize(){
		return grid.length;
	}
	
	/**
	 * Enters a move in the game grid
	 * 
	 * @param x the x position
	 * @param y the y position
	 * @param player
	 * @return true if the insertion worked, false otherwise
	 */
	public boolean move(int x, int y, int player){
		
		return true; //ej klar
	}
	
	/**
	 * Clears the grid of pieces
	 */
	public void clearGrid(){
		int i = 0;
		int j = 0;
		int row = getSize();
		int column = getSize();
		
		while(i < row) {
			j = 0;
			while(j < column) {
				grid[i][j] = EMPTY;
				j++;
			} i++;
		}
	}
	
	/**
	 * Check if a player has 5 in row
	 * 
	 * @param player the player to check for
	 * @return true if player has 5 in row, false otherwise
	 */
	public boolean isWinner(int player){
		return true; //ej klar
	}
	
	
}