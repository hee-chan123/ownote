var urlParams = new URLSearchParams(window.location.search);
if (urlParams.get("error") === "1") {
    window.location.href = "/";
    alert("잔여 연차가 없습니다.");

}