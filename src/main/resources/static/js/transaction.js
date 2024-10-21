let selectedProducts = [];
let totalPrice = 0;

function getOrderHeader() {
    let dateNow = Date.now();
    // $.ajax({
    //     url: 'http://localhost:9001/api/orderheader',
    //     type: 'post',
    //     contentType: 'application/json',
    //     success: function (orderHeader) {
    //         $('#orderHeaderId').val(orderHeader.data.id);
    //     }
    // })
    $('#reference').val(dateNow);

    $('#newOrder').prop('disabled', false);
    $('#newTransaction').prop('disabled', true);
}

function newOrder() {
    $.ajax({
        url: '/transaction/getproductvariants',
        type: 'get',
        contentType: 'html',
        success: function (variantForm) {
            $('#myModal').modal('show');
            $('.modal-title').html("Select New Order");
            $('.modal-body').html(variantForm);
        }
    })
}

function newPayment() {
    $.ajax({
        url: '/transaction/payment',
        type: 'get',
        contentType: 'html',
        success: function (paymentForm) {
            $('#myModal').modal('show');
            $('.modal-title').html("Payment");
            $('.modal-body').html(paymentForm);
            $('#paymentRef').val($('#reference').val());
            $('#bill').val(totalPrice);
        }
    })
}

function saveNewOrder(id) {
    let jsonData = {
        price: $('#price_' + id).val(),
        variantId: id,
        headerId: $('#orderHeaderId').val(),
        quantity: 1,
        variantName: $('#variant_' + id).val()
    }

    if (selectedProducts.length == 0) {
        selectedProducts.push(jsonData);
    } else {
        let selected = selectedProducts.find(product => product.variantId === jsonData.variantId);
        if (selected && jsonData.variantId === selected.variantId) {
            selected.quantity += 1;
        } else {
            selectedProducts.push(jsonData);
        }
    }
    console.log(selectedProducts);
    showSelectedItems();

    // $.ajax({
    //     url:`http://localhost:9001/api/orderdetail/${id}`,
    //     type: 'post',
    //     data:JSON.stringify(jsonData),
    //     contentType: 'application/json',
    //     success: function (orderDetail) {
    //         console.log(orderDetail);
    //     }
    // })
}

function showSelectedItems() {
    let number = 1;
    let tbody = ``;
    let totalQty = 0;
    totalPrice = 0;
    let total = 0;
    selectedProducts.forEach(product => {
        total = product.quantity * product.price;
        tbody += `
                    <tr>
                        <th>${number++}</th>
                        <td>
                            <input type="text" class="form-control" value="${product.variantName}" readonly>
                        </td>
                        <td>
                            <input type="text" class="form-control" id="price" value="${product.price}" readonly>
                        </td>
                        <td>
                            <input type="text" class="form-control" id="quantity" value="${product.quantity}" readonly>
                        </td>
                        <td>
                            <input type="text" class="form-control" id="total" value="${total}" readonly>
                        </td>
                        <td class="text-center">
                            <button class="btn btn-danger" onclick="deleteProduct(${product.variantId})"><i class="bi bi-x-circle"></i> Delete</button>
                        </td>
                    </tr>
                `
        totalPrice += total;
    });
    $('#orderDetailData').html(tbody);
    console.log(totalPrice);


    selectedProducts.forEach(product => {
        totalQty += +product.quantity;
    });

    let tfoot = `
                    <tr>
                        <th colspan="3">
                            Total
                        </th>
                        <th>
                            <input type="text" class="form-control" value="${totalQty}" readonly>
                        </th>
                        <th>
                            <input type="text" class="form-control" value="${totalPrice}" readonly>
                        </th>
                        <th></th>
                    </tr>
                `;
    $('#orderDetailTotal').html(tfoot);
    $('#newPayment').prop('disabled', false);
    if (selectedProducts.length < 1) {
        $('#newPayment').prop('disabled', true);
        $('#orderDetailTotal').empty();
        totalQty = 0;
        totalPrice = 0;
        total = 0;
    }
}

function deleteProduct(id) {
    let selected = selectedProducts.filter((product => product.variantId != id));
    selectedProducts = selected;
    showSelectedItems();
}

function calculatePayment(payment) {
    let bill = $('#bill').val();
    let change = payment - bill;
    $('#change').val(change);
}