var html = '<div class="layui-upload-drag layui-col-md-offset1 div11" id="shangchuan">\n' +
    '        <div style="margin-top: 15%">\n' +
    '              <i class="layui-icon">$#xe67c;</i>\n' +
    '              <p>点击上传，或将文件拖拽到此处</p>\n' +
    '        </div>\n' +
    '    </div>';
upload.render();
var index1 = layer.open({
    type: 1,
    titile: '文件上传',
    content: ${'#xiaoming'},
    area: ['50%', '70%'],
    closeBtn: 1,
    btn: ['关闭'],
    btnAlign: 'c',
    yes: function () {
        layer.close(index1);
    },
    success: function () {
        uploadFile(currentRow);
    }
});
 function uploadFile(currentRow) {
    //
     upload.render({
         elem: '#shangchuan',
         url: '/*/*/*',
         data: {"id": currentRow.id},
         accept: ['file'],
         exts: 'pdf',
         before: function () {
             layer.load();
             console.log(currentRow.id);
         },
         done: function (res) {

         },
         error: function (XMLHttpRequest) {
             layer.alert("错误提示：" + XMLHttpRequest.status + ":" + XMLHttpRequest.responseText, {iicon: 0});
             layer.closeAll('loading');
         }
     });
}