<#import 'common/base.ftl' as base>
<#import 'common/breadcrumb.ftl' as breadcrumb>
<#import 'common/language.ftl' as Language>
<@base.base_container
title="${Language.lans[lan].profile_title}"
headers=[
"/css/breadcrumb.css",
"/css/profile.css"
]
scripts=[
"/js/profile.js"
]>
    <div class="profile">
        <img class="banner hidden-xs" src="http://img.bnibt.com/static/banner_sevice.png" alt="">
        <img class="banner visible-xs-block" src="http://img.bnibt.com/static/banner_sevice.png?x-oss-process=image/crop,w_750,h_300,g_center" alt="">
        <@breadcrumb.breadcrumb paths=[
        {
        "url": "${Language.lans[lan].url_path}index.html",
        "zh": "首页",
        "en": "Home"
        }, {
        "url": "#",
        "zh": "关于我们",
        "en": "Advisory"
        }, {
        "url": "${Language.lans[lan].url_path}advisory.html",
        "zh": "公司简介",
        "en": "About Us"
        }] />
       <div class="container">
           <h1 class="profile-title">
               ${Language.lans[lan].profile_content_desc}
           </h1>
           <#if lan?? && lan == "en">
               <div class="profile-content">
                   <p>
                       Beijing North Institute of Biotechnology Co.,Ltd (BNIBT) upholds the mission of "Focusing on in vitro diagnosis, serving human health", and strives to be a top-ranking comprehensive provider in vitro diagnostic products and services.
                   </p>
                   <p>
                       BNIBT was established on 1985 and affiliated with China Isotope & Radiation Corporation Co., Ltd. (China Tong fu, 1763.HK),which is the holding subsidiary of China National Nuclear Corporation. BNIBT has engaged in researching, producing, selling and service of vitro diagnostic kits. The production platforms cover RIA, CLIA, EIA, TRFIA, POCT and biological raw materials. BNIBT has more than 150 product registration certificates, forming a nationwide marketing network. Products are exported to more than 10 countries, such as Asia, Africa and Europe. It can also provide comprehensive product solutions and overall services in the vitro diagnostic field.
                   </p>
               </div>
           <#else>
               <div class="profile-content">
                   <p>
                       北京北方生物技术研究所有限公司(以下简称：北方所）成立于1985年，隶属于中国核工业集团直属的中国同辐股份有限公司，是国内最早从事免疫诊断试剂产品的研发和生产的机构之一,也是体外诊断药盒的生物技术企业。
                   </p>
                   <p>
                       二十多年来，北方所以“发展诊断事业，造福人类健康”为宗旨，以创建免疫诊断技术国内一流企业为目标，在覆盖免疫诊断产业的多个领域——化学发光、酶联免疫、POCT放射免疫建立起发展平台。
                   </p>
                   <p>
                       北方所下设研发、制造、质检、营销、外贸、免疫检验中心等机构，拥有甲功、性腺、肿瘤、心血管、肾病、内分泌、糖尿病、胃肠病、骨代谢、肝炎类、病毒、细胞因子、高血压因子等全系列检测产品。北方所现有产品十多大类300余种，用户遍及全国各省、市、自治区，部分产品出口韩国、菲律宾等国家。
                   </p>
               </div>
           </#if>
       </div>
        <div class="profile-center">
            <div class="profile-center-img">
                <img src="http://img.bnibt.com/static/conpany_img.png" alt="">
            </div>
            <#if lan?? && lan == "en">
                <div class="profile-other">
                    <p>
                        BNIBT adheres to the concepts of "customer-centered, employee-based, win-win cooperation" , “independent research by ourselves, continuously introduce new products, “improvement of quality , service standards and capabilities ” and strives to build brands of enterprises and products.
                    </p>
                </div>
            <#else>
                <div class="profile-other">
                    <p>
                        北方所新开展的免疫检验中心专业服务于全国各科研课题组，为其提供专业化的检测服务。成功地承担了国家药检所乙肝项目、营养所铁蛋白项目、北京市疾病预防控制中心孕妇乙肝病毒携带者的遴选筛查项目、中华糖尿病学会全国流行病学调查项目等重大课题的放射免疫分析药盒的生产保障及标本检测工作。对于跨批次检测的变异系数控制，样品前处理等方面具有出色控制能力和经验。
                    </p>
                    <p>
                        北京北方生物技术研究所从诞生到现在的发展过程中，一直得到了全国广大用户的关心和支持，北方所全体职工在此向您表示衷心的感谢！我们将以优良的产品质量，以真诚，优质，高效的服务满足您们的期望，为人类健康做出贡献！
                    </p>
                </div>
            </#if>
        </div>
        <div class="container">
            <h2 class="profile-title">
                ${Language.lans[lan].profile_organization}
            </h2>
            <div class="profile-charts">
                <img src="https://bnibt-public-resource.oss-cn-beijing.aliyuncs.com/static/Organization_2019_0306.jpg" alt="">
            </div>
        </div>
    </div>
</@base.base_container>