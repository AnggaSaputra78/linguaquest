package com.linguaquest.data.local;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.linguaquest.data.local.entity.UserProfileEntity;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class UserDao_Impl implements UserDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<UserProfileEntity> __insertionAdapterOfUserProfileEntity;

  public UserDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfUserProfileEntity = new EntityInsertionAdapter<UserProfileEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `user_profile` (`id`,`display_name`,`target_minutes_per_day`,`onboarding_completed`,`placement_completed`,`current_level_id`,`created_at_epoch_millis`) VALUES (?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final UserProfileEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getDisplayName());
        statement.bindLong(3, entity.getTargetMinutesPerDay());
        final int _tmp = entity.getOnboardingCompleted() ? 1 : 0;
        statement.bindLong(4, _tmp);
        final int _tmp_1 = entity.getPlacementCompleted() ? 1 : 0;
        statement.bindLong(5, _tmp_1);
        if (entity.getCurrentLevelId() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getCurrentLevelId());
        }
        statement.bindLong(7, entity.getCreatedAtEpochMillis());
      }
    };
  }

  @Override
  public Object upsertUserProfile(final UserProfileEntity profile,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfUserProfileEntity.insert(profile);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<UserProfileEntity> observeUserProfile() {
    final String _sql = "SELECT * FROM user_profile WHERE id = 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"user_profile"}, new Callable<UserProfileEntity>() {
      @Override
      @Nullable
      public UserProfileEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfDisplayName = CursorUtil.getColumnIndexOrThrow(_cursor, "display_name");
          final int _cursorIndexOfTargetMinutesPerDay = CursorUtil.getColumnIndexOrThrow(_cursor, "target_minutes_per_day");
          final int _cursorIndexOfOnboardingCompleted = CursorUtil.getColumnIndexOrThrow(_cursor, "onboarding_completed");
          final int _cursorIndexOfPlacementCompleted = CursorUtil.getColumnIndexOrThrow(_cursor, "placement_completed");
          final int _cursorIndexOfCurrentLevelId = CursorUtil.getColumnIndexOrThrow(_cursor, "current_level_id");
          final int _cursorIndexOfCreatedAtEpochMillis = CursorUtil.getColumnIndexOrThrow(_cursor, "created_at_epoch_millis");
          final UserProfileEntity _result;
          if (_cursor.moveToFirst()) {
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final String _tmpDisplayName;
            _tmpDisplayName = _cursor.getString(_cursorIndexOfDisplayName);
            final int _tmpTargetMinutesPerDay;
            _tmpTargetMinutesPerDay = _cursor.getInt(_cursorIndexOfTargetMinutesPerDay);
            final boolean _tmpOnboardingCompleted;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfOnboardingCompleted);
            _tmpOnboardingCompleted = _tmp != 0;
            final boolean _tmpPlacementCompleted;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfPlacementCompleted);
            _tmpPlacementCompleted = _tmp_1 != 0;
            final String _tmpCurrentLevelId;
            if (_cursor.isNull(_cursorIndexOfCurrentLevelId)) {
              _tmpCurrentLevelId = null;
            } else {
              _tmpCurrentLevelId = _cursor.getString(_cursorIndexOfCurrentLevelId);
            }
            final long _tmpCreatedAtEpochMillis;
            _tmpCreatedAtEpochMillis = _cursor.getLong(_cursorIndexOfCreatedAtEpochMillis);
            _result = new UserProfileEntity(_tmpId,_tmpDisplayName,_tmpTargetMinutesPerDay,_tmpOnboardingCompleted,_tmpPlacementCompleted,_tmpCurrentLevelId,_tmpCreatedAtEpochMillis);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object getUserProfile(final Continuation<? super UserProfileEntity> $completion) {
    final String _sql = "SELECT * FROM user_profile WHERE id = 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<UserProfileEntity>() {
      @Override
      @Nullable
      public UserProfileEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfDisplayName = CursorUtil.getColumnIndexOrThrow(_cursor, "display_name");
          final int _cursorIndexOfTargetMinutesPerDay = CursorUtil.getColumnIndexOrThrow(_cursor, "target_minutes_per_day");
          final int _cursorIndexOfOnboardingCompleted = CursorUtil.getColumnIndexOrThrow(_cursor, "onboarding_completed");
          final int _cursorIndexOfPlacementCompleted = CursorUtil.getColumnIndexOrThrow(_cursor, "placement_completed");
          final int _cursorIndexOfCurrentLevelId = CursorUtil.getColumnIndexOrThrow(_cursor, "current_level_id");
          final int _cursorIndexOfCreatedAtEpochMillis = CursorUtil.getColumnIndexOrThrow(_cursor, "created_at_epoch_millis");
          final UserProfileEntity _result;
          if (_cursor.moveToFirst()) {
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final String _tmpDisplayName;
            _tmpDisplayName = _cursor.getString(_cursorIndexOfDisplayName);
            final int _tmpTargetMinutesPerDay;
            _tmpTargetMinutesPerDay = _cursor.getInt(_cursorIndexOfTargetMinutesPerDay);
            final boolean _tmpOnboardingCompleted;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfOnboardingCompleted);
            _tmpOnboardingCompleted = _tmp != 0;
            final boolean _tmpPlacementCompleted;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfPlacementCompleted);
            _tmpPlacementCompleted = _tmp_1 != 0;
            final String _tmpCurrentLevelId;
            if (_cursor.isNull(_cursorIndexOfCurrentLevelId)) {
              _tmpCurrentLevelId = null;
            } else {
              _tmpCurrentLevelId = _cursor.getString(_cursorIndexOfCurrentLevelId);
            }
            final long _tmpCreatedAtEpochMillis;
            _tmpCreatedAtEpochMillis = _cursor.getLong(_cursorIndexOfCreatedAtEpochMillis);
            _result = new UserProfileEntity(_tmpId,_tmpDisplayName,_tmpTargetMinutesPerDay,_tmpOnboardingCompleted,_tmpPlacementCompleted,_tmpCurrentLevelId,_tmpCreatedAtEpochMillis);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
