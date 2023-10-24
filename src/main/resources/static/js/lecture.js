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