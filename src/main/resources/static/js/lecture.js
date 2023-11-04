$(document).ready(function () {
    var lectureIdInput = $('#lectureId');
    var subjectInput = $('#lectureSubject');
    var infoInput = $('#lectureInfo');
    var urlInput = $('#url');
    var modalTriggerButtons = $('[data-bs-target="#registerModal"]');

    modalTriggerButtons.each(function (index, button) {
        $(button).on('click', function () {
            var lectureId = $(button).data('lectureid');
            var subject = $(button).data('lecturesubject');
            var info = $(button).data('lectureinfo');
            var url = $(button).data('lectureurl')

            lectureIdInput.val(lectureId);
            subjectInput.val(subject);
            infoInput.val(info);
            urlInput.val(url);
        });
    });
});

// 강의 평가 등록
function registerCheck() {
    let lectureId = $('#lectureId').val();
    let lectureSubject = $('#lectureSubject').val();
    let lectureInfo = $('#lectureInfo').val();
    let lectureURL = $('#url').val();
    let evaluationTitle = $('#evaluationTitle').val();
    let evaluationContent = $('#evaluationContent').val();

    let data = {
        lectureId: lectureId,
        lectureSubject: lectureSubject,
        lectureInfo: lectureInfo,
        lectureURL: lectureURL,
        evaluationTitle: evaluationTitle,
        evaluationContent: evaluationContent
    };

    $.ajax({
        url: '/evaluationRegisterAction',
        type: 'POST',
        data: data,
        success: function(response) {
            if(response) {
                alert("강의평가가 등록되었습니다.");
                $('#evaluationTitle').val("");
                $('#evaluationContent').val("");
            } else {
                alert('강의평가 등록에 실패하였습니다.');
            }
        },
        error:function() {
            alert("서버 통신 에러가 발생했습니다.");
        }
    });
}