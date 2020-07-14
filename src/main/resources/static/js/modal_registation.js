import * as reg from './registration.js';

$(".open-modal-1").click(function(){
    $("#modal-reg-1").modal('show');    //окно входа
});
$("#open-modal-2").click(function () {
    $("#modal-reg-2").modal("show");    //окно регистрации
});
$("#open-modal-3").click(function () {
    $("#modal-reg-3").modal("show");
});

$("#btn-reg").click(function (event) {
    event.preventDefault();
    // let user_1 = $("#add-reg-form").serialize();        //переделать!

    //begin namor script
    let email = $("#login").val();
    let password = $("#password").val();
    let password_confirm = $("#password_confirm").val();
    let first_name = $('#first_Name').val();
    let last_name = $('#last_Name').val();
    let region = $('#regionId').children().val();
    let city = $('#citiesId').children().val();
    let phone = $("#phone").val();
    let sum = 0;
    let successValidate;

    //=============== test 0 -  login is email ========//
    let loginRe = new RegExp("\\w+@\\w+\\.\\w{2,4}");
    if ((reg.checker(email, loginRe) === true) && (email.length > 7)) {
        reg.successField("#login");
        sum++;  //1
    } else {
        reg.warningField("#login");
    }

    //========= test1 - is password's not empty =========//
    //!! краснить только проблемный пароль! только пустой!
    if (reg.passwordExist(password) && reg.passwordExist(password_confirm) === true) {
        reg.successField("#password");
        reg.successField("#password_confirm");
        sum++;  //2
    }
    else {
        //вдруг один из паролей - пуст....
        reg.warningField("#password");
        reg.warningField("#password_confirm");
        alert("увы, не введён пароль...");  //!! поменять на нормальный вывод
    }
    //=========== password's equals? =============//
    if (reg.passwordEquals(password, password_confirm) == true) {
        reg.successField("#password");
        reg.successField("#password_confirm");
        sum++;  //3
    } else {
        reg.warningField("#password");
        reg.warningField("#password_confirm");
        alert("проверьте совпадение паролей!");
    }
    //=========  password strong? ===============//
    var passwordStrong = reg.summator(password);
    if ((passwordStrong < 2) || (password.length < 5)){
        reg.infoField("#password");
        reg.infoField("#password_confirm");
        alert("пожалуйста, используйте более сложный пароль.");
    } else {
        sum++;  //4
    }
    //========== check phone number ============/
    var correctPhone = new RegExp("\\d{10}|(\\d{3}(\\s|-)){2}(\\d{2}(\\s|-)\\d{2})");
    if(reg.checker(phone, correctPhone) === true) {
        reg.successField("#phone");
        sum++;  //5
    }
    else {
        reg.warningField("#phone");
        alert("введите номер телефоне в формате 913-123-45-67");
    }
    //============== check exist public name  ==========//
    if(first_name.length > 3) {
        reg.successField('#first_name');
        sum++;  //6!
    } else {
        alert("попробуйте имя более 3 символов!");
        reg.warningField('#first_name');
    }
    if (sum == 6) {
        reg.save(email, password, first_name, last_name, region, city, phone);
        $("#modal-reg-2").modal('toggle');
    }
    else {
    }
    //end script
});

document.getElementById('region').addEventListener('change', loadCities);
function loadCities(){
    $("#citiesId").children().remove();
    let regionId = {
        id : this.selectedIndex
    }
    $.ajax({
        url: '/rest/getCities',
        type: 'POST',
        data: JSON.stringify(regionId),
        contentType: "application/json",
        dataType: "json",
        success: function (cities) {
            $("#citiesId").append("<select class='custom-select' style='margin-top: 10px' id='cities'>");
            $("#cities").append($("<option disabled hidden selected></option>").attr("value", 0).attr("label", 'Город'));
            for(let i=0; i<cities.length; i++) {
                let city_id = cities[i].id;
                let city_name = cities[i].name;
                $("#cities").append($("<option></option>").attr("value", city_id).attr("label", city_name));
                //$("<option value=\"${cities[i].id}\" label=\"${cities[i].name}\"></option>").appendTo($("#cities"));
            }
            $("#regionId").append("</select");
        }
    })
}

$("#btn-modal-3").click(function (event) {
    event.preventDefault();
    let user_2 = $("#form-modal-3").serialize();
    $.ajax({
        url: "/rest/resetPassword",
        data: user_2,
        method: "POST",
        success: function (bool) {
            if (bool){
                $("#modal-reg-password").modal("show");
                $("#reg-password").html("<p>Вскоре вы получите электронное письмо для сброса пароля</p>");
            } else {
                $("#modal-reg-2").modal("show");
                $("#message-reset-password").html("<h4>Пользователь с таким Email не зарегистрирован</h4>" +
                    "<br><p>Пройдите регистрацию</p>");
            }
        }
    });

});
