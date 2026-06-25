package com.vx.launcher;

import android.content.Context;
import android.content.SharedPreferences;
import java.util.Locale;

public class SettingsManager {
    private static final String PREFS_NAME = "VX_Settings";
    private SharedPreferences prefs;

    public SettingsManager(Context context) {
        this.prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    // 1. تحديد حجم الذاكرة (Memory Allocation) بالـ MB
    public void setMemorySize(int megabytes) {
        prefs.edit().putInt("ram_size", megabytes).apply();
    }
    public int getMemorySize() {
        return prefs.getBoolean("no_lag_enabled", false) ? 1024 : prefs.getInt("ram_size", 2048); 
        // إذا تم تفعيل وضع No Lag، يتم تخصيص ذاكرة مستقرة تلقائياً لمنع السخونة
    }

    // 2. نوع الراندر (Render Engine) مثل Holy Render أو OpenGL
    public void setRenderEngine(String renderType) {
        prefs.edit().putString("render_engine", renderType).apply();
    }
    public String getRenderEngine() {
        return prefs.getString("render_engine", "HolyRender");
    }

    // 3. وضع منع اللاج الذكي (No Lag Mode)
    public void setNoLagEnabled(boolean enabled) {
        prefs.edit().putBoolean("no_lag_enabled", enabled).apply();
    }
    public boolean isNoLagEnabled() {
        return prefs.getBoolean("no_lag_enabled", false);
    }

    // 4. إعدادات اللغة (عربي / إنجليزي)
    public void setLanguage(Context context, String langCode) {
        prefs.edit().putString("app_lang", langCode).apply();
        updateResourceLocale(context, langCode);
    }
    public String getLanguage() {
        return prefs.getString("app_lang", "en"); // الإنجليزية هي اللغة الأساسية الافتراضية
    }

    // دالة لتحديث لغة النظام داخل اللانشر فورياً
    public void updateResourceLocale(Context context, String langCode) {
        Locale locale = new Locale(langCode);
        Locale.setDefault(locale);
        android.content.res.Configuration config = new android.content.res.Configuration();
        config.setLocale(locale);
        context.getResources().updateConfiguration(config, context.getResources().getDisplayMetrics());
    }
}
