const host = 'http://' + window.location.host;

$(document).ready(function () {
    const auth = getToken();

    if (auth !== undefined && auth !== '') {
        $.ajaxPrefilter(function (options, originalOptions, jqXHR) {
            jqXHR.setRequestHeader('Authorization', auth);

        });
    } else {
        window.location.href = host + '/users/login-page';
        return;
    }



    $.ajax({
        type: 'GET',
        url: `/users/user-info`,
        contentType: 'application/json',
    })
        .done(function (res, status, xhr) {
            const username = res.username;
            const isAdmin = !!res.admin;

            if (!username) {
                window.location.href = '/users/login-page';
                return;
            }

            $('#username').text(username);
        })
        .fail(function (jqXHR, textStatus) {
            console.log("유저정보 error");
            logout();
        });
})


function logout() {
    // 토큰 삭제
    Cookies.remove('Authorization', {path: '/'});
    window.location.href = host + '/users/login-page';
}

function getToken() {
    let auth = Cookies.get('Authorization');

    if(auth === undefined) {
        return '';
    }

    // kakao 로그인 사용한 경우 Bearer 추가
    if(auth.indexOf('Bearer') === -1 && auth !== ''){
        auth = 'Bearer ' + auth;
    }

    return auth;
}