<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title><?= e($title) ?> - Billing System</title>
    <link rel="stylesheet" href="style.css">
</head>
<body>
<header class="topbar">
    <h1>Billing System</h1>
    <nav>
        <a href="home.php">Home</a>
        <a href="add_item.php">Add Item</a>
        <a href="update_item.php">Update Item</a>
        <a href="delete_item.php">Delete Item</a>
        <a href="report.php">Report</a>
        <a href="logout.php">Logout</a>
    </nav>
</header>
<main class="container">
    <h2><?= e($title) ?></h2>
    <?php if (!empty($_SESSION['flash'])): ?>
        <div class="alert <?= e($_SESSION['flash'][0]) ?>"><?= e($_SESSION['flash'][1]) ?></div>
        <?php unset($_SESSION['flash']); ?>
    <?php endif; ?>
    <?php if (!empty($errors)): ?>
        <div class="alert error">
            <ul>
                <?php foreach ($errors as $error): ?>
                    <li><?= e($error) ?></li>
                <?php endforeach; ?>
            </ul>
        </div>
    <?php endif; ?>
