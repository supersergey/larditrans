$(document).ready(
    function () {
        $("#btnLogout").click(
            function () {
                $.post("/logout",
                    {
                        userLogin: userLogin,
                        token: token
                    },
                    function () {
                        window.location.replace("login.html");})
            }
        )
    }
)
