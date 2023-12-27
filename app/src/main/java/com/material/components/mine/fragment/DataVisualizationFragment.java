package com.material.components.mine.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.atech.staggedrv.GridItemDecoration;
import com.atech.staggedrv.StaggerdRecyclerView;
import com.atech.staggedrv.callbacks.LoadMoreAndRefresh;
import com.atech.staggedrv.model.StaggedModel;
import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.hjq.permissions.OnPermissionCallback;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;
import com.material.components.R;
import com.material.components.activity.about.AboutCompanyImage;
import com.material.components.activity.card.SurveyActivity;
import com.material.components.activity.settings.SettingSectioned;
import com.material.components.mine.CustomBarChartView;
import com.material.components.mine.DataVisualizationModel;
import com.material.components.mine.healthdata.HealthDataManager;
import com.material.components.mine.login.GoogleAccountData;
import com.material.components.mine.login.GoogleSignInManager;
import com.material.components.utils.Tools;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DataVisualizationFragment extends Fragment {

    private FrameLayout root;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = (FrameLayout) inflater.inflate(R.layout.fragment_menu_drawer_news, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getLifecycle().addObserver(observer);
        initFloatButton();
        initRecyclerView();
        initDrawerHeader();
    }

    private final LifecycleObserver observer = new DefaultLifecycleObserver() {
        @Override
        public void onResume(@NonNull LifecycleOwner owner) {
            getData(true);
        }

    };

    private void initDrawerHeader() {

    }


    private void initFloatButton() {
        ((FloatingActionButton) root.findViewById(R.id.fab_add)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new android.content.Intent(getActivity(), SurveyActivity.class));
            }
        });
    }

    StaggedAdapter<DataVisualizationModel> staggedAdapter;
    StaggerdRecyclerView str;

    private void initRecyclerView() {

        str = root.findViewById(R.id.staggerd_recyclerView);
        staggedAdapter = new StaggedAdapter<>(getActivity());
        str.link(staggedAdapter, 2);

        //动画效果
        str.addAnimation(R.anim.right_to_left);
        //间距
        str.addDecoration(new GridItemDecoration(getActivity(), 10));

        str.addCallbackListener(new LoadMoreAndRefresh() {
            @Override
            public void onLoadMore() {
                getData(true);
            }

            @Override
            public void onRefresh() {
                getData(true);
            }
        });
    }

    private void getData(final boolean refresh) {
        if (refresh) {
            staggedAdapter.refresh(HealthDataManager.getInstance().getData());
        }
    }


    class StaggedAdapter<T extends StaggedModel> extends com.atech.staggedrv.StaggedAdapter<T> {

        StaggedAdapter(Context c) {
            super(c);
        }


        @Override
        public RecyclerView.ViewHolder addViewHolder(ViewGroup viewGroup, int i) {
            //绑定自定义的viewholder
            View v = LayoutInflater.from(getContext()).inflate(R.layout.custom_item_layout2, viewGroup, false);
            return new MyHolder(v);
        }

        @Override
        public void bindView(RecyclerView.ViewHolder viewHolder, int i) {
            MyHolder myHolder = (MyHolder) viewHolder;
            myHolder.titleView.setText(((DataVisualizationModel) datas.get(i)).getTitle());
            myHolder.valueView.setText(((DataVisualizationModel) datas.get(i)).getValue());
            String change = ((DataVisualizationModel) datas.get(i)).getChange();
            if (change.isEmpty()) {
                myHolder.changeView.setVisibility(View.GONE);
            } else {
                myHolder.changeView.setVisibility(View.VISIBLE);
                myHolder.changeView.setText(change);
            }

            ArrayList<Float> scores = ((DataVisualizationModel) datas.get(i)).getScores();
            if (scores != null) {
                myHolder.barChartView.setScores(scores);
                myHolder.barChartView.setVisibility(View.VISIBLE);
            } else {
                myHolder.barChartView.setVisibility(View.GONE);
            }
        }
    }


    /**
     * 自定义viewholder
     */

    class MyHolder extends RecyclerView.ViewHolder {
        TextView titleView;
        TextView valueView;
        TextView changeView;

        CustomBarChartView barChartView;


        public MyHolder(@NonNull View itemView) {
            super(itemView);
            titleView = itemView.findViewById(R.id.indicator_name_view);
            valueView = itemView.findViewById(R.id.indicator_value_view);
            changeView = itemView.findViewById(R.id.indicator_change_view);
            barChartView = itemView.findViewById(R.id.bar_chart_view);
        }
    }


}