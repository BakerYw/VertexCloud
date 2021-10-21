package com.vertex.cloud.app.widget.loadsir;

import com.kingja.loadsir.callback.Callback;
import com.vertex.cloud.R;

public class LoadingCallback extends Callback {
    @Override
    protected int onCreateView() {
        return R.layout.loading_common_layout;
    }

}
