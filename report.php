<?php
require 'config.php';
require_login();

$products = $pdo->query('SELECT product_name, description, quantity, price FROM products ORDER BY id')->fetchAll();

$title = 'Product Report';
include 'header.php';
?>
<table>
    <tr><th>Product Name</th><th>Description</th><th>Quantity</th><th>Price</th></tr>
    <?php foreach ($products as $p): ?>
        <tr>
            <td><?= e($p['product_name']) ?></td>
            <td><?= e($p['description']) ?></td>
            <td><?= e($p['quantity']) ?></td>
            <td><?= e($p['price']) ?></td>
        </tr>
    <?php endforeach; ?>
    <?php if (!$products): ?>
        <tr><td colspan="4" class="center">No products found.</td></tr>
    <?php endif; ?>
</table>
<p><strong>Total products: <?= count($products) ?></strong></p>
<div class="actions">
    <a class="btn btn-green" href="report.php">Refresh</a>
    <a class="btn btn-blue" href="home.php">Back</a>
</div>
<?php include 'footer.php'; ?>
