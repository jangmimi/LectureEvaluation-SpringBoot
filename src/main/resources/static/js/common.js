// 로그인 유효성 검사 및 ajax 처리
function loginCheck() {
    let userId = $('#userId').val().trim();
    let userPw = $('#userPw').val().trim();
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
        userPw: userPw
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

function likeyCheck() {
    var confirmed = confirm('추천하시겠습니까?');
    return confirmed;
}

function deleteCheck() {
    var confirmed = confirm('삭제하시겠습니까?');
    return confirmed;
}

// 강의 평가 수정
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
                evaluationTitle = $('#evaluationTitle').val("");
                evaluationContent = $('#evaluationContent').val("");
            } else {
                alert('강의평가 등록에 실패하였습니다.');
            }
        },
        error:function() {
            alert("서버 통신 에러가 발생했습니다.");
        }
    });
}

// 강의 평가 수정 모달창 데이터
$(document).ready(function () {
    var lectureIdInput = $('#lectureId');
    var lectureNameInput = $('#lectureName');
    var professorNameInput = $('#professorName');
    var evaluationTitleInput = $('#evaluationTitle');
    var evaluationContentInput = $('#evaluationContent');
    var modalTriggerButtons = $('[data-bs-target="#registerModal"]');

    // 모달창에 데이터 가져오기 (HTML에서 data-속성은 대소문자를 구분하며, 소문자로 가져와야함)
    modalTriggerButtons.each(function (index, button) {
        $(button).on('click', function () {
            var lectureId = $(button).data('lectureid');
            var subject = $(button).data('subject');
            var professor = $(button).data('professor');
            var title = $(button).data('title');
            var content = $(button).data('content');

            lectureIdInput.val(lectureId);
            lectureNameInput.val(subject);
            professorNameInput.val(professor);
            evaluationTitleInput.val(title);
            evaluationContentInput.val(content);
        });
    });
});
