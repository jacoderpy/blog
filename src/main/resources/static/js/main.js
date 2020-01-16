
$(document).ready(function(){
    var THRESHOLD = 50;
    var $top = $('.back-to-top');

    $(window).on('scroll', function () {
        $top.toggleClass('back-to-top-on', window.pageYOffset > THRESHOLD);

        var scrollTop = $(window).scrollTop();
        var contentVisibilityHeight = ($('#content').height() > $(window).height()) ? ($('#content').height() - $(window).height()) : ($(document).height() - $(window).height());
        var scrollPercent = (scrollTop) / (contentVisibilityHeight);
        var scrollPercentRounded = Math.round(scrollPercent*100);
        var scrollPercentMaxed = (scrollPercentRounded > 100) ? 100 : scrollPercentRounded;
        $('#scrollpercent>span').html(scrollPercentMaxed);
    });
    $top.on('click', function () {
        $('body').velocity('scroll');
    });
})


