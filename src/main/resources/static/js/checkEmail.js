function check_emp_email() {
    var emp_email = document.getElementById("emp_email").value;
    //let emp_email2 = $('input[name="emp_email"]').val();
    $('#textError4').text('');
    let error = false;
    // AJAX 요청 보내기
    $.ajax({
        type: "POST",
        url: "/checkEmail",
        data: { emp_email: emp_email + '@ownote.com' },
        success: function(response) {
        if (response === "사용 가능한 이메일입니다.") {
        document.getElementById("result").innerHTML= "사용 가능한 이메일입니다.";
    } else {
            document.getElementById("result").innerHTML = "이미 사용 중인 이메일입니다.";
        }
        if (!emp_email){
                document.getElementById("result").innerHTML = "이메일을 입력해 주세요.";
            }
        if (emp_email.includes('@')){
            document.getElementById("result").innerHTML = "형식에 맞춰 입력해 주세요.";
        }
        }
    });
}


