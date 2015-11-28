package com.example.biglabellib;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.TextView;

import com.example.biglabellib.R;

public class BigTextLabelView extends TextView{
	
	
	private Paint backgroundPaint;

	// constructor
	public BigTextLabelView(Context context) {
		super(context);
		initTextLabel(context, null);
	}

	public BigTextLabelView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initTextLabel(context, attrs);
	}

	public BigTextLabelView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		initTextLabel(context, attrs);
	}
	
	// initialization
	private void initTextLabel(Context context, AttributeSet attrs){
		
		backgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		backgroundPaint.setStyle(Paint.Style.FILL);
		
		int backgroundColor = Color.GRAY;
		if(null != attrs){
			final TypedArray res = context.obtainStyledAttributes(attrs, R.styleable.BigTextLabel);
			
			backgroundColor = res.getColor(R.styleable.BigTextLabel_backgroundColor, backgroundColor);
			
			res.recycle();
		}
		
		backgroundPaint.setColor(backgroundColor);

	}
	
	private Rect getCurrentViewRect(){
		
		int left = 0;
		int right = 0;
		int top  = 0;
		int bottom = 0;

		left = Math.round(this.getX());
		top = Math.round(this.getY());
		
		right = left + this.getWidth();
		bottom = top + this.getHeight();
		
		return new Rect(left, top, right, bottom);
	}
	
	
	
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		
		super.onSizeChanged(w, h, oldw, oldh);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawRect(getCurrentViewRect(), backgroundPaint);
		super.onDraw(canvas);
	}
	
	

}
