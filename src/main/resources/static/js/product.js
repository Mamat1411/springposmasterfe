function openForm() {
    $.ajax({
        url: '/product/form',
        type: 'get',
        contentType: 'html',
        success: function (productForm) {
            $('#myModal').modal('show');
            $('.modal-title').html("Product Form");
            $('.modal-body').html(productForm);
        }
    });
}

function editForm(id) {
    $.ajax({
        url: '/product/edit/' + id,
        type: 'get',
        contentType: 'html',
        success: function (productForm) {
            $('#myModal').modal('show');
            $('.modal-title').html("Edit Product");
            $('.modal-body').html(productForm);
        }
    });
}

function deleteForm(id) {
    $.ajax({
        url: `/product/delete/${id}`,
        type: `get`,
        contentType: `html`,
        success: function (productForm) {
            $('#myModal').modal('show');
            $('.modal-title').html("Delete Product");
            $('.modal-body').html(productForm);
        }
    })
}

function deleteProduct(id) {
    $.ajax({
        url: `/product/remove/${id}`,
        type: `get`,
        contentType: `html`,
        success: function (productForm) {
            location.reload();
        }
    })
}