package com.csnt.ui_editbox;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Rect;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;


/**
 * 自定义的输入框，可以在有类容的时候显示一个叉
 */
public class EditBox extends RelativeLayout {
    private boolean isShowClean = true;//默认是显示的
    private boolean hasFocus = false;

    private Rect rect;
    private EditText editText;
    private ImageView imageView;
    private Context mContext;

    /**
     * 获得editText
     *
     * @Author: JACK-GU
     * @E-Mail: 528489389@qq.com
     */
    public EditText getEditText() {
        return editText;
    }

    /**
     * 获得字符串，不会为null
     *
     * @Author: JACK-GU
     * @E-Mail: 528489389@qq.com
     */
    public String getText() {
        return editText.getText().toString();
    }

    public EditBox(Context context) {
        super(context);
        this.mContext=context;
        initView(context, null);
    }

    public EditBox(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext=context;
        initView(context, attrs);
    }

    public EditBox(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext=context;
        initView(context, attrs);
    }

    /**
     * 初始化
     *
     * @Author: JACK-GU
     * @E-Mail: 528489389@qq.com
     */
    private void initView(Context mContext, AttributeSet attrs) {
//        if (attrs != null) {
//            TypedArray a = mContext.obtainStyledAttributes(attrs, R.styleable.MyEditText);
//            isShowClean = a.getBoolean(R.styleable.MyEditText_myEditTextShowClear, true);
//            a.recycle();
//            setPadding(0, 0, 0, 0);
//            setBackgroundColor(Color.TRANSPARENT);
//        }

        editText = new EditText(mContext, attrs);
        LayoutParams layoutParams = new LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        layoutParams.leftMargin = 0;
        layoutParams.bottomMargin = 0;
        layoutParams.topMargin = 0;
        layoutParams.rightMargin = 0;
        addView(editText, layoutParams);

        rect = new Rect(editText.getPaddingLeft(), editText.getPaddingTop(),
                editText.getPaddingRight(), editText.getPaddingBottom());

        imageView = new ImageView(mContext);
        imageView.setImageResource(android.R.drawable.ic_delete);
        LayoutParams imageViewLayoutParams = new LayoutParams(
                (int) editText.getTextSize(), (int) editText.getTextSize());
        imageViewLayoutParams.setMargins(0, 0, rect.right, 0);
        imageViewLayoutParams.addRule(ALIGN_PARENT_RIGHT);
        imageViewLayoutParams.addRule(CENTER_VERTICAL);

        imageView.setLayoutParams(imageViewLayoutParams);
        imageView.setVisibility(GONE);
        addView(imageView);

        editText.setOnFocusChangeListener((v, hasFocus) -> {
            EditBox.this.hasFocus = hasFocus;
            if (hasFocus && isShowClean && !TextUtils.isEmpty(editText.getText())) {
                show();
            } else {
                hide();
            }
        });

        imageView.setOnClickListener(v -> editText.setText(null));


        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (hasFocus && isShowClean && !TextUtils.isEmpty(s)) {
                    show();
                } else {
                    hide();
                }
            }
        });
    }

    /**
     * 恢复
     *
     * @Author: JACK-GU
     * @E-Mail: 528489389@qq.com
     */
    private void hide() {
        imageView.setVisibility(View.GONE);
        //恢复
        editText.setPadding(rect.left, rect.top,
                rect.right, rect.bottom);
    }

    /**
     * 显示清除按钮
     *
     * @Author: JACK-GU
     * @E-Mail: 528489389@qq.com
     */
    private void show() {
        //设置padding
        editText.setPadding(rect.left, rect.top,
                (int) (rect.right + dip2px(2) +
                        editText.getTextSize()), rect.bottom);

        imageView.setVisibility(VISIBLE);
    }
    private  int dip2px(float dpValue) {
        final float scale = mContext.getResources()
                .getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
