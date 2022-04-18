
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>请假申请</title>
    <link rel="stylesheet" href="/resources/layui/css/layui.css">
    <style>
        /*表单容器*/
        .ns-container {
            position: absolute;
            width: 500px;
            height: 450px;
            top: 150px;
            left: 50%;
            margin-left: -250px;
            padding: 20px;
            box-sizing: border-box;
            border: 1px solid #cccccc;
        }
    </style>
</head>
<body>
<div class="layui-row">
    <blockquote class="layui-elem-quote">
        <h2>请假申请</h2>
    </blockquote>
    <table id="grdNoticeList" lay-filter="grdNoticeList"></table>
</div>
<div class="ns-container">
    <h1 style="text-align: center;margin-bottom: 20px">请假申请单</h1>
    <form class="layui-form">
        <!--基本信息-->
        <div class="layui-form-item">
            <label class="layui-form-label">部门</label>
            <div class="layui-input-block">
                <div class="layui-col-md12" style="padding-top: 10px;">
                    ${current_department.departmentName}
                </div>

            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">申请人</label>
            <div class="layui-input-block">
                <div class="layui-col-md12" style="padding-top: 10px;">
                    ${current_employee.name}[${current_employee.title}]
                </div>

            </div>
        </div>
        <!--请假类型下拉框-->
        <div class="layui-form-item">
            <label class="layui-form-label">请假类别</label>
            <div class="layui-input-block layui-col-space5">
                    <select name="formType" lay-verify="required" lay-filter="cityCode">
                        <option value="1">事假</option>
                        <option value="2">病假</option>
                        <option value="3">工伤假</option>
                        <option value="4">婚嫁</option>
                        <option value="5">产假</option>
                        <option value="6">丧假</option>
                    </select>
            </div>
        </div>
        
        <!--请假时长日期选择框-->
        <div class="layui-form-item">
            <label class="layui-form-label">请假时长</label>
            <div class="layui-input-block layui-col-space5">
                    <input name="leaveRange" type="text" class="layui-input" id="daterange" placeholder=" - ">
                    <input id="startTime" name="startTime" type="hidden">
                    <input id="endTime" name="endTime" type="hidden">
            </div>
        </div>

        <!--请假事由-->
        <div class="layui-form-item">
            <label class="layui-form-label">请假事由</label>
            <div class="layui-input-block layui-col-space5">
                    <input name="reason" type="text"  lay-verify="required|mobile" placeholder="" autocomplete="off" class="layui-input">
            </div>
        </div>

        <!--提交按钮-->
        <div class="layui-form-item " style="text-align: center">
                <button class="layui-btn" type="button" lay-submit lay-filter="sub">立即申请</button>
        </div>
    </form>
</div>

<script src="/resources/layui/layui.all.js"></script>
<script src="/resources/sweetalert2.all.min.js"></script>

<script>

        var layDate = layui.laydate; //Layui日期选择框JS对象
        var layForm = layui.form; //layui表单对象
        var $ = layui.$; //jQuery对象
        //日期时间范围
        layDate.render({
            elem: '#daterange'
            ,type: 'datetime'
            ,range: true
            ,format: 'yyyy年M月d日H时'
            ,done: function(value, start, end){
                //选择日期后出发的时间,设置startTime与endTime隐藏域
                var startTime = start.year + "-" + start.month + "-" + start.date + "-" + start.hours;
                var endTime = end.year + "-" + end.month + "-" + end.date + "-" + end.hours;
                console.info("请假开始时间",startTime);
                $("#startTime").val(startTime);
                console.info("请假结束时间",endTime);
                $("#endTime").val(endTime);
            }
        });

        //表单提交事件
        layForm.on('submit(sub)', function(data){
            console.info("向服务器提交的表单数据",data.field);
            $.post("/leave/create",data.field,function (json) {
                console.info("服务器返回数据",json);
                if(json.code == "0"){
                    /*SweetAlert2确定对话框*/
                    swal({
                        type: 'success',
                        html: "<h2>请假单已提交,等待上级审批</h2>",
                        confirmButtonText: "确定"
                    }).then(function (result) {
                        window.location.href="/forward/notice";
                    });
                    // window.location.href = "/forward/notice";
                    // layui.layer.msg(json.message);
                }else{
                    // layui.layer.msg(json.message);
                    swal({
                        type: 'warning',
                        html: "<h2>" + json.message + "</h2>",
                        confirmButtonText: "确定"
                    });
                }
            },"json");
            return false;
        });

</script>
</body>
</html>