import { Movie } from "./models";

export class WatchedRequest {

    constructor(
        public email: string,
        public movie: Movie,
        public reviewRating: number,
        public text: string
    ) { }

}