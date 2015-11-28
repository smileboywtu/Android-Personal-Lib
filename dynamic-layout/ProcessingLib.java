package com.smileboy.DynamicBgLinearLayout;

public class ProcessingLib {

	public ProcessingLib() {

	}

	/**
	 * 
	 * @param value
	 * 			value to be converted
	 * @param istart
	 * 			current range start
	 * @param istop
	 * 			current range stop
	 * @param ostart
	 * 			target range start
	 * @param ostop
	 * 			target range stop
	 * @return
	 */
	static public final float map(float value, float istart, float istop, float ostart, float ostop) {
		return ostart + (ostop - ostart) * ((value - istart) / (istop - istart));
	}
	
	/**
	 * 
	 * @param startx
	 * 			start point x
	 * @param starty
	 * 			start point y
	 * @param endx
	 * 			end point x 
	 * @param endy
	 * 			end point y
	 * @return
	 * 			distance between the two
	 */
	static public final float dist(float startx, float starty, float endx, float endy){
		
		return (float) Math.sqrt(Math.pow((startx - endx), 2f) + Math.pow(starty - endy, 2f));
	}
	
	/**
	 * 
	 * @param value
	 * 			value to constrain
	 * @param min
	 * 			min value
	 * @param max
	 * 			max value
	 * @return
	 */
	static public final float constrain(float value, float min, float max){
		if(value > max){
			value = max;
		}
		if(value < min){
			value = min;
		}
		
		return value;
	}
}
