// 로그인 submit 전 체크
function loginCheck() {
    var userId = $('#userId').val().trim();
    var userPw = $('#userPw').val().trim();;
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
    return true;
}

// 회원가입 submit 전 체크
function joinCheck() {
    var userId = $('#userId').val().trim();
    var userPw = $('#userPw').val().trim();;
    var userEmail = $('#userEmail').val().trim();;
    var errorMessage = '';

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
    return true;
}

// 정보수정 submit 전 체크
function updateCheck() {
    var userId = $('#userId').val().trim();
    var userPw = $('#userPw').val().trim();;
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

function likeyCheck() {
    var confirmed = confirm('추천하시겠습니까?');
    return confirmed;
}

function deleteCheck() {
    var confirmed = confirm('삭제하시겠습니까?');
    return confirmed;
}
