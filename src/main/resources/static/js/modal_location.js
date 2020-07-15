$("#location").click(function () {
    $("#locationModal").modal('show');
});
$('#locationModal').on('shown.bs.modal', function () {
    $('#location-search').focus();
    $("#location-search").val("");
    $("#location-list").empty();

})

$("#location-search").keyup(function () {
    setTimeout(function () {
        let location = $("#location-search").val();
        $.ajax({
            url: `/rest/kladr/region?name=${location}`,
            type: 'get',
            dataType: 'json',
            success: function (data) {
                $("#location-list").empty();
                data.forEach(region => {
                    $("#location-list").prepend(`<a id="region-${region.id}" href="#" 
                    class="list-group-item list-group-item-action">
                    ${region.name} ${region.shortType} </a>`)
                })
            }
        })
        $.ajax({
            url: `/rest/kladr/city?name=${location}`,
            type: 'get',
            dataType: 'json',
            success: function (data) {
                data.forEach(city => {
                    $("#location-list").append(`<a id="city-${city.id}" href="#" 
                    class="location list-group-item list-group-item-action">
                    ${city.shortType} ${city.name} (${city.region.name} ${city.region.shortType})</a>`)
                })
            }
        })
    }, 200)

})

$("#location-list").click(function (event) {
    let id = event.target.id;
    let name = event.target.text;
    name = name.replace(/\s+/g, ' ').trim();
    $("#location-search").attr('data', id);
    $("#location-search").val(name);
    $("#location-list").empty();

})

$("#location-close").click(function () {
    let dataId = $("#location-search").attr("data");
    let locationName = $("#location-search").val();
    locationName = locationName.replace(/\([^()]*\)/g, '');
    $("#locationModal").modal('hide');
    $("#location-name").text(locationName);
    $("#location-name").attr("data", dataId);
    if (typeof dataId !== typeof undefined && dataId !== false && dataId.includes("region")) {
        let regionId = dataId.slice(7);
        $.ajax({
            url: `/rest/posting/searchByRegion/${regionId}`,
            type: 'get',
            dataType: 'json',
            success: function (data) {
                drawPosting(data);
            }
        });
    } else if (typeof dataId !== typeof undefined && dataId !== false && dataId.includes("city")) {
        let cityId = dataId.slice(5);
        $.ajax({
            url: `/rest/posting/searchByCity/${cityId}`,
            type: 'get',
            dataType: 'json',
            success: function (data) {
                drawPosting(data);
            }
        });
    }
})

function drawPosting(data) {
    $(".container_cus").empty();
    data.forEach(posting => {
        $(".container_cus").append(
                '    <div class="card">\n' +
                `    <div id="${posting.id}" class="card-header">` +
                '       <img src="' + posting.images[0].imagePath + '" class="card-img-top" alt="...">' +
                '   </div>' +
                '       <div class="card-body">\n' +
                '                <h5 class="card-title">' + posting.title + '</h5>\n' +
                '                <p class="card-text">' + posting.price + '</p>\n' +
                '                <a href="posting/' + posting.id + '" class="btn btn-primary" ' +
                'th:text="#{main_page.go_to_ad}">Перейти к объявлению</a>\n' +
                '            </div>\n' +
                '        </div>'
        )
    })
    showFavoriteIcon();
}
