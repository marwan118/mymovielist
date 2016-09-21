var movies;

$(document).ready(function() {
    $.getJSON('/mymovies', function(data) {
        movies = data;

        for (var i = 0; i < movies.movieList.length; i++) {

            var span = document.createElement("span");
            var br = document.createElement("br");
            var img = document.createElement("img");
            span.innerHTML = movies.movieList[i].name;
            var imgurl = movies.movieList[i].imgUrl;

            img.src = "../../../db/" + imgurl.substring(imgurl.lastIndexOf("/") + 1);
            console.log(img.src);
            document.body.appendChild(span);
            document.body.appendChild(img);
            document.body.appendChild(br);

        }

    });

});
