$(document).ready(function () {
    $('#submit1').click(function (e) { //write
        e.preventDefault();
        let boardtitle = $('input[name="boardTitle"]').val();
        console.log(boardtitle)
        let boardwriter = $('input[name="boardWriter"]').val();
        console.log(boardwriter)
        let boarddivision = $('select[name="boardDivision"]').val();
        console.log(boarddivision)
        let boardimportant = $('#boardImportant').prop('checked') ? 1 : 0;
        console.log(boardimportant)
        let boardcontent = $('textarea[name="boardContent"]').val();
        console.log(boardcontent)
        let error = false;

        if (!boardtitle || !boardcontent) {
            alert("제목과 내용을 입력해 주세요.");
            error = true;
        }

        if(!error){
            $.ajax({
                type: 'POST',
                url: '/board/boardwrite',
                data: {
                    boardtitle: boardtitle,
                    boardwriter: boardwriter,
                    boarddivision: boarddivision,
                    boardcontent: boardcontent,
                    boardimportant: boardimportant
                },
                success: function (data) {
                    alert("글 등록이 완료되었습니다.");
                    switch (boarddivision) {
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
        let boardnum = $('#boardNum').val();
        let boardtitle = $('input[name="boardTitle"]').val();
        let boardwriter = $('input[name="boardWriter"]').val();
        let boarddivision = $('select[name="boardDivision"]').val();
        let boardimportant = $('#boardImportant').prop('checked') ? 1 : 0;
        let boardcontent = $('textarea[name="boardContent"]').val();
        let error = false;

        if (!boardtitle || !boardcontent) {
            alert("제목과 내용을 입력해 주세요.");
            error = true;
        }

        if (!error) {
            $.ajax({
                type: 'POST',
                url: '/board/boardupdate/' + boardnum,
                data: {
                    boardtitle: boardtitle,
                    boardwriter: boardwriter,
                    boarddivision: boarddivision,
                    boardimportant: boardimportant,
                    boardcontent: boardcontent
                },
                success: function (data) {
                    alert("글 수정이 완료되었습니다.")
                    switch (boarddivision) {
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
        let boardnum = $('#boardNum').val();
        let boardtitle = $('input[name="boardTitle"]').val();
        let boardwriter = $('input[name="boardWriter"]').val();
        let boarddivision = $('select[name="boardDivision"]').val();
        let boardcontent = $('textarea[name="boardContent"]').val();
        let error = false;

        if (!boardtitle || !boardcontent) {
            alert("제목과 내용을 입력해 주세요.");
            error=true;
        }

        if(!error){
            $.ajax({
                type: 'POST',
                url: '/board/replywrite/'+boardnum,
                data: {
                    boardtitle: boardtitle,
                    boardwriter: boardwriter,
                    boarddivision: boarddivision,
                    boardcontent: boardcontent
                },
                success: function (data) {
                    alert("글 등록이 완료되었습니다.")
                    switch (boarddivision) {
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

        let boardnum = $(this).attr('data-boardnum');
        console.log(boardnum);
        let boarddivision = $(this).attr('data-boarddivision');
        console.log(boarddivision);
        let confirmResult = confirm('게시글을 삭제하시겠습니까?');
        console.log(confirmResult);
        if (confirmResult) {
            $.ajax({
                type: 'GET',
                url: '/board/boarddelete/'+boardnum,
                data: {
                    boardnum: boardnum
                },

                success: function (data) {
                    alert('삭제가 완료되었습니다.')
                    switch (boarddivision) {
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
