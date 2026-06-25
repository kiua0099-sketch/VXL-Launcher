package com.vx.launcher;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.io.File;

public class DashboardActivity extends AppCompatActivity {

    private EditText edtUsername;
    private Spinner spinnerLoaders;
    private Button btnPlay;
    private TextView txtAiStatus;
    private SettingsManager settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        settings = new SettingsManager(this);

        edtUsername = findViewById(R.id.edt_username);
        spinnerLoaders = findViewById(R.id.spinner_loaders);
        btnPlay = findViewById(R.id.btn_play);
        txtAiStatus = findViewById(R.id.txt_dashboard_ai_status);

        String[] loadersAndVersions = {
            "Fabric - 1.26.2",
            "Forge - 1.26.2",
            "NeoForge - 1.26.2",
            "VX Custom Loader"
        };
        
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, loadersAndVersions);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLoaders.setAdapter(adapter);

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = edtUsername.getText().toString().trim();
                
                if (username.isEmpty()) {
                    Toast.makeText(DashboardActivity.this, "Username Empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                String mcPath = getFilesDir().getAbsolutePath() + "/.minecraft";
                File mcDir = new File(mcPath);

                AccountManager.createOfflineAccount(username, mcPath);

                settings.setNoLagEnabled(true); 
                int allocatedRam = settings.getMemorySize();

                txtAiStatus.setText("VX AI: Active");
                Toast.makeText(DashboardActivity.this, "Launching...", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
