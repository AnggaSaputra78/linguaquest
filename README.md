# 🎯 LinguaQuest — Learn English, One Quest at a Time

<p align="center">
  <strong>An Android English-learning app concept designed to make language learning structured, engaging, and accessible.</strong>
</p>

<p align="center">
  <a href="https://github.com/AnggaSaputra78/LinguaQuest">
    <img src="https://img.shields.io/badge/Project-LinguaQuest-6558F5?style=for-the-badge" alt="LinguaQuest project"/>
  </a>
  <img src="https://img.shields.io/badge/Platform-Android-3DDC84?style=for-the-badge&logo=android&logoColor=white" alt="Android"/>
  <img src="https://img.shields.io/badge/Language-Kotlin-7F52FF?style=for-the-badge&logo=kotlin&logoColor=white" alt="Kotlin"/>
  <img src="https://img.shields.io/badge/UI-Jetpack%20Compose-4285F4?style=for-the-badge&logo=jetpackcompose&logoColor=white" alt="Jetpack Compose"/>
  <img src="https://img.shields.io/badge/Status-In%20Development-orange?style=for-the-badge" alt="In development"/>
</p>

<p align="center">
  <a href="#-why-linguaquest">Why LinguaQuest</a> ·
  <a href="#-features">Features</a> ·
  <a href="#-getting-started">Get Started</a> ·
  <a href="#-roadmap">Roadmap</a> ·
  <a href="#-contributing">Contribute</a>
</p>

---

## 🌟 Welcome to LinguaQuest

**LinguaQuest** is a project exploring how mobile technology and gamification can help Indonesian learners develop practical English skills.

The goal is to combine a structured learning journey with interactive exercises, vocabulary review, progress tracking, and a modern Android experience.

Inspired by the engagement of gamified learning apps and the breadth of established English-learning resources, LinguaQuest aims to help learners move beyond memorizing words toward understanding and using English in everyday situations.

> 💡 **The vision:** Make consistent English practice easier, more engaging, and more accessible—one lesson at a time.

**Project status:** LinguaQuest is under development. The feature list and roadmap below describe the intended direction, not a claim that every feature is already available.

## 🚀 Quick Navigation

