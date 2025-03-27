function logout() {
    if(!confirm("로그아웃 하시겠습니까?")) return;
    //csrf 토큰이 있어야 로그아웃 처리할 수 있음. -> 추후 만들기
    fetch('/logout', {
        method: 'POST',
        credentials: 'same-origin' // CSRF 보호 활성화 시 필요
    })
        .then(response => {
            if (response.ok) {
                return response;
            } else {
                throw new Error('Logout failed');
            }
        })
        .then(data => {
            window.location.href = '/loginForm'; // 로그인 페이지로 리다이렉트
        })
        .catch(error => {
            console.error('Error:', error);
        });
}