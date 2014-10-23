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

import com.example.shillelagh.model.Author;
import com.example.shillelagh.model.Book;
import com.example.shillelagh.model.Chapter;

import shillelagh.DatabaseHelper;

public class ExampleSqliteHelper extends SQLiteOpenHelper {
  private static final String DATABASE_NAME = "shillelagh_example.db";
  private static final int DATABASE_VERSION = 6;

  public ExampleSqliteHelper(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
  }

  @Override public void onCreate(SQLiteDatabase db) {
      DatabaseHelper.createTable(db, Author.class);
      DatabaseHelper.createTable(db, Book.class);
      DatabaseHelper.createTable(db, Chapter.class);
  }

  @Override public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    // Simplistic solution, you will lose your data though, good for debug builds bad for prod
      DatabaseHelper.dropTable(db, Author.class);
      DatabaseHelper.dropTable(db, Book.class);
      DatabaseHelper.dropTable(db, Chapter.class);
    onCreate(db);
  }
}
