$(document).ready(function () {
    $('#submit').click(function (e) {
        e.preventDefault();
        let boardTitle = $('#input[name="boardTitle"]').val();
        let boardContent = $('#textarea[name="boardContent"]').val();

        if (boardTitle.trim() === "" || boardContent.trim() === "") {
            alert("제목과 내용을 입력해 주세요.");
        } else {
            $('#boardForm').submit();
        }
    });
});