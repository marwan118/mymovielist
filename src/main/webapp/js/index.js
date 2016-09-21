$(document).ready(function() {

    $.getJSON('/mymovies', function(data) {
        console.log(data);
    });


});
