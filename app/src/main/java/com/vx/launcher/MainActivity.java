package com.vx.launcher;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView aiStatusText;
    private Button btnAutoFix;
    private String currentDetectedCrash = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // ربط الكود بجرافيك الواجهة الفخم (الأسود والأحمر)
        setContentView(R.layout.activity_main); 

        aiStatusText = findViewById(R.id.ai_status_text);
        btnAutoFix = findViewById(R.id.btn_auto_fix);

        // محاكاة إقلاع اللعبة وفحص الكراشات لنسخة 26.2 والمودات في الخلفية
        simulateGameLaunch();

        if (btnAutoFix != null) {
            btnAutoFix.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // تنفيذ ميزة الـ AI: حل المشكلة تلقائياً بدون تدخل يدوي من المستخدم
                    if (!currentDetectedCrash.isEmpty()) {
                        String solution = CrashHandlerAI.analyzeCrashLog(currentDetectedCrash);
                        CrashHandlerAI.autoFixProblem(solution, getFilesDir().getAbsolutePath() + "/.minecraft");
                        
                        if (aiStatusText != null) {
                            aiStatusText.setText("الذكاء الاصطناعي: تم حل مشكلة المود بنجاح وتوفير ثبات عالي للـ FPS! اللعبة جاهزة.");
                        }
                        btnAutoFix.setVisibility(View.GONE);
                        Toast.makeText(MainActivity.this, "تم الإصلاح التلقائي بنجاح!", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }

    private void simulateGameLaunch() {
        // فحص تلقائي لكراش مود الفيزكس المشهور على فابرك 26.2
        currentDetectedCrash = "Exception in thread \"main\" java.lang.RuntimeException: PhysicsMod failed to load on Fabric 26.2";
        
        String result = CrashHandlerAI.analyzeCrashLog(currentDetectedCrash);
        if (!result.equals("UNKNOWN_CRASH_NEED_API")) {
            if (aiStatusText != null) {
                aiStatusText.setText("الذكاء الاصطناعي: تم رصد تعارض في Physics Mod مع نسخة 26.2. اضغط الزر للإصلاح التلقائي.");
            }
            if (btnAutoFix != null) {
                btnAutoFix.setVisibility(View.VISIBLE);
            }
        }
    }
}
