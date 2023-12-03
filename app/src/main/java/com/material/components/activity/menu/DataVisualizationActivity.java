package com.material.components.activity.menu;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
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
import com.material.components.R;
import com.material.components.activity.card.SurveyActivity;
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

public class DataVisualizationActivity extends AppCompatActivity {

    private ActionBar actionBar;
    private Toolbar toolbar;

    private DrawerLayout root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_drawer_news);

        initRoot();
        initToolbar();
        initStatusBar();
        initNavigationMenu();
        initFloatButton();
        initRecyclerView();
        initDrawerHeader();

        getLifecycle().addObserver(observer);
    }


    private final LifecycleObserver observer = new DefaultLifecycleObserver() {
        @Override
        public void onResume(@NonNull LifecycleOwner owner) {
            getData(true);
        }

    };

    private void initDrawerHeader() {

    }


    private void initRoot(){
        root = findViewById(R.id.drawer_layout);
    }

    private void initFloatButton() {
        ((FloatingActionButton) findViewById(R.id.fab_add)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new android.content.Intent(DataVisualizationActivity.this, SurveyActivity.class));
            }
        });
    }

    StaggedAdapter<DataVisualizationModel> staggedAdapter;
    StaggerdRecyclerView str;

    private void initRecyclerView() {

        str = findViewById(R.id.staggerd_recyclerView);
        staggedAdapter = new StaggedAdapter<>(this);
        str.link(staggedAdapter, 2);

        //动画效果
        str.addAnimation(R.anim.right_to_left);
        //间距
        str.addDecoration(new GridItemDecoration(this, 10));

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



    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setTitle("Health Data");
    }


    private void initStatusBar() {
        Tools.setSystemBarColor(this, R.color.white);
        Tools.setSystemBarLight(this);
    }


    private void initNavigationMenu() {
        NavigationView nav_view = (NavigationView) findViewById(R.id.nav_view);
        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        View headerView = nav_view.getHeaderView(0);

        TextView nameView = headerView.findViewById(R.id.google_account_name);
        TextView emailView = headerView.findViewById(R.id.email);
        CircularImageView imageView = headerView.findViewById(R.id.avatar);

        GoogleAccountData data = GoogleSignInManager.getInstance().getGoogleAccountData();
        if (data != null) {
            nameView.setText(data.getDisplayName());
            emailView.setText(data.getAccountId());
            Glide.with(this).load(data.getProfilePictureUri()).into(imageView);
        }


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(final MenuItem item) {
                if (item.getItemId() == R.id.nav_report_time) {
                    dialogTimePickerLight();
                } else if (item.getItemId() == R.id.nav_report_frequency) {
                    showSingleChoiceDialog();
                } else if (item.getItemId() == R.id.nav_contact_us) {
                    showDialogContactUS();
                }
                return true;
            }
        });
    }

    private void showSingleChoiceDialog() {
        single_choice_selected = Frequency[0];
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Your preferred report frequency");
        builder.setSingleChoiceItems(Frequency, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                single_choice_selected = Frequency[i];
            }
        });
        builder.setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Snackbar.make(root, "selected : " + single_choice_selected, Snackbar.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton(R.string.CANCEL, null);
        builder.show();
    }


    private String single_choice_selected;

    private static final String[] Frequency = new String[]{
            "daily", "once every two days", "once every three days"

    };

    private void showDialogContactUS() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(R.layout.dialog_contact_us);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCancelable(true);
        (dialog.findViewById(R.id.dialog_contact_us_close)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }


    private void dialogTimePickerLight() {
        Calendar cur_calender = Calendar.getInstance();
        TimePickerDialog datePicker = TimePickerDialog.newInstance(new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
                Snackbar.make(root, "selected : " + hourOfDay + ":" + minute, Snackbar.LENGTH_SHORT).show();
            }
        }, cur_calender.get(Calendar.HOUR_OF_DAY), cur_calender.get(Calendar.MINUTE), true);
        //set dark light
        datePicker.setThemeDark(false);
        datePicker.setAccentColor(getResources().getColor(R.color.colorPrimary));
        datePicker.show(getFragmentManager(), "Timepickerdialog");
    }



    class StaggedAdapter<T extends StaggedModel> extends com.atech.staggedrv.StaggedAdapter<T> {

        StaggedAdapter(Context c) {
            super(c);
        }


        @Override
        public RecyclerView.ViewHolder addViewHolder(ViewGroup viewGroup, int i) {
            //绑定自定义的viewholder
            View v = LayoutInflater.from(DataVisualizationActivity.this).inflate(R.layout.custom_item_layout2, viewGroup, false);
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