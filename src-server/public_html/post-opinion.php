<?php
// TODO: currently not checking wether the actual issue is a legit one

$success = "true";

if (isset($_POST['email']) && isset($_POST['pass']) && isset($_POST['issue']) && isset($_POST['opinion'])) {
    
    require_once("../../www-includes/dbcx.php");
    $dbh = dbcx();
          
    $email = trim($_POST['email']);
    $pass = $_POST['pass'];
	$issue = trim($_POST['issue']);
	$opinion = trim($_POST['opinion']);
            
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