package GameOfLifeMVC;

abstract public class GameOfLifeViewEvent {

	public boolean isResizeEvent() {
		return false;
	}
	public boolean isClearEvent() {
		return false;
	}
	public boolean isRandomEvent() {
		return false;
	}
	public boolean isAdvanceEvent() {
		return false;
	}
	public boolean isBirthMinEvent() {
		return false;
	}
	public boolean isBirthMaxEvent() {
		return false;
	}
	public boolean isSurviveMinEvent() {
		return false;
	}
	public boolean isSurviveMaxEvent() {
		return false;
	}
	public boolean isTorusOnEvent() {
		return false;
	}
	public boolean isTorusOffEvent() {
		return false;
	}
	public boolean isButtonClickEvent() {
		return false;
	}
	public boolean isSliderEvent() {
		return false;
	}
	public boolean isSpotEnteredEvent() {
		return false;
	}
	public boolean isSpotExitedEvent() {
		return false;
	}
	public boolean isMillisecondSliderEvent() {
		return false;
	}
	public boolean isPlayEvent() {
		return false;
	}
	public boolean isStopEvent() {
		return false;
	}
}

class ResizeEvent extends GameOfLifeViewEvent {
	int dimension;
	public ResizeEvent(int dimension) {
		this.dimension = dimension;
	}
	public boolean isResizeEvent() {
		return true;
	}
}
class ClearEvent extends GameOfLifeViewEvent {
	public boolean isClearEvent() {
		return true;
	}
}
class RandomEvent extends GameOfLifeViewEvent {
	public boolean isRandomEvent() {
		return true;
	}
}
class AdvanceEvent extends GameOfLifeViewEvent {
	public boolean isAdvanceEvent() {
		return true;
	}
}
class BirthMinEvent extends GameOfLifeViewEvent {
	int birthMin;
	
	public BirthMinEvent(int birthMin) {
		this.birthMin=birthMin;
	}
	public boolean isBirthMinEvent() {
		return true;
	}
}
class BirthMaxEvent extends GameOfLifeViewEvent {
	int birthMax;
	
	public BirthMaxEvent(int birthMax) {
		this.birthMax=birthMax;
	}
	public boolean isBirthMaxEvent() {
		return true;
	}
}
class SurviveMinEvent extends GameOfLifeViewEvent {
	int surviveMin;
	
	public SurviveMinEvent(int surviveMin) {
		this.surviveMin=surviveMin;
	}
	public boolean isSurviveMinEvent() {
		return true;
	}
}
class SurviveMaxEvent extends GameOfLifeViewEvent {
	int surviveMax;
	
	public SurviveMaxEvent(int surviveMax) {
		this.surviveMax=surviveMax;
	}
	public boolean isSurviveMaxEvent() {
		return true;
	}
}
class TorusOnEvent extends GameOfLifeViewEvent {
	public boolean isTorusOnEvent() {
		return true;
	}
}
class TorusOffEvent extends GameOfLifeViewEvent {
	public boolean isTorusOffEvent() {
		return true;
	}
}
class ButtonClickEvent extends GameOfLifeViewEvent {
	int x;
	int y;
	
	public ButtonClickEvent(int x, int y) {
		this.x=x;
		this.y=y;
	}
	
	public boolean isButtonClickEvent() {
		return true;
	}
}
class SliderEvent extends GameOfLifeViewEvent {
	int value;
	
	public SliderEvent(int i) {
		value=i;
	}
	
	public boolean isSliderEvent() {
		return true;
	}
}
class SpotEnteredEvent extends GameOfLifeViewEvent {
	int x;
	int y;
	
	public SpotEnteredEvent(int x, int y) {
		this.x=x;
		this.y=y;
	}
	public boolean isSpotEnteredEvent() {
		return true;
	}
}
class SpotExitedEvent extends GameOfLifeViewEvent {
	int x;
	int y;
	
	public SpotExitedEvent(int x, int y) {
		this.x=x;
		this.y=y;
	}
	public boolean isSpotExitedEvent() {
		return true;
	}
}
class MillisecondSliderEvent extends GameOfLifeViewEvent {
	int value;
	
	public MillisecondSliderEvent(int v) {
		value = v;
	}
	
	public boolean isMillisecondSliderEvent() {
		return true;
	}
}
class PlayEvent extends GameOfLifeViewEvent {
	public boolean isPlayEvent() {
		return true;
	}
}
class StopEvent extends GameOfLifeViewEvent {
	public boolean isStopEvent() {
		return true;
	}
}

