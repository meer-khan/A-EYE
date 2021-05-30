function myFunc() {
    document.getElementById("drop-content").classList.toggle("show");
}
logOut = () => {
    location.href = 'login.html'
}
home = () => {
    location.href = 'profile.html'
}
// Close the dropdown if the user clicks outside of it
window.onclick = function (e) {
    if (!e.target.matches('.dropbtn')) {
        var Dropdown = document.getElementById("drop-content");
        if (Dropdown.classList.contains('show')) {
            Dropdowm.classList.remove('show');
        }
    }
}


$('#web').click(function(){
    $('li a').toggleClass("hideMenuBar");
    $('.sideBar').toggleClass("changeWidth");
      
})


$('#mobile').click(function() {
    $('.sideBar').toggleClass("showMenu");
    $('.backcolor').toggleClass("showBack");
})


$('.close-icon').click(function() {
    $('.sideBar').toggleClass("showMenu");
    $('.backcolor').removeClass("showBack");
})


$('.backcolor').click(function() {
    $('.sideBar').removeClass("showMenu");
    $('.backcolor').removeClass("showBack");
})


$('li').click(function () {
    $('li').removeClass();
    $(this).addClass('selected');
    $('.sideBar').removeClass("showMenu");
    $('.backcolor').removeClass("showBack");
   
})






