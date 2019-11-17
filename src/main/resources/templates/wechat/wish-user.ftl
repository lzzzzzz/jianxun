<#import '../common/language.ftl' as Language>
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
    <title>TA的心愿单</title>

    <link rel="shortcut icon" href="/favicon.ico" type="image/x-icon">
    <link rel="stylesheet" type="text/css" href="/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="/css/wechat/wish-user.css">
    <!--[if lt IE 9]>
    <script src="https://cdn.bootcss.com/html5shiv/3.7.3/html5shiv-printshiv.min.js"></script>
    <script src="/js/respond.min.js"></script>
    <![endif]-->
</head>
<body data-content="${(errorStatus)!''}">
<div class="wish">
    <div class="wish-list">
        <#if wishList?? && wishList?size gt 0>
            <div class="wish-list-container">
                <#list wishList as wish>
                    <div class="wish-item">
                        <div class="wish-item-container">
                            <div class="wproduct-infor">
                                <div class="infor-container">
                                    <div class="wproduct-banner">
                                        <img src="${(wish.product.bannerList[0].resourceUrl)!''}${(wish.product.bannerList[0].params)!''}"
                                             alt="">
                                    </div>
                                    <div class="wproduct-detail">
                                        <h2 class="wproduct-title">
                                            ${(wish.product.title)!'无'}
                                        </h2>
                                        <p class="wproduct-other">型号：${(wish.product.modelName)!'无'}</p>
                                        <p class="wproduct-other">规格：${(wish.product.specifications)!'无'}</p>
                                        <p class="wproduct-other">代码：${(wish.product.goodsNum)!'无'}</p>
                                        <p class="wproduct-other">原产地：${(wish.product.originPlace)!'无'}</p>
                                        <#--<p class="wproduct-other">方法学：${(wish.product.methodology)!'无'}</p>-->
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="wish-item-control">
                            <p class="wish-time">
                                收藏时间：${wish.createdTime?string('yyyy-MM-dd hh:mm:ss')}
                            </p>
                        </div>
                    </div>
                </#list>
            </div>
            <#if page??>
                <div class="advisory-more  ${(page.totalPage == page.page)?string("none", "")}">
                    <div class="advisory-none">
                        <p>没有更多了</p>
                    </div>
                    <div class="advisory-hadmore"
                         data-content="${page.page?c}/${page.totalPage?c}/${page.limit?c}/${page.totalItem?c}">
                        点击加载更多
                    </div>
                </div>
            </#if>
        <#else>
            <div class="wish-none">
                <img src="http://img.bnibt.com/static/img_none.png" alt="">
                <p>暂无相关内容</p>
            </div>
        </#if>
    </div>
</div>
<script src="/js/jquery.min.js"></script>
<script src="/js/jquery.cookie.js"></script>
<script src="/js/bootstrap.min.js"></script>
<script src="/js/wechat/jweixin-1.4.0.js"></script>
<script src="/js/base.js"></script>
<script src="/js/wechat/wish-user.js"></script>
</body>
</html>