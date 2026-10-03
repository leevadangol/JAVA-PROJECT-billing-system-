<?php
require 'config.php';
require_login();

$errors = [];
$edit = null;

if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    $id = (int)($_POST['id'] ?? 0);
    if ($id <= 0) {
        $errors[] = 'Please select a product from the table first.';
    } else {
        [$errors, $data] = validate_product($_POST);
        $edit = ['id' => $id] + $data;
        if (!$errors) {
            $stmt = $pdo->prepare('UPDATE products SET product_name = ?, description = ?, quantity = ?, price = ? WHERE id = ?');
            $stmt->execute([$data['product_name'], $data['description'], $data['quantity'], $data['price'], $id]);
            $_SESSION['flash'] = ['success', 'Product updated successfully.'];
            header('Location: update_item.php');
            exit;
        }
    }
} elseif (isset($_GET['id'])) {
    $stmt = $pdo->prepare('SELECT * FROM products WHERE id = ?');
    $stmt->execute([(int)$_GET['id']]);
    $edit = $stmt->fetch() ?: null;
}

$products = $pdo->query('SELECT * FROM products ORDER BY id')->fetchAll();

$title = 'Update Item';
include 'header.php';
?>
<table>
    <tr><th>ID</th><th>Product Name</th><th>Description</th><th>Quantity</th><th>Price</th><th>Select</th></tr>
    <?php foreach ($products as $p): ?>
        <tr<?= ($edit && (int)$edit['id'] === (int)$p['id']) ? ' class="selected"' : '' ?>>
            <td><?= e($p['id']) ?></td>
            <td><?= e($p['product_name']) ?></td>
            <td><?= e($p['description']) ?></td>
            <td><?= e($p['quantity']) ?></td>
            <td><?= e($p['price']) ?></td>
            <td><a href="update_item.php?id=<?= (int)$p['id'] ?>">Edit</a></td>
        </tr>
    <?php endforeach; ?>
    <?php if (!$products): ?>
        <tr><td colspan="6" class="center">No products found.</td></tr>
    <?php endif; ?>
</table>

<form method="post" class="form">
    <input type="hidden" name="id" value="<?= e($edit['id'] ?? '') ?>">

    <label>Product Name</label>
    <input type="text" name="product_name" value="<?= e($edit['product_name'] ?? '') ?>">

    <label>Description</label>
    <input type="text" name="description" value="<?= e($edit['description'] ?? '') ?>">

    <label>Quantity</label>
    <input type="text" name="quantity" value="<?= e($edit['quantity'] ?? '') ?>">

    <label>Price</label>
    <input type="text" name="price" value="<?= e($edit['price'] ?? '') ?>">

    <div class="actions">
        <button type="submit" class="btn btn-green">Update</button>
        <a class="btn btn-gray" href="update_item.php">Clear</a>
        <a class="btn btn-blue" href="home.php">Back</a>
    </div>
</form>
<?php include 'footer.php'; ?>
