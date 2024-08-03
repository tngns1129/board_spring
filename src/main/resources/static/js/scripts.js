document.getElementById('loginForm').addEventListener('submit', function(event) {
    event.preventDefault();

    const userName = document.getElementById('userName').value;
    const userPassword = document.getElementById('userPassword').value;

    fetch('/sign/in', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        body: `userName=${encodeURIComponent(userName)}&userPassword=${encodeURIComponent(userPassword)}`
    })
        .then(response => response.json())
        .then(data => {
            if (data.code === 0) {
                document.getElementById('resultMessage').innerText = '로그인 성공';
                // 로그인 성공 시 추가 작업 (예: 리다이렉션)
            } else {
                document.getElementById('resultMessage').innerText = '로그인 실패: ' + data.message;
            }
        })
        .catch(error => {
            document.getElementById('resultMessage').innerText = '오류 발생: ' + error.message;
        });
});
