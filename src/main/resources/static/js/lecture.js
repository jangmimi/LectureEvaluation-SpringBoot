$(document).ready(function () {
    var lectureIdInput = $('#lectureId');
    var urlInput = $('#url');
    var lectureNameInput = $('#lectureName');
    var professorNameInput = $('#professorName');
    var modalTriggerButtons = $('[data-bs-target="#registerModal"]');

    modalTriggerButtons.each(function (index, button) {
        $(button).on('click', function () {
            var lectureId = $(button).data('lectureid');
            var url = $(button).data('url');
            var subject = $(button).data('subject');
            var professor = $(button).data('professor');
            lectureIdInput.val(lectureId);
            urlInput.val(url);
            lectureNameInput.val(subject);
            professorNameInput.val(professor);
        });
    });
});

// 강의 평가 등록
function registerCheck() {
    let lectureId = $('#lectureId').val();
    let url = $('#url').val();
    let lectureName = $('#lectureName').val();
    let professorName = $('#professorName').val();
    let evaluationTitle = $('#evaluationTitle').val();
    let evaluationContent = $('#evaluationContent').val();

    let data = {
        lectureId: lectureId,
        lectureURL: url,
        lectureName: lectureName,
        lectureProfessor: professorName,
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
            } else {
                alert('강의평가 등록에 실패하였습니다.');
            }
        },
        error:function() {
            alert("서버 통신 에러가 발생했습니다.");
        }
    });
}