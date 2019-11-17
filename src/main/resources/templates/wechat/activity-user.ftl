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
        <title>活动中心</title>

        <link rel="shortcut icon" href="/favicon.ico" type="image/x-icon">
        <link rel="stylesheet" type="text/css" href="/css/bootstrap.min.css">
        <link rel="stylesheet" type="text/css" href="/css/wechat/activity.css">
        <!--[if lt IE 9]>
        <script src="https://cdn.bootcss.com/html5shiv/3.7.3/html5shiv-printshiv.min.js"></script>
        <script src="/js/respond.min.js"></script>
        <![endif]-->
    </head>
    <body data-content="${(errorStatus)!''}">
        <div class="activity">
            <#if activityList?? && activityList?size gt 0>
                <div class="activity-list">
                    <#list activityList as activity>
                        <a href="${Language.lans[lan].url_path}news/${activity.id}.html">
                            <div class="activity-item">
                                <div class="item-banner">
                                    <img src="${(activity.bannerList[0].resourceUrl)!''}${(activity.bannerList[0].params)!''}" alt="">
                                </div>
                                <div class="item-infor">
                                    <h2 class="item-infor-title">
                                        ${(activity.title)!''}
                                    </h2>
                                    <p class="item-infor-detail">
                                        ${(activity.brief)!''}
                                    </p>
                                </div>
                                <#if activity.campaignStatus?? && activity.campaignStatus == "checkin">
                                    <img class="banner-status checkin" src="http://img.bnibt.com/static/mark.png" alt="">
                                <#elseif activity.campaignStatus?? && activity.campaignStatus == "signup">
                                    <img class="banner-status signup" src="http://img.bnibt.com/static/reporder.png" alt="">
                                </#if>
                            </div>
                        </a>
                    </#list>
                </div>
                <div class="activity-more ${(page.totalPage == page.page)?string("none", "")}">
                    <div class="activity-none">
                        <p>没有更多了</p>
                    </div>
                    <div class="activity-hadmore" data-content="${page.page?c}/${page.totalPage?c}/${page.limit?c}/${page.totalItem?c}">
                        点击加载更多
                    </div>
                </div>
            <#else>
                <div class="activity-nodata">
                    <img src="http://img.bnibt.com/static/img_none.png" alt="">
                    <p>暂无相关内容</p>
                </div>
            </#if>
        </div>
        <script src="/js/jquery.min.js"></script>
        <script src="/js/jquery.cookie.js"></script>
        <script src="/js/bootstrap.min.js"></script>
        <script src="/js/wechat/jweixin-1.4.0.js"></script>
        <script src="/js/base.js"></script>
        <script src="/js/wechat/activity.js"></script>
    </body>
</html>