package lab4.data;


import java.util.Observable;



/**
 * Represents the 2-d game grid
 */

public class GameGrid extends Observable{

	public static int EMPTY = 0;
	public static int ME = 1;
	public static int OTHER = 2;
	private int INROW = 5;
	private int[][] gameGrid;
	/**
	 * Constructor
	 * 
	 * @param size The width/height of the game grid
	 * @return 
	 */
	public GameGrid(int size){
		gameGrid = new int[size][size];
		int i = 0;
		int j = 0;
		while(i < size) {
			while(j < size) {
				gameGrid[i][j] = EMPTY;
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
		return gameGrid[x][y];
	}
	
	/**
	 * Returns the size of the grid
	 * 
	 * @return the grid size
	 */
	public int getSize(){
		return gameGrid.length;
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
		if(gameGrid[x][y]==EMPTY) {
			gameGrid[x][y] = player;
			setChanged();
			notifyObservers();
			return true;
		} else {
			return false;
		}
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
				gameGrid[i][j] = EMPTY;
				j++;
			} i++;
		}
		setChanged();
		notifyObservers();
	}
	
	/**
	 * Checks if a player has 5 in row
	 * 
	 * @param player the player to check for
	 * @param lastX last square placed in x-coord
	 * @param lastY last square placed in y-coord
	 * @return true if player has 5 in row, false otherwise
	 */
	public boolean isWinner(int player, int lastX, int lastY){
		
		return (rowCheckHorizontal(player, lastX, lastY)
				|| rowCheckVertical(player, lastX, lastY)
				|| rowCheckDiag1(player, lastX, lastY)
				|| rowCheckDiag2(player, lastX, lastY));
	}
	
	/*
	 * Checks if player has any horizontal 5 in a row from last placed square
	 */
	private boolean rowCheckHorizontal(int player, int lastX, int lastY) {
		int playerRow = 0;
		
		// When checking horizontal, we are only interested in x-coord
		int start = lastX - (INROW - 1); // starting x
		start = (start < 0) ? 0 : start; // if start is out of bounds
		int end = lastX + (INROW - 1); // ending x
		end = (end >= gameGrid.length)
				? gameGrid.length-1 : end; // if end is out of bounds
		
		for (int i = start; i <= end; i++) {
			if (gameGrid[i][lastY] == player) {
				playerRow++;
			} else {
				playerRow = 0;
			}
			if (playerRow == INROW) {
				return true;
			}
		}
		
		return false;
	}
	
	/*
	 * Checks if player has any vertical 5 in a row from last placed square
	 */
	private boolean rowCheckVertical(int player, int lastX, int lastY) {
		int playerRow = 0;
		
		// When checking horizontal, we are only interested in y-coord
		int start = lastY - (INROW - 1); // starting x
		start = (start < 0) ? 0 : start; // if start is out of bounds
		int end = lastY + (INROW - 1); // ending x
		end = (end >= gameGrid.length)
				? gameGrid.length-1 : end; // if end is out of bounds
		
		for (int i = start; i <= end; i++) {
			if (gameGrid[lastX][i] == player) {
				playerRow++;
			} else {
				playerRow = 0;
			}
			
			if (playerRow == INROW) {
				return true;
			}
		}
		
		return false;
	}
	
	/*
	 * Checks if player has any diagonal (bottom-left to top-right)
	 * 5 in a row from last placed square
	 */
	private boolean rowCheckDiag1(int player, int lastX, int lastY) {
		int x = lastX - (INROW - 1); // starting x-coord
		int y = lastY - (INROW - 1); // starting y-coord
		
		if (x < 0) { // if x is out of bounds
			y -= x; // advance y amount of steps that x is out of bounds with.
			x = 0;
		}
		if (y < 0) { // if y is out of bounds
			x -= y; // advance x amount of steps that y is out of bounds with.
			y = 0;
		}
		
		
		int endX = lastX + (INROW - 1); // ending x-coord
		endX = (endX >= gameGrid.length) ? gameGrid.length-1 : endX;
		int endY = lastY + (INROW - 1); // ending y-coord
		endY = (endY >= gameGrid.length) ? gameGrid.length-1 : endY;
		
		int playerRow = 0;
		
		while (x <= endX && y <= endY) {
			if (gameGrid[x][y] == player) {
				playerRow++;
			} else {
				playerRow = 0;
			}
			
			if (playerRow == INROW) {
				return true;
			}
			
			x++;
			y++;
		}
		
		return false;
	}
	
	/*
	 * Checks if player has any diagonal (top-left to bottom-right)
	 * 5 in a row from last placed square
	 */
	private boolean rowCheckDiag2(int player, int lastX, int lastY) {
		int x = lastX - (INROW - 1); // starting x-coord
		int y = lastY + (INROW - 1); // starting y-coord
		
		if (x < 0) { // if x is out of bounds
			y += x; // advance y amount of steps that x is out of bounds with.
			x = 0;
		}
		if (y >= gameGrid.length) { // if y is out of bounds
			x += (y - (gameGrid.length - 1)); // Advance x like above
			y = gameGrid.length - 1;
		}
		
		int endX = lastX + (INROW - 1); // ending x-coord
		endX = (endX >= gameGrid.length) ? gameGrid.length-1 : endX;
		int endY = lastY - (INROW - 1); // ending y-coord
		endY = (endY < 0) ? 0 : endY;
		
		int playerRow = 0;
		
		while (x <= endX && y >= endY) {
			if (gameGrid[x][y] == player) {
				playerRow++;
			} else {
				playerRow = 0;
			}
			
			if (playerRow == INROW) {
				return true;
			}
			
			x++;
			y--;
		}
		
		return false;
	}

}
