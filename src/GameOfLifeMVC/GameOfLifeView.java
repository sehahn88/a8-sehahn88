package GameOfLifeMVC;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.Random;
import java.util.*;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class GameOfLifeView extends JPanel implements ChangeListener, ActionListener, SpotListener {
	
	JSpotBoard _board;		
	private JLabel _message;		
	private JSlider boardSizeSlider;
	JComboBox<String> birthThreshMin;
	JComboBox<String> birthThreshMax;
	JComboBox<String> surviveThreshMin;
	JComboBox<String> surviveThreshMax;
	JRadioButton torusOn;
	JRadioButton torusOff;
	JButton playStopButton;
	JSlider millisecondSlider;
	
	private int boardSize;
	
	private List<GameOfLifeViewListener> listeners;
	
	public GameOfLifeView() {
		listeners = new ArrayList<GameOfLifeViewListener>();
		
		/* Create SpotBoard and message label. */
		boardSizeSlider = new JSlider(10,500,10);
		boardSizeSlider.addChangeListener(this);
		boardSize = 10; 
		
		_board = new JSpotBoard(boardSize,boardSize);
	
		for (int i=0;i<boardSize;i+=2) {
			for (int j=0;j<boardSize;j+=2) {
				_board.getSpotAt(i, j).setBackground(new Color(193,154,107));
				_board.getSpotAt(i, j).setHighlight(new Color(34,139,34));
			}
		}
		for (int i=1;i<boardSize;i+=2) {
			for (int j=0;j<boardSize;j+=2) {
				_board.getSpotAt(i, j).setBackground(new Color(213,174,127));
				_board.getSpotAt(i, j).setHighlight(new Color(34,139,34));
			}
		}
		for (int i=0;i<boardSize;i+=2) {
			for (int j=1;j<boardSize;j+=2) {
				_board.getSpotAt(i, j).setBackground(new Color(213,174,127));
				_board.getSpotAt(i, j).setHighlight(new Color(34,139,34));
			}
		}
		for (int i=1;i<boardSize;i+=2) {
			for (int j=1;j<boardSize;j+=2) {
				_board.getSpotAt(i, j).setBackground(new Color(193,154,107));
				_board.getSpotAt(i, j).setHighlight(new Color(34,139,34));
			}
		}
		
		_message = new JLabel();
		_message.setText("Board size: " + boardSize+"x"+boardSize);
	
		setLayout(new BorderLayout());
		add(_board, BorderLayout.CENTER);
	
		JPanel reset_message_panel = new JPanel();
		reset_message_panel.setLayout(new BorderLayout());


		JButton reset_button = new JButton("Resize");
		reset_button.addActionListener(this);
		reset_button.setActionCommand("resize");
		reset_message_panel.add(reset_button, BorderLayout.EAST);
		reset_message_panel.add(_message, BorderLayout.CENTER);
		reset_message_panel.add(boardSizeSlider, BorderLayout.WEST);

		add(reset_message_panel, BorderLayout.SOUTH);

		_board.addSpotListener(this);
		
		
		JPanel variousFeaturesPanel = new JPanel();
		variousFeaturesPanel.setLayout(new BorderLayout());
		
		JButton clearButton = new JButton("Clear Board");
		clearButton.addActionListener(this);
		clearButton.setActionCommand("clear");
		
		JButton randomButton = new JButton("Random Fill");
		randomButton.addActionListener(this);
		randomButton.setActionCommand("random");
		
		JButton advanceButton = new JButton("Advance One Step");
		advanceButton.addActionListener(this);
		advanceButton.setActionCommand("advance");
		
		JPanel autoAdvancePanel = new JPanel();
		autoAdvancePanel.setLayout(new BorderLayout());
		
		playStopButton = new JButton("Play");
		playStopButton.addActionListener(this);
		playStopButton.setActionCommand("play");
		
		millisecondSlider = new JSlider(10,1000,100);
		millisecondSlider.addChangeListener(this);
		millisecondSlider.setMinorTickSpacing(1);
		millisecondSlider.setMajorTickSpacing(100);
		millisecondSlider.setPaintTicks(true);
		Hashtable<Integer, JLabel> labels = new Hashtable<>();
		labels.put(100, new JLabel("100"));
        labels.put(200, new JLabel("200"));
        labels.put(300, new JLabel("300"));
        labels.put(400, new JLabel("400"));
        labels.put(500, new JLabel("500"));
        labels.put(600, new JLabel("600"));
        labels.put(700, new JLabel("700"));
        labels.put(800, new JLabel("800"));
        labels.put(900, new JLabel("900"));
        millisecondSlider.setLabelTable(labels);

        millisecondSlider.setPaintLabels(true);


		
		autoAdvancePanel.add(playStopButton,BorderLayout.WEST);
		autoAdvancePanel.add(millisecondSlider,BorderLayout.CENTER);
		
		variousFeaturesPanel.add(clearButton,BorderLayout.EAST);
		variousFeaturesPanel.add(randomButton,BorderLayout.CENTER);
		variousFeaturesPanel.add(advanceButton,BorderLayout.WEST);
		variousFeaturesPanel.add(autoAdvancePanel,BorderLayout.SOUTH);
		
		add(variousFeaturesPanel,BorderLayout.NORTH);
		
		
		JPanel customizePanel = new JPanel();
		customizePanel.setLayout(new GridLayout(0,1));
		
		JPanel birthPanel = new JPanel();
		birthPanel.setLayout(new BorderLayout());
		String[] choices = {"1","2","3","4","5","6","7","8"};
		birthThreshMin = new JComboBox<String>(choices);
		birthThreshMax = new JComboBox<String>(choices);
		birthThreshMin.setSelectedIndex(2);
		birthThreshMax.setSelectedIndex(2);
		birthThreshMin.addActionListener(this);
		birthThreshMax.addActionListener(this);
		birthThreshMin.setActionCommand("birthMin");
		birthThreshMax.setActionCommand("birthMax");
		
		
		birthPanel.add(birthThreshMax,BorderLayout.EAST);
		birthPanel.add(birthThreshMin,BorderLayout.WEST);
		JLabel birthLabel = new JLabel("Min - Max");
		birthPanel.add(birthLabel,BorderLayout.CENTER);
		JLabel birthTitle = new JLabel("Birth Thresholds");
		birthPanel.add(birthTitle,BorderLayout.NORTH);


		
		JPanel survivePanel = new JPanel();
		survivePanel.setLayout(new BorderLayout());
		surviveThreshMin = new JComboBox<String>(choices);
		surviveThreshMax = new JComboBox<String>(choices);
		surviveThreshMin.setSelectedIndex(1);
		surviveThreshMax.setSelectedIndex(2);
		surviveThreshMin.addActionListener(this);
		surviveThreshMax.addActionListener(this);
		surviveThreshMin.setActionCommand("surviveMin");
		surviveThreshMax.setActionCommand("surviveMax");
		
		survivePanel.add(surviveThreshMax,BorderLayout.EAST);
		survivePanel.add(surviveThreshMin,BorderLayout.WEST);
		JLabel surviveLabel = new JLabel("Min - Max");
		survivePanel.add(surviveLabel,BorderLayout.CENTER);
		JLabel surviveTitle = new JLabel("Survive Thresholds");
		survivePanel.add(surviveTitle,BorderLayout.NORTH);
		
		JPanel torusPanel = new JPanel();
		torusPanel.setLayout(new GridLayout(0,1));
		torusOn = new JRadioButton("On");
		torusOn.addActionListener(this);
		torusOn.setActionCommand("torusOn");
		torusOff = new JRadioButton("Off");
		torusOff.addActionListener(this);
		torusOff.setActionCommand("torusOff");
		torusOff.setSelected(true);
		
		ButtonGroup torusGroup = new ButtonGroup();
		torusGroup.add(torusOn);
		torusGroup.add(torusOff);
		
		JLabel torusLabel = new JLabel("Torus Mode");
		torusPanel.add(torusLabel);
		torusPanel.add(torusOn);
		torusPanel.add(torusOff);
		JLabel blank2 = new JLabel("");
		torusPanel.add(blank2);
		
		
		customizePanel.add(birthPanel);
		JLabel blank3 = new JLabel("");
		customizePanel.add(blank3);
		customizePanel.add(survivePanel);
		JLabel blank4 = new JLabel("");
		customizePanel.add(blank4);
		customizePanel.add(torusPanel);
		
		add(customizePanel,BorderLayout.EAST);
		
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("resize")) {
			fireEvent(new ResizeEvent(boardSizeSlider.getValue()));
		}
		if (e.getActionCommand().equals("clear")) {
			fireEvent(new ClearEvent());
		}
		if (e.getActionCommand().equals("random")) {
			fireEvent(new RandomEvent());
		}
		if (e.getActionCommand().equals("advance")) {
			fireEvent(new AdvanceEvent());
		}
		if (e.getActionCommand().equals("birthMin")) {
			fireEvent(new BirthMinEvent(birthThreshMin.getSelectedIndex()+1));
		}
		if (e.getActionCommand().equals("birthMax")) {
			fireEvent(new BirthMaxEvent(birthThreshMax.getSelectedIndex()+1));
		}
		if (e.getActionCommand().equals("surviveMin")) {
			fireEvent(new SurviveMinEvent(surviveThreshMin.getSelectedIndex()+1));
		}
		if (e.getActionCommand().equals("surviveMax")) {
			fireEvent(new SurviveMaxEvent(surviveThreshMax.getSelectedIndex()+1));
		}
		if (e.getActionCommand().equals("torusOn")) {
			fireEvent(new TorusOnEvent());
		}
		if (e.getActionCommand().equals("torusOff")) {
			fireEvent(new TorusOffEvent());
		}
		if (e.getActionCommand().equals("play")) {
			fireEvent(new PlayEvent());
		}
		if (e.getActionCommand().equals("stop")) {
			fireEvent(new StopEvent());
		}
		
		
	}

	@Override
	public void spotClicked(Spot s) {
		fireEvent(new ButtonClickEvent(s.getSpotX(),s.getSpotY()));
	}
	
	@Override
	public void spotEntered(Spot s) {
		fireEvent(new SpotEnteredEvent(s.getSpotX(),s.getSpotY()));
		//s.highlightSpot();
	}

	@Override
	public void spotExited(Spot s) {
		fireEvent(new SpotExitedEvent(s.getSpotX(),s.getSpotY()));
		//s.unhighlightSpot();
	}
	
	@Override
	public void stateChanged(ChangeEvent e) {
		if (e.getSource()==millisecondSlider) {
			fireEvent(new MillisecondSliderEvent(millisecondSlider.getValue()));
		} else {
			fireEvent(new SliderEvent(boardSizeSlider.getValue()));
		}
	}
	
	public void addGameOfLifeViewListener(GameOfLifeViewListener l) {
		listeners.add(l);
	}
	
	public void removeGameOfLifeViewListener(GameOfLifeViewListener l) {
		listeners.remove(l);
	}
	
	public void fireEvent(GameOfLifeViewEvent e) {
		for (GameOfLifeViewListener l : listeners) {
			l.handleCalculatorViewEvent(e);
		}
	}
	
	public JSpotBoard getBoard() {return _board;}
	
	public void setBoard(JSpotBoard board) {
		clearBoard();
		for (Spot s : board) {
			int x = s.getSpotX();
			int y = s.getSpotY();
			
			if (!s.isEmpty()) {
				_board.getSpotAt(x, y).toggleSpot();
			}
		}
	}
	
	public void clearBoard() {
		for (Spot s:_board) {
			s.clearSpot();
		}
	}
	
	public void setMessage(String m) {
		_message.setText(m);
	}

}
