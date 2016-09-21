$(document).ready(function($) {

    var movies = $.ajax({ url: "/mymovies", async: false });
    console.log(movies);

});
