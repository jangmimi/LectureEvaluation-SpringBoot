// 로그인 유효성 검사 및 ajax 처리
function loginCheck() {
    let title = $('#title').val().trim();
    let content = $('#content').val().trim();
    let errorMessage = '';

    if(title === '') {
        errorMessage = '제목을 입력해주세요.';
    } else if(content === '') {
        errorMessage = '내용을 입력해주세요.';
    }
    if (errorMessage !== '') {
        alert(errorMessage);
        return false;
    }

    let data = {
        title: title,
        content: content
    };

    $.ajax({
        url: '/writeAction',
        type: 'POST',
        data: data, // 데이터 객체를 전달
        success: function(response) {
            if(response == 1) { // 로그인 성공 시
                alert('게시글 등록 완료!');
               window.location.href = '/freeBoard';

            } else if(response == 0) {  // 로그인 실패 시
                alert('게시글 등록에 실패했습니다.');
            }
        },
        error:function() {
            alert("서버 통신 에러가 발생했습니다.");
        }
    });
};