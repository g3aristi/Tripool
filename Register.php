<?php
	$mysql_host = "mysql13.000webhost.com";
	$mysql_database = "a1085606_tripool";
	$mysql_user = "a1085606_gil";
	$mysql_password = "ilovehalo2";
    
    $con=mysql_connect("mysql_host", "mysql_user", "mysql_password", "mysql_database");

    $name = $_POST["name"];

    $stmt = mysqli_prepare($con, "INSER INTO User (name, email, password) VALUES (?, ?, ?)");
    mysqli_stmt_bind_param($stmt, "sss", $name, $email, $password);
    mysqli_stmt_execute($stmt);

    mysqli_stmt_close($stmt); 

    mysqli_close($con);

?>