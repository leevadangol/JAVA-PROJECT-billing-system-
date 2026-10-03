<?php
session_start();
header('Location: ' . (isset($_SESSION['username']) ? 'home.php' : 'login.php'));
exit;
