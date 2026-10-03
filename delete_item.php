<?php
require 'config.php';
require_login();

if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    $id = (int)($_POST['id'] ?? 0);
    if ($id <= 0) {
        $_SESSION['flash'] = ['error', 'Please select a product to delete.'];
    } else {
        $stmt = $pdo->prepare('DELETE FROM products WHERE id = ?');
        $stmt->execute([$id]);
        $_SESSION['flash'] = ['success', 'Product deleted successfully.'];
    }
    header('Location: delete_item.php');
    exit;
}

$products = $pdo->query('SELECT * FROM products ORDER BY id')->fetchAll();

$title = 'Delete Item';
include 'header.php';
?>
<table>
    <tr><th>ID</th><th>Product Name</th><th>Description</th><th>Quantity</th><th>Price</th><th>Action</th></tr>
    <?php foreach ($products as $p): ?>
        <tr>
            <td><?= e($p['id']) ?></td>
            <td><?= e($p['product_name']) ?></td>
            <td><?= e($p['description']) ?></td>
            <td><?= e($p['quantity']) ?></td>
            <td><?= e($p['price']) ?></td>
            <td>
                <form method="post" onsubmit="return confirm('Are you sure you want to delete this product?');">
                    <input type="hidden" name="id" value="<?= (int)$p['id'] ?>">
                    <button type="submit" class="btn btn-red small">Delete</button>
                </form>
            </td>
        </tr>
    <?php endforeach; ?>
    <?php if (!$products): ?>
        <tr><td colspan="6" class="center">No products found.</td></tr>
    <?php endif; ?>
</table>
<div class="actions"><a class="btn btn-blue" href="home.php">Back</a></div>
<?php include 'footer.php'; ?>
