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
	 * Check if a player has 5 in row
	 * 
	 * @param player the player to check for
	 * @return true if player has 5 in row, false otherwise
	 */
	public boolean isWinner(int player){
		if(rowCheck() == ME || diagCheck1() == ME) {
			return true; //ej klar
		} else {
			return false;
		} 
	}
	
//	private boolean rowCheck(int x, int y, int player) {
//		int meRow = 0;
//		int otherRow = 0;
//		if(player == ME) {
//			for(int i = 0; i < getSize()-INROW; i++) {
//				if(meRow == INROW) {
//						return true;
//				} else if(gameGrid[i][y] == ME) {
//				    meRow++;
//				    otherRow = 0;
//				} else if (gameGrid[i][y] == OTHER) {
//				    otherRow++;
//				    meRow = 0;
//				}
//			}	
//	} return false;
//	}
	
	private int rowCheck() {
		int meRow = 0;
		int otherRow = 0;
			for(int i = 0; i < getSize()-INROW; i++) {
				for(int j = 0; j < getSize()-INROW; j++) {	
					if(gameGrid[i][j] == ME) {
						meRow++;
						otherRow = 0;
						continue;
					} else if (gameGrid[i][j] == OTHER) {
						otherRow++;
						meRow = 0;
						continue;
					}
				}	
				if(meRow == INROW) {
					return ME;
				} else if(otherRow == INROW) {
					return OTHER;
				} 
			} return 9;
			
	
	}

	
	private int diagCheck1() {
		int meDiag = 0;
		int otherDiag = 0;
			for(int i = 0; i <= getSize()-INROW; i++) {
				for(int j = 0; j <= getSize()-INROW; j++) {
					for(int k = 0; k < INROW; k++) {
						int tempX = j+k;
						int tempY = i+k;
						 if(gameGrid[tempX][tempY] == ME) {
							meDiag++;
							otherDiag = 0;
							continue;
						} else if (gameGrid[tempX][tempY] == OTHER) {
							otherDiag++;
							meDiag = 0;
							continue;
						}
					}
				}		
				if(meDiag == INROW) {
					return ME;
				} else if(otherDiag == INROW) {
					return OTHER;
				} else {
					continue;
				}
		} return 9;
	}
	
}
