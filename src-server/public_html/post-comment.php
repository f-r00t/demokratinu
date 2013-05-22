<?php

$success = "true";

if (isset($_POST['email']) && isset($_POST['pass']) && isset($_POST['parentId']) && isset($_POST['content'])) {
    
    require_once("../../www-includes/dbcx.php");
    $dbh = dbcx();
          
    $email = trim($_POST['email']);
    $pass = $_POST['pass'];
    $parentId = trim($_POST['parentId']);
    $content = trim($_POST['content']);
            
    $sql = "SELECT password FROM users WHERE email = :email";
    $stmt = $dbh->prepare($sql);
    $stmt->bindParam(":email", $email);
    $stmt->execute();
    $dbcollected = $stmt->fetch();
    
    if ($dbcollected['password'] != crypt($pass, $dbcollected['password'])) {
        $success = "false"; // Wrong email/pass
    }
    else {
        $sqlParentId = $parentId . "%";
    
        $sql = "SELECT * FROM comments WHERE id LIKE :parentId";
        $stmt = $dbh->prepare($sql);
        $stmt->bindParam(":parentId", $sqlParentId);
        $stmt->execute();
        
        $pattern = '/(\/\d?$)/';
        $siblings = 0;
        while ($dbcollected = $stmt->fetch()) {
            if (preg_replace($pattern, "", $dbcollected['id']) == $parentId) {
                $siblings++;
            }
        }
        $id = $parentId . "/" . $siblings;
    
        $sql = "INSERT INTO comments (id, author, content, date) VALUES (:id, :author, :content, NOW())";
        $stmt = $dbh->prepare($sql);
        $stmt->bindParam(":id", $id);
        $stmt->bindParam(":author", $email);
        $stmt->bindParam(":content", $content);
        $stmt->execute();
    }
}
else {
    $success = "false";
}

echo "{'success' : '" . $success . "'}";

?>