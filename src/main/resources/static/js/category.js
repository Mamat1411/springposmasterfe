function openForm() {
    $.ajax({
        url: '/category/form',
        type: 'get',
        contentType: 'html',
        success: function (categoryForm) {
            $('#myModal').modal('show');
            $('.modal-title').html("Category Form");
            $('.modal-body').html(categoryForm);
        }
    });
}

function editForm(id) {
    $.ajax({
        url: '/category/edit/' + id,
        type: 'get',
        contentType: 'html',
        success: function (categoryForm) {
            $('#myModal').modal('show');
            $('.modal-title').html("Edit Category");
            $('.modal-body').html(categoryForm);
        }
    });
}

function deleteForm(id) {
    $.ajax({
        url: `/category/delete/${id}`,
        type: `get`,
        contentType: `html`,
        success: function (categoryForm) {
            $('#myModal').modal('show');
            $('.modal-title').html("Delete Category");
            $('.modal-body').html(categoryForm);
        }
    })
}

function deleteCategory(id) {
    $.ajax({
        url: `/category/remove/${id}`,
        type: `get`,
        contentType: `html`,
        success: function (categoryForm) {
            location.reload();
        }
    })
}