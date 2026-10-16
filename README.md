# LinguaQuest 🎯
### Learn English Smarter, One Quest at a Time.

<p align="center">
  <strong>An interactive Android English-learning application designed to make learning engaging, structured, and accessible to everyone.</strong>
</p>

<p align="center">
  <img src="https://img.shields.io/badge/Platform-Android-3DDC84?style=for-the-badge&logo=android&logoColor=white" alt="Android"/>
  <img src="https://img.shields.io/badge/Language-Kotlin-7F52FF?style=for-the-badge&logo=kotlin&logoColor=white" alt="Kotlin"/>
  <img src="https://img.shields.io/badge/UI-Jetpack%20Compose-4285F4?style=for-the-badge&logo=jetpackcompose&logoColor=white" alt="Jetpack Compose"/>
  <img src="https://img.shields.io/badge/Status-In%20Development-orange?style=for-the-badge" alt="In Development"/>
</p>

---

## 📖 About the Project

**LinguaQuest** is an Android application concept focused on helping Indonesian learners develop practical English skills through interactive lessons, structured learning paths, and personalized practice.

Inspired by the engagement of gamified language-learning platforms and the structured educational approach of established English-learning resources, LinguaQuest aims to combine effective learning techniques with a modern mobile experience.

The project emphasizes more than memorizing vocabulary. It aims to help learners understand grammar, improve listening and reading comprehension, practice writing and pronunciation, and build confidence in everyday English communication.

> **Our mission:** Make effective English learning accessible, engaging, and sustainable through consistent daily practice.

## 🎯 The Problem

Learning English independently can be challenging because learners often struggle with:

- Finding a clear learning path from beginner to intermediate.
- Maintaining a consistent daily learning habit.
- Remembering vocabulary and grammar over time.
- Identifying weaknesses and deciding what to study next.
- Finding opportunities to practice different language skills in one place.

## 💡 The Solution

LinguaQuest is designed to address these challenges through:

- **Structured Learning:** Progressive lessons organized by level and topic.
- **Gamified Experience:** Learning goals, XP, achievements, and progress visualization.
- **Active Recall:** Exercises that require learners to retrieve information rather than simply reread it.
- **Spaced Repetition:** Scheduled reviews to reinforce long-term retention.
- **Personalized Practice:** Learning recommendations based on previous performance.
- **Offline-First Learning:** A design goal that prioritizes access to core learning materials without requiring a constant internet connection.

## ✨ Key Features

The following features represent the project direction. Features marked as planned are not necessarily available in the current build.

| Feature | Description | Status |
|---|---|---|
| Structured Curriculum | Progressive English lessons from beginner to intermediate | Planned / In development |
| Interactive Exercises | Multiple-choice, vocabulary matching, sentence building, and grammar practice | Planned / In development |
| Gamification | XP, streaks, achievements, and daily goals | Planned |
| Spaced Repetition | Scheduled vocabulary and lesson reviews | Planned |
| Adaptive Learning | Practice recommendations based on learning performance | Planned |
| Listening Practice | Audio lessons, dialogues, and comprehension exercises | Planned |
| Speaking Practice | Pronunciation exercises and speech interaction | Planned |
| Progress Dashboard | Learning statistics and skill-level tracking | Planned |
| Offline Learning | Access to downloaded or locally stored learning content | Planned |
| Daily Reminders | Configurable study reminders | Planned |

*Update this table as features are implemented and verified.*

## 🧠 Learning Methodology

LinguaQuest is designed around established learning principles:

- **Active Recall:** Strengthen memory by retrieving information from memory.
- **Spaced Repetition:** Review vocabulary and concepts at increasing intervals.
- **Retrieval Practice:** Use frequent low-stakes quizzes to reinforce learning.
- **Progressive Difficulty:** Introduce more complex material as learners improve.
- **Immediate Feedback:** Explain mistakes and provide corrective examples.
- **Consistent Practice:** Encourage manageable daily learning sessions.

The objective is to help users build lasting language skills, rather than optimize for points or lesson completion alone.

## 🛠️ Technology Stack

The intended technology stack is designed for maintainability, performance, and modern Android development.

| Technology | Purpose |
|---|---|
| Kotlin | Primary Android programming language |
| Jetpack Compose | Declarative UI development |
| Material 3 | Modern Android design system |
| MVVM | Presentation architecture |
| Room | Local relational database |
| DataStore | Persistent user preferences |
| Navigation Compose | Screen navigation |
| WorkManager | Background work and reminders |
| Android Text-to-Speech | Spoken learning content |
| JUnit | Unit testing |

*This table describes the intended stack. Confirm each dependency against the actual repository before describing it as implemented.*

## 🏗️ Architecture

The project aims to follow a modular architecture with clear separation of responsibilities.

