package vn.edu.fpt.fa24;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import io.realm.Realm;

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Realm.init(this);
        navigateToHome();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void navigateToHome(){
        Intent intent = new Intent(SplashActivity.this,MainActivity.class);
        startActivity(intent);
        finish();
    }
}