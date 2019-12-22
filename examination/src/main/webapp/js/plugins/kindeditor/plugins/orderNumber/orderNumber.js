KindEditor.plugin('orderNumber', function(K) {
        var editor = this, name = 'orderNumber';
        // 点击图标时执行
        editor.clickToolbar(name, function() {
                editor.insertHtml('orderNumber');
        });
});
