<?php

$success = "true";
$username = "";

if (isset($_POST['email']) && isset($_POST['pass'])) {
	
	require_once("../../www-includes/dbcx.php");
    $dbh = dbcx();
          
    $email = trim($_POST['email']);
    $pass = $_POST['pass'];
            
    $sql = "SELECT password, username FROM users WHERE email = :email";
    $stmt = $dbh->prepare($sql);
    $stmt->bindParam(":email", $email);
    $stmt->execute();
    $dbcollected = $stmt->fetch();
    
    if ($dbcollected['password'] != crypt($pass, $dbcollected['password'])) {
        $success = "false"; // Wrong email/pass
    }
    else {
	    $username = htmlentities($dbcollected['username']);
    }
}
else {
	$success = "false";
}

echo "{'success' : '{$success}', 'username' : '{$username}'}";
?>