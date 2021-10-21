package com.vertex.cloud.mvp.ui.work;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.blankj.utilcode.util.ToastUtils;
import com.google.android.flexbox.FlexboxLayout;
import com.jess.arms.di.component.AppComponent;
import com.vertex.cloud.R;
import com.vertex.cloud.app.base.CloudBaseFragment;
import com.vertex.cloud.di.component.DaggerWorkComponent;
import com.vertex.cloud.mvp.contract.WorkContract;
import com.vertex.cloud.mvp.presenter.WorkPresenter;

import butterknife.BindView;
import butterknife.OnClick;

public class WorkFragment extends CloudBaseFragment<WorkPresenter> implements WorkContract.View {

    @BindView(R.id.tv_company)
    TextView tvCompany;
    @BindView(R.id.tv_project)
    TextView tvProject;
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.data_container)
    LinearLayout dataContainer;
    @BindView(R.id.function_container)
    LinearLayout functionContainer;

    public static WorkFragment newInstance() {
        WorkFragment fragment = new WorkFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerWorkComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_work, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initDataView();
        initFunctionView();
    }

    /**
     * 初始化功能view
     */
    private void initFunctionView() {
        functionContainer.addView(createFunctionItem("项目管理",R.drawable.ic_fun_01));
        functionContainer.addView(createFunctionItem("合同管理",R.drawable.ic_fun_02));
        functionContainer.addView(createFunctionItem("工程进度",R.drawable.ic_fun_03));
        functionContainer.addView(createFunctionItem("结算管理",R.drawable.ic_fun_04));
        functionContainer.addView(createFunctionItem("财务管理",R.drawable.ic_fun_05));
    }

    /**
     * 初始化数据view
     */
    private void initDataView() {
        dataContainer.addView(createDataItem("在建项目",11));
        dataContainer.addView(createDataItem("待审批",25));
        dataContainer.addView(createDataItem("待签署",536));
        dataContainer.addView(createDataItem("待结算",452));
    }

    @Override
    public void setData(@Nullable Object data) {

    }


    @OnClick({R.id.tv_company, R.id.tv_project})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_company:
                ToastUtils.showShort("你点击了公司");
                break;
            case R.id.tv_project:
                ToastUtils.showShort("你点击了项目");
                break;
        }
    }


    /**
     * 创建数据统计布局
     */
    private View createDataItem(String title, int num){
        View view= LayoutInflater.from(mContext).inflate(R.layout.item_data_layout,dataContainer,false);
        TextView mTvNum = view.findViewById(R.id.tv_num);
        TextView mTvTitle = view.findViewById(R.id.tv_title);
        mTvTitle.setText(title);
        mTvNum.setText(num+"");
        return view;
    }

    /**
     * 创建数据统计布局
     */
    private View createFunctionItem(String title, @DrawableRes int res){
        View view= LayoutInflater.from(mContext).inflate(R.layout.item_fun_layout,functionContainer,false);
        ImageView mIvPic = view.findViewById(R.id.iv_pic);
        TextView mTvTitle = view.findViewById(R.id.tv_title);
        mIvPic.setImageResource(res);
        mTvTitle.setText(title);
        return view;
    }
}