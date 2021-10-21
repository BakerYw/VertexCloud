package com.vertex.cloud.mvp.ui.main.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.blankj.utilcode.util.ToastUtils;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.jess.arms.di.component.AppComponent;
import com.vertex.cloud.R;
import com.vertex.cloud.app.base.CloudBaseFragment;
import com.vertex.cloud.di.component.DaggerMainComponent;
import com.vertex.cloud.mvp.contract.MainContract;
import com.vertex.cloud.mvp.presenter.MainPresenter;
import com.vertex.cloud.mvp.ui.infomation.InfoMationFragment;
import com.vertex.cloud.mvp.ui.message.MessageFragment;
import com.vertex.cloud.mvp.ui.mine.MineFragment;
import com.vertex.cloud.mvp.ui.work.WorkFragment;

import java.util.ArrayList;
import java.util.Random;

import butterknife.BindView;
import me.yokeyword.fragmentation.SupportFragment;
import q.rorbin.badgeview.Badge;
import q.rorbin.badgeview.QBadgeView;

/**
 * @Author CHEESE
 * @Date 10/21/21 11:45 AM
 * @Version 1.0
 **/
public class MainFragment extends CloudBaseFragment<MainPresenter> implements MainContract.View{

    @BindView(R.id.main_bnve)
    BottomNavigationViewEx mBnve;
    private ArrayList<SupportFragment> mFragments = new ArrayList<>();

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerMainComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build().inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        InfoMationFragment infoFragment = findChildFragment(InfoMationFragment.class);
        if (infoFragment == null) {
            mFragments.add(0, InfoMationFragment.newInstance());
            mFragments.add(1, MessageFragment.newInstance());
            mFragments.add(2, WorkFragment.newInstance());
            mFragments.add(3, MineFragment.newInstance());
            loadMultipleRootFragment(R.id.main_frame_layout, 0, mFragments.get(0)
                    , mFragments.get(1), mFragments.get(2), mFragments.get(3));
        } else {
            mFragments.add(0, infoFragment);
            mFragments.add(1, findChildFragment(MessageFragment.class));
            mFragments.add(2, findChildFragment(WorkFragment.class));
            mFragments.add(3, findChildFragment(MineFragment.class));
        }
        mBnve.enableAnimation(false);
        mBnve.enableShiftingMode(false);
        mBnve.enableItemShiftingMode(false);
        mBnve.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.menu_info:
                    showHideFragment(mFragments.get(0));
                    break;
                case R.id.menu_message:
                    showHideFragment(mFragments.get(1));
                    break;
                case R.id.menu_work:
                    showHideFragment(mFragments.get(2));
                    break;
                case R.id.menu_mine:
                    showHideFragment(mFragments.get(3));
                    break;
            }
            return true;
        });
        addBadgeAt(1,21);
    }


    @Override
    public void setData(@Nullable Object data) {

    }

    private Badge addBadgeAt(int position, int number) {
        // add badge
        return new QBadgeView(getContext())
                .setBadgeNumber(number)
                .setGravityOffset(15, 2, true)
                .bindTarget(mBnve.getBottomNavigationItemView(position))
                .setOnDragStateChangedListener((dragState, badge, targetView) -> {
                    if (Badge.OnDragStateChangedListener.STATE_SUCCEED == dragState)
                        ToastUtils.showShort("你把消息偷走了！");
                        addBadgeAt(position,0);
                });
    }
}
