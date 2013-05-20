<?php

// TODO: Check values for valid emails/usernames/passwords

$success = "true";
$userExists = "false";
$emailExists = "false";

if (isset($_POST['email']) && isset($_POST['user']) && isset($_POST['pass'])) {

	require_once("../../www-includes/dbcx.php");
    $dbh = dbcx();

	$email = trim($_POST['email']);
	$username = trim($_POST['user']);
    $pass = $_POST['pass'];	  
	
	$sql = "SELECT username, email FROM users";
	$stmt = $dbh->prepare($sql);
    $stmt->execute();
	
	while($dbcollected = $stmt->fetch()) {
        if ($dbcollected['username'] == $username) {
			$usernameExists = "true";
			$success = "false";
		}
		if ($dbcollected['email'] == $email) {
			$emailExists = "true";
			$success = "false";
		}
	}
	
    if ($success != "false") {
		$salt = uniqid('', true);
        $pass = crypt($pass, '$6$' . $salt . '$');
		
		$sql = "INSERT INTO users (username, email, password, date)
		        VALUES (:username, :email, :password, NOW())";
				
		$stmt = $dbh->prepare($sql);
		$stmt->bindParam(":username", $username);
		$stmt->bindParam(":email", $email);
		$stmt->bindParam(":password", $pass);
		$stmt->execute();
	}
}
else {
    $success = "0";
}

echo "{'success' : '{$success}', 'emailExists' : '{$emailExists}', 'userExists' : '{$userExists}'}"
?>
