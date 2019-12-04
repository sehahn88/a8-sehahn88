package GameOfLifeMVC;

import javax.swing.JFrame;

public class Main {
	public static void main(String[] args) {
		GameOfLifeModel model = new GameOfLifeModel();
		GameOfLifeView view = new GameOfLifeView();
		GameOfLifeController controller = new GameOfLifeController(model, view);
		
		JFrame main_frame = new JFrame();
		main_frame.setTitle("Game of Life");
		main_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		main_frame.setContentPane(view);

		main_frame.pack();
		main_frame.setVisible(true);
	}
}
