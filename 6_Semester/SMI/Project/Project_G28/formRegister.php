<?php
if ( !isset( $_SESSION ) ) {
    session_start();
}

require_once("lib/lib.php");
require_once("lib/db.php");

require_once( "config.php" );
require_once( "configDebug.php" );
require_once( "regExps.php" );
?>
<!DOCTYPE html>
<html>
<head>
    <title>Register</title>
    <meta http-equiv='Content-Type' content='text/html; charset=utf-8'>

    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>

    <link rel="stylesheet" type="text/css" href="Styles/GlobalStyle.css">
    <link rel="icon" href="images/icon.png">

    <script type="text/javascript">
        <?php
        echo "\t\t\tvar filterCaptcha  = $filterCaptcha;\n";
        echo "\t\t\tvar filterUserName = $filterUserName;\n";
        echo "\t\t\tvar filterPassword = $filterPassword;\n";
        echo "\t\t\tvar filterEmail    = $filterEmail;\n";
        ?>
    </script>

    <script type="text/javascript" src="scripts/forms.js"></script>
</head>
<body>
<div id="containerDiv" >
    <div id="headerDiv" style="width: 100%;">
        <?php include_once("header.php"); ?>
    </div>

    <div id="mainContent" style="display: flex; width: 100%; height: 100%;">
        <div id="menuDiv" style="width: 15%; height: 100%; box-sizing: border-box;">
            <?php include_once("sideBar.php") ?>
        </div>

        <div id="contentDiv">
            <div id="titleRow">
                <h1 class="titleClass">Register</h1>
            </div>
            <form action="./processFormRegister.php" method="POST" onsubmit="return FormRegisterUserValidator(this)" style="margin-top: 350px; margin-left: 100px">
                <table>
                    <tr>
                        <td><img src="captchaImage.php"/></td>
                        <td><input
                                    type="text"
                                    size="20"
                                    onblur="CheckCaptcha()"
                                    name="captcha"
                                    id="captcha"
                                    placeholder="Captcha code"
                                <?php echo $captchaValueForm; ?> >
                        </td>
                        <td><div id="captchaState"></div></td>
                    </tr>

                    <tr>
                        <td>User Name</td>
                        <td><input
                                    type="text"
                                    size="20"
                                    onblur="CheckUserName()"
                                    name="userName"
                                    id="userName"
                                    placeholder="User name"
                                <?php echo $userValueForm; ?> >
                        </td>
                        <td><div id="userNameState"></div></td>
                    </tr>

                    <tr>
                        <td>Password</td>
                        <td><input
                                    type="password"
                                    size="20"
                                    onblur="CheckPasswords()"
                                    name="password1"
                                    id="password1"
                                    placeholder="Password" >
                        </td>
                        <td><div id="password1State"></div></td>
                    </tr>

                    <tr>
                        <td>Re-type password</td>
                        <td><input
                                    type="password"
                                    size="20"
                                    onblur="CheckPasswords()"
                                    name="password2"
                                    id="password2"
                                    placeholder="Confirm password">
                        </td>
                        <td><div id="password2State"></div></td>
                    </tr>

                    <tr>
                        <td>e-mail</td>
                        <td><input
                                    type="email"
                                    size="20"
                                    onblur="CheckEmail()"
                                    name="email"
                                    id="email"
                                    placeholder="Email"
                                <?php echo $emailValueForm; ?> >
                        </td>
                        <td><div id="emailState"></div></td>
                    </tr>
                </table>

                <br>

                <input type="submit" value="Register User"> <input type="reset" value="Clear">

                <br><br>

                <a href="formLogin.php">Login for existing users</a>

                <br><br>

                <a target="_main" href="index.php">Back to Main Page</a>
            </form>
        </div>
    </div>

</div>
</body>
</html>