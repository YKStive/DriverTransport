package com.tengbo.module_order.ui.task;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.tengbo.basiclibrary.utils.LogUtil;
import com.tengbo.commonlibrary.base.BaseApplication;
import com.tengbo.commonlibrary.base.BaseMvpFragment;
import com.tengbo.module_order.R;
import com.tengbo.module_order.adapter.TaskListAdapter;
import com.tengbo.module_order.bean.Order;

import java.util.ArrayList;
import java.util.List;

import utils.ToastUtils;


public class TaskListFragment extends BaseMvpFragment<TaskContract.Presenter> implements TaskContract.View {

    RecyclerView rvTask;

    private List<Order> mTasks = new ArrayList<>();
    private TaskListAdapter mAdapter;

    public static TaskListFragment newInstance() {
        return new TaskListFragment();
    }

    @Override
    protected void initPresent() {
        mPresent = new TaskListPresenter();
        mPresent.bindView(this);
    }

    /**
     *
     */
    @Override
    protected void initView() {
        LogUtil.d("initView");
        rvTask = mRootView.findViewById(R.id.rv_task);

        mAdapter = new TaskListAdapter(mTasks);
        mAdapter.openLoadAnimation();
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                TaskDetailActivity.start(_mActivity, (Order) adapter.getItem(position));
            }
        });
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                Order order = (Order) adapter.getItem(position);
                int id = view.getId();
                if (id == R.id.btn_accept_task) {
                    ToastUtils.show(_mActivity, "接受编号为" + order.getOrderNum() + "的订单");
                } else if (id == R.id.btn_reject_task) {
                    ToastUtils.show(_mActivity, "拒绝编号为" + order.getOrderNum() + "的订单");
                } else if (id == R.id.btn_start_task) {
                    ToastUtils.show(_mActivity, "开始编号为" + order.getOrderNum() + "的订单");
                }
            }
        });

        rvTask.setLayoutManager(new LinearLayoutManager(BaseApplication.get()));
        rvTask.setAdapter(mAdapter);

    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_task;
    }

    @Override
    protected void getTransferData(Bundle arguments) {

    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        mPresent.getTasks();
    }


    @Override
    public void showTasks(List<Order> tasks) {
        mTasks = tasks;
        mAdapter.setNewData(mTasks);
    }

    @Override
    public void changeStatusSuccess(int status) {

    }
}
