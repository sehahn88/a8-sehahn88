package GameOfLifeMVC;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameOfLifeController implements GameOfLifeObserver, GameOfLifeViewListener {
	
	GameOfLifeModel model;
	GameOfLifeView view;
	
	Thread1 t1;
	
	public GameOfLifeController(GameOfLifeModel model, GameOfLifeView view) {
		this.model=model;
		this.view=view;
		
		view.addGameOfLifeViewListener(this);
		model.addObserver(this);
		
	}

	@Override
	public void handleCalculatorViewEvent(GameOfLifeViewEvent e) {
		if (e.isResizeEvent()) {
			if (t1.isAlive()) {
				view._message.setText("Stop auto-play before resizing");
				return;
				}
			int size = model.getBoardSize();
			JSpotBoard _board = new JSpotBoard(size,size);
			
			for (int i=0;i<size;i+=2) {
				for (int j=0;j<size;j+=2) {
					_board.getSpotAt(i, j).setBackground(new Color(193,154,107));
					_board.getSpotAt(i, j).setHighlight(new Color(34,139,34));
				}
			}
			for (int i=1;i<size;i+=2) {
				for (int j=0;j<size;j+=2) {
					_board.getSpotAt(i, j).setBackground(new Color(213,174,127));
					_board.getSpotAt(i, j).setHighlight(new Color(34,139,34));
				}
			}
			for (int i=0;i<size;i+=2) {
				for (int j=1;j<size;j+=2) {
					_board.getSpotAt(i, j).setBackground(new Color(213,174,127));
					_board.getSpotAt(i, j).setHighlight(new Color(34,139,34));
				}
			}
			for (int i=1;i<size;i+=2) {
				for (int j=1;j<size;j+=2) {
					_board.getSpotAt(i, j).setBackground(new Color(193,154,107));
					_board.getSpotAt(i, j).setHighlight(new Color(34,139,34));
				}
			}
			
			view.remove(view._board);
			view._board = _board;
			view.add(_board,BorderLayout.CENTER);
			view._board.addSpotListener(view);
			view.revalidate();
			
			int dimension = model.getBoardSize();
			JSpotBoard board = new JSpotBoard(dimension,dimension);
			view.setBoard(board);
			model.setBoard(board);
			
			
		} else if (e.isClearEvent()) {
			JSpotBoard board = model.getBoard();
			for (Spot s:board) {
				if (!s.isEmpty()) {
					s.toggleSpot();
				}
			}
			model.setBoard(board);
			view.setBoard(board);
		} else if (e.isRandomEvent()) {
			Random r = new Random();
			int dimension = model.getBoardSize();
			JSpotBoard board = new JSpotBoard(dimension,dimension);
	
			for (Spot s:board) {
				if (r.nextInt(2)==1) {
					s.toggleSpot();
				}
			}
			view.setBoard(board);
			model.setBoard(board);
		} else if (e.isAdvanceEvent()) {
			JSpotBoard board = model.getBoard();
			int surviveThresholdMin = model.getSurviveThreshMin();
			int surviveThresholdMax = model.getSurviveThreshMax();
			int birthThresholdMin = model.getBirthThreshMin();
			int birthThresholdMax = model.getBirthThreshMax();
			List<Spot> alive = new ArrayList<>();
			if (!model.getTorusMode()) {
				for (Spot s:board) {
					if (!s.isEmpty()) {
						if (numNeighbors(s)>=surviveThresholdMin && numNeighbors(s)<=surviveThresholdMax) {
							alive.add(s);
						}
					} else {
						if (numNeighbors(s)>=birthThresholdMin && numNeighbors(s)<=birthThresholdMax) {
							alive.add(s);
						}
					}
				}
				clearBoard(board);
				for (Spot s:alive) {
					s.toggleSpot();
				}
				view.setBoard(board);
				model.setBoard(board);
			} else {
				for (Spot s:board) {
					if (!s.isEmpty()) {
						if (numNeighborsTorus(s)>=surviveThresholdMin && numNeighborsTorus(s)<=surviveThresholdMax) {
							alive.add(s);
						}
					} else {
						if (numNeighborsTorus(s)>=birthThresholdMin && numNeighborsTorus(s)<=birthThresholdMax) {
							alive.add(s);
						}
					}
				}
				clearBoard(board);
				for (Spot s:alive) {
					s.toggleSpot();
				}
				view.setBoard(board);
				model.setBoard(board);
			}
		} else if (e.isBirthMinEvent()) {
			model.setBirthThreshMin(view.birthThreshMin.getSelectedIndex()+1);
		} else if (e.isBirthMaxEvent()) {
			model.setBirthThreshMax(view.birthThreshMax.getSelectedIndex()+1);
		} else if (e.isSurviveMinEvent()) {
			model.setSurviveThreshMin(view.surviveThreshMin.getSelectedIndex()+1);
		} else if (e.isSurviveMaxEvent()) {
			model.setSurviveThreshMax(view.surviveThreshMax.getSelectedIndex()+1);
		} else if (e.isTorusOnEvent()) {
			model.setTorusMode(true);
		} else if (e.isTorusOffEvent()) {
			model.setTorusMode(false);
		} else if (e.isButtonClickEvent()) {
			ButtonClickEvent event = (ButtonClickEvent) e;
		
			model.getSpotAt(event.x, event.y).setSpotColor(Color.BLACK);
			model.getSpotAt(event.x, event.y).toggleSpot();
			
			view.setBoard(model.getBoard());
		} else if (e.isSliderEvent()) {
			if (t1.isAlive()) {
				view._message.setText("Stop auto-play before resizing");
				return;
				}
			SliderEvent event = (SliderEvent) e;
			
			model.setBoardSize(event.value);
			view.setMessage("Board size: " + event.value+"x"+event.value);
		} else if (e.isSpotEnteredEvent()) {
			SpotEnteredEvent event = (SpotEnteredEvent) e;
			
			view._board.getSpotAt(event.x, event.y).highlightSpot();;
		} else if (e.isSpotExitedEvent() ) {
			SpotExitedEvent event = (SpotExitedEvent) e;
			
			view._board.getSpotAt(event.x, event.y).unhighlightSpot();
		} else if (e.isMillisecondSliderEvent()) {
			MillisecondSliderEvent event = (MillisecondSliderEvent) e;
			
			model.setMillisecondDelay(event.value);
		} else if (e.isPlayEvent()) {
			t1 = new Thread1(this);
			t1.start();
			
			view.playStopButton.setText("Stop");
			view.playStopButton.setActionCommand("stop");
			
		} else if (e.isStopEvent()) {
			t1.halt();
			
			view.playStopButton.setText("Start");
			view.playStopButton.setActionCommand("play");
		}
		
	}
	
	@Override
	public void update(GameOfLifeModel model, JSpotBoard board) {
	
	}
	
	
	private void clearBoard(JSpotBoard board) {
		for (Spot s:board) {
			s.clearSpot();
		}
	}
	
	private int numNeighbors(Spot s) {
		int x = s.getSpotX();
		int y = s.getSpotY();
		JSpotBoard board = model.getBoard();
		int boardSize = model.getBoardSize();
		
		int num = 0;
		if (x>0) {
			if (!board.getSpotAt(x-1, y).isEmpty()) {
				num++;
			}
		}
		if (x<boardSize-1) {
			if (!board.getSpotAt(x+1, y).isEmpty()) {
				num++;
			}
		}
		if (y>0) {
			if (!board.getSpotAt(x, y-1).isEmpty()) {
				num++;
			}
		}
		if (y<boardSize-1) {
			if (!board.getSpotAt(x, y+1).isEmpty()) {
				num++;
			}
		}
		if (x>0 && y>0) {
			if (!board.getSpotAt(x-1, y-1).isEmpty()) {
				num++;
			}
		}
		if (x>0 && y<boardSize-1) {
			if (!board.getSpotAt(x-1, y+1).isEmpty()) {
				num++;
			}
		}
		if (x<boardSize-1 && y<boardSize-1) {
			if (!board.getSpotAt(x+1, y+1).isEmpty()) {
				num++;
			}
		}
		if (x<boardSize-1 && y>0) {
			if (!board.getSpotAt(x+1, y-1).isEmpty()) {
				num++;
			}
		}
		
		return num;
	}
	
	private int numNeighborsTorus(Spot s) {
		int x = s.getSpotX();
		int y = s.getSpotY();
		JSpotBoard board = model.getBoard();
		int boardSize = model.getBoardSize();
		
		int num = 0;
		if (x>0) {
			if (!board.getSpotAt(x-1, y).isEmpty()) {
				num++;
			}
		}
		if (x<boardSize-1) {
			if (!board.getSpotAt(x+1, y).isEmpty()) {
				num++;
			}
		}
		if (y>0) {
			if (!board.getSpotAt(x, y-1).isEmpty()) {
				num++;
			}
		}
		if (y<boardSize-1) {
			if (!board.getSpotAt(x, y+1).isEmpty()) {
				num++;
			}
		}
		if (x>0 && y>0) {
			if (!board.getSpotAt(x-1, y-1).isEmpty()) {
				num++;
			}
		}
		if (x>0 && y<boardSize-1) {
			if (!board.getSpotAt(x-1, y+1).isEmpty()) {
				num++;
			}
		}
		if (x<boardSize-1 && y<boardSize-1) {
			if (!board.getSpotAt(x+1, y+1).isEmpty()) {
				num++;
			}
		}
		if (x<boardSize-1 && y>0) {
			if (!board.getSpotAt(x+1, y-1).isEmpty()) {
				num++;
			}
		}
		
		//TORUS PART
		if (x==0) {
			if (!board.getSpotAt(boardSize-1, y).isEmpty()) {
				num++;
			}
			if (y>0) {
				if (!board.getSpotAt(boardSize-1, y-1).isEmpty()) {
					num++;
				}
			}
			if (y<boardSize-1) {
				if (!board.getSpotAt(boardSize-1, y+1).isEmpty()) {
					num++;
				}
			}
		}
		if (x==boardSize-1) {
			if (!board.getSpotAt(0, y).isEmpty()) {
				num++;
			}
			if (y>0) {
				if (!board.getSpotAt(0, y-1).isEmpty()) {
					num++;
				}
			}
			if (y<boardSize-1) {
				if (!board.getSpotAt(0, y+1).isEmpty()) {
					num++;
				}
			}
		}
		if (y==0) {
			if (!board.getSpotAt(x, boardSize-1).isEmpty()) {
				num++;
			}
			if (x>0) {
				if (!board.getSpotAt(x-1, boardSize-1).isEmpty()) {
					num++;
				}
			}
			if (x<boardSize-1) {
				if (!board.getSpotAt(x+1, boardSize-1).isEmpty()) {
					num++;
				}
			}
		}
		if (y==boardSize-1) {
			if (!board.getSpotAt(x, 0).isEmpty()) {
				num++;
			}
			if (x>0) {
				if (!board.getSpotAt(x-1, 0).isEmpty()) {
					num++;
				}
			}
			if (x<boardSize-1) {
				if (!board.getSpotAt(x+1, 0).isEmpty()) {
					num++;
				}
			}
		}
		if (x==0 && y==0) {
			if (!board.getSpotAt(boardSize-1, boardSize-1).isEmpty()) {
				num++;
			}
		}
		if (x==0 && y==boardSize-1) {
			if (!board.getSpotAt(boardSize-1, 0).isEmpty()) {
				num++;
			}
		}
		if (x==boardSize-1 && y==0) {
			if (!board.getSpotAt(0, boardSize-1).isEmpty()) {
				num++;
			}
		}
		if (x==boardSize-1 && y==boardSize-1) {
			if (!board.getSpotAt(0, 0).isEmpty()) {
				num++;
			}
		}
		
	
		return num;
	}

	
	

}
