<?php
require 'config.php';
require_login();
$title = 'Home';
include 'header.php';
?>
<p>Welcome, <strong><?= e($_SESSION['username']) ?></strong>. Choose an option:</p>
<div class="cards">
    <a class="card blue" href="add_item.php">Add Item</a>
    <a class="card blue" href="update_item.php">Update Item</a>
    <a class="card red" href="delete_item.php">Delete Item</a>
    <a class="card green" href="report.php">Report</a>
    <a class="card gray" href="logout.php">Logout</a>
</div>
<?php include 'footer.php'; ?>
