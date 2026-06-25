package com.vx.launcher;

import android.util.Log;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ModStoreManager {
    private static final String TAG = "VX_ModStore";

    /**
     * دالة تحميل المود مباشرة من الإنترنت إلى مجلد اللعبة
     * @param modUrl رابط تحميل ملف الـ .jar الخاص بالمود
     * @param modsDir مسار مجلد mods داخل لعبة ماين كرافت (.minecraft/mods)
     * @param modName اسم المود (مثال: PhysicsMod)
     */
    public static void downloadMod(final String modUrl, final File modsDir, final String modName) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Log.d(TAG, "جاري بدء تحميل المود: " + modName + " من المتجر المدمج...");
                    
                    // إنشاء مجلد mods إذا لم يكن موجوداً لمنع الأخطاء
                    if (!modsDir.exists()) {
                        modsDir.mkdirs();
                    }

                    URL url = new URL(modUrl);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.connect();

                    InputStream input = new BufferedInputStream(url.openStream(), 8192);
                    File outputFile = new File(modsDir, modName + ".jar");
                    FileOutputStream output = new FileOutputStream(outputFile);

                    byte[] data = new byte[1024];
                    int count;
                    while ((count = input.read(data)) != -1) {
                        output.write(data, 0, count);
                    }

                    output.flush();
                    output.close();
                    input.close();

                    Log.d(TAG, "تم تحميل المود " + modName + " بنجاح وتثبيته داخل مجلد اللعبة!");

                    // ⚡ الربط مع الـ AI: بمجرد اكتمال التحميل، يقوم الذكاء الاصطناعي بفحص الإعدادات فوراً لمنع الكراش
                    CrashHandlerAI.autoFixProblem("FIX_PHYSICS_MOD_CONFLICT", modsDir.getParent());

                } catch (Exception e) {
                    Log.e(TAG, "خطأ أثناء تحميل المود من المتجر: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
