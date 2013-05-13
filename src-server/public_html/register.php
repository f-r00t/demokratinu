<?php

// TODO: Check values for valid emails/usernames/passwords

//header("HTTP/1.0 404 Not Found"); // Makes this not show up in google searches etc

if (isset($_GET['email']) && isset($_GET['user']) && isset($_GET['pass'])) {
	
	require_once("dbcx.php");
	/*
    $dbh = dbcx();
          
	$email = trim($_GET['email']);
	$username = trim($_GET['user']);
    $pass = $_GET['pass'];	  
	
	$sql = "SELECT username, email FROM users";
	$stmt = $dbh->prepare($sql);
    $stmt->execute();
	
	while($dbcollected = $stmt->fetch()) {
        if ($dbcollected['username'] == $username) {
			$status = "0";
		}
		if ($dbcollected['email'] == $email) {
			$status = "0";
		}
	}
	
    if ($status != "0") {
		$salt = uniqid('', true);
        $pass = crypt($pass, '$6$' . $salt . '$');
		
		$sql = "INSERT INTO users (username, email, password, date)
		        VALUES (:username, :email, :password, NOW())";
				
		$stmt = $dbh->prepare($sql);
		$stmt->bindParam(":username", $username);
		$stmt->bindParam(":email", $email);
		$stmt->bindParam(":password", $pass);
		$stmt->execute();
		
		echo "1"; // Success
	}
	else {
		echo "0"; // Either the username or email was already taken
	}*/
}
else {
	echo "Error"; // Should never happen
}
?>