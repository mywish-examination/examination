KindEditor.plugin('productName', function(K) {
        var editor = this, name = 'productName';
        // 点击图标时执行
        editor.clickToolbar(name, function() {
                editor.insertHtml('productName');
        });
});
