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

        // تهيئة مدير الإعدادات
        settings = new SettingsManager(this);

        // ربط عناصر الواجهة بالأكواد
        edtUsername = findViewById(R.id.edt_username);
        spinnerLoaders = findViewById(R.id.spinner_loaders);
        btnPlay = findViewById(R.id.btn_play);
        txtAiStatus = findViewById(R.id.txt_dashboard_ai_status);

        // إعداد قائمة اللودرات والإصدارات داخل الـ Spinner
        String[] loadersAndVersions = {
            "Fabric - 1.26.2 (Recommended)",
            "Forge - 1.26.2",
            "NeoForge - 1.26.2",
            "VX Custom Loader (Future)"
        };
        
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, loadersAndVersions);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLoaders.setAdapter(adapter);

        // برمجة زر التشغيل الفخم (PLAY GAME)
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = edtUsername.getText().toString().trim();
                
                if (username.isEmpty()) {
                    Toast.makeText(DashboardActivity.this, "Please enter a username first!", Toast.LENGTH_SHORT).show();
                    return;
                }

                // 1. مسار مجلد ماين كرافت الوهمي على الهاتف
                String mcPath = getFilesDir().getAbsolutePath() + "/.minecraft";
                File mcDir = new File(mcPath);

                // 2. تفعيل الحساب الأوفلاين فوراً بالاسم المكتوب
                AccountManager.createOfflineAccount(username, mcPath);

                // 3. تحديد اللودر المختار من القائمة وتشغيله ديناميكياً
                int selectedPosition = spinnerLoaders.getSelectedItemPosition();
                ModLoaderEngine.LoaderType selectedLoader;
                
                if (selectedPosition == 0) selectedLoader = ModLoaderEngine.LoaderType.FABRIC;
                else if (selectedPosition == 1) selectedLoader = ModLoaderEngine.LoaderType.FORGE;
                else if (selectedPosition == 2) selectedLoader = ModLoaderEngine.LoaderType.NEOFORGE;
                else selectedLoader = ModLoaderEngine.LoaderType.CUSTOM_FUTURE_LOADER;

                ModLoaderEngine.setupLoader(selectedLoader, "1.26.2", mcDir);

                // 4. تطبيق وضع الـ No Lag وتهيئة الذاكرة تلقائياً لرفع الـ FPS قبل الإقلاع
                settings.setNoLagEnabled(true); 
                int allocatedRam = settings.getMemorySize();

                txtAiStatus.setText("VX AI: Optimizing " + allocatedRam + "MB RAM | Launching Minecraft...");
                Toast.makeText(DashboardActivity.this, "Launching Minecraft with " + selectedLoader.name() + "!", Toast.LENGTH_LONG).show();
            }
        });
    }
}
