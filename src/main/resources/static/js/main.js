
$(document).ready(function () {
    // 초기 페이지 로드 시 기본 메뉴를 표시합니다.
    // loadContent('');

    // 메뉴 탭 클릭 시 Ajax 호출을 통해 해당 컨텐츠를 로드합니다.


    $('#list-link').click(function () {
        loadContent('list');
    });

    $('.list-link').click(function () {
        loadContent('list');
    });

    $('#write-link').click(function () {
        loadContent('write');
    });

    $('.view-link').click(function (event) {
        event.preventDefault(); // 기본 링크 동작을 막습니다.
        var boardNo = $(this).data('board-no');
        loadContent('view?ly_board_no=' + boardNo);
    });



    $('.listpage-link').click(function (event) {
        event.preventDefault(); // 기본 링크 동작을 막습니다.
        var pageNo = $(this).data('pageno');

        loadContent('list?pageNo=' + pageNo);

    });

    $('.modify-link').click(function (event) {
        event.preventDefault(); // 기본 링크 동작을 막습니다.
        var boardNo = $(this).data('board-no');
        loadContent('modify?ly_board_no=' + boardNo);
    });

    $('.delete-link').click(function (e) {
        e.preventDefault(); // 기본 폼 제출 동작을 막습니다.

        // 폼 데이터를 가져옵니다.
        var boardNo1 = $(this).data('board-no');

        // Ajax 요청을 보냅니다.
        $.ajax({
            type: 'POST',
            url: 'delete', // 수정 처리를 수행하는 컨트롤러의 URL을 적어야 합니다.
            data: {
                boardNo: boardNo1
            },

            success: function (data) {
                // 수정 성공 시 처리
                // data에 컨트롤러에서 반환한 결과가 들어옵니다.
                // 원하는 방식으로 결과를 처리합니다.
                $('#content-container').html(data);
                alert('게시글이 삭제되었습니다.');
            },
            error: function () {
                // 오류 발생 시 처리
                alert('삭제 처리 중 오류가 발생했습니다.');
            }
        });
    });

    $('#modify-button').click(function (e) {
        e.preventDefault(); // 기본 폼 제출 동작을 막습니다.

        // 폼 데이터를 가져옵니다.
        var ly_board_no1 = $('input[name="ly_board_no"]').val();
        var ly_title1 = $('input[name="ly_title"]').val();
        var tag_name1 = $('input[name="tag_name"]:checked').val();
        var ly_content1 = $('textarea[name="ly_content"]').val();

        // Ajax 요청을 보냅니다.
        $.ajax({
            type: 'POST',
            url: 'modify', // 수정 처리를 수행하는 컨트롤러의 URL을 적어야 합니다.
            data: {
                ly_board_no: ly_board_no1,
                ly_title: ly_title1,
                tag_name: tag_name1,
                ly_content: ly_content1
            },

            success: function (data) {
                // 수정 성공 시 처리
                // data에 컨트롤러에서 반환한 결과가 들어옵니다.
                // 원하는 방식으로 결과를 처리합니다.
                $('#content-container').html(data);
                alert('수정이 완료되었습니다.');
            },
            error: function () {
                // 오류 발생 시 처리
                alert('수정 중 오류가 발생했습니다.');
            }
        });
    });

    $('#write1-button').click(function (e) {
        e.preventDefault(); // 기본 폼 제출 동작을 막습니다.

        // 폼 데이터를 가져옵니다.
        var ly_title1 = $('input[name="ly_title"]').val();
        var tag_name1 = $('input[name="tag_name"]:checked').val();
        var ly_content1 = $('textarea[name="ly_content"]').val();
        var nickname1 = $('input[name="nickname"]').val();
        var id1 = $('input[name="id"]').val();

        // 모든 오류 메시지 초기화
        $('#titleError').text('');
        $('#tagError').text('');
        $('#contentError').text('');

        // 필드 검증
        var error = false;

        if (!ly_title1) {
            $('#titleError').text('제목이 비어있습니다.').css('color', 'red');
            error = true;
        } else {
            $('#titleError').text('');
        }

        if (!tag_name1) {
            $('#tagError').text('태그 필수입니다.').css('color', 'red');
            error = true;
        }else {
            $('#tagError').text('');
        }

        if (!ly_content1) {
            $('#contentError').text('내용이 비어있습니다.').css('color', 'red');
            error = true;
        }else {
            $('#contentError').text('');
        }

        if (!error) {
            // 확인 대화 상자 표시
            var confirmResult = confirm('등록하시겠습니까?');

            if (confirmResult) {
                // 사용자가 확인을 누른 경우에만 Ajax 요청을 보냅니다.
                $.ajax({
                    type: 'POST',
                    url: 'write', // 수정 처리를 수행하는 컨트롤러의 URL을 적어야 합니다.
                    data: {
                        ly_title: ly_title1,
                        tag_name: tag_name1,
                        ly_content: ly_content1,
                        nickname: nickname1,
                        id: id1
                    },

                    success: function (data) {
                        // 수정 성공 시 처리
                        // data에 컨트롤러에서 반환한 결과가 들어옵니다.
                        // 원하는 방식으로 결과를 처리합니다.
                        $('#content-container').html(data);
                        alert('게시글 등록이 완료되었습니다.');
                    },
                    error: function () {
                        // 오류 발생 시 처리
                        alert('게시물 등록 중 오류가 발생했습니다.');
                    }
                });
            }
        }
    });

    // 다른 메뉴 항목에 대한 클릭 이벤트도 추가할 수 있습니다.
});

function loadContent(page) {
    // Ajax 호출을 사용하여 컨텐츠를 로드합니다.
    $.ajax({
        type: 'GET',
        url: page, // 요청할 URL을 수정해야 할 수 있습니다.
        success: function (data) {
            // 성공적으로 데이터를 받아왔을 때 컨텐츠 영역에 삽입합니다.
            $('#content-container').html(data);
        },
        error: function () {
            alert('컨텐츠를 불러오는 중 오류가 발생했습니다.');
        }
    });
}