<?php
require 'config.php';
require_login();

$errors = [];
$data = ['product_name' => '', 'description' => '', 'quantity' => '', 'price' => ''];

if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    [$errors, $data] = validate_product($_POST);
    if (!$errors) {
        $stmt = $pdo->prepare('INSERT INTO products (product_name, description, quantity, price) VALUES (?, ?, ?, ?)');
        $stmt->execute([$data['product_name'], $data['description'], $data['quantity'], $data['price']]);
        $_SESSION['flash'] = ['success', 'Product added successfully.'];
        header('Location: add_item.php');
        exit;
    }
}

$title = 'Add Item';
include 'header.php';
?>
<form method="post" class="form">
    <label>Product Name</label>
    <input type="text" name="product_name" value="<?= e($data['product_name']) ?>">

    <label>Description</label>
    <input type="text" name="description" value="<?= e($data['description']) ?>">

    <label>Quantity</label>
    <input type="text" name="quantity" value="<?= e($data['quantity']) ?>">

    <label>Price</label>
    <input type="text" name="price" value="<?= e($data['price']) ?>">

    <div class="actions">
        <button type="submit" class="btn btn-green">Add</button>
        <a class="btn btn-gray" href="add_item.php">Clear</a>
        <a class="btn btn-blue" href="home.php">Back</a>
    </div>
</form>
<?php include 'footer.php'; ?>
