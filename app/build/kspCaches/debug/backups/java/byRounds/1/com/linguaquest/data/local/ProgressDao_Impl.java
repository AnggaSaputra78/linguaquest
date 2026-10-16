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
import com.linguaquest.data.local.entity.AnswerAttemptEntity;
import com.linguaquest.data.local.entity.DailyActivityEntity;
import com.linguaquest.data.local.entity.LessonProgressEntity;
import com.linguaquest.data.local.entity.VocabularyEntity;
import com.linguaquest.data.local.entity.VocabularyReviewEntity;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Integer;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class ProgressDao_Impl implements ProgressDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<AnswerAttemptEntity> __insertionAdapterOfAnswerAttemptEntity;

  private final EntityInsertionAdapter<LessonProgressEntity> __insertionAdapterOfLessonProgressEntity;

  private final EntityInsertionAdapter<VocabularyReviewEntity> __insertionAdapterOfVocabularyReviewEntity;

  private final EntityInsertionAdapter<DailyActivityEntity> __insertionAdapterOfDailyActivityEntity;

  public ProgressDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfAnswerAttemptEntity = new EntityInsertionAdapter<AnswerAttemptEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `answer_attempts` (`id`,`exercise_id`,`lesson_id`,`is_correct`,`answer_text`,`created_at_epoch_millis`) VALUES (nullif(?, 0),?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final AnswerAttemptEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getExerciseId());
        if (entity.getLessonId() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getLessonId());
        }
        final int _tmp = entity.isCorrect() ? 1 : 0;
        statement.bindLong(4, _tmp);
        if (entity.getAnswerText() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getAnswerText());
        }
        statement.bindLong(6, entity.getCreatedAtEpochMillis());
      }
    };
    this.__insertionAdapterOfLessonProgressEntity = new EntityInsertionAdapter<LessonProgressEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `lesson_progress` (`lesson_id`,`status`,`completed_at_epoch_millis`,`mastery_score`,`last_attempt_at_epoch_millis`) VALUES (?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final LessonProgressEntity entity) {
        statement.bindString(1, entity.getLessonId());
        statement.bindString(2, entity.getStatus());
        if (entity.getCompletedAtEpochMillis() == null) {
          statement.bindNull(3);
        } else {
          statement.bindLong(3, entity.getCompletedAtEpochMillis());
        }
        if (entity.getMasteryScore() == null) {
          statement.bindNull(4);
        } else {
          statement.bindLong(4, entity.getMasteryScore());
        }
        if (entity.getLastAttemptAtEpochMillis() == null) {
          statement.bindNull(5);
        } else {
          statement.bindLong(5, entity.getLastAttemptAtEpochMillis());
        }
      }
    };
    this.__insertionAdapterOfVocabularyReviewEntity = new EntityInsertionAdapter<VocabularyReviewEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `vocabulary_reviews` (`vocabulary_id`,`last_review_epoch_millis`,`review_count`,`error_count`,`mastery_level`,`next_review_epoch_millis`,`difficulty`) VALUES (?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final VocabularyReviewEntity entity) {
        statement.bindString(1, entity.getVocabularyId());
        if (entity.getLastReviewEpochMillis() == null) {
          statement.bindNull(2);
        } else {
          statement.bindLong(2, entity.getLastReviewEpochMillis());
        }
        statement.bindLong(3, entity.getReviewCount());
        statement.bindLong(4, entity.getErrorCount());
        statement.bindLong(5, entity.getMasteryLevel());
        if (entity.getNextReviewEpochMillis() == null) {
          statement.bindNull(6);
        } else {
          statement.bindLong(6, entity.getNextReviewEpochMillis());
        }
        statement.bindLong(7, entity.getDifficulty());
      }
    };
    this.__insertionAdapterOfDailyActivityEntity = new EntityInsertionAdapter<DailyActivityEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `daily_activity` (`date_string`,`xp_earned`,`lessons_completed`,`minutes_studied`,`updated_at_epoch_millis`) VALUES (?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final DailyActivityEntity entity) {
        statement.bindString(1, entity.getDateString());
        statement.bindLong(2, entity.getXpEarned());
        statement.bindLong(3, entity.getLessonsCompleted());
        statement.bindLong(4, entity.getMinutesStudied());
        statement.bindLong(5, entity.getUpdatedAtEpochMillis());
      }
    };
  }

  @Override
  public Object insertAnswerAttempt(final AnswerAttemptEntity attempt,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfAnswerAttemptEntity.insert(attempt);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object upsertLessonProgress(final LessonProgressEntity progress,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfLessonProgressEntity.insert(progress);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object upsertVocabularyReview(final VocabularyReviewEntity review,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfVocabularyReviewEntity.insert(review);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object upsertDailyActivity(final DailyActivityEntity activity,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfDailyActivityEntity.insert(activity);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<LessonProgressEntity>> observeAllLessonProgress() {
    final String _sql = "SELECT * FROM lesson_progress";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"lesson_progress"}, new Callable<List<LessonProgressEntity>>() {
      @Override
      @NonNull
      public List<LessonProgressEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfLessonId = CursorUtil.getColumnIndexOrThrow(_cursor, "lesson_id");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfCompletedAtEpochMillis = CursorUtil.getColumnIndexOrThrow(_cursor, "completed_at_epoch_millis");
          final int _cursorIndexOfMasteryScore = CursorUtil.getColumnIndexOrThrow(_cursor, "mastery_score");
          final int _cursorIndexOfLastAttemptAtEpochMillis = CursorUtil.getColumnIndexOrThrow(_cursor, "last_attempt_at_epoch_millis");
          final List<LessonProgressEntity> _result = new ArrayList<LessonProgressEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final LessonProgressEntity _item;
            final String _tmpLessonId;
            _tmpLessonId = _cursor.getString(_cursorIndexOfLessonId);
            final String _tmpStatus;
            _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
            final Long _tmpCompletedAtEpochMillis;
            if (_cursor.isNull(_cursorIndexOfCompletedAtEpochMillis)) {
              _tmpCompletedAtEpochMillis = null;
            } else {
              _tmpCompletedAtEpochMillis = _cursor.getLong(_cursorIndexOfCompletedAtEpochMillis);
            }
            final Integer _tmpMasteryScore;
            if (_cursor.isNull(_cursorIndexOfMasteryScore)) {
              _tmpMasteryScore = null;
            } else {
              _tmpMasteryScore = _cursor.getInt(_cursorIndexOfMasteryScore);
            }
            final Long _tmpLastAttemptAtEpochMillis;
            if (_cursor.isNull(_cursorIndexOfLastAttemptAtEpochMillis)) {
              _tmpLastAttemptAtEpochMillis = null;
            } else {
              _tmpLastAttemptAtEpochMillis = _cursor.getLong(_cursorIndexOfLastAttemptAtEpochMillis);
            }
            _item = new LessonProgressEntity(_tmpLessonId,_tmpStatus,_tmpCompletedAtEpochMillis,_tmpMasteryScore,_tmpLastAttemptAtEpochMillis);
            _result.add(_item);
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
  public Flow<LessonProgressEntity> observeLessonProgress(final String lessonId) {
    final String _sql = "SELECT * FROM lesson_progress WHERE lesson_id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, lessonId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"lesson_progress"}, new Callable<LessonProgressEntity>() {
      @Override
      @Nullable
      public LessonProgressEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfLessonId = CursorUtil.getColumnIndexOrThrow(_cursor, "lesson_id");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfCompletedAtEpochMillis = CursorUtil.getColumnIndexOrThrow(_cursor, "completed_at_epoch_millis");
          final int _cursorIndexOfMasteryScore = CursorUtil.getColumnIndexOrThrow(_cursor, "mastery_score");
          final int _cursorIndexOfLastAttemptAtEpochMillis = CursorUtil.getColumnIndexOrThrow(_cursor, "last_attempt_at_epoch_millis");
          final LessonProgressEntity _result;
          if (_cursor.moveToFirst()) {
            final String _tmpLessonId;
            _tmpLessonId = _cursor.getString(_cursorIndexOfLessonId);
            final String _tmpStatus;
            _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
            final Long _tmpCompletedAtEpochMillis;
            if (_cursor.isNull(_cursorIndexOfCompletedAtEpochMillis)) {
              _tmpCompletedAtEpochMillis = null;
            } else {
              _tmpCompletedAtEpochMillis = _cursor.getLong(_cursorIndexOfCompletedAtEpochMillis);
            }
            final Integer _tmpMasteryScore;
            if (_cursor.isNull(_cursorIndexOfMasteryScore)) {
              _tmpMasteryScore = null;
            } else {
              _tmpMasteryScore = _cursor.getInt(_cursorIndexOfMasteryScore);
            }
            final Long _tmpLastAttemptAtEpochMillis;
            if (_cursor.isNull(_cursorIndexOfLastAttemptAtEpochMillis)) {
              _tmpLastAttemptAtEpochMillis = null;
            } else {
              _tmpLastAttemptAtEpochMillis = _cursor.getLong(_cursorIndexOfLastAttemptAtEpochMillis);
            }
            _result = new LessonProgressEntity(_tmpLessonId,_tmpStatus,_tmpCompletedAtEpochMillis,_tmpMasteryScore,_tmpLastAttemptAtEpochMillis);
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
  public Flow<Integer> observeTotalXp() {
    final String _sql = "\n"
            + "        SELECT \n"
            + "            (SELECT COUNT(DISTINCT exercise_id) * 10 \n"
            + "             FROM answer_attempts \n"
            + "             WHERE is_correct = 1)\n"
            + "        +\n"
            + "            (SELECT COUNT(*) * 20 \n"
            + "             FROM lesson_progress \n"
            + "             WHERE status = 'COMPLETED')\n"
            + "        ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"answer_attempts",
        "lesson_progress"}, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final int _tmp;
            _tmp = _cursor.getInt(0);
            _result = _tmp;
          } else {
            _result = 0;
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
  public Object getDueVocabulary(final long currentTimeMillis, final int limit,
      final Continuation<? super List<VocabularyEntity>> $completion) {
    final String _sql = "\n"
            + "        SELECT v.* FROM vocabulary v\n"
            + "        INNER JOIN vocabulary_reviews vr ON v.id = vr.vocabulary_id\n"
            + "        WHERE vr.next_review_epoch_millis <= ?\n"
            + "        ORDER BY vr.next_review_epoch_millis ASC\n"
            + "        LIMIT ?\n"
            + "        ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, currentTimeMillis);
    _argIndex = 2;
    _statement.bindLong(_argIndex, limit);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<VocabularyEntity>>() {
      @Override
      @NonNull
      public List<VocabularyEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfLessonId = CursorUtil.getColumnIndexOrThrow(_cursor, "lesson_id");
          final int _cursorIndexOfEnglish = CursorUtil.getColumnIndexOrThrow(_cursor, "english");
          final int _cursorIndexOfIndonesian = CursorUtil.getColumnIndexOrThrow(_cursor, "indonesian");
          final int _cursorIndexOfPronunciation = CursorUtil.getColumnIndexOrThrow(_cursor, "pronunciation");
          final int _cursorIndexOfExampleEn = CursorUtil.getColumnIndexOrThrow(_cursor, "example_en");
          final int _cursorIndexOfExampleId = CursorUtil.getColumnIndexOrThrow(_cursor, "example_id");
          final int _cursorIndexOfDifficulty = CursorUtil.getColumnIndexOrThrow(_cursor, "difficulty");
          final List<VocabularyEntity> _result = new ArrayList<VocabularyEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final VocabularyEntity _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpLessonId;
            _tmpLessonId = _cursor.getString(_cursorIndexOfLessonId);
            final String _tmpEnglish;
            _tmpEnglish = _cursor.getString(_cursorIndexOfEnglish);
            final String _tmpIndonesian;
            _tmpIndonesian = _cursor.getString(_cursorIndexOfIndonesian);
            final String _tmpPronunciation;
            _tmpPronunciation = _cursor.getString(_cursorIndexOfPronunciation);
            final String _tmpExampleEn;
            _tmpExampleEn = _cursor.getString(_cursorIndexOfExampleEn);
            final String _tmpExampleId;
            _tmpExampleId = _cursor.getString(_cursorIndexOfExampleId);
            final int _tmpDifficulty;
            _tmpDifficulty = _cursor.getInt(_cursorIndexOfDifficulty);
            _item = new VocabularyEntity(_tmpId,_tmpLessonId,_tmpEnglish,_tmpIndonesian,_tmpPronunciation,_tmpExampleEn,_tmpExampleId,_tmpDifficulty);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<Integer> observeDueVocabularyCount(final long currentTimeMillis) {
    final String _sql = "SELECT COUNT(*) FROM vocabulary_reviews WHERE next_review_epoch_millis <= ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, currentTimeMillis);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"vocabulary_reviews"}, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final int _tmp;
            _tmp = _cursor.getInt(0);
            _result = _tmp;
          } else {
            _result = 0;
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
  public Object getVocabularyReview(final String vocabId,
      final Continuation<? super VocabularyReviewEntity> $completion) {
    final String _sql = "SELECT * FROM vocabulary_reviews WHERE vocabulary_id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, vocabId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<VocabularyReviewEntity>() {
      @Override
      @Nullable
      public VocabularyReviewEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfVocabularyId = CursorUtil.getColumnIndexOrThrow(_cursor, "vocabulary_id");
          final int _cursorIndexOfLastReviewEpochMillis = CursorUtil.getColumnIndexOrThrow(_cursor, "last_review_epoch_millis");
          final int _cursorIndexOfReviewCount = CursorUtil.getColumnIndexOrThrow(_cursor, "review_count");
          final int _cursorIndexOfErrorCount = CursorUtil.getColumnIndexOrThrow(_cursor, "error_count");
          final int _cursorIndexOfMasteryLevel = CursorUtil.getColumnIndexOrThrow(_cursor, "mastery_level");
          final int _cursorIndexOfNextReviewEpochMillis = CursorUtil.getColumnIndexOrThrow(_cursor, "next_review_epoch_millis");
          final int _cursorIndexOfDifficulty = CursorUtil.getColumnIndexOrThrow(_cursor, "difficulty");
          final VocabularyReviewEntity _result;
          if (_cursor.moveToFirst()) {
            final String _tmpVocabularyId;
            _tmpVocabularyId = _cursor.getString(_cursorIndexOfVocabularyId);
            final Long _tmpLastReviewEpochMillis;
            if (_cursor.isNull(_cursorIndexOfLastReviewEpochMillis)) {
              _tmpLastReviewEpochMillis = null;
            } else {
              _tmpLastReviewEpochMillis = _cursor.getLong(_cursorIndexOfLastReviewEpochMillis);
            }
            final int _tmpReviewCount;
            _tmpReviewCount = _cursor.getInt(_cursorIndexOfReviewCount);
            final int _tmpErrorCount;
            _tmpErrorCount = _cursor.getInt(_cursorIndexOfErrorCount);
            final int _tmpMasteryLevel;
            _tmpMasteryLevel = _cursor.getInt(_cursorIndexOfMasteryLevel);
            final Long _tmpNextReviewEpochMillis;
            if (_cursor.isNull(_cursorIndexOfNextReviewEpochMillis)) {
              _tmpNextReviewEpochMillis = null;
            } else {
              _tmpNextReviewEpochMillis = _cursor.getLong(_cursorIndexOfNextReviewEpochMillis);
            }
            final int _tmpDifficulty;
            _tmpDifficulty = _cursor.getInt(_cursorIndexOfDifficulty);
            _result = new VocabularyReviewEntity(_tmpVocabularyId,_tmpLastReviewEpochMillis,_tmpReviewCount,_tmpErrorCount,_tmpMasteryLevel,_tmpNextReviewEpochMillis,_tmpDifficulty);
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

  @Override
  public Object getRecentActivities(
      final Continuation<? super List<DailyActivityEntity>> $completion) {
    final String _sql = "SELECT * FROM daily_activity WHERE xp_earned > 0 ORDER BY date_string DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<DailyActivityEntity>>() {
      @Override
      @NonNull
      public List<DailyActivityEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfDateString = CursorUtil.getColumnIndexOrThrow(_cursor, "date_string");
          final int _cursorIndexOfXpEarned = CursorUtil.getColumnIndexOrThrow(_cursor, "xp_earned");
          final int _cursorIndexOfLessonsCompleted = CursorUtil.getColumnIndexOrThrow(_cursor, "lessons_completed");
          final int _cursorIndexOfMinutesStudied = CursorUtil.getColumnIndexOrThrow(_cursor, "minutes_studied");
          final int _cursorIndexOfUpdatedAtEpochMillis = CursorUtil.getColumnIndexOrThrow(_cursor, "updated_at_epoch_millis");
          final List<DailyActivityEntity> _result = new ArrayList<DailyActivityEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final DailyActivityEntity _item;
            final String _tmpDateString;
            _tmpDateString = _cursor.getString(_cursorIndexOfDateString);
            final int _tmpXpEarned;
            _tmpXpEarned = _cursor.getInt(_cursorIndexOfXpEarned);
            final int _tmpLessonsCompleted;
            _tmpLessonsCompleted = _cursor.getInt(_cursorIndexOfLessonsCompleted);
            final int _tmpMinutesStudied;
            _tmpMinutesStudied = _cursor.getInt(_cursorIndexOfMinutesStudied);
            final long _tmpUpdatedAtEpochMillis;
            _tmpUpdatedAtEpochMillis = _cursor.getLong(_cursorIndexOfUpdatedAtEpochMillis);
            _item = new DailyActivityEntity(_tmpDateString,_tmpXpEarned,_tmpLessonsCompleted,_tmpMinutesStudied,_tmpUpdatedAtEpochMillis);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getDailyActivity(final String dateString,
      final Continuation<? super DailyActivityEntity> $completion) {
    final String _sql = "SELECT * FROM daily_activity WHERE date_string = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, dateString);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<DailyActivityEntity>() {
      @Override
      @Nullable
      public DailyActivityEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfDateString = CursorUtil.getColumnIndexOrThrow(_cursor, "date_string");
          final int _cursorIndexOfXpEarned = CursorUtil.getColumnIndexOrThrow(_cursor, "xp_earned");
          final int _cursorIndexOfLessonsCompleted = CursorUtil.getColumnIndexOrThrow(_cursor, "lessons_completed");
          final int _cursorIndexOfMinutesStudied = CursorUtil.getColumnIndexOrThrow(_cursor, "minutes_studied");
          final int _cursorIndexOfUpdatedAtEpochMillis = CursorUtil.getColumnIndexOrThrow(_cursor, "updated_at_epoch_millis");
          final DailyActivityEntity _result;
          if (_cursor.moveToFirst()) {
            final String _tmpDateString;
            _tmpDateString = _cursor.getString(_cursorIndexOfDateString);
            final int _tmpXpEarned;
            _tmpXpEarned = _cursor.getInt(_cursorIndexOfXpEarned);
            final int _tmpLessonsCompleted;
            _tmpLessonsCompleted = _cursor.getInt(_cursorIndexOfLessonsCompleted);
            final int _tmpMinutesStudied;
            _tmpMinutesStudied = _cursor.getInt(_cursorIndexOfMinutesStudied);
            final long _tmpUpdatedAtEpochMillis;
            _tmpUpdatedAtEpochMillis = _cursor.getLong(_cursorIndexOfUpdatedAtEpochMillis);
            _result = new DailyActivityEntity(_tmpDateString,_tmpXpEarned,_tmpLessonsCompleted,_tmpMinutesStudied,_tmpUpdatedAtEpochMillis);
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
