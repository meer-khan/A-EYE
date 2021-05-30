let container = document.getElementById('container')

toggle = () => {
    container.classList.toggle('log-in')
    container.classList.toggle('Forgot-Password')
}

setTimeout(()=> {
    container.classList.add('log-in')
},200)


validate = () =>{
    var email = document.getElementById("email").value;
    var psw = document.getElementById("psw").value;
    if (email == "dennis123@gmail.com" && psw == "123456") {
        window.location.href = "profile.html";
        return false;   
    }
    
    if (email == null || email == "" && psw == null || psw == "") {
        alert("Fields can not be empty"); 
        return false;
    }
    else{
        alert("Invalid email or password");
        window.location.href = "login.html";
        return false;
    }
    window.location.reload();
}
