<#import 'common/base.ftl' as base>
<#import "common/language.ftl" as Language>
<@base.base_container
title="${Language.lans[lan].index_title}"
headers=[
"/css/swiper.css",
"/css/index.css"
]
scripts=[
"/js/swiper.min.js",
"/js/index.js"
]>
    <!------------------------- 轮播图 ----------------------->
    <div class="swiper-container">
        <div class="swiper-wrapper">
            <#list carousel as item>
                <div class="swiper-slide">
                    <a href="${item.action}" title="${item.title}">
                        <div class="banner-item ${(item_index == 0)?string("active", "")}">
                            <img class="hidden-xs" src="${item.banners}" alt="${item.title?html}">
                            <img class="visible-xs-block"
                                 src="${item.banners}?x-oss-process=image/crop,w_1243,h_497,g_center"
                                 alt="${item.title?html}">
                        </div>
                    </a>
                </div>
            </#list>
        </div>
        <div class="pagination-container"></div>
        <!-- Controls -->
        <button class="hidden-xs left carousel-control" role="button" data-slide="prev">
            <img class="glyphicon glyphicon-chevron-left" src="http://img.bnibt.com/static/l_arrow.png" alt="">
            <span class="sr-only">Previous</span>
        </button>
        <button class="hidden-xs right carousel-control" role="button" data-slide="next">
            <img class="glyphicon glyphicon-chevron-right" src="http://img.bnibt.com/static/r_arrow.png" alt="">
            <span class="sr-only">Next</span>
        </button>
    </div>
<#--<div class="container-fluid carousel-map">-->
<#--<div id="carousel-example-generic" class="carousel slide" data-ride="carousel" data-interval="10000">-->
<#--<!-- Indicators &ndash;&gt;-->
<#--<ol class="carousel-indicators">-->
<#--<#list carousel as item>-->
<#--<li data-target="#carousel-example-generic" data-slide-to="${item_index?c}"-->
<#--class="active">${(item_index + 1)?c}</li>-->
<#--</#list>-->
<#--</ol>-->

<#--<!-- Wrapper for slides &ndash;&gt;-->
<#--<div class="carousel-inner" role="listbox">-->
<#--<#list carousel as item>-->
<#--<div class="item  <#if item_index == 0>active</#if>">-->
<#--<a href="${item.action}" title="${item.title}">-->
<#--<img class="hidden-xs" src="${item.banners}" alt="${item.title?html}">-->
<#--<img class="visible-xs-block"-->
<#--src="${item.banners}?x-oss-process=image/crop,w_1243,h_497,g_center"-->
<#--alt="${item.title?html}">-->
<#--</a>-->
<#--</div>-->
<#--</#list>-->
<#--</div>-->

