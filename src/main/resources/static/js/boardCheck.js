$(document).ready(function () {
    $('#submit1').click(function (e) { //write
            e.preventDefault();
            let boardTitle = $('input[name="boardTitle"]').val();
            let boardWriter = $('input[name="boardWriter"]').val();
            let boardDivision = $('select[name="boardDivision"]').val();
            let boardImportant = $('#boardImportant').prop('checked') ? 1 : 0;
            let boardContent = $('textarea[name="boardContent"]').val();
            let error = false;

            if (!boardTitle || !boardContent) {
                alert("제목과 내용을 입력해 주세요.");
                error=true;
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
                        switch (boardDivision) {
                            case "공지사항":
                                window.location.href = "/board/noticeList";
                                break;
                            case "자유게시판":
                                window.location.href = "/board/forumList";
                                break;
                            case "Q&A":
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
            error=true;
        }

        if(!error){
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
                    switch (boardDivision) {
                        case "공지사항":
                            window.location.href = "/board/noticeList";
                            break;
                        case "자유게시판":
                            window.location.href = "/board/forumList";
                            break;
                        case "Q&A":
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
                    switch (boardDivision) {
                        case "공지사항":
                            window.location.href = "/board/noticeList";
                            break;
                        case "자유게시판":
                            window.location.href = "/board/forumList";
                            break;
                        case "Q&A":
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