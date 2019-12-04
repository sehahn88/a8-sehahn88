package GameOfLifeMVC;

import javax.swing.SwingUtilities;

public class Thread1 extends Thread {

	private boolean done;
	private GameOfLifeController golc;
	
	public Thread1(GameOfLifeController golc) {
		this.golc = golc;
		done = false;
	}
	
	public void halt() {
		done = true;
	}
	
	@Override
	public void run() {
		while (!done) {
			try {Thread.sleep(golc.model.getMillisecondDelay());
			} catch (InterruptedException e) {}
			SwingUtilities.invokeLater(new Updater(golc));
		}
		
	}

}

class Updater implements Runnable {
	private GameOfLifeController golc;
	
	public Updater(GameOfLifeController golc) {
		this.golc = golc;
	}
	
	@Override
	public void run() {
		golc.handleCalculatorViewEvent(new AdvanceEvent());
	}
}
