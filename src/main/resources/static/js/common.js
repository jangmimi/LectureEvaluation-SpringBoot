function likeyCheck() {
    var confirmed = confirm('추천하시겠습니까?');
    return confirmed;
}

function deleteCheck() {
    var confirmed = confirm('삭제하시겠습니까?');
    return confirmed;
}

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