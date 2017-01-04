package com.xingye.netzoo.zooapplication.login;

import android.databinding.BindingAdapter;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

/**
 * Created by yx on 16/12/27.
 */

public class LoginVMComponent implements android.databinding.DataBindingComponent {

    @BindingAdapter("loginbind:text")
    public static void justSettext(TextView view, String tips)
    {
        int length = tips.length();
        Spannable wordtoSpan = new SpannableString(tips);
        wordtoSpan.setSpan(new ForegroundColorSpan(0xffCE393A), length-3, length-1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        view.setText(wordtoSpan);
    }
}
