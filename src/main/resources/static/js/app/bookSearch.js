'use strict';

const bookSearch = {
    init : function () {
        let _this = this;
        $('.form-title').keypress( function (event) {
            if(event.keyCode === 13){
                event.preventDefault();
                _this.search();
            }

        });
        $('.btn-search').on('click', function () {
            _this.search();
        });
        $('.bookSelected').on('click', function () {
            _this.selection(this);
        });
        _this.hide();
        _this.selectedResult();
    },
    search : function () {
        let bookTitle = $('.bookTitle').val();
        window.location.href = '/search/results/' + bookTitle;
    },
    selection : function (button) {
        let tr = $(button).parent().parent();

        let image = tr.children('.td-image').children().text();
        let title = tr.children('.td-title').text();
        let author = tr.children('.td-author').text();
        let publisher = tr.children('.td-publisher').text();

        if(sessionStorage){
            sessionStorage.setItem('image', image);
            sessionStorage.setItem('title', title);
            sessionStorage.setItem('author', author);
            sessionStorage.setItem('publisher', publisher);
        }else{
            alert("your browser don't support sessionStorage.");
            return;
        }

        window.location.href='/posts/save';
    },
    hide : function () {
        $('.image-src').hide();
    },
    selectedResult : function () {
        if($('.selectedResult').length){
            let image = sessionStorage.getItem('image');
            let title = sessionStorage.getItem('title');
            let author = sessionStorage.getItem('author');
            let publisher = sessionStorage.getItem('publisher');

            $('.bookTitle').text(title);
            $('.bookAuthor').text(author);
            $('.bookPublisher').text(publisher);
            $('.bookImage').html("<img class='mx-auto d-block' alt='이미지없음' src="+image+">");
            $('.image-src').text(image);

            sessionStorage.clear();
        }
    }
};

bookSearch.init();