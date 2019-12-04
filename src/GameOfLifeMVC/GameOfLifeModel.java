package GameOfLifeMVC;

import java.util.*;

public class GameOfLifeModel {

	private JSpotBoard board;
	private int boardSize;
	private List<GameOfLifeObserver> observers;
	private int birthThreshMin;
	private int birthThreshMax;
	private int surviveThreshMin;
	private int surviveThreshMax;
	private boolean torusMode;
	private int millisecondDelay;
	
	public GameOfLifeModel() {
		
		boardSize = 10; 
		board = new JSpotBoard(boardSize,boardSize);
		
		observers = new ArrayList<GameOfLifeObserver>();
		
		birthThreshMin=3;
		birthThreshMax=3;
		surviveThreshMin=2;
		surviveThreshMax=3;
		torusMode=false;
		millisecondDelay=100;
	}
	
	public JSpotBoard getBoard() {
		return board;
	}
	
	public void setBoard(JSpotBoard jsb) {
		this.board = jsb;
	}
	
	public Spot getSpotAt(int x, int y) {
		return board.getSpotAt(x, y);
	}
	
	public void toggleSpotAt(int x, int y) {
		board.getSpotAt(x, y).toggleSpot();
	}
	
	public int getBoardSize() {return boardSize;}
	public int getBirthThreshMin() {return birthThreshMin;}
	public int getBirthThreshMax() {return birthThreshMax;}
	public int getSurviveThreshMin() {return surviveThreshMin;}
	public int getSurviveThreshMax() {return surviveThreshMax;}
	public boolean getTorusMode() {return torusMode;} 
	public int getMillisecondDelay() {return millisecondDelay;}
	
	public void setBoardSize(int size) {boardSize=size;}
	public void setBirthThreshMin(int btm) {birthThreshMin=btm;}
	public void setBirthThreshMax(int btm) {birthThreshMax=btm;}
	public void setSurviveThreshMin(int stm) {surviveThreshMin=stm;}
	public void setSurviveThreshMax(int stm) {surviveThreshMax=stm;}
	public void setTorusMode(boolean b) {torusMode=b;}
	public void setMillisecondDelay(int msDelay) {millisecondDelay=msDelay;}
	
	
	public void addObserver(GameOfLifeObserver o) {
		observers.add(o);
	}
	
	public void removeObserver(GameOfLifeObserver o) {
		observers.remove(o);
	}
	
	private void notifyObservers(JSpotBoard jsb) {
		for (GameOfLifeObserver o : observers) {
			o.update(this, jsb);
		}
	}
	
	
}
