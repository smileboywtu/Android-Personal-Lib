Android Personal Lib 
====================

publish the components used in my work.

![Alt text](https://github.com/smileboywtu/Android-Personal-Lib/blob/master/snapshot/1.png) 
![Alt text](https://github.com/smileboywtu/Android-Personal-Lib/blob/master/snapshot/2.png) 
![Alt text](https://github.com/smileboywtu/Android-Personal-Lib/blob/master/snapshot/3.png) 
![Alt text](https://github.com/smileboywtu/Android-Personal-Lib/blob/master/snapshot/4.png) 

the above show the four shot of the demo. you can see more about it from the demo dir.

Usage 
=====

general 
------- 

when you want to use these customer components, you should define a new android project 
and make it a library project, according to code below define the package name or you can 
define your own.

circle button 
-------------

```Android

 xmlns:button="http://schemas.android.com/apk/res-auto"

 <com.smileboy.roundbutton.CircleButton
            android:id="@+id/openCamera"
            android:layout_width="100dp"
            android:layout_height="100dp"
            android:maxHeight="64dp"
            android:maxWidth="64dp"
            android:scaleType="fitCenter"
            android:src="@drawable/camera"
            button:cb_color="#64DF90"
            button:cb_pressedRingWidth="8dp"
            button:cb_text=""
            button:cb_text_color="#30C0FF"
            button:cb_text_size="30.0" />

```

please keep care of using this components when you want to ignore some params. see more about 
the java file to get safe way.

```java

            final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CircleButton);
            color = a.getColor(R.styleable.CircleButton_cb_color, color);
			textColor = a.getColor(R.styleable.CircleButton_cb_text_color, textColor);
			text = a.getString(R.styleable.CircleButton_cb_text);
			textSize = a.getFloat(R.styleable.CircleButton_cb_text_size, 30.0f);
			pressedRingWidth = (int) a.getDimension(R.styleable.CircleButton_cb_pressedRingWidth, 
			                                        pressedRingWidth);

```

dynamic linear layout 
--------------------- 

```android 

xmlns:linearlayout="http://schemas.android.com/apk/res-auto"

 <com.smileboy.DynamicBgLinearLayout.DynamicBgLinearLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:layout_marginBottom="60dp"
        android:layout_marginTop="60dp"
        android:background="@android:color/white"
        android:gravity="bottom|center_horizontal"
        android:orientation="horizontal"
        linearlayout:circleNumber="45"
        linearlayout:marginBottomSize="60"
        linearlayout:marginTopSize="60"
        linearlayout:screenHeight="1280"
        linearlayout:screenWidth="720" >
		
		' 
		' 
		' 
 </com.smileboy.DynamicBgLinearLayout.DynamicBgLinearLayout>
 
```
if you want to get more imformation about the params, see the java source file. 

big text label 
-------------- 

```android  

xmlns:title="http://schemas.android.com/apk/res-auto"

 <com.example.biglabellib.BigTextLabelView
        android:id="@+id/titlebar"
        android:layout_width="match_parent"
        android:layout_height="60dp"
        android:gravity="center"
        android:text="@string/fountain_code"
        android:textColor="#FFFFFF"
        android:textSize="20sp"
        android:textStyle="bold"
        title:backgroundColor="#1B65AB" />

```

version button 
-------------- 

```android 

xmlns:version="http://schemas.android.com/apk/res-auto"

<com.example.versionbuttonlib.VersionButton
        android:id="@+id/version"
        android:layout_width="match_parent"
        android:layout_height="60dp"
        android:layout_alignParentBottom="true"
        android:text="@string/version"
        android:textColor="#FFFFFF"
        android:textSize="20sp"
        android:textStyle="bold"
        version:vb_backgroundColor="#1B65AB" />

```

toggle button 
------------- 
notice that i do not use this compoents in the demo, this components comes from the 
other github.

```android

xmlns:toggle="http://schemas.android.com/apk/res-auto"

 <com.zcw.togglebutton.ToggleButton
            android:layout_marginLeft="20dp"
            android:layout_width="54dp"
            toggle:onColor="#f00"
            toggle:offColor="#ddd"
            toggle:spotColor="#00f"
            toggle:offBorderColor="#000"
            toggle:borderWidth="2dp"
            android:layout_height="30dp" >
        </com.zcw.togglebutton.ToggleButton>
	
``` 

contact 
======= 

get a really good idear, find a bug? or you may want to get more detail, you can send 
me an email to **294101042 at dot com **.

