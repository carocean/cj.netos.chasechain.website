$(document).ready(function(){
    $('.my-gallery share').click(function(){
        window.location.href=$(this).find('a').attr('href');
    });
    $('.demo p.fx_content').click(function(){
        $(this).attr('style',"overflow:auto;display:block;");
    });
});