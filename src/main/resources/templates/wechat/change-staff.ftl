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
    <title>更换销售</title>

    <link rel="shortcut icon" href="/favicon.ico" type="image/x-icon">
    <link rel="stylesheet" type="text/css" href="/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="/css/login.css">
    <link rel="stylesheet" type="text/css" href="/css/wechat/signup-list.css">
    <link rel="stylesheet" type="text/css" href="/css/wechat/change-staff.css">
    <!--[if lt IE 9]>
    <script src="https://cdn.bootcss.com/html5shiv/3.7.3/html5shiv-printshiv.min.js"></script>
    <script src="/js/respond.min.js"></script>
    <![endif]-->
</head>
<body data-content="${(errorStatus)!''}">
<div class="signup-list ${(staffList?? && staffList?size gt 0)?string("", "none")}">
    <div class="list-search navbar-fixed-top">
        <div class="search-input-container">
            <input type="text" placeholder="搜索销售昵称">
        </div>
        <button class="btn" role="button">搜索</button>
    </div>
    <div class="list-container">
        <#if staffList?? && staffList?size gt 0>
            <#list  staffList as user>
                <div class="signup-item" data-content="${user.id}/${user.username}">
                    <div class="item-banner">
                        <img src="${(user.avatarUrl)!'http://img.bnibt.com/static/offical.png'}" alt="">
                    </div>
                    <div class="item-detail">
                        <div class="item-detail-name">
                            <p class="item-detail-title">
                                ${user.username}
                            </p>
                            <p class="item-detail-other">
                                地区：${(user.districtName)!'无'}
                            </p>
                        </div>
                        <div class="item-detail-phone">
                            <p class="item-detail-title">
                                ${user.phone}
                            </p>
                            <p class="item-detail-other">
                                &nbsp;
                            </p>
                        </div>
                    </div>
                </div>
            </#list>
        </#if>
    </div>
    <div class="list-none">
        <img src="http://img.bnibt.com/static/img_none.png" alt="">
        <p>抱歉，没有搜到哟</p>
    </div>
    <#if page??>
        <div class="signup-more ${(page.totalPage == page.page)?string("none", "")}">
            <div class="signup-none">
                <p>没有更多了</p>
            </div>
            <div class="signup-hadmore"
                 data-content="${page.page?c}/${page.totalPage?c}/${page.limit?c}/${page.totalItem?c}">
                点击加载更多
            </div>
        </div>
    </#if>
</div>
<!-- 更改销售确认框 -->
<div class="modal fade" id="changeModal" tabindex="-1" role="dialog" aria-labelledby="changeModalLabel">
    <div class="modal-dialog modal-sm" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title" id="changeModalLabel">更换销售</h4>
            </div>
            <div class="modal-body">
                <p></p>
            </div>
            <div class="modal-footer">
                <button class="btn change-cancel" data-dismiss="modal" aria-label="Close">取消</button>
                <button data-content="${(staffId)!''}" class="btn change-confirm" data-dismiss="modal"
                        aria-label="Close">确定
                </button>
            </div>
        </div>
    </div>
</div>


<script src="/js/jquery.min.js"></script>
<script src="/js/jquery.cookie.js"></script>
<script src="/js/bootstrap.min.js"></script>
<script src="/js/wechat/jweixin-1.4.0.js"></script>
<script src="/js/base.js"></script>
<script src="/js/wechat/change-staff.js"></script>
</body>
</html>