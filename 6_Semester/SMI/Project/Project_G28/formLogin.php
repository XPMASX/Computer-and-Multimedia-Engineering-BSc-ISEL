<!DOCTYPE html>
<html>
<head>
    <meta http-equiv='Content-Type' content='text/html; charset=utf-8'>
    <title>Login</title>

    <link rel="icon" href="images/icon.png">

    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>

    <link rel="stylesheet" type="text/css" href="Styles/GlobalStyle.css">
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
                <h1 class="titleClass">Login</h1>
            </div>
            <form action="processFormLogin.php" method="POST" style="margin-top: 350px; margin-left: 100px">
                <table>
                    <tr>
                        <td>User Name</td>
                        <td><input type="text" name="username" placeholder="Type your name"></td>
                    </tr>
                    <tr>
                        <td>Password</td>
                        <td><input type="password" name="password" placeholder="Type your password"></td>
                    </tr>
                </table>

                <input type="submit" value="Login"> <input type="reset" value="Clear">

                <br><br><br>

                <a href="formRegister.php">Register New User</a>

                <br><br>

                <a href="index.php">Back to Main Page</a>
            </form>

        </div>
    </div>

</div>
</body>
</html>