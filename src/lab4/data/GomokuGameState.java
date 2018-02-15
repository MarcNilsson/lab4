/*
 * Created on 2007 feb 8
 */
package lab4.data;

import java.util.Observable;
import java.util.Observer;

import lab4.client.GomokuClient;
import lab4.gui.GamePanel;


/**
 * Represents the state of a game
 */

public class GomokuGameState extends Observable implements Observer{


	// Game variables
	public final int DEFAULT_SIZE = 15;
	private GameGrid grid;
	
    //Possible game states
	private final int NOT_STARTED = 0;
	private int currentState;
	private static int MY_TURN = 0;
	private static int OTHER_TURN = 0;
	private static int FINISHED = 0;
	
	private GomokuClient client;
	
	private String message = "asd";
	
	/**
	 * The constructor
	 * 
	 * @param gc The client used to communicate with the other player
	 */
	public GomokuGameState(GomokuClient gc){
		client = gc;
		client.addObserver(this);
		gc.setGameState(this);
		currentState = NOT_STARTED;
		grid = new GameGrid(DEFAULT_SIZE);
	}
	

	/**
	 * Returns the message string
	 * 
	 * @return the message string
	 */
	public String getMessageString(){
		return message;
	}
	
	/**
	 * Returns the game grid
	 * 
	 * @return the game grid
	 */
	public GameGrid getGameGrid(){
		return grid;
	}

	/**
	 * This player makes a move at a specified location
	 * 
	 * @param x the x coordinate
	 * @param y the y coordinate
	 */
	public void move(int x, int y){
		x = x/GamePanel.UNIT_SIZE;
		y = y/GamePanel.UNIT_SIZE;
		if (currentState == NOT_STARTED) {
			message = "The game is not started.";
			System.out.println("NOT_STARTED x: "+x+" y: "+y);
			System.out.println(grid.getLocation(x, y));
		} else if (currentState == OTHER_TURN) {
			message = "It is not your turn yet.";
			System.out.println("OTHER_TURN x: "+x+" y: "+y);
			System.out.println(grid.getLocation(x, y));
		} else {
			System.out.println("ANNARS x: "+x+" y: "+y);
			System.out.println(grid.getLocation(x, y));
			grid.move(x, y, grid.ME);

			
		}
	}
	
	/**
	 * Starts a new game with the current client
	 */
	public void newGame(){}
	
	/**
	 * Other player has requested a new game, so the 
	 * game state is changed accordingly
	 */
	public void receivedNewGame(){}
	
	/**
	 * The connection to the other player is lost, 
	 * so the game is interrupted
	 */
	public void otherGuyLeft(){}
	
	/**
	 * The player disconnects from the client
	 */
	public void disconnect(){}
	
	/**
	 * The player receives a move from the other player
	 * 
	 * @param x The x coordinate of the move
	 * @param y The y coordinate of the move
	 */
	public void receivedMove(int x, int y){
		
	}
	
	public void update(Observable o, Object arg) {
		
		switch(client.getConnectionStatus()){
		case GomokuClient.CLIENT:
			message = "Game started, it is your turn!";
			currentState = MY_TURN;
			break;
		case GomokuClient.SERVER:
			message = "Game started, waiting for other player...";
			currentState = OTHER_TURN;
			break;
		}
		setChanged();
		notifyObservers();
		
		
	}
	
}
