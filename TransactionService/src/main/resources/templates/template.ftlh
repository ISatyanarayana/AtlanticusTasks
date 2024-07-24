<!DOCTYPE html>
<html>
<head>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 0;
        }
        .container {
            width: 100%;
            max-width: 600px;
            margin: 0 auto;
            background-color: #ffffff;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            padding: 20px;
            border-radius: 8px;
        }
        .header {
            background-color: #007BFF;
            color: #ffffff;
            padding: 10px 20px;
            border-radius: 8px 8px 0 0;
            text-align: center;
        }
        .content {
            padding: 20px;
        }
        .transaction-details {
            border: 1px solid #e0e0e0;
            border-radius: 4px;
            padding: 15px;
            margin: 20px 0;
            background-color: #f9f9f9;
        }
        .transaction-details p {
            margin: 5px 0;
        }
        .footer {
            text-align: center;
            padding: 10px;
            font-size: 0.9em;
            color: #777777;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="header">
            <h1>Transaction Details</h1>
        </div>
        <div class="content">
                  <p>Hello ${name},</p>
                  <p>Your transaction details are as follows:</p>
                  <div class="transaction-details">
                      <p><strong>Transaction ID:</strong> ${transaction.transactionId}</p>
                      <p><strong>Amount:</strong> ${transaction.amount}</p>
                      <p><strong>Credited:</strong> ${transaction.credited?string("Yes", "No")}</p>
                      <p><strong>Description:</strong> ${transaction.description}</p>
                      <p><strong>Transaction Date:</strong> ${transaction.createdDate}</p>
                  </div>
                  <p>Thank you!</p>
              </div>
        <div class="footer">
            <p>&copy; 2024 Your Company. All rights reserved.</p>
        </div>
    </div>
</body>
</html>
