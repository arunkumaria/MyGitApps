<!DOCTYPE html>
<html>
<head>
    <title>Invoice</title>
    <style>
        body { font-family: Arial, sans-serif; }
        table { width: 100%; border-collapse: collapse; }
        th, td { border: 1px solid #333; padding: 8px; }
        th { background-color: #f2f2f2; }
    </style>
</head>
<body>

<h2>Invoice</h2>

<p><b>Invoice No:</b> ${invoice.invoiceNumber}</p>
<p><b>Customer:</b> ${invoice.customerName}</p>

<table>
    <tr>
        <th>Product</th>
        <th>Quantity</th>
        <th>Price</th>
    </tr>

    <#list invoice.items as item>
        <tr>
            <td>${item.productName}</td>
            <td>${item.quantity}</td>
            <td>${item.price}</td>
        </tr>
    </#list>
</table>

<h3>Total Amount: ₹ ${invoice.totalAmount}</h3>

</body>
</html>
