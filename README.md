[![Build Status](https://travis-ci.com/sitatec/RealEstateManager.svg?branch=master)](https://travis-ci.com/sitatec/RealEstateManager) [![Maintainability](https://api.codeclimate.com/v1/badges/7ebf01f657a570d9786f/maintainability)](https://codeclimate.com/github/sitatec/RealEstateManager/maintainability)
# RealEstateManager
A Real Estate Manager that stores the data in an SQLite database using Room.

![Berete Real estate manager android app](https://github.com/sitatec/RealEstateManager/blob/master/assets/real_estate_manager_banner.png)
![Berete RealEstateManager mobile app](https://github.com/sitatec/RealEstateManager/blob/master/assets/REM_banner_small_screens.png)


A demo video is available [here](#demo)
# Used Architecture Components
  - Room Database
  - Hilt
  - Data Binding
  - View Model
  - Live Data
  
# Architecture
  - MVVM  
  - SOLID principles applied
  - Adapter design pattern used to adapt room's entities and DAOs without depending on them
  
# Entity-relationship Diagram
![Entities Relationship Real Estate Manager](https://github.com/sitatec/RealEstateManager/blob/master/assets/EntityRelationship.png)

# Build
To be able to build the project you need google maps and google static maps API key(s), and store them in your local.properties file like this:
```
GOOGLE_MAP_API_KEY=YOUR-API-KEY
GOOGLE_MAP_STATIC_API_KEY=YOUR-API-KEY
```
If you don't want to store your keys in the local.properties, you will have to edit the [build.gradle](https://github.com/sitatec/RealEstateManager/blob/1e2c41261c4c2ac23369f2feed70bfa5cbe7afcc/app/build.gradle#L20) inside the app module.

# Demo
Click on the image below, you will be redirected to the video page.

[![Berete RealEstateManager demo video](https://github.com/sitatec/RealEstateManager/blob/master/assets/screenshot.png)](https://drive.google.com/file/d/1LSv3KUzzywHuQjkeqI5HgW6QNaCOGtUZ/preview)
