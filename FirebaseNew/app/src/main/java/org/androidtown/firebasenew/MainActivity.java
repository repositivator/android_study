package org.androidtown.firebasenew;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

public class MainActivity extends AppCompatActivity {


    // firebase 원격 구성
    // 별도의 서버 구성 없이 매개변수 데이터가 제공이 되고, 이를 통해 앱을 제어한다.
    FirebaseRemoteConfig config;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 1. Config 획득
        config = FirebaseRemoteConfig.getInstance();

        // 2. 세팅 획득
        FirebaseRemoteConfigSettings configSettings
                = new FirebaseRemoteConfigSettings.Builder()
                .setDeveloperModeEnabled(true)
                .build();
        // 3. 설정
        config.setConfigSettings(configSettings);
        // 4. 데이터 획득
        loadRemoteData();
    }

    public void loadRemoteData(){

        // 리모트로 가져오는 시간
        long cacheException = 3600;
        if (config.getInfo().getConfigSettings().isDeveloperModeEnabled()){
            cacheException = 0;
        }

        // 원격매개변수 요청
        config.fetch(cacheException).addOnCompleteListener(this, new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if(task.isSuccessful()){
                    config.activateFetched();
                    Log.i("FCOM->", config.getString("APP_DOMAIN"));
                }

            }
        });
    }

}
