package com.linguaquest.data.local;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomOpenHelper;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class LinguaQuestDatabase_Impl extends LinguaQuestDatabase {
  private volatile ContentDao _contentDao;

  private volatile ProgressDao _progressDao;

  private volatile UserDao _userDao;

  @Override
  @NonNull
  protected SupportSQLiteOpenHelper createOpenHelper(@NonNull final DatabaseConfiguration config) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(config, new RoomOpenHelper.Delegate(4) {
      @Override
      public void createAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `learning_levels` (`id` TEXT NOT NULL, `name` TEXT NOT NULL, `description` TEXT NOT NULL, `display_order` INTEGER NOT NULL, PRIMARY KEY(`id`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `courses` (`id` TEXT NOT NULL, `title` TEXT NOT NULL, `description` TEXT NOT NULL, `display_order` INTEGER NOT NULL, PRIMARY KEY(`id`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `units` (`id` TEXT NOT NULL, `course_id` TEXT NOT NULL, `title` TEXT NOT NULL, `description` TEXT NOT NULL, `level_id` TEXT NOT NULL, `display_order` INTEGER NOT NULL, PRIMARY KEY(`id`), FOREIGN KEY(`course_id`) REFERENCES `courses`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE , FOREIGN KEY(`level_id`) REFERENCES `learning_levels`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_units_course_id` ON `units` (`course_id`)");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_units_level_id` ON `units` (`level_id`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `lessons` (`id` TEXT NOT NULL, `unit_id` TEXT NOT NULL, `title` TEXT NOT NULL, `description` TEXT NOT NULL, `estimated_minutes` INTEGER NOT NULL, `display_order` INTEGER NOT NULL, PRIMARY KEY(`id`), FOREIGN KEY(`unit_id`) REFERENCES `units`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_lessons_unit_id` ON `lessons` (`unit_id`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `exercises` (`id` TEXT NOT NULL, `lesson_id` TEXT NOT NULL, `type` TEXT NOT NULL, `skill` TEXT NOT NULL, `instruction` TEXT NOT NULL, `prompt` TEXT NOT NULL, `answer` TEXT NOT NULL, `explanation` TEXT NOT NULL, `difficulty` INTEGER NOT NULL, `display_order` INTEGER NOT NULL, PRIMARY KEY(`id`), FOREIGN KEY(`lesson_id`) REFERENCES `lessons`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_exercises_lesson_id` ON `exercises` (`lesson_id`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `exercise_options` (`id` TEXT NOT NULL, `exercise_id` TEXT NOT NULL, `text` TEXT NOT NULL, `is_correct` INTEGER NOT NULL, `display_order` INTEGER NOT NULL, PRIMARY KEY(`id`), FOREIGN KEY(`exercise_id`) REFERENCES `exercises`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_exercise_options_exercise_id` ON `exercise_options` (`exercise_id`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `vocabulary` (`id` TEXT NOT NULL, `lesson_id` TEXT NOT NULL, `english` TEXT NOT NULL, `indonesian` TEXT NOT NULL, `pronunciation` TEXT NOT NULL, `example_en` TEXT NOT NULL, `example_id` TEXT NOT NULL, `difficulty` INTEGER NOT NULL, PRIMARY KEY(`id`), FOREIGN KEY(`lesson_id`) REFERENCES `lessons`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_vocabulary_lesson_id` ON `vocabulary` (`lesson_id`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `example_sentences` (`id` TEXT NOT NULL, `vocabulary_id` TEXT NOT NULL, `english` TEXT NOT NULL, `indonesian` TEXT NOT NULL, PRIMARY KEY(`id`), FOREIGN KEY(`vocabulary_id`) REFERENCES `vocabulary`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_example_sentences_vocabulary_id` ON `example_sentences` (`vocabulary_id`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `user_profile` (`id` INTEGER NOT NULL, `display_name` TEXT NOT NULL, `target_minutes_per_day` INTEGER NOT NULL, `onboarding_completed` INTEGER NOT NULL, `placement_completed` INTEGER NOT NULL, `current_level_id` TEXT, `created_at_epoch_millis` INTEGER NOT NULL, PRIMARY KEY(`id`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `learning_sessions` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `started_at_epoch_millis` INTEGER NOT NULL, `ended_at_epoch_millis` INTEGER, `focus_skill` TEXT, `xp_earned` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `answer_attempts` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `exercise_id` TEXT NOT NULL, `lesson_id` TEXT, `is_correct` INTEGER NOT NULL, `answer_text` TEXT, `created_at_epoch_millis` INTEGER NOT NULL, FOREIGN KEY(`exercise_id`) REFERENCES `exercises`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_answer_attempts_exercise_id` ON `answer_attempts` (`exercise_id`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `lesson_progress` (`lesson_id` TEXT NOT NULL, `status` TEXT NOT NULL, `completed_at_epoch_millis` INTEGER, `mastery_score` INTEGER, `last_attempt_at_epoch_millis` INTEGER, PRIMARY KEY(`lesson_id`), FOREIGN KEY(`lesson_id`) REFERENCES `lessons`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        db.execSQL("CREATE TABLE IF NOT EXISTS `vocabulary_reviews` (`vocabulary_id` TEXT NOT NULL, `last_review_epoch_millis` INTEGER, `review_count` INTEGER NOT NULL, `error_count` INTEGER NOT NULL, `mastery_level` INTEGER NOT NULL, `next_review_epoch_millis` INTEGER, `difficulty` INTEGER NOT NULL, PRIMARY KEY(`vocabulary_id`), FOREIGN KEY(`vocabulary_id`) REFERENCES `vocabulary`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        db.execSQL("CREATE TABLE IF NOT EXISTS `achievements` (`id` TEXT NOT NULL, `title` TEXT NOT NULL, `description` TEXT NOT NULL, `unlocked_at_epoch_millis` INTEGER, PRIMARY KEY(`id`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `daily_goal` (`id` INTEGER NOT NULL, `target_minutes` INTEGER NOT NULL, `target_xp` INTEGER NOT NULL, `updated_at_epoch_millis` INTEGER NOT NULL, PRIMARY KEY(`id`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `study_reminder` (`id` INTEGER NOT NULL, `enabled` INTEGER NOT NULL, `hour` INTEGER NOT NULL, `minute` INTEGER NOT NULL, `days_of_week_mask` INTEGER NOT NULL, PRIMARY KEY(`id`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `personal_notes` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `lesson_id` TEXT, `vocabulary_id` TEXT, `content` TEXT NOT NULL, `updated_at_epoch_millis` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `bookmarks` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `lesson_id` TEXT, `unit_id` TEXT, `created_at_epoch_millis` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `daily_activity` (`date_string` TEXT NOT NULL, `xp_earned` INTEGER NOT NULL, `lessons_completed` INTEGER NOT NULL, `minutes_studied` INTEGER NOT NULL, `updated_at_epoch_millis` INTEGER NOT NULL, PRIMARY KEY(`date_string`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '54cd0116735e718e68bcd3da0474e30f')");
      }

      @Override
      public void dropAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS `learning_levels`");
        db.execSQL("DROP TABLE IF EXISTS `courses`");
        db.execSQL("DROP TABLE IF EXISTS `units`");
        db.execSQL("DROP TABLE IF EXISTS `lessons`");
        db.execSQL("DROP TABLE IF EXISTS `exercises`");
        db.execSQL("DROP TABLE IF EXISTS `exercise_options`");
        db.execSQL("DROP TABLE IF EXISTS `vocabulary`");
        db.execSQL("DROP TABLE IF EXISTS `example_sentences`");
        db.execSQL("DROP TABLE IF EXISTS `user_profile`");
        db.execSQL("DROP TABLE IF EXISTS `learning_sessions`");
        db.execSQL("DROP TABLE IF EXISTS `answer_attempts`");
        db.execSQL("DROP TABLE IF EXISTS `lesson_progress`");
        db.execSQL("DROP TABLE IF EXISTS `vocabulary_reviews`");
        db.execSQL("DROP TABLE IF EXISTS `achievements`");
        db.execSQL("DROP TABLE IF EXISTS `daily_goal`");
        db.execSQL("DROP TABLE IF EXISTS `study_reminder`");
        db.execSQL("DROP TABLE IF EXISTS `personal_notes`");
        db.execSQL("DROP TABLE IF EXISTS `bookmarks`");
        db.execSQL("DROP TABLE IF EXISTS `daily_activity`");
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onDestructiveMigration(db);
          }
        }
      }

      @Override
      public void onCreate(@NonNull final SupportSQLiteDatabase db) {
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onCreate(db);
          }
        }
      }

      @Override
      public void onOpen(@NonNull final SupportSQLiteDatabase db) {
        mDatabase = db;
        db.execSQL("PRAGMA foreign_keys = ON");
        internalInitInvalidationTracker(db);
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onOpen(db);
          }
        }
      }

      @Override
      public void onPreMigrate(@NonNull final SupportSQLiteDatabase db) {
        DBUtil.dropFtsSyncTriggers(db);
      }

      @Override
      public void onPostMigrate(@NonNull final SupportSQLiteDatabase db) {
      }

      @Override
      @NonNull
      public RoomOpenHelper.ValidationResult onValidateSchema(
          @NonNull final SupportSQLiteDatabase db) {
        final HashMap<String, TableInfo.Column> _columnsLearningLevels = new HashMap<String, TableInfo.Column>(4);
        _columnsLearningLevels.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLearningLevels.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLearningLevels.put("description", new TableInfo.Column("description", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLearningLevels.put("display_order", new TableInfo.Column("display_order", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysLearningLevels = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesLearningLevels = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoLearningLevels = new TableInfo("learning_levels", _columnsLearningLevels, _foreignKeysLearningLevels, _indicesLearningLevels);
        final TableInfo _existingLearningLevels = TableInfo.read(db, "learning_levels");
        if (!_infoLearningLevels.equals(_existingLearningLevels)) {
          return new RoomOpenHelper.ValidationResult(false, "learning_levels(com.linguaquest.data.local.entity.LearningLevelEntity).\n"
                  + " Expected:\n" + _infoLearningLevels + "\n"
                  + " Found:\n" + _existingLearningLevels);
        }
        final HashMap<String, TableInfo.Column> _columnsCourses = new HashMap<String, TableInfo.Column>(4);
        _columnsCourses.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCourses.put("title", new TableInfo.Column("title", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCourses.put("description", new TableInfo.Column("description", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCourses.put("display_order", new TableInfo.Column("display_order", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysCourses = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesCourses = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoCourses = new TableInfo("courses", _columnsCourses, _foreignKeysCourses, _indicesCourses);
        final TableInfo _existingCourses = TableInfo.read(db, "courses");
        if (!_infoCourses.equals(_existingCourses)) {
          return new RoomOpenHelper.ValidationResult(false, "courses(com.linguaquest.data.local.entity.CourseEntity).\n"
                  + " Expected:\n" + _infoCourses + "\n"
                  + " Found:\n" + _existingCourses);
        }
        final HashMap<String, TableInfo.Column> _columnsUnits = new HashMap<String, TableInfo.Column>(6);
        _columnsUnits.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUnits.put("course_id", new TableInfo.Column("course_id", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUnits.put("title", new TableInfo.Column("title", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUnits.put("description", new TableInfo.Column("description", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUnits.put("level_id", new TableInfo.Column("level_id", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUnits.put("display_order", new TableInfo.Column("display_order", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysUnits = new HashSet<TableInfo.ForeignKey>(2);
        _foreignKeysUnits.add(new TableInfo.ForeignKey("courses", "CASCADE", "NO ACTION", Arrays.asList("course_id"), Arrays.asList("id")));
        _foreignKeysUnits.add(new TableInfo.ForeignKey("learning_levels", "CASCADE", "NO ACTION", Arrays.asList("level_id"), Arrays.asList("id")));
        final HashSet<TableInfo.Index> _indicesUnits = new HashSet<TableInfo.Index>(2);
        _indicesUnits.add(new TableInfo.Index("index_units_course_id", false, Arrays.asList("course_id"), Arrays.asList("ASC")));
        _indicesUnits.add(new TableInfo.Index("index_units_level_id", false, Arrays.asList("level_id"), Arrays.asList("ASC")));
        final TableInfo _infoUnits = new TableInfo("units", _columnsUnits, _foreignKeysUnits, _indicesUnits);
        final TableInfo _existingUnits = TableInfo.read(db, "units");
        if (!_infoUnits.equals(_existingUnits)) {
          return new RoomOpenHelper.ValidationResult(false, "units(com.linguaquest.data.local.entity.UnitEntity).\n"
                  + " Expected:\n" + _infoUnits + "\n"
                  + " Found:\n" + _existingUnits);
        }
        final HashMap<String, TableInfo.Column> _columnsLessons = new HashMap<String, TableInfo.Column>(6);
        _columnsLessons.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLessons.put("unit_id", new TableInfo.Column("unit_id", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLessons.put("title", new TableInfo.Column("title", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLessons.put("description", new TableInfo.Column("description", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLessons.put("estimated_minutes", new TableInfo.Column("estimated_minutes", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLessons.put("display_order", new TableInfo.Column("display_order", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysLessons = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysLessons.add(new TableInfo.ForeignKey("units", "CASCADE", "NO ACTION", Arrays.asList("unit_id"), Arrays.asList("id")));
        final HashSet<TableInfo.Index> _indicesLessons = new HashSet<TableInfo.Index>(1);
        _indicesLessons.add(new TableInfo.Index("index_lessons_unit_id", false, Arrays.asList("unit_id"), Arrays.asList("ASC")));
        final TableInfo _infoLessons = new TableInfo("lessons", _columnsLessons, _foreignKeysLessons, _indicesLessons);
        final TableInfo _existingLessons = TableInfo.read(db, "lessons");
        if (!_infoLessons.equals(_existingLessons)) {
          return new RoomOpenHelper.ValidationResult(false, "lessons(com.linguaquest.data.local.entity.LessonEntity).\n"
                  + " Expected:\n" + _infoLessons + "\n"
                  + " Found:\n" + _existingLessons);
        }
        final HashMap<String, TableInfo.Column> _columnsExercises = new HashMap<String, TableInfo.Column>(10);
        _columnsExercises.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsExercises.put("lesson_id", new TableInfo.Column("lesson_id", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsExercises.put("type", new TableInfo.Column("type", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsExercises.put("skill", new TableInfo.Column("skill", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsExercises.put("instruction", new TableInfo.Column("instruction", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsExercises.put("prompt", new TableInfo.Column("prompt", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsExercises.put("answer", new TableInfo.Column("answer", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsExercises.put("explanation", new TableInfo.Column("explanation", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsExercises.put("difficulty", new TableInfo.Column("difficulty", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsExercises.put("display_order", new TableInfo.Column("display_order", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysExercises = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysExercises.add(new TableInfo.ForeignKey("lessons", "CASCADE", "NO ACTION", Arrays.asList("lesson_id"), Arrays.asList("id")));
        final HashSet<TableInfo.Index> _indicesExercises = new HashSet<TableInfo.Index>(1);
        _indicesExercises.add(new TableInfo.Index("index_exercises_lesson_id", false, Arrays.asList("lesson_id"), Arrays.asList("ASC")));
        final TableInfo _infoExercises = new TableInfo("exercises", _columnsExercises, _foreignKeysExercises, _indicesExercises);
        final TableInfo _existingExercises = TableInfo.read(db, "exercises");
        if (!_infoExercises.equals(_existingExercises)) {
          return new RoomOpenHelper.ValidationResult(false, "exercises(com.linguaquest.data.local.entity.ExerciseEntity).\n"
                  + " Expected:\n" + _infoExercises + "\n"
                  + " Found:\n" + _existingExercises);
        }
        final HashMap<String, TableInfo.Column> _columnsExerciseOptions = new HashMap<String, TableInfo.Column>(5);
        _columnsExerciseOptions.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsExerciseOptions.put("exercise_id", new TableInfo.Column("exercise_id", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsExerciseOptions.put("text", new TableInfo.Column("text", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsExerciseOptions.put("is_correct", new TableInfo.Column("is_correct", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsExerciseOptions.put("display_order", new TableInfo.Column("display_order", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysExerciseOptions = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysExerciseOptions.add(new TableInfo.ForeignKey("exercises", "CASCADE", "NO ACTION", Arrays.asList("exercise_id"), Arrays.asList("id")));
        final HashSet<TableInfo.Index> _indicesExerciseOptions = new HashSet<TableInfo.Index>(1);
        _indicesExerciseOptions.add(new TableInfo.Index("index_exercise_options_exercise_id", false, Arrays.asList("exercise_id"), Arrays.asList("ASC")));
        final TableInfo _infoExerciseOptions = new TableInfo("exercise_options", _columnsExerciseOptions, _foreignKeysExerciseOptions, _indicesExerciseOptions);
        final TableInfo _existingExerciseOptions = TableInfo.read(db, "exercise_options");
        if (!_infoExerciseOptions.equals(_existingExerciseOptions)) {
          return new RoomOpenHelper.ValidationResult(false, "exercise_options(com.linguaquest.data.local.entity.ExerciseOptionEntity).\n"
                  + " Expected:\n" + _infoExerciseOptions + "\n"
                  + " Found:\n" + _existingExerciseOptions);
        }
        final HashMap<String, TableInfo.Column> _columnsVocabulary = new HashMap<String, TableInfo.Column>(8);
        _columnsVocabulary.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVocabulary.put("lesson_id", new TableInfo.Column("lesson_id", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVocabulary.put("english", new TableInfo.Column("english", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVocabulary.put("indonesian", new TableInfo.Column("indonesian", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVocabulary.put("pronunciation", new TableInfo.Column("pronunciation", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVocabulary.put("example_en", new TableInfo.Column("example_en", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVocabulary.put("example_id", new TableInfo.Column("example_id", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVocabulary.put("difficulty", new TableInfo.Column("difficulty", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysVocabulary = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysVocabulary.add(new TableInfo.ForeignKey("lessons", "CASCADE", "NO ACTION", Arrays.asList("lesson_id"), Arrays.asList("id")));
        final HashSet<TableInfo.Index> _indicesVocabulary = new HashSet<TableInfo.Index>(1);
        _indicesVocabulary.add(new TableInfo.Index("index_vocabulary_lesson_id", false, Arrays.asList("lesson_id"), Arrays.asList("ASC")));
        final TableInfo _infoVocabulary = new TableInfo("vocabulary", _columnsVocabulary, _foreignKeysVocabulary, _indicesVocabulary);
        final TableInfo _existingVocabulary = TableInfo.read(db, "vocabulary");
        if (!_infoVocabulary.equals(_existingVocabulary)) {
          return new RoomOpenHelper.ValidationResult(false, "vocabulary(com.linguaquest.data.local.entity.VocabularyEntity).\n"
                  + " Expected:\n" + _infoVocabulary + "\n"
                  + " Found:\n" + _existingVocabulary);
        }
        final HashMap<String, TableInfo.Column> _columnsExampleSentences = new HashMap<String, TableInfo.Column>(4);
        _columnsExampleSentences.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsExampleSentences.put("vocabulary_id", new TableInfo.Column("vocabulary_id", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsExampleSentences.put("english", new TableInfo.Column("english", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsExampleSentences.put("indonesian", new TableInfo.Column("indonesian", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysExampleSentences = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysExampleSentences.add(new TableInfo.ForeignKey("vocabulary", "CASCADE", "NO ACTION", Arrays.asList("vocabulary_id"), Arrays.asList("id")));
        final HashSet<TableInfo.Index> _indicesExampleSentences = new HashSet<TableInfo.Index>(1);
        _indicesExampleSentences.add(new TableInfo.Index("index_example_sentences_vocabulary_id", false, Arrays.asList("vocabulary_id"), Arrays.asList("ASC")));
        final TableInfo _infoExampleSentences = new TableInfo("example_sentences", _columnsExampleSentences, _foreignKeysExampleSentences, _indicesExampleSentences);
        final TableInfo _existingExampleSentences = TableInfo.read(db, "example_sentences");
        if (!_infoExampleSentences.equals(_existingExampleSentences)) {
          return new RoomOpenHelper.ValidationResult(false, "example_sentences(com.linguaquest.data.local.entity.ExampleSentenceEntity).\n"
                  + " Expected:\n" + _infoExampleSentences + "\n"
                  + " Found:\n" + _existingExampleSentences);
        }
        final HashMap<String, TableInfo.Column> _columnsUserProfile = new HashMap<String, TableInfo.Column>(7);
        _columnsUserProfile.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserProfile.put("display_name", new TableInfo.Column("display_name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserProfile.put("target_minutes_per_day", new TableInfo.Column("target_minutes_per_day", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserProfile.put("onboarding_completed", new TableInfo.Column("onboarding_completed", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserProfile.put("placement_completed", new TableInfo.Column("placement_completed", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserProfile.put("current_level_id", new TableInfo.Column("current_level_id", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserProfile.put("created_at_epoch_millis", new TableInfo.Column("created_at_epoch_millis", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysUserProfile = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesUserProfile = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoUserProfile = new TableInfo("user_profile", _columnsUserProfile, _foreignKeysUserProfile, _indicesUserProfile);
        final TableInfo _existingUserProfile = TableInfo.read(db, "user_profile");
        if (!_infoUserProfile.equals(_existingUserProfile)) {
          return new RoomOpenHelper.ValidationResult(false, "user_profile(com.linguaquest.data.local.entity.UserProfileEntity).\n"
                  + " Expected:\n" + _infoUserProfile + "\n"
                  + " Found:\n" + _existingUserProfile);
        }
        final HashMap<String, TableInfo.Column> _columnsLearningSessions = new HashMap<String, TableInfo.Column>(5);
        _columnsLearningSessions.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLearningSessions.put("started_at_epoch_millis", new TableInfo.Column("started_at_epoch_millis", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLearningSessions.put("ended_at_epoch_millis", new TableInfo.Column("ended_at_epoch_millis", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLearningSessions.put("focus_skill", new TableInfo.Column("focus_skill", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLearningSessions.put("xp_earned", new TableInfo.Column("xp_earned", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysLearningSessions = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesLearningSessions = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoLearningSessions = new TableInfo("learning_sessions", _columnsLearningSessions, _foreignKeysLearningSessions, _indicesLearningSessions);
        final TableInfo _existingLearningSessions = TableInfo.read(db, "learning_sessions");
        if (!_infoLearningSessions.equals(_existingLearningSessions)) {
          return new RoomOpenHelper.ValidationResult(false, "learning_sessions(com.linguaquest.data.local.entity.LearningSessionEntity).\n"
                  + " Expected:\n" + _infoLearningSessions + "\n"
                  + " Found:\n" + _existingLearningSessions);
        }
        final HashMap<String, TableInfo.Column> _columnsAnswerAttempts = new HashMap<String, TableInfo.Column>(6);
        _columnsAnswerAttempts.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAnswerAttempts.put("exercise_id", new TableInfo.Column("exercise_id", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAnswerAttempts.put("lesson_id", new TableInfo.Column("lesson_id", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAnswerAttempts.put("is_correct", new TableInfo.Column("is_correct", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAnswerAttempts.put("answer_text", new TableInfo.Column("answer_text", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAnswerAttempts.put("created_at_epoch_millis", new TableInfo.Column("created_at_epoch_millis", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysAnswerAttempts = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysAnswerAttempts.add(new TableInfo.ForeignKey("exercises", "CASCADE", "NO ACTION", Arrays.asList("exercise_id"), Arrays.asList("id")));
        final HashSet<TableInfo.Index> _indicesAnswerAttempts = new HashSet<TableInfo.Index>(1);
        _indicesAnswerAttempts.add(new TableInfo.Index("index_answer_attempts_exercise_id", false, Arrays.asList("exercise_id"), Arrays.asList("ASC")));
        final TableInfo _infoAnswerAttempts = new TableInfo("answer_attempts", _columnsAnswerAttempts, _foreignKeysAnswerAttempts, _indicesAnswerAttempts);
        final TableInfo _existingAnswerAttempts = TableInfo.read(db, "answer_attempts");
        if (!_infoAnswerAttempts.equals(_existingAnswerAttempts)) {
          return new RoomOpenHelper.ValidationResult(false, "answer_attempts(com.linguaquest.data.local.entity.AnswerAttemptEntity).\n"
                  + " Expected:\n" + _infoAnswerAttempts + "\n"
                  + " Found:\n" + _existingAnswerAttempts);
        }
        final HashMap<String, TableInfo.Column> _columnsLessonProgress = new HashMap<String, TableInfo.Column>(5);
        _columnsLessonProgress.put("lesson_id", new TableInfo.Column("lesson_id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLessonProgress.put("status", new TableInfo.Column("status", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLessonProgress.put("completed_at_epoch_millis", new TableInfo.Column("completed_at_epoch_millis", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLessonProgress.put("mastery_score", new TableInfo.Column("mastery_score", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLessonProgress.put("last_attempt_at_epoch_millis", new TableInfo.Column("last_attempt_at_epoch_millis", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysLessonProgress = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysLessonProgress.add(new TableInfo.ForeignKey("lessons", "CASCADE", "NO ACTION", Arrays.asList("lesson_id"), Arrays.asList("id")));
        final HashSet<TableInfo.Index> _indicesLessonProgress = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoLessonProgress = new TableInfo("lesson_progress", _columnsLessonProgress, _foreignKeysLessonProgress, _indicesLessonProgress);
        final TableInfo _existingLessonProgress = TableInfo.read(db, "lesson_progress");
        if (!_infoLessonProgress.equals(_existingLessonProgress)) {
          return new RoomOpenHelper.ValidationResult(false, "lesson_progress(com.linguaquest.data.local.entity.LessonProgressEntity).\n"
                  + " Expected:\n" + _infoLessonProgress + "\n"
                  + " Found:\n" + _existingLessonProgress);
        }
        final HashMap<String, TableInfo.Column> _columnsVocabularyReviews = new HashMap<String, TableInfo.Column>(7);
        _columnsVocabularyReviews.put("vocabulary_id", new TableInfo.Column("vocabulary_id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVocabularyReviews.put("last_review_epoch_millis", new TableInfo.Column("last_review_epoch_millis", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVocabularyReviews.put("review_count", new TableInfo.Column("review_count", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVocabularyReviews.put("error_count", new TableInfo.Column("error_count", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVocabularyReviews.put("mastery_level", new TableInfo.Column("mastery_level", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVocabularyReviews.put("next_review_epoch_millis", new TableInfo.Column("next_review_epoch_millis", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVocabularyReviews.put("difficulty", new TableInfo.Column("difficulty", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysVocabularyReviews = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysVocabularyReviews.add(new TableInfo.ForeignKey("vocabulary", "CASCADE", "NO ACTION", Arrays.asList("vocabulary_id"), Arrays.asList("id")));
        final HashSet<TableInfo.Index> _indicesVocabularyReviews = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoVocabularyReviews = new TableInfo("vocabulary_reviews", _columnsVocabularyReviews, _foreignKeysVocabularyReviews, _indicesVocabularyReviews);
        final TableInfo _existingVocabularyReviews = TableInfo.read(db, "vocabulary_reviews");
        if (!_infoVocabularyReviews.equals(_existingVocabularyReviews)) {
          return new RoomOpenHelper.ValidationResult(false, "vocabulary_reviews(com.linguaquest.data.local.entity.VocabularyReviewEntity).\n"
                  + " Expected:\n" + _infoVocabularyReviews + "\n"
                  + " Found:\n" + _existingVocabularyReviews);
        }
        final HashMap<String, TableInfo.Column> _columnsAchievements = new HashMap<String, TableInfo.Column>(4);
        _columnsAchievements.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAchievements.put("title", new TableInfo.Column("title", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAchievements.put("description", new TableInfo.Column("description", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAchievements.put("unlocked_at_epoch_millis", new TableInfo.Column("unlocked_at_epoch_millis", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysAchievements = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesAchievements = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoAchievements = new TableInfo("achievements", _columnsAchievements, _foreignKeysAchievements, _indicesAchievements);
        final TableInfo _existingAchievements = TableInfo.read(db, "achievements");
        if (!_infoAchievements.equals(_existingAchievements)) {
          return new RoomOpenHelper.ValidationResult(false, "achievements(com.linguaquest.data.local.entity.AchievementEntity).\n"
                  + " Expected:\n" + _infoAchievements + "\n"
                  + " Found:\n" + _existingAchievements);
        }
        final HashMap<String, TableInfo.Column> _columnsDailyGoal = new HashMap<String, TableInfo.Column>(4);
        _columnsDailyGoal.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDailyGoal.put("target_minutes", new TableInfo.Column("target_minutes", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDailyGoal.put("target_xp", new TableInfo.Column("target_xp", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDailyGoal.put("updated_at_epoch_millis", new TableInfo.Column("updated_at_epoch_millis", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysDailyGoal = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesDailyGoal = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoDailyGoal = new TableInfo("daily_goal", _columnsDailyGoal, _foreignKeysDailyGoal, _indicesDailyGoal);
        final TableInfo _existingDailyGoal = TableInfo.read(db, "daily_goal");
        if (!_infoDailyGoal.equals(_existingDailyGoal)) {
          return new RoomOpenHelper.ValidationResult(false, "daily_goal(com.linguaquest.data.local.entity.DailyGoalEntity).\n"
                  + " Expected:\n" + _infoDailyGoal + "\n"
                  + " Found:\n" + _existingDailyGoal);
        }
        final HashMap<String, TableInfo.Column> _columnsStudyReminder = new HashMap<String, TableInfo.Column>(5);
        _columnsStudyReminder.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStudyReminder.put("enabled", new TableInfo.Column("enabled", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStudyReminder.put("hour", new TableInfo.Column("hour", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStudyReminder.put("minute", new TableInfo.Column("minute", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStudyReminder.put("days_of_week_mask", new TableInfo.Column("days_of_week_mask", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysStudyReminder = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesStudyReminder = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoStudyReminder = new TableInfo("study_reminder", _columnsStudyReminder, _foreignKeysStudyReminder, _indicesStudyReminder);
        final TableInfo _existingStudyReminder = TableInfo.read(db, "study_reminder");
        if (!_infoStudyReminder.equals(_existingStudyReminder)) {
          return new RoomOpenHelper.ValidationResult(false, "study_reminder(com.linguaquest.data.local.entity.StudyReminderEntity).\n"
                  + " Expected:\n" + _infoStudyReminder + "\n"
                  + " Found:\n" + _existingStudyReminder);
        }
        final HashMap<String, TableInfo.Column> _columnsPersonalNotes = new HashMap<String, TableInfo.Column>(5);
        _columnsPersonalNotes.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPersonalNotes.put("lesson_id", new TableInfo.Column("lesson_id", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPersonalNotes.put("vocabulary_id", new TableInfo.Column("vocabulary_id", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPersonalNotes.put("content", new TableInfo.Column("content", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPersonalNotes.put("updated_at_epoch_millis", new TableInfo.Column("updated_at_epoch_millis", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysPersonalNotes = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesPersonalNotes = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoPersonalNotes = new TableInfo("personal_notes", _columnsPersonalNotes, _foreignKeysPersonalNotes, _indicesPersonalNotes);
        final TableInfo _existingPersonalNotes = TableInfo.read(db, "personal_notes");
        if (!_infoPersonalNotes.equals(_existingPersonalNotes)) {
          return new RoomOpenHelper.ValidationResult(false, "personal_notes(com.linguaquest.data.local.entity.PersonalNoteEntity).\n"
                  + " Expected:\n" + _infoPersonalNotes + "\n"
                  + " Found:\n" + _existingPersonalNotes);
        }
        final HashMap<String, TableInfo.Column> _columnsBookmarks = new HashMap<String, TableInfo.Column>(4);
        _columnsBookmarks.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBookmarks.put("lesson_id", new TableInfo.Column("lesson_id", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBookmarks.put("unit_id", new TableInfo.Column("unit_id", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBookmarks.put("created_at_epoch_millis", new TableInfo.Column("created_at_epoch_millis", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysBookmarks = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesBookmarks = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoBookmarks = new TableInfo("bookmarks", _columnsBookmarks, _foreignKeysBookmarks, _indicesBookmarks);
        final TableInfo _existingBookmarks = TableInfo.read(db, "bookmarks");
        if (!_infoBookmarks.equals(_existingBookmarks)) {
          return new RoomOpenHelper.ValidationResult(false, "bookmarks(com.linguaquest.data.local.entity.BookmarkEntity).\n"
                  + " Expected:\n" + _infoBookmarks + "\n"
                  + " Found:\n" + _existingBookmarks);
        }
        final HashMap<String, TableInfo.Column> _columnsDailyActivity = new HashMap<String, TableInfo.Column>(5);
        _columnsDailyActivity.put("date_string", new TableInfo.Column("date_string", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDailyActivity.put("xp_earned", new TableInfo.Column("xp_earned", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDailyActivity.put("lessons_completed", new TableInfo.Column("lessons_completed", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDailyActivity.put("minutes_studied", new TableInfo.Column("minutes_studied", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDailyActivity.put("updated_at_epoch_millis", new TableInfo.Column("updated_at_epoch_millis", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysDailyActivity = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesDailyActivity = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoDailyActivity = new TableInfo("daily_activity", _columnsDailyActivity, _foreignKeysDailyActivity, _indicesDailyActivity);
        final TableInfo _existingDailyActivity = TableInfo.read(db, "daily_activity");
        if (!_infoDailyActivity.equals(_existingDailyActivity)) {
          return new RoomOpenHelper.ValidationResult(false, "daily_activity(com.linguaquest.data.local.entity.DailyActivityEntity).\n"
                  + " Expected:\n" + _infoDailyActivity + "\n"
                  + " Found:\n" + _existingDailyActivity);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "54cd0116735e718e68bcd3da0474e30f", "fb3aa5ba3421e0722f9ec4dc09bdbe93");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
    final SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "learning_levels","courses","units","lessons","exercises","exercise_options","vocabulary","example_sentences","user_profile","learning_sessions","answer_attempts","lesson_progress","vocabulary_reviews","achievements","daily_goal","study_reminder","personal_notes","bookmarks","daily_activity");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    final boolean _supportsDeferForeignKeys = android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP;
    try {
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = FALSE");
      }
      super.beginTransaction();
      if (_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA defer_foreign_keys = TRUE");
      }
      _db.execSQL("DELETE FROM `learning_levels`");
      _db.execSQL("DELETE FROM `courses`");
      _db.execSQL("DELETE FROM `units`");
      _db.execSQL("DELETE FROM `lessons`");
      _db.execSQL("DELETE FROM `exercises`");
      _db.execSQL("DELETE FROM `exercise_options`");
      _db.execSQL("DELETE FROM `vocabulary`");
      _db.execSQL("DELETE FROM `example_sentences`");
      _db.execSQL("DELETE FROM `user_profile`");
      _db.execSQL("DELETE FROM `learning_sessions`");
      _db.execSQL("DELETE FROM `answer_attempts`");
      _db.execSQL("DELETE FROM `lesson_progress`");
      _db.execSQL("DELETE FROM `vocabulary_reviews`");
      _db.execSQL("DELETE FROM `achievements`");
      _db.execSQL("DELETE FROM `daily_goal`");
      _db.execSQL("DELETE FROM `study_reminder`");
      _db.execSQL("DELETE FROM `personal_notes`");
      _db.execSQL("DELETE FROM `bookmarks`");
      _db.execSQL("DELETE FROM `daily_activity`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = TRUE");
      }
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  @NonNull
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(ContentDao.class, ContentDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(ProgressDao.class, ProgressDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(UserDao.class, UserDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  @NonNull
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  @NonNull
  public List<Migration> getAutoMigrations(
      @NonNull final Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecs) {
    final List<Migration> _autoMigrations = new ArrayList<Migration>();
    return _autoMigrations;
  }

  @Override
  public ContentDao contentDao() {
    if (_contentDao != null) {
      return _contentDao;
    } else {
      synchronized(this) {
        if(_contentDao == null) {
          _contentDao = new ContentDao_Impl(this);
        }
        return _contentDao;
      }
    }
  }

  @Override
  public ProgressDao progressDao() {
    if (_progressDao != null) {
      return _progressDao;
    } else {
      synchronized(this) {
        if(_progressDao == null) {
          _progressDao = new ProgressDao_Impl(this);
        }
        return _progressDao;
      }
    }
  }

  @Override
  public UserDao userDao() {
    if (_userDao != null) {
      return _userDao;
    } else {
      synchronized(this) {
        if(_userDao == null) {
          _userDao = new UserDao_Impl(this);
        }
        return _userDao;
      }
    }
  }
}
