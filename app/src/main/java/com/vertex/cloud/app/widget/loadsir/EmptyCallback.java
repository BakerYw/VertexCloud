package com.vertex.cloud.app.widget.loadsir;

import com.kingja.loadsir.callback.Callback;
import com.vertex.cloud.R;

public class EmptyCallback extends Callback {
    @Override
    protected int onCreateView() {
        return R.layout.load_layout_empty;
    }
}
