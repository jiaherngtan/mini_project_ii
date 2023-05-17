# Mini Project - The Movie Database

## Disclaimer
This product uses the TMDB API but is not endorsed or certified by TMDB.<br>
[Link to TMDB API](https://developers.themoviedb.org/3)

## Project Description
This is a full-stack web application that allows users to:
* Look for movies by popularity, ratung, genre, and search
* Look for movie information and similar movies
* Add, update, and delete movies to/from their personal watchlist
* Add, update, and delete movie reviews

It is built using Angular for the front-end and Spring Boot for the back-end.<br>
The authentication, authorization, and validation features are done by Firebase Authentication.

## Project Structure
The project is organized into two main directories: frontend and backend. The frontend directory contains the Angular front-end code, while the backend directory contains the Spring Boot back-end code.

## Features
* User registration, login and deletion with Firebase Authentication
* Notifications with Firebase Cloud Messaging
* Watchlist creation, modification and deletion
* Review creation, modification and deletion
* Watchlist display and sorting

## Technologies Used
#### Client side
* Angular 15.2.6
* Angular Fire 7.5.0
* Angular Google Map 15.2.8
* Bootstrap 5.2.3

#### Server Side
* Spring Boot

#### Persistence
* Redis
* MySQL
* DigitalOcean

#### Deployment
* Github actions deploy to Railway
* Docker

## Screenshots
#### Landing page:
* User can view movies by genre, and search for movies and casts through the top navigation bar.
* User can view latest movies, popular movies, and top rated movies at one glance.
<img width="1426" alt="image" src="https://github.com/jiaherngtan/mini_project_ii/assets/87298876/108dd70a-d4bc-4e64-b0ac-a7576ecd1206">

#### Sign-up / Login page:
* User can sign up a new account or login to their account.
* User account is needed to access to some of the features of the application such as Watchlist creation, modification, and deletion; Review creation, modification, and deletion; Watchlist display, and sorting.
<img width="1425" alt="image" src="https://github.com/jiaherngtan/mini_project_ii/assets/87298876/48d10f93-d709-4528-8269-4bf8fbe22977">

#### Movie details page:
* User can view movie details such as production countries, production year, overview, running time, genres, language, rating, casts, and similar movies.
<img width="1430" alt="image" src="https://github.com/jiaherngtan/mini_project_ii/assets/87298876/ce645216-4001-44f9-bbe0-db1aea61047a">

* User can add the movie to "Want to Watch" or "Watched" via the Add to Watchlist button. Review (rating and comment) can be given if the movie is added to "Watched".
<img width="1428" alt="image" src="https://github.com/jiaherngtan/mini_project_ii/assets/87298876/f7395892-8210-448f-bb3e-9d771f9082e7">

#### Watchlist page:
* User can view the list of movies in their watchlist at a glance with date added being displayed. When movie in "Want to Watch" is expanded, user can view some of the basic information about the movie, and they are able to add the movie to "Watched", or delete it from the list.
<img width="1427" alt="image" src="https://github.com/jiaherngtan/mini_project_ii/assets/87298876/bbc3760a-c30f-4001-a634-f19af282751d">

* When movie in "Watched" is expanded, user can view their rating and comment on the movie, and they are able to delete the movie from the list as well.
<img width="1425" alt="image" src="https://github.com/jiaherngtan/mini_project_ii/assets/87298876/de14eff6-be4d-45fe-ac39-2267f4b51fb1">

#### User details page:
* User can view their basic information about themselve (username, email, date joined).
* User can view the number of movies they have in their watchlist at a glance via analytics.
* User can delete their profile.

<img width="1428" alt="image" src="https://github.com/jiaherngtan/mini_project_ii/assets/87298876/b8598936-187b-4643-b1e1-ae5d490698d5">

* User can view the list of movies in ther "Watched" section, and can sort them by alphabetically, time added, and rating.
<img width="1426" alt="image" src="https://github.com/jiaherngtan/mini_project_ii/assets/87298876/5e07fee4-7867-46d2-bce7-689b368fd0a2">

## Setup and Installation
...
