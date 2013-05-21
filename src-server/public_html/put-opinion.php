<?php
// TODO: currently not checking wether the actual issue is a legit one

$success = "true";

if (isset($_GET['email']) && isset($_GET['pass']) && isset($_GET['issue']) && isset($_GET['opinion'])) {
    
    require_once("../../www-includes/dbcx.php");
    $dbh = dbcx();
          
    $email = trim($_GET['email']);
    $pass = $_GET['pass'];
	$issue = trim($_GET['issue']);
	$opinion = trim($_GET['opinion']);
            
    $sql = "SELECT password FROM users WHERE email = :email";
    $stmt = $dbh->prepare($sql);
    $stmt->bindParam(":email", $email);
    $stmt->execute();
    $dbcollected = $stmt->fetch();
    
    if ($dbcollected['password'] != crypt($pass, $dbcollected['password'])) {
        $success = "false"; // Wrong email/pass
    }
	else if ($opinion != "1" && $opinion != "0" && $opinion != "-1") {
		$success = "false"; // Shouldn't happen, someone has manipulated the client
	}
    else {
		$sql = "SELECT * FROM votes WHERE issue = :issue";
		$stmt = $dbh->prepare($sql);
		$stmt->bindParam(":issue", $issue);
		$stmt->execute();
		$dbcollected = $stmt->fetch();
	
		if (empty($dbcollected)) {
			$sql = "INSERT INTO votes (voter, issue, opinion) VALUES (:email, :issue, :opinion)";
			$stmt = $dbh->prepare($sql);
			$stmt->bindParam(":issue", $issue);
			$stmt->bindParam(":email", $email);
			$stmt->bindParam(":opinion", $opinion);
			$stmt->execute();
		}
		else {
			$sql = "UPDATE votes SET opinion = :opinion WHERE voter = :email AND issue = :issue";
			$stmt = $dbh->prepare($sql);
			$stmt->bindParam(":issue", $issue);
			$stmt->bindParam(":email", $email);
			$stmt->bindParam(":opinion", $opinion);
			$stmt->execute();
		}
	
        $sql = "SELECT * FROM votes WHERE issue LIKE :agenda";
        $stmt = $dbh->prepare($sql);
        $stmt->bindParam(":agenda", $agenda);
        $stmt->execute();
		$dbcollected = $stmt->fetch();
    }
}
else {
    $success = "false";
}

echo "{'success' : '" . $success . "'}";

?>