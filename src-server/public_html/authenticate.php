<?php

if (isset($_GET['email']) && isset($_GET['pass'])) {
	
	require_once("../../www-includes/dbcx.php");
    $dbh = dbcx();
          
    $email = trim($_GET['email']);
    $pass = $_GET['pass'];
            
    $sql = "SELECT password FROM users WHERE email = :email";
    $stmt = $dbh->prepare($sql);
    $stmt->bindParam(":email", $email);
    $stmt->execute();
    $dbcollected = $stmt->fetch();
    
    if ($dbcollected['password'] != crypt($pass, $dbcollected['password'])) {
        $status = "0"; // Wrong email/pass
    }
    else {
	    $status = "1"; // Success
    }
}
else {
	$status = "0";
}

echo $status;
?>