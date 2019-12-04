package GameOfLifeMVC;

public class Thread1 extends Thread implements Runnable {

	private boolean done;
	private GameOfLifeController golc;
	
	public Thread1(GameOfLifeController golc) {
		this.golc = golc;
		done = false;
	}
	
	public void halt() {
		done = true;
	}
	public void begin() {
		done = false;
	}
	
	@Override
	public void run() {
		while (!done) {
			try {Thread.sleep(golc.model.getMillisecondDelay());
			} catch (InterruptedException e) {}
			golc.handleCalculatorViewEvent(new AdvanceEvent());
		}
		
	}

}
