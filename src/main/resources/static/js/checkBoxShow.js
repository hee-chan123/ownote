$(document).ready(function () {
    // boardDivision 선택값 변경 시 이벤트 핸들러
    $('#boardDivision').change(function () {
        let selectedDivision = $(this).val();
        if (selectedDivision === "회사뉴스및공지") {
            $('.checkbox').show();
            $('.checkbox-text').show();
        } else {
            $('.checkbox').hide();
            $('.checkbox-text').hide();
        }
    });
});