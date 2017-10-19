package com.tinkerdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.tencent.tinker.lib.util.TinkerLog;
import com.tencent.tinker.loader.shareutil.ShareTinkerInternals;
import com.tinkerpatch.sdk.TinkerPatch;
import com.tinkerpatch.sdk.server.callback.ConfigRequestCallback;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private Button fetchButton;
    private Button fetchDynamicConfig;
    private Button cleanAll;
    private Button killAllOtherProcess;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fetchButton = (Button) findViewById(R.id.fetchButton);
        fetchDynamicConfig = (Button) findViewById(R.id.fetchDynamicConfig);
        cleanAll = (Button) findViewById(R.id.cleanAll);
        killAllOtherProcess = (Button) findViewById(R.id.killAllOtherProcess);

        fetchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TinkerPatch.with().fetchPatchUpdate(true);
            }
        });
        fetchDynamicConfig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TinkerPatch.with().fetchDynamicConfig(new ConfigRequestCallback() {

                    @Override
                    public void onSuccess(HashMap<String, String> configs) {
                        TinkerLog.w("ly", "request config success, config:" + configs);
                    }

                    @Override
                    public void onFail(Exception e) {
                        TinkerLog.w("ly", "request config failed, exception:" + e);
                    }
                }, true);
            }
        });
        cleanAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TinkerPatch.with().cleanAll();
            }
        });
        killAllOtherProcess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShareTinkerInternals.killAllOtherProcess(getApplicationContext());
                android.os.Process.killProcess(android.os.Process.myPid());
            }
        });
    }
}
