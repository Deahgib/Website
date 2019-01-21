
<!DOCTYPE html>
<html>
<head>
    <title><?php echo "Four Fours"; ?></title>
	<meta charset="utf-8" />
</head>
<body>


<h1>Four Fours</h1>
</br>

<?php
/**
 * Simple example of extending the SQLite3 class and changing the __construct
 * parameters, then using the open method to initialize the DB.
 */


$db = new SQLite3('/usr/FourFoursServer/game-results.db');

$result = $db->query('SELECT * FROM results');


echo "<table>";
echo "<tr><th> Target </th> <th> Solution </th></tr>";
while ($row = $result->fetchArray()) {
    
	echo "<tr>";
	echo "<td>{$row["target"]}</td>";
	echo "<td>{$row["solution"]}</td>";
	echo "</tr>";
}
echo "</table>";

?>

</body>
</html>
