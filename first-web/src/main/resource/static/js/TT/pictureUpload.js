
//上传
var uploadInst = upload.render({
    elem: '#test1' //绑定元素
    ,url: '/upload/' //上传接口
    ,accept: 'file' //允许上传的文件类型
    ,size: 50 //最大允许上传的文件大小
    ,exts: 'png|jpg|jpeg'
    ,before: function (obj) { // 图片上传回调
        obj.preview(function (index, picture, result) {
            $('#picture').attr("src", result);
        });
    }
    ,done: function(res){
        //上传完毕回调
        if(res.code === 200){
            $('#pictureID').attr("value", res.data);
            layer.msg("成功！！");
        }
    }
    ,error: function(){
        //请求异常回调
    }
});


// 修改的时候
layer.open({
    type: 1
    ,titel: "测试图片上传"
    ,content: $('#pictureDivID') // 可以添加另外的页面 通过 div
    ,area: ['40%','50%']
    ,btnAlign: 'c' //居中
    ,btn: ['按钮一', '按钮二', '按钮三']
    ,yes: function(index, layero){
        //按钮【按钮一】的回调
    }
    ,btn2: function(index, layero){
        //按钮【按钮二】的回调

        //return false 开启该代码可禁止点击该按钮关闭
    }
    ,cancel: function(){
        //右上角关闭回调

        //return false 开启该代码可禁止点击该按钮关闭
    }
    ,success: function () {
        //将数据填充到，form 表单

        $('#picture').attr("src", "图片链接地址");

    }
});


