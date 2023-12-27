package com.material.components.activity.settings;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.material.components.R;
import com.material.components.utils.Tools;

public class SettingSectioned extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_sectioned);
        initToolbar();

        initButtonListener();

    }

    private void initButtonListener() {
        LinearLayout myButton =  findViewById(R.id.delete_account_item);
        myButton.setOnClickListener(this);

        LinearLayout myButton2 =  findViewById(R.id.delete_all_my_data_item);
        myButton2.setOnClickListener(this);

        LinearLayout myButton3 =  findViewById(R.id.sync_data);
        myButton3.setOnClickListener(this);

        LinearLayout myButton4 =  findViewById(R.id.save_data_locally_only_item);
        myButton4.setOnClickListener(this);
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Privacy");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Tools.setSystemBarColor(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.delete_account_item:
                Toast.makeText(getApplicationContext(), "Delete account clicked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.delete_all_my_data_item:
                SwitchCompat switchCompat = (SwitchCompat) findViewById(R.id.delete_all_my_data_switch);
                if (switchCompat.isChecked()){
                    switchCompat.setChecked(false);
                } else {
                    switchCompat.setChecked(true);
                }

                Toast.makeText(getApplicationContext(), "Delete all my data clicked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.sync_data:
                Toast.makeText(getApplicationContext(), "Sync data clicked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.save_data_locally_only_item:
                SwitchCompat switchCompat2 = (SwitchCompat) findViewById(R.id.save_data_locally_only_switch);
                if (switchCompat2.isChecked()){
                    switchCompat2.setChecked(false);
                } else {
                    switchCompat2.setChecked(true);
                }

                Toast.makeText(getApplicationContext(), "Save data locally only clicked", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }
}