```text
LinguaQuest
├── Presentation
│   ├── Screens
│   ├── Components
│   ├── ViewModels
│   └── Navigation
├── Domain
│   ├── Models
│   ├── UseCases
│   └── LearningRules
└── Data
    ├── LocalDatabase
    ├── Repositories
    ├── ContentSources
    └── Preferences
```

This structure is a proposed logical architecture, not a verified representation of the current source tree.

The separation is intended to make the application easier to test, extend, and maintain as new learning features are introduced.

## 📱 Screenshots & Demo

Visual demonstrations will be added as the application develops.

| Home | Lesson | Progress |
|---|---|---|
| Add screenshot | Add screenshot | Add screenshot |

Replace these placeholders with genuine screenshots of the application. For a stronger portfolio presentation, include the onboarding flow, an interactive exercise, the learning dashboard, and a completed lesson.

## 🚀 Getting Started

### Prerequisites

Before running the project, ensure that your development environment has the required Android development tools.

- Android Studio
- A compatible JDK
- Android SDK
- Git

The required JDK and SDK versions depend on the project's Gradle configuration.

### Installation

Clone the repository:

```bash
git clone https://github.com/YOUR_USERNAME/LinguaQuest.git
cd LinguaQuest
```

Open the project in Android Studio and allow Gradle synchronization to finish.

To build a debug APK on Linux, if the Gradle wrapper is configured:

```bash
chmod +x gradlew
./gradlew assembleDebug
```

To run available unit tests:

```bash
./gradlew test
```

To install the debug build on a connected Android device with ADB configured:

```bash
./gradlew installDebug
```

The debug APK is typically generated at:

```text
app/build/outputs/apk/debug/app-debug.apk
```

**Note:** These commands assume the repository contains a working Android Gradle project and the relevant build tasks. Build success has not been verified here.

## 🗺️ Roadmap

### Phase 1 — Learning Foundation
- [ ] Establish the Android project structure.
- [ ] Implement the main navigation and design system.
- [ ] Create the initial curriculum and lesson content.
- [ ] Build the interactive lesson engine.
- [ ] Persist learning progress locally.

### Phase 2 — Personalized Learning
- [ ] Add vocabulary review and spaced repetition.
- [ ] Implement XP and daily learning goals.
- [ ] Add placement tests and lesson assessments.
- [ ] Build a learning progress dashboard.
- [ ] Recommend lessons based on previous mistakes.

### Phase 3 — Language Skills
- [ ] Add listening exercises and audio playback.
- [ ] Integrate Android Text-to-Speech.
- [ ] Develop speaking and pronunciation practice.
- [ ] Add reading comprehension and writing exercises.
- [ ] Introduce contextual conversations.

### Phase 4 — Reliability & Accessibility
- [ ] Improve offline functionality.
- [ ] Add dark mode and accessibility improvements.
- [ ] Expand automated test coverage.
- [ ] Optimize startup time and memory usage.
- [ ] Prepare a release build and installation guide.

## 🧪 Testing & Quality

Quality is an important part of the development process.

Planned testing areas include:

- Unit tests for lesson scoring and XP calculation.
- Tests for spaced-repetition scheduling.
- Database persistence and migration tests.
- UI tests for navigation and lesson completion.
- Validation of offline behavior.
- Error handling and accessibility checks.

Test coverage and build status will be documented as they are verified.

## 🔒 Privacy & Accessibility

LinguaQuest aims to minimize unnecessary data collection and support independent learning.

Design priorities include:

- Local storage for learning progress where practical.
- No mandatory account for core learning functionality.
- Explicit permission requests for microphone-based features.
- Clear disclosure when a feature requires network access.
- Accessible text, contrast, and interactive controls.

These are project goals and should be verified against the actual implementation before release.

## 🌱 Contributing

Contributions, suggestions, bug reports, and learning-content improvements are welcome.

1. Fork the repository.
2. Create a feature branch.
3. Make your changes.
4. Run relevant tests.
5. Submit a pull request describing the change.

Please keep contributions focused, maintainable, and consistent with the project's architecture.

## 📌 Project Status

LinguaQuest is an evolving personal software project focused on Android development and educational technology.

The roadmap is intentionally incremental: establish a reliable learning foundation first, then expand into adaptive learning, audio-based practice, and advanced progress tracking.

## 👨‍💻 Author

**YOUR NAME**

Aspiring Android Developer | Informatics Engineering

- GitHub: [@YOUR_USERNAME](https://github.com/AnggaSaputra78)
- Portfolio: [YOUR_PORTFOLIO_URL](https://portofolio-wfh.netlify.app/)

Interested in Android development, software architecture, educational technology, and building applications that solve practical problems.

## 📄 License

A license has not yet been selected.

Before making the repository public, choose an appropriate license and add a `LICENSE` file. Do not claim that the project is open-source under a particular license until that file is present.

---

<p align="center">
  <strong>LinguaQuest — Every lesson is a step forward.</strong>
  <br/>
  Built with Kotlin and a commitment to continuous learning.
</p>
