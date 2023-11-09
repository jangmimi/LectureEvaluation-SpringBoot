// 강의 평가 신고 모달창 데이터 전달
$(document).ready(function () {
    var idInput = $('#targetid');
    var modalTriggerButtons = $('[data-bs-target="#reportModal"]');

    // 모달창에 데이터 가져오기
    modalTriggerButtons.each(function (index, button) {
        $(button).on('click', function () {
            var id = $(button).data('targetid');
            idInput.val(id);
            console.log(id);
        });
    });
});