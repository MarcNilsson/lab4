package lab4;
import lab4.client.GomokuClient;
import lab4.data.GomokuGameState;
import lab4.gui.GomokuGUI;

public class GomokuMain {

	public static void main(String[] args) {
		//Online
		/*int portNumber = (args.length<1) ? 9001 : Integer.parseInt(args[0]);
		GomokuClient GC = new GomokuClient(portNumber);
		GomokuGameState GS = new GomokuGameState(GC);
		GomokuGUI GUI = new GomokuGUI(GS,GC);*/

		
		int portNumberOne = 5000;
		int portNumberTwo = 5001;
		GomokuClient ClientEtt = new GomokuClient(portNumberOne);
		GomokuClient ClientTvå = new GomokuClient(portNumberTwo);
		GomokuGameState StateEtt = new GomokuGameState(ClientEtt);
		GomokuGameState StateTvå = new GomokuGameState(ClientTvå);
		GomokuGUI GUIEtt = new GomokuGUI(StateEtt,ClientEtt);
		GomokuGUI GUITvå = new GomokuGUI(StateTvå,ClientTvå);
		


	}

}
