$(document).ready(function () {
  // 초기 페이지 로드 시 기본 메뉴를 표시합니다.
  // loadContent('');

  // 메뉴 탭 클릭 시 Ajax 호출을 통해 해당 컨텐츠를 로드합니다.

    let empPhoneInput = document.getElementById("new_emp_phone");
    let empPasswordInput = document.getElementById("new_emp_password");

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
      let old_emp_password = $('input[name="old_emp_password"]').val();
      let new_emp_password = $('input[name="new_emp_password"]').val();
      let old_emp_phone = $('input[name="old_emp_phone"]').val();
      let new_emp_phone = $('input[name="new_emp_phone"]').val();

      // 모든 오류 메시지 초기화
      $('#textError1').text('');
      $('#textError2').text('');

      // 필드 검증
      let error = false;

      if (!new_emp_password) {
          $('#textError1').text('반드시 입력해 주세요.').css('color', 'red');
          error = true;
      } else {
          $('#textError1').text('');
      }

      if (!new_emp_phone) {
          $('#textError2').text('반드시 입력해 주세요.').css('color', 'red');
          error = true;
      }else {
          $('#textError2').text('');
      }



      if (!error) {
          // 확인 대화 상자 표시
          let confirmResult = confirm('변경하시겠습니까?');

          if (confirmResult) {
              // 사용자가 확인을 누른 경우에만 Ajax 요청을 보냅니다.
              $.ajax({
                  type: 'POST',
                  url: '/emp/changePhone', // 수정 처리를 수행하는 컨트롤러의 URL을 적어야 합니다.
                  data: {
                      old_emp_password: old_emp_password,
                      new_emp_password: new_emp_password,
                      old_emp_phone: old_emp_phone,
                      new_emp_phone: new_emp_phone
                  },
                  success: function (data) {
                      alert('사원 정보 변경이 완료되었습니다.');
                      window.location.href = "/logout";
                  },
                  error: function () {
                      // 오류 발생 시 처리
                      alert('사원 정보 변경 중 오류가 발생했습니다.');
                  }
              });
          }
      }
  });
});
