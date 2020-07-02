package com.csnt.ui_editbox;

import android.content.Context;
import android.content.res.ColorStateList;
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

import androidx.annotation.ColorInt;
import androidx.annotation.StringRes;


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
    private int dip2px(float dpValue) {
        final float scale = mContext.getResources()
                .getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
    private void setText(CharSequence str){
        editText.setText(str);
    }
    private void setText(@StringRes int resid){
        editText.setText(getContext().getResources().getText(resid));
    }
    private void setHint(CharSequence str){
        editText.setHint(str);
    }
    private void setHint(@StringRes int resid){
        editText.setHint(getContext().getResources().getText(resid));
    }
    private void setTextColor(@ColorInt int color){
        editText.setTextColor(color);
    }
    private void setTextColor( ColorStateList color){
        editText.setTextColor(color);
    }
    private void setHintColor(@ColorInt int color){
        editText.setHintTextColor(color);
    }
    private void setHintColor( ColorStateList color){
        editText.setHintTextColor(color);
    }
    public static class Builder{
        private CharSequence text;
        private ColorStateList textColor;
        private CharSequence hint;
        private ColorStateList hintColor;
        private int width=LayoutParams.MATCH_PARENT;
        private int height=LayoutParams.MATCH_PARENT;
        private Context mContext;
        public Builder(Context context){
            this.mContext=context;
        };
        public Builder setText(@StringRes int resId){
            this.text=  mContext.getResources().getText(resId);
            return this;
        }
        public Builder setText(CharSequence text){
            this.text= text;
            return this;
        }
        public Builder setHint(@StringRes int resId){
            this.hint=  mContext.getResources().getText(resId);
            return this;
        }
        public Builder setHint(CharSequence text){
            this.hint= text;
            return this;
        }
        public Builder setTextColor(@StringRes int colorId){
            this.textColor= ColorStateList.valueOf(colorId);
            return this;
        }
        public Builder setTextColor(ColorStateList color){
            this.textColor= color;
            return this;
        }
        public Builder setHintColor(@StringRes int colorId){
            this.hintColor= ColorStateList.valueOf(colorId);
            return this;
        }
        public Builder setHintColor(ColorStateList color){
            this.hintColor= color;
            return this;
        }
        public Builder setWidth(int width){
            this.width=dip2px(width);
            return this;
        }
        public Builder setHeight(int height){
            this.height=dip2px(height);
            return this;
        }

        private int dip2px(float dpValue) {
            final float scale = mContext.getResources()
                    .getDisplayMetrics().density;
            return (int) (dpValue * scale + 0.5f);
        }
        public EditBox build(){
            EditBox editBox = new EditBox(mContext);
            LayoutParams layoutParams = new LayoutParams(width, height);
            editBox.setLayoutParams(layoutParams);

            if(text!=null){
                editBox.setText(text);
            }
            if(textColor!=null){
                editBox.setTextColor(textColor);
            }
            if(hint!=null){
                editBox.setHint(hint);
            }
            if(hintColor!=null){
                editBox.setHintColor(hintColor);
            }
            return editBox;
        }
    }

}
