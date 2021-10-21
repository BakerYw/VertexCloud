package com.vertex.cloud.app.widget.loadsir;

import com.kingja.loadsir.callback.Callback;
import com.vertex.cloud.R;

public class ErrorCallback extends Callback {
    @Override
    protected int onCreateView() {
        return R.layout.load_layout_error;
    }
}
