$(document).ready(
    function () {
        $("#login").click(
            function () {
                var userLogin = $("#userlogin").val();
                var password = $("#password").val();
// Checking for blank fields.
                if (userLogin == '' || userLogin.length < 3 || password == '' || password.length < 5) {
                    $('input[type="text"],input[type="password"]').css("border", "2px solid red");
                    $('input[type="text"],input[type="password"]').css("box-shadow", "0 0 3px red");

                } else {
                    $.post("/authorize",
                        {
                            userLogin: userLogin,
                            password: password
                        }, function (token) {
                            localStorage.setItem('userLogin', userLogin);
                            localStorage.setItem('token', token);
                            window.location.replace("viewdata.html");
                        }).error(function () {
                            var opt = {
                                autoOpen: false,
                                modal: true,
                                title: 'Ошибка авторизации',
                                buttons: {
                                    OK: function () {
                                        $(this).dialog("close");
                                    }
                                }
                            };
                            var theDialog = $("#dialog").dialog(opt);
                            theDialog.dialog("open");
                        })
                }
            })
    })
