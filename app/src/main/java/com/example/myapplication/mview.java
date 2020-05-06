package com.example.myapplication;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Picture;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.core.content.ContextCompat;

import static android.graphics.Paint.Align.CENTER;

public class mview extends View {
    private float screenX = Resources.getSystem().getDisplayMetrics().widthPixels;
    private float screenY = Resources.getSystem().getDisplayMetrics().heightPixels;
    private float marginX = screenX / 35;
    private float marginY = screenY / 50;
    private int spaceperline = 1;
    private float betweenBars = (screenX - 2 * marginX) / spaceperline;
    private float unit = betweenBars / 32;
    private int currentNote;
    public String huiNote;
    private String[] huiNotes = new String[]{};
    public int xianNote;
    public int[] xianNotes = new int[]{};
    public int timeNote = 0;
    public int timing = 1;
    private String[] notes = new String[]{};
    public String[] notetypes = {"散音", "泛音", "按音","绰","注","上","下", "撮","反撮","泛起", "泛止","空"};
    public String leftNote = "泛音";
    public int[] lengths = new int[] {};
    private Paint huipaint=new Paint();
    private int lineNumber = 0;
    private int lengthPerLine = 0;
    public String[] timings = {"全音","二分", "四分","八分","十六","三二"};
    public void timingPlus() {
        Log.i("mview",",timingPlus start");
        if (timeNote < 5) {
            timeNote++;
            timing = (int) Math.pow(2, timeNote);
        }
        Log.i("mview",",timingPlus, timing=" + timing + ",timeNote=" + timeNote);
    }
    public void timingMinus() {
        Log.i("mview",",timingMinus start");
        if (timeNote > 0) {
            timeNote--;
            timing = (int) Math.pow(2, timeNote);
        }
        Log.i("mview",",timingMinus, timing=" + timing + ",timeNote=" + timeNote);
    }
    public void addNotes() {
        //update note types
        String[] newnotes = new String[notes.length + 1];
        for (int i = 0; i < notes.length; i++) {
            newnotes[i] = notes[i];
            Log.i("mview",",note" + i + "=" + notes[i] + "added");
        }
        newnotes[newnotes.length - 1] = leftNote;
        notes = newnotes;
        // update xianNotes
        int[] newXians = new int[xianNotes.length + 1];
        for (int i = 0; i < xianNotes.length; i++) {
            newXians[i] = xianNotes[i];
            Log.i("mview",",xianNote" + i + "=" + xianNotes[i] + "added");
        }
        newXians[newXians.length - 1] = xianNote;
        xianNotes = newXians;
        //update huinotes
        String[] newhuiNotes = new String[huiNotes.length+1];
        for (int i = 0; i<huiNotes.length;i++){
            newhuiNotes[i]=huiNotes[i];
        }
        newhuiNotes[newhuiNotes.length-1] = huiNote;
        huiNotes=newhuiNotes;

        //update note lengths
        int[] newlengths = new int[lengths.length + 1];
        for (int i = 0; i < lengths.length; i++) {
            newlengths[i] = lengths[i];
            Log.i("mview",",length" + i + "=" + lengths[i] + "added");
        }
        newlengths[newlengths.length - 1] = timing;
        lengths = newlengths;
        int toltallength = 0;
        for (int i = 0; i < lengths.length; i++) {
            toltallength = toltallength + lengths[i];
        }
        lineNumber = (int)Math.ceil(toltallength / (screenX - 2 * marginX));
        Log.i("mview",",lineNumber=" + lineNumber + ",toltallength=" + toltallength + ",lengthPerLine=" + lengthPerLine);
        postInvalidate();
    }
    public void deleteNotes() {
        if (notes.length != 0) {
            //update note types
            String[] newnotes = new String[notes.length - 1];
            for (int i = 0; i < newnotes.length; i++) {
                newnotes[i] = notes[i];

            }
            notes = newnotes;
            //update note xians
            int[] newXians = new int[xianNotes.length - 1];
            for (int i = 0; i < newXians.length; i++) {
                newXians[i] = xianNotes[i];
            }
            xianNotes = newXians;
            //update huinotes
            String[] newhuiNotes = new String[huiNotes.length-1];
            for (int i = 0; i<newhuiNotes.length;i++){
                newhuiNotes[i]=huiNotes[i];
            }
            huiNotes=newhuiNotes;
            //update note lengths
            int[] newlengths = new int[lengths.length - 1];
            for (int i = 0; i < newlengths.length; i++) {
                newlengths[i] = lengths[i];
            }
            lengths = newlengths;
        }
        int toltallength = 0;
        for (int i = 0; i < lengths.length; i++) {
            toltallength = toltallength + lengths[i];
        }
        lineNumber = (int)Math.ceil(toltallength / (screenX - 2 * marginX));
        Log.i("mview",",lineNumber=" + lineNumber + ",toltallength=" + toltallength + ",lengthPerLine=" + lengthPerLine);
        postInvalidate();
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }


