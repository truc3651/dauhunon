var stompClient = null;

function setConnected(connected) {
    $("#content").html("");
}

function connect() {
    var socket = new SockJS('/fpt-entertainment');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);

//		like video
		stompClient.subscribe('/topic/like', function (video) {
            updateTotalLike(JSON.parse(video.body))
        });

//		dislike video
		stompClient.subscribe('/topic/dislike', function (video) {
            updateTotalDislike(JSON.parse(video.body));
        });
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
		stompClient = null;
    }else{
		console.log(">> disconnected aldready")
	}
    setConnected(false);
    console.log("Disconnected");
}

function actionLikeOnVideo(){
	stompClient.send("/app/likeAction", {}, JSON.stringify({
		'idVideo': $('#id').val(),
		'username': $('#loggedInUsername').text()
	}));
}

function updateTotalLike(video){
	if(video != null){
		$("#totalLike").text(formatNumberOfLikeDislike(video.numberOfLike))
		$("#totalDislike").text(formatNumberOfLikeDislike(video.numberOfDislike))

		if(video.username == $('#loggedInUsername').text()){
			var classOfBtnLike = $("#btnLikeVideo").attr("class")
			var classOfBtnDislike = $("#btnDisikeVideo").attr("class")

			if(classOfBtnLike == "btn btn-outline-secondary")
				$("#btnLikeVideo").attr("class", "btn btn-secondary")
			else
				$("#btnLikeVideo").attr("class", "btn btn-outline-secondary")

			if(classOfBtnDislike == "btn btn-secondary")
				$("#btnDisikeVideo").attr("class", "btn btn-outline-secondary")
		}
	}
}

function actionDislikeOnVideo(){
	stompClient.send("/app/dislikeAction", {}, JSON.stringify({
		'idVideo': $('#id').val(),
		'username': $('#loggedInUsername').text()
	}));
}

function updateTotalDislike(video){
	if(video != null){
		$("#totalLike").text(formatNumberOfLikeDislike(video.numberOfLike))
		$("#totalDislike").text(formatNumberOfLikeDislike(video.numberOfDislike))

		if(video.username == $('#loggedInUsername').text()){
			var classOfBtnLike = $("#btnLikeVideo").attr("class")
			var classOfBtnDislike = $("#btnDisikeVideo").attr("class")

			if(classOfBtnLike == "btn btn-secondary")
				$("#btnLikeVideo").attr("class", "btn btn-outline-secondary")

			if(classOfBtnDislike == "btn btn-outline-secondary")
				$("#btnDisikeVideo").attr("class", "btn btn-secondary")
			else
				$("#btnDisikeVideo").attr("class", "btn btn-outline-secondary")
		}
	}
}

$(function () {
	stompClient = null;
    $("form").on('submit', function (e) {
        e.preventDefault();
    });

//	connect ngay sau khi truy cập
	connect();

//    $( "#connect" ).click(function() { connect(); });
//    $( "#disconnect" ).click(function() { disconnect(); });

    $( "#send" ).click(function() { sendComment(); });

	$( "#btnLikeVideo" ).click(function() { actionLikeOnVideo(); });
	$( "#btnDisikeVideo" ).click(function() { actionDislikeOnVideo(); });

//	mục đích của trang video-detail
//	bật connect đến STOMP socket, ngay khi mới truy cập
//	bạn k cần phải disconnect bằng tay, vì khi reload trang, chrome đã disconnect
});




