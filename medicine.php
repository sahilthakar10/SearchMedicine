<?php

$link = mysqli_connect('localhost','root','','demo');

if(isset($_REQUEST['name']))
{
	$name = mysqli_real_escape_string($link , $_REQUEST['name']);

	$query = "SELECT * FROM `abc` WHERE medicine LIKE '$name%'";

	$output = array();
	
	if($query_run = mysqli_query($link , $query))
	{
		if(mysqli_affected_rows($link)>0)
		{
			while($row = mysqli_fetch_assoc($query_run))
			{
				$output[] = $row;
			}
		}	
		echo json_encode($output);
	}
}

?>