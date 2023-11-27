$(document).ready(function () {
  // 초기 페이지 로드 시 기본 메뉴를 표시합니다.
  // loadContent('');

  // 메뉴 탭 클릭 시 Ajax 호출을 통해 해당 컨텐츠를 로드합니다.

    let empPhoneInput = document.getElementById("emp_phone");
    let empPasswordInput = document.getElementById("emp_password");

    // emp_phone 입력란의 값을 변경할 때마다 실행되는 함수를 정의합니다.
    empPhoneInput.oninput = function() {
        let empPhoneValue = empPhoneInput.value;
        // emp_phone 입력란의 마지막 4자리 가져오기
        let lastFourDigits = empPhoneValue.slice(-4);
        // emp_password 입력란에 마지막 4자리 값을 할당
        empPasswordInput.value = lastFourDigits;
    };


  $('#submit_emp').click(function (e) {
      e.preventDefault(); // 기본 폼 제출 동작을 막습니다.

      // 폼 데이터를 가져옵니다.
      let emp_password = $('input[name="emp_password"]').val();
      let emp_birth = $('input[name="emp_birth"]').val();
      let emp_name = $('input[name="emp_name"]').val();
      let emp_email = $('input[name="emp_email"]').val();
      let emp_phone = $('input[name="emp_phone"]').val();
      let dept_num = $("#dept_num").val();
      let grade_num = $("#grade_num").val();

      // 모든 오류 메시지 초기화
      $('#textError1').text('');
      $('#textError2').text('');
      $('#textError3').text('');
      $('#textError4').text('');
      $('#textError5').text('');

      // 필드 검증
      let error = false;

      if (!emp_password) {
          $('#textError1').text('반드시 입력해 주세요.').css('color', 'red');
          error = true;
      } else {
          $('#textError1').text('');
      }

      if (!emp_birth) {
          $('#textError2').text('반드시 입력해 주세요.').css('color', 'red');
          error = true;
      }else {
          $('#textError2').text('');
      }

      if (!emp_name) {
          $('#textError3').text('반드시 입력해 주세요.').css('color', 'red');
          error = true;
      }else {
          $('#textError3').text('');
      }

      if (!emp_email) {
        $('#textError4').text('반드시 입력해 주세요.').css('color', 'red');
        error = true;
    } else {
        $('#textError4').text('');
    }

    if (!emp_phone) {
      $('#textError5').text('반드시 입력해 주세요.').css('color', 'red');
      error = true;
  }else {
      $('#textError5').text('');
  }

      if (!error) {
          // 확인 대화 상자 표시
          let confirmResult = confirm('등록하시겠습니까?');

          if (confirmResult) {
              // 사용자가 확인을 누른 경우에만 Ajax 요청을 보냅니다.
              $.ajax({
                  type: 'POST',
                  url: '/ownote', // 수정 처리를 수행하는 컨트롤러의 URL을 적어야 합니다.
                  data: {
                    emp_password: emp_password,
                    emp_birth: emp_birth,
                    emp_name: emp_name,
                    emp_email: emp_email,
                    emp_phone: emp_phone,
                      dept_num: dept_num,
                      grade_num: grade_num
                  },

                  success: function (data) {
                      alert('사원 등록이 완료되었습니다.');
                      window.location.href = "/";
                  },
                  error: function () {
                      // 오류 발생 시 처리
                      alert('사원 등록 중 오류가 발생했습니다.');
                  }
              });
          }
      }
  });
});
