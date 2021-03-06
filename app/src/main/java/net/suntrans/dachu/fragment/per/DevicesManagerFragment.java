package net.suntrans.dachu.fragment.per;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.trello.rxlifecycle.android.FragmentEvent;
import com.trello.rxlifecycle.components.support.RxFragment;

import net.suntrans.dachu.R;
import net.suntrans.dachu.activity.DeviceDetailActivity;
import net.suntrans.dachu.activity.EnvDetailActivity;
import net.suntrans.dachu.adapter.DefaultDecoration;
import net.suntrans.dachu.api.RetrofitHelper;
import net.suntrans.dachu.bean.DeviceInfoResult;
import net.suntrans.dachu.rx.BaseSubscriber;
import net.suntrans.dachu.utils.LogUtil;
import net.suntrans.dachu.utils.UiUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.http.POST;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Looney on 2017/4/20.
 */

public class DevicesManagerFragment extends RxFragment {

    private static final String TAG = "DevicesManagerFragment";
    private DevicesAdapter adapter;
    private SwipeRefreshLayout refreshLayout;


    public static DevicesManagerFragment newInstance() {
        return new DevicesManagerFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_devicesmanager, container, false);
        setHasOptionsMenu(true);


        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        view.findViewById(R.id.fanhui).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        adapter = new DevicesAdapter(datas, getActivity());
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refreshlayout);
        recyclerView.addItemDecoration(new DefaultDecoration());
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData();
            }
        });


    }


    @Override
    public void onResume() {
        getData();
        super.onResume();
    }


    private List<DeviceInfoResult.DeviceInfo> datas = new ArrayList<>();

    public static class DevicesAdapter extends RecyclerView.Adapter {
        List<DeviceInfoResult.DeviceInfo> datas;
        private Context context;
        public int size = UiUtils.dip2px(96);

        public DevicesAdapter(List<DeviceInfoResult.DeviceInfo> datas, Context context) {
            this.datas = datas;
            this.context = context;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            return new ViewHolder(inflater.inflate(R.layout.item_device, parent, false));
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            ((ViewHolder) holder).setData(position);
        }

        @Override
        public int getItemCount() {
            return datas.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {


            ImageView imageView;
            TextView title;
            TextView name;
            TextView des;
            ImageView arrow;
            RelativeLayout root;

            public ViewHolder(View itemView) {
                super(itemView);
                imageView = (ImageView) itemView.findViewById(R.id.image);
                arrow = (ImageView) itemView.findViewById(R.id.arrow);
                title = (TextView) itemView.findViewById(R.id.title);
                name = (TextView) itemView.findViewById(R.id.name);
                des = (TextView) itemView.findViewById(R.id.des);
                root = (RelativeLayout) itemView.findViewById(R.id.root);

                root.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if ("4300".equals(datas.get(getAdapterPosition()).vtype)
                                || "4100".equals(datas.get(getAdapterPosition()).vtype)
                                || "4301".equals(datas.get(getAdapterPosition()).vtype)) {
                            Intent intent = new Intent(context, DeviceDetailActivity.class);
                            intent.putExtra("title", datas.get(getAdapterPosition()).device_name);
                            intent.putExtra("id", datas.get(getAdapterPosition()).id);
                            context.startActivity(intent);//fc561f
                        }else if ("6100".equals(datas.get(getAdapterPosition()).vtype)){
                            Intent intent = new Intent(context, EnvDetailActivity.class);
                            intent.putExtra("id", datas.get(getAdapterPosition()).id);
                            intent.putExtra("name", datas.get(getAdapterPosition()).device_name);
                            context.startActivity(intent);
                        }
                    }
                });

            }

            public void setData(int position) {
                des.setText(datas.get(position).product_name);
                name.setText(datas.get(position).device_name);
                if (datas.get(position).is_online.equals("1")) {
                    title.setText("在线");
                    title.setTextColor(Color.parseColor("#1e81d2"));

                } else {
                    title.setText("不在线");
                    title.setTextColor(Color.parseColor("#999999"));
                }
                int resID = R.drawable.ic_liutongdao;
                if (datas.get(position).vtype.equals("4100")) {//十通道
                    arrow.setVisibility(View.VISIBLE);
                    resID = R.drawable.ic_shitongdao;
                } else if (datas.get(position).vtype.equals("4300")) {//六通道

                    arrow.setVisibility(View.VISIBLE);
                    resID = R.drawable.ic_liutongdao;


                } else if (datas.get(position).vtype.equals("6100")) {//diliugan
                    resID = R.drawable.diliugan;
                    arrow.setVisibility(View.VISIBLE);


                } else if (datas.get(position).vtype.equals("4301")) {//三相两通道
                    resID = R.drawable.sanxiang2;
                    arrow.setVisibility(View.VISIBLE);
                }
                if (datas.get(position).device_name.contains("三相两通道")){
                    des.setText("三相两通道智能控制器");
                    resID = R.drawable.sanxiang2;
                }
                Glide.with(context)
                        .load(resID)
                        .dontTransform()
                        .crossFade()
                        .override(size, size)
                        .into(imageView);

            }
        }
    }

    public void getData() {
        RetrofitHelper.getApi().getDevicesInfo()
                .compose(this.<DeviceInfoResult>bindUntilEvent(FragmentEvent.DESTROY_VIEW))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new BaseSubscriber<DeviceInfoResult>(getActivity()) {
                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        e.printStackTrace();
                        if (refreshLayout != null)
                            refreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onNext(DeviceInfoResult deviceInfoResult) {
                        if (refreshLayout != null)
                            refreshLayout.setRefreshing(false);
                        if (deviceInfoResult != null) {
                            datas.clear();
                            if (deviceInfoResult.data.lists != null) {
                                datas.addAll(deviceInfoResult.data.lists);
                                adapter.notifyDataSetChanged();
                            }
                        }
                        if (datas.size() == 0) {
                            UiUtils.showToast("暂无数据");
                        }
                    }
                });
//                .subscribe(new Subscriber<DeviceInfoResult>() {
//                    @Override
//                    public void onCompleted() {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        e.printStackTrace();
//                        UiUtils.showToast("服务器错误!");
//                        if (refreshLayout!=null)
//                            refreshLayout.setRefreshing(false);
//                    }
//
//                    @Override
//                    public void onNext(DeviceInfoResult deviceInfoResult) {
//
//                    }
//                });
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            LogUtil.i(TAG + "可见");
        } else {
            LogUtil.i(TAG + "不可见");

        }
    }
}