<#--<!-- Controls &ndash;&gt;-->
<#--<a class="left carousel-control" href="#carousel-example-generic" role="button" data-slide="prev">-->
<#--<img class="glyphicon glyphicon-chevron-left" src="http://img.bnibt.com/static/l_arrow.png" alt="">-->
<#--<span class="sr-only">Previous</span>-->
<#--</a>-->
<#--<a class="right carousel-control" href="#carousel-example-generic" role="button" data-slide="next">-->
<#--<img class="glyphicon glyphicon-chevron-right" src="http://img.bnibt.com/static/r_arrow.png" alt="">-->
<#--<span class="sr-only">Next</span>-->
<#--</a>-->
<#--</div>-->
<#--</div>-->

    <!------------------------- 主推新闻 ----------------------->
    <div class="news">
        <div class="container">
            <div class="news-title">
                <p>${Language.lans[lan].index_news_title}</p>
                <a href="${Language.lans[lan].url_path}news.html">
                    <div class="news-more">
                        <span>${Language.lans[lan].index_btn_more}</span>
                        <img src="http://img.bnibt.com/static/right_white.png" alt="">
                    </div>
                </a>
            </div>
            <div class="news-container">
                <#list news as item>
                    <a hidefocus="true" class="news-item" href="${Language.lans[lan].url_path}news/${item.id}.html"
                       title="${item.title?html}">
                        <div class="news-left">
                            <p class="news-name">${item.title?html}</p>
                            <p class="news-desc">${(item.brief)!''}</p>
                        </div>
                        <#if (item.bannerList)??>
                            <div class="news-right">
                                <img src="${(item.bannerList[0].resourceUrl)!''}${(item.bannerList[0].params?? && item.bannerList[0].params?length gt 0)?string(item.bannerList[0].params, '?x-oss-process=image')}/crop,w_186,h_177,g_center"
                                     alt="${item.title?html}">
                            </div>
                        </#if>
                    </a>
                </#list>
            </div>
        </div>
    </div>

    <!------------------------- 推荐产品 ----------------------->
    <div class="products">
        <div class="container">
            <div class="products-title">
                <p>${Language.lans[lan].index_products_title}</p>
                <a href="${Language.lans[lan].url_path}products.html">
                    <div class="products-more">
                        <span>${Language.lans[lan].index_btn_more}</span>
                        <img src="http://img.bnibt.com/static/right_white.png" alt="">
                    </div>
                </a>
            </div>
            <div class="product-items">
                <#list products as product>
                    <a hidefocus="true" title="${product.title?html}"
                       href="${Language.lans[lan].url_path}products/${product.id}.html" class="product-item">
                        <img src="${(product.bannerList[0].resourceUrl)!''}${(product.bannerList[0].params)!''}"
                             alt="${product.title?html}">

                        <p class="product-name">
                            ${product.title?html}
                        </p>
                    </a>
                </#list>
            </div>
        </div>
    </div>

    <!------------------------- 热门产品 ----------------------->
    <#if hotProductList?? && hotProductList?size gt 0>
        <div class="products-hot">
            <div class="container">
                <div class="products-title">
                    <p>${Language.lans[lan].index_hot_products_title}</p>
                    <a href="${Language.lans[lan].url_path}products.html">
                        <div class="products-more">
                            <span>${Language.lans[lan].index_btn_more}</span>
                            <img src="http://img.bnibt.com/static/right_white.png" alt="">
                        </div>
                    </a>
                </div>
                <div class="product-items">
                    <#list hotProductList as product>
                        <a hidefocus="true" title="${product.title?html}"
                           href="${Language.lans[lan].url_path}products/${product.id}.html" class="product-item">
                            <img src="${(product.bannerList[0].resourceUrl)!''}${(product.bannerList[0].params)!''}"
                                 alt="${product.title?html}">

                            <p class="product-name">
                                ${product.title?html}
                            </p>
                        </a>
                    </#list>
                </div>
            </div>
        </div>
    </#if>

    <!------------------------- 关于我们 ----------------------->
    <div class="aboutus">
        <div class="container">
            <div class="aboutus-title">
                <p>${Language.lans[lan].index_aboutus_title}</p>
                <a href="${Language.lans[lan].url_path}profile.html">
                    <div class="aboutus-more">
                        <span>${Language.lans[lan].index_btn_more}</span>
                        <img src="http://img.bnibt.com/static/right_white.png" alt="">
                    </div>
                </a>
            </div>
            <#if lan?? && lan == "en">
                <p class="aboutus-desc">
                    Beijing North Institute of Biotechnology Co.,Ltd (BNIBT) upholds the mission of "Focusing on in
                    vitro diagnosis, serving human health", and strives to be a top-ranking comprehensive provider in
                    vitro diagnostic products and services.
                </p>
                <p class="aboutus-desc">
                    BNIBT was established on 1985 and affiliated with China Isotope & Radiation Corporation Co., Ltd.
                    (China Tong fu, 1763.HK),which is the holding subsidiary of China National Nuclear Corporation.
                    BNIBT has engaged in researching, producing, selling and service of vitro diagnostic kits. The
                    production platforms cover RIA, CLIA, EIA, TRFIA, POCT and biological raw materials. BNIBT has more
                    than 150 product registration certificates, forming a nationwide marketing network. Products are
                    exported to more than 10 countries, such as Asia, Africa and Europe. It can also provide
                    comprehensive product solutions and overall services in the vitro diagnostic field .
                </p>
                <p class="aboutus-desc">
                    BNIBT adheres to the concepts of "customer-centered, employee-based, win-win cooperation" ,
                    “independent research by ourselves, continuously introduce new products, “improvement of quality ,
                    service standards and capabilities ” and strives to build brands of enterprises and products.
                </p>
            <#else>
                <p class="aboutus-desc">
                    北京北方生物技术研究所有限公司（简称“北方所”）秉承“聚焦体外诊断，服务人类健康”的使命，努力成为一流的体外诊断产品和服务综合提供商。
                </p>
                <p class="aboutus-desc">
                    北方所创立于1985年，隶属于中国核工业集团有限公司旗下的中国同辐股份有限公司（中国同辐，1763．HK），专注于体外诊断试剂产品的研发、生产、经营及服务，产品涵盖放射免疫、化学发光、酶联免疫、时间分辨、胶体金和生物原材料等技术平台，拥有150多项产品注册证书，形成了覆盖全国的营销网络，产品出口至亚非欧等10余个国家或地区，能够为体外诊断领域提供全面的产品解决方案和整体服务。
                </p>
                <p class="aboutus-desc">
                    公司坚持“以客户为中心、以员工为根本、合作共赢”的理念，坚持自主研发路线、持续推出新产品、提高质量控制水平、提升服务标准和能力，着力打造企业和产品品牌。
                </p>
            </#if>
            <img class="aboutus-pic" src="http://img.bnibt.com/static/conpany_one.png"
                 alt="北方所创立于1985年，隶属于中国核工业集团有限公司旗下的中国同辐股份有限公司（中国同辐，1763．HK），专注于体外诊断试剂产品的研发、生产、经营及服务，产品涵盖放射免疫、化学发光、酶联免疫、时间分辨、胶体金和生物原材料等技术平台，拥有150多项产品注册证书，形成了覆盖全国的营销网络，产品出口至亚非欧等10余个国家或地区，能够为体外诊断领域提供全面的产品解决方案和整体服务">

            <img class="aboutus-pic" src="http://img.bnibt.com/static/conpany_two.png"
                 alt="北方所创立于1985年，隶属于中国核工业集团有限公司旗下的中国同辐股份有限公司（中国同辐，1763．HK），专注于体外诊断试剂产品的研发、生产、经营及服务，产品涵盖放射免疫、化学发光、酶联免疫、时间分辨、胶体金和生物原材料等技术平台，拥有150多项产品注册证书，形成了覆盖全国的营销网络，产品出口至亚非欧等10余个国家或地区，能够为体外诊断领域提供全面的产品解决方案和整体服务">
        </div>
    </div>
    <!------------------------- 合作伙伴 ----------------------->
    <div class="partners">
        <div class="container">
            <p class="hidden-xs partner-title">
                ${Language.lans[lan].index_friendlink_title}
            </p>
            <#if links?size gt 0>
                <div class="logos">
                    <#list links as item>
                        <a hidefocus="true" title="${item.title?html}" href="${item.action}">
                            <div class="logo-item">
                                <img src="${(item.bannerList[0].resourceUrl)!''}${(item.bannerList[0].params)!''}"
                                     alt="${item.title?html}">
                            </div>
                        </a>
                    </#list>
                </div>
            </#if>
            <#if lan?? && lan == "en">
                <div class="parter-site panel-group" id="accordion-site">
                    <div class="sites">
                        <a class="btn" data-toggle="collapse" aria-expanded="false" data-parent="#accordion-site"
                           aria-controls="collapseExample1" href="#collapseExample1" role="button">Government</a>
                        <a class="btn" data-toggle="collapse" aria-expanded="false" data-parent="#accordion-site"
                           aria-controls="collapseExample2" href="#collapseExample2" role="button">Central
                            Enterprise</a>
                        <a class="btn" data-toggle="collapse" aria-expanded="false" data-parent="#accordion-site"
                           aria-controls="collapseExample3" href="#collapseExample3" role="button">Media Sites</a>
                        <a class="btn" data-toggle="collapse" aria-expanded="false" data-parent="#accordion-site"
                           aria-controls="collapseExample4" href="#collapseExample4" role="button">Life Information</a>
                    </div>
                    <div class="sites-container panel">
                        <div class="panel-collapse collapse" id="collapseExample1">
                            <ul class="site-list">
                                <li><a target="_blank" href="http://www.sasac.gov.cn/">国资委</a></li>
                                <li><a target="_blank" href="http://www.fmprc.gov.cn/">外 交 部</a></li>
                                <li><a target="_blank" href="http://www.spp.gov.cn/">高 检 院</a></li>
                                <li><a target="_blank" href="http://www.moe.edu.cn/">教 育 部</a></li>
                                <li><a target="_blank" href="http://www.mps.gov.cn//">公 安 部</a></li>
                                <li><a target="_blank" href="http://www.mca.gov.cn/">民 政 部</a></li>
                                <li><a target="_blank" href="http://www.mohurd.gov.cn/">建 设 部</a></li>
                                <li><a target="_blank" href="http://www.12306.cn/">铁 道 部</a></li>
                                <li><a target="_blank" href="http://www.moc.gov.cn/">交 通 部</a></li>
                                <li><a target="_blank" href="http://www.nhfpc.gov.cn/">计 生 委</a></li>
                                <li><a target="_blank" href="http://www.pbc.gov.cn/">人民银行</a></li>
                                <li><a target="_blank" href="http://wms.mofcom.gov.cn//">外经贸部</a></li>
                                <li><a target="_blank" href="http://www.chinatax.gov.cn/">税务总局</a></li>
                                <li><a target="_blank" href="http://www.chinatax.gov.cn/">国家税务总局</a></li>
                                <li><a target="_blank" href="http://www.chinacoop.com/">供销总社</a></li>
                                <li><a target="_blank" href="http://www.clii.com.cn/">轻工业局</a></li>
                                <li><a target="_blank" href="http://www.caac.gov.cn/">民航总局</a></li>
                                <li><a target="_blank" href="http://www.sport.gov.cn/">体育总局</a></li>
                                <li><a target="_blank" href="http://www.zhb.gov.cn/">环保总局</a></li>
                                <li><a target="_blank" href="http://www.sdpc.gov.cn/">国家发改委</a></li>
                                <li><a target="_blank" href="http://www.cnii.com.cn/">信息产业部</a></li>
                                <li><a target="_blank" href="http://www.most.gov.cn/">科学技术部</a></li>
                                <li><a target="_blank" href="http://www.cnsa.gov.cn">国家航天局</a></li>
                                <li><a target="_blank" href="http://www.safe.gov.cn">外汇管理局</a></li>
                                <li><a target="_blank" href="http://www.mlr.gov.cn/">国土资源部</a></li>
                                <li><a target="_blank" href="http://www.cnnic.net.cn/">互联网中心</a></li>
                                <li><a target="_blank" href="http://www.stats.gov.cn/">国家统计局</a></li>
                                <li><a target="_blank" href="http://www.soa.gov.cn/">国家海洋局</a></li>
                                <li><a target="_blank" href="http://www.sbsm.gov.cn/">国家测绘局</a></li>
                                <li><a target="_blank" href="http://www.sipo.gov.cn/">知识产权局</a></li>
                                <li><a target="_blank" href="http://www.aqsiq.gov.cn/">国家质监局</a></li>
                                <li><a target="_blank" href="http://www.sda.gov.cn/">药品监管局</a></li>
                                <li><a target="_blank" href="http://www.chinacoal.gov.cn/">煤炭工业局</a></li>
                                <li><a target="_blank" href="http://www.chinacoal.gov.cn/">煤矿工业局</a></li>
                                <li><a target="_blank" href="http://xiehui.ctei.cn/">中国纺织工业联合会</a></li>
                                <li><a target="_blank" href="http://www.cbminfo.com/">中国建材信息网</a></li>
                                <li><a target="_blank" href="http://www.tobaccochina.com/">烟草专卖局</a></li>
                                <li><a target="_blank" href="http://www.mohrss.gov.cn/">劳动保障部</a></li>
                                <li><a target="_blank" href="http://www.saic.gov.cn/">工商行政管理</a></li>
                                <li><a target="_blank" href="http://www.caea.gov.cn/">国家原子能机构</a></li>
                                <li><a target="_blank" href="http://www.china-nea.cn/">核能行业协会</a></li>
                                <li><a target="_blank" href="http://www.cec.org.cn/">中国电力企业联合会</a></li>
                            </ul>
                        </div>
                        <div class="panel-collapse collapse" id="collapseExample2">
                            <ul class="site-list">
                                <li><a target="_blank" href="http://www.cnecc.com/">核工业建设集团</a></li>
                                <li><a target="_blank" href="http://www.spacechina.com/">航天科技集团公司</a></li>
                                <li><a target="_blank" href="http://www.casic.com.cn/">航天科工集团公司</a></li>
                                <li><a target="_blank" href="http://www.cssc.net.cn/">船舶工业集团公司</a></li>
                                <li><a target="_blank" href="http://www.cngc.com.cn/">兵器工业集团公司</a></li>
                                <li><a target="_blank" href="http://www.chinasouth.com.cn/">兵器装备集团公司</a></li>
                                <li><a target="_blank" href="http://www.acef.com.cn/">中华环保联合会网</a></li>
                                <li><a target="_blank" href="http://www.zhige.net/">中国兵器止戈网</a></li>
                                <li><a target="_blank" href="http://www.cgnpc.com.cn/">中国广东核电集团</a></li>
                                <li><a target="_blank" href="http://www.spic.com.cn/">国家电力投资集团</a></li>
                            </ul>
                        </div>
                        <div class="panel-collapse collapse" id="collapseExample3">
                            <ul class="site-list">
                                <li><a target="_blank" href="http://www.xinhua.org/">新 华 网</a></li>
                                <li><a target="_blank" href="http://www.chinanews.com.cn/">中 新 社</a></li>
                                <li><a target="_blank" href="http://www.peopledaily.com.cn/">人 民 网</a></li>
                                <li><a target="_blank" href="http://www.sina.com.cn/">新 浪 网</a></li>
                                <li><a target="_blank" href="http://www.sohu.com/">搜 狐 网</a></li>
                                <li><a target="_blank" href="http://whb.news365.com.cn/">文 汇 报</a></li>
                                <li><a target="_blank" href="http://epaper.ynet.com">北 青 报</a></li>
                                <li><a target="_blank" href="http://www.hsm.com.cn/">华 声 报</a></li>
                                <li><a target="_blank" href="http://www.cyd.com.cn/">中青在线</a></li>
                                <li><a target="_blank" href="http://www.gmw.cn/">光明日报</a></li>
                                <li><a target="_blank" href="http://www.nanfangdaily.com.cn/">南方日报</a></li>
                                <li><a target="_blank" href="http://www.dzdaily.com.cn/">大众日报</a></li>
                                <li><a target="_blank" href="http://www.stdaily.com/">科技日报</a></li>
                                <li><a target="_blank" href="http://www.women.org.cn/">中国妇女</a></li>
                                <li><a target="_blank" href="http://www.morningpost.com.cn/">北晨网</a></li>
                                <li><a target="_blank" href="http://www.ycwb.com/">金羊网</a></li>
                                <li><a target="_blank" href="http://www.acin.org.cn/">中国产业报协会</a></li>
                                <li><a target="_blank" href="http://www.cinic.org.cn/">中国产业网</a></li>
                            </ul>

                        </div>
                        <div class="panel-collapse collapse" id="collapseExample4">
                            <ul class="site-list">
                                <li><a target="_blank" href="http://www.keyunzhan.com/">全国客运查询系统</a></li>
                                <li><a target="_blank" href="http://www.piao.com.cn/">中国票务网</a></li>
                                <li><a target="_blank" href="http://www.bjjtgl.gov.cn/publish/portal0/">驾驶员违章查询</a></li>
                                <li><a target="_blank" href="http://www.ctrip.com">航班查询</a></li>
                                <li><a target="_blank" href="http://www.12306.cn/mormhweb/">火车票查询</a></li>
                                <li><a target="_blank" href="http://www.rushangwu.com/">儒商坞网</a></li>
                            </ul>

                        </div>
                    </div>
                </div>
            <#else>
                <div class="parter-site panel-group" id="accordion-site">
                    <div class="sites">
                        <a class="btn" data-toggle="collapse" aria-expanded="false" data-parent="#accordion-site"
                           aria-controls="collapseExample1" href="#collapseExample1" role="button">政府站点</a>
                        <a class="btn" data-toggle="collapse" aria-expanded="false" data-parent="#accordion-site"
                           aria-controls="collapseExample2" href="#collapseExample2" role="button">央企站点</a>
                        <a class="btn" data-toggle="collapse" aria-expanded="false" data-parent="#accordion-site"
                           aria-controls="collapseExample3" href="#collapseExample3" role="button">媒体网站</a>
                        <a class="btn" data-toggle="collapse" aria-expanded="false" data-parent="#accordion-site"
                           aria-controls="collapseExample4" href="#collapseExample4" role="button">生活信息服务</a>
                    </div>
                    <div class="sites-container panel">
                        <div class="panel-collapse collapse" id="collapseExample1">
                            <ul class="site-list">
                                <li><a target="_blank" href="http://www.sasac.gov.cn/">国资委</a></li>
                                <li><a target="_blank" href="http://www.fmprc.gov.cn/">外 交 部</a></li>
                                <li><a target="_blank" href="http://www.spp.gov.cn/">高 检 院</a></li>
                                <li><a target="_blank" href="http://www.moe.edu.cn/">教 育 部</a></li>
                                <li><a target="_blank" href="http://www.mps.gov.cn//">公 安 部</a></li>
                                <li><a target="_blank" href="http://www.mca.gov.cn/">民 政 部</a></li>
                                <li><a target="_blank" href="http://www.mohurd.gov.cn/">建 设 部</a></li>
                                <li><a target="_blank" href="http://www.12306.cn/">铁 道 部</a></li>
                                <li><a target="_blank" href="http://www.moc.gov.cn/">交 通 部</a></li>
                                <li><a target="_blank" href="http://www.nhfpc.gov.cn/">计 生 委</a></li>
                                <li><a target="_blank" href="http://www.pbc.gov.cn/">人民银行</a></li>
                                <li><a target="_blank" href="http://wms.mofcom.gov.cn//">外经贸部</a></li>
                                <li><a target="_blank" href="http://www.chinatax.gov.cn/">税务总局</a></li>
                                <li><a target="_blank" href="http://www.chinatax.gov.cn/">国家税务总局</a></li>
                                <li><a target="_blank" href="http://www.chinacoop.com/">供销总社</a></li>
                                <li><a target="_blank" href="http://www.clii.com.cn/">轻工业局</a></li>
                                <li><a target="_blank" href="http://www.caac.gov.cn/">民航总局</a></li>
                                <li><a target="_blank" href="http://www.sport.gov.cn/">体育总局</a></li>
                                <li><a target="_blank" href="http://www.zhb.gov.cn/">环保总局</a></li>
                                <li><a target="_blank" href="http://www.sdpc.gov.cn/">国家发改委</a></li>
                                <li><a target="_blank" href="http://www.cnii.com.cn/">信息产业部</a></li>
                                <li><a target="_blank" href="http://www.most.gov.cn/">科学技术部</a></li>
                                <li><a target="_blank" href="http://www.cnsa.gov.cn">国家航天局</a></li>
                                <li><a target="_blank" href="http://www.safe.gov.cn">外汇管理局</a></li>
                                <li><a target="_blank" href="http://www.mlr.gov.cn/">国土资源部</a></li>
                                <li><a target="_blank" href="http://www.cnnic.net.cn/">互联网中心</a></li>
                                <li><a target="_blank" href="http://www.stats.gov.cn/">国家统计局</a></li>
                                <li><a target="_blank" href="http://www.soa.gov.cn/">国家海洋局</a></li>
                                <li><a target="_blank" href="http://www.sbsm.gov.cn/">国家测绘局</a></li>
                                <li><a target="_blank" href="http://www.sipo.gov.cn/">知识产权局</a></li>
                                <li><a target="_blank" href="http://www.aqsiq.gov.cn/">国家质监局</a></li>
                                <li><a target="_blank" href="http://www.sda.gov.cn/">药品监管局</a></li>
                                <li><a target="_blank" href="http://www.chinacoal.gov.cn/">煤炭工业局</a></li>
                                <li><a target="_blank" href="http://www.chinacoal.gov.cn/">煤矿工业局</a></li>
                                <li><a target="_blank" href="http://xiehui.ctei.cn/">中国纺织工业联合会</a></li>
                                <li><a target="_blank" href="http://www.cbminfo.com/">中国建材信息网</a></li>
                                <li><a target="_blank" href="http://www.tobaccochina.com/">烟草专卖局</a></li>
                                <li><a target="_blank" href="http://www.mohrss.gov.cn/">劳动保障部</a></li>
                                <li><a target="_blank" href="http://www.saic.gov.cn/">工商行政管理</a></li>
                                <li><a target="_blank" href="http://www.caea.gov.cn/">国家原子能机构</a></li>
                                <li><a target="_blank" href="http://www.china-nea.cn/">核能行业协会</a></li>
                                <li><a target="_blank" href="http://www.cec.org.cn/">中国电力企业联合会</a></li>
                            </ul>
                        </div>
                        <div class="panel-collapse collapse" id="collapseExample2">
                            <ul class="site-list">
                                <li><a target="_blank" href="http://www.cnecc.com/">核工业建设集团</a></li>
                                <li><a target="_blank" href="http://www.spacechina.com/">航天科技集团公司</a></li>
                                <li><a target="_blank" href="http://www.casic.com.cn/">航天科工集团公司</a></li>
                                <li><a target="_blank" href="http://www.cssc.net.cn/">船舶工业集团公司</a></li>
                                <li><a target="_blank" href="http://www.cngc.com.cn/">兵器工业集团公司</a></li>
                                <li><a target="_blank" href="http://www.chinasouth.com.cn/">兵器装备集团公司</a></li>
                                <li><a target="_blank" href="http://www.acef.com.cn/">中华环保联合会网</a></li>
                                <li><a target="_blank" href="http://www.zhige.net/">中国兵器止戈网</a></li>
                                <li><a target="_blank" href="http://www.cgnpc.com.cn/">中国广东核电集团</a></li>
                                <li><a target="_blank" href="http://www.spic.com.cn/">国家电力投资集团</a></li>
                            </ul>
                        </div>
                        <div class="panel-collapse collapse" id="collapseExample3">
                            <ul class="site-list">
                                <li><a target="_blank" href="http://www.xinhua.org/">新 华 网</a></li>
                                <li><a target="_blank" href="http://www.chinanews.com.cn/">中 新 社</a></li>
                                <li><a target="_blank" href="http://www.peopledaily.com.cn/">人 民 网</a></li>
                                <li><a target="_blank" href="http://www.sina.com.cn/">新 浪 网</a></li>
                                <li><a target="_blank" href="http://www.sohu.com/">搜 狐 网</a></li>
                                <li><a target="_blank" href="http://whb.news365.com.cn/">文 汇 报</a></li>
                                <li><a target="_blank" href="http://epaper.ynet.com">北 青 报</a></li>
                                <li><a target="_blank" href="http://www.hsm.com.cn/">华 声 报</a></li>
                                <li><a target="_blank" href="http://www.cyd.com.cn/">中青在线</a></li>
                                <li><a target="_blank" href="http://www.gmw.cn/">光明日报</a></li>
                                <li><a target="_blank" href="http://www.nanfangdaily.com.cn/">南方日报</a></li>
                                <li><a target="_blank" href="http://www.dzdaily.com.cn/">大众日报</a></li>
                                <li><a target="_blank" href="http://www.stdaily.com/">科技日报</a></li>
                                <li><a target="_blank" href="http://www.women.org.cn/">中国妇女</a></li>
                                <li><a target="_blank" href="http://www.morningpost.com.cn/">北晨网</a></li>
                                <li><a target="_blank" href="http://www.ycwb.com/">金羊网</a></li>
                                <li><a target="_blank" href="http://www.acin.org.cn/">中国产业报协会</a></li>
                                <li><a target="_blank" href="http://www.cinic.org.cn/">中国产业网</a></li>
                            </ul>

                        </div>
                        <div class="panel-collapse collapse" id="collapseExample4">
                            <ul class="site-list">
                                <li><a target="_blank" href="http://www.keyunzhan.com/">全国客运查询系统</a></li>
                                <li><a target="_blank" href="http://www.piao.com.cn/">中国票务网</a></li>
                                <li><a target="_blank" href="http://www.bjjtgl.gov.cn/publish/portal0/">驾驶员违章查询</a></li>
                                <li><a target="_blank" href="http://www.ctrip.com">航班查询</a></li>
                                <li><a target="_blank" href="http://www.12306.cn/mormhweb/">火车票查询</a></li>
                                <li><a target="_blank" href="http://www.rushangwu.com/">儒商坞网</a></li>
                            </ul>

                        </div>
                    </div>
                </div>
            </#if>
        </div>
    </div>
</@base.base_container>