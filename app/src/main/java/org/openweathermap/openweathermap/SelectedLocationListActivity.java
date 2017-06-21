package org.openweathermap.openweathermap;


import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import org.openweathermap.Model.WeatherModel;
import org.openweathermap.apis.ApiClient;
import org.openweathermap.fragments.AddCityFragment;
import org.openweathermap.fragments.EditCityFragment;
import org.openweathermap.fragments.HelpFragment;
import org.openweathermap.fragments.SettingsFragment;
import org.openweathermap.fragments.ViewPagerFragment;
import org.openweathermap.ui.FontTextView;
import org.openweathermap.utils.AppUtils;
import org.openweathermap.utils.GPSTracker;

public class SelectedLocationListActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    AppBarLayout appBarLayout;
    public static Typeface typeface;

    WeatherModel weatherModel = null;

    public static Typeface getTypeface() {
        return typeface;
    }

    CollapsingToolbarLayout collapsingToolbarLayout;
    String collapsingToolbarLayoutTitle;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (typeface == null) {
            typeface = Typeface.createFromAsset(getAssets(), "fonts/mxx_font2.ttf");
        }
        if (checkLocationPermission()) {
            addFragment(R.id.frame_container, new ViewPagerFragment(), "ViewPagerFragment", "ViewPagerBackFragment");
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.mipmap.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            setupDrawerContent(navigationView);
        }
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle(" ");
        appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset < 160) {
                    collapsingToolbarLayout.setTitle(collapsingToolbarLayoutTitle);
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbarLayout.setTitle(" ");
                    isShow = false;
                }
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mDrawerLayout.isDrawerOpen(Gravity.LEFT)) {
            mDrawerLayout.closeDrawer(Gravity.LEFT);
        } else {
            mDrawerLayout.openDrawer(Gravity.LEFT);
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mDrawerLayout.isDrawerOpen(Gravity.LEFT)) {
                    mDrawerLayout.closeDrawer(Gravity.LEFT);
                } else {
                    mDrawerLayout.openDrawer(Gravity.LEFT);
                }
            }
        }, 2000);
    }

    public void setCollapsingToolbarLayoutTitle(String Title) {
        collapsingToolbarLayoutTitle = AppUtils.toTitleCase(Title);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);//Menu Resource, Menu
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            if (mDrawerLayout.isDrawerOpen(Gravity.LEFT)) {
                mDrawerLayout.closeDrawer(Gravity.LEFT);
            } else {
                mDrawerLayout.openDrawer(Gravity.LEFT);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean checkLocationPermission() {
        String permission = "android.permission.ACCESS_FINE_LOCATION";
        int res = this.checkCallingOrSelfPermission(permission);
        return (res == PackageManager.PERMISSION_GRANTED);
    }

    public void setHeaderDetails(WeatherModel weatherModel) {
        this.weatherModel = weatherModel;
        FontTextView fontTextView = (FontTextView) findViewById(R.id.w_now_tmp);
        double temp = weatherModel.getMain().getTemp();
        fontTextView.setText("" + temp);
        TextView conditionTextView = (TextView) findViewById(R.id.w_now_cond_text);
        String condition = weatherModel.getWeather()[0].getDescription();
        if (condition != null) {
            conditionTextView.setText("" + AppUtils.toTitleCase(condition));
        }
        FontTextView locationTextView = (FontTextView) findViewById(R.id.w_basic_update_loc);
        String location = weatherModel.getName();
        if (location != null) {
            locationTextView.setText("" + AppUtils.toTitleCase(location));
            setCollapsingToolbarLayoutTitle("" + AppUtils.toTitleCase(location));
        }

        TextView updatedTimeTextView = (TextView) findViewById(R.id.w_time_text);
        long dateTimeMillis = weatherModel.getCurrentDateTime();
        dateTimeMillis *= 1000;
        if (location != null) {
            String dateTime = AppUtils.convertMilliSecondsToFormattedDate(dateTimeMillis);
            if (dateTime != null) {
                updatedTimeTextView.setText("Updated Today " + dateTime.split(" ")[1]);
            }
        }
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        if (menuItem.getItemId() == R.id.nav_help) {
                            addFragment(R.id.frame_container, new HelpFragment(), "HelpFragment", "HelpBackFragment");
                            appBarLayout.setExpanded(false, false);
                        } else if (menuItem.getItemId() == R.id.nav_curr_location) {
                            FragmentManager fm = getSupportFragmentManager();
                            if (fm.getBackStackEntryCount() > 1) {
                                String name = fm.getBackStackEntryAt(0).getName();
                                fm.popBackStack(name, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                            }
                            addFragment(R.id.frame_container, new ViewPagerFragment(), "ViewPagerFragment", "ViewPagerBackFragment");
                            appBarLayout.setExpanded(true, true);
                        } else if (menuItem.getItemId() == R.id.nav_weather) {
                            FragmentManager fm = getSupportFragmentManager();
                            if (fm.getBackStackEntryCount() > 1) {
                                String name = fm.getBackStackEntryAt(0).getName();
                                fm.popBackStack(name, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                            }
                            addFragment(R.id.frame_container, new ViewPagerFragment(), "ViewPagerFragment", "ViewPagerBackFragment");
                            appBarLayout.setExpanded(true, true);
                        } else if (menuItem.getItemId() == R.id.nav_settings) {
                            addFragment(R.id.frame_container, new SettingsFragment(), "SettingsFragment", "SettingsBackFragment");
                            appBarLayout.setExpanded(false, false);
                        } else if (menuItem.getItemId() == R.id.addCity) {
                            addFragment(R.id.frame_container, new AddCityFragment(), "AddCityFragment", "AddCityBackFragment");
                            appBarLayout.setExpanded(false, false);
                        } else if (menuItem.getItemId() == R.id.editCity) {
                            addFragment(R.id.frame_container, new EditCityFragment(), "EditCityFragment", "EditCityBackFragment");
                            appBarLayout.setExpanded(false, false);
                        }
                        menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                });
    }


    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    FragmentManager fm = getSupportFragmentManager();
                    if (fm.getBackStackEntryCount() > 1) {
                        String name = fm.getBackStackEntryAt(0).getName();
                        fm.popBackStack(name, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    }
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            addFragment(R.id.frame_container, new ViewPagerFragment(), "ViewPagerFragment", "ViewPagerBackFragment");
                        }
                    }, 2000);


                } else {
                    Snackbar.make(collapsingToolbarLayout, "App will not work without this Permission!", Snackbar.LENGTH_LONG)
                            .setAction("Exit", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    finish();
                                }
                            }).show();
                }
                return;
            }
        }
    }

    protected void addFragment(@IdRes int containerViewId,
                               @NonNull Fragment fragment,
                               @NonNull String fragmentTag, @NonNull String backName) {
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction()
                .replace(containerViewId, fragment, fragmentTag)
                .addToBackStack(backName)
                .commit();
    }


    @Override
    public void onBackPressed() {
        FragmentManager fm = getSupportFragmentManager();
        if (fm.getBackStackEntryCount() > 1) {
            super.onBackPressed();
        } else {
            finish();
        }
    }


}