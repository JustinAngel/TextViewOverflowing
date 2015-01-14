package net.justinangel.textoverflowexample;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewTreeObserver;
import android.widget.TextView;

public class TextViewOverflowing extends TextView
    implements ViewTreeObserver.OnGlobalLayoutListener
{
    public TextViewOverflowing(Context context) {
        super(context);
    }

    public TextViewOverflowing(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TextViewOverflowing(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        super.setText(text, type);

        ViewTreeObserver vto = getViewTreeObserver();
        vto.addOnGlobalLayoutListener(this);
    }

    @Override
    public void onGlobalLayout() {

        // only try to calculate ellipsized text if MaxLines were set
        if (getMaxLines() != Integer.MAX_VALUE) {
            // get the index of the Ellipsis in the line that has an elipsis
            int ellipIndex = getLayout().getLineEnd(getMaxLines() - 1);

            // get all the text after the elipsis
            setOverflowText((String) getText()
                    .subSequence(ellipIndex, getText().length()));

            getViewTreeObserver().removeOnGlobalLayoutListener(this);
        }

        // only do crazy truncation if we need too.
        // this math isn't 100% accurate, but it should never fail.
        boolean allTextVisible =
                (getLineHeight() * getLineCount()) <= getHeight();

        if (!allTextVisible) {
            // get the index of the last visible line
            int lastLineIndex = getHeight() / getLineHeight();

            setMaxLines(lastLineIndex);
        }
    }

    String overflowText;

    public String getOverflowText() {
        return overflowText;
    }

    private void setOverflowText(String overflowText) {
        this.overflowText = overflowText;
        invokeOverflowTextListener();
        updateOverflowTextView();
    }

    private void updateOverflowTextView() {
        if (getOverflowTextViewId() != 0) {
            if (getContext() instanceof Activity) {
                TextView overflowTextView = (TextView) ((Activity)getContext()).findViewById(overflowTextViewId);
                overflowTextView.setText(getOverflowText());
            }
        }
    }

    private OverflowTextListener overflowTextListener;

    public void setOverflowTextListener(OverflowTextListener listener){
        overflowTextListener = listener;
    }

    private void invokeOverflowTextListener() {
        OverflowTextListener listener = overflowTextListener;
        if (listener != null)
            listener.overflowTextCalculated(overflowText);
    }

    private int overflowTextViewId;

    public int getOverflowTextViewId() {
        return overflowTextViewId;
    }

    public void setOverflowTextViewId(int overflowTextViewId) {
        this.overflowTextViewId = overflowTextViewId;
    }

    public interface OverflowTextListener
    {
        void overflowTextCalculated(String overflowText);
    }
}

