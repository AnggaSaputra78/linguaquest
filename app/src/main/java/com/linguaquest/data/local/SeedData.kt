package com.linguaquest.data.local

import androidx.sqlite.db.SupportSQLiteDatabase

object SeedData {

    fun seed(db: SupportSQLiteDatabase) {
        db.beginTransaction()
        try {
            seedLearningLevels(db)
            seedCourses(db)
            seedUnits(db)
            seedLessons(db)
            seedExercises(db)
            seedExerciseOptions(db)
            seedVocabulary(db)
            seedPlacement(db)
            seedAdditionalContent(db)

            db.setTransactionSuccessful()
        } finally {
            db.endTransaction()
        }
    }

    fun seedPlacement(db: SupportSQLiteDatabase) {
        seedPlacementCourse(db)
        seedPlacementUnit(db)
        seedPlacementLesson(db)
        seedPlacementExercises(db)
        seedPlacementExerciseOptions(db)
    }

    fun seedAdditionalContent(db: SupportSQLiteDatabase) {
        seedNumbersLesson(db)
        seedNumbersVocabulary(db)
        seedNumbersExercises(db)
        seedNumbersExerciseOptions(db)
    }

    private fun seedLearningLevels(db: SupportSQLiteDatabase) {
        db.execSQL(
            """
            INSERT OR IGNORE INTO learning_levels (id, name, description, display_order)
            VALUES
            ('PRE_A1', 'Pre-A1', 'Fondasi awal bahasa Inggris', 0),
            ('A1', 'A1', 'Beginner', 1),
            ('A2', 'A2', 'Elementary', 2),
            ('B1_INTRO', 'B1 Intro', 'Pengantar menuju level menengah', 3)
            """.trimIndent()
        )
    }

    private fun seedCourses(db: SupportSQLiteDatabase) {
        db.execSQL(
            """
            INSERT OR IGNORE INTO courses (id, title, description, display_order)
            VALUES
            ('english-foundations', 'English Foundations', 'Fase 0 untuk membangun fondasi bahasa Inggris', 0)
            """.trimIndent()
        )
    }

    private fun seedUnits(db: SupportSQLiteDatabase) {
        db.execSQL(
            """
            INSERT OR IGNORE INTO units (id, course_id, title, description, level_id, display_order)
            VALUES
            ('f0-u01', 'english-foundations', 'Greetings and To Be', 'Sapaan, perkenalan, dan penggunaan to be', 'PRE_A1', 0)
            """.trimIndent()
        )
    }

    private fun seedLessons(db: SupportSQLiteDatabase) {
        db.execSQL(
            """
            INSERT OR IGNORE INTO lessons (id, unit_id, title, description, estimated_minutes, display_order)
            VALUES
            ('f0-u01-l01', 'f0-u01', 'Hello and I am', 'Mengucapkan salam dan memperkenalkan diri', 5, 0)
            """.trimIndent()
        )
    }

    private fun seedExercises(db: SupportSQLiteDatabase) {
        db.execSQL(
            """
            INSERT OR IGNORE INTO exercises (
                id, lesson_id, type, skill, instruction, prompt, answer, explanation, difficulty, display_order
            )
            VALUES
            (
                'ex-f0-u01-l01-01',
                'f0-u01-l01',
                'MULTIPLE_CHOICE',
                'VOCABULARY',
                'Pilih terjemahan yang benar untuk kata: Halo',
                'Halo',
                'Hello',
                'Hello berarti halo.',
                1,
                0
            ),
            (
                'ex-f0-u01-l01-02',
                'f0-u01-l01',
                'FILL_BLANK',
                'GRAMMAR',
                'Lengkapi kalimat berikut dengan to be yang benar',
                'I ___ a student',
                'am',
                'Subjek I menggunakan am.',
                1,
                1
            ),
            (
                'ex-f0-u01-l01-03',
                'f0-u01-l01',
                'SENTENCE_BUILD',
                'WRITING',
                'Susun kata menjadi kalimat yang benar',
                'saya - am - Rina',
                'I am Rina',
                'Urutan kalimat bahasa Inggris adalah subject + to be + nama.',
                1,
                2
            )
            """.trimIndent()
        )
    }

    private fun seedExerciseOptions(db: SupportSQLiteDatabase) {
        db.execSQL(
            """
            INSERT OR IGNORE INTO exercise_options (id, exercise_id, text, is_correct, display_order)
            VALUES
            ('ex-f0-u01-l01-01-opt-1', 'ex-f0-u01-l01-01', 'Hello', 1, 0),
            ('ex-f0-u01-l01-01-opt-2', 'ex-f0-u01-l01-01', 'Goodbye', 0, 1),
            ('ex-f0-u01-l01-01-opt-3', 'ex-f0-u01-l01-01', 'Thank you', 0, 2),
            ('ex-f0-u01-l01-01-opt-4', 'ex-f0-u01-l01-01', 'Please', 0, 3)
            """.trimIndent()
        )
    }

