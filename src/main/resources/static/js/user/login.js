// 로그인 유효성 검사 및 ajax 처리
function loginCheck() {
    let userId = $('#userId').val().trim();
    let userPw = $('#userPw').val().trim();
    let saveId = $('#saveId').val().trim();
    let errorMessage = '';

    if(userId === '') {
        errorMessage = '아이디를 입력해주세요.';
    } else if(userPw === '') {
        errorMessage = '비밀번호를 입력해주세요.';
    }
    if (errorMessage !== '') {
        alert(errorMessage);
        return false;
    }

    let data = {
        userId: userId,
        userPw: userPw,
        saveId: saveId
    };

    $.ajax({
        url: '/loginAction',
        type: 'POST',
        data: data, // 데이터 객체를 전달
        success: function(response) {
            if(response == 1) { // 로그인 성공 시
                alert(`${userId}님, 반갑습니다!`);
                window.location.href = '/';

            } else if(response == 0) {  // 로그인 실패 시
                alert('아이디 또는 비밀번호를 다시 확인해주세요.');
            }
        },
        error:function() {
            alert("서버 통신 에러가 발생했습니다.");
        }
    });
};