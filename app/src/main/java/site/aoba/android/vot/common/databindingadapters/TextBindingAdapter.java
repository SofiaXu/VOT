package site.aoba.android.vot.common.databindingadapters;

import android.widget.TextView;
import androidx.databinding.BindingAdapter;

public class TextBindingAdapter {
    @BindingAdapter("bindingText")
    public static void setBindingText(TextView view, String text) {
        view.setText(text);
    }
}
