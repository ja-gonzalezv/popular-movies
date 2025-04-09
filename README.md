# Popular Movies

Welcome to the **Popular Movies** app! 🎬✨

This app allows you to explore a list of popular movies and get detailed information about each one. It's a simple yet powerful app built with Kotlin, Jetpack Compose, and follows MVI + Clean architecture principles.

## Screens

The app contains **two screens**:

### 1. **Movies List Screen**  
- Retrieves a list of popular movies from the internet 🌐
- Displays movie poster, title, overview, and release date 🗓️
- You can search locally to filter through the movies 🔍

### 2. **Movie Details Screen**  
- When you tap on a movie from the list, you can see its detailed information 🎥
- Displays the movie's backdrop image, title, rating, and overview 🌟

## Tech Stack

This app is built entirely using **Kotlin** and follows best practices with **Jetpack Compose** for UI. It's designed with **MVI + Clean architecture** to keep things organized and maintainable.

### Key Libraries Used:
- **ViewModel**: For managing UI-related data in a lifecycle-conscious way
- **Coil**: To load images efficiently, including posters and backdrops
- **Hilt**: For Dependency Injection to keep everything clean and modular
- **Paging**: For handling large sets of data with smooth pagination
- **Retrofit**: For making network requests and fetching movie data
- **Room**: For local database storage and caching of movie data

## Required Authorization Token

To fetch data from the API, you'll need to add your **personal authorization token**. 

### Add your token:
1. Obtain an API key from [The Movie Database (TMDb)](https://www.themoviedb.org/settings/api/).
2. In the app's code, locate the following line:
   
   ```kotlin
   const val AUTH_TOKEN = "{YOUR_AUTH_TOKEN}"
   
3. Replace "{YOUR_AUTH_TOKEN}" with your personal token.

This is required to authenticate requests and get movie data from the endpoint.

## Supported Android Versions

This app targets Android API level 35 (Android 15) but is compatible with devices running Android API level 24 (Nougat) and above.
