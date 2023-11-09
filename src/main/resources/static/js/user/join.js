// 회원가입 submit 전 체크
function joinCheck() {
    let userId = $('#userId').val().trim();
    let userPw = $('#userPw').val().trim();
    let userEmail = $('#userEmail').val().trim()
    let errorMessage = '';

    if(userId === '') {
        errorMessage = '아이디를 입력해주세요.';
    } else if(userPw === '') {
        errorMessage = '비밀번호를 입력해주세요.';
    } else if(userEmail === '') {
          errorMessage = '이메일을 입력해주세요.';
    }
    if (errorMessage !== '') {
        alert(errorMessage);
        return false;
    }

    $.ajax({
        url: '/checkId',
        type: 'POST',
        data: { userId: userId }, // 데이터 객체를 전달
        success: function(response) {
            if(response == 1) { // 회원가입 성공 시

                $.ajax({
                    url: '/joinAction',
                    type: 'POST',
                    data: { userId: userId,
                            userPw: userPw,
                            userEmail: userEmail }, // 데이터 객체를 전달
                    success: function(response) {
                        if(response == 1) {
                            alert(`${userId}님, 회원가입을 축하드립니다!`);
                            window.location.href = '/';
                        } else if(response == 0) {
                            alert('회원가입에 실패하였습니다.');
                        }
                    },
                    error:function() {
                        alert("서버 통신 에러가 발생했습니다.");
                    }
                });

            } else if(response == 0) {  // 로그인 실패 시
                alert('중복된 아이디입니다.');
            }
        },
        error:function() {
            alert("서버 통신 에러가 발생했습니다.");
        }
    });
}