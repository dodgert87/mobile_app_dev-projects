# mobile_app_dev-projects

# Movie Recommendation App

## Overview
The Movie Recommendation App leverages the TMDB API to offer tailored movie suggestions across various categories like Top, New, Trending, and more. This project showcases the use of advanced Android development techniques, including ViewModel for robust data management and LiveData for responsive UI updates. It aims to provide a learning platform for developers interested in modern Android architecture and API integration.

## Features
- **Custom Data Management**: Implements ViewModel for efficient state and data handling.
- **Dynamic Region Selection**: Features a custom dropdown menu for selecting regions, enhancing user experience with localized content.
- **Detailed Movie Information**: Displays extensive details about movies, including genres, ratings, and overviews.
- **Star Rating Component**: A custom UI component that users can interact with to rate movies.
- **Multi-language Support**: The app supports both English and French, catering to a wider audience.
- **Automated Region Detection**: Utilizes device permissions to set the region automatically, optimizing content relevance.
- **Navigational Flow**: Employs the Android Navigation Component for seamless navigation across different screens.

## Technologies Used
- Kotlin
- Android Architecture Components (ViewModel, LiveData)
- TMDB API for real-time movie data
- Android Navigation Component for managing screen navigation
- Retrofit for network calls

## Setup and Installation
1. **Clone the Repository**:
   ```bash
   git clone https://github.com/dodgert87/mobile_app_dev-projects.git

2. **Open the Project in Android Studio:**
- Launch Android Studio and select 'Open an Existing Project', then navigate to the directory where you cloned the repo.


3. **Build and Run the App**
- Ensure your Android SDK is set up correctly in Android Studio.
- Connect an Android device or use the Android emulator.
- Run the app through Android Studio to see it in action.

 4. **Permissions**
- **Internet**: Necessary to fetch movie data from the TMDB API.
- **Read Phone State**: Used to automatically set the region based on the user's device settings.

5. **Localization**
The application is available in English and French. You can switch the language through your device's language settings.

## About the Developer
Developed by Abdenour Abdelziz, this project serves as an academic endeavor to explore and implement advanced features in Android application development.

