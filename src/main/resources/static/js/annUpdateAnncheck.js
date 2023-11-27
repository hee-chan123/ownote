// function updateAnncheck(value) {
//     console.log("updateAnncheck 함수 호출됨. value: " + value);
//     // 선택된 값을 anncheck 필드에 할당
//     document.getElementById("anncheck_id").value = value;
// }

function updateAnncheck(value) {
    console.log("updateAnncheck 함수 호출됨. value: " + value);
    // 선택된 값을 anncheck 필드에 할당
    document.querySelector('input[name="anncheck_id"]:checked').value = value;
}