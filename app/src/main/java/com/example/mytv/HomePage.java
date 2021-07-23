package com.example.mytv;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ViewFlipper;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

public class HomePage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private int[] images;
    SliderAdapter slideradapter;
    SliderView sliderView;
    DrawerLayout drawerhome;
    NavigationView navhome;
    Toolbar toolbarhome;
    LinearLayout kannada,hindi,kids,sports;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        sliderView=findViewById(R.id.sliderview);
        images=new int[]{R.drawable.ad1,R.drawable.itsanad,R.drawable.adv2};
        slideradapter=new SliderAdapter(images);
        sliderView.setSliderAdapter(slideradapter);
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setIndicatorAnimation(IndicatorAnimationType.SCALE);
        sliderView.startAutoCycle();
        drawerhome=findViewById(R.id.drawerhome);
        navhome=findViewById(R.id.navhome);
        toolbarhome=findViewById(R.id.toolbarhome);
        kannada=findViewById(R.id.kannada);
        hindi=findViewById(R.id.hindi);
        kids=findViewById(R.id.kids);
        sports=findViewById(R.id.sports);
        toolbarhome.setTitle("Channel categories");
        setSupportActionBar(toolbarhome);
        navhome.bringToFront();
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,drawerhome,toolbarhome,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerhome.addDrawerListener(toggle);
        toggle.syncState();
        navhome.setNavigationItemSelectedListener(this);

        kannada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(HomePage.this,Kannada.class);
                startActivity(i);
            }
        });
        hindi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(HomePage.this,Hindi.class);
                startActivity(i);
            }
        });
        kids.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(HomePage.this,Kids.class);
                startActivity(i);
            }
        });
        sports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(HomePage.this,Sports.class);
                startActivity(i);
            }
        });

    }
    @Override
    public void onBackPressed() {
        if(drawerhome.isDrawerOpen(GravityCompat.START)){
            drawerhome.closeDrawer(GravityCompat.START);
        }
        else
        {
            super.onBackPressed();
        }
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.itemcart:
                Intent i1 = new Intent(HomePage.this, CartPage.class);
                startActivity(i1);
                break;
            case R.id.itemcomplaints:
                Intent i2=new Intent(HomePage.this,Complaint.class);
                startActivity(i2);
                break;
            case R.id.itemcontact:
                Intent i3=new Intent(HomePage.this,ContactUs.class);
                startActivity(i3);
                break;
            case R.id.itemprofile:
                Intent i4=new Intent(HomePage.this,Profile.class);
                startActivity(i4);
                break;
            case R.id.itemlogout:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(),LoginPage.class));
                finish();
        }
        drawerhome.closeDrawer(GravityCompat.START);

        return true;
    }
}