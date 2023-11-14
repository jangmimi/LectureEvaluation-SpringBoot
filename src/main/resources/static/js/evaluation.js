// 강의 평가 수정 모달창 데이터 전달
$(document).ready(function () {
    var updateIdInput = $('#id');
    var lectureSubjectInput = $('#lectureSubject');
    var lectureInfoInput = $('#lectureInfo');
    var evaluationTitleInput = $('#evaluationTitle');
    var evaluationContentInput = $('#evaluationContent');
    var updateModalTriggerButtons = $('[data-bs-target="#updateModal"]');

    // 모달창에 데이터 가져오기 (HTML에서 data-속성은 대소문자를 구분하며, 소문자로 가져와야함)
    updateModalTriggerButtons.each(function (index, button) {
        $(button).on('click', function () {
            var id = $(button).data('id');
            var subject = $(button).data('lecturesubject');
            var professor = $(button).data('lectureinfo');
            var title = $(button).data('title');
            var content = $(button).data('content');

            updateIdInput.val(id);
            lectureSubjectInput.val(subject);
            lectureInfoInput.val(professor);
            evaluationTitleInput.val(title);
            evaluationContentInput.val(content);
        });
    });

    // 강의 평가 신고 모달창 데이터 전달
    var reportIdInput = $('#targetid');
    var reportModalTriggerButtons = $('[data-bs-target="#reportModal"]');

    // 모달창에 데이터 가져오기
    reportModalTriggerButtons.each(function (index, button) {
        $(button).on('click', function () {
            var id = $(button).data('targetid');
            reportIdInput.val(id);
        });
    });
});

// 강의 평가 수정
function updateCheck() {
    let id = $('#id').val();
    let evaluationTitle = $('#evaluationTitle').val();
    let evaluationContent = $('#evaluationContent').val();

    let data = {
        id: id,
        evaluationTitle: evaluationTitle,
        evaluationContent: evaluationContent
    };

    $.ajax({
        url: '/updateAction',
        type: 'POST',
        data: data,
        success: function(response) {
            if(response) {
                alert("강의평가가 수정되었습니다.");
                evaluationTitle = $('#evaluationTitle').val("");
                evaluationContent = $('#evaluationContent').val("");
                location.reload();
            } else {
                alert('강의평가 수정에 실패하였습니다.');
            }
        },
        error:function() {
            alert("서버 통신 에러가 발생했습니다.");
        }
    });
}

// 추천 클릭 시 실행 함수
function likey() {
    var evaluationID = $("#evaluationID").val();

    $.ajax({
        url: '/likeyAction/' + evaluationID,
        type: 'POST',
        success: function(response) {
            if (response === 1) {
                alert('추천되었습니다.');
                location.reload();
            } else if (response === 0) {
                alert('추천이 취소되었습니다.');
            } else {
                alert('추천에 실패하였습니다.');
            }
        },
        error: function() {
            alert('서버 통신 에러가 발생했습니다.');
        }
    });
}

// 강의 평가 조회 모달창 데이터 전달
//$(document).ready(function () {
//    var idInput = $('#id');
//    var lectureSubjectInput = $('#lectureSubject');
//    var lectureInfoInput = $('#lectureInfo');
//    var evaluationTitleInput = $('#evaluationTitle');
//    var evaluationContentInput = $('#evaluationContent');
//    var modalTriggerButtons = $('[data-bs-target="#detailModal"]');
//
//    // 모달창에 데이터 가져오기 (HTML에서 data-속성은 대소문자를 구분하며, 소문자로 가져와야함)
//    modalTriggerButtons.each(function (index, button) {
//        $(button).on('click', function () {
//            var id = $(button).data('id');
//            var subject = $(button).data('lecturesubject');
//            var professor = $(button).data('lectureinfo');
//            var title = $(button).data('title');
//            var content = $(button).data('content');
//
//            idInput.val(id);
//            lectureSubjectInput.val(subject);
//            lectureInfoInput.val(professor);
//            evaluationTitleInput.val(title);
//            evaluationContentInput.val(content);
//        });
//    });
//});

function likeyCheck() {
    var confirmed = confirm('추천하시겠습니까?');
    return confirmed;
}

function deleteCheck() {
    var confirmed = confirm('삭제하시겠습니까?');
    return confirmed;
}
