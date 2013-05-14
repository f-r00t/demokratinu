<?php
header("HTTP/1.0 404 Not Found"); // Makes this not show up in google searches etc

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
        echo "0"; // Wrong email/pass
    }
    else {
	    echo "1"; // Success
    }
}
else {
	echo "Error"; // Should never happen
}
?>