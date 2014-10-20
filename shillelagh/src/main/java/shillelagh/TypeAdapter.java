package shillelagh;

import android.content.ContentValues;
import android.database.Cursor;

/**
 * Created by TALE on 10/20/2014.
 */
public interface TypeAdapter<T> {

    String getCreateStatement();

    void map(Cursor cursor, T target);

    T newObject();

    ContentValues asContentValues(T target);
}
