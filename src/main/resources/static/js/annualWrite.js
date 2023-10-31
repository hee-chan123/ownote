$(document).ready(function () {
    $('#anninfo_code').change(function () {
        // 선택한 옵션의 값을 가져옵니다.
        var selectedValue = $(this).val();
        $.ajax({
            type: "GET",
            url: "/getAnnualTime", // 연차 수량을 가져올 엔드포인트 URL을 지정하세요.
            data: { anninfo_code: selectedValue },
            success: function (data) {
                // 서버에서 받은 데이터로 연차 수량 input의 값을 업데이트합니다.
                $('#annual_time').val(data.annualTime);
            },
            error: function (xhr, status, error) {
                console.error("Error: " + error);
            }
        });
    });
});

$(document).ready(function () {
    $('#anninfo_code').change(function () {
        var selectedValue = $(this).val();
        $.ajax({
            type: 'POST', // POST 요청으로 변경
            url: '/saveAnnualTime', // 저장 엔드포인트로 변경
            data: { anninfo_code: selectedValue },
            success: function (response) {
                // 성공 처리
                alert('연차 수량이 저장되었습니다.');
            },
            error: function (error) {
                console.error('Ajax 요청 중 오류 발생:', error);
            }
        });
    });
});


