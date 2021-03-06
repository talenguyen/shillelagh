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
import android.app.Activity;
import android.os.Build;
import android.widget.TextView;

import com.example.shillelagh.model.TestPrimitiveTable;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

import rx.Observable;
import rx.Subscriber;

public class SpeedTestActivity extends Activity {

  private final Observable<Queue<TestPrimitiveTable>> insertValues =
      Observable.create(new Observable.OnSubscribe<Queue<TestPrimitiveTable>>() {
        @Override public void call(Subscriber<? super Queue<TestPrimitiveTable>> subscriber) {

          Queue<TestPrimitiveTable> values = new LinkedList<TestPrimitiveTable>();
          final Random random = new Random(System.currentTimeMillis());
          for (int i = 0; i < 50000; i++) {
            TestPrimitiveTable value = new TestPrimitiveTable();
            value.setaBoolean(false);
            value.setaDouble(random.nextDouble());
            value.setaFloat(random.nextFloat());
            value.setAnInt(random.nextInt());
            value.setaLong(random.nextLong());
            value.setaShort((short) random.nextInt(Short.MAX_VALUE));
            values.add(value);
          }

          subscriber.onNext(values);
          subscriber.onCompleted();
        }
      }).cache();

//  @Override protected void onCreate(Bundle savedInstanceState) {
//    super.onCreate(savedInstanceState);
//    setContentView(R.layout.activity_speed_test);
//
//    setupHeaders();
//
//    insertValues.flatMap(new Func1<Queue<TestPrimitiveTable>, Observable<Integer>>() {
//      @Override public Observable<Integer> call(Queue<TestPrimitiveTable> testPrimitiveTables) {
//        return runWrites(shillelagh, testPrimitiveTables);
//      }
//    }).subscribeOn(Schedulers.computation()) //
//        .observeOn(AndroidSchedulers.mainThread()) //
//        .doOnNext(new Action1<Integer>() {
//          @Override public void call(Integer writes) {
//            findTextViewById(R.id.writes_per_second) //
//                .setText(String.valueOf(writes));
//          }
//        }) //
//        .flatMap(new Func1<Integer, Observable<Queue<TestPrimitiveTable>>>() {
//          @Override public Observable<Queue<TestPrimitiveTable>> call(Integer integer) {
//            return insertValues;
//          }
//        }) //
//        .flatMap(new Func1<Queue<TestPrimitiveTable>, Observable<Integer>>() {
//          @Override public Observable<Integer> call(Queue<TestPrimitiveTable> queue) {
//            return runReads(shillelagh, queue);
//          }
//        }) //
//        .subscribeOn(Schedulers.computation()) //
//        .observeOn(AndroidSchedulers.mainThread()) //
//        .doOnNext(new Action1<Integer>() {
//          @Override public void call(Integer reads) {
//            findTextViewById(R.id.reads_per_second) //
//                .setText(String.valueOf(reads));
//          }
//        }) //
//        .flatMap(new Func1<Integer, Observable<Long>>() {
//          @Override public Observable<Long> call(Integer integer) {
//            return runSelect1(shillelagh);
//          }
//        }) //
//        .subscribeOn(Schedulers.computation()) //
//        .observeOn(AndroidSchedulers.mainThread()) //
//        .doOnNext(new Action1<Long>() {
//          @Override public void call(Long time) {
//            findTextViewById(R.id.select_time_1).setText(time + "ms");
//          }
//        }) //
//        .flatMap(new Func1<Long, Observable<Long>>() {
//          @Override public Observable<Long> call(Long aLong) {
//            return runSelect2(shillelagh);
//          }
//        }) //
//        .subscribeOn(Schedulers.computation()) //
//        .observeOn(AndroidSchedulers.mainThread()) //
//        .subscribe(new Action1<Long>() {
//          @Override public void call(Long time) {
//            findTextViewById(R.id.select_time_2).setText(time + "ms");
//          }
//        });
//  }
//
//  private Observable<Integer> runWrites(final Shillelagh shillelagh,
//      final Queue<TestPrimitiveTable> values) {
//    return Observable.create(new Observable.OnSubscribe<Integer>() {
//      @Override public void call(Subscriber<? super Integer> subscriber) {
//        Queue<TestPrimitiveTable> valueQueue = new LinkedList<TestPrimitiveTable>(values);
//        int inserts = 0;
//        for (long stop = System.nanoTime() + TimeUnit.SECONDS.toNanos(1);
//            System.nanoTime() < stop; ) {
//          shillelagh.insert(valueQueue.poll());
//          inserts++;
//        }
//
//        subscriber.onNext(inserts);
//        subscriber.onCompleted();
//      }
//    });
//  }
//
//  private Observable<Integer> runReads(final Shillelagh shillelagh,
//      final Queue<TestPrimitiveTable> values) {
//    return Observable.create(new Observable.OnSubscribe<Integer>() {
//      @Override public void call(final Subscriber<? super Integer> subscriber) {
//        final String inserted = "inserted";
//        final SharedPreferences sharedPreferences =
//            PreferenceManager.getDefaultSharedPreferences(SpeedTestActivity.this);
//
//        if (!sharedPreferences.getBoolean(inserted, false)) {
//          // Insert a bunch
//          for (; !values.isEmpty(); ) {
//            shillelagh.insert(values.poll());
//          }
//          final SharedPreferences.Editor editor = sharedPreferences.edit();
//          editor.putBoolean(inserted, true);
//          editor.apply();
//        }
//
//        final AtomicInteger reads = new AtomicInteger(0);
//        final long stop = System.nanoTime() + TimeUnit.SECONDS.toNanos(1);
//        shillelagh.get(TestPrimitiveTable.class).subscribe(new Action1<TestPrimitiveTable>() {
//          @Override public void call(TestPrimitiveTable testBoxedPrimitivesTable) {
//            if (System.nanoTime() < stop) {
//              reads.getAndAdd(1);
//            }
//          }
//        });
//
//        subscriber.onNext(reads.get());
//        subscriber.onCompleted();
//      }
//    });
//  }
//
//  private Observable<Long> runSelect1(final Shillelagh shillelagh) {
//    // we already know there are 50,000 some rows inserted in runReads
//    final long startTime = System.currentTimeMillis();
//    return shillelagh.get(TestPrimitiveTable.class)
//        .filter(new Func1<TestPrimitiveTable, Boolean>() {
//          @Override public Boolean call(TestPrimitiveTable testPrimitiveTable) {
//            return testPrimitiveTable.getId() == 25000;
//          }
//        })
//        .map(new Func1<TestPrimitiveTable, Long>() {
//          @Override public Long call(TestPrimitiveTable testPrimitiveTable) {
//            return System.currentTimeMillis() - startTime;
//          }
//        });
//  }
//
//  private Observable<Long> runSelect2(final Shillelagh shillelagh) {
//    final long start = System.currentTimeMillis();
//    return shillelagh.createQuery(TestPrimitiveTable.class, "SELECT * FROM %s WHERE id = 25000",
//        Shillelagh.getTableName(TestPrimitiveTable.class))
//        .map(new Func1<TestPrimitiveTable, Long>() {
//          @Override public Long call(TestPrimitiveTable testPrimitiveTable) {
//            return System.currentTimeMillis() - start;
//          }
//        });
//  }

  /** Setup the info headers */
  @TargetApi(Build.VERSION_CODES.DONUT)
  private void setupHeaders() {
    if (Build.VERSION_CODES.DONUT <= Build.VERSION.SDK_INT) {
      findTextViewById(R.id.device_make).setText(truncateAt(Build.MANUFACTURER, 20));
    } else {
      findTextViewById(R.id.device_make).setText("Old");
    }
    findTextViewById(R.id.device_model).setText(Build.MODEL);
    findTextViewById(R.id.android_version).setText(Build.VERSION.RELEASE);
    findTextViewById(R.id.sdk_version).setText(String.valueOf(Build.VERSION.SDK_INT));
  }

  @SuppressWarnings({ "unchecked", "UnusedDeclaration" }) // Checked by runtime cast. Public API.
  private TextView findTextViewById(int id) {
    return (TextView) findViewById(id);
  }

  private String truncateAt(String string, int length) {
    return string.length() > length ? string.substring(0, length) : string;
  }
}
