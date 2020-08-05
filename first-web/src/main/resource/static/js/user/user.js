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
function quanxuan(number) {
    /* 判断该id 的复选框是否被选中, 做全选的时候使用 */
    var checked = $("#id"+number).is(":checked");
    if (checked) {
        //由于每一行都存在一个复选框，所以这里不能使用 按照name 属性进行设置，通过父属性唯一的id 来确定是哪一行的复选框
        $("#ch" + number).children().children().attr("checked", "true");
    } else {
        $("#ch" + number).children().children().removeAttr("checked", "true");
    }

}


var val = "苹果,香蕉,橘子";
var split = val.split(",");
/* 在数据加载的时候，通过这种方式，将后天传递过来的数据进行选中*/
for (var i = 0; i < split.length; i++) {
    var string = split[i];
    switch (string) {
        case '苹果':
            $("#shuiGuo" + number).children().children("input[value='苹果']").attr("checked", "checked");
            break;
        case '香蕉':
            $("#shuiGuo" + number).children().children("input[value='香蕉']").attr("checked", "checked");
            break;
        case '橘子':
            $("#shuiGuo" + number).children().children("input[value='橘子']").attr("checked", "checked");
            break;
    }
}

// 苹果复选框的点击操作，其他的复选框也需要进行这样的操作，尝试过将 fucntion() 抽取出来，但是这样一来页面的数据展示会出现问题，
$("#ch" + number).children().children("input[value='苹果']").click(function () {
    var arrShuiGuo = "";
    //获得该行复选框所有的选中数据
    var jQuery = $("#ch"+number).children().children(":checkbox:checked");
    jQuery.each(function () {
        var val1 = $this.val();
        arrShuiGuo += val1;
        arrShuiGuo += ",";
    });
    // 将值赋给 隐藏的输入框里面，后台获取数据的时候，从该输入框获得数据，而不用从复选框获得数据，
    //看了一下复选框传递的数据，将该列所有选中的数据形成了一个数组，无法分清是哪一行的数据。
    $("#" + number).attr("value", arrShuiGuo);

});




var editRow=null;
$('#result_grid').datagrid({
    url:"http://localhost:8082/springmvc2/hello/queryPlayer.do",
    fitColumns:true,//宽度自适应
    height: 280,
    rownumbers:true,
    nowrap:true,
    pagination:true,
    pageNumber:1,
    pageSize:10,
    pageList:[10,20,30],
    onClickRow: function (rowIndex, rowData) {
        $("#result_grid").datagrid('selectRow', rowIndex);
        $("#result_grid").datagrid('beginEdit', rowIndex);//设置可编辑状态
    },
    onBeforeEdit:function(index,row){
        editRow=row;
        row.editing = true;
    },
    onAfterEdit:function(index, row, changes){
        $('#result_grid').datagrid('updateRow',{
            index: index,
            row: {
                occupation: parseInt(row.occupation),
                cause: row.cause
            }
        });
        datagridMgr.addRow(row);
        row.editing = false;
    },
    onCancelEdit:function(index,row){
        row.editing = false;
    },
    columns:[[

        {field: 'id', checkbox:true,width:60},
        {field:'name',title:'名字',width:150},
        {field:'age',title:'年龄',width:150},
        {field:'sex',title:'性别',width:150,formatter:function(value,row,index){
                var result='';
                switch(value){
                    case 0:
                        result='女';
                        break;
                    case 1:
                        result='男';
                        break;
                }
                return result;
            }},
        {field:'occupation',title:'职业(可编辑)',width:150,
            formatter:function(value,row,index){
                var result='';
                switch(parseInt(value)){
                    case 1:
                        result='教师';
                        break;
                    case 2:
                        result='工程师';
                        break;
                }
                return result;
            },
            editor : {
                type : 'combobox',
                options : {
                    editable:false,
                    valueField:'code',
                    textField:'text',
                    url:"http://localhost:8082/springmvc2/hello/occupation.do"

                }
            }

        },
        {field:'cause',title:'参赛原因(可编辑)',width:150,
            editor : {
                type : 'text'
            }

        },
        {field:'creatTime',title:'报名时间',width:150}

    ]],
    toolbar: '#grid-toolbar'
});




function exportDate(){
    //获得form 表单的参数
    var param = $('#serache_form').serializeObject();
    console.log(param);

    $.messager.confirm('提示','确认导出？',function (r) {
        if (r) {
            //拼接url 请求
            var url = "xxxxxAction.do?opt=export&s_name=" + param.s_name + "&s_age=" + param.s_age + "&s_phone" + param.s_phone;
            //在本身的窗口打开新的网页链接
            var win = window.open(url, "_self");
            win.location.href = url;
        }
    });
}
