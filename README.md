<div align="center"> 

# Aadhyapan Android Client

**The Android client of Aadhyapan, now open source.**

Originally built by [Aadhyapan](https://aadhyapan.com), a Nepali software development company, and sold commercially for around $100. The full source code is now free for everyone to use, learn from, and improve.

[![License: GPL v3](https://img.shields.io/badge/License-GPLv3-blue.svg)](LICENSE)
[![Platform](https://img.shields.io/badge/platform-Android-3DDC84.svg)](https://developer.android.com)
[![PRs Welcome](https://img.shields.io/badge/PRs-welcome-brightgreen.svg)](../../pulls)

[**Download APK**](https://aadhyapan.github.io/) · [Report a Bug](../../issues) · [Request a Feature](../../issues)

</div>

---

## About

Aadhyapan Android Client is a complete Android app that was previously sold as a paid product. It is now released as open source so that developers, students, and small teams in Nepal and beyond can build on real-world code instead of starting from scratch.

> **Why open source?** Good software shouldn't be locked behind a price tag. Releasing this code lets the community learn from a real commercial app, adapt it to their own needs, and keep improving it together.

## Download

Want to try the app without building it? Download the latest APK from **[aadhyapan.github.io](https://aadhyapan.github.io/)**.

> You may need to allow installs from unknown sources on your device.

## Features

1. OTP Login system.
2. Different login page for Admin (school), teacher and student.
3. Notifications can be created by admins.
4. Good UI.
5. Widely used by schools.

## Tech Stack

- **Language:** Kotlin / Java
- **Build system:** Gradle
- **UI:** XML layouts / Jetpack Compose
- **Architecture:** MVVM / MVP / other
- **Networking:** Retrofit / Volley / OkHttp / other
- **Min SDK:** 21

## Getting Started

### Prerequisites

- Android Studio (https://developer.android.com/studio) (latest stable recommended)
- JDK 11 / 17
- An Android device or emulator

### Build from source

1. **Clone the repository**
   ```bash
   git clone https://github.com/Aadhyapan/Aadhyapan-Android-Client-Source-Code.git
   cd Aadhyapan-Android-Client-Source-Code
   ```

2. **Open in Android Studio**
   Choose `File > Open`, select the project folder, and let Gradle sync finish.

3. **Configure the backend**
   Point the app to your own API by updating the base URL in `[path to the file where it is defined]`.

4. **Run the app**
   Select a device or emulator and press **Run** (`Shift + F10`).

### Build a release APK

```bash
./gradlew assembleRelease
```

You will need to provide your own signing keystore. Never commit keystores or secrets to the repository.

## Project Structure

```
.
├── app/                  # Main Android application module
├── gradle/wrapper/       # Gradle wrapper
├── build.gradle          # Project-level build config
├── settings.gradle
└── LICENSE
```

## Backend

This repository contains the **client** only. [Describe whether a backend is included, which API the app expects, or how people can mock or replace it.]

## Contributing

Contributions of all sizes are welcome: bug fixes, new features, documentation, translations, and cleanup.

1. Fork the repository
2. Create a branch: `git checkout -b feature/your-feature`
3. Commit your changes: `git commit -m "Add your feature"`
4. Push the branch: `git push origin feature/your-feature`
5. Open a Pull Request

For large changes, please open an issue first so we can discuss the idea.

## License

This project is licensed under the **GNU General Public License v3.0**. See the [LICENSE](LICENSE) file for details.

## Acknowledgements

- Originally developed by **Aadhyapan**, a software development company based in Nepal.
- Thanks to everyone who contributes, reports issues, and shares this project.

## Contact

Maintained by [**Aadhyapan**](https://github.com/Aadhyapan) · [aadhyapan.com](https://aadhyapan.com)

Questions or ideas? [Open an issue](../../issues).

---

<div align="center">

If this project helps you, consider giving it a ⭐

</div>
