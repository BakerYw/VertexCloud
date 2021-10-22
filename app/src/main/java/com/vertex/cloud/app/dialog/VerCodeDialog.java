package com.vertex.cloud.app.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.vertex.cloud.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * @Author CHEESE
 * @Version 1.0
 * @Describe 验证码弹出框
 **/
public class VerCodeDialog extends Dialog {

    @BindView(R.id.dialog_ver_code_iv_del)
    ImageView dialogVerCodeIvDel;
    @BindView(R.id.dialog_ver_code_ed_code)
    EditText dialogVerCodeEdCode;
    @BindView(R.id.dialog_ver_code_btn_confirm)
    Button dialogVerCodeBtnConfirm;

    private Context mContext;
    private OnVerCodeConfirmListener codeConfirmListener;

    public VerCodeDialog(@NonNull Context context) {
        super(context, R.style.all_dialog);
        this.mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_ver_code);
        ButterKnife.bind(this);

        WindowManager windowManager = this.getWindow().getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.height = (int) display.getHeight();
        layoutParams.width = (int) display.getWidth();
        //layoutParams.width = (int) (display.getWidth() * 0.5);// 宽度设置为屏幕的0.5
        getWindow().setAttributes(layoutParams);
    }

    @OnClick({R.id.dialog_ver_code_iv_del, R.id.dialog_ver_code_btn_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.dialog_ver_code_iv_del:
                dismiss();
                break;
            case R.id.dialog_ver_code_btn_confirm:
                codeConfirmListener.onVerCodeConfirmClick();
                dismiss();
                break;
        }
    }

    public void setOnRoomLockListener(OnVerCodeConfirmListener codeConfirmListener) {
        this.codeConfirmListener = codeConfirmListener;
    }

    public interface OnVerCodeConfirmListener {
        void onVerCodeConfirmClick();
    }

}
