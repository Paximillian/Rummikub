/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


function setCookie(cookieName, cookieValue){
    document.cookie = cookieName + "=" + cookieValue +"; path=/";
}

function getCookie(cookieName){
    var name = cookieName + "=";
    var cookies = document.cookie.split(';');

    for(var i = 0; i < cookies.length; ++i){
        var cookie = cookies[i];

        while(cookie.charAt(0) == ' '){
            cookie = cookie.substring(1);
        }

        if(cookie.indexOf(name) == 0){
            return cookie.substring(name.length, cookie.length);
        }
    }

    return "";
}