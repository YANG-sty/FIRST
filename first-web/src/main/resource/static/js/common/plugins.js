var FIRST = {
    ctxPath: "",
    addContextPath: function (ctx) {
        if (this.ctxPath == "") {
            this.ctxPath = ctx;
        }
    },
    sessionTimeoutRegistry: function () {
        $.ajaxSetup({
            contentType: "application/x-www-form-urlencoded; charset=utf-8",
            complete: function (XMLHttpRequest, textStatus) {
                //通过XMLHttpRequest取得响应头，sessionstatus
                var sessionstatus = XMLHttpRequest.getResponseHeader("sessionstatus");
                if (sessionstatus == "timeout") {
                    layer.msg("无权限或用户身份已过期，请重新登录。。。。。", {
                        icon: 7,
                        time: 2000 //2秒关闭，默认是3秒
                    }, function () {
                        //如果超时，跳转到指定页面
                        if (window.parent) {
                            window.parent.location.href = FIRST.ctxPath + "/login";
                        } else {
                            window.parent.href = FIRST.ctxPath + "/login";
                        }
                    });
                }

            }
        });
    }
};