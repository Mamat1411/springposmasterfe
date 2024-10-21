function openForm() {
    $.ajax({
        url: '/variant/form',
        type: 'get',
        contentType: 'html',
        success: function (variantForm) {
            $('#myModal').modal('show');
            $('.modal-title').html("Variant Form");
            $('.modal-body').html(variantForm);
        }
    });
}

function editForm(id) {
    $.ajax({
        url: '/variant/edit/' + id,
        type: 'get',
        contentType: 'html',
        success: function (variantForm) {
            $('#myModal').modal('show');
            $('.modal-title').html("Edit Variant");
            $('.modal-body').html(variantForm);
        }
    });
}

function deleteForm(id) {
    $.ajax({
        url: `/variant/delete/${id}`,
        type: `get`,
        contentType: `html`,
        success: function (variantForm) {
            $('#myModal').modal('show');
            $('.modal-title').html("Delete Variant");
            $('.modal-body').html(variantForm);
        }
    })
}

function deleteVariant(id) {
    $.ajax({
        url: `/variant/remove/${id}`,
        type: `get`,
        contentType: `html`,
        success: function () {
            location.reload();
        }
    })
}

function getProductByCategoryId(id) {
    $.ajax({
        url: `http://localhost:9001/api/product/category/${id}`,
        type: `get`,
        contentType: `application/json`,
        success: function (product) {
            product = product.data;
            products = ``;
            for (let i = 0; i < product.length; i++) {
                products += `<option value="${product[i].id}">${product[i].name}</option>`
            }
            $('#productId').html(products);
        }
    })
}