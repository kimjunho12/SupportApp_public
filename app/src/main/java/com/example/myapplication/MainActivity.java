package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.widget.Toolbar;


import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private FragmentManager fm;
    private FragmentTransaction ft;
    private bottom_home_fragment frag1;
    private bottom_search_fragment frag2;
    private bottom_favorite_fragment frag3;
    private bottom_account_fragment frag4;
    private bottom_setting_fragment frag5;
    private MenuItem top_menu_Search;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.top_menu, menu);
        top_menu_Search = menu.findItem(R.id.top_action_search);
        SearchView searchView = (SearchView) top_menu_Search.getActionView();
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setQueryHint("선수명을 검색해주세요.");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.top_action_search) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setHomeAsUpIndicator(R.drawable.top_category);



        bottomNavigationView = findViewById(R.id.bottomNavi);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()) {
                    case R.id.action_home:
                        setFrag(0);
                        break;
                    case R.id.action_search:
                        setFrag(1);
                        break;
                    case R.id.action_favorite:
                        setFrag(2);
                        break;
                    case R.id.action_account:
                        setFrag(3);
                        break;
                    case R.id.action_setting:
                        setFrag(4);
                        break;

                }
                return true;
            }
        });
        frag1 = new bottom_home_fragment();
        frag2 = new bottom_search_fragment();
        frag3 = new bottom_favorite_fragment();
        frag4 = new bottom_account_fragment();
        frag5 = new bottom_setting_fragment();
        setFrag(0); // 기본 페이지 0
    }

    private void setFrag(int n) {
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        switch(n) {
            case 0:
                ft.replace(R.id.main_frame, frag1);
                ft.commit();
                break;
            case 1:
                ft.replace(R.id.main_frame, frag2);
                ft.commit();
                break;
            case 2:
                ft.replace(R.id.main_frame, frag3);
                ft.commit();
                break;
            case 3:
                ft.replace(R.id.main_frame, frag4);
                ft.commit();
                break;
            case 4:
                ft.replace(R.id.main_frame, frag5);
                ft.commit();
                break;
        }
    }
}
