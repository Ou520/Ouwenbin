package com.example.myapplication.ouwenbin.controller.fragmemt.PeopleNearby;


import android.view.View;
import android.widget.Toast;
import com.example.myapplication.ouwenbin.R;
import com.example.myapplication.ouwenbin.controller.fragmemt.BaseFagment;
import com.scwang.smartrefresh.header.BezierCircleHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;


public class PeopleNearbyFragment extends BaseFagment {

    private View view;
    private RefreshLayout refreshLayout;//下拉刷新上拉加载更多

    public PeopleNearbyFragment() { }


    @Override
    protected View initView() {
        view =View.inflate(mContext, R.layout.fragment_people_nearby,null);
        initView(view);
        return view;
    }

    @Override
    protected void initData() {
        getData();
        setData();
        setListener();
    }


    private void initView(View view) {
        //变量的初始化工作
        refreshLayout = view.findViewById(R.id.refreshLayout);
    }

    private void getData() {

    }

    private void setData() {
        //设置RefreshLayout的属性
        //设置 Header 样式:
        refreshLayout.setRefreshHeader(new BezierCircleHeader(getContext()));
        //设置 Footer 为 球脉冲 样式
        refreshLayout.setRefreshFooter(new BallPulseFooter(getContext()).setSpinnerStyle(SpinnerStyle.Scale));
    }

    private void setListener() {

        //下拉刷新的监听
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                Toast.makeText(getContext(), "刷新成功！！", Toast.LENGTH_SHORT).show();

                //建议使用异步任务

                refreshlayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败
            }
        });

        //上拉加载的监听
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                Toast.makeText(getContext(), "加载成功！！", Toast.LENGTH_SHORT).show();
                refreshlayout.finishLoadMore(2000/*,false*/);//传入false表示加载失败
            }
        });


    }


}
