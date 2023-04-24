export interface Movie {
    id: string
    imdbId: string
    imdbUrl: string
    title: string
    tagline: string
    overview: string
    runtime: number
    releaseDate: string
    releaseYear: string
    posterUrl: string
    backdropUrl: string
    rating: number
    ratingCount: string
    genres: string[]
    countries: string[]
    languages: string[]
    added: string
    reviewRating: number
    text: string
    reviewed: string
}

export interface Genre {
    id: number
    name: string
}

export interface People {
    id: string
    role: string
    name: string
    pictureUrl: string
    movies: Movie[]
    biography: string
    birthday: string
    place: string
}

export interface Crew {
    id: string
    role: string
    name: string
    pictureUrl: string
}

export interface User {
    imageUrl: string
    id: string
    username: string
    email: string
    password: string
    created: string
}

export interface LoginUser {
    loginEmail: string
    loginPassword: string
}