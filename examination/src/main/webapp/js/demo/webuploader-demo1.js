//id -> 存放图片  width，height  存放图片尺寸宽高  id2 按钮  allMaxSize图片大小
function picUpload(id,width,height,id2,allMaxSize){

    var $ = jQuery,
        $list = $(id),
        $btn = $('#uploadBtn'),
        state = 'pending',
    // 优化retina, 在retina下这个值是2
        ratio = window.devicePixelRatio || 1,

    // 缩略图大小
        thumbnailWidth = width * ratio,
        thumbnailHeight = height * ratio,

    // Web Uploader实例
        uploader;

    // 初始化Web Uploader
    uploader = WebUploader.create({
        // swf文件路径
        swf: 'E:/work/seller/public/img/common',

        // 文件接收服务端。
        server: 'http://webuploader.duapp.com/server/fileupload.php',

        // 选择文件的按钮。可选。
        // 内部根据当前运行是创建，可能是input元素，也可能是flash.
        pick: id2,

        duplicate :true,//是否可以重复上传

        fileSizeLimit: allMaxSize*1024*1024, //限制图片大小，不可以超过

        compress: null,

        // 只允许选择文件，可选。
        accept: {
            title: 'Images',
            extensions: 'gif,jpg,jpeg,bmp,png',
            mimeTypes: 'image/jpg,image/jpeg,image/png'   //修改这行，加快上传文件框的打开速度
        }
    });

    // 当有文件添加进来的时候
    uploader.on( 'fileQueued', function( file ) {
        $list.empty(); //清空之前上传的文件

        var $li = $(
                '<div id="' + file.id + '" class="file-item">' +
                '<div class="info">' + file.name + '</div>' +
                '</div>'
            );

        $list.append( $li );

        // 创建缩略图
        /*uploader.makeThumb( file, function( error, src ) {
            if ( error ) {
                $img.replaceWith('<span>不能预览</span>');
                return;
            }

            //去掉默认的头像，清空图片名字的信息
            $('.webuploader-pick img').attr('src','')
            //$('.file-item .info').empty();

            $img.attr( 'src', src );
        }, thumbnailWidth, thumbnailHeight );*/

    });

    // 文件上传过程中创建进度条实时显示。
    uploader.on( 'uploadProgress', function( file, percentage ) {
        var $li = $( '#'+file.id ),
            $percent = $li.find('.progress span');

        // 避免重复创建
        if ( !$percent.length ) {
            $percent = $('<p class="progress"><span></span></p>')
                .appendTo( $li )
                .find('span');
        }

        $percent.css( 'width', percentage * 100 + '%' );
    });

    // 文件上传成功，给item添加成功class, 用样式标记上传成功。
    uploader.on( 'uploadSuccess', function( file ) {
        $( '#'+file.id ).addClass('upload-state-done');
    });

    // 文件上传失败，现实上传出错。
    uploader.on( 'uploadError', function( file ) {
        var $li = $( '#'+file.id ),
            $error = $li.find('div.error');

        // 避免重复创建
        if ( !$error.length ) {
            $error = $('<div class="error"></div>').appendTo( $li );
        }

        //$error.text('上传失败');

    });

    // 完成上传完了，成功或者失败，先删除进度条。
    uploader.on( 'uploadComplete', function( file ) {
        $( '#'+file.id ).find('.progress').remove();
    });

    //  验证大小
    uploader.on("error",function (type){
         if(type == "Q_EXCEED_SIZE_LIMIT"){
            window.alert("系统提示:所选图片总大小不可超过" + allMaxSize);
        }
    });
	uploader.on( 'all', function( type ) {
        if ( type === 'startUpload' ) {
            state = 'uploading';
        } else if ( type === 'stopUpload' ) {
            state = 'paused';
        } else if ( type === 'uploadFinished' ) {
            state = 'done';
        }

        if ( state === 'uploading' ) {
            $btn.text('暂停上传');
        } else {
            $btn.text('开始上传');
        }
    });
 	$btn.on( 'click', function() {
        if ( state === 'uploading' ) {
            uploader.stop();
        } else {
            uploader.upload();
        }
    });
}
