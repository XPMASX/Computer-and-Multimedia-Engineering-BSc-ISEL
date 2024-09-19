<!DOCTYPE html>
<html>
<head>
    <meta http-equiv='Content-Type' content='text/html; charset=utf-8'>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <style>
        .autocomplete-suggestion {
            padding: 8px;
            cursor: pointer;
            border-bottom: 1px solid #ddd;
        }
        .autocomplete-suggestion:hover {
            background-color: #e9e9e9;
        }
    </style>
    <script>
        function showResult(str) {
            if (str.length == 0) {
                document.getElementById("livesearch").innerHTML = "";
                document.getElementById("livesearch").style.border = "0px";
                return;
            }
            var xmlhttp = new XMLHttpRequest();
            xmlhttp.onreadystatechange = function () {
                if (this.readyState == 4 && this.status == 200) {
                    console.log(this.responseText); // Log the response text
                    document.getElementById("livesearch").innerHTML = this.responseText;
                    document.getElementById("livesearch").style.border = "1px solid #A5ACB2";

                    // Log the first suggestion if available
                    var suggestions = document.getElementsByClassName('autocomplete-suggestion');
                    if (suggestions.length > 0) {
                        console.log(suggestions[0].innerText);
                    } else {
                        console.log("No suggestions found");
                    }

                    // Add click event listeners to suggestions
                    for (var i = 0; i < suggestions.length; i++) {
                        suggestions[i].addEventListener('click', function() {
                            var title = this.getAttribute('data-title');
                            window.location.href = 'showArticle.php?title=' + encodeURIComponent(title);
                        });
                    }
                }
            }
            xmlhttp.open("GET", "autoComplete.php?q=" + str, true);
            xmlhttp.send();
        }

        document.addEventListener('click', function (e) {
            var target = e.target;
            if (!target.closest('.autocomplete-suggestion')) {
                document.getElementById("livesearch").innerHTML = "";
                document.getElementById("livesearch").style.border = "0px";
            }
        });
    </script>
</head>
<body>
<?php
if (!isset($_SESSION)) {
    session_start();
}

if (isset($_SESSION['username'])) {
    require_once("lib/lib.php");
    require_once("lib/db.php");

    $user = $_SESSION['username'];
    $id = $_SESSION['id'];
    $userRole = getRole($id);
    $userEmail = getEmail($id, "basic");
    echo "<div style=\"display: flex; align-items: center;\">";
    echo "<div style=\"font-weight:bold;text-align:right; margin-right: 10px;\">$user (<a href=\"mailto:$userEmail\">$userEmail</a>) $userRole</div>";
    ?>
    <script>
        function showResult(str) {
            if (str.length == 0) {
                document.getElementById("livesearch").innerHTML = "";
                document.getElementById("livesearch").style.border = "0px";
                return;
            }
            var xmlhttp = new XMLHttpRequest();
            xmlhttp.onreadystatechange = function () {
                if (this.readyState == 4 && this.status == 200) {
                    console.log(this.responseText); // Log the response text
                    document.getElementById("livesearch").innerHTML = this.responseText;
                    document.getElementById("livesearch").style.border = "1px solid #A5ACB2";

                    // Log the first suggestion if available
                    var suggestions = document.getElementsByClassName('autocomplete-suggestion');
                    if (suggestions.length > 0) {
                        console.log(suggestions[0].innerText);
                    } else {
                        console.log("No suggestions found");
                    }

                    // Add click event listeners to suggestions
                    for (var i = 0; i < suggestions.length; i++) {
                        suggestions[i].addEventListener('click', function() {
                            var title = this.getAttribute('data-title');
                            window.location.href = 'showArticle.php?title=' + encodeURIComponent(title);
                        });
                    }
                }
            }
            xmlhttp.open("GET", "autoComplete.php?q=" + str, true);
            xmlhttp.send();
        }

        document.addEventListener('click', function (e) {
            var target = e.target;
            if (!target.closest('.autocomplete-suggestion')) {
                document.getElementById("livesearch").innerHTML = "";
                document.getElementById("livesearch").style.border = "0px";
            }
        });
    </script>

    <!-- Logout form -->
    <form action="logout.php" method="post" style="padding: 10px;">
        <button type="submit">Logout</button>
    </form>

    <form action="articleUpload.php" method="post" style="padding: 10px;">
        <button type="submit">Add Article</button>
    </form>
    <!-- Search form -->
    <form action="showArticle.php" method="get" style="position: relative;">
        <input type="text" name="title" placeholder="Pesquisar" style="padding-right: 30px;" onkeyup="showResult(this.value)">
        <button type="submit" style="position: absolute; right: 0; top: 0; border: none; background: none; cursor: pointer;">&#128269;</button>
        <div id="livesearch" style="position: absolute; background: white; z-index: 1000;"></div>
    </form>
    </div>
<?php
} else {
?>
    <!-- Create Account and Login buttons -->
    <div style="display: flex; align-items: center;">
        <form action="formRegister.php" method="post" style="margin-right: 10px;">
            <button type="submit">Create Account</button>
        </form>
        <form action="formLogin.php" method="post" style="margin-right: 10px;">
            <button type="submit">Login</button>
        </form>
        <!-- Search form -->
        <form action="showArticle.php" method="get" style="position: relative;">
            <input type="text" name="title" placeholder="Pesquisar" style="padding-right: 30px;" onkeyup="showResult(this.value)">
            <button type="submit" style="position: absolute; right: 0; top: 0; border: none; background: none; cursor: pointer;">&#128269;</button>
            <div id="livesearch" style="position: absolute; background: white; z-index: 1000;"></div>
        </form>
    </div>
    <?php
}
?>
</body>
</html>
