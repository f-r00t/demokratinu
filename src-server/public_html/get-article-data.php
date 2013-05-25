<?php

$success = "true";
$article = "";
$cp = array();
$replies = array();
$replyStrings = array();

function addReply($parent, $child) {
    $pattern = '/\'replies\'\s\:\s\[(.?)\]/';
    preg_match($pattern, $parent, $match);
    if ($match[1] != "") {
        return $parent = preg_replace($pattern, ("'replies' : [{$child}, {$match[1]}]"), $parent);
    }
    return $parent = preg_replace($pattern, ("'replies' : [{$child}]"), $parent);
    
}

if (isset($_POST['email']) && isset($_POST['pass']) && isset($_POST['article'])) {
    
    require_once("../../www-includes/dbcx.php");
    $dbh = dbcx();
          
    $email = trim($_POST['email']);
    $pass = $_POST['pass'];
    $article = trim($_POST['article']);
            
    $sql = "SELECT password FROM users WHERE email = :email";
    $stmt = $dbh->prepare($sql);
    $stmt->bindParam(":email", $email);
    $stmt->execute();
    $dbcollected = $stmt->fetch();
    
    if ($dbcollected['password'] != crypt($pass, $dbcollected['password'])) {
        $success = "false"; // Wrong email/pass
    }
    else {
    $article = "{$article}_%";
        $sql = "SELECT * FROM comments WHERE id LIKE :article";
        $stmt = $dbh->prepare($sql);
        $stmt->bindParam(":article", $article);
        $stmt->execute();
        
        while ($dbcollected = $stmt->fetch()) {       


    $replies[$dbcollected['id']] = array("numberOfLikes" => 0,
                                            "numberOfDislikes" => 0,
                                            "ownOpinion" => 0,
                                            "author" => $dbcollected['author'],
                                            "content" => $dbcollected['content'],
                                            "replies" => "[]");
       
 }
        
    
        $sql = "SELECT * FROM votes WHERE issue LIKE :article";
        $stmt = $dbh->prepare($sql);
        $stmt->bindParam(":article", $article);
        $stmt->execute();
        
        while($dbcollected = $stmt->fetch()) {
            if (strpos($dbcollected['issue'], '#') !== false) {
                if (!array_key_exists($dbcollected['issue'], $cp)) {
                    $cp[$dbcollected['issue']] = array("numberOfLikes" => 0,
                                                  "numberOfDislikes" => 0,
                                                  "ownOpinion" => 0);
                }
            
                if ($dbcollected['opinion'] == "1") {
                    $cp[$dbcollected['issue']]['numberOfLikes']++;
                }
                else if ($dbcollected['opinion'] == "-1") {
                    $cp[$dbcollected['issue']]['numberOfDislikes']++;
                }
                if ($dbcollected['voter'] == $email) {
                    $cp[$dbcollected['issue']]['ownOpinion'] = $dbcollected['opinion'];
                }
            }
            else if (strpos($dbcollected['issue'], '/') !== false && !empty($replies[$dbcollected['issue']])) {
                if ($dbcollected['opinion'] == "1") {
                    $replies[$dbcollected['issue']]['numberOfLikes']++;
                }
                else if ($dbcollected['opinion'] == "-1") {
                    $replies[$dbcollected['issue']]['numberOfDislikes']++;
                }
                if ($dbcollected['voter'] == $email) {
                    $replies[$dbcollected['issue']]['ownOpinion'] = $dbcollected['opinion'];
                }
            }
        }
    }
}
else {
    $success = "false";
}

echo "{'success' : '{$success}'";

if ($success == "true") {
    echo ", 'cpmap' : {";
    
    $numberOfItems = count($cp);
    $i = 0;
    
    foreach ($cp as $key => $value) {
        $numberOfInnerItems = count($value);
        $j = 0;
        
        echo "'" . htmlentities($key) . "' : {";
        foreach ($value as $key2 => $value2) {
            echo "'{$key2}' : {$value2}";
            if (++$j != $numberOfInnerItems) {
                echo ", ";
            }
        }
        
        echo "}";
        if (++$i != $numberOfItems) {
            echo ", ";
        }
    }
    
    echo "}, 'replies' : [";
    
    $numberOfItems = count($replies);
    $i = 0;
    
    $firstPattern = '/^' . trim($_POST['article']) . '\/\d?$/';
    $lastPattern = '/(\/\d?$)/';
    
    foreach ($replies as $key => $value) {
        $replyStrings[$key] = "{'author' : '{$value['author']}', 'content' : '{$value['content']}', 'replies' : [],
                                'nbrOfDislikes' : {$value['numberOfDislikes']}, 'nbrOfLikes' : {$value['numberOfLikes']},
                                'opinion' : {$value['ownOpinion']}, 'isHidden' : false}";
    }
    
    array_reverse($replyStrings);
    
    foreach ($replyStrings as $key => $value) {
        if (!preg_match($firstPattern, $key)) {
            $keyParent = preg_replace($lastPattern, "", $key);
            $replyStrings[$keyParent] = addReply($replyStrings[$keyParent], $replyStrings[$key]);
        }
    }
    $i = 0;
    array_reverse($replyStrings);
    foreach ($replyStrings as $key => $value) {
        if (preg_match($firstPattern, $key)) {
if ($i > 0) {
echo ", ";
}  
          echo $replyStrings[$key];
$i++;
        }
    }
    
    echo "]";
}
echo "}";

?>
