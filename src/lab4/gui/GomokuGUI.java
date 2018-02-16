package lab4.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Observable;
import java.util.Observer;

import javax.swing.*;

import lab4.client.GomokuClient;
import lab4.data.GameGrid;
import lab4.data.GomokuGameState;
import lab4.gui.GamePanel;

/*
 * The GUI class
 */

public class GomokuGUI implements Observer{

	private GomokuClient client;
	private GomokuGameState gamestate;
	
	JButton connectButton;
	JButton newGameButton;
	JButton disconnectButton;
	JLabel messageLabel;
	GamePanel gameGridPanel;
	GameGrid grid;
	
	/**
	 * The constructor
	 * 
	 * @param g   The game state that the GUI will visualize
	 * @param c   The client that is responsible for the communication
	 */
	public GomokuGUI(GomokuGameState g, GomokuClient c){
		
		this.client = c;
		this.gamestate = g;
		client.addObserver(this);
		gamestate.addObserver(this);

		
		grid = gamestate.getGameGrid();
		gameGridPanel = new GamePanel(grid);
		gameGridPanel.setVisible(true);
		connectButton = new JButton("Connect");
		newGameButton= new JButton("New Game");
		disconnectButton = new JButton("Disconnect");
		messageLabel = new JLabel();
		JFrame window = new JFrame();
		messageLabel.setText(gamestate.getMessageString());
		newGameButton.setEnabled(false);
		disconnectButton.setEnabled(false);
		
		//Box menu = Box.createHorizontalBox();
		Box menu = new Box(BoxLayout.LINE_AXIS);

		
		window.setVisible(true);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setSize(gamestate.DEFAULT_SIZE*gameGridPanel.UNIT_SIZE+100, gamestate.DEFAULT_SIZE*gameGridPanel.UNIT_SIZE+180);		
		
		menu.add(connectButton);
		menu.add(newGameButton);
		menu.add(disconnectButton);
		window.add(messageLabel, BorderLayout.CENTER);
		window.add(menu, BorderLayout.SOUTH);
		window.add(gameGridPanel, BorderLayout.NORTH);
		window.pack();
		window.setResizable(false);

		
		connectButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				new ConnectionWindow(client);	
			}
			
		});
		
		newGameButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				gamestate.newGame();	
				
			}
			
		});
		
		disconnectButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				gamestate.disconnect();	
				
			}
			
		});
		
		gameGridPanel.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				g.move(e.getX(), e.getY());
			}

		});		
		
		
	}
	
	
	public void update(Observable arg0, Object arg1) {
		
		// Update the buttons if the connection status has changed
		if(arg0 == client){
			if(client.getConnectionStatus() == GomokuClient.UNCONNECTED){
				connectButton.setEnabled(true);
				newGameButton.setEnabled(false);
				disconnectButton.setEnabled(false);
			}else{
				connectButton.setEnabled(false);
				newGameButton.setEnabled(true);
				disconnectButton.setEnabled(true);
			}
		}
		
		// Update the status text if the gamestate has changed
		if(arg0 == gamestate){
			messageLabel.setText(gamestate.getMessageString());
		}
		
	}
	
}
