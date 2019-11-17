<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="format-detection" content="telephone=no">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <meta content="产品,试剂,医疗,北方所" name="Keywords">
    <meta name="description"
          content="北方所创立于1985年，隶属于中国核工业集团有限公司旗下的中国同辐股份有限公司（中国同辐，1763．HK），专注于体外诊断试剂产品的研发、生产、经营及服务，产品涵盖放射免疫、化学发光、酶联免疫、时间分辨、胶体金和生物原材料等技术平台，拥有150多项产品注册证书，形成了覆盖全国的营销网络，产品出口至亚非欧等10余个国家或地区，能够为体外诊断领域提供全面的产品解决方案和整体服务。">
    <title>活动报名</title>

    <link rel="shortcut icon" href="/favicon.ico" type="image/x-icon">
    <link rel="stylesheet" type="text/css" href="/css/bootstrap.min.css">
    <link href="/css/toastr.min.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="/css/wechat/signup.css">
    <!--[if lt IE 9]>
    <script src="https://cdn.bootcss.com/html5shiv/3.7.3/html5shiv-printshiv.min.js"></script>
    <script src="/js/respond.min.js"></script>
    <![endif]-->
</head>
<body data-content="${(errorStatus)!''}">
<div class="signup">
    <#if activity??>
        <div class="banner">
            <img src="${(activity.bannerList[0].resourceUrl)!''}${(activity.bannerList[0].params)!''}" alt="">
        </div>
    </#if>
    <#if signup?? && signup == "done">
        <div class="activity-container">
            <div class="activity-title">
                活动已报名，不可重复报名
            </div>
        </div>
    <#elseif activity.expireTime?date gt .now?date>
        <div class="activity-container">
            <div class="activity-title">
                请确认参加本次活动
            </div>
            <div class="activity-infor">
                <#--<p>-->
                <#--昵称: ${(userName)!'无'}-->
                <#--</p>-->
                <p>
                    <span style="font-weight: 700;">绑定手机</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${(phone)!'无'}
                </p>
            </div>
            <div class="signup-field">
                <label for="realName" class="signup-field-label"><span>*</span>真实姓名</label>
                <div class="signup-field-input">
                    <input type="text" class="form-control" id="realName" placeholder="真实姓名">
                </div>
            </div>
            <div class="signup-field">
                <label for="companyName" class="signup-field-label"><span>*</span>所在公司</label>
                <div class="signup-field-input">
                    <input type="text" class="form-control" id="companyName" placeholder="所在公司">
                </div>
            </div>
            <div class="signup-field">
                <label for="position" class="signup-field-label"><span>*</span>公司职位</label>
                <div class="signup-field-input">
                    <input type="text" class="form-control" id="position" placeholder="公司职位">
                </div>
            </div>
            <div class="signup-field">
                <label for="department" class="signup-field-label"><span>*</span>所在部门</label>
                <div class="signup-field-input">
                    <input type="text" class="form-control" id="department" placeholder="所在部门">
                </div>
            </div>
            <div class="signup-field">
                <label for="email" class="signup-field-label"><span>*</span>邮&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;箱</label>
                <div class="signup-field-input">
                    <input type="email" class="form-control" id="email" placeholder="邮箱">
                </div>
            </div>
            <div class="activity-confirm">
                <button type="submit" class="btn">确定报名</button>
            </div>
        </div>
    <#else>
        <div class="activity-container">
            <div class="activity-title">
                活动已过期
            </div>
        </div>
    </#if>
    <#if activity??>
        <p class="signup-other-infor">
            活动结束时间: ${activity.expireTime?string('yyyy-MM-dd')}
        </p>
    </#if>
</div>

<script src="/js/jquery.min.js"></script>
<script src="/js/jquery.cookie.js"></script>
<script src="/js/bootstrap.min.js"></script>
<script src="/js/wechat/jweixin-1.4.0.js"></script>
<script src="/js/toastr.min.js"></script>
<script src="/js/base.js"></script>
<script src="/js/wechat/signup.js"></script>
</body>
</html>