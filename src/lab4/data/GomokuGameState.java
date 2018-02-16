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
	public final int DEFAULT_SIZE = 19;
	private GameGrid gameGrid;
	
    //Possible game states
	private final int NOT_STARTED = 0;
	private int currentState;
	private static int MY_TURN = 1;
	private static int OTHER_TURN = 2;
	private static int FINISHED = 3;
	
	private GomokuClient client;
	
	private String message = "Welcome to Global Gomoku!";
	
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
		gameGrid = new GameGrid(DEFAULT_SIZE);
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
		return gameGrid;
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
			setChanged();
			notifyObservers();
			return;
		} else if (currentState == OTHER_TURN) {
			message = "It is not your turn yet.";
			System.out.println("OTHER_TURN x: "+x+" y: "+y);
			setChanged();
			notifyObservers();
			return;
		} else if (currentState == FINISHED) {
			message = "The game is finished.";
			System.out.println("FINISHED x: "+x+" y: "+y);
			setChanged();
			notifyObservers();
			return;
			
		} else if (currentState == MY_TURN){
			System.out.println("ANNARS x: "+x+" y: "+y);
			receivedMove(x, y);
			gameGrid.move(x, y, gameGrid.ME);
			message = "GameState.move hej";
			client.sendMoveMessage(x, y);
			currentState = OTHER_TURN;
			receivedMove(x, y);
			setChanged();
			notifyObservers();
			return;

			
		}
	}
	
	/**
	 * Starts a new game with the current client
	 */
	public void newGame(){
		currentState = OTHER_TURN;
		gameGrid.clearGrid();
		message = "New game msg.";
		client.sendNewGameMessage();
		setChanged();
		notifyObservers();
	}
	
	/**
	 * Other player has requested a new game, so the 
	 * game state is changed accordingly
	 */
	public void receivedNewGame(){
		currentState = MY_TURN;
		gameGrid.clearGrid();
		setChanged();
		notifyObservers();
	}
	
	/**
	 * The connection to the other player is lost, 
	 * so the game is interrupted
	 */
	public void otherGuyLeft(){
		currentState = NOT_STARTED;
		message = "The other player left.";
		gameGrid.clearGrid();
		setChanged();
		notifyObservers();
	}
	
	/**
	 * The player disconnects from the client
	 */
	public void disconnect(){
		currentState = NOT_STARTED;
		message = "You left the game.";
		gameGrid.clearGrid();
		client.disconnect();
		setChanged();
		notifyObservers();
	}
	
	/**
	 * The player receives a move from the other player
	 * 
	 * @param x The x coordinate of the move
	 * @param y The y coordinate of the move
	 */
	public void receivedMove(int x, int y){
		gameGrid.move(x,y,GameGrid.OTHER);
		currentState = MY_TURN;
		message = "min tur?";
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
