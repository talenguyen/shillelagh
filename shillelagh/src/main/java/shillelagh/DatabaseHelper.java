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

package shillelagh;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Hashtable;
import java.util.Map;

public final class DatabaseHelper {

    public static final Map<Class, TypeAdapter> ADAPTER_MAP = new Hashtable<Class, TypeAdapter>();
    public static final String $$SUFFIX = "_Adapter";

    public static void createTable(SQLiteDatabase db, Class clazz) {
        final TypeAdapter adapter = getAdapter(clazz);
        db.execSQL(adapter.getCreateStatement());
    }

    public static void dropTable(SQLiteDatabase db, Class clazz) {
        final TypeAdapter adapter = getAdapter(clazz);
        db.execSQL(String.format("DROP TABLE IF EXISTS %s", adapter.geTableName()));
    }

    public static long insert(SQLiteOpenHelper helper, Object target) {
        final SQLiteDatabase database = helper.getWritableDatabase();
        final TypeAdapter adapter = getAdapter(target.getClass());
        return database.insert(adapter.geTableName(), null, adapter.asContentValues(target));
    }

    private static TypeAdapter getAdapter(Class clazz) {
        if (!ADAPTER_MAP.containsKey(clazz)) {
            final TypeAdapter adapter = reflectConstructor(String.format("%s%s", clazz.getName(), $$SUFFIX));
            if (adapter == null) {
                throw new IllegalArgumentException(String.format("Class %s is not supported. Please make sure you have added @Table annotation for that class.", clazz.getName()));
            }
            ADAPTER_MAP.put(clazz, adapter);
            return adapter;
        } else {
            return ADAPTER_MAP.get(clazz);
        }
    }

    private static <T> T reflectConstructor(String className) {
        try {
            final Class<?> clazz = Class.forName(className);
            final Constructor<?> constructor = clazz.getDeclaredConstructor();
            return (T) constructor.newInstance();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}
