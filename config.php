<?php
session_start();

$host = 'localhost';
$db   = 'billing_system';
$user = 'root';
$pass = '';

set_exception_handler(function ($ex) {
    http_response_code(500);
    echo 'Database error. Check that MySQL is running in XAMPP and that billing_system.sql was imported.';
});

try {
    $pdo = new PDO("mysql:host=$host;dbname=$db;charset=utf8mb4", $user, $pass, [
        PDO::ATTR_ERRMODE            => PDO::ERRMODE_EXCEPTION,
        PDO::ATTR_DEFAULT_FETCH_MODE => PDO::FETCH_ASSOC,
    ]);
} catch (PDOException $ex) {
    die('Cannot connect to MySQL. Start MySQL in the XAMPP Control Panel and import billing_system.sql.');
}

/** Safely print text in HTML. */
function e($text) {
    return htmlspecialchars((string)$text, ENT_QUOTES, 'UTF-8');
}

/** Stop users who are not logged in. */
function require_login() {
    if (empty($_SESSION['username'])) {
        header('Location: login.php');
        exit;
    }
}

/** Length of text in characters (works even if mbstring is off). */
function text_length($text) {
    return function_exists('mb_strlen') ? mb_strlen($text) : strlen($text);
}

/** Validate the product form. Returns [errors, cleanedData]. */
function validate_product($post) {
    $errors = [];
    $name  = trim($post['product_name'] ?? '');
    $desc  = trim($post['description'] ?? '');
    $qty   = trim($post['quantity'] ?? '');
    $price = trim($post['price'] ?? '');

    if ($name === '') {
        $errors[] = 'Product Name cannot be empty.';
    } elseif (text_length($name) > 100) {
        $errors[] = 'Product Name must be 100 characters or less.';
    }
    if ($desc === '') {
        $errors[] = 'Description cannot be empty.';
    } elseif (text_length($desc) > 255) {
        $errors[] = 'Description must be 255 characters or less.';
    }
    if (!ctype_digit($qty) || (int)$qty <= 0) {
        $errors[] = 'Quantity must be a whole number greater than 0.';
    }
    if (!preg_match('/^\d+(\.\d{1,2})?$/', $price) || (float)$price <= 0) {
        $errors[] = 'Price must be a valid number greater than 0 (example: 49.99).';
    } elseif ((float)$price > 99999999.99) {
        $errors[] = 'Price is too large.';
    }

    return [$errors, [
        'product_name' => $name,
        'description'  => $desc,
        'quantity'     => (int)$qty,
        'price'        => $price,
    ]];
}
