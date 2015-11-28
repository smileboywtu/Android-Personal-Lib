package com.smileboy.DynamicBgLinearLayout;

import java.security.SecureRandom;
import com.example.dynamicbglinearlayoutlib.R;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.util.AttributeSet;
import android.widget.LinearLayout;

public class DynamicBgLinearLayout extends LinearLayout {

	private static final int defaultScreenWidth = 720;
	private static final int defaultScreenHeight = 1280;
	private static final int defaultCircleNumber = 45;

	private static final int DOTSIZE = 2;
	private static final int[] colors = { Color.parseColor("#669933"), Color.parseColor("#256699") };

	private Paint backgroundPaint;

	private int marginTop = 0;
	private int marginBottom = 0;

	private int screentWidth = defaultScreenWidth;
	private int screentHeight = defaultScreenHeight;

	private int maxAreaSize = 100;
	private int minAreaSize = 20;

	private int frame = 60;
	private int circleCount = defaultCircleNumber;
	private long timerDelayTime = 0;

	private int[] circleColors;
	private float[][] circleProperties;

	private Bitmap currentBitmap;

	private final Handler timerHandler = new Handler();
	private final Runnable timerRunnable = new UpdateBackground();

	public DynamicBgLinearLayout(Context context) {
		super(context);
		initialization(context, null);
	}

