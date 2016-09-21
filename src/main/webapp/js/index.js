var movies;

$(document).ready(function() {
    $.getJSON('/mymovies', function(data) {
        movies = data;

        for (var i = 0; i < movies.movieList.length; i++) {

            var span = document.createElement("span");
            var br = document.createElement("br");
            var img = document.createElement("img");
            span.innerHTML = movies.movieList[i].title;
            img.src = movies.movieList[i].img;

            document.body.appendChild(span);
            document.body.appendChild(img);
            document.body.appendChild(br);

        }

    });

});
