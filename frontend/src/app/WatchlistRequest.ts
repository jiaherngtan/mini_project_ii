import { Movie } from "./models";

export class WatchlistRequest {

    constructor(
        public email: string,
        public movie: Movie
    ) { }

}