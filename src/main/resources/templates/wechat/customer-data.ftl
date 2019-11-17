<#import '../common/language.ftl' as Language>
<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="format-detection" content="telephone=no">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <meta content="产品,试剂,医疗,北方所" name="Keywords">
    <meta name="description" content="北方所创立于1985年，隶属于中国核工业集团有限公司旗下的中国同辐股份有限公司（中国同辐，1763．HK），专注于体外诊断试剂产品的研发、生产、经营及服务，产品涵盖放射免疫、化学发光、酶联免疫、时间分辨、胶体金和生物原材料等技术平台，拥有150多项产品注册证书，形成了覆盖全国的营销网络，产品出口至亚非欧等10余个国家或地区，能够为体外诊断领域提供全面的产品解决方案和整体服务。">
    <title>客户资料</title>

    <link rel="shortcut icon" href="/favicon.ico" type="image/x-icon">
    <link rel="stylesheet" type="text/css" href="/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="/css/login.css">
    <link rel="stylesheet" type="text/css" href="/css/wechat/customer-data.css">
    <!--[if lt IE 9]>
    <script src="https://cdn.bootcss.com/html5shiv/3.7.3/html5shiv-printshiv.min.js"></script>
    <script src="/js/respond.min.js"></script>
    <![endif]-->
</head>
<body data-content="${(errorStatus)!''}">
<div class="customer-data">
    <#if user??>
        <div class="data-container" data-content="${user.id}">
            <div class="data-item">
                <p class="data-label">
                    客户姓名:
                </p>
                <p class="data-value">
                    ${(user.username)!'客户'}
                </p>
            </div>
            <div class="data-item">
                <p class="data-label">
                    客户电话:
                </p>
                <p class="data-value">
                    ${(user.phone)!'无'}
                </p>
            </div>
            <div class="data-item">
                <p class="data-label">
                    客户位置:
                </p>
                <p class="data-value">
                    ${(user.location)!'无'}
                </p>
            </div>
            <div class="data-item">
                <p class="data-label">
                    客户状态:
                </p>
                <p class="data-value">
                    首次登录
                </p>
            </div>
            <div class="data-item">
                <p class="data-label">
                    上次登录时间:
                </p>
                <p class="data-value">
                    无
                </p>
            </div>
            <div class="data-item">
                <p class="data-label">
                    客户公司名:
                </p>
                <p class="data-value">
                    ${(user.company)!''}
                </p>
            </div>
            <div class="data-control">
                <a class="btn staff" href="/wx/changestaff.html?id=${user.id}">更换销售</a>
                <button class="btn company" role="button" data-toggle="modal" data-target="#changeCompanyModal">修改公司名</button>
            </div>
        </div>
    </#if>
</div>
<!-- 修改公司名弹窗 -->
<div class="modal fade" id="changeCompanyModal" tabindex="-1" role="dialog" aria-labelledby="changeCompanyModalLabel">
    <div class="modal-dialog modal-sm" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">关闭</span></button>
                <h4 class="modal-title" id="changeCompanyModalLabel">修改公司名</h4>
            </div>
            <div class="modal-body">
                <input type="text" placeholder="请输入公司名称">
            </div>
            <div class="modal-footer">
                <button class="btn" data-dismiss="modal" aria-label="Close">确认提交</button>
            </div>
        </div>
    </div>
</div>
<script src="/js/jquery.min.js"></script>
<script src="/js/jquery.cookie.js"></script>
<script src="/js/bootstrap.min.js"></script>
<script src="/js/wechat/jweixin-1.4.0.js"></script>
<script src="/js/base.js"></script>
<script src="/js/wechat/customer-data.js"></script>
</body>
</html>