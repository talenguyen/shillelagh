/*
 * Copyright 2014 Andrew Reitz
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.shillelagh;

import android.annotation.TargetApi;
import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.os.StrictMode;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class ShillelaghApp extends Application {

    /**
     * Shillelagh Singleton
     */
    private SQLiteOpenHelper sqliteOpenHelper;

    @Override
    public void onCreate() {
        super.onCreate();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                    .detectAll()
                    .penaltyDeath()
                    .build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                    .detectAll()
                    .penaltyDeath()
                    .build());
        }

        sqliteOpenHelper = new ExampleSqliteHelper(this);
    }

    public synchronized SQLiteOpenHelper getSqlOpenHelper() {
        return sqliteOpenHelper;
    }

    public static ShillelaghApp get(Context context) {
        return (ShillelaghApp) context.getApplicationContext();
    }
}
