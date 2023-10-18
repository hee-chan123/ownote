$(document).ready(function () {
    $('#submit1').click(function (e) { //write
        e.preventDefault();
        let boardTitle = $('input[name="boardTitle"]').val();
        let boardWriter = $('input[name="boardWriter"]').val();
        let boardDivision = $('select[name="boardDivision"]').val();
        let boardImportant = $('#boardImportant').prop('checked') ? 1 : 0;
        let boardContent = $('textarea[name="boardContent"]').val();
        console.log(boardContent.length);
        let error = false;

        if (!boardTitle || !boardContent) {
            alert("제목과 내용을 입력해 주세요.");
            error = true;
        }

        if(!error){
            $.ajax({
                type: 'POST',
                url: '/board/boardwrite',
                data: {
                    boardTitle: boardTitle,
                    boardWriter: boardWriter,
                    boardDivision: boardDivision,
                    boardImportant: boardImportant,
                    boardContent: boardContent
                },
                success: function (data) {
                    alert("글 등록이 완료되었습니다.");
                    switch (boardDivision) {
                        case "회사뉴스및공지":
                            window.location.href = "/board/noticeList";
                            break;
                        case "자유게시판":
                            window.location.href = "/board/forumList";
                            break;
                        case "사내시스템/F&Q":
                            window.location.href = "/board/qaList";
                            break;
                        default:
                            window.location.href = "/board/boardmain";
                            break;
                    }
                },
                error: function () {
                    alert("오류")
                }
            });
        }
    });
});

$(document).ready(function () {
    $('#submit2').click(function (e) { //update
        e.preventDefault();
        let boardNum = $('#boardNum').val();
        let boardTitle = $('input[name="boardTitle"]').val();
        let boardWriter = $('input[name="boardWriter"]').val();
        let boardDivision = $('select[name="boardDivision"]').val();
        let boardImportant = $('#boardImportant').prop('checked') ? 1 : 0;
        let boardContent = $('textarea[name="boardContent"]').val();
        let error = false;

        if (!boardTitle || !boardContent) {
            alert("제목과 내용을 입력해 주세요.");
            error = true;
        }

        if (!error) {
            $.ajax({
                type: 'POST',
                url: '/board/boardupdate/' + boardNum,
                data: {
                    boardTitle: boardTitle,
                    boardWriter: boardWriter,
                    boardDivision: boardDivision,
                    boardImportant: boardImportant,
                    boardContent: boardContent
                },
                success: function (data) {
                    alert("글 수정이 완료되었습니다.")
                    switch (boardDivision) {
                        case "회사뉴스및공지":
                            window.location.href = "/board/noticeList";
                            break;
                        case "자유게시판":
                            window.location.href = "/board/forumList";
                            break;
                        case "사내시스템/F&Q":
                            window.location.href = "/board/qaList";
                            break;
                        default:
                            window.location.href = "/board/boardmain";
                            break;
                    }
                },
                error: function () {
                    alert("오류")
                }
            });
        }
    });
});

$(document).ready(function () {
    $('#submit3').click(function (e) { //reply
        e.preventDefault();
        let boardNum = $('#boardNum').val();
        let boardTitle = $('input[name="boardTitle"]').val();
        let boardWriter = $('input[name="boardWriter"]').val();
        let boardDivision = $('select[name="boardDivision"]').val();
        let boardContent = $('textarea[name="boardContent"]').val();
        let error = false;

        if (!boardTitle || !boardContent) {
            alert("제목과 내용을 입력해 주세요.");
            error=true;
        }

        if(!error){
            $.ajax({
                type: 'POST',
                url: '/board/replywrite/'+boardNum,
                data: {
                    boardTitle: boardTitle,
                    boardWriter: boardWriter,
                    boardDivision: boardDivision,
                    boardContent: boardContent
                },
                success: function (data) {
                    alert("글 등록이 완료되었습니다.")
                    switch (boardDivision) {
                        case "회사뉴스및공지":
                            window.location.href = "/board/noticeList";
                            break;
                        case "자유게시판":
                            window.location.href = "/board/forumList";
                            break;
                        case "사내시스템/F&Q":
                            window.location.href = "/board/qaList";
                            break;
                        default:
                            window.location.href = "/board/boardmain";
                            break;
                    }
                },
                error: function () {
                    alert("오류")
                }
            });
        }
    });
});

$(document).ready(function () { //삭제
    $('.deleteBoard').click(function (e) {
        e.preventDefault();

        let boardNum = $(this).attr('data-boardNum');
        console.log(boardNum);
        let boardDivision = $(this).attr('data-boardDivision');
        console.log(boardDivision);
        let confirmResult = confirm('게시글을 삭제하시겠습니까?');
        console.log(confirmResult);
        if (confirmResult) {
            $.ajax({
                type: 'GET',
                url: '/board/boarddelete/'+boardNum,
                data: {
                    boardNum: boardNum
                },

                success: function (data) {
                    alert('삭제가 완료되었습니다.')
                    switch (boardDivision) {
                        case "회사뉴스및공지":
                            window.location.href = "/board/noticeList";
                            break;
                        case "자유게시판":
                            window.location.href = "/board/forumList";
                            break;
                        case "사내시스템/F&Q":
                            window.location.href = "/board/qaList";
                            break;
                    }
                },
                error: function () {
                    alert('오류가 발생했습니다.');
                }
            });
        }
    });
});