    private fun seedVocabulary(db: SupportSQLiteDatabase) {
        db.execSQL(
            """
            INSERT OR IGNORE INTO vocabulary (
                id, lesson_id, english, indonesian, pronunciation, example_en, example_id, difficulty
            )
            VALUES
            (
                'v-hello',
                'f0-u01-l01',
                'hello',
                'halo',
                'he-LOU',
                'Hello, I am Rina.',
                'Halo, saya Rina.',
                1
            ),
            (
                'v-i',
                'f0-u01-l01',
                'I',
                'saya',
                'AI',
                'I am a student.',
                'Saya adalah seorang pelajar.',
                1
            )
            """.trimIndent()
        )
    }

    private fun seedPlacementCourse(db: SupportSQLiteDatabase) {
        db.execSQL(
            """
            INSERT OR IGNORE INTO courses (id, title, description, display_order)
            VALUES
            ('placement', 'Placement Test', 'Tes penempatan awal', -1)
            """.trimIndent()
        )
    }

    private fun seedPlacementUnit(db: SupportSQLiteDatabase) {
        db.execSQL(
            """
            INSERT OR IGNORE INTO units (id, course_id, title, description, level_id, display_order)
            VALUES
            ('placement-u01', 'placement', 'Placement Unit', 'Tes singkat untuk menentukan level awal', 'PRE_A1', 0)
            """.trimIndent()
        )
    }

    private fun seedPlacementLesson(db: SupportSQLiteDatabase) {
        db.execSQL(
            """
            INSERT OR IGNORE INTO lessons (id, unit_id, title, description, estimated_minutes, display_order)
            VALUES
            ('placement-l01', 'placement-u01', 'Placement Test', 'Tes singkat menentukan level awal', 3, 0)
            """.trimIndent()
        )
    }

    private fun seedPlacementExercises(db: SupportSQLiteDatabase) {
        db.execSQL(
            """
            INSERT OR IGNORE INTO exercises (
                id, lesson_id, type, skill, instruction, prompt, answer, explanation, difficulty, display_order
            )
            VALUES
            (
                'ex-placement-01',
                'placement-l01',
                'MULTIPLE_CHOICE',
                'VOCABULARY',
                'Pilih arti yang benar',
                'Hello',
                'Halo',
                'Hello berarti halo.',
                1,
                0
            ),
            (
                'ex-placement-02',
                'placement-l01',
                'MULTIPLE_CHOICE',
                'GRAMMAR',
                'Lengkapi kalimat dengan to be yang benar',
                'I ___ a student',
                'am',
                'Subjek I menggunakan am.',
                1,
                1
            ),
            (
                'ex-placement-03',
                'placement-l01',
                'MULTIPLE_CHOICE',
                'GRAMMAR',
                'Lengkapi kalimat dengan to be yang benar',
                'She ___ my friend',
                'is',
                'Subjek she menggunakan is.',
                1,
                2
            ),
            (
                'ex-placement-04',
                'placement-l01',
                'MULTIPLE_CHOICE',
                'VOCABULARY',
                'Pilih arti yang benar',
                'Good morning',
                'Selamat pagi',
                'Good morning berarti selamat pagi.',
                1,
                3
            ),
            (
                'ex-placement-05',
                'placement-l01',
                'MULTIPLE_CHOICE',
                'GRAMMAR',
                'Lengkapi kalimat dengan to be yang benar',
                'They ___ students',
                'are',
                'Subjek they menggunakan are.',
                1,
                4
            )
            """.trimIndent()
        )
    }

    private fun seedPlacementExerciseOptions(db: SupportSQLiteDatabase) {
        db.execSQL(
            """
            INSERT OR IGNORE INTO exercise_options (id, exercise_id, text, is_correct, display_order)
            VALUES
            ('ex-placement-01-opt-1', 'ex-placement-01', 'Halo', 1, 0),
            ('ex-placement-01-opt-2', 'ex-placement-01', 'Selamat tinggal', 0, 1),
            ('ex-placement-01-opt-3', 'ex-placement-01', 'Terima kasih', 0, 2),
            ('ex-placement-01-opt-4', 'ex-placement-01', 'Maaf', 0, 3),
            ('ex-placement-02-opt-1', 'ex-placement-02', 'am', 1, 0),
            ('ex-placement-02-opt-2', 'ex-placement-02', 'is', 0, 1),
            ('ex-placement-02-opt-3', 'ex-placement-02', 'are', 0, 2),
            ('ex-placement-02-opt-4', 'ex-placement-02', 'be', 0, 3),
            ('ex-placement-03-opt-1', 'ex-placement-03', 'is', 1, 0),
            ('ex-placement-03-opt-2', 'ex-placement-03', 'am', 0, 1),
            ('ex-placement-03-opt-3', 'ex-placement-03', 'are', 0, 2),
            ('ex-placement-03-opt-4', 'ex-placement-03', 'be', 0, 3),
            ('ex-placement-04-opt-1', 'ex-placement-04', 'Selamat pagi', 1, 0),
            ('ex-placement-04-opt-2', 'ex-placement-04', 'Selamat siang', 0, 1),
            ('ex-placement-04-opt-3', 'ex-placement-04', 'Selamat malam', 0, 2),
            ('ex-placement-04-opt-4', 'ex-placement-04', 'Sampai jumpa', 0, 3),
            ('ex-placement-05-opt-1', 'ex-placement-05', 'are', 1, 0),
            ('ex-placement-05-opt-2', 'ex-placement-05', 'is', 0, 1),
            ('ex-placement-05-opt-3', 'ex-placement-05', 'am', 0, 2),
            ('ex-placement-05-opt-4', 'ex-placement-05', 'be', 0, 3)
            """.trimIndent()
        )
    }