	public DynamicBgLinearLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		initialization(context, attrs);
	}

	public DynamicBgLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		initialization(context, attrs);
	}

	public DynamicBgLinearLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
		initialization(context, attrs);
	}

	private void initialization(Context context, AttributeSet attrs) {

		if (null != attrs) {

			final TypedArray res = context.obtainStyledAttributes(attrs, R.styleable.DynamicBackgroundLinearLayout);

			marginTop = res.getInt(R.styleable.DynamicBackgroundLinearLayout_marginTopSize, marginTop);
			marginBottom = res.getInt(R.styleable.DynamicBackgroundLinearLayout_marginBottomSize, marginBottom);

			screentWidth = res.getInt(R.styleable.DynamicBackgroundLinearLayout_screenWidth, defaultScreenWidth);
			screentHeight = res.getInt(R.styleable.DynamicBackgroundLinearLayout_screenHeight, defaultScreenHeight);

			circleCount = res.getInt(R.styleable.DynamicBackgroundLinearLayout_circleNumber, defaultCircleNumber);

			res.recycle();
		}

		currentBitmap = setBlankBitmap();

		setBackground();
		setTimeDelayTime(frame);

		// start the timer
		timerHandler.postDelayed(timerRunnable, 0);
	}

	/**
	 * @brief here set up the parameters when draw the background
	 */
	private final void setBackground() {

		// set up the paint
		backgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		backgroundPaint.setStyle(Paint.Style.FILL);

		// set up circle properties
		circleColors = new int[circleCount];
		circleProperties = new float[circleCount][5];

		int colorCounter = 0;
		int width = screentWidth;
		int height = screentHeight - 2 * (marginBottom + marginTop);
		SecureRandom random = new SecureRandom();

		for (int j = 0; j < circleCount; j++) {
			circleProperties[j][0] = random.nextInt() % width; // X
			circleProperties[j][1] = random.nextInt() % height; // Y
			circleProperties[j][2] = random.nextFloat() * (maxAreaSize - minAreaSize) + minAreaSize; // Radius
			circleProperties[j][3] = random.nextFloat() - 0.5f; // X Speed
			circleProperties[j][4] = random.nextFloat() - 0.5f; // Y Speed
			circleColors[j] = colors[colorCounter]; // Color

			// reset counter
			colorCounter++;
			if (colorCounter == colors.length) {
				colorCounter = 0;
			}
		}
	}

	/**
	 * @brief create blank bitmap with proper size
	 */
	private final Bitmap setBlankBitmap() {

		int width = screentWidth;
		int height = screentHeight - 2 * (marginBottom + marginTop);

		Bitmap.Config conf = Bitmap.Config.ARGB_8888;
		Bitmap image = Bitmap.createBitmap(width, height, conf);

		return image;
	}

	/**
	 * @brief use the frame to calculate the timer time delay we need to get the
	 *        millisecond.
	 * @param frame
	 *            dynamic background frame
	 */
	private final void setTimeDelayTime(final int frame) {
		timerDelayTime = Math.round(1000 / frame);
	}

	/**
	 * @brief this only works when the layout is initialized
	 * @return
	 */
	private final Rect getCurrentViewRect() {

		int left = 0;
		int right = 0;
		int top = 0;
		int bottom = 0;
		
		left = Math.round(this.getX());
		top = Math.round(this.getY());

		right = left + this.getWidth();
		bottom = top + this.getHeight();

		return new Rect(left, top, right, bottom);
	}

	/**
	 * @brief all paint design should done here
	 * @param rect
	 *            current draw canvas
	 */
	private final Bitmap customeBackgroundPaint() {

		Rect rect = getCurrentViewRect();
		Rect dotRect = new Rect();

		int width = rect.width();
		int height = rect.height();

		Paint areaPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		Paint linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		Paint dotPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

		Bitmap futureBitmap = setBlankBitmap();

		Canvas canvas = new Canvas(futureBitmap);

		// paint foreground
		for (int j = 0; j < circleCount; j++) {

			// Disable shape stroke/border
			areaPaint.setStyle(Paint.Style.FILL);

			// Cache diameter and radius of current circle
			float radi = circleProperties[j][2];
			float diam = radi;

			float opacity = ProcessingLib.dist(circleProperties[j][0], circleProperties[j][1], width / 2, height / 2);
			opacity = ProcessingLib.map(opacity, width * 0.4f, 0, 0, 70);
			opacity = ProcessingLib.constrain(opacity, 0, 100);

			int r = Color.red(circleColors[j]);
			int g = Color.green(circleColors[j]);
			int b = Color.blue(circleColors[j]);
			areaPaint.setARGB(Math.round(opacity), r, g, b);

			// Draw circle
			canvas.drawCircle(circleProperties[j][0], circleProperties[j][1], radi, areaPaint);

			// Move circle
			circleProperties[j][0] += circleProperties[j][3] * 2;
			circleProperties[j][1] += circleProperties[j][4] * 2;

			/*
			 * Wrap edges of canvas so circles leave the top and re-enter the
			 * bottom, etc...
			 */
			if (circleProperties[j][0] < -diam) {
				circleProperties[j][0] = width + diam;
			}
			if (circleProperties[j][0] > width + diam) {
				circleProperties[j][0] = -diam;
			}
			if (circleProperties[j][1] < 0 - diam) {
				circleProperties[j][1] = height + diam;
			}
			if (circleProperties[j][1] > height + diam) {
				circleProperties[j][1] = -diam;
			}

			// otherwise set center dot color to black..
			dotPaint.setARGB(Math.round(opacity * 2), 0, 0, 0);
			// and set line color to turquoise.
			linePaint.setStyle(Paint.Style.FILL);
			linePaint.setARGB(Math.round(opacity), 64, 128, 128);

			// Loop through all circles
			for (int k = 0; k < circleCount; k++) {
				// If the circles are close...
				if (Math.sqrt(circleProperties[j][0] - circleProperties[k][0])
						+ Math.sqrt(circleProperties[j][1] - circleProperties[k][1]) < Math.sqrt(diam)) {
					// Stroke a line from current circle to adjacent circle
					canvas.drawLine(circleProperties[j][0], circleProperties[j][1], circleProperties[k][0],
							circleProperties[k][1], linePaint);
				}
			}

			// Turn off stroke/border
			dotPaint.setStyle(Paint.Style.FILL);

			// Draw dot in center of circle
			dotRect.left = Math.round(circleProperties[j][0] - DOTSIZE);
			dotRect.right = Math.round(circleProperties[j][0] + DOTSIZE);
			dotRect.top = Math.round(circleProperties[j][1] - DOTSIZE);
			dotRect.bottom = Math.round(circleProperties[j][1] + DOTSIZE);
			canvas.drawRect(dotRect, dotPaint);
		}

		rect = null;
		dotRect = null;

		return futureBitmap;
	}

	private final void setBitmap(Bitmap image) {
		currentBitmap = image;
	}

	private final Bitmap getBitmap() {
		return currentBitmap;
	}

	// use to update the background
	private class UpdateBackground implements Runnable {

		@Override
		public void run() {

			// draw first
			Bitmap newImage = customeBackgroundPaint();

			setBitmap(newImage);

			// force the layout to repaint
			repaintLayout();

			// start next turn
			timerHandler.postDelayed(timerRunnable, timerDelayTime);
		}

	}

	/**
	 * @brief to make the background to repaint
	 */
	private final void repaintLayout() {
		this.postInvalidate();
	}

	@Override
	protected void onDraw(Canvas canvas) {

		// get current view rectangle
		Rect rect = getCurrentViewRect();

		// draw new bitmap
		canvas.drawBitmap(getBitmap(), rect.left, rect.top, backgroundPaint);

		super.onDraw(canvas);
	}

}
