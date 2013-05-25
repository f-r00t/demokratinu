<?php

$success = "true";

if (isset($_POST['email']) && isset($_POST['pass']) && isset($_POST['moprosition'])) {
    
    require_once("../../www-includes/dbcx.php");
    $dbh = dbcx();
          
    $email = trim($_POST['email']);
    $pass = $_POST['pass'];
    $article = trim($_POST['moprosition']);
            
    $sql = "SELECT password FROM users WHERE email = :email";
    $stmt = $dbh->prepare($sql);
    $stmt->bindParam(":email", $email);
    $stmt->execute();
    $dbcollected = $stmt->fetch();
    
    if ($dbcollected['password'] != crypt($pass, $dbcollected['password'])) {
        $success = "false"; // Wrong email/pass
    }
    else {
        $sql = "SELECT * FROM votes WHERE issue = :moprosition";
        $stmt = $dbh->prepare($sql);
        $stmt->bindParam(":moprosition", $moprosition);
        $stmt->execute();
        
        $moprositionOpinions = new array("totalLikes" => 0,
                                          "totalDislikes" => 0,
                                          "ownOpinion" => 0);
        
        while ($stmt->fetch()) {
            if ($dbcollected['voter'] == $email) {
                $moprositionOpinons['ownOpinion'] = $dbcollected['opinion'];
            }
            if ($dbcollected[opinion] == "1") {
                $moprositionOpinions['totalLikes']++;
            }
            else if ($dbcollected[opinion] == "1") {
                $moprositionOpinions['totalDisikes']++;
            }
        }
    }
}
else {
    $success = "false";
}

echo "{'success' : '{$success}'";

if ($success == "true") {
    for ($moprositionOpinions as $key => $value) {
        echo ", '{$key}' : {$value}";
    }
}
echo "}";

?>
