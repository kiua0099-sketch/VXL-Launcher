package com.vx.launcher;

import java.io.File;
import java.io.FileWriter;

public class AccountManager {
    
    // دالة لإنشاء حساب أوفلاين وتوليد بيانات اللاعب تلقائياً لكي لا يحدث كراش
    public static void createOfflineAccount(String username, String minecraftDir) {
        try {
            File authFile = new File(minecraftDir, "launcher_profiles.json");
            
            // صياغة نص الـ JSON يدوياً لضمان أداء سريع وخفيف على الهاتف
            String jsonConfig = "{\n" +
                    "  \"authentication\": {\n" +
                    "    \"displayName\": \"" + username + "\",\n" +
                    "    \"uuid\": \"00000000-0000-0000-0000-000000000000\",\n" +
                    "    \"accessToken\": \"00000000000000000000000000000000\"\n" +
                    "  }\n" +
                    "}";
            
            if (!authFile.getParentFile().exists()) {
                authFile.getParentFile().mkdirs();
            }
            
            FileWriter writer = new FileWriter(authFile);
            writer.write(jsonConfig);
            writer.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
