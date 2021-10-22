package com.vertex.cloud.mvp.ui.mine;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.jess.arms.di.component.AppComponent;
import com.vertex.cloud.R;
import com.vertex.cloud.app.base.CloudBaseFragment;
import com.vertex.cloud.di.component.DaggerMineComponent;
import com.vertex.cloud.mvp.contract.MineContract;
import com.vertex.cloud.mvp.presenter.MinePresenter;

import butterknife.BindView;
import butterknife.OnClick;


public class MineFragment extends CloudBaseFragment<MinePresenter> implements MineContract.View {

    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_nickname)
    TextView tvNickname;
    @BindView(R.id.iv_scan)
    ImageView ivScan;
    @BindView(R.id.ll_mine_container)
    LinearLayout llMineContainer;

    public static MineFragment newInstance() {
        MineFragment fragment = new MineFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerMineComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mine, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initContainer();
    }

    private void initContainer() {
        llMineContainer.addView(createContainerItem("钱包",R.drawable.ic_wallet));
        llMineContainer.addView(createContainerItem("企业信息",R.drawable.ic_enterprise));
        llMineContainer.addView(createContainerItem("设置",R.drawable.ic_setting));
        llMineContainer.addView(createContainerItem("联系客服",R.drawable.ic_contact));
        llMineContainer.addView(createContainerItem("关于我们",R.drawable.ic_about));
    }

    /**
     * 创建数据统计布局
     */
    private View createContainerItem(String title, @DrawableRes int res){
        View view= LayoutInflater.from(mContext).inflate(R.layout.item_mine_layout,llMineContainer,false);
        ImageView mIvPic = view.findViewById(R.id.iv_pic);
        TextView mTvTitle = view.findViewById(R.id.tv_title);
        mIvPic.setImageResource(res);
        mTvTitle.setText(title);
        return view;
    }

    @Override
    public void setData(@Nullable Object data) {

    }


    @OnClick({R.id.tv_name, R.id.iv_scan})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_name:
                break;
            case R.id.iv_scan:
                break;
        }
    }
}