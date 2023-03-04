package com.example.food_delivery_app_scholars_of_mars;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;

import com.example.food_delivery_app_scholars_of_mars.session.SessionManager;


public class GetStartedActivity extends AppCompatActivity {
    AppCompatButton btn;
    AlphaAnimation btnClick = new AlphaAnimation(1F, 0.8F);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_started);

        SessionManager sessionManager = new SessionManager(GetStartedActivity.this);
        if(sessionManager.isLogged()){
            startActivity(new Intent(GetStartedActivity.this, MainActivity.class));
            finish();
        }
        
        Window window = this.getWindow();
        //window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        //window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(this.getResources().getColor(R.color.orange));
        window.setNavigationBarColor(this.getResources().getColor(R.color.orange));



        btn = findViewById(R.id.get_started);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(btnClick);
                Intent intent = new Intent(GetStartedActivity.this, AcountActivity.class);
                startActivity(intent);
                finish();

            }
        });

    }
}