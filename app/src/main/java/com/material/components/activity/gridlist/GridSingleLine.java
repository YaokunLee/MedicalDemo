package com.material.components.activity.gridlist;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.material.components.R;
import com.material.components.adapter.AdapterGridSingleLine;
import com.material.components.adapter.StaggeredGridLayoutAdapter;
import com.material.components.utils.Tools;
import com.material.components.widget.SpacingItemDecoration;

import java.util.Arrays;
import java.util.List;

public class GridSingleLine extends AppCompatActivity {

    private View parent_view;

    private RecyclerView recyclerView;
    private AdapterGridSingleLine mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_single_line);
        parent_view = findViewById(android.R.id.content);

        initToolbar();
        initComponent();
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_menu);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Singe Line");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Tools.setSystemBarColor(this);
    }


    private void initComponent() {

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.addItemDecoration(new SpacingItemDecoration(2, Tools.dpToPx(this, 3), true));
//        recyclerView.setHasFixedSize(true);

        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        List<String> descriptions = Arrays.asList("描述1", "描述2", "图片3", "描述1", "描述2", "图片3", "描述1", "描述2",
                "图片3", "描述1", "描述2", "图片3", "描述1", "描述2", "图片3", "描述1", "描述2", "图片3","描述1", "描述2", "图片3", "描述1", "描述2", "图片3", "描述1", "描述2",
                "图片3", "描述1", "描述2", "图片3", "描述1", "描述2", "图片3", "描述1", "描述2", "图片3");
        List<Integer> images = Arrays.asList(R.drawable.image_16, R.drawable.image_15,R.drawable.image_17,
                R.drawable.image_14, R.drawable.image_13,R.drawable.image_12,
                R.drawable.image_11, R.drawable.image_10,R.drawable.image_9,R.drawable.image_16, R.drawable.image_15,R.drawable.image_17,
                R.drawable.image_14, R.drawable.image_13,R.drawable.image_12,
                R.drawable.image_11, R.drawable.image_10,R.drawable.image_9, R.drawable.image_16, R.drawable.image_15,R.drawable.image_17,
                R.drawable.image_14, R.drawable.image_13,R.drawable.image_12,
                R.drawable.image_11, R.drawable.image_10,R.drawable.image_9,R.drawable.image_16, R.drawable.image_15,R.drawable.image_17,
                R.drawable.image_14, R.drawable.image_13,R.drawable.image_12,
                R.drawable.image_11, R.drawable.image_10,R.drawable.image_9);
        StaggeredGridLayoutAdapter adapter = new StaggeredGridLayoutAdapter(descriptions, images);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search_setting, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        } else {
            Toast.makeText(getApplicationContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

}
