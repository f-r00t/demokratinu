<?php
if (isset($_GET['user']) && isset($_GET['pass']) {
	
	require_once("../includes/dbcx.php");
    $dbh = dbcx();
          
    $username = trim($_GET['user']);
    $pass = $_GET['pass'];
            
    $sql = "SELECT password FROM users WHERE userid = :username";
    $stmt = $dbh->prepare($sql);
    $stmt->bindParam(":username", $username);
    $stmt->execute();
    $dbcollected = $stmt->fetch();
    if ($dbcollected['password'] != crypt($pass, $dbcollected['password'])) {
        
    }
    else {
	
    }
}
?>