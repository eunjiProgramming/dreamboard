function searchBook() {
    var bookname = $("#bookname").val();
    if (bookname == "") {
        alert("책 제목을 입력하세요");
        return false;
    }
    $.ajax({
        url: "https://dapi.kakao.com/v3/search/book?target=title",
        headers: { "Authorization": "KakaoAK 616c762caa94dab798c1028d9f1ddb74" },
        type: "get",
        data: { "query": bookname },
        dataType: "json",
        success: bookPrint,
        error: function() { alert("error"); }
    });
    $(document).ajaxStart(function() { $(".loading-progress").show(); });
    $(document).ajaxStop(function() { $(".loading-progress").hide(); });
}

function autocompleteBook() {
    $("#bookname").autocomplete({
        source: function() {
            var bookname = $("#bookname").val();
            $.ajax({
                url: "https://dapi.kakao.com/v3/search/book?target=title",
                headers: { "Authorization": "KakaoAK 616c762caa94dab798c1028d9f1ddb74" },
                type: "get",
                data: { "query": bookname },
                dataType: "json",
                success: bookPrint,
                error: function() { alert("error"); }
            });
        },
        minLength: 1
    });
}

function mapViewBtn() {
    var address = $("#address").val();
    if (address == '') {
        alert("주소를 입력하세요");
        return false;
    }
    $.ajax({
        url: "https://dapi.kakao.com/v2/local/search/address.json",
        headers: { "Authorization": "KakaoAK 616c762caa94dab798c1028d9f1ddb74" },
        type: "get",
        data: { "query": address },
        dataType: "json",
        success: mapView,
        error: function() { alert("error"); }
    });
}

function bookPrint(data) {
    var bList = "<table class='table table-hover'>";
    bList += "<thead>";
    bList += "<tr>";
    bList += "<th>책이미지</th>";
    bList += "<th>책가격</th>";
    bList += "</tr>";
    bList += "</thead>";
    bList += "<tbody>";
    $.each(data.documents, function(index, obj) {
        var image = obj.thumbnail;
        var price = obj.price;
        var url = obj.url;
        bList += "<tr>";
        bList += "<td><a href='" + url + "'><img src='" + image + "' width='50px' height='60px'/></a></td>";
        bList += "<td>" + price + "</td>";
        bList += "</tr>";
    });
    bList += "</tbody>";
    bList += "</table>";
    $("#bookList").html(bList);
}

function mapView(data) {
    var x = data.documents[0].x;
    var y = data.documents[0].y;
    var mapContainer = document.getElementById('map');
    var mapOption = {
        center: new kakao.maps.LatLng(y, x),
        level: 2
    };
    var map = new kakao.maps.Map(mapContainer, mapOption);
    var markerPosition = new kakao.maps.LatLng(y, x);
    var marker = new kakao.maps.Marker({
        position: markerPosition
    });
    marker.setMap(map);
    var iwContent = '<div style="padding:5px;">' + memName + '</div>';
    var iwRemoveable = true;

    var infowindow = new kakao.maps.InfoWindow({
        content: iwContent,
        removable: iwRemoveable
    });
    kakao.maps.event.addListener(marker, 'click', function() {
        infowindow.open(map, marker);
    });
}
