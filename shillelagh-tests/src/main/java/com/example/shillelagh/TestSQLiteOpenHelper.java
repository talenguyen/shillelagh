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

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.shillelagh.model.TestBlobs;
import com.example.shillelagh.model.TestBoxedPrimitivesTable;
import com.example.shillelagh.model.TestJavaObjectsTable;
import com.example.shillelagh.model.TestOneToMany;
import com.example.shillelagh.model.TestOneToOne;
import com.example.shillelagh.model.TestPrimitiveTable;

import tale.androiddb.DatabaseHelper;

public class TestSQLiteOpenHelper extends SQLiteOpenHelper {
  public static final String DATABASE_NAME = "shillelagh_test.db";
  private static final int DATABASE_VERSION = 3;

  public TestSQLiteOpenHelper(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
  }

  @Override public void onCreate(SQLiteDatabase db) {
      DatabaseHelper.createTable(db, TestBoxedPrimitivesTable.class);
      DatabaseHelper.createTable(db, TestPrimitiveTable.class);
      DatabaseHelper.createTable(db, TestJavaObjectsTable.class);
      DatabaseHelper.createTable(db, TestBlobs.class);
      DatabaseHelper.createTable(db, TestOneToOne.class);
      DatabaseHelper.createTable(db, TestOneToOne.Child.class);
      DatabaseHelper.createTable(db, TestOneToMany.class);
      DatabaseHelper.createTable(db, TestOneToMany.Child.class);
  }

  @Override public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    // Simplistic solution, you will lose your data though, good for debug builds bad for prod
      DatabaseHelper.dropTable(db, TestPrimitiveTable.class);
      DatabaseHelper.dropTable(db, TestBoxedPrimitivesTable.class);
      DatabaseHelper.dropTable(db, TestPrimitiveTable.class);
      DatabaseHelper.dropTable(db, TestBlobs.class);
      DatabaseHelper.dropTable(db, TestOneToOne.class);
      DatabaseHelper.dropTable(db, TestOneToOne.Child.class);
      DatabaseHelper.dropTable(db, TestOneToMany.class);
      DatabaseHelper.dropTable(db, TestOneToMany.Child.class);
    onCreate(db);
  }
}
