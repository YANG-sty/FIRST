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