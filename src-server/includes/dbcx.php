<?php
$settings = array(
	'phptype'  => 'mysql',
    'hostspec' => 'localhost',
    'database' => '',
    'username' => '',
    'password' => ''
);

function dbcx() {
	$dsnstr = "{$settings['phptype']}:host={$settings['hostspec']};dbname={$settings['database']}";
    $dbuser = $settings['username'];
    $dbpass = $settings['password'];
	
	$db = new PDO($dsnstr, $dbuser, $dbpass);
	
	$db->setAttribute(PDO::ATTR_DEFAULT_FETCH_MODE, PDO::FETCH_ASSOC);

	$charset_sql = "SET NAMES 'UTF8' COLLATE 'utf8_swedish_ci'";
    $db->query($charset_sql);

	$dbtime = get_setting('dbtime');
    $ts_sql = "SET time_zone = '$dbtime'";
    $svar   = $db->query($ts_sql);
}
?>