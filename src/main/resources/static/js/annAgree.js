function validateForm() {
    var anncheck1 = document.getElementById("anncheck_id1");
    var anncheck2 = document.getElementById("anncheck_id2");

    if (!anncheck1.checked && !anncheck2.checked) {
        alert("승인 여부를 선택해야 합니다.");
        return false; // 폼 제출을 막음
    }
    return true; // 폼 제출 허용
}