| Looking for... | Go to |
|---|---|
| Project motivation | [Why LinguaQuest](#-why-linguaquest) |
| Planned functionality | [Features](#-features) |
| Technologies | [Tech Stack](#-tech-stack) |
| Installation | [Getting Started](#-getting-started) |
| Upcoming work | [Roadmap](#-roadmap) |
| Collaboration | [Contributing](#-contributing) |
| Developer profile | [About the Developer](#-about-the-developer) |

## 🎯 Why LinguaQuest?

Learning English independently can be difficult. Learners may struggle to choose suitable materials, maintain a routine, remember vocabulary, and understand what to study next.

LinguaQuest explores a more organized learning experience through:

- **Structured learning paths** — progress from foundational English toward intermediate skills.
- **Active recall** — practise retrieving information instead of only rereading notes.
- **Spaced repetition** — revisit vocabulary and concepts over time.
- **Gamification** — make learning progress visible through goals, XP, and achievements.
- **Progress-oriented learning** — use mistakes and previous results to guide future practice.
- **Mobile-first access** — make short learning sessions convenient on Android.

## ✨ Features

The following table reflects the project direction. Update each status only after the corresponding functionality has been implemented and tested.

| Feature | Intended experience | Status |
|---|---|---|
| 📚 Structured Curriculum | Lessons arranged by level and topic | Planned |
| ✍️ Interactive Exercises | Grammar, vocabulary, and sentence practice | Planned |
| 🧠 Spaced Repetition | Review vocabulary at appropriate intervals | Planned |
| 🏆 Gamification | XP, streaks, achievements, and daily goals | Planned |
| 📊 Learning Dashboard | Track activity and learning progress | Planned |
| 🎧 Listening Practice | Audio-based lessons and comprehension | Planned |
| 🗣️ Speaking Practice | Explore pronunciation and speech interaction | Planned |
| 🔄 Adaptive Practice | Recommend exercises based on performance | Planned |
| 📱 Offline Learning | Access supported learning content locally | Planned |
| 🔔 Study Reminders | Encourage consistent learning habits | Planned |

<details>
<summary><strong>🧠 Learning principles behind the project</strong></summary>

**Active recall:** Retrieve information from memory to strengthen learning.

**Spaced repetition:** Revisit material at increasing intervals rather than cramming everything into one session.

**Progressive difficulty:** Introduce more challenging exercises as the learner advances.

**Immediate feedback:** Explain incorrect answers and help learners understand the right approach.

**Consistent practice:** Encourage manageable learning sessions that fit into daily life.

These are the learning principles guiding the design; their implementation will evolve with the application.

</details>

## 🛠️ Tech Stack

The intended technical direction uses modern Android development tools.

| Technology | Purpose |
|---|---|
| Kotlin | Android application development |
| Jetpack Compose | Declarative user interface |
| Material 3 | UI components and visual consistency |
| MVVM | Separation of UI and application logic |
| Room | Local structured data storage |
| DataStore | Persistent preferences |
| Navigation Compose | Screen navigation |
| WorkManager | Background tasks and reminders |
| Android Text-to-Speech | Spoken learning content |
| JUnit | Unit testing |

*This is the intended stack. Verify the actual project dependencies before treating every item as implemented.*

## 🏗️ Architecture Direction

LinguaQuest aims to separate presentation, learning logic, and data access so the project can grow without making the codebase unnecessarily difficult to maintain.

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

This is a proposed logical architecture. Refer to the actual source tree for the current implementation.

## 📸 Screenshots & Demo

Screenshots will be added as real application screens become available.

| Home Screen | Lesson Screen | Progress Screen |
|---|---|---|
| *Coming soon* | *Coming soon* | *Coming soon* |

> **Portfolio tip:** Replace these placeholders with genuine screenshots or a short screen recording. A working demo makes it easier for recruiters and contributors to evaluate the project.

## 🚀 Getting Started

Want to explore or contribute to LinguaQuest? Follow these steps.

### Prerequisites

- [Android Studio](https://developer.android.com/studio)
- Git
- Android SDK
- A JDK version compatible with the project's Gradle configuration

### 1. Clone the repository

```bash
git clone https://github.com/AnggaSaputra78/LinguaQuest.git
cd LinguaQuest
```

### 2. Open the project

Open the cloned directory in Android Studio, allow Gradle synchronization to complete, and review any build configuration requirements.

### 3. Build the debug APK

On Linux or Ubuntu, if the Gradle wrapper and build configuration are ready:

```bash
chmod +x gradlew
./gradlew assembleDebug
```

### 4. Run available unit tests

```bash
./gradlew test
```

### 5. Install on a connected Android device

With USB debugging and ADB configured:

```bash
./gradlew installDebug
```

If the build succeeds, the debug APK is typically located at:

```text
app/build/outputs/apk/debug/app-debug.apk
```

**Important:** These are standard Android Gradle commands. Successful compilation has not been verified here. The repository must contain a complete, working Gradle project before these commands can be expected to succeed.

## 🗺️ Roadmap

Help shape the direction of LinguaQuest. The checklist below represents planned milestones; check items off as they are completed and verified.

<details open>
<summary><strong>Phase 1 — Learning Foundation</strong></summary>

- [ ] Establish a reliable Android project structure
- [ ] Build the main navigation and UI foundation
- [ ] Create the initial English curriculum
- [ ] Implement the first interactive lesson
- [ ] Save learning progress locally

</details>

<details>
<summary><strong>Phase 2 — Personalised Learning</strong></summary>

- [ ] Implement vocabulary review
- [ ] Add spaced-repetition scheduling
- [ ] Introduce XP and daily learning goals
- [ ] Build the progress dashboard
- [ ] Recommend practice based on previous mistakes

</details>

<details>
<summary><strong>Phase 3 — Language Skills</strong></summary>

- [ ] Add listening exercises
- [ ] Integrate text-to-speech
- [ ] Explore pronunciation practice
- [ ] Add reading comprehension and writing exercises
- [ ] Introduce contextual conversation practice

</details>

<details>
<summary><strong>Phase 4 — Quality & Accessibility</strong></summary>

- [ ] Improve offline functionality
- [ ] Expand automated test coverage
- [ ] Improve accessibility and visual consistency
- [ ] Optimize performance and memory usage
- [ ] Prepare a release build and installation guide

</details>

## 🧪 Testing & Quality

Planned testing areas include:

- Unit tests for lesson scoring and XP calculations.
- Tests for vocabulary review scheduling.
- Database persistence and migration tests.
- UI tests for navigation and lesson completion.
- Offline behavior and error handling.
- Accessibility and usability checks.

Testing results will be documented as the corresponding tests become available.

## 🔒 Privacy & Accessibility

The project aims to prioritize responsible data handling and accessible learning.

Design goals include:

- Store learning progress locally where practical.
- Avoid requiring an account for core functionality where feasible.
- Request microphone permissions only when needed.
- Clearly communicate features that require network access.
- Support readable text, adequate contrast, and accessible controls.

These are design goals, not guarantees about the current build.

## 🤝 Contributing

Contributions are welcome—from Android developers and UI/UX designers to English learners, educators, and documentation writers.

You can help by:

- Reporting reproducible bugs.
- Suggesting useful learning features.
- Improving the user interface.
- Reviewing learning content for clarity.
- Adding tests or improving documentation.

### Contribution workflow

1. Fork this repository.
2. Create a feature branch.
3. Make a focused change.
4. Run the relevant tests, if available.
5. Open a pull request describing the change and how it was tested.

```bash
git checkout -b feature/your-feature-name
git add .
git commit -m "feat: describe your change"
git push origin feature/your-feature-name
```

Then open a pull request from your fork on GitHub.

<details>
<summary><strong>💡 Suggested contribution ideas</strong></summary>

- Design a beginner-friendly English lesson.
- Improve screen layouts and accessibility.
- Add unit tests for learning logic.
- Improve offline data handling.
- Review grammar explanations and example sentences.
- Improve the installation instructions.

If you are unsure where to begin, open an issue to discuss your idea before making a large change.

</details>

## 📌 Project Principles

LinguaQuest is being developed with the following principles in mind:

- **Learning first:** Features should support actual language learning, not just engagement metrics.
- **Maintainable code:** Prefer clear responsibilities and testable logic.
- **Honest documentation:** Distinguish working features from planned functionality.
- **Incremental development:** Build a reliable foundation before expanding the feature set.
- **Open collaboration:** Make it easier for other developers to understand and contribute.

## 👨‍💻 About the Developer

**Angga Saputra**  
Informatics Engineering | Aspiring Android & Full-Stack Developer

I am interested in Android development, software architecture, educational technology, and building software that addresses practical problems.

LinguaQuest is part of my journey toward developing maintainable applications, improving my engineering practices, and learning through hands-on projects.

- **GitHub:** [@AnggaSaputra78](https://github.com/AnggaSaputra78)
- **Portfolio:** [portofolio-wfh.netlify.app](https://portofolio-wfh.netlify.app/)

If you are a recruiter, developer, educator, or fellow learner, you are welcome to explore the project and share constructive feedback.

## 📄 License

No license has been selected in this README.

Before presenting LinguaQuest as an open-source project for reuse, add a suitable `LICENSE` file. The license determines how others may use, modify, and distribute the code.

---

<p align="center">
  <strong>🌱 LinguaQuest — Every lesson is a step forward.</strong>
  <br/>
  Built with Kotlin and a commitment to continuous learning.
  <br/><br/>
  <a href="https://github.com/AnggaSaputra78/LinguaQuest">Explore the Repository</a> ·
  <a href="https://github.com/AnggaSaputra78">More Projects</a> ·
  <a href="https://portofolio-wfh.netlify.app/">Developer Portfolio</a>
</p>