    public mview(Context context) {
        super(context);
        init(null, 0);
    }
    public mview(Context context, AttributeSet attr) {
        super(context, attr);
        init(attr, 0);
    }
    public mview(Context context, AttributeSet attr, int def) {
        super(context, attr, def);
        init(attr, def);
    }
    private void init(AttributeSet set, int def) {
        huipaint.setColor(Color.BLACK);
        huipaint.setTextSize(30f);
        huipaint.setTextAlign(CENTER);
        huipaint.setStyle(Paint.Style.STROKE);
        line1.setColor(Color.BLACK);
        line1.setAntiAlias(true);
    }
    private void drawCursor(Canvas canvas,float left, float top, float right, float bottom){
        RectF topRect = new RectF(left, top, left+20,top+7);
        RectF bottomRect = new RectF(left, bottom-7, left+20, bottom);
        RectF centerRect = new RectF(left+7, top, left+13, bottom);
        canvas.drawRect(topRect, line1);
        canvas.drawRect(bottomRect, line1);
        canvas.drawRect(centerRect, line1);
    }
    private void drawArrowup(Canvas canvas, Paint paint, float left, float top) {

        Path path = new Path();
        path.moveTo(left+8, top); // Top
        path.lineTo(left, top+10); // Bottom left
        path.lineTo(left+16, top+10); // Bottom right
        path.lineTo(left+8, top); // Back to Top
        RectF center = new RectF(left+4, top+10, left+12, top+38);
        canvas.drawRect(center, line1);
        path.close();

        canvas.drawPath(path, paint);
    }

    private void drawArrowdown(Canvas canvas, Paint paint, float left, float top) {

        Path path = new Path();
        path.moveTo(left+8, top+38); // Top
        path.lineTo(left, top+28); // Bottom left
        path.lineTo(left+16, top+28); // Bottom right
        path.lineTo(left+8, top+38); // Back to Top
        RectF center = new RectF(left+4, top+2, left+12, top+28);
        canvas.drawRect(center, line1);
        path.close();

        canvas.drawPath(path, paint);
    }
    Paint cursor = new Paint();
    Paint line1 = new Paint();
    Rect theCursor = new Rect();
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // draw notes
        int currentlength = 0;
        int currentlinenumber = 0;
        Paint arc = new Paint();
        arc.setStyle(Paint.Style.STROKE);
        for (int i = 0; i < lengths.length; i++) {
            currentlength  = currentlength + 32 / lengths[i];
            int currentlengthPerLine = (currentlength - 32/lengths[i]) % 64;
            currentlinenumber = (int)Math.ceil(((float)currentlength) /64f);
            int preveousLinenumber = (int)Math.ceil(((float)currentlength-32/lengths[i]) /64f);
            float left = (marginX + currentlengthPerLine*unit);
            float top;
            top = 40 + 320 * (currentlinenumber-1) + 40 * xianNotes[i];
            if(preveousLinenumber<currentlinenumber&&currentlengthPerLine!=0){
                top = 40 + 320 * (currentlinenumber-2) + 40 * xianNotes[i];
            }
            RectF rect = new RectF(left, top, left + 40, top + 40);
            arc.setStyle(Paint.Style.STROKE);
            switch (notes[i]) {
                case "撮":
                    RectF newrect = new RectF(left, top-80, left + 40, top - 40);
                    canvas.drawArc(newrect, 0, 360, false, arc);
                    drawCursor(canvas, left-10, top, left+40, top+40);
                    canvas.drawText(huiNotes[i], left+20, top+30, huipaint);
                    break;
                case "空":
                    break;
                case "绰":
                    drawArrowup(canvas,line1,left-30, top);
                    drawCursor(canvas, left-10, top, left+40, top+40);
                    canvas.drawText(huiNotes[i], left+20, top+30, huipaint);
                    break;
                case "注":
                    drawArrowdown(canvas,line1,left-30,top);
                    drawCursor(canvas, left-10, top, left+40, top+40);
                    canvas.drawText(huiNotes[i], left+20, top+30, huipaint);
                    break;
                case "上":
                    drawArrowup(canvas,line1,left-30, top);
                    drawCursor(canvas, left-10, top, left+40, top+40);
                    canvas.drawText(huiNotes[i], left+20, top+30, huipaint);
                    break;
                case "下":
                    drawArrowdown(canvas,line1,left-30,top);
                    drawCursor(canvas, left-10, top, left+40, top+40);
                    canvas.drawText(huiNotes[i], left+20, top+30, huipaint);
                    break;
                case "散音":
                    canvas.drawArc(rect, 0, 360, false, arc);
                    break;
                case "按音":
                    drawCursor(canvas, left-10, top, left+40, top+40);
                    canvas.drawText(huiNotes[i], left+20, top+30, huipaint);
                    break;
                case "泛音":
                    canvas.drawArc(rect, 0, 360, false, arc);
                    canvas.drawText(huiNotes[i],left+17,top+30, huipaint);
                    Log.i("newline",",previousline="+Math.ceil(((float)currentlength-32/lengths[i]) /64f)+",currentLine"+currentlinenumber);
                    Log.i("mview",",linenumber=("+currentlength+"-"+32 / lengths[i]+")/64="+((float)currentlength - 32f/lengths[i]) /64f+",left="+marginX+"+"+currentlengthPerLine*unit + ",drawing circle:" + ",left=" + left + ",top=" + top + ",currentlengthperline=" + currentlengthPerLine);
                    break;

            }
        }
        //draw grids
        for (int i = 0; i < currentlinenumber; i++) {
            //draw horizontal
            for (int j = 0; j < 8; j++) {
                canvas.drawLine(marginX, 40 + i * 320 + j * 40, screenX - marginX, 40 + i * 320 + j * 40, line1);
            }
            //draw verticle
            for (int j = 0; j < spaceperline + 1; j++) {
                canvas.drawLine(marginX + betweenBars * j, 40 + i * 320, marginX + betweenBars * j, 320 + i * 320, line1);
            }
        }

    }
}

