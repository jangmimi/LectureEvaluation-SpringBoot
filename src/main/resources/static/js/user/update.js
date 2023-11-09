// 정보수정 submit 전 체크
function updateCheck() {
    var userId = $('#userId').val().trim();
    var userPw = $('#userPw').val().trim();
    var errorMessage = '';

    if(userId === '') {
        errorMessage = '아이디를 입력해주세요.';
    } else if(userPw === '') {
        errorMessage = '비밀번호를 입력해주세요.';
    }
    if (errorMessage !== '') {
        alert(errorMessage);
        return false;
    }

    var confirmed = confirm('수정하시겠습니까?');
    return confirmed;
}

$(document).ready(function() {
    $("#updateButton").click(function(e) {
        e.preventDefault(); // 기본 제출 동작 방지

        var userNumber = $("#userNumber").val();
        var userId = $("#userId").val().trim();
        var userPw = $("#userPw").val().trim();

        if (userId === '') {
            alert('아이디를 입력해주세요.');
        } else if (userPw === '') {
            alert('비밀번호를 입력해주세요.');
        } else {
            var confirmed = confirm('수정하시겠습니까?');
            if (confirmed) {
                $.ajax({
                    url: "/update/" + userNumber,
                    type: "POST",
                    data: {
                        userNumber: userNumber,
                        userId: userId,
                        userPw: userPw
                    },
                    success: function(data) {
                        if (data) {
                            // 수정 완료 후 처리
                            alert("수정이 완료되었습니다.");
                            window.location.href = "/";
                        } else {
                            alert("수정에 실패하였습니다.");
                        }
                    },
                    error: function() {
                        alert("서버 통신 에러가 발생했습니다.");
                    }
                });
            }
        }
    });
});