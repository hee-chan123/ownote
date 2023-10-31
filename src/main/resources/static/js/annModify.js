$(document).ready(function () {
    $('#annual').submit(function (event) {
        // 이벤트 기본 동작을 막습니다.
        event.preventDefault();

        // 폼 데이터를 가져와서 필요한 값을 추가합니다.
        var formData = $(this).serializeArray();
        formData.push({name: "annual_id", value: $('#annual_id').val()});
        formData.push({name: "anncheck_id", value: $('#anncheck_id').val()});

        // 폼을 서버로 제출합니다.
        $.ajax({
            type: "POST",
            url: $(this).attr('action'),
            data: formData, // 수정된 formData를 전송
            success: function (data) {
                // 수정이 완료되었음을 알리는 알럿을 띄웁니다.
                alert("수정되었습니다.");

                // 리스트 폼 페이지로 이동합니다.
                window.location.href = "/annual/anndata";
            },
            error: function (xhr, status, error) {
                alert("재시도해주세요.");
                console.error("Error: " + error);
            }
        });
    });
});