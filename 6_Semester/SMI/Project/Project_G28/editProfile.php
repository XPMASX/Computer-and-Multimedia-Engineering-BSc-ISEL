<?php
require_once("lib/lib.php");
require_once("lib/db.php");

require_once("config.php");
require_once("configDebug.php");
require_once("regExps.php");

if (!isset($_SESSION)) {
    session_start();
}

if ($_GET['profile'] != '') {
    $perfilName = $_GET['profile'];
    $changeUser = getUser($perfilName);
}

if (empty($changeUser)) {
    echo "<div style='color: red; font-weight: bold; text-align: center;'>User does not exist</div>";
    echo "<div style='text-align: center;'><a href='index.php'>Go Back</a></div>";
    exit(); // Stop execution if no articles are found
}
if ($_SESSION['username'] !== $perfilName) {
    echo "<div style='color: red; font-weight: bold; text-align: center;'>You do not have permission to access this</div>";
    echo "<div style='text-align: center;'><a href='index.php'>Go Back</a></div>";
    exit(); // Stop execution if the user does not have permission
}
?>
<!DOCTYPE html>
<html>
<head>
    <title>Edit Profile</title>
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
<div id="containerDiv">
    <div id="headerDiv" style="width: 100%;">
        <?php include_once("header.php"); ?>
    </div>

    <div id="mainContent" style="display: flex; width: 100%; height: 100%;">
        <div id="menuDiv" style="width: 15%; height: 100%; box-sizing: border-box;">
            <?php include_once("sideBar.php") ?>
        </div>

        <div id="contentDiv">
            <div id="titleRow">
                <h1 class="titleClass">Profile: <?php echo $changeUser['name']; ?></h1>
            </div>
            <form action="processEditUser.php?profile=<?php echo $_GET['profile']; ?>" method="POST"
                  onsubmit="return FormEditProfileValidator(this)" style="margin-top: 400px; margin-left: 100px">
                <table>
                    <tr>
                        <td>User Name</td>
                        <td><input
                                    type="text"
                                    size="20"
                                    onblur="checkNameChange(this)"
                                    name="userName"
                                    id="userName"
                                    data-original="<?php echo $changeUser['name']; ?>"
                                    value="<?php echo $changeUser['name']; ?>">
                        </td>
                        <td>
                            <div id="userNameState"></div>
                        </td>
                    </tr>
                    <tr>
                        <td>e-mail</td>
                        <td><input
                                    type="email"
                                    size="20"
                                    onblur="checkEmailChange(this)"
                                    name="email"
                                    id="email"
                                    data-original="<?php echo $changeUser['email']; ?>"
                                    value="<?php echo $changeUser['email']; ?>">
                        </td>
                        <td>
                            <div id="emailState"></div>
                        </td>
                    </tr>
                    <tr>
                        <td>Change Password</td>
                        <td><input
                                    type="checkbox"
                                    id="changePassword"
                                    onchange="togglePasswordFields()">
                            <input type="hidden" id="isPasswordChanged" name="isPasswordChanged" value="false">
                        </td>
                    </tr>
                    <tr>
                        <td>New Password</td>
                        <td><input
                                    type="password"
                                    size="20"
                                    name="password1"
                                    id="password1"
                                    onblur="CheckPasswords()"
                                    placeholder="Password"
                                    disabled>
                        </td>
                        <td>
                            <div id="password1State"></div>
                        </td>
                    </tr>

                    <tr>
                        <td>Confirm password</td>
                        <td><input
                                    type="password"
                                    size="20"
                                    name="password2"
                                    id="password2"
                                    onblur="CheckPasswords()"
                                    placeholder="Confirm password"
                                    disabled>
                        </td>
                        <td>
                            <div id="password2State"></div>
                        </td>
                    </tr>

                </table>

                <script>

                    function checkNameChange(input) {
                        var original = input.getAttribute('data-original');
                        var userNameState = document.getElementById('userNameState');
                        if (input.value !== original) {
                            CheckUserName();
                        } else {
                            userNameState.innerHTML = '';
                        }
                    }

                    function checkEmailChange(input) {
                        var original = input.getAttribute('data-original');
                        var emailState = document.getElementById('emailState');
                        if (input.value !== original) {
                            CheckEmail();
                        } else {
                            emailState.innerHTML = '';
                        }
                    }

                    function togglePasswordFields() {
                        var changePasswordCheckbox = document.getElementById('changePassword');
                        var password1Field = document.getElementById('password1');
                        var password2Field = document.getElementById('password2');
                        var isPasswordChangedField = document.getElementById('isPasswordChanged');
                        var password1State = document.getElementById('password1State');
                        var password2State = document.getElementById('password2State');

                        if (changePasswordCheckbox.checked) {
                            password1Field.disabled = false;
                            password2Field.disabled = false;
                            password1Field.required = true;
                            password2Field.required = true;
                            isPasswordChangedField.value = 'true';
                        } else {
                            password1Field.disabled = true;
                            password2Field.disabled = true;
                            password1Field.value = '';
                            password2Field.value = '';
                            password1Field.required = false;
                            password2Field.required = false;
                            isPasswordChangedField.value = 'false';
                            password1State.innerHTML = '';
                            password2State.innerHTML = '';
                        }
                    }
                </script>

                <br>

                <input type="submit" value="Edit User">

                <br><br>
                <a href="index.php">Back to Main Page</a>
            </form>


        </div>
    </div>

</div>
</body>
</html>