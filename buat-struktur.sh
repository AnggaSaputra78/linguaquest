
#!/usr/bin/env bash
set -e

PROJECT="$HOME/../../mnt/data/Projects/linguaquest"

# Gunakan lokasi proyek yang telah ditentukan
PROJECT="/mnt/data/Projects/linguaquest"

cd "$PROJECT"

# Folder utama dan struktur package
mkdir -p \
  gradle/wrapper \
  app/schemas \
  app/src/main/java/com/linguaquest/data/local/entity \
  app/src/main/java/com/linguaquest/data/repository \
  app/src/main/java/com/linguaquest/di \
  app/src/main/java/com/linguaquest/ui/home \
  app/src/main/java/com/linguaquest/ui/navigation \
  app/src/main/java/com/linguaquest/ui/screens \
  app/src/main/java/com/linguaquest/ui/theme \
  app/src/main/res/values \
  app/src/test/java/com/linguaquest

# File konfigurasi Gradle
touch \
  settings.gradle.kts \
  build.gradle.kts \
  gradle.properties \
  gradle/wrapper/gradle-wrapper.properties \
  app/build.gradle.kts \
  app/proguard-rules.pro

# File utama aplikasi
touch \
  app/src/main/AndroidManifest.xml \
  app/src/main/java/com/linguaquest/LinguaQuestApplication.kt \
  app/src/main/java/com/linguaquest/MainActivity.kt

# Database lokal
touch \
  app/src/main/java/com/linguaquest/data/local/ContentDao.kt \
  app/src/main/java/com/linguaquest/data/local/LinguaQuestDatabase.kt \
  app/src/main/java/com/linguaquest/data/local/SeedData.kt \
  app/src/main/java/com/linguaquest/data/local/entity/ContentEntities.kt \
  app/src/main/java/com/linguaquest/data/local/entity/ProgressEntities.kt \
  app/src/main/java/com/linguaquest/data/repository/ContentRepository.kt

# Dependency injection
touch \
  app/src/main/java/com/linguaquest/di/AppContainer.kt

# Antarmuka pengguna dan navigasi
touch \
  app/src/main/java/com/linguaquest/ui/MainScreen.kt \
  app/src/main/java/com/linguaquest/ui/home/HomeViewModel.kt \
  app/src/main/java/com/linguaquest/ui/navigation/TopLevelDestination.kt \
  app/src/main/java/com/linguaquest/ui/screens/HomeScreen.kt \
  app/src/main/java/com/linguaquest/ui/screens/OtherScreens.kt

# Tema aplikasi
touch \
  app/src/main/java/com/linguaquest/ui/theme/Color.kt \
  app/src/main/java/com/linguaquest/ui/theme/Theme.kt \
  app/src/main/java/com/linguaquest/ui/theme/Type.kt

# Resource Android
touch \
  app/src/main/res/values/strings.xml \
  app/src/main/res/values/themes.xml

echo "Struktur proyek LinguaQuest berhasil dibuat."
echo
echo "Lokasi proyek: $PROJECT"
echo
find . -maxdepth 10 -type f | sort
