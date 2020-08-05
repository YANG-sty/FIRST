$(document).ready(function(){

    showAllOrgsOfAgent();

});


function showAllOrgsOfAgent(){

    var obj = document.getElementById("dlrt");
    var btn = $("#btn");
    var btnSq = $("#btnSq");
    var total_height = obj.scrollHeight;
    var show_height = 145;
    if(total_height>show_height){
        btn.css("display","block");
        $("#btn").click(function(){
            $(".dlrzbox").height(600);
            btn.css("display","none");
            btnSq.css("display","block");
        });
        $("#btnSq").click(function(){
            $(".dlrzbox").height(195);
            btn.css("display","block");
            btnSq.css("display","none");
        });
    }
}

$(function () {
    //第一次加载，空数据表格加载
    var property = initDatagrid();
    $('#id').datagrid(property);
    $('#id').datagrid('loadData', {total: 0, row: []});
});

function chushihua() {
    //第二次加载，传参加载
    var property = initDatagrid();
    property.url = 'xxxxxxxxxxx'; //路径

    //传参方式一
    // var param = $('#searchForm').serializeObject();
    // $('#id').datagrid('load', param);

    //传参方式二
    property.queryParams = {"name": anme, "age": age};

    $('#id').datagrid(property);
}


function initDatagrid() {
    var property = {
        pagination: true,
        rownumbers: true,
        singleSelect: true,
        fitColumns: true,
        method: 'post',
        dataType: 'json',
        pageSize: 10,
        pageList: [20, 40, 80],
        loadMsg: '正在加载数据...',
        toolbar: '#toobar',
        onLoadError: function () {
            $(this).datagrid('loadData', {total: 0, row: []});
            $.messager.alert('提示', '加载失败，请重试！！！', 'info');
            return;
        },
        columns: [[
            {field: 'code', title: 'Code', align: 'center'},
            {field: 'name', title: 'Name', align: 'center'},
            {field: 'price', title: 'Price', align: 'center'},
            {
                field: 'suitable', title: 'Suitable', align: 'center', formatter: function (value, row, index) {
                    //进行一些操作
                }
            }
        ]],
        onClickRow: oneClick //当用户点击一行时触发
    };

    return property;

}

function oneClick(rowindex, rowData) {
    //rowIndex：被点击行的索引，从 0 开始
    // rowData：被点击行对应的记录
}