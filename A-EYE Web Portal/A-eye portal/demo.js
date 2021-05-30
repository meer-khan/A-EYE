viewDemo = () => {
    location.href = 'demo.html'
}


$('#dem').click(function () {
    $('.demo').toggleClass("showDem");
})

$('#close').click(function () {
    $('.demo').toggleClass("hideDem");
})