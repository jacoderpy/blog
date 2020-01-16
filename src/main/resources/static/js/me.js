/*
 * search
 * ====================================================
*/
$('.search-show').bind('click', function(){
    $(this).find('.fa').toggleClass('fa-remove')

    $('body').toggleClass('search-on')

    if( $('body').hasClass('search-on') ){
        $('.site-search').find('input').focus()
        $('body').removeClass('m-nav-show')
    }
})