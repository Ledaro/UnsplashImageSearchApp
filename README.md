# Unsplash Image Search App
Unsplash Image Search App is an android application built using Kotlin which allows users to search and view photos from Unsplash API. It also allows for checking 
authors profiles and liking/unliking images. The project uses Hilt for dependency injection and follows MVVM architecture.

<p float="left">
<img src="https://user-images.githubusercontent.com/58782531/212917193-18c799e2-5ddf-492d-a661-8e7d64be917b.png" width=24% height=24%>
<img src="https://user-images.githubusercontent.com/58782531/212918514-ad723750-5e65-41ce-a234-11ff4cce427e.png" width=24% height=24%>
<img src="https://user-images.githubusercontent.com/58782531/212918775-98385e03-0b16-4d60-9aec-c7cb4411de9f.png" width=24% height=24%>
<img src="https://user-images.githubusercontent.com/58782531/212918712-e9351bf1-f9bb-4dcb-90e4-a6bb0608c3e1.png" width=24% height=24%>
</p>

This project is an example app and is not intended for production use.

## Features
- Search for images
- View image details including user information, description
- Like and unlike image
- View photo author profile inculiding their own photos and likes
- Navigate between multiple fragments


## Technologies used
- Kotlin
- Retrofit
- Dagger Hilt
- Paging 3
- Glide
- Navigation Component
- View Binding

## Installation
1. Clone the repository
``` 
git clone https://github.com/Ledaro/UnsplashImageSearchApp.git
```
2. Open the project in Android Studio
3. Build and run the app on an emulator or device

## Contributions
This project is open to contributions. Feel free to submit pull requests with bug fixes or new features.

#### What can be improved
- Optimalize API calls due to Unsplash 50 call limits
- Improve Animations
- Add updating to Favourites adapter when user unlikes photo
- Save UI state of adapters without calling and creating new ones
- Improve Bottom Menu Navigation
- Add scrolling inside Profile Fragment (similar to Instagram)
