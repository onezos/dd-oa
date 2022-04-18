<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>DDNotes办公OA系统</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" href="/resources/layui/css/layui.css">
</head>

<body class="layui-layout-body">
<!-- Layui后台布局CSS -->
<div class="layui-layout layui-layout-admin">
    <!--头部导航栏-->
    <div class="layui-header

">
        <!--系统标题-->
        <div class="layui-logo" style="font-size:18px">DDNotes办公OA系统</div>
        <!--右侧当前用户信息-->
        <ul class="layui-nav layui-layout-right">
            <li class="layui-nav-item">
                <a href="javascript:void(0)">
                    <!--图标-->
                    <span class="layui-icon layui-icon-user" style="font-size: 20px">
                    </span>
                    <!--用户信息-->
                    ${current_employee.name}[${current_department.departmentName}-${current_employee.title}]
                </a>
            </li>
            <!--注销按钮-->
            <li class="layui-nav-item"><a href="/logout">注销</a></li>
        </ul>
    </div>
    <!--左侧菜单栏-->
    <div class="layui-side layui-bg-black">
        <!--可滚动菜单-->
        <div class="layui-side-scroll">
            <!--可折叠导航栏-->
            <ul class="layui-nav layui-nav-tree">
                <#list node_list as node>
                    <!--父节点-->
                    <#if node.nodeType == 1>
                        <li class="layui-nav-item layui-nav-itemed">
                            <a href="javascript:void(0)">${node.nodeName}</a>
                            <dl class="layui-nav-child module" data-node-id="${node.nodeId}"></dl>
                        </li>
                    </#if>
                    <!--子节点-->
                    <#if node.nodeType == 2>
                        <dd class="function" data-parent-id="${node.parentId}">
                            <a href="${node.url}" target="ifmMain">${node.nodeName}</a>
                        </dd>
                    </#if>
                </#list>
            </ul>
        </div>
    </div>
    <!--主体部分采用iframe嵌入其他页面-->
    <div class="layui-body" style="overflow-y: hidden">
        <iframe name="ifmMain" src="/forward/notice" style="border: 0px;width: 100%;height: 100%"></iframe>
    </div>
    <!--版权信息-->
    <div class="layui-footer">
        Copyright © ddnotes. All Rights Reserved.
    </div>
</div>
<!--LayUI JS文件-->
<script src="/resources/layui/layui.all.js"></script>
<script>
    //将所有功能根据parent_id移动到指定模块下
    layui.$(".function").each(function () {
        var func = layui.$(this);
        var parentId = func.data("parent-id");
        layui.$("dl[data-node-id=" + parentId + "]").append(func);
    })
    //刷新折叠菜单
    layui.element.render('nav');
</script>
</body>
</html>