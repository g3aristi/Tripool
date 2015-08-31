<?php
	$mysql_host = "mysql13.000webhost.com";
	$mysql_database = "a1085606_tripool";
	$mysql_user = "a1085606_gil";
	$mysql_password = "ilovehalo2";
    
    $con=mysql_connect("mysql_host", "mysql_user", "mysql_password", "mysql_database");

    $email = $_POST["email"];
    $password = $_POST["password"];

    $stmt = mysqli_prepare($con, "SELECT * FROM User WHERE email = ? AND password = ?");
    mysqli_stmt_bind_param($stmt, "ss", $email, $password);
    mysqli_stmt_execute($stmt);

    mysqli_stmt_store_result($stmt);
    mysqli_stmt_bind_result($stmt, $userId, $name, $email, $password);

    $user = array();

    while(mysqli_stmt_fetch($stmt)){
        $user[name] = $name;
        $user[email] = $email;
        $user[password] = $password;
    }

    echo json_encode($user);

    mysqli_stmt_close($stmt);

    mysqli_close($con);

?>