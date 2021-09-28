package site.aoba.android.vot.common.databindingadapters;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import androidx.databinding.BindingAdapter;
import com.google.android.material.textfield.TextInputLayout;
import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FormBindingAdapter {

    @BindingAdapter({"validation", "errorMessage"})
    public static void setValidationEnable(TextInputLayout textInputLayout, @NotNull String stringRule, @NotNull String errorMessage) {
        if (!textInputLayout.isErrorEnabled()) return;
        textInputLayout.getEditText().addTextChangedListener(new TextWatcher() {
            private final Pattern pattern = Pattern.compile(stringRule);
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Matcher matcher = pattern.matcher(textInputLayout.getEditText().getText());
                if (!matcher.matches()) {
                    textInputLayout.setError(errorMessage);
                } else {
                    textInputLayout.setError(null);
                }
            }
        });
    }

    @BindingAdapter("EnableImeAutoHide")
    public void setEnableImeAutoHide(View view, Boolean enable) {
        if (!enable) {
            view.setOnFocusChangeListener(null);
            return;
        }
        View x = view.getRootView().getRootView();
        view.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    InputMethodManager imm =  (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        });
    }
}
