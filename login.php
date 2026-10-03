<?php
require 'config.php';

if (!empty($_SESSION['username'])) {
    header('Location: home.php');
    exit;
}

$error = '';
$username = '';

if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    $username = trim($_POST['username'] ?? '');
    $password = $_POST['password'] ?? '';

    if ($username === '') {
        $error = 'Please enter your username.';
    } elseif ($password === '') {
        $error = 'Please enter your password.';
    } else {
        $stmt = $pdo->prepare('SELECT id FROM users WHERE username = ? AND password = ?');
        $stmt->execute([$username, hash('sha256', $password)]);
        if ($stmt->fetch()) {
            session_regenerate_id(true);
            $_SESSION['username'] = $username;
            header('Location: home.php');
            exit;
        }
        $error = 'Incorrect username or password.';
    }
}
?>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Login</title>
    <link rel="stylesheet" href="style.css">
</head>
<body class="login-page">
    <div class="login-card">
        <h2>Login</h2>
        <?php if ($error): ?>
            <div class="alert error"><?= e($error) ?></div>
        <?php endif; ?>
        <form method="post" autocomplete="off">
            <input type="text" name="username" placeholder="Enter Username" value="<?= e($username) ?>">
            <input type="password" name="password" placeholder="Enter Password">
<button type="submit" class="btn btn-blue" style="width:100%; margin-top:18px; padding:12px; font-size:16px; background:#2962a8;">Login</button>        </form>
    </div>
</body>
</html>
