function submitForm() {
    // 폼 요소 가져오기
    var form = document.getElementById('upload-form');

    // 폼 유효성 검사 실행
    if (form.checkValidity()) {
        // 유효성 검사 통과 시 폼 제출
        form.submit();
    } else {
        // 유효성 검사 실패 시 사용자에게 알림을 표시하거나 원하는 작업 수행
        alert('제목과 내용을 모두 입력하세요.');
    }
}