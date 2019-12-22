KindEditor.plugin('memberName', function(K) {
        var editor = this, name = 'memberName';
        // 点击图标时执行
        editor.clickToolbar(name, function() {
                editor.insertHtml('memberName');
        });
});