    private fun seedNumbersLesson(db: SupportSQLiteDatabase) {
        db.execSQL(
            """
            INSERT OR IGNORE INTO lessons (
                id, unit_id, title, description, estimated_minutes, display_order
            )
            VALUES
            (
                'f0-u01-l02',
                'f0-u01',
                'Numbers One to Five',
                'Angka satu sampai lima',
                5,
                1
            )
            """.trimIndent()
        )
    }

    private fun seedNumbersVocabulary(db: SupportSQLiteDatabase) {
        db.execSQL(
            """
            INSERT OR IGNORE INTO vocabulary (
                id, lesson_id, english, indonesian, pronunciation, example_en, example_id, difficulty
            )
            VALUES
            (
                'v-one',
                'f0-u01-l02',
                'one',
                'satu',
                'WAN',
                'I have one book.',
                'Saya punya satu buku.',
                1
            ),
            (
                'v-two',
                'f0-u01-l02',
                'two',
                'dua',
                'TU',
                'I have two pens.',
                'Saya punya dua pulpen.',
                1
            ),
            (
                'v-three',
                'f0-u01-l02',
                'three',
                'tiga',
                'TRI',
                'I have three apples.',
                'Saya punya tiga apel.',
                1
            ),
            (
                'v-four',
                'f0-u01-l02',
                'four',
                'empat',
                'FOR',
                'I have four chairs.',
                'Saya punya empat kursi.',
                1
            ),
            (
                'v-five',
                'f0-u01-l02',
                'five',
                'lima',
                'FAIV',
                'I have five fingers.',
                'Saya punya lima jari.',
                1
            )
            """.trimIndent()
        )
    }

    private fun seedNumbersExercises(db: SupportSQLiteDatabase) {
        db.execSQL(
            """
            INSERT OR IGNORE INTO exercises (
                id, lesson_id, type, skill, instruction, prompt, answer, explanation, difficulty, display_order
            )
            VALUES
            (
                'ex-f0-u01-l02-01',
                'f0-u01-l02',
                'MULTIPLE_CHOICE',
                'VOCABULARY',
                'Pilih arti yang benar',
                'three',
                'tiga',
                'Three berarti tiga.',
                1,
                0
            ),
            (
                'ex-f0-u01-l02-02',
                'f0-u01-l02',
                'MATCHING',
                'VOCABULARY',
                'Cocokkan angka dengan artinya',
                'Pasangkan kata bahasa Inggris dengan arti bahasa Indonesia',
                'MATCH',
                'Setiap angka dipasangkan dengan arti yang benar.',
                1,
                1
            ),
            (
                'ex-f0-u01-l02-03',
                'f0-u01-l02',
                'FILL_BLANK',
                'VOCABULARY',
                'Tulis angka dalam bahasa Inggris',
                '2 = ___',
                'two',
                'Two berarti dua.',
                1,
                2
            )
            """.trimIndent()
        )
    }

    private fun seedNumbersExerciseOptions(db: SupportSQLiteDatabase) {
        db.execSQL(
            """
            INSERT OR IGNORE INTO exercise_options (id, exercise_id, text, is_correct, display_order)
            VALUES
            ('ex-f0-u01-l02-01-opt-1', 'ex-f0-u01-l02-01', 'tiga', 1, 0),
            ('ex-f0-u01-l02-01-opt-2', 'ex-f0-u01-l02-01', 'satu', 0, 1),
            ('ex-f0-u01-l02-01-opt-3', 'ex-f0-u01-l02-01', 'dua', 0, 2),
            ('ex-f0-u01-l02-01-opt-4', 'ex-f0-u01-l02-01', 'empat', 0, 3),
            ('ex-f0-u01-l02-02-opt-1', 'ex-f0-u01-l02-02', 'one|satu', 1, 0),
            ('ex-f0-u01-l02-02-opt-2', 'ex-f0-u01-l02-02', 'two|dua', 1, 1),
            ('ex-f0-u01-l02-02-opt-3', 'ex-f0-u01-l02-02', 'three|tiga', 1, 2),
            ('ex-f0-u01-l02-02-opt-4', 'ex-f0-u01-l02-02', 'four|empat', 1, 3),
            ('ex-f0-u01-l02-02-opt-5', 'ex-f0-u01-l02-02', 'five|lima', 1, 4)
            """.trimIndent()
        )
    }
}