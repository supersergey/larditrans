$(document).ready(
    function () {
        $("#login").click(
            function () {
                var opt = {
                    autoOpen: false,
                    modal: true,
                    buttons: {
                        OK: function () {
                            $(this).dialog("close");
                        }
                    },
                    close: function () {
                    }
                };
                var userlogin = $("#userlogin").val();
                var password1 = $("#password1").val();
                var password2 = $("#password2").val();
                var fullName = $("#fullName").val();

// Checking for blank fields.
                if (password1 != password2) {
                    var theDialog = $("#passwordNotMatchdialog").dialog(opt);
                    theDialog.dialog("open");

                }
                else if (userlogin == '' || userlogin.length < 3
                    || password1 == '' || password1.length < 5
                    || fullName == '' || fullName.length < 5) {
                    $('input[type="text"],input[type="password"]').css("border", "2px solid red");
                    $('input[type="text"],input[type="password"]').css("box-shadow", "0 0 3px red");
                }

                else {
                    $.post("/addUser",
                        {
                            userLogin: userlogin,
                            password: password1,
                            fullName: fullName
                        }, function (data) {
                            var theDialog = $("#okdialog").dialog(opt);
                            theDialog.dialog("open").on("dialogclose", function () {
                                window.location.replace("login.html");
                            });
                        }).error(function () {
                            var theDialog = $("#errordialog").dialog(opt);
                            theDialog.dialog("open");
                        }
                    )
                }
            }
        )
    });